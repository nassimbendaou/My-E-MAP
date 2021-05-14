package com.example.demo.DAO;

import com.example.demo.Modele.Block;
import com.example.demo.Modele.Establishment;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockS {
    static String db_name = "USMBA", db_collection = "Blocks";
    MongoFactory mongoFactory = new MongoFactory();

    // Fetch all users from the mongo database.
    public List getAll() {
        List B_list = new ArrayList();
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        // Fetching cursor object for iterating on the database records.
        DBCursor cursor = coll.find();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            Block B = new Block();
            B.setId(dbObject.get("id").toString());
            B.setName(dbObject.get("name").toString());
            B.setsize(Integer.parseInt(dbObject.get("size").toString()));
            System.out.println("size");
            System.out.println(dbObject.get("size").toString());

            B.setEstablishment(Integer.parseInt(dbObject.get("id_establishment").toString()));
            // Adding the user details to the list.
            B_list.add(B);
        }
        return B_list;
    }

    public List getAll_B(int E) {
        //System.out.println("1");
        List B_list = new ArrayList();
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        DBObject where_query = new BasicDBObject("id_establishment", Integer.toString(E));
        DBCursor cursor = coll.find(where_query);
        //where_query.put("id", Integer.toString(id));

      //  System.out.println("done");
        System.out.println(cursor);
        while (cursor.hasNext()) {
            System.out.println("search");
            DBObject dbObject = cursor.next();
            Block B = new Block();
            B.setId(dbObject.get("id").toString());
            B.setName(dbObject.get("name").toString());
            B.setsize(Integer.parseInt(dbObject.get("size").toString()));
            System.out.println(dbObject.get("name").toString());
            B.setLatitude(Float.parseFloat(dbObject.get("latitude").toString()));
            B.setLongitude(Float.parseFloat(dbObject.get("longitude").toString()));
            B.setEstablishment(E);
            // Adding the user details to the list.
            B_list.add(B);
        }

        return B_list;
    }

    // Fetching a particular record from the mongo database.
    private DBObject getDBObject(String id) {
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        // Fetching the record object from the mongo database.
        DBObject where_query = new BasicDBObject();
        // Put the selected user_id to search.
        where_query.put("id", id);
        return coll.findOne(where_query);
    }

    public Boolean add(Block B, int id_e) {
        boolean output = false;
        Establishment E = EstablishmentS.findEstablishment(id_e);
        try {
            String id = null;
            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
            // DBCursor cursor =coll.find().sort((DBObject) new Document("id", -1)).limit(1);
            //JSON json =new JSON();
            // String lastid = json.serialize(cursor);
            //Integer.parseInt(lastid)
            // Create a new object and add the new user details to this object.
            DBCursor cursor = coll.find().sort(new BasicDBObject("id", -1)).limit(1);
            System.out.println("1");
            while (cursor.hasNext()) {
                DBObject dbObject = cursor.next();
                id = (dbObject.get("id").toString());
                System.out.println("2");
            }
            // DBCursor cursor = coll.find(query).sort(new BasicDBObject("_id", OrderBy.DESC.getIntRepresentation())).limit(1);
            //DBCursor cursor =col.find().limit(1).sort({"$natural":"-1"})
            JSON json = new JSON();
            System.out.println("3");
            if (id == null) {
                B.setId("1");
                System.out.println("3.1");
            } else if (id.isBlank() || id.isEmpty()) {
                B.setId("1");
                System.out.println("3.2");
            } else {
                B.setId(Integer.toString(Integer.parseInt(id) + 1));
                System.out.println("done");
            }
            BasicDBObject doc = new BasicDBObject();
            doc.put("name", B.getName());
            doc.put("id_establishement", B.getEstablishment());
            doc.put("id", B.getId());
            doc.put("latitude", B.getLatitude());
            doc.put("longitude", B.getLongitude());
            // Save a new user to the mongo collection.
            coll.insert(doc);
            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    public Boolean edit(Block B) {
        boolean output = false;
        try {
            // Fetching the user details.
            BasicDBObject existing = (BasicDBObject) getDBObject(B.getId());
            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
            // Create a new object and assign the updated details.
            BasicDBObject edited = new BasicDBObject();
            edited.put("name", B.getName());
            edited.put("latitude", B.getLatitude());
            edited.put("longitude", B.getLongitude());
            // Update the existing user to the mongo database.
            coll.update(existing, edited);
            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    public static Block findBlock(String id) {
        Block B = new Block();
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        // Fetching the record object from the mongo database.
        /*      String id = null;
            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
            // DBCursor cursor =coll.find().sort((DBObject) new Document("id", -1)).limit(1);
            //JSON json =new JSON();
            // String lastid = json.serialize(cursor);
            //Integer.parseInt(lastid)
            // Create a new object and add the new user details to this object.
            DBCursor cursor = coll.find().sort(new BasicDBObject("id", -1)).limit(1);
            System.out.println("1");
            while (cursor.hasNext()) {
                DBObject dbObject = cursor.next();
                id = (dbObject.get("id").toString());
                System.out.println("2");
            }
            // DBCursor cursor = coll.find(query).sort(new BasicDBObject("_id", OrderBy.DESC.getIntRepresentation())).limit(1);
            //DBCursor cursor =col.find().limit(1).sort({"$natural":"-1"})
            JSON json = new JSON();
            System.out.println("3");
            if (id == null) {
                B.setId("1");
                System.out.println("3.1");
            } else if (id.isBlank() || id.isEmpty()) {
                B.setId("1");
                System.out.println("3.2");
            } else {
                B.setId(Integer.toString(Integer.parseInt(id) + 1));
                System.out.println("done");
            }*/
        DBObject where_query = new BasicDBObject();
        where_query.put("id", id);
        DBObject dbo = coll.findOne(where_query);
        B.setId(dbo.get("id").toString());
        B.setEstablishment(Integer.parseInt(dbo.get("id_establishement").toString()));
        B.setName(dbo.get("name").toString());
        B.setLatitude(Float.parseFloat(dbo.get("latitude").toString()));
        B.setLongitude(Float.parseFloat(dbo.get("longitude").toString()));
        // Return user object.
        return B;
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
