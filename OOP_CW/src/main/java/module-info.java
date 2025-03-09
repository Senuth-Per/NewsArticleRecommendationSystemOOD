module com.example.oop_cw {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires java.desktop;
    requires com.google.gson;
    requires okhttp3;
    requires java.net.http;


    opens com.example.oop_cw to javafx.fxml;
    exports com.example.oop_cw;
}