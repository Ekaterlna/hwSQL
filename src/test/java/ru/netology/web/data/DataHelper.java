package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;
import org.checkerframework.checker.units.qual.A;

import java.util.Locale;

public class DataHelper {
    private DataHelper() {};

    private static final Faker faker = new Faker(new Locale("en"));

    @Value
    public static class AuthInfo{
        private String login;
        private String password;
    };

    public static AuthInfo getAuthInfo() {return new AuthInfo("vasya", "qwerty123");}

    public static String generateRandomLogin() {
        return faker.name().username();
    }

    public static String generateRandomPassword() {
        return faker.internet().password();
    }

    public static AuthInfo getGenerateRandomUser() {
        return new AuthInfo(generateRandomLogin(), generateRandomPassword());
    }

    public static AuthInfo getGenerateBlockedUser() {
        return new AuthInfo(getAuthInfo().login, generateRandomPassword());
    }

    @Value
    public static class VerificationCode {private String code;}

    public static VerificationCode getRandomVerificationCode() {return new VerificationCode(faker.numerify("######"));}
}
