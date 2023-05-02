import Model.Location;
import Model.Place;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {
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
                .log().body()
                .statusCode(200)
                .body("places.'place name'",hasItem("Dörtağaç Köyü"))
                //bütün place name lerin herhangi birinde Dörtağaç Köyü var mı

        ;

    }
    @Test
    public void bodyArrayHasSizeTest(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                //.log().body()
                .statusCode(200)
                .body("places",hasSize(1))

        ;

    }
    @Test
    public void combiningTest(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                //.log().body()
                .statusCode(200)
                .body("places",hasSize(1))
                .body("places.state", hasItem("California"))
                .body("places[0].'place name'",equalTo("Beverly Hills"))

        ;

    }

    @Test
    public void pathParamTest(){
        given()
                .pathParam("ulke","us")
                .pathParam("postaKod",90210)
                .log().uri()//request url(link) sonucunu göster dedik

                .when()
                .get("http://api.zippopotam.us/{ulke}/{postaKod}")

                .then()
                .statusCode(200)
                //.log().body()


        ;

    }
    @Test
    public void queryParamTest(){
        //https://gorest.co.in/public/v1/users?page=3
        given()
                .param("page",1) //?page=1 şeklinde linke ekleniyor
                .log().uri()//request url(link) sonucunu göster dedik

                .when()
                .get("https://gorest.co.in/public/v1/users")//?page=1

                .then()
                .statusCode(200)
        //.log().body()


        ;

    }

    @Test
    public void queryParamTest2(){
        // https://gorest.co.in/public/v1/users?page=3
        // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
        // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.

        for (int i = 1; i < 10; i++) {

            given()
                    .param("page", i) //?page=1 şeklinde linke ekleniyor

                    .log().uri()//request url(link) sonucunu göster dedik

                    .when()
                    .get("https://gorest.co.in/public/v1/users")//?page=1

                    .then()
                    .statusCode(200)
                    .body("meta.pagination.page",equalTo(i))
                    .log().body()


            ;
        }
    }
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void Setup(){
        baseURI="https://gorest.co.in/public/v1";
        requestSpec=new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setContentType(ContentType.JSON)
                .build();
        responseSpec=new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .log(LogDetail.BODY)
                .build();

    }

    @Test
    public void requestResponseSpecification(){
        given()
                // https://gorest.co.in/public/v1/users?page=3
                .param("page", 1) //?page=1 şeklinde linke ekleniyor
                .spec(requestSpec)

                .when()
                .get("/users")

                .then()
                .spec(responseSpec)
                ;


    }

    @Test
    public void extractingJsonPath(){
        String countryName=
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .extract().path("country")
                ;

        System.out.println("countryName = " + countryName);
        Assert.assertEquals(countryName,"United States");


    }

    @Test
    public void extractingJsonPath2(){
        String placeName=
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        //.log().body()
                        .extract().path("places[0]['place name']")
                ;
        System.out.println("placeName = " + placeName);
        Assert.assertEquals(placeName,"Beverly Hills");


    }

    @Test
    public void extractingJsonPath3(){
        // https://gorest.co.in/public/v1/users  dönen değerdeki limit bilgisini yazdırınız.
        int limit=
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .log().body()
                        .extract().path("meta.pagination.limit")
                ;
                System.out.println("limit = " + limit);


    }

    @Test
    public void extractingJsonPath4(){
        // https://gorest.co.in/public/v1/users   data da ki bütün id lerin  bilgisini yazdırınız.

            List<String> name =
                    given()
                            .when()
                            .get("https://gorest.co.in/public/v1/users")
                            .then()
                            //.log().body()
                            .extract().path("data.'name'");
        System.out.println("name = " + name);


    }

    @Test
    public void extractingJsonPathResponsAll(){
        // https://gorest.co.in/public/v1/users   data da ki bütün id lerin  bilgisini yazdırınız.

        Response data =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                       // .log().body()
                        .extract().response();

        List<Integer> idler=data.path("data.id");
        List<String> name=data.path("data.name");
        int limit=data.path("meta.pagination.limit");
        List<String>email=data.path("data.email");
        List<String>gender=data.path("data.gender");

        System.out.println("idler = " + idler);
        System.out.println("name = " + name);
        System.out.println("limit = " + limit);
        System.out.println("email = " + email);
        System.out.println("gender = " + gender);

        Assert.assertTrue(idler.contains(1203771));
        Assert.assertTrue(name.contains("Gouranga Panicker"));
        Assert.assertTrue(email.contains("panicker_gouranga@breitenberg-lynch.test"));
        Assert.assertEquals(limit,10,"test sonucu hatalı");


    }
    @Test
    public void extractJsonAll_POJO(){
        //POJO: Json nesnesi == locationNesnesi
        Location locationNesnesi=
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .extract().body().as(Location.class) //location class ona göre bunu dönüştür demek.
        
        ;

        System.out.println("locationNesnesi.getCountry() = " + locationNesnesi.getCountry());
        for (Place p:locationNesnesi.getPlaces()) 
            System.out.println("p = " + p);

        System.out.println(locationNesnesi.getPlaces().get(0).getPlacename());
            
    }



    @Test
    public void extract_POJO_Soru(){
        //aşağıdaki endpointte Dörtağaç Köyü ait bilgileri yazdırınız
        Location adana=
        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                //.log().body()
                .extract().body().as(Location.class)

        ;
        for (Place p: adana.getPlaces()) {
            if (p.getPlacename().equalsIgnoreCase("Dörtağaç Köyü")){
                System.out.println("p = " + p);
            }
        }
    }
















}
