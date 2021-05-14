package com.example.demo.DAO;

import com.example.demo.Modele.Block;
import com.example.demo.Modele.Establishment;
import com.example.demo.Modele.Structure;

import java.util.ArrayList;
import java.util.List;

public class EstablishmentDaoMemory implements EstablishmentDao {
    private static EstablishmentDaoMemory establishmentDaoMemory;
    private static List<Establishment> establishments = new ArrayList<Establishment>();

    private EstablishmentDaoMemory() {
        getAll();
    }

    public static EstablishmentDaoMemory getInstance() {
        if (establishmentDaoMemory == null) {
            establishmentDaoMemory = new EstablishmentDaoMemory();
        }
        return establishmentDaoMemory;
    }

    private BlockS blockS = new BlockS();
    private StructureS structureS = new StructureS();
    private EstablishmentS E_service = new EstablishmentS();

    public List<Establishment> getEstablishments(){return establishments;}
    @Override
    public void addEstablishment(Establishment establishment) {
        E_service.add(establishment);
        establishments.add(establishment);
    }

    @Override
    public void getAll() {
        establishments = E_service.getAll();
        for (Establishment e : establishments
        ) {
            e.setStructures(StructureS.getAll(e));
            blockS.getAll_B(e.getId());
        }
    }

    @Override
    public void deleteEstablishment(int id) {
        E_service.delete(id);
    }

    @Override
    public void updateEstablishment(Establishment id) {
        E_service.edit(id);
    }

    @Override
    public Establishment findEstablishment(int id) {
        Establishment e = EstablishmentS.findEstablishment(id);
        e.setStructures(StructureS.getAll(e));
        e.setBlocks(blockS.getAll_B(e.getId()));

        return e;
    }

    @Override
    public Establishment getEstablismentbyCoordinate(Float lat, Float lon) {
        for (Establishment e : establishments
        ) {
            if (e.getLatitude().equals(lat) && e.getLongitude().equals(lon)) {
                return e;
            }
        }
        return null;
    }

}
