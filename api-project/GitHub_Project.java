package rESTAssured_GitHub_Project;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GitHub_Project {
	
	// initializing request specification
		RequestSpecification requestSpec;
	
	// declaration of variables
		String sshKey ;
		int idToken;
		
	@BeforeClass
	public void setUp()
	{
		requestSpec = new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", "token e77424b0c94a4571841b0f2f1edcee62105956e7")
				.setBaseUri("https://api.github.com")
				.build();
		
		sshKey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC6jlU/t2bke7McmWV0gVKRgSdHGU1RzZ33n9lGh2w6TZhKMVDWnuSK2AAGFPsQKd/dlyVJaO8Zl5Z6EXQwHrSmQaMo0DkkZKACwF53MzRZYEK2v9dKXlkqND1X906Qu86HZrj8UvPg9o6z8U8+Jo40mQM8H+gO7FgDEi1sAb89o6FwW9N5ylLran5wSGYPdLFI74AjpR6Z+NTXcUvpfwZ8ogdYaMFf6bWbWc0opl+QnBF+WFb0kyHGfyY5sxCTepk8qtTWRlNpxhe47HjGIrTgIiZhsHWeRLgVYzh+F6ZodBQYZ5cMSuzbYWIm8SpUyKxHfE7P5CYYDgAHJZ3Ul3bb azuread\\vijaymoyalan";
	}
	
	@Test(priority=1)
	public void getSSHKey()
	{
		Response response = given().spec(requestSpec).when().get("/user/keys");
		
		String resBody = response.getBody().asPrettyString();
		System.out.println("Response from GET Keys details -> "+resBody);
		
		//Assertion
		response.then().statusCode(200);	
	}
	
	@Test(priority=2)
	public void deleteSSHKey()
	{
		Response response = 
				given()
				.spec(requestSpec)
				.pathParam("keyId", idToken)
				.when().delete("/user/keys/{keyId}");
		
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		
		//Assertion
		response.then().statusCode(204);
	}
	
	@Test(priority=3)
	public void addSSHKey()
	{
		String reqBody = "{\"title\": \"TestKey\", \"key\": \"" + sshKey + "\" }";
		
		Response response = given()
				.spec(requestSpec)
				.body(reqBody)
				.when().post("/user/keys");
		
		String resBody = response.getBody().asPrettyString();
		
		System.out.println(resBody);
		idToken = response.then().extract().path("id");
		
		
		// Assertions
		response.then().statusCode(201);
	}

}
