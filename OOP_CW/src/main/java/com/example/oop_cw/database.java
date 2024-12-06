package com.example.oop_cw;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class database {

    // OOP Concept: Encapsulation - Hides con nection details and provides controlled access through methods.
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "News_Recommendation";
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    static {
        // OOP Concept: Abstraction - Users of this class don't need to know the details of how the connection is established.
        mongoClient = MongoClients.create(CONNECTION_STRING);
        database = mongoClient.getDatabase(DATABASE_NAME);
    }

    // OOP Concept: Abstraction - The method hides the complexity of querying the database.
    public static Optional<Document> findUserByUsername(String username) {
        MongoCollection<Document> usersCollection = database.getCollection("users");
        Document user = usersCollection.find(new Document("username", username)).first();
        return Optional.ofNullable(user);
    }

    // OOP Concept: Abstraction - Hides the complexity of inserting user data into the database.
    public static void saveUser(User_Register user) {
        MongoCollection<Document> usersCollection = database.getCollection("users");

        Document doc = new Document("firstName", user.getfirstName())
                .append("lastName", user.getlastName())
                .append("email", user.getemail())
                .append("phoneNumber", user.getphoneNumber())
                .append("username", user.getusername())
                .append("password", user.getpassword())
                .append("password", user.getconfirmPassword());


        usersCollection.insertOne(doc);
    }

    // New Method: Save rating, feedback, news title, and username
    public static void saveRating(String newsTitle, double rating, String feedback, String username) {
        MongoCollection<Document> ratingsCollection = database.getCollection("newsRatings");

        Document ratingDoc = new Document("title", newsTitle)
                .append("rating", rating)
                .append("feedback", feedback.isEmpty() ? "No feedback provided" : feedback)
                .append("username", username); // Store username to track who submitted the rating

        ratingsCollection.insertOne(ratingDoc);
    }

    public static void saveUserHistory(String username, String articleTitle, double rating) {
        MongoCollection<Document> historyCollection = database.getCollection("user_history");

        Document historyEntry = new Document("username", username)
                .append("title", articleTitle)
                .append("rating", rating)
                .append("timestamp", System.currentTimeMillis()); // Optional: track when it was saved

        historyCollection.insertOne(historyEntry);
    }

    public static void AdminSaveArticle(Admin_articles article) {
        MongoCollection<Document> articlesCollection = database.getCollection("Admin_Articles");

        Document doc = new Document("title", article.getTitle())
                .append("url", article.getUrl())
                .append("description", article.getDescription())
                .append("date", article.getDate())
                .append("author", article.getAuthor())
                .append("content", article.getContent());


        articlesCollection.insertOne(doc);
    }

    public static List<Admin_articles> getAllArticles() {
        MongoCollection<Document> articlesCollection = database.getCollection("articles");
        List<Admin_articles> articles = new ArrayList<>();

        for (Document doc : articlesCollection.find()) {
            String title = doc.getString("title");
            String url = doc.getString("url");
            String description = doc.getString("description");
            String date = doc.getString("date");
            String author = doc.getString("author");
            String content = doc.getString("content");

            articles.add(new Admin_articles(title, url, description, date, author, content));
        }

        return articles;
    }


    public static void deleteArticle(Admin_articles article) {
        MongoCollection<Document> articlesCollection = database.getCollection("articles");

        // Find and delete the document based on the title (assuming titles are unique)
        articlesCollection.deleteOne(new Document("title", article.getTitle()));
        System.out.println("Article deleted from database: " + article.getTitle());
    }

    // Fetch user history from the database
    public static List<Document> getUserHistory(String username) {
        MongoCollection<Document> historyCollection = database.getCollection("user_history");
        return historyCollection.find(Filters.eq("username", username)).into(new ArrayList<>());
    }


    // OOP Concept: Encapsulation - Provides a single method to handle resource cleanup.
    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}

