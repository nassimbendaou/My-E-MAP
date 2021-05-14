package com.example.demo.Modele;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Floor {
    private String id;
    private int num;
    private List<Classroom> classrooms;
    private Block block;
    public Block getBlock() {
        return block;
    }
    public void setBlock(Block block) {
        this.block = block;
    }
    public String getId() {
        return id;
    }
    public List<Classroom> getClassrooms() {
        return classrooms;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }
    public Floor(){
        id=null;
        classrooms=null;
        block=null;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public Floor(String id, int num, List<Classroom> classrooms, Block block){
        this.id=id;
        this.num=num;
        this.classrooms=classrooms;
        this.block=block;
    }
    public void Modify(@NotNull Floor floor){
        this.id=getId();
        this.num=floor.getNum();
        this.classrooms=floor.getClassrooms();
    }
    public void AddClass (@NotNull Classroom classroom){
        this.classrooms.add(classroom);
    }
    public void DeleteClass (@NotNull Classroom classroom){
        this.classrooms.remove(classroom);
    }
    public Classroom getClassroomById(@NotNull String id){
        for (Classroom c: classrooms) {
            if(c.getId()==id)
                return c;
        }
        return null;
    }
}
