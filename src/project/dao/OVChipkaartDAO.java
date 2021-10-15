package project.dao;

import org.hibernate.HibernateException;
import project.domein.*;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {

    public boolean save(OVChipkaart ovChipkaart) throws HibernateException;

    public boolean update(OVChipkaart ovChipkaart) throws HibernateException;

    public boolean delete(OVChipkaart ovChipkaart) throws HibernateException;

    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws HibernateException;

    public List<OVChipkaart> findAll() throws HibernateException;
}
