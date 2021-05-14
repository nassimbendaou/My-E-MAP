package com.example.demo.Metier;

import com.example.demo.DAO.BlockS;
import com.example.demo.DAO.ClassroomS;
import com.example.demo.DAO.FloorS;
import com.example.demo.Modele.Block;
import com.example.demo.Modele.Classroom;
import com.example.demo.Modele.Establishment;
import com.example.demo.Modele.Floor;

import java.util.Vector;

public class FloorM {
    FloorS floorS = new FloorS();
    ClassroomS classroomS = new ClassroomS();

    public Floor findFloor(String id) {
        return FloorS.findFloor(id);
    }

    public void addFloor(Block b, Floor f) {
        floorS.add(f, Integer.parseInt(b.getId()));
        b.AddFloor(f);

    }

    public Classroom searchforClassroom(Floor floor, int id) {
        for (Classroom c : floor.getClassrooms()) {
            if (c.getId().equals(Integer.toString(id))) {
                return c;
            }
        }
        return null;
    }

    public void deleteClass(Classroom classroom, Floor F) {
        F.DeleteClass(classroom);
        classroomS.delete(classroom.getId());
    }


}
