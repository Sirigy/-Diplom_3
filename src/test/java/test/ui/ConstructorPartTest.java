package test.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Step;
import pages.MainPage;

@Epic("Навигация в разделе конструктора") // Эпик для Allure-отчётов
public class ConstructorPartTest extends TestsSetUp {

    private MainPage mainPage; // Главная страница

    @Before
    @Step("Инициализация тестовой среды")
    @Description("Выполняет инициализацию тестовой среды перед началом тестов")
    public void setUp() {
        super.setUp();
        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @Test
    @Step("Тест: Переключение на вкладку 'Булки'")
    public void testSwitchToBunsTab() {
        mainPage.clickTabWithWait("Булки");
        Assert.assertTrue("Вкладка 'Булки' не активна", mainPage.isCorrectTabActive(0));
    }

    @Test
    @Step("Тест: Переключение на вкладку 'Соусы'")
    public void testSwitchToSaucesTab() {
        mainPage.clickTabWithWait("Соусы");
        Assert.assertTrue("Вкладка 'Соусы' не активна", mainPage.isCorrectTabActive(1));
    }

    @Test
    @Step("Тест: Переключение на вкладку 'Начинки'")
    public void testSwitchToFillingsTab() {
        mainPage.clickTabWithWait("Начинки");
        Assert.assertTrue("Вкладка 'Начинки' не активна", mainPage.isCorrectTabActive(2));
    }
}

