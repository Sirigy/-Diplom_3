package test.ui;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BrowserChoose;

import java.time.Duration;

/*
 Абстрактный класс для настройки тестовой среды.
 Наследуется всеми UI-тестами для инициализации драйвера и базового URL.
 */
public abstract class TestsSetUp {
    protected WebDriver driver; // Веб-драйвер для тестов
    protected WebDriverWait wait; // Явное ожидание

    /*
     Настраивает тестовую среду перед выполнением тестов.
     Устанавливает базовый URL и инициализирует веб-драйвер.
     */
    @Before
    @Description("Настройка драйвера и базового URL для тестов")
    @DisplayName("Инициализация тестовой среды")
    public void setUp() {
        // Устанавливаем базовый URL для API
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        // Создаём веб-драйвер через утилитарный класс
        driver = BrowserChoose.createDriver();
        // Инициализируем WebDriverWait с таймаутом 10 секунд
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Закрывает браузер после завершения теста.
    @After
    @Description("Закрытие браузера после выполнения теста")
    @DisplayName("Завершение тестовой среды")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
