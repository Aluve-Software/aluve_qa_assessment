package assessment.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import tech.aluvesoftware.api.http.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.apache.http.client.utils.URLEncodedUtils.CONTENT_TYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BookSearchSteps {
    Request request;
    Response response;
    List<Map<String, Object>> listOfBooks;
    List<Map<String, Object>> listOfSimilarBooks;
    Map<String, Object> selectedBook;

    public BookSearchSteps() {
        this.request = new Request("https://bookcart.azurewebsites.net/");
    }


    @When("the user searches for similar books to {string}")
    public void theUserSearchesForABook(String bookName) {
        int id = 0;
        for(Map<String, Object> book: listOfBooks){
            if(Objects.equals((String) book.get("title"), bookName)){
                id = (Integer) book.get("bookId");
                break;
            }
        }

        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, "application/json; charset=utf-8");
        request.setHTTPMethod("GET");
        request.setEndPoint("/api/Book/GetSimilarBooks/"+id);
        request.build();
        response = request.send();
        listOfSimilarBooks = response.getBody().jsonPath().getList("");
    }

    @Given("the user has all books")
    public void theUserHasAllBooks() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, "application/json; charset=utf-8");
        request.setHTTPMethod("GET");
        request.setEndPoint("/api/Book");
        request.build();
        response = request.send();
        listOfBooks = response.getBody().jsonPath().getList("");
    }

    @Then("the book price is {string}")
    public void theBookPriceIs(String price) {

        assertEquals(Float.valueOf(price), (Float) selectedBook.get("price"));
    }

    @Then("the author is {string}")
    public void theAuthorIs(String author) {
        assertEquals(author, (String) selectedBook.get("author"));
    }

    @Then("the category is {string}")
    public void theCategoryIs(String category) {
        assertEquals(category, (String) selectedBook.get("category"));
    }

    @Then("the book {string} is in the list of similar books")
    public void theBookIsInTheListOfSimilarBooks(String chosenBook) {
        boolean isBookFound = false;
        for(Map<String, Object> book: listOfSimilarBooks){
            if(Objects.equals((String) book.get("title"), chosenBook)){
                selectedBook = book;
                isBookFound = true;
                break;
            }
        }
        assertTrue(isBookFound);
    }
}
