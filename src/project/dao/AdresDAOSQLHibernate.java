package project.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import project.domein.Adres;
import project.domein.Reiziger;

import java.util.List;

public class AdresDAOSQLHibernate extends BaseDAOHibernate implements AdresDAO {
    public AdresDAOSQLHibernate(Session sess) {
        super(sess);
    }

    @Override
    public boolean save(Adres adres) throws HibernateException {
        try {
            sess.beginTransaction();
            sess.save(adres);
            sess.getTransaction().commit();
            return true;
        }catch (HibernateException ignored){}
        return false;
    }

    @Override
    public boolean update(Adres adres) throws HibernateException {
        try {
            sess.beginTransaction();
            sess.update(adres);
            sess.getTransaction().commit();
            return true;
        }catch (HibernateException ignored){}
        return false;
    }

    @Override
    public boolean delete(Adres adres) throws HibernateException {
        try {
            sess.beginTransaction();
            sess.delete(adres);
            sess.getTransaction().commit();
            return true;
        }catch (HibernateException ignored){}
        return false;
    }

    @Override
    public List<Adres> findByReiziger(Reiziger reiziger) throws HibernateException {
        try{
            return sess.createQuery("FROM adres a WHERE a.reiziger = " + reiziger.getId(), Adres.class).getResultList();
        }catch (HibernateException ignored){}
        return null;
    }

    @Override
    public List<Adres> findAll() throws HibernateException {
        try {
            return sess.createQuery("FROM adres ", Adres.class).getResultList();
        }catch (HibernateException ignored){}
        return null;
    }
}
