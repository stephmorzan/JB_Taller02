import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class ItemTest {

    JSONObject body = new JSONObject();
    Response response;
    String user = "api2025@api2025.com";
    String password = "12345";
    String token = "";

    @BeforeEach
    public void init(){
        body.put("Content", "Item" + new Date().getTime());

        response = given()
                .auth()
                .preemptive()
                .basic(user, password)
                .when()
                .get("https://todo.ly/api/authentication/token.json");

        response.then()
                .log().all()
                .statusCode(200);

        token = response.then().extract().path("TokenString");
    }

    @Test
    public void verifyCRUDItem(){
        //POST
        response = given()
                .header("Token", token)
                .body(body.toString())
                .log().all()

                .when()
                .post("https://todo.ly/api/items.json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo(body.get("Content")));

        int idItem = response.then().extract().path("Id");

        //GET

        //PUT

        //DELETE
    }
}
