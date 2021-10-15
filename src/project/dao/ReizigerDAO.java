package project.dao;

import org.hibernate.HibernateException;
import project.domein.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
public interface ReizigerDAO {

    boolean save(Reiziger reiziger) throws HibernateException;

    boolean update(Reiziger reiziger) throws HibernateException;

    boolean delete(Reiziger reiziger) throws HibernateException;

    Reiziger findById(int id) throws HibernateException;

    List<Reiziger> findByGbDatum(String geboortedatum) throws HibernateException;

    List<Reiziger> findAll() throws HibernateException;
}
