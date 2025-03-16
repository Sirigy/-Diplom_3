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
import pages.RegisterPage;
import steps.UserSteps;

import static org.junit.Assert.assertEquals;

@Epic("Регистрация пользователя") // Эпик для Allure-отчётов
public class RegistrationTest extends TestsSetUp {

    private UserSteps userSteps; // Шаги работы с пользователем
    private RegisterPage registerPage; // Страница регистрации
    private LoginPage loginPage; // Страница входа
    private String email; // Email тестового пользователя
    private String password; // Пароль тестового пользователя
    private String name; // Имя тестового пользователя


    // Настройка тестовой среды перед выполнением тестов.
    // Открывает страницу регистрации и инициализирует тестовые данные пользователя
    @Before
    @Step("Подготовка тестовой среды")
    @Description("Настраивает тестовую среду, открывает страницу регистрации и инициализирует тестовые данные пользователя")
    public void setUp() {
        super.setUp();
        userSteps = new UserSteps();
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        registerPage.open();
        // Генерируем данные нового пользователя
        var user = UserData.getValidUser();
        email = user.getEmail();
        password = user.getPassword();
        name = user.getName();
    }

    @Test
    @Description("Тест проверяет, что пользователь успешно регистрируется и перенаправляется на страницу входа")
    @DisplayName("Успешная регистрация пользователя")
    public void testSuccessfulRegistration() {
        // Вводим данные для регистрации
        registerPage.enterName(name);
        registerPage.enterEmail(email);
        registerPage.enterPassword(password);
        registerPage.clickRegisterButton();

        // Проверяем, что после регистрации пользователь перенаправляется на страницу входа
        String expectedText = LoginPage.EXPECTED_LOGIN_TEXT;
        String actualText = driver.findElement(RegisterPage.LOGIN_HEADER).getText();

        assertEquals("Текст на странице входа после регистрации не соответствует ожидаемому", expectedText, actualText);
    }

    @Test
    @Description("Тест проверяет отображение сообщения об ошибке при вводе некорректного пароля")
    @DisplayName("Проверка валидации ошибки при некорректном пароле")
    public void testErrorsForInvalidPasswords() {
        // Вводим данные, но используем некорректный пароль
        registerPage.enterName(name);
        registerPage.enterEmail(email);
        registerPage.enterPassword("12345"); // Пароль слишком короткий
        registerPage.clickRegisterButtonWithoutWait();

        // Получаем текст ошибки
        String actualErrorMessage = registerPage.getPasswordErrorMessage();

        assertEquals("Некорректный пароль", actualErrorMessage);
    }

    // Удаляет созданного пользователя после тестов.
    @After
    @Step("Очистка данных после теста")
    @Description("Удаляет пользователя, созданного во время теста")
    public void cleanUpAfterUserRegistration() {
        // Авторизуем пользователя, чтобы получить accessToken
        Response loginResponse = userSteps.loginUser(new models.UserModel(email, password, null));
        userSteps.getAccessToken(loginResponse);

        // Удаляем пользователя, если он успешно вошёл
        if (userSteps.accessToken != null) {
            userSteps.deleteUser();
        }
    }
}

