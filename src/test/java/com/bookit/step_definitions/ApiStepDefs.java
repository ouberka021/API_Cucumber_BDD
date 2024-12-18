package com.bookit.step_definitions;

import com.bookit.utilities.BookitUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.notNullValue;

public class ApiStepDefs {
    JsonPath jsonPath;
    Response response;
    ValidatableResponse thenPart;
    RequestSpecification givenPart = given().log().all();

    @Given("I logged in Bookit api as a {string}")
    public void i_logged_in_bookit_api_as_a(String role) {
        givenPart.header("Authorization", BookitUtils.generateTokenByRole(role));
    }

    @Given("request accept type is {string}")
    public void request_accept_type_is(String acceptHeader) {
        givenPart.accept(acceptHeader);
    }

    @When("I sent GET request to {string} endpoint")
    public void i_sent_get_request_to_endpoint(String endpoint) {
        response = givenPart.when().get(endpoint);
        jsonPath = response.jsonPath();
        thenPart = response.then();
        response.prettyPeek();
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int expectedStatusCode) {
//OPT1 - RESPONSE
        Assert.assertEquals(expectedStatusCode, response.statusCode());

//OPT2 THEN PART
        //thenPart.statusCode(expectedStatusCode);
        thenPart.statusCode(200);
    }

    @Then("response content type is {string}")
    public void response_content_type_is(String expectedContentType) {
//OPT1 - RESPONSE
        Assert.assertEquals(expectedContentType, response.contentType());

//OPT2 THEN PART
        //thenPart.statusCode(expectedStatusCode);
        thenPart.contentType(expectedContentType);

    }

    @Then("Each {string} field should not be null")
    public void each_field_should_not_be_null(String path) {
        //opt1 JsonPath
        List<Object> allFields = jsonPath.getList(path);
        for (Object eachField : allFields) {
            Assert.assertNotNull(eachField);
        }
        //opt2 then part
        thenPart.body(path, everyItem(notNullValue()));
    }



    /*
    second scenario
     */

    @Given("Path Param {string} is {string}")
    public void path_param_is(String pathParam, String pathParamValue) {
        givenPart.pathParam(pathParam, pathParamValue);
    }

    @Then("{string} field should not be null")
    public void field_should_not_be_null(String path) {
        String value = jsonPath.getString(path);
        Assert.assertNotNull(value);
    }

    @Then("the field value for {string} path should be equal {string}")
    public void the_field_value_for_path_should_be_equal(String path, String expectedValue) {
        String actualValue = jsonPath.getString(path);
        Assert.assertEquals(expectedValue, actualValue);

    }
}
