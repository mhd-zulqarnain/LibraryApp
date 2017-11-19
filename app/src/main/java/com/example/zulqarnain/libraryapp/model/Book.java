package com.example.zulqarnain.libraryapp.model;

/**
 * Created by Zul Qarnain on 11/19/2017.
 */

public class Book {
    private String bookTitle;
    private String bookDescription;
    private String downPath;
    private String bookKey;


    public Book() {
    }

    public Book(String bookTitle, String bookDescription, String downPath, String bookKey) {
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        this.downPath = downPath;
        this.bookKey = bookKey;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getDownPath() {
        return downPath;
    }

    public void setDownPath(String downPath) {
        this.downPath = downPath;
    }

    public String getBookKey() {
        return bookKey;
    }

    public void setBookKey(String bookKey) {
        this.bookKey = bookKey;
    }
}
