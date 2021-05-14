package com.example.demo.DAO;


import com.example.demo.Modele.Establishment;
import com.example.demo.Modele.Structure;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("StructureService")
public class StructureS {
    static String db_name = "USMBA", db_collection = "Structures";

    //EstablishmentS E_S = new EstablishmentS();
    MongoFactory mongoFactory = new MongoFactory();

    public static List<Structure> getAll(Establishment E) {
        List<Structure> S_list = new ArrayList<>();
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        DBObject where_query = new BasicDBObject("id_establishment", Integer.toString(E.getId()));
        DBCursor cursor = coll.find(where_query);
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            Structure S = new Structure();
            S.setId(Integer.parseInt(dbObject.get("id").toString()));
            S.setName(dbObject.get("name").toString());
            S.setDescription(dbObject.get("description").toString());
            S.setEstablishment(E.getId());
            S.setNbplace(Integer.parseInt(dbObject.get("nbplace").toString()));
            System.out.println("nbolace");
            S.setH_Begin(dbObject.get("date_begin").toString());
            S.setEstablishment(Integer.parseInt(dbObject.get("id_establishment").toString()));
            S.setH_End(dbObject.get("date_end").toString());
            S.setA_internet(Boolean.parseBoolean(dbObject.get("a_internet").toString()));
            S.setLatitude(Float.parseFloat(dbObject.get("latitude").toString()));
            S.setLongitude(Float.parseFloat(dbObject.get("longitude").toString()));
            S_list.add(S);
            System.out.println(S.getId());
        }
        return S_list;
    }

    public List<Structure> getAll_S() {
        List<Structure> S_list = new ArrayList<>();
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        DBCursor cursor = coll.find();
        System.out.println("p2");
        while (cursor.hasNext()) {
            System.out.println("p3");
            DBObject dbObject = cursor.next();
            System.out.println("p4");
            Structure S = new Structure();
            S.setId(Integer.parseInt(dbObject.get("id").toString()));
            S.setName(dbObject.get("name").toString());
            S.setDescription(dbObject.get("description").toString());
            S.setEstablishment(Integer.parseInt(dbObject.get("id_establishment").toString()));
            S.setH_Begin(dbObject.get("date_begin").toString());
            S.setH_End(dbObject.get("date_end").toString());
            S.setA_internet(Boolean.parseBoolean(dbObject.get("a_internet").toString()));
            S.setLatitude(Float.parseFloat(dbObject.get("latitude").toString()));
            S.setLongitude(Float.parseFloat(dbObject.get("longitude").toString()));
            // Adding the structure details to the list.
            S_list.add(S);
        }
        return S_list;


    }

    private DBObject getDBObject(int id) {
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        // Fetching the record object from the mongo database.
        DBObject where_query = new BasicDBObject();
        // Put the selected user_id to search.
        where_query.put("id", id);
        return coll.findOne(where_query);
    }

    public void add(Structure S, int id_e) {
        // boolean output = false;
        Establishment E = EstablishmentS.findEstablishment(id_e);
        try {

            String id = null;
            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
            DBCursor cursor = coll.find().sort(new BasicDBObject("id", -1)).limit(1);
            System.out.println("1");
            while (cursor.hasNext()) {
                DBObject dbObject = cursor.next();
                id = (dbObject.get("id").toString());
                System.out.println("2");
            }
            JSON json = new JSON();
            System.out.println("3");
            if (id == null) {
                S.setId(1);
                System.out.println("3.1");
            } else if (id.isBlank() || id.isEmpty()) {
                S.setId(1);
                System.out.println("3.2");
            } else {
                S.setId(Integer.parseInt(id) + 1);
                System.out.println("done");
            }
            // Create a new object and add the new user details to this object.
            BasicDBObject doc = new BasicDBObject();
            doc.put("id", S.getId());
            doc.put("name", S.getName());
            doc.put("id_etstablishment", E.getId());
            System.out.println(E.getName());
            doc.put("date_begin", S.getH_Begin());
            doc.put("date_end", S.getH_End());
            doc.put("id_establishment", S.getEstablishment());
            doc.put("nbplace", S.getNbplace());
            doc.put("description", S.getDescription());
            doc.put("a_internet", S.isA_internet());
            doc.put("latitude", S.getLatitude());
            doc.put("longitude", S.getLongitude());
            // Save a new user to the mongo collection.
            coll.insert(doc);
            System.out.println("done");
            //output = true;
        } catch (Exception e) {
            System.out.println(e);
        }
        //return output;
    }

    public void edit(Structure S) {
        boolean output = false;
        try {
            // Fetching the user details.
            BasicDBObject existing = (BasicDBObject) getDBObject(S.getId());
            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
            // Create a new object and assign the updated details.
            BasicDBObject doc = new BasicDBObject();
            System.out.println("1");
            doc.put("id", S.getId());
            doc.put("name", S.getName());
            //doc.put("id_etstablishment", S.getEstablishment().getId());
            doc.put("date_begin", S.getH_Begin());
            doc.put("description", S.getDescription());
            doc.put("date_end", S.getH_End());
            doc.put("nbplace", S.getNbplace());
            doc.put("a_internet", S.isA_internet());
            doc.put("latitude", S.getLatitude());
            doc.put("longitude", S.getLongitude());
            System.out.println("2");
            // Update the existing user to the mongo database.
            coll.update(existing, doc);
            System.out.println("3");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public Structure findStructure(int id) {
        // SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        Structure S = new Structure();
        DBCollection coll = mongoFactory.getCollection(db_name, db_collection);
        // Fetching the record object from the mongo database.
        DBObject where_query = new BasicDBObject("id", id);
        //where_query.put("id", Integer.toString(id));
        DBObject dbo = coll.findOne(where_query);
        //DBObject dbo = coll.findOne(where_query);
        S.setId(Integer.parseInt(dbo.get("id").toString()));
        S.setName(dbo.get("name").toString());
        S.setDescription(dbo.get("description").toString());
        S.setA_internet(Boolean.parseBoolean(dbo.get("a_internet").toString()));
        S.setH_Begin(dbo.get("date_begin").toString());
        S.setH_End(dbo.get("date_end").toString());
        S.setLatitude(Float.parseFloat(dbo.get("latitude").toString()));
        S.setLongitude(Float.parseFloat(dbo.get("longitude").toString()));
        // Return user object.
        return S;
    }

    public void delete(int id) {
        //boolean output = false;
        try {
            // Fetching the required user from the mongo database.
            BasicDBObject item = (BasicDBObject) getDBObject(id);
            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
            // Deleting the selected user from the mongo database.
            coll.remove(item);
            //output = true;
        } catch (Exception e) {
            //output = false;
            System.out.println(e);
        }
        // return output;

    }


}
