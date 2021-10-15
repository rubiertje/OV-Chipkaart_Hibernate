package project.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;

public class BaseDAOHibernate {
    Session sess;

    public BaseDAOHibernate(Session sess) {
        this.sess = sess;
    }

    protected boolean executeInsideTransaction(Consumer<Session> action) {
        Transaction tx = sess.getTransaction();
        try {
            tx.begin();
            action.accept(sess);
            tx.commit();
            return true;
        } catch (RuntimeException e) {
            tx.rollback();
            System.err.println("[" + this.getClass().getSimpleName() + "] RuntimeException while executing " + action + ": " + e.getMessage());
        }
        return false;
    }
}
