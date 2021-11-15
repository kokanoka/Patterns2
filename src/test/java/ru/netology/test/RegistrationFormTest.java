package ru.netology.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class RegistrationFormTest {
    private DataGenerator user;

    @BeforeEach
    void Setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        DataGenerator.Registration activeUser = user.getActiveUser();
        $("[data-test-id=login] input").setValue(activeUser.getLogin());
        $("[data-test-id=password] input").setValue(activeUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        DataGenerator.Registration blockedUser = user.getBlockedUser();
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id='error-notification'] .notification__content");
        $(withText("Пользователь заблокирован")).shouldBe(visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        DataGenerator.Registration activeUser = user.getActiveUser();
        $("[data-test-id=login] input").setValue(DataGenerator.getRandomLogin());
        $("[data-test-id=password] input").setValue(activeUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        DataGenerator.Registration activeUser = user.getActiveUser();
        $("[data-test-id=login] input").setValue(activeUser.getLogin());
        $("[data-test-id=password] input").setValue(DataGenerator.getRandomPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(visible);
    }
}
