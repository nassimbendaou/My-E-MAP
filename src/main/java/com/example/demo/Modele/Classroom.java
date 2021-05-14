package com.example.demo.Modele;

import java.util.List;

public class Classroom {
    private String id;
    private String name;
    private int places;
    private List<Double> latitude;
    private List<Double> longitude;
    private int id_establishment;



    private boolean projector;// True for Yes, False for NO
    private int rj45_outlets;
    private int elec_outlets;
    private boolean board;// True for felt pen False for chalk
    private String equipments;
    private int floor;

    public List<Double> getLatitude() {
        return latitude;
    }

    public void setLatitude(List<Double> latitude) {
        this.latitude = latitude;
    }

    public List<Double> getLongitude() {
        return longitude;
    }

    public void setLongitude(List<Double> longitude) {
        this.longitude = longitude;
    }
    public int getId_establishment() {
        return id_establishment;
    }

    public void setId_establishment(int id_establishment) {
        this.id_establishment = id_establishment;
    }

    public int getFloor() {
        return floor;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }
    public Classroom (){
        id=null;
        places=0;
        projector=false;
        rj45_outlets=0;
        elec_outlets=0;
        board=false;
        equipments=null;
        floor=0;
        name=null;
    }
    public  Classroom(String id,String name, int places, boolean projector, int rj45_outlets, int elec_outlets,int floor ,boolean board, String equipments,List lat,List lon){
        this.id=id;
        this.latitude=lat;
        this.longitude=lon;
        this.name=name;
        this.places=places;
        this.projector=projector;
        this.rj45_outlets=rj45_outlets;
        this.elec_outlets=elec_outlets;
        this.board=board;
        this.floor=floor;
        this.equipments=equipments;
    }
    public String getId() {
        return id;
    }
    public int getPlaces() {
        return places;
    }
    public boolean isProjector() {
        return projector;
    }
    public int getRj45_outlets() {
        return rj45_outlets;
    }
    public int getElec_outlets() {
        return elec_outlets;
    }
    public boolean isBoard() {
        return board;
    }
    public String getEquipments() {
        return equipments;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setPlaces(int places) {
        this.places = places;
    }
    public void setProjector(boolean projector) {
        this.projector = projector;
    }
    public void setRj45_outlets(int rj45_outlets) {
        this.rj45_outlets = rj45_outlets;
    }
    public void setElec_outlets(int elec_outlets) {
        this.elec_outlets = elec_outlets;
    }
    public void setBoard(boolean board) {
        this.board = board;
    }
    public void setEquipments(String equipments) {
        this.equipments = equipments;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void Modify(Classroom classroom){
        this.id=classroom.getId();
        this.name=classroom.getName();
        this.places=classroom.getPlaces();
        this.projector=classroom.isProjector();
        this.rj45_outlets=classroom.getRj45_outlets();
        this.elec_outlets=classroom.getElec_outlets();
        this.board=classroom.isBoard();
        this.equipments=classroom.getEquipments();
    }
}
