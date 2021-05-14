package com.example.demo.DAO;

import com.example.demo.Modele.Classroom;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Service
public class ClassroomS {
    static String db_name = "USMBA", db_collection = "Classroom";

    //MongoFactory mongoFactory = new MongoFactory();

    public List getAll() {
        List C_list = new ArrayList();
        System.out.println("1");
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        System.out.println("3");
        // Fetching cursor object for iterating on the database records.
        DBCursor cursor = coll.find();
        System.out.println("2");
        List lat = new ArrayList<>();
        List lon = new ArrayList<>();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            Classroom c = new Classroom();
            System.out.println("d");
            c.setFloor(Integer.parseInt(dbObject.get("id_floor").toString()));
            c.setId(dbObject.get("id").toString());
            c.setName(dbObject.get("name").toString());
            c.setProjector(Boolean.parseBoolean(dbObject.get("projector").toString()));
            c.setBoard(Boolean.parseBoolean(dbObject.get("board").toString()));
            c.setRj45_outlets(Integer.parseInt(dbObject.get("rj45_outlets").toString()));
            c.setElec_outlets(Integer.parseInt(dbObject.get("elec_outlets").toString()));
            c.setEquipments(dbObject.get("equipments").toString());
            c.setId_establishment(Integer.parseInt(dbObject.get("id_establishment").toString()));
            lat= (List) dbObject.get("latitude");
            lon= (List) dbObject.get("longitude");
            List<Double> coordslat=new Vector<>();
            List<Double> coordslon=new Vector<>();
            for (Object s: lat) {
                coordslat.add(Double.parseDouble(s.toString()));
            }
            for (Object s: lon) {
                coordslon.add(Double.parseDouble(s.toString()));
            }

            System.out.println(coordslat.toArray()[0].getClass());
            c.setLatitude( coordslat);
            c.setLongitude( coordslon);
            //System.out.println(c.getCoord());
            c.setPlaces(Integer.parseInt(dbObject.get("nbplace").toString()));
            C_list.add(c);
        }

        return C_list;
    }
    public  int getNumberOfClassesByBlock(int id){
        int size=0;
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        DBObject where_query = new BasicDBObject();
        where_query.put("id_block", Integer.toString(id));
        DBCursor cursor = coll.find(where_query);
        while (cursor.hasNext()) {
                size++;
        }
        return size;
    }
    public List<?> getAll_C(int id) {
        List<Classroom> C_list = new ArrayList<>();
        //SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        // Fetching the record object from the mongo database.
        DBObject where_query = new BasicDBObject();

        where_query.put("id_floor", Integer.toString(id));
        DBCursor cursor = coll.find(where_query);
        List lat = new ArrayList<>();
        List lon = new ArrayList<>();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            Classroom c = new Classroom();
            System.out.println("d");
            c.setId(dbObject.get("id").toString());
            c.setName(dbObject.get("name").toString());
            c.setProjector(Boolean.parseBoolean(dbObject.get("projector").toString()));
            c.setBoard(Boolean.parseBoolean(dbObject.get("board").toString()));
            c.setRj45_outlets(Integer.parseInt(dbObject.get("rj45_outlets").toString()));
            c.setElec_outlets(Integer.parseInt(dbObject.get("elec_outlets").toString()));
            c.setEquipments(dbObject.get("equipments").toString());
            c.setId_establishment(Integer.parseInt(dbObject.get("id_establishment").toString()));
            lat= (List) dbObject.get("latitude");
            lon= (List) dbObject.get("longitude");
            List<Double> coordslat=new Vector<>();
            List<Double> coordslon=new Vector<>();
            for (Object s: lat) {
                coordslat.add(Double.parseDouble(s.toString()));
            }
            for (Object s: lon) {
                coordslon.add(Double.parseDouble(s.toString()));
            }

            System.out.println(coordslat.toArray()[0].getClass());
            c.setLatitude( coordslat);
            c.setLongitude( coordslon);
            //System.out.println(c.getCoord());
            c.setPlaces(Integer.parseInt(dbObject.get("nbplace").toString()));
            C_list.add(c);
        }
        return C_list;
    }

    private DBObject getDBObject(String id) {
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        // Fetching the record object from the mongo database.
        DBObject where_query = new BasicDBObject();
        // Put the selected user_id to search.
        where_query.put("id", id);
        System.out.println("2");
        return coll.findOne(where_query);
    }



    public Boolean edit(Classroom C) {
        boolean output = false;
        try {
            // Fetching the user details.
            BasicDBObject existing = (BasicDBObject) getDBObject(C.getId());
            System.out.println("2");
            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
            // Create a new object and assign the updated details.
            BasicDBObject doc = new BasicDBObject();
            // Update the existing user to the mongo database.
            doc.put("id", C.getId());
            doc.put("name", C.getName());
            doc.put("places", C.getPlaces());
            doc.put("projector", C.isProjector());
            doc.put("rj45_outlets", C.getRj45_outlets());
            doc.put("elec_outlets", C.getElec_outlets());
            doc.put("board", C.isBoard());
            doc.put("equipments", C.getEquipments());
            coll.update(existing, doc);
            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    public static Classroom findClassroom(String id) {
        Classroom C = new Classroom();
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        // Fetching the record object from the mongo database.
        DBObject where_query = new BasicDBObject();
        where_query.put("id", id);
        DBObject dbo = coll.findOne(where_query);
        C.setId(dbo.get("id").toString());
        // Return user object.
        return C;
    }

    public Boolean delete(String id) {
        boolean output = false;
        try {
            // Fetching the required user from the mongo database.
            BasicDBObject item = (BasicDBObject) getDBObject(id);
            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
            // Deleting the selected user from the mongo database.
            coll.remove(item);
            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }
}
