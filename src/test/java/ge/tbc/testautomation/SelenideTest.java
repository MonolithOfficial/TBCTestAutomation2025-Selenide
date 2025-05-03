package ge.tbc.testautomation;

import com.codeborne.selenide.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTest {
    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
    }

    @Test(priority = 1, invocationCount = 10, successPercentage = 95)
    public void testLocators() {
        open("https://the-internet.herokuapp.com/dynamic_controls");

//        SelenideElement enable = $(byCssSelector("css selector"));
//        SelenideElement enable = $("css selector");
//        SelenideElement removeButton = $(byTagAndText("button", "Remove"));
//        System.out.println(removeButton.text());
        SelenideElement checkBox = $(byId("checkbox")).$(byTagName("input"));

//        ElementsCollection tableCells = $$x("//table//td");
        ElementsCollection tableCells = $(byTagName("table")).$$(byTagName("td"));

        Random random = new Random();
        int randomInt = random.nextInt(4);
        if (randomInt == 1 || randomInt == 2){
            Assert.fail();
        }
//        tableCells.
    }

    @Test(priority = 3)
    public void waitTest() {
        open("https://the-internet.herokuapp.com/dynamic_controls");

//        WebElement enableButton = driver.findElement(By.xpath("//button[text()='Enable']"));

//        SelenideElement enable = $x("//button[text()='Enable']");

        SelenideElement enable = $(byTagAndText("button", "Enable"));
        enable.click();

        SelenideElement message = $(byId("message"))
                .shouldBe(Condition.visible)
                .shouldBe(visible, Duration.ofSeconds(30))
                .shouldHave(Condition.text("It's enabled!"));

        SelenideElement disabled = $(byTagAndText("button", "Disable"))
//                .shouldBe(or("check", visible, clickable))
                .shouldBe(visible)
                .shouldBe(clickable);
        disabled.click();
//        Assert.fail();
    }


    @Test(priority = 2, dependsOnMethods = {"waitTest"})
//    @Test(priority = 2, dependsOnMethods = {"waitTest"}, enabled = false)
//    @Ignore
    public void collectionTest(){
        open("https://the-internet.herokuapp.com/challenging_dom");
        ElementsCollection tableCells = $$x("//table//td");
        tableCells.forEach(System.out::println);
        tableCells.shouldHave(CollectionCondition.itemWithText("Consequuntur4"));
//        tableCells.shouldHave(CollectionCondition.texts("Adipisci3", "Definiebas4", "Definiebas6"));
//        throw new SkipException("Skipped");
    }
}
