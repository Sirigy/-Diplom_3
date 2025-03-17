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
import pages.ProfilePage;
import pages.RegisterPage;
import steps.UserSteps;

import static org.junit.Assert.assertEquals;

@Epic("Навигация авторизованного пользователя на сайте") // Эпик-тема теста
public class NaviTest extends TestsSetUp {

    private UserSteps userSteps; // Объект для шагов пользователя
    private Response loginResponse; // Ответ после авторизации пользователя
    private String email; // Email пользователя
    private String password; // Пароль пользователя
    private MainPage mainPage; // Главная страница
    private ProfilePage profilePage; // Страница профиля
    private LoginPage loginPage; // Страница входа
    private RegisterPage registerPage; // Страница регистрации

    @Before
    @Step("Инициализация тестового окружения, создание пользователя и установка токена")
    @Description("Создает уникального пользователя через API, авторизует его, извлекает accessToken и refreshToken, сохраняет токены в localStorage браузера и обновляет сессию для тестов.")
    public void setUp() {
        super.setUp(); // Вызов метода setUp() родительского класса
        userSteps = new UserSteps(); // Создание объекта для работы с пользователем
        mainPage = new MainPage(driver); // Инициализация главной страницы
        profilePage = new ProfilePage(driver); // Инициализация страницы профиля
        loginPage = new LoginPage(driver); // Инициализация страницы входа
        registerPage = new RegisterPage(driver); // Инициализация страницы регистрации
        mainPage.open(); // Открытие главной страницы
        var user = UserData.getValidUser(); // Получение валидного пользователя
        email = user.getEmail(); // Сохранение email пользователя
        password = user.getPassword(); // Сохранение пароля пользователя
        userSteps.createUser(user); // Создание пользователя через API
        loginResponse = userSteps.loginUser(user); // Авторизация пользователя и получение ответа
        userSteps.getAccessToken(loginResponse); // Извлечение accessToken
        userSteps.setTokenInLocalStorage(driver, userSteps.accessToken); // Установка accessToken в localStorage
        userSteps.setRefreshTokenInLocalStorage(driver, userSteps.refreshToken); // Установка refreshToken в localStorage
        driver.navigate().refresh(); // Обновление страницы для применения токенов
    }

    @Test
    @Description("Тест проверяет, что нажатие кнопки 'Личный кабинет' ведет в личный кабинет")
    @DisplayName("Переход в личный кабинет через кнопку 'Личный кабинет' для авторизованного пользователя")
    public void testNavigateToPersonalCabinet() {
        mainPage.clickToPersonalAccountFromMainPage(); // Нажатие на кнопку 'Личный кабинет'
        String expectedUrl = ProfilePage.PROFILE_URL; // Ожидаемый URL страницы профиля
        assertEquals(expectedUrl, driver.getCurrentUrl()); // Проверка, что текущий URL совпадает с ожидаемым
    }

    @Test
    @Description("Тест проверяет, что нажатие кнопки 'Конструктор' ведет из личного кабинета в конструктор")
    @DisplayName("Переход из личного кабинета в конструктор через кнопку 'Конструктор' для авторизованного пользователя")
    public void testNavigateToConstructorFromPersonalCabinetByClickConstructorButton() {
        mainPage.clickToPersonalAccountFromMainPage(); // Нажатие на кнопку 'Личный кабинет'
        profilePage.clickToConstructorButton(); // Нажатие на кнопку 'Конструктор'
        String expectedUrl = MainPage.BASE_URL; // Ожидаемый URL главной страницы
        assertEquals(expectedUrl, driver.getCurrentUrl()); // Проверка, что текущий URL совпадает с ожидаемым
    }

    @Test
    @Description("Тест проверяет, что нажатие логотипа Stellar Burgers ведет из личного кабинета в конструктор")
    @DisplayName("Переход из личного кабинета в конструктор через логотип Stellar Burgers для авторизованного пользователя")
    public void testNavigateToConstructorFromPersonalCabinetUsingLogo() {
        mainPage.clickToPersonalAccountFromMainPage(); // Нажатие на кнопку 'Личный кабинет'
        profilePage.clickToLogoButton(); // Нажатие на логотип Stellar Burgers
        String expectedUrl = MainPage.BASE_URL; // Ожидаемый URL главной страницы
        assertEquals(expectedUrl, driver.getCurrentUrl()); // Проверка, что текущий URL совпадает с ожидаемым
    }

    @Test
    @Description("Тест проверяет, что нажатие кнопки 'Выход' в личном кабинете разлогинивает пользователя")
    @DisplayName("Выход из аккаунта через кнопку 'Выход'")
    public void testLogoutFromPersonalCabinet() {
        mainPage.clickToPersonalAccountFromMainPage(); // Нажатие на кнопку 'Личный кабинет'
        profilePage.clickLogoutButton(); // Нажатие на кнопку 'Выход'
        String expectedText = LoginPage.EXPECTED_LOGIN_TEXT; // Ожидаемый текст на странице входа
        String actualText = driver.findElement(RegisterPage.LOGIN_HEADER).getText(); // Получение текущего текста
        assertEquals("Текст на странице входа после выхода не совпадает с ожидаемым", expectedText, actualText); // Проверка текста
    }

    @After
    @Step("Очистка данных после теста")
    @Description("Удаляет пользователя, созданного перед тестом")
    public void tearDown() {
        super.tearDown(); // Вызов метода tearDown() родительского класса
        if (loginResponse != null) {
            userSteps.getAccessToken(loginResponse); // Извлечение accessToken
            userSteps.deleteUser(); // Удаление пользователя через API
        }
    }
}
