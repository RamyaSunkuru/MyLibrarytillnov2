package com.example.ramya_5542.mylibrary.Activities;


import java.sql.Blob;

public class Books {
    int book_id , pages ,rating;
    String publisher,title,genre,description, author;
    byte[] coverpage;
    Blob cover;
    public Books(int book_id, int pages ,int rating,String title, String publisher, String description, String genre, String author){
        this.book_id=book_id;
        this.pages=pages;
        this.rating=rating;
        this.title=title;
        this.publisher=publisher;
        this.description=description;
        this.genre=genre;
        this.author=author;
    }
    public Books( int pages ,int rating,String title, String publisher, String description, String genre, String author){
        this.pages=pages;
        this.rating=rating;
        this.title=title;
        this.publisher=publisher;
        this.description=description;
        this.genre=genre;
        this.author=author;
    }
    public Books(String title,String author, String genre,  String publisher, int pages ,int rating,byte[] coverpage, String description){
        this.pages=pages;
        this.rating=rating;
        this.title=title;
        this.publisher=publisher;
        this.description=description;
        this.genre=genre;
        this.author=author;
        this.coverpage=coverpage;
    }
    public  Books(int book_id, String title, String author, int rating ,byte[] coverpage){
        this.title=title;
        this.rating=rating;
        this.book_id=book_id;
        this.author=author;
        this.coverpage=coverpage;
    }
    public int getBook_id(){return this.book_id;}
    public void setBook_id(int book_id){this.book_id=book_id;}
    public int getPages(){return this.pages;}
    public void setPages(int pages){this.pages=pages;}
    public int getRating(){return this.rating;}
    private void setRating(int rating){this.rating=rating;}
    public String getTitle(){return  this.title;}
    public void setTitle(String title){this.title=title;}
    public String getPublisher(){return  this.publisher;}
    public void setPublisher(String publisher){this.publisher=publisher;}
    public String getDescription(){return this.description;}
    public void setDescription(String description){this.description=description;}
    public String getGenre(){return this.genre;}
    public void setGenre(String genre){this.genre=genre;}
    public String getAuthor(){return this.author;}
    public void setAuthor(String author){this.author= author;}
    public byte[] getCoverpage(){return  this.coverpage;}
    public  void setCoverpage(byte[] coverpage){this.coverpage= coverpage;}
}
