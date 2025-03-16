package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MainPage {

    // ========================= Константы =========================
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site/"; // Базовый URL сайта

    // ========================= Локаторы =========================

    // Локатор вкладки "Булки"
    private static final By BUNS_TAB = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='Булки']]");

    // Локатор вкладки "Соусы"
    private static final By SAUCES_TAB = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='Соусы']]");

    // Локатор вкладки "Начинки"
    private static final By FILLINGS_TAB = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and .//span[text()='Начинки']]");

    // Локатор кнопки "Войти в аккаунт"
    private static final By LOGIN_BUTTON = By.xpath("//button[contains(@class, 'button_button__33qZ0') and text()='Войти в аккаунт']");

    // Локатор ссылки на "Личный кабинет"
    private static final By PERSONAL_ACCOUNT_LINK = By.xpath("//p[@class='AppHeader_header__linkText__3q_va ml-2' and text()='Личный Кабинет']");

    // Локатор всех вкладок
    private static final By TABS = By.className("tab_tab__1SPyG");

    // Локатор кнопки "Конструктор"
    private static final By CONSTRUCTOR_BUTTON = By.xpath("//p[contains(@class, 'AppHeader_header__linkText__3q_va') and contains(@class, 'ml-2') and text()='Конструктор']");

    // Локатор логотипа сайта
    private static final By LOGO = By.xpath("//div[contains(@class, 'AppHeader_header__logo__2D0X2')]");

    // ========================= Атрибуты =========================

    // Класс активной вкладки (используется для проверки, какая вкладка выбрана)
    private static final String ACTIVE_TAB_CLASS = "tab_tab_type_current__2BEPc";

    // Атрибут class, используемый для проверки активного состояния элемента
    private static final String ATTRIBUTE_CLASS = "class";

    // Экземпляр WebDriver для управления браузером
    private final WebDriver driver;

    // Экземпляр WebDriverWait для явных ожиданий в тестах
    private final WebDriverWait wait;


    // ========================= Конструктор =========================
    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Ожидание элементов на странице
    }

    // ========================= Методы =========================
    @Step("Открытие главной страницы")
    public void open() {
        driver.get(BASE_URL); // Переход на главную страницу
    }

    @Step("Нажатие на кнопку 'Войти в аккаунт'")
    public void clickLoginButton() {
        driver.findElement(LOGIN_BUTTON).click(); // Клик по кнопке "Войти в аккаунт"
        wait.until(ExpectedConditions.urlToBe(LoginPage.LOGIN_URL)); // Ожидание перехода на страницу входа
    }

    @Step("Нажатие на 'Личный кабинет' для перехода на страницу авторизации")
    public void clickToLoginFromPersonalAccount() {
        driver.findElement(PERSONAL_ACCOUNT_LINK).click(); // Клик по ссылке "Личный кабинет"
        wait.until(ExpectedConditions.urlToBe(LoginPage.LOGIN_URL)); // Ожидание перехода на страницу входа
    }

    @Step("Нажатие на 'Личный кабинет' для перехода в профиль")
    public void clickToPersonalAccountFromMainPage() {
        driver.findElement(PERSONAL_ACCOUNT_LINK).click(); // Клик по ссылке "Личный кабинет"
        wait.until(ExpectedConditions.urlToBe(ProfilePage.PROFILE_URL)); // Ожидание перехода на страницу профиля
    }



    @Step("Нажатие на вкладку: {tabName}")
    public void clickTabWithWait(String tabName) {
        By tabLocator = By.xpath("//span[text()='" + tabName + "']/parent::div");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement tab = wait.until(ExpectedConditions.visibilityOfElementLocated(tabLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", tab);
        wait.until(ExpectedConditions.elementToBeClickable(tab));

        try {
            tab.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
        }

        wait.until(ExpectedConditions.attributeContains(tab, "class", ACTIVE_TAB_CLASS));
    }

    @Step("Проверка, что активна только ожидаемая вкладка с индексом: {expectedIndex}")
    public boolean isCorrectTabActive(int expectedIndex) {
        List<WebElement> tabs = driver.findElements(TABS);
        for (int i = 0; i < tabs.size(); i++) {
            boolean isActive = tabs.get(i).getAttribute("class").contains(ACTIVE_TAB_CLASS);
            if (i == expectedIndex && !isActive || i != expectedIndex && isActive) {
                return false;
            }
        }
        return true;
    }
}

