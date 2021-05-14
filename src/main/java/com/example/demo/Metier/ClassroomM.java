package com.example.demo.Metier;

import com.example.demo.DAO.BlockS;
import com.example.demo.DAO.ClassroomS;
import com.example.demo.DAO.FloorS;
import com.example.demo.Modele.Block;
import com.example.demo.Modele.Classroom;
import com.example.demo.Modele.Establishment;
import com.example.demo.Modele.Floor;

import java.util.Vector;

public class ClassroomM {
    ClassroomS classroomS= new ClassroomS();
  //  FloorS floorS=new FloorS();
    public Classroom findClassromm(String id){
        return ClassroomS.findClassroom(id);
    }
    public void addClassrom(Floor F,Classroom c){
       
        F.AddClass(c);

    }
    public void editClass(Classroom old, String name, boolean isboard, boolean isprojector, int outils, String Eq, int places, int rj) {
        old.setName(name);
        old.setBoard(isboard);
        old.setProjector(isprojector);
        old.setElec_outlets(outils);
        old.setEquipments(Eq);
        old.setPlaces(places);
        old.setRj45_outlets(rj);
        classroomS.edit(old);
    }


}
