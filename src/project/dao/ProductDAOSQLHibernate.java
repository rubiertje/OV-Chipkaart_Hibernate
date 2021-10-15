package project.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import project.domein.Adres;
import project.domein.OVChipkaart;
import project.domein.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductDAOSQLHibernate extends BaseDAOHibernate implements ProductDAO{
    public ProductDAOSQLHibernate(Session sess) {
        super(sess);
    }

    @Override
    public boolean save(Product product) throws HibernateException {
        try {
            sess.beginTransaction();
            sess.save(product);
            sess.getTransaction().commit();
            return true;
        }catch (HibernateException ignored){}
        return false;
    }

    @Override
    public boolean update(Product product) throws HibernateException {
        try {
            sess.beginTransaction();
            sess.update(product);
            sess.getTransaction().commit();
            return true;
        }catch (HibernateException ignored){}
        return false;
    }

    @Override
    public boolean delete(Product product) throws HibernateException {
        try {
            sess.beginTransaction();
            sess.delete(product);
            sess.getTransaction().commit();
            return true;
        }catch (HibernateException ignored){}
        return false;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws HibernateException {
        try{
            return sess.createQuery("SELECT pr FROM product p " +
                    "JOIN p.chipkaarts c ON c.kaartNummer = " + ovChipkaart.getKaartNummer() +
                    " JOIN c.producten pr ON pr.productNummer = p.productNummer ", Product.class).getResultList();
        }catch (HibernateException ignored){}
        return null;
    }

    @Override
    public List<Product> findAll() throws HibernateException {
        try{
            return sess.createQuery("FROM product", Product.class).getResultList();
        }catch (HibernateException ignored){}
        return null;
    }
}
