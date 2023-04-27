import GoRest.User;
import Model.Location;
import Model.Place;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.*;
public class PathAndJsonPath {
    @Test
    public void extractingPath(){
        //"post code": "90210"
        int postCode= //hata versin diye int yaptık
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .extract().path("'post code'");

        System.out.println("postCode = " + postCode);
    }


    @Test
    public void extractingJsonPath(){
        // aslında postcode string di hata oranını azaltmak amaçlı int yapıp jsonPath().getInt() şeklinde kullandık
        //"post code": "90210"
        int postCode= //hata versin diye int yaptık
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().jsonPath().getInt("'post code'");

        System.out.println("postCode = " + postCode);
    }

    @Test
    public void getUsers(){
        Response response=
        given()

                .when()
                .get("https://gorest.co.in/public/v2/users")


                .then()
                //.log().body()
                .extract().response()


                ;
        int idPath=response.path("[2].id");
        int idJsonPath=response.jsonPath().getInt("[2].id");

        System.out.println("idPath = " + idPath);
        System.out.println("idJsonPath = " + idJsonPath);

        User[] usersPath=response.as(User[].class);//sadece dizi destekli as() nesne dönüşümü (POJO)
        List<User> usersJsonPath=response.jsonPath().getList("",User.class);//jsonPath() ise list olarak veriyor

        System.out.println("usersPath = " + Arrays.toString(usersPath));
        System.out.println("usersJsonPath = " + usersJsonPath);

    }


    @Test
    public void getUsersV1(){

        Response body=
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .log().body()
                        .extract().response()
                ;

        List<User>dataUsers=body.jsonPath().getList("data", User.class);
        // JSONPATH bir response içindeki bir parçayı nesneye ödnüştürebiliriz.
        System.out.println("dataUsers = " + dataUsers);
        // Daha önceki örneklerde (as) Clas dönüşümleri için tüm yapıya karşılık gelen
        // gereken tüm classları yazarak dönüştürüp istediğimiz elemanlara ulaşıyorduk.
        // Burada ise(JsonPath) aradaki bir veriyi clasa dönüştürerek bir list olarak almamıza
        // imkan veren JSONPATH i kullandık.Böylece tek class ise veri alınmış oldu
        // diğer class lara gerek kalmadan

        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.

    }

    @Test
    public void getZipCode(){
     Response response=
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                //.log().body()
                .extract().response()
             ;
        Location locPathAs=response.as(Location.class);// burada bütün classları yazmak zorundasın
        System.out.println("locPathAs.getPlaces() = " + locPathAs.getPlaces());

        List<Place> places=response.jsonPath().getList("places", Place.class);//burada nokta atışı istediğimizi aldık
        System.out.println("places = " + places);





    }

}