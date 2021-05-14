package com.example.demo.Modele;
import org.springframework.stereotype.Component;


@Component
public class Structure {

    private int id;
    private String name;
    private String Description;
    private int Nbplace;
    private String H_Begin,H_End;
    int establishment;
    Float latitude;
    Float longitude;

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    private boolean A_internet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getH_Begin() {
        return H_Begin;
    }

    public void setH_Begin(String h_Begin) {
        H_Begin = h_Begin;
    }

    public String getH_End() {
        return H_End;
    }

    public void setH_End(String h_End) {
        H_End = h_End;
    }

    public int getEstablishment() {
        return establishment;
    }

    public void setEstablishment(int establishment) {
        this.establishment = establishment;
    }

    public Structure(String nom, String description, int nbplace, String h_Debut, String h_Fin, boolean a_internet,Float lat,Float lon, int E) {

        this.name = nom;
        Description = description;
        Nbplace = nbplace;
        H_Begin = h_Debut;
        H_End = h_Fin;
        A_internet = a_internet;
        this.latitude=lat;
        this.longitude=lon;
        this.establishment=E;
    }
    public Structure(){}
    public int getId() {
        return id;
    }

    public String getDescription() {
        return Description;
    }

    public int getNbplace() {
        return Nbplace;
    }

    public boolean isA_internet() {
        return A_internet;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.name = nom;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setNbplace(int nbplace) {
        Nbplace = nbplace;
    }

    public void setA_internet(boolean a_internet) {
        A_internet = a_internet;
    }

    public void modifier(Structure structure){

        name=structure.getName();
        Description=structure.getDescription();
        A_internet=structure.isA_internet();
        Nbplace=structure.getNbplace();
        H_Begin=structure.getH_Begin();
        H_End=structure.getH_End();
    }
    public void ajouter(String nom, int nbplace, String description, String H_debut, String H_fin, boolean a_internet, Float lat, Float lon, int E){
        new Structure(nom,description,nbplace,H_debut,H_fin,a_internet,lat,lon,E);
    }

}
