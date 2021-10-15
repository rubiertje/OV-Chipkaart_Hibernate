package project.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import project.domein.Adres;
import project.domein.OVChipkaart;
import project.domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public class OVChipkaartDAOSQLHibernate extends BaseDAOHibernate implements OVChipkaartDAO{
    public OVChipkaartDAOSQLHibernate(Session sess) {
        super(sess);
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws HibernateException {
        try {
            sess.beginTransaction();
            sess.save(ovChipkaart);
            sess.getTransaction().commit();
            return true;
        }catch (HibernateException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws HibernateException {
        try {
            sess.beginTransaction();
            sess.update(ovChipkaart);
            sess.getTransaction().commit();
            return true;
        }catch (HibernateException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws HibernateException {
        try {
            sess.beginTransaction();
            sess.delete(ovChipkaart);
            sess.getTransaction().commit();
            return true;
        }catch (HibernateException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws HibernateException {
        try{
            return sess.createQuery("FROM ov_chipkaart o WHERE o.reiziger = " + reiziger.getId(), OVChipkaart.class).getResultList();
        }catch (HibernateException ignored){}
        return null;
    }

    @Override
    public List<OVChipkaart> findAll() throws HibernateException {
        try {
            return sess.createQuery("FROM ov_chipkaart ", OVChipkaart.class).getResultList();
        }catch (HibernateException ignored){}
        return null;
    }
}
