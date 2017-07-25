package com.pratamawijaya.popularmovieudacity.data.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("author")
    private String author;

    @SerializedName("id")
    private String id;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String url;

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return
                "Review{" +
                        "author = '" + author + '\'' +
                        ",id = '" + id + '\'' +
                        ",content = '" + content + '\'' +
                        ",url = '" + url + '\'' +
                        "}";
    }
}