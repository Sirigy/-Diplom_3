package test.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.MainPage;

@Epic("Навигация в разделе конструктора") // Эпик для Allure-отчётов
public class ConstructorPartTest extends TestsSetUp {

    private MainPage mainPage; // Главная страница

    @Before
    @Step("Подготовка тестовой среды")
    @Description("Выполняет инициализацию тестовой среды перед началом тестов")
    public void setUp() {
        super.setUp();
        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @Test
    @DisplayName("Переключение на вкладку 'Булки'")
    @Description("Проверяет, что после нажатия на вкладку 'Булки' она становится активной")
    public void testSwitchToBunsTab() {
        mainPage.clickTabWithWait("Булки");
        Assert.assertTrue("Вкладка 'Булки' не активна", mainPage.isCorrectTabActive(0));
    }

    @Test
    @DisplayName("Переключение на вкладку 'Соусы'")
    @Description("Проверяет, что после нажатия на вкладку 'Соусы' она становится активной")
    public void testSwitchToSaucesTab() {
        mainPage.clickTabWithWait("Соусы");
        Assert.assertTrue("Вкладка 'Соусы' не активна", mainPage.isCorrectTabActive(1));
    }

    @Test
    @DisplayName("Переключение на вкладку 'Начинки'")
    @Description("Проверяет, что после нажатия на вкладку 'Начинки' она становится активной")
    public void testSwitchToFillingsTab() {
        mainPage.clickTabWithWait("Начинки");
        Assert.assertTrue("Вкладка 'Начинки' не активна", mainPage.isCorrectTabActive(2));
    }
}

