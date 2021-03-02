package tests.api;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static spec.Spec.request;

@Tag("api")
public class ApiTests {

    @ParameterizedTest(name = "Неуспешная авторизация по API")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    @AllureId("1772")
    void unsuccessfulLogin(int n) {
        List<Credentials> credentials = new ArrayList<>();
        credentials.add(0, new Credentials("acpjapfjapca", "ajpcjp5pjfac"));
        credentials.add(1, new Credentials("fapj pajcpj4", "949ct4qaf"));
        credentials.add(2, new Credentials("/*-$34", "$5324#"));
        credentials.add(3, new Credentials("/*-  $34", "$5324#"));
        credentials.add(4, new Credentials("464651fac /*-", "0000#"));

        Allure.description("Логин: " + credentials.get(n).getLogin() +
                " Пароль: " + credentials.get(n).getPassword()
        );

        String token = "64afcafa-9478-415d-a7f0-9d268fd2ce81";

        request()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .header("X-XSRF-TOKEN", token)
                .param("username", credentials.get(n).getLogin())
                .param("password", credentials.get(n).getPassword())
                .cookie("XSRF-TOKEN", token)
                .when()
                .post("https://ib.rencredit.ru/rencredit.server.portal.app/rest/public/auth/login")
                .then()
                .statusCode(400)
                .log().body()
                .body("errorResponseMo.errorMsg", is("Неверный логин или пароль"));
    }
}

