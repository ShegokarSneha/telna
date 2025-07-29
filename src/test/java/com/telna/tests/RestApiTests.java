package com.telna.tests;

import com.telna.pojoclasses.BookingDates;
import com.telna.pojoclasses.Booking;
import com.telna.reporting.ExtentReportListener;
import com.telna.reporting.ReportingDetails;
import com.telna.reporting.AssertionValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

@Listeners(ExtentReportListener.class)
public class RestApiTests {
    static {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
    }

    String token;
    int bookingId;

    @Test
    public void createToken(){
        String body = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";

        ReportingDetails.addStepInReport("Request Body : " + body);
        Response response = given().contentType(ContentType.JSON).body(body).
                when().post("/auth").then().extract().response();

        AssertionValidation.validateStatusCode(response.statusCode(), 200);
        token = response.path("token").toString();
        ReportingDetails.addStepInReport(!token.isEmpty(), "Generated token : "+ token);

    }

    @Test
    public void createBooking() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/data/booking.json");
        Booking booking = objectMapper.readValue(file, Booking.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        BookingDates bookingDates = booking.getBookingdates();
        bookingDates.setCheckin(LocalDate.now().format(formatter));
        bookingDates.setCheckout(LocalDate.now().plusDays(7).format(formatter));
        booking.setBookingDates(bookingDates);
        ReportingDetails.addStepInReport("Request Body : " + booking.toString());
        Response response = given().contentType(ContentType.JSON).body(booking).
                when().post("/booking").then().extract().response();
        AssertionValidation.validateStatusCode(response.statusCode(), 200);
        ReportingDetails.addStepInReport("Response : " + response.prettyPrint());
        assertEquals("Jim", response.path("booking.firstname"), "First Name is " + response.path("firstname"));
        bookingId = response.path("bookingid");
        ReportingDetails.addStepInReport( "Booking Id : "+ bookingId);
    }

    @Test(dependsOnMethods = {"createToken", "createBooking"})
    public void updateBooking() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/data/booking.json");
        Booking booking = objectMapper.readValue(file, Booking.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        BookingDates bookingDates = booking.getBookingdates();
        bookingDates.setCheckin(LocalDate.now().plusDays(10).format(formatter));
        bookingDates.setCheckout(LocalDate.now().plusDays(20).format(formatter));
        booking.setBookingDates(bookingDates);
        booking.setFirstname("James");
        String tokenValue = "token=" + token;
        ReportingDetails.addStepInReport("Request Body : " + booking.toString());
        Response response = given().contentType(ContentType.JSON).body(booking).header("Cookie" , tokenValue).
                when().put("/booking/" + bookingId).then().extract().response();
        ReportingDetails.addStepInReport("Response : " + response.prettyPrint());
        AssertionValidation.validateStatusCode(response.statusCode(), 200);
    }

    @Test(dependsOnMethods = {"createBooking"})
    public void getBooking() {
        System.out.println(bookingId);
        Response response = given().contentType(ContentType.JSON).
                when().get("/booking/" + bookingId).then().extract().response();
        ReportingDetails.addStepInReport("Response : " + response.prettyPrint());
        AssertionValidation.validateStatusCode(response.statusCode(), 200);
    }
}
