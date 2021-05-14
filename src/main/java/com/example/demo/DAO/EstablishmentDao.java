package com.example.demo.DAO;

import com.example.demo.Modele.Block;
import com.example.demo.Modele.Establishment;
import com.example.demo.Modele.Structure;

import java.util.List;

public interface EstablishmentDao {
     void addEstablishment(Establishment establishment);

     void getAll();

     void deleteEstablishment(int id);

     void updateEstablishment(Establishment id);

     Establishment findEstablishment(int id);

     Establishment getEstablismentbyCoordinate(Float lat, Float lon);


}
