package com.utilities;

import io.restassured.RestAssured;

public class GitBase {

    public static void setBaseURI() {
        RestAssured.baseURI = "https://api.github.com/";
    }

    public static void setContentType() {
        RestAssured.given().header("Content-Type", "application/vnd.github+json");
    }

}
