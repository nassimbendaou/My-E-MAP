package com.example.demo.DAO;


import com.example.demo.Modele.Block;
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

@Service("Establishment")

public class EstablishmentS {
    static String db_name = "USMBA", db_collection = "Establishment";
    //private static MongoFactory mongoFactory;

    //private    MongoFactory mongoFactory = new MongoFactory();
   // StructureS structureS = new StructureS();
    // Fetch all users from the mongo database.
    public List getAll()  {
        List E_list = new ArrayList();
        //SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

        // Fetching cursor object for iterating on the database records.
        DBCursor cursor = coll.find();

        while(cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            System.out.println("phase1");
            Establishment E = new Establishment();
            E.setId(Integer.parseInt(dbObject.get("id").toString()));
            E.setName(dbObject.get("name").toString());
            E.setDateC(dbObject.get("dateC").toString());

            E.setLatitude(Float.parseFloat(dbObject.get("latitude").toString()));
            System.out.println(Double.parseDouble(dbObject.get("latitude").toString()));
            E.setLongitude(Float.parseFloat(dbObject.get("longitude").toString()));

            System.out.println(Double.parseDouble(dbObject.get("longitude").toString()));
            E.setStructures(StructureS.getAll(E));

            //E.setBlocks(blocksS.getall(E));
            // Adding the user details to the list.
            E_list.add(E);
            System.out.println("phase4");
        }

        return E_list;
    }
    // Fetching a particular record from the mongo database.
    private DBObject getDBObject(int id) {
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        DBObject where_query = new BasicDBObject();
        where_query.put("id", id);
        return coll.findOne(where_query);
    }
    public Boolean add(Establishment E) {
        boolean output = false;


        try {
            String id=null;
            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
            DBCursor cursor=coll.find().sort(new BasicDBObject("id",-1)).limit(1);
            System.out.println("1");
            while(cursor.hasNext()) {
                DBObject dbObject = cursor.next();
                id=(dbObject.get("id").toString());
                System.out.println("2");

            }

           // DBCursor cursor = coll.find(query).sort(new BasicDBObject("_id", OrderBy.DESC.getIntRepresentation())).limit(1);

            //DBCursor cursor =col.find().limit(1).sort({"$natural":"-1"})
            JSON json =new JSON();
            System.out.println("3");
            if(id==null){
                E.setId(1);
                System.out.println("3.1");
            }
            else if(id.isBlank()||id.isEmpty()){
                E.setId(1);
                System.out.println("3.2");
            }else{E.setId(Integer.parseInt(id)+1);
                System.out.println("done");}

            // Create a new object and add the new user details to this object.
            BasicDBObject doc = new BasicDBObject();
            doc.put("id", E.getId());
            doc.put("name", E.getName());
            doc.put("dateC", E.getDateC());
            doc.put("latitude", E.getLatitude());
            doc.put("longitude", E.getLongitude());
            System.out.println("V");
            // Save a new user to the mongo collection.
            coll.insert(doc);
            output = true;
        } catch (Exception e) {
            System.out.println(e);
            output = false;
                    }
        return output;
    }
    public Boolean edit(Establishment E) {
        boolean output = false;
        try {
            // Fetching the user details.
            BasicDBObject existing = (BasicDBObject) getDBObject(E.getId());
            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
            // Create a new object and assign the updated details.
            //Establishment E = findEstablishment(e.getId());
            BasicDBObject edited = new BasicDBObject();
            edited.put("id", E.getId());
            edited.put("name", E.getName());
            edited.put("dateC", E.getDateC());
            edited.put("latitude", E.getLatitude());
            edited.put("logitude", E.getLongitude());
            // Update the existing user to the mongo database.
            coll.update(existing, edited);
            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }
    public Structure getStructure(Establishment E,int id){
        List<Structure> list_S= E.getStructures();
        for ( Structure S: list_S) {
            if(S.getId()==id){
                return S;
            }

        }
        return null;
    }
    public Block getBlock(Establishment E,String id){
        List<Block> list_B= E.getBlocks();
        for ( Block B: list_B) {
            if(B.getId().equals(id)){
                return B;
            }
        }
        return null;
    }
    // Fetching a single user details from the mongo database.
    public static Establishment findEstablishment(int id)  {
       // SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        Establishment E = new Establishment();
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        // Fetching the record object from the mongo database.
        //DBObject where_query = new BasicDBObject();
        DBObject where_query = new BasicDBObject("id", id);
        //where_query.put("id", Integer.toString(id));
        DBObject dbo = coll.findOne(where_query);
        System.out.println("query done");
        E.setId(Integer.parseInt(dbo.get("id").toString()));
        E.setName(dbo.get("name").toString());
        E.setDateC( dbo.get("dateC").toString());
        E.setLatitude(Float.parseFloat(dbo.get("latitude").toString()));
        E.setLongitude(Float.parseFloat(dbo.get("longitude").toString()));
        // Return user object.
        return E;
    }
    public Boolean delete(int id) {
        boolean output = false;
        try {
            // Fetching the required user from the mongo database.
            BasicDBObject item = (BasicDBObject) getDBObject(id);
            System.out.println(id);
            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
            // Deleting the selected user from the mongo database.
            coll.remove(item);
            System.out.println("done");
            output = true;
        } catch (Exception e) {
            System.out.println(e);
            output = false;
        }
        return output;
    }
}
