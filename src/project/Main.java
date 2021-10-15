package project;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import project.dao.*;
import project.domein.Adres;
import project.domein.OVChipkaart;
import project.domein.Product;
import project.domein.Reiziger;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
//        testFetchAll();
//        testreiziger();
//        testAdresDAO();
//        testOVChipkaartDAO();
        testProductDAO();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o.toString());
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    private static void testreiziger(){
        ReizigerDAO rdao = new ReizigerDAOSQLHibernate(getSession());

        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        Reiziger reizigersid = rdao.findById(99);
        System.out.println("[Test] ReizigerDAO.findById geeft de volgende reiziger:");
        System.out.println(reizigersid);

        System.out.println();

        List<Reiziger> reizigersgbdatum = rdao.findByGbDatum("2002-12-03");
        System.out.println("[Test] ReizigerDAO.findByGbDatum geeft de volgende reizigers:");
        for (Reiziger r : reizigersgbdatum) {
            System.out.println(r);
        }
        System.out.println();


        // Maak een nieuwe reiziger aan en persisteer deze in de database
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", LocalDate.of(1981, 3, 14));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers");

        System.out.println();
        System.out.println("[Test] Aantal reizigers voor de Delete functie: " + reizigers.size());

        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println("Aantal reizigers na de Delete functie: " + reizigers.size());

        System.out.println();

        System.out.println("[Test] UPDATE FUNCTIE");
        System.out.println("De reiziger voor de update: \n\t" + sietske.getId() + "\n\t" + sietske.getVoorletters() + "\n\t" + sietske.getTussenvoegsel() + "\n\t" + sietske.getAchternaam() + "\n\t" + sietske.getGeboortedatum());
        sietske.setVoorletters("R");
        sietske.setTussenvoegsel("van");
        sietske.setAchternaam("Rooijen");
        sietske.setGeboortedatum(LocalDate.of(2003, 4, 23));
        System.out.println("De reiziger na de update: \n\t" + sietske.getId() + "\n\t" + sietske.getVoorletters() + "\n\t" + sietske.getTussenvoegsel() + "\n\t" + sietske.getAchternaam() + "\n\t" + sietske.getGeboortedatum());
    }

    private static void testAdresDAO() throws SQLException {
        AdresDAO adoa = new AdresDAOSQLHibernate(getSession());

        System.out.println("\n---------- Test AdresDAO -------------");

        List<Adres> adressen = adoa.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende Adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        Reiziger reiziger = new Reiziger(4, "H", "", "Memari", LocalDate.of(2002, 12, 3));
        List<Adres> adressenByReiziger = adoa.findByReiziger(reiziger);
        System.out.println("[Test] AdresDAO.findByReiziger() geeft de volgende Adressen:");
        for (Adres a : adressenByReiziger) {
            System.out.println(a);
        }
        System.out.println();

        Reiziger r1 = new Reiziger(99, "T.I", "van", "Rooijen", LocalDate.of(2003, 4, 23));
        Adres adres5 = new Adres(100, "1111 AA", "1", "heidelberglaan", "Spakenburg", r1);
        System.out.print("[Test] Eerst " + adressen.size() + " Adressen, na AdresDAO.save() ");
        adoa.save(adres5);
        adressen = adoa.findAll();
        System.out.println(adressen.size() + " adressen");


        System.out.println();
        System.out.println("[Test] UPDATE FUNCTIE");
        System.out.println("Het adres voor de update: \n\t" + adres5.getId() + "\n\t" + adres5.getPostcode() + "\n\t" + adres5.getHuisnummer() + "\n\t" + adres5.getStraat() + "\n\t" + adres5.getWoonplaats() + "\n\t" + adres5.getReiziger());
        adres5.setPostcode("2222 BB");
        adres5.setHuisnummer("2");
        adres5.setStraat("Padualaan");
        adres5.setWoonplaats("Utrecht");
        adoa.update(adres5);
        System.out.println("Het adres na de update: \n\t" + adres5.getId() + "\n\t" + adres5.getPostcode() + "\n\t" + adres5.getHuisnummer() + "\n\t" + adres5.getStraat() + "\n\t" + adres5.getWoonplaats() + "\n\t" + adres5.getReiziger());



        System.out.println();
        System.out.println("[Test] Aantal adressen voor de Delete functie: " + adressen.size());

        adoa.delete(adres5);
        adressen = adoa.findAll();
        System.out.println("Aantal adressen na de Delete functie: " + adressen.size());
    }

    public static void testOVChipkaartDAO() throws SQLException {
        OVChipkaartDAO ovdoa = new OVChipkaartDAOSQLHibernate(getSession());

        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        List<OVChipkaart> chipkaarts = ovdoa.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende kaarten:");
        for (OVChipkaart ovChipkaart : chipkaarts) {
            System.out.println(ovChipkaart);
        }
        System.out.println();

        Reiziger reiziger = new Reiziger(2, "B", "van", "Rijn", LocalDate.of(2002, 10, 22));
        List<OVChipkaart> chipkaartsFindByReiziger = ovdoa.findByReiziger(reiziger);
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende kaarten:");
        for (OVChipkaart ovChipkaart : chipkaartsFindByReiziger) {
            System.out.println(ovChipkaart);
        }
        System.out.println();

        Reiziger r1 = new Reiziger(99, "T.I", "van", "Rooijen", LocalDate.of(2003, 4, 23));
        OVChipkaart ovChipkaart = new OVChipkaart(5, LocalDate.of(2022, 2, 4), 1, 45.33, r1);
        OVChipkaart ovChipkaart1 = new OVChipkaart(6, LocalDate.of(2022, 2, 4), 1, 45.33, r1);
        System.out.print("[Test] Eerst " + chipkaarts.size() + " kaarten, na 2 x OVChipkaartDAO.save() ");
        ovdoa.save(ovChipkaart);
        ovdoa.save(ovChipkaart1);
        chipkaarts = ovdoa.findAll();
        System.out.println(chipkaarts.size() + " kaarten");


        System.out.println();
        System.out.println("[Test] UPDATE FUNCTIE");
        System.out.println("De kaart voor de update: \n\t" + ovChipkaart.getKaartNummer() + "\n\t" + ovChipkaart.getGeldigTot() + "\n\t" + ovChipkaart.getKlasse() + "\n\t" + ovChipkaart.getSaldo());
        ovChipkaart.setGeldigTot(LocalDate.of(2222,2,22));
        ovChipkaart.setKlasse(2);
        ovChipkaart.setSaldo(11.11);
        ovdoa.update(ovChipkaart);
        System.out.println("De kaart na de update: \n\t" + ovChipkaart.getKaartNummer() + "\n\t" + ovChipkaart.getGeldigTot() + "\n\t" + ovChipkaart.getKlasse() + "\n\t" + ovChipkaart.getSaldo());


        System.out.println();
        System.out.println("[Test] Aantal kaarten voor de Delete functie: " + chipkaarts.size());

        ovdoa.delete(ovChipkaart);
        ovdoa.delete(ovChipkaart1);
        chipkaarts = ovdoa.findAll();
        System.out.println("Aantal kaarten na 2 x de Delete functie: " + chipkaarts.size());
    }

    public static void testProductDAO() throws SQLException {
        ProductDAO pdoa = new ProductDAOSQLHibernate(getSession());

        System.out.println("\n---------- Test ProductDAO -------------");

        List<Product> products = pdoa.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println();

        Reiziger reiziger = new Reiziger(2, "B", "van", "Rijn", LocalDate.of(2002, 10, 22));
        OVChipkaart ovChipkaart = new OVChipkaart(35283, LocalDate.of(2018, 5, 31), 2, 25.50, reiziger);
        List<Product> productsfindByOVChipkaart = pdoa.findByOVChipkaart(ovChipkaart);
        System.out.println("[Test] ProductDAO.findByOVChipkaart() geeft de volgende producten:");
        for (Product product : productsfindByOVChipkaart) {
            System.out.println(product);
        }
        System.out.println();

        Product product = new Product(999, "Test", "testfunctieproduct", 99.99);
        System.out.print("[Test] Eerst " + products.size() + " producten, na ProductDAO.save() ");
        pdoa.save(product);
        products = pdoa.findAll();
        System.out.println(products.size() + " producten");


        System.out.println();
        System.out.println("[Test] UPDATE FUNCTIE");
        System.out.println("Het product voor de update: '" + product.getBeschrijving() + "'");
        product.setBeschrijving("eenanderebeschrijving");
        pdoa.update(product);
        System.out.println("Het product na de update: '" + product.getBeschrijving() + "'");


        System.out.println();
        System.out.println("[Test] Aantal producten voor de Delete functie: " + products.size());

        pdoa.delete(product);
        products = pdoa.findAll();
        System.out.println("Aantal producten na de delete functie: " + products.size());
    }

}