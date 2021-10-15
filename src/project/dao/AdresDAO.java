package project.dao;


import org.hibernate.HibernateException;
import project.domein.*;

import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {

    public boolean save(Adres adres) throws HibernateException;

    public boolean update(Adres adres) throws HibernateException;

    public boolean delete(Adres adres) throws HibernateException;

    public List<Adres> findByReiziger(Reiziger reiziger) throws HibernateException;

    public List<Adres> findAll() throws HibernateException;

}
