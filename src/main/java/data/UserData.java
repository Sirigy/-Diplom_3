package data;

import com.github.javafaker.Faker;
import models.UserModel;


public class UserData {

    // ========================= Константы =========================
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    // Генератор случайных данных Faker
    private static final Faker faker = new Faker();

    public static final String REGISTER_URL = BASE_URL + "/api/auth/register";

    public static final String AUTH_URL = BASE_URL + "/api/auth/user";

    public static final String LOGIN_URL = BASE_URL + "/api/auth/login";

    // ========================= Методы =========================

    // Создать валидного пользователя с уникальными данными
    public static UserModel getValidUser() {
        return new UserModel(
                faker.internet().emailAddress(),
                faker.internet().password(8, 16),
                faker.name().firstName()
        );
    }


}

