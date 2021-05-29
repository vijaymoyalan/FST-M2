package activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity2 {
    // Set base URL
    final static String ROOT_URI = "https://petstore.swagger.io/v2/user";

    @Test(priority=1)
    public void addNewUserFromFile() throws IOException {
        
    	// Import JSON file
        FileInputStream inputJSON = new FileInputStream("src/test/java/activities/info.json");
        
        // Read JSON file as String
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (inputJSON.read(buffer) != -1) {
            sb.append(new String(buffer));
            buffer = new byte[10];
        }
        inputJSON.close();
        //String reqBody = new String(inputJSON.readAllBytes());
        String reqBody = sb.toString();
        
        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .body(reqBody) // Pass request body from file
            .when().post(ROOT_URI); // Send POST request

        inputJSON.close();

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("3585"));
    }
    
    @Test(priority=2)
    public void getUserInfo() {
        // Import JSON file to write to
        File outputJSON = new File("src/test/java/activities/userGETResponse.json");

        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .pathParam("username", "vijaymoyalan") // Pass request body from file
            .when().get(ROOT_URI + "/{username}"); // Send POST request
        
        // Get response body
        String resBody = response.getBody().asPrettyString();

        try {
            // Create JSON file
            outputJSON.createNewFile();
            // Write response body to external file
            FileWriter writer = new FileWriter(outputJSON.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException excp) {
            excp.printStackTrace();
        }
        
        // Assertion
        response.then().body("id", equalTo(3585));
        response.then().body("username", equalTo("vijaymoyalan"));
        response.then().body("firstName", equalTo("Vijay"));
        response.then().body("lastName", equalTo("Moyalan"));
        response.then().body("email", equalTo("vijaym@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9812783213"));
    }
    
    @Test(priority=3)
    public void deleteUser() throws IOException {
        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .pathParam("username", "vijaymoyalan") // Add path parameter
            .when().delete(ROOT_URI + "/{username}"); // Send POST request

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("vijaymoyalan"));
    }
}