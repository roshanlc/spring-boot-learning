package com.roshan.graphqldemo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.junit.jupiter.api.Assertions.*;

@GraphQlTest(BookController.class)
class BookControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldGetFirstBook() {
        System.out.println(this.graphQlTester.documentName("schema") //graphql schema file document name
                .variable("id", "book-1").execute().path("bookById").toString()

        );

//                .matchesJson("""
//                        {
//                        "id" :"book-1",
//                        "name":"Effective Java",
//                        "pageCount": 416,
//                        "author": {
//                        "name": "Joshua Bloch"
//                          }
//                        }
//                        """);
    }

}