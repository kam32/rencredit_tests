package spec;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static template.ReportTemplate.filters;

public class Spec {
    public static RequestSpecification request() {
        return given()
                .filter(filters().customTemplates())
                .log().uri();
    }
}
