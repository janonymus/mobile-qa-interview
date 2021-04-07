package org.viesure.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.viesure.common.Article;

import java.util.ArrayList;
import java.util.List;

public class Networking {

    static String dataURL = "https://run.mocky.io/v3/de42e6d9-2d03-40e2-a426-8953c7c94fb8";

    /**
     * Gets the dummy data from the backend as a List of Articles
     * @return the article list
     */
    public static List<Article> getDummyData(){
        return parseResponse(getRequest());
    }

    /**
     * Makes a GET request to the dummy backend
     * @return the response object of the request
     */
    private static Response getRequest(){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);

        return requestSpecification.relaxedHTTPSValidation().get(dataURL);
    }

    /**
     * Parses a response into a list of articles
     * @param response the response to be parsed
     * @return the parsed list of articles
     */
    private static List<Article> parseResponse(Response response){
        List<Article> responseArticles;

        Gson gson = new Gson();

        responseArticles = gson.fromJson(response.then().extract().response().asString(), new TypeToken<ArrayList<Article>>(){}.getType());

        return responseArticles;
    }
}
