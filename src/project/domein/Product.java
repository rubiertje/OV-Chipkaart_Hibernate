package project.domein;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "product")
public class Product {
    @Id
    @Column(name = "product_nummer")
    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    @ManyToMany(mappedBy = "producten")
    private List<OVChipkaart> chipkaarts;

    public Product(int productNummer, String naam, String beschrijving, double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.chipkaarts = new ArrayList<>();
    }

    public Product() {

    }

    public int getNummer() {
        return productNummer;
    }

    public void setNummer(int productNummer) {
        this.productNummer = productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public List<OVChipkaart> getChipkaartsnummers() {
        return chipkaarts;
    }

    public void addChipkaartNummer(OVChipkaart chipkaart) {
        this.chipkaarts.add(chipkaart);
    }

    public void removeChipkaartNummer(OVChipkaart chipkaart){
        this.chipkaarts.remove(chipkaart);
    }

    public String toString() {
        return "productnummer: " + productNummer + " naam: " + naam + " beschrijving: " + beschrijving + " prijs: " + prijs + ", aatal ovchipkaarten: " + chipkaarts.size();
    }
}
