package com.example.demo.Modele;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Establishment {
    @Override
    public String toString() {
        return super.toString();
    }

    int id;
    String name;
    String dateC;
    Float Latitude;
    Float Longitude;
    List<Structure> structures = new ArrayList<Structure>();
    List<Block> blocks = new ArrayList<Block>();

    public Float getLatitude() {
        return Latitude;
    }

    public void setLatitude(Float latitude) {
        Latitude = latitude;
    }

    public Float getLongitude() {
        return Longitude;
    }

    public void setLongitude(Float longitude) {
        Longitude = longitude;
    }

    public String getDateC() {
        return dateC;
    }

    public void setDateC(String dateC) {
        this.dateC = dateC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Structure> getStructures() {
        return structures;
    }

    public void setStructures(List<Structure> structures) {
        this.structures = structures;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public Establishment(String name, String dateC, Float lat, Float lon, List<Structure> structures, List<Block> blocks) {
        this.name = name;
        this.structures = structures;
        this.blocks = blocks;
        this.dateC = dateC;
        this.Latitude = lat;
        this.Longitude = lon;
    }

    public Establishment() {
    }

    public Block getBlock(String id) {
        for (Block B : blocks) {
            if (B.getId().equals(id)) {
                return B;
            }
        }
        return null;
    }

    public void addStructure(Structure S) {
        structures.add(S);
    }

    public void addBlock(Block B) {
        blocks.add(B);
    }

}
