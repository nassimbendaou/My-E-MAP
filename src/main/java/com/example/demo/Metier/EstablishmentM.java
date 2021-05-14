package com.example.demo.Metier;

import com.example.demo.DAO.BlockS;
import com.example.demo.DAO.EstablishmentDaoMemory;
import com.example.demo.DAO.EstablishmentS;
import com.example.demo.DAO.StructureS;
import com.example.demo.Modele.Establishment;
import com.example.demo.Modele.Structure;

import java.util.List;


public class EstablishmentM {
    private EstablishmentDaoMemory establishmentDaoMemory = EstablishmentDaoMemory.getInstance();
    BlockS blockS = new BlockS();
   // EstablishmentS establishmentS = new EstablishmentS();
    public Establishment searchByCoordinate(Establishment E) {
        return establishmentDaoMemory.getEstablismentbyCoordinate(E.getLatitude(), E.getLongitude());
    }
    public Establishment findEstablishment(int id){
        return establishmentDaoMemory.findEstablishment(id);
    }
    public List<Establishment> getAll() {
        List<Establishment> ests =establishmentDaoMemory.getEstablishments();
       for(Establishment e:ests){
           e.setStructures(StructureS.getAll(e));
           e.setBlocks(blockS.getAll_B(e.getId()));
       }
        return ests;
    }

    public Structure searchForStructure(Establishment E, int id) {
        for (Structure s : E.getStructures()
        ) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public void addEstablishment(Establishment E) {
        establishmentDaoMemory.addEstablishment(E);
    }

    public void updateEstablishement(Establishment old, String name, String dateC, Float lat, Float lon) {
        old.setDateC(dateC);
        old.setName(name);
        old.setLatitude(lat);
        old.setLongitude(lon);
        establishmentDaoMemory.updateEstablishment(old);

    }

    public void removeAllStructures(Establishment e) {

        e.getStructures().clear();
    }

    public void removeAllBlocks(Establishment e) {
        e.getBlocks().clear();
    }

    public void deleteEstablishement(Establishment e) {
        List<Establishment> establishments = establishmentDaoMemory.getEstablishments();

        establishmentDaoMemory.deleteEstablishment(e.getId());
        establishments.remove(e);

    }


}
