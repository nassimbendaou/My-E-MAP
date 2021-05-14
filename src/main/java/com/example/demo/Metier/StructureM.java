package com.example.demo.Metier;

import com.example.demo.DAO.BlockS;
import com.example.demo.DAO.StructureS;
import com.example.demo.Modele.Block;
import com.example.demo.Modele.Establishment;
import com.example.demo.Modele.Floor;
import com.example.demo.Modele.Structure;

import java.util.Vector;

public class StructureM {
    StructureS structureS = new StructureS();
    public Structure searchByCoordinate(Float lat, Float lon, Establishment e){
        Vector<Structure> structures= (Vector<Structure>) e.getStructures();
        for (Structure s:structures
        ) {
            if (s.getLongitude().equals(lon)&&s.getLatitude().equals(lat)){
                return s;
            }
        }
        return null;
    }
    public Block findStructure(String id){
        return BlockS.findBlock(id);
    }
    public void addStructure(Establishment E,Structure b){
        structureS.add(b,E.getId());
        E.addStructure(b);

    }
    public Floor searchforFloor(Block block, int num){
        for (Floor f:block.getFloors()) {
            if(f.getId().equals(Integer.toString(num))){
                return f;
            }
        }
        return null;
    }

}
