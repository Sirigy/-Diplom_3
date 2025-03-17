package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;



public class LoginPage {

    // ========================= Константы ====================
    // URL страницы входа
    public static final String LOGIN_URL = MainPage.BASE_URL + "login";
    // Ожидаемый текст заголовка страницы входа
    public static final String EXPECTED_LOGIN_TEXT = "Вход";

    // ========================= Локаторы =====================
    // Поле ввода email
    private static final By EMAIL_INPUT = By.xpath("//input[@name='name' and @type='text']");
    // Поле ввода пароля
    private static final By PASSWORD_INPUT = By.xpath("//input[@name='Пароль' and @type='password']");
    // Кнопка входа
    private static final By LOGIN_BUTTON = By.xpath("//button[contains(@class, 'button_button__33qZ0') and text()='Войти']");
    // Ссылка на регистрацию
    private static final By REGISTER_LINK = By.xpath("//a[contains(@class, 'Auth_link__1fOlj') and @href='/register']");
    // Ссылка на восстановление пароля
    private static final By FORGOT_PASSWORD_BUTTON = By.xpath("//a[contains(@class, 'Auth_link__1fOlj') and @href='/forgot-password']");

    // Веб-драйвер
    private final WebDriver driver;
    // Явное ожидание с таймаутом 10 секунд
    private final WebDriverWait wait;

    // ========================= Конструктор ====================
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ========================= Методы =========================
    // Ввод email
    @Step("Ввод email: {email}")
    public void enterEmail(String email) {
        driver.findElement(EMAIL_INPUT).sendKeys(email);
    }

    // Ввод пароля
    @Step("Ввод пароля: {password}")
    public void enterPassword(String password) {
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
    }

    // Нажатие кнопки входа
    @Step("Нажатие кнопки 'Войти'")
    public void clickLoginButton() {
        driver.findElement(LOGIN_BUTTON).click();
        // Ожидание перехода на главную страницу
        wait.until(ExpectedConditions.urlToBe(MainPage.BASE_URL));
    }

    // Нажатие на ссылку регистрации
    @Step("Нажатие на ссылку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        driver.findElement(REGISTER_LINK).click();
    }

    // Нажатие на ссылку восстановления пароля
    @Step("Нажатие на ссылку 'Восстановить пароль'")
    public void clickRecoveryPasswordButton() {
        driver.findElement(FORGOT_PASSWORD_BUTTON).click();
    }
}

