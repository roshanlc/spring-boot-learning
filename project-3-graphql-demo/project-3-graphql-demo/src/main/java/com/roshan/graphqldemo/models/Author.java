package com.roshan.graphqldemo.models;

import java.util.Arrays;
import java.util.List;

public record Author(String id, String name) {

    private static List<Author> authors = Arrays.asList(
            new Author("author-1", "Joshua Bloch"),
            new Author("author-2", "Douglas Adams"),
            new Author("author-3", "Bill Bryson")
    );

    public static Author getAuthorById(String id) {
        return authors.stream()
                .filter(author -> author.id.equals(id))
                .findFirst()
                .orElse(null);
    }
}
