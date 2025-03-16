package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserChoose {

    // Метод для создания WebDriver в зависимости от выбранного браузера
    public static WebDriver createDriver() {
        // Получаем имя браузера из системных свойств, если не задано — используем "chrome"
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        ChromeOptions options = new ChromeOptions();
        addCommonArguments(options); // Добавляем общие аргументы

        WebDriver driver;

        // Определяем, какой браузер использовать
        if (browser.equalsIgnoreCase("yandex")) {
            // Устанавливаем путь к WebDriver для Yandex (используется Chrome-драйвер)
            System.setProperty("webdriver.chrome.driver", "C:/WebDriver/bin/yandexdriver.exe");

            // Указываем путь к исполняемому файлу Yandex-браузера
            options.setBinary("C:\\Users\\Sirigu07\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");

            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        } else {
            throw new IllegalArgumentException("Неподдерживаемый браузер: " + browser);
        }

        driver.manage().window().maximize(); // Открываем браузер в полноэкранном режиме
        return driver;
    }

    // Метод для добавления стандартных аргументов к настройкам браузера
    private static void addCommonArguments(ChromeOptions options) {
        // Разрешает удалённые источники, обход возможных ограничений безопасности
        options.addArguments("--remote-allow-origins=*");
        // Отключает фичу AutomationControlled, чтобы браузер не распознавал автоматизацию
        options.addArguments("--disable-blink-features=AutomationControlled");
        // Отключает песочницу Chrome, что может быть полезно в контейнеризированных средах
        options.addArguments("--no-sandbox");
        // Отключает использование разделяемой памяти /dev/shm, помогает в средах с ограниченной памятью
        options.addArguments("--disable-dev-shm-usage");
        // Отключает использование GPU, может помочь при запуске в виртуальных средах
        options.addArguments("--disable-gpu");
        // Отключает расширения, снижает вероятность их влияния на тесты
        options.addArguments("--disable-extensions");
        // Запускает браузер в развернутом на весь экран режиме
        options.addArguments("--start-maximized");
        // Включает режим автоматизации, полезно для явного указания браузеру, что он управляется тестами
        options.addArguments("--enable-automation");
        // Отключает блокировку всплывающих окон, чтобы тесты могли взаимодействовать с попапами
        options.addArguments("--disable-popup-blocking");
        // Отключает уведомления, чтобы они не мешали тестированию
        options.addArguments("--disable-notifications");
        // Запускает браузер в режиме инкогнито, не сохраняя историю и куки
        options.addArguments("--incognito");
    }

}
