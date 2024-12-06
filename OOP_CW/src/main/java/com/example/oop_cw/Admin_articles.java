package com.example.oop_cw;

public class Admin_articles {
    private final String title;
    private final String url;
    private final String description;
    private final String date;
    private final String author;
    private final String content;

    public Admin_articles(String title, String url, String description, String date, String author, String content) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.date = date;
        this.author = author;
        this.content = content;
    }

    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }

}
