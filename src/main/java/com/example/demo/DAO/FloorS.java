package com.example.demo.DAO;

import com.example.demo.Modele.Block;
import com.example.demo.Modele.Classroom;
import com.example.demo.Modele.Floor;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FloorS {
    static String db_name = "USMBA", db_collection = "Floor";

    public List getAll() {
        List F_list = new ArrayList();
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        //BlockS blockS = new BlockS();
        // Fetching cursor object for iterating on the database records.
        DBCursor cursor = coll.find();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();

            Floor F = new Floor();
            F.setId(dbObject.get("id").toString());
            F.setNum(Integer.parseInt(dbObject.get("num").toString()));
            F.setBlock(BlockS.findBlock(dbObject.get("id_Block").toString()));
            F_list.add(F);

        }

        return F_list;
    }

    public static List<Floor> getAll_F(Block B) {
        List<Floor> F_list = new ArrayList<>();
        //SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        // Fetching the record object from the mongo database.
        //DBObject where_query = new BasicDBObject();
        DBObject where_query = new BasicDBObject("id_block", B.getId());
        //where_query.put("id", Integer.toString(id));
        DBCursor cursor = coll.find(where_query);

        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            Floor F = new Floor();
            F.setId(dbObject.get("id").toString());
            F.setNum(Integer.parseInt(dbObject.get("num").toString()));
            //F.setClassrooms((List<Classroom>) ClassroomS.getAll_C(Integer.parseInt(F.getId())));
            F.setBlock(B);
            F_list.add(F);
            System.out.println(F.getId());
        }
        return F_list;

    }


    private DBObject getDBObject(String id) {
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        // Fetching the record object from the mongo database.
        DBObject where_query = new BasicDBObject();
        // Put the selected user_id to search.
        where_query.put("id", id);
        return coll.findOne(where_query);
    }

    public Boolean add(Floor F, int id_B) {
        boolean output = false;
        //Block block = BlockS.findBlock(Integer.toString(id_B));
        try {
            String id = null;
            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
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
                F.setId("1");
                System.out.println("3.1");
            } else if (id.isBlank() || id.isEmpty()) {
                F.setId("1");
                System.out.println("3.2");
            } else {
                F.setId(Integer.toString(Integer.parseInt(id) + 1));
                System.out.println("done");
            }
            // Create a new object and add the new user details to this object.
            BasicDBObject doc = new BasicDBObject();
            doc.put("id", F.getId());
            doc.put("num", F.getNum());
            // doc.put("id_block",block.getId());
            doc.put("id_Block", id_B);
            // Save a new user to the mongo collection.
            coll.insert(doc);
            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    public Boolean edit(Floor F) {
        boolean output = false;
        try {
            // Fetching the user details.
            BasicDBObject existing = (BasicDBObject) getDBObject(F.getId());
            DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
            // Create a new object and assign the updated details.
            BasicDBObject edited = new BasicDBObject();
            // Update the existing user to the mongo database.
            coll.update(existing, edited);
            output = true;
        } catch (Exception e) {
            output = false;
        }
        return output;
    }

    public static Floor findFloor(String id) {
        Floor F = new Floor();
        DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
        // Fetching the record object from the mongo database.
        DBObject where_query = new BasicDBObject();
        where_query.put("id", id);
        DBObject dbo = coll.findOne(where_query);
        F.setId(dbo.get("id").toString());
        F.setNum(Integer.parseInt(dbo.get("num").toString()));
        F.setBlock(BlockS.findBlock(dbo.get("id_block").toString()));
        // Return user object.
        return F;
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
