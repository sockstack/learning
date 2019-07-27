package cn.sockstack.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;

public class MongoLearning {
    private static String DB = "user";
    private static String COLLECTION = "test";

    public static void main(String[] args) {
        //插入一条数据
        //insertOne();

        //插入多条数据
        //insertMany();

        //查询
        //findOne();

        //更新
        //update();

        //删除
        delete();
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static MongoClient getMongoClient() {
        return MongoClients.create("mongodb://127.0.0.1:27017");
    }

    /**
     * 插入一条数据
     */
    public static void insertOne() {
        //获取mongoClient
        MongoClient mongoClient = getMongoClient();

        //获取collection
        MongoCollection<Document> collection = mongoClient.getDatabase(DB).getCollection(COLLECTION);

        //设置要插入的数据
        Document document = new Document();
        Document data = document.append("name", "sockstack").append("age", 27).append("address", "oke");

        //插入数据库
        collection.insertOne(data);

        //关闭数据库连接
        mongoClient.close();
    }

    /**
     * 插入多条数据
     */
    public static void insertMany() {
        //获取数据库连接
        MongoClient mongoClient = getMongoClient();

        //获取collection
        MongoCollection<Document> collection = mongoClient.getDatabase(DB).getCollection(COLLECTION);

        //设置要插入的数据
        ArrayList<Document> documents = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            Document document = new Document();
            Document data = document.append("name", "sockstack" + i)
                    .append("age", 27 + i)
                    .append("address", "ok" + i);
            documents.add(data);
        }

        //批量插入数据库
        collection.insertMany(documents);

        //关闭数据库连接
        mongoClient.close();
    }

    /**
     * 更新数据
     */
    private static void update() {
        //获取连接
        MongoClient mongoClient = getMongoClient();

        //获取collection
        MongoCollection<Document> collection = mongoClient.getDatabase(DB).getCollection(COLLECTION);

        //更新多条数据
        UpdateResult updateResult = collection.updateMany(Filters.eq("name", "sockstack0"),
                Updates.combine(Updates.set("name", "sockstack0+update")));

        //显示影响条数
        System.out.println(updateResult.getModifiedCount());

        //关闭连接
        mongoClient.close();
    }

    /**
     * 获取第一条数据
     */
    private static void findOne() {
        //获取数据库连接
        MongoClient mongoClient = getMongoClient();

        //获取collection
        MongoCollection<Document> collection = mongoClient.getDatabase(DB).getCollection(COLLECTION);

        //获取所有的数据
        FindIterable<Document> documents = collection.find(new Document());

        //显示第一条数据
        System.out.println(documents.first());

        //关闭连接
        mongoClient.close();
    }

    /**
     * 删除数据
     */
    private static void delete() {
        //获取连接
        MongoClient mongoClient = getMongoClient();

        //获取collection
        MongoCollection<Document> collection = mongoClient.getDatabase(DB).getCollection(COLLECTION);

        //删除数据
        DeleteResult deleteResult = collection.deleteOne(Filters.eq("name", "sockstack2"));

        //获取删除的条数
        System.out.println(deleteResult.getDeletedCount());

        //关闭连接
        mongoClient.close();
    }
}
