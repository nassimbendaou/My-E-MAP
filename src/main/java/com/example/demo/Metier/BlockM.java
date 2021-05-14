package com.example.demo.Metier;

import com.example.demo.DAO.BlockS;
import com.example.demo.DAO.FloorS;
import com.example.demo.Modele.Block;
import com.example.demo.Modele.Establishment;
import com.example.demo.Modele.Floor;

import java.util.Vector;

public class BlockM {
    BlockS blockS = new BlockS();
    FloorS floorS=new FloorS();
    public Block searchByCoordinate(Float lat, Float lon, Establishment e){
        Vector<Block> blocks= (Vector<Block>) e.getBlocks();
        for (Block b:blocks
             ) {
            if (b.getLongitude().equals(lon)&&b.getLatitude().equals(lat)){
                return b;
            }
        }
        return null;
    }
    public Block findBlock(String id){
        Block b=BlockS.findBlock(id);
        b.setFloors(floorS.getAll_F(b));
       return b;
    }
    public void addBlock(Establishment E,Block b){
        blockS.add(b,E.getId());
        E.addBlock(b);

    }
    public Floor searchforFloor(Block block,int num){
        for (Floor f:block.getFloors()) {
            if(f.getId().equals(Integer.toString(num))){
                return f;
            }
        }
        return null;
    }
    public void deleteFloor(Block block , Floor F){
       block.DeleteFloor(F); ;
       blockS.delete(F.getId());
    }
    public void editBlock(Block old, String name, String dateC, Float lat, Float lon){
        old.setName(name);
        old.setLatitude(lat);
        old.setLongitude(lon);
        blockS.edit(old);

    }


}
