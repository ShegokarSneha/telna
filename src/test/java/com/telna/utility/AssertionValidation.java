package com.telna.utility;

import com.telna.reporting.ReportingDetails;

import static org.testng.Assert.assertEquals;

public class AssertionValidation {

    public static void validateStatusCode(int actualStatusCode, int expectedStatusCode){
        assertEquals(expectedStatusCode, actualStatusCode, "Response is " + actualStatusCode);
        ReportingDetails.addStepInReport(true, "Response Status Code : " + actualStatusCode);
    }
}
