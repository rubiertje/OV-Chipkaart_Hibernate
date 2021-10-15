package project.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import project.domein.Reiziger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOSQLHibernate extends BaseDAOHibernate implements ReizigerDAO{

    public ReizigerDAOSQLHibernate(Session sess) {
        super(sess);
    }

    @Override
    public boolean save(Reiziger reiziger) throws HibernateException {
        try {
            sess.beginTransaction();
            sess.save(reiziger);
            sess.getTransaction().commit();
            return true;
        }catch (HibernateException ignored){}
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) throws HibernateException {
        try {
            sess.beginTransaction();
            sess.update(reiziger);
            sess.getTransaction().commit();
            return true;
        }catch (HibernateException ignored){}
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws HibernateException {
        try {
            sess.beginTransaction();
            sess.delete(reiziger);
            sess.getTransaction().commit();
            return true;
        }catch (HibernateException ignored){}
        return false;
    }

    @Override
    public Reiziger findById(int id) throws HibernateException {
        try{
            return sess.get(Reiziger.class, id);
        }catch (HibernateException ignored){}
        return null;
    }

    @Override
    public List<Reiziger> findByGbDatum(String geboortedatum) throws HibernateException {
        try{
            return sess.createQuery("FROM reiziger WHERE geboortedatum = '" + geboortedatum + "'", Reiziger.class).getResultList();
        }catch (HibernateException ignored){}
        return null;
    }

    @Override
    public List<Reiziger> findAll() throws HibernateException {
        try {
            return sess.createQuery("FROM reiziger ", Reiziger.class).getResultList();
        }catch (HibernateException ignored){}
        return null;
    }
}
