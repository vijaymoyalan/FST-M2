package activities;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Activity1 {
	
	String rootURL="https://petstore.swagger.io/v2/pet";
 
    @Test(priority=1)
    public void PostPetDetails() {
   	
    	String reqBody="{\"id\": 77232, \"name\": \"Riley\",\"status\": \"alive\"}";
    	
    	Response response = 
    			given().contentType(ContentType.JSON) // set the header of XML
    			.body(reqBody).when().post(rootURL); // Run Post Request
    	
    	// printing the body
    	String responseBody = response.getBody().asPrettyString();
    	System.out.println("Post Response is ->" + responseBody);
    	
    	// Assertion
    	response.then().body("id", equalTo(77232));
    	response.then().body("name", equalTo("Riley"));
    	response.then().body("status", equalTo("alive"));
    }
    
    
    @Test(priority=2)
    public void GetPetDetails() {
    	
    	Response response = 
    	given().contentType(ContentType.JSON)
    	.when().pathParam("petId", 77232)
    	.get(rootURL+"/{petId}");
    	
    	// printing the body
    	String responseBody = response.getBody().asPrettyString();
    	System.out.println("Get Response is ->" + responseBody);
    	
    	// Assertion
    	response.then().body("id", equalTo(77232));
    	response.then().body("name", equalTo("Riley"));
    	response.then().body("status", equalTo("alive"));        
    }
    
    @Test(priority=3)
    public void DeletePetDetails() {
    	String rootURL="https://petstore.swagger.io/v2/pet";
    	
    	Response response=
    	given().contentType(ContentType.JSON)
    	.when().pathParam("petId",77232)
    	.delete(rootURL+"/{petId}");
    	
    	// printing the body
    	String responseBody = response.getBody().asPrettyString();
    	System.out.println("Delete Response is ->" + responseBody);
    	
        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("77232"));    	
    }

}
