package com.example.demo.DAO;

import com.mongodb.*;

@SuppressWarnings("deprecation")
public class MongoFactory {
    private static Mongo mongo;
    public MongoFactory() { }
    // Returns a mongo instance.
    public static Mongo getMongo() {

        if (mongo == null) {
            try {
                mongo = new MongoClient(
                        new MongoClientURI("mongodb+srv://nassim:1611ben@pfe-wnq5p.mongodb.net/test"));
            } catch (MongoException ex) {
                System.out.println(ex);
            }
        }
        return mongo;
    }

    // Fetches the mongo database.
    public static DB getDB(String db_name) {
        return getMongo().getDB(db_name);
    }

    // Fetches the collection from the mongo database.
    public static DBCollection getCollection(String db_name, String db_collection) {
        return getDB(db_name).getCollection(db_collection);
    }

}
