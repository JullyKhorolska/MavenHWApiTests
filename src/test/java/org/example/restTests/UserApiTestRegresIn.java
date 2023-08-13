package org.example.restTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UserApiTestRegresIn {
    @BeforeAll
    public static void setBaseSpec(){
        BaseApiTest.setBaseRestAssuredSpec();
    }

    @BeforeEach
    public void printAfterTest() {
        System.out.println("Start of testing");
    }

    public String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date).substring(0,10);    }

    @Test
    @DisplayName("List Of Resource names")
    public void getListResourceOfNames() {
        String[] names = {"cerulean", "fuchsia rose", "true red", "aqua sky", "tigerlily", "blue turquoise"};
        List<UserObj> listOfNames = RestAssured.given()
                .when()
                .get("api/unknown")
                .then()
                .extract()
                .jsonPath()
                .getList("data", UserObj.class);
        Assertions.assertEquals(Arrays.toString(names),Arrays.toString(listOfNames.stream().map(UserObj::getName).toArray()));
    }

    @Test
    @DisplayName("Post Create User")
    public void postCreateUser(){
        CreateObj newUser = new CreateObj("morpheus","leader");
        String createdAt = RestAssured.given()
                .when()
                .body(newUser)
                .post("/api/users")
                .then()
                .extract()
                .jsonPath()
                .get("createdAt");
        Assertions.assertEquals(createdAt.substring(0, 10), getDate());
    }

    @Test
    @DisplayName("Update User")
    public void updateUser(){
        CreateObj newUser = new CreateObj("morpheus","zion resident");
        RestAssured.given()
                .when()
                .body(newUser)
                .post("/api/users/2")
                .then()
                .body("createdAt",Matchers.containsString(getDate()));

    }
    @Test
    @DisplayName("Delete User")
    public void deleteUser(){
        RestAssured.given()
                .when()
                .delete("api/users/2")
                .then()
                .statusCode(204);
    }
    @Test
    @DisplayName("Register User")
    public void postLoginSuccessful() throws IOException {
        File userJson = new File("src/test/java/org/example/restTests/userJson");
        LoginObj user = new ObjectMapper().readValue(userJson, LoginObj.class);
        String token = RestAssured.given()
                .when()
                .body(user)
                .post("api/login")
                .then()
                .extract()
                .jsonPath()
                .get("token");
        Assertions.assertEquals(token,"QpwL5tke4Pnpja7X4");
    }
}


