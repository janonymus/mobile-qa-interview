package org.viesure.common;

public class Article {

    private int id;
    private String title;
    private String description;
    private String author;
    private String release_date;
    private String image;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString(){
        return "Title:\t" + this.title +
                "\nAuthor:\t" + this.author +
                "\nRelease Date:\t" + this.release_date;
    }
}
