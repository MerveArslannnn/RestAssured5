import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _01_ZippoTest {
    @Test
    public void test1(){
        given()
                //Hazırlık işlemleri:(token,send body,parametreler)

                .when()
                //endpoint linki(url), methodu

                .then()
                // assertion,test kısmı,data işlemleri

        ;

    }
    @Test
    public void statusCodeTest(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()   //dönen body json datası,log.all()
                .statusCode(200)//dönüş kodu 200 mü demek..

        ;

    }
    @Test
    public void contantTypeTest(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()   //dönen body json datası,log.all()
                .statusCode(200)//dönüş kodu 200 mü demek..
                .contentType(ContentType.JSON) //dönen tip json mı

        ;

    }
    @Test
    public void checkCountryInResponseBody(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()   //dönen body json datası,log.all()
                .statusCode(200)//dönüş kodu 200 mü demek..
                .body("country",equalTo("United States"))//body nin

        ;

    }
//PM                            RestAssured
//body.country                  body("country")
//body.'post code'              body("post code")
//body.places[0].'place name'   body("places[0].'place name'")
//body.places.'place name'      body("places.'place name'")
//bütün place nameleri bir arraylist olarak verir
// https://jsonpathfinder.com/
@Test
public void checkCountryInResponseBodyTest(){
    given()

            .when()
            .get("http://api.zippopotam.us/us/90210")

            .then()
            .log().body()   //dönen body json datası,log.all()
            .statusCode(200)//dönüş kodu 200 mü demek..
            .body("places[0].state",equalTo("California"))//body nin

    ;

}


    @Test
    public void checkHasItemy(){
        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                //.log().body()
                .statusCode(200)
                .body("places.'place name'",hasItem("Dörtağaç Köyü"))
                //bütün place name lerin herhangi birinde Dörtağaç Köyü var mı

        ;

    }











}
