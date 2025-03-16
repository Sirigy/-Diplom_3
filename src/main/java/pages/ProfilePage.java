package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage { // Определение класса страницы профиля

    // ========================= Константы ====================
    public static final String PROFILE_URL = MainPage.BASE_URL + "account/profile"; // URL страницы профиля
    public static final String LOGIN_PAGE_URL = MainPage.BASE_URL + "login"; // URL страницы входа

    // ========================= Локаторы =====================
    private static final By CONSTRUCTOR_BUTTON = By.xpath("//p[contains(@class, 'AppHeader_header__linkText__3q_va') and contains(@class, 'ml-2') and text()='Конструктор']"); // Локатор кнопки "Конструктор"
    private static final By LOGOUT_BUTTON = By.xpath("//button[contains(@class, 'Account_button__14Yp3') and text()='Выход']"); // Локатор кнопки "Выход"
    private static final By STELLAR_BURGER_LOGO = By.xpath("//div[contains(@class, 'AppHeader_header__logo__2D0X2')]"); // Локатор логотипа "Stellar Burgers"

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ========================= Конструктор ===================
    public ProfilePage(WebDriver driver) { // Конструктор класса
        this.driver = driver;
        // Инициализация WebDriverWait с таймаутом 10 секунд
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ========================= Методы =========================
    @Step("Нажатие кнопки 'Конструктор' и ожидание перехода на главную страницу") // Описание шага в Allure
    public void clickToConstructorButton() {
        driver.findElement(CONSTRUCTOR_BUTTON).click();
        // Ожидание смены URL на главную страницу
        wait.until(ExpectedConditions.urlToBe(MainPage.BASE_URL));
    }

    @Step("Нажатие на логотип Stellar Burgers и ожидание перехода на главную страницу")
    public void clickToLogoButton() {
        driver.findElement(STELLAR_BURGER_LOGO).click();
        wait.until(ExpectedConditions.urlToBe(MainPage.BASE_URL));
    }

    @Step("Нажатие кнопки 'Выход' и ожидание перехода на страницу входа")
    public void clickLogoutButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGOUT_BUTTON)).click(); // Ожидание видимости и клик по кнопке "Выход"
        wait.until(ExpectedConditions.urlToBe(LOGIN_PAGE_URL));
    }
}
