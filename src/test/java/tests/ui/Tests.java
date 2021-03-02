package tests.ui;

import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Tag("ui")
public class Tests extends TestBase {

    @Test
    @AllureId("1767")
    @Feature("Authorization")
    @DisplayName("Неуспешная авторизация")
    void unsuccessfulAuthorization() {
        String errorMessage = "Вход в Интернет Банк невозможен. " +
                "Ваш логин не удовлетворяет политикам безопасности. " +
                "Обратитесь, пожалуйста, в контактный центр Банка по телефону 8 (800) 200-0-981 для смены логина";

        step("Открываем главную страницу", () -> {
            open("");
        });
        step("Нажимаем на \"Интернет-банк\"", () -> {
            $$(".top-nav__item").find(text("Интернет-банк")).click();
        });
        step("Вводим некорректные логин, пароль, кликаем на \"Войти\"", () -> {
            $("#username").val("1234567");
            $("#password").val("12495821");
            $("#button-button").click();
        });
        step("Проверяем, что появилось сообщение об ошибке", () -> {
            $(".empty-data-error").shouldHave(exactText(errorMessage));
        });
    }

    @Test
    @AllureId("1768")
    @Feature("Debit card")
    @DisplayName("Проверка количества дебетовых карт")
    void checkDebitCardsSizeOnPage() {
        step("Открываем главную страницу", () -> {
            open("");
        });
        step("Наводим мышь на блок \"Карты\"", () -> {
            $$(".service").find(text("Карты")).hover();
        });
        step("Нажать на \"Оформить карту\"", () -> {
            $$(".service").find(text("Карты")).$(byText("Оформить карту")).click();
        });
        step("Выбрать дебетовые карты", () -> {
            $(".btns-group__content").$(byText("Дебетовые")).click();
        });
        step("Проверить, что появилось 5 дебетовых карт", () -> {
            $$(".cards-list .cards-list__item").filter(text("Дебетовая карта")).shouldHaveSize(5);
        });
    }

    @Test
    @AllureId("1769")
    @Feature("Order card")
    @DisplayName("Проверка формы заказа дебетовой карты")
    void checkOrderFormOfDebitCard() {
        step("Открыть форму заказа дебетовой карты", () -> {
            open("https://rencredit.ru/app/debetcard/365");
        });
        step("Проверяем, что первое поле ввода содержит \"Фамилия\"", () -> {
            step("Сброс фокус с элемента", () -> {
                executeJavaScript("$(\"[name='ClientLastName']\").blur()");
            });
            $(byName("ClientLastName")).parent().shouldHave(text("Фамилия"));
        });
        step("Проверяем, что первое поле ввода содержит \"Имя\"", () -> {
            $(byName("ClientName")).parent().shouldHave(text("Имя"));
        });
        step("Проверяем, что второе поле ввода содержит \"Отчество\"", () -> {
            $(byName("ClientSecondName")).parent().shouldHave(text("Отчество"));
        });
        step("Проверяем, что третье поле ввода содержит \"Мобильный телефон\"", () -> {
            $(byName("ClientMobilePhone")).parent().shouldHave(text("Мобильный телефон"));
        });
        step("Проверяем, что четвертое поле ввода содержит \"E-mail\"", () -> {
            $(byName("AdditionalEmail")).parent().shouldHave(text("E-mail"));
        });
        step("Проверяем, что регион содержит \"Где Вы желаете получить карту?\"", () -> {
            $(byName("CreditRegion")).parent().shouldHave(text("Где Вы желаете получить карту?"));
        });
    }

    @Test
    @AllureId("1770")
    @Feature("Order card")
    @DisplayName("Отправка пустой формы заказа")
    void checkEmptyOrderFormOfDebitCard() {
        step("Открыть форму заказа дебетовой карты", () -> {
            open("https://rencredit.ru/app/debetcard/365");
        });
        step("Оставить все поля пустыми и нажать \"Далее\"", () -> {
            $$(".btn").find(text("Далее")).click();
        });
        step("Проверить, что появилось 5 сообщений о необходимости заполнить обязательные поля. " +
                "По одному сообщению под каждым полем", () -> {
            $$(".form-row [id*='error']").filter(exactText("Поле не заполнено.")).shouldHaveSize(5);
        });
    }

    @Test
    @AllureId("1771")
    @Feature("Deposit calculator")
    @DisplayName("Проверить калькулятор вкладов")
    void checkDepositCalculator() {
        step("Открыть калькулятор вкладов", () -> {
            open("https://rencredit.ru/contributions/");
        });
        step("Выбрать валюту  \"Доллары США\"", () -> {
            $(byValue("USD")).sibling(0).click();
        });
        step("Выбрать \"В интернет-банке\"", () -> {
            $$(".calculator__check-text").find(text("В интернет-банке")).click();
        });
        step("Указать сумму - 300 $", () -> {
            $(byName("amount")).val("300");
        });
        step("Проверить, что ставка 0.50%", () -> {
            $(".js-calc-rate").shouldHave(exactText("0.50%"));
        });
        step("Проверить, что текущая сумма равна 300", () -> {
            $(".js-calc-amount").shouldHave(exactText("300"));
        });
        step("Проверить, что будущая сумма равны 302,26", () -> {
            $$(".js-calc-result").filter(exactText("302,26")).shouldHaveSize(2);
        });
        step("Проверить, что начисленная сумма равна 2,26", () -> {
            $(".js-calc-earned").shouldHave(exactText("2,26"));
        });
    }

    @Test
    @AllureId("1782")
    @Feature("Feedback")
    @DisplayName("Проверить кнопку формы обратной связи")
    void checkButtonOfFeedbackForm() {
        step("Открыть форму обратной связи", () -> {
            open("https://rencredit.ru/support/appeals/");
        });
        step("Проверить, что кнопка полупрозрачна и неактивна", () -> {
            $("button[type='submit']").shouldHave(cssValue("opacity","0.5"),cssClass("btn_red_disabled"));
        });
    }
}


