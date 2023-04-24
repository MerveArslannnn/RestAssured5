import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

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












}
