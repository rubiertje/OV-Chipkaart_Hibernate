package project.dao;

import org.hibernate.HibernateException;
import project.domein.*;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    boolean save(Product product) throws HibernateException;

    boolean update(Product product) throws HibernateException;

    boolean delete(Product product) throws HibernateException;

    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws HibernateException;

    List<Product> findAll() throws HibernateException;
}
