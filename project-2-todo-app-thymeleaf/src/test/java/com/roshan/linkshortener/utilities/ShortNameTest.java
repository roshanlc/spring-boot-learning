package com.roshan.linkshortener.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortNameTest {

    @Test
    void testShortName(){
        final String actualLink = "https://www.roshan-lamichhane.com.np";
        final String expectedShortLink ="21E9dmhT";
        final String gotShortLink= ShortName.generateShortLink(actualLink);
        assertEquals(expectedShortLink,gotShortLink);
    }
}