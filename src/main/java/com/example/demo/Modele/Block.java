package com.example.demo.Modele;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

@Component
public class Block {
    private String id;
    private String name;
    private List<Floor> floors;
    private int establishment;
    private int size;
    public int getEstablishment() {
        return establishment;
    }
    public void setEstablishment(int establishment) {
        this.establishment = establishment;
    }
    Float Latitude;
    Float Longitude;
    public String getId() {
        return id;
    }
    public List<Floor> getFloors() {
        return floors;
    }
    public Float getLatitude() {
        return Latitude;
    }
    public Float getLongitude() {
        return Longitude;
    }
    public int getsize() {
        return size;
    }
    public void setsize(int s) {
        this.size = s;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }
    public void setLatitude(Float latitude) {
        Latitude = latitude;
    }
    public void setLongitude(Float longitude) {
        Longitude = longitude;
    }
    public Block(){
        id=null;
        floors=null;
        //establishment=null;
    }
    public Block(String id,String name, List<Floor> floors,int establishment){
        this.id=id;
        this.floors=floors;
        this.establishment=establishment;
        this.name=name;
    }
    public void AddFloor(Floor floor){
        floors.add(floor);
    }
    public void DeleteFloor( Floor floor){
        floors.remove(floor);
    }

}
