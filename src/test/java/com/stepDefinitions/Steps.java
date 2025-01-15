package com.stepDefinitions;

import static io.restassured.RestAssured.given;

import com.resources.CommonFunctions;
import org.json.simple.JSONObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.resources.LinkConstants;
import com.utility.FrameworkUtility;


public class Steps extends CommonFunctions {

    RequestSpecification reqSpec;
    RequestSpecification reqSpec2;
    FrameworkUtility utility;
    Response response;

    @Given("Github API exists")
    public void github_get_api_exists() {
        log.info("Setting GET/PUT/DELETE API Base URI...");
        reqSpec = given().spec(requestSpecification());
    }

    @Given("Github POST API exists")
    public void github_post_api_exists() {
        log.info("Setting POST API Base URI...");
        reqSpec = given().spec(postRequestSpecification());
    }

    @When("^Github GET API is called for the id (.+)$")
    public void github_get_api_is_called_for_the_id(String idvalue){
        log.info("Calling Github GET API...");
        System.out.println("Calling Github GET API..!");
        response = reqSpec.auth().preemptive().basic(FrameworkUtility.readConfigurationFile("key"),utility.readConfigurationFile("token"))
                .get(LinkConstants.GETNEW+idvalue);
        System.out.println("Response code after getting the project : " + response.getStatusCode());
        JsonPath jsnPath = response.jsonPath();
        System.out.println(jsnPath.get("name"));
    }

    @When("^Github POST API is called with the name (.+)$")
    public void github_post_api_is_called_with_the_name(String project) {

        RestAssured.baseURI = FrameworkUtility.readConfigurationFile("baseURI");
        // JSONObject is a class that represents a Simple JSON.
        // We can add Key - Value pairs using the put method
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", project);
        requestParams.put("description", "Test Project created by RestAssured");
        requestParams.put("homepage", "https://api.github.com");
        requestParams.put("private", false);
        requestParams.put("has_issues", true);
        requestParams.put("has_projects", true);

        log.info("Calling Github POST API...");
        response = reqSpec.auth().preemptive().basic(utility.readConfigurationFile("key"),utility.readConfigurationFile("token"))
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString()).when()
                .post(LinkConstants.POSTREPO);
        System.out.println("Response code is : " + response.getStatusCode());
    }

    @Then("Verify the status code is {string}")
    public void verify_the_status_code_is(String string) {
        log.info("Verifying the status code...");
        System.out.println("Response code is : " + response.statusCode());
        response.then().assertThat().statusCode(Integer.parseInt(string));
    }

    @When("^Github PUT API is called for the id (.+) and (.+)$")
    public void github_put_api_is_called_for_the_id_and(String idValue, String nameValue) {
        log.info("Calling Github PUT API...");

        // Correct API endpoint structure: repos/{owner}/{repo}
        String repoUrl = "https://api.github.com/repos/Saptagiri47/" + idValue; // Assuming `idValue` is the repository name

        // Prepare the request body with updated values
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", nameValue.trim());
        requestBody.put("description", "Test Project updated by RestAssured");
        requestBody.put("homepage", "https://api.github.com");
        requestBody.put("private", false);
        requestBody.put("has_issues", true);
        requestBody.put("has_projects", true);

        // Send the PUT request to update the repository
        Response response = RestAssured.given()
                .auth().preemptive().basic(FrameworkUtility.readConfigurationFile("key"), FrameworkUtility.readConfigurationFile("token"))
                .header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .put(repoUrl+idValue); // Use the correct URL with repo name (idValue)

        System.out.println("Response code is : " + response.getStatusCode());
        System.out.println("Response body is : " + response.getBody().asString());  // Print response body for debugging
    }

    @When("^Github PATCH API is called for the id (.+) and (.+)$")
    public void github_patch_api_is_called_for_the_id_and(String idValue, String description) {
        log.info("Calling Github PATCH API...");
        System.out.println("Calling Github PATCH API..!" + idValue);

        // Make sure the base URL and authentication are correct.
        RestAssured.baseURI = "https://api.github.com";

        // Authentication (make sure credentials are being correctly loaded)
        RestAssured.authentication = RestAssured.preemptive().basic(utility.readConfigurationFile("key"), utility.readConfigurationFile("token"));

        // Prepare the request with necessary fields
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", idValue);
        requestParams.put("description", description);
        requestParams.put("homepage", "https://api.github.com");
        requestParams.put("private", false);
        requestParams.put("has_issues", true);
        requestParams.put("has_projects", true);

        // Perform the PATCH request
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestParams.toJSONString())
                .when()
                .patch(LinkConstants.partialUpdate+idValue);

        // Ensure response is captured properly
        System.out.println("Response code after PATCH request: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
    }

    @When("^Github DELETE API is called for the id (.+)$")
    public void github_delete_api_is_called_for_the_id(String idValue) {
        log.info("Calling Github DELETE API...");
        System.out.println("Calling Github DELETE API..!");
        response = reqSpec.auth().preemptive().basic(FrameworkUtility.readConfigurationFile("key"), FrameworkUtility.readConfigurationFile("token"))
                .delete(LinkConstants.DELETEREPO+idValue);
        System.out.println("Response code after deleting the project : " + response.getStatusCode());
        System.out.println("Deleted the project : " + idValue);
    }

}

