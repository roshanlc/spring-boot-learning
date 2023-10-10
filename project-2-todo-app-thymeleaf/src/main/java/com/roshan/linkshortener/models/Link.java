package com.roshan.linkshortener.models;

public class Link {
    private String actualLink;
    private String shortLink;

    public Link() {
    }

    public Link(String actualLink, String shortLink) {
        this.actualLink = actualLink;
        this.shortLink = shortLink;
    }

    public String getActualLink() {
        return actualLink;
    }

    public void setActualLink(String actualLink) {
        this.actualLink = actualLink;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }
}
