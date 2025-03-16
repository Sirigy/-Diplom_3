package test.ui;

import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;
import steps.UserSteps;

import static org.junit.Assert.assertEquals;

@Epic("Авторизация пользователя") // Эпик для Allure-отчётов
public class LoginTest extends TestsSetUp {

    private UserSteps userSteps; // Шаги работы с пользователем
    private Response loginResponse; // Ответ сервера на создание пользователя
    private String email; // Email тестового пользователя
    private String password; // Пароль тестового пользователя
    private MainPage mainPage; // Главная страница
    private LoginPage loginPage; // Страница входа
    private RegisterPage registerPage; // Страница регистрации

    @Before
    @Step("Подготовка тестовой среды")
    @Description("Настраивает тестовую среду, открывает страницу входа, создаёт нового пользователя")
    public void setUp() {
        super.setUp();
        userSteps = new UserSteps();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        mainPage.open();
        // Создаём нового пользователя
        var user = UserData.getValidUser();
        email = user.getEmail();
        password = user.getPassword();
        loginResponse = userSteps.createUser(user);
    }

    @Test
    @Description("Тест проверяет вход через кнопку 'Войти в аккаунт' на главной странице и сам процесс авторизации")
    @DisplayName("Проверка входа через кнопку 'Войти в аккаунт'")
    public void testLoginFromEnterToAccountButton() {
        mainPage.clickLoginButton();
        enterEmailPasswordAndClickLoginButton();
        String expectedUrl = MainPage.BASE_URL;
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    @Description("Тест проверяет вход через кнопку 'Личный кабинет' на главной странице и переход в аккаунт")
    @DisplayName("Проверка входа через кнопку 'Личный кабинет'")
    public void testLoginWithPersonalCabinetButton() {
        mainPage.clickToLoginFromPersonalAccount();
        enterEmailPasswordAndClickLoginButton();
        String expectedUrl = MainPage.BASE_URL;
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    @Description("Тест проверяет успешный вход через кнопку 'Войти' в форме регистрации")
    @DisplayName("Проверка входа через форму регистрации")
    public void testLoginThroughRegistrationButton() {
        mainPage.clickLoginButton();
        loginPage.clickRegisterButton();
        registerPage.clickEnterButton();
        enterEmailPasswordAndClickLoginButton();
        String expectedUrl = MainPage.BASE_URL;
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    @Description("Тест проверяет успешный вход через кнопку 'Войти' в форме восстановления пароля")
    @DisplayName("Проверка входа через форму восстановления пароля")
    public void testLoginThroughRecoveryPasswordTemplate() {
        mainPage.clickLoginButton();
        loginPage.clickRecoveryPasswordButton();
        registerPage.clickEnterButton();
        enterEmailPasswordAndClickLoginButton();
        String expectedUrl = MainPage.BASE_URL;
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    // Удаляет созданного пользователя после тестов.
    @After
    @Step("Очистка данных после теста")
    @Description("Удаляет пользователя, созданного перед тестом")
    public void tearDown() {
        super.tearDown();
        if (loginResponse != null) {
            userSteps.getAccessToken(loginResponse);
            userSteps.deleteUser();
        }
    }


    //  Вводит email и пароль на странице входа и нажимает кнопку "Войти".
    @Step("Ввод email и пароля, затем нажатие кнопки 'Войти'")
    private void enterEmailPasswordAndClickLoginButton() {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
    }
}
