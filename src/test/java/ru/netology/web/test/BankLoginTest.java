package ru.netology.web.test;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.SQLHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.SQLHelper.cleanAuthCodes;
import static ru.netology.web.data.SQLHelper.cleanDatabase;

public class BankLoginTest {
    LoginPage loginPage;

    @BeforeEach
    void setUp() {loginPage = open("http://localhost:9999/", LoginPage.class);}

    @AfterEach
    void tearDown() {cleanAuthCodes();}

    @AfterAll
    static void tearDownAll() {cleanDatabase();}

    @Test
    void shouldSuccessfulLogin(){
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisible();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void shouldShowErrorLoggingWithRandomUser(){
        var authInfo = DataHelper.getGenerateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
    }

    @Test
    void shouldShowErrorWithRandomVerificationCode() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisible();
        var verificationCode = DataHelper.getRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка! \nНеверно указан код! Попробуйте ещё раз.");
    }
}
