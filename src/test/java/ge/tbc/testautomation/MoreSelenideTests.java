package ge.tbc.testautomation;

import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.FileNotDownloadedError;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Objects;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byTagName;
import static com.codeborne.selenide.Selenide.*;

public class MoreSelenideTests {
    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
    }

    @Test
    public void fileUpload() {
        open("https://the-internet.herokuapp.com/upload");
        SelenideElement fileUploadInput = $(byId("file-upload"));
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("ronaldokneeslide.jpg")).getFile());
        fileUploadInput.uploadFile(file);
        SelenideElement uploadButton = $(byId("file-submit"));
        uploadButton.click();
    }

    @Test
    public void fileDownload() {
        open("https://the-internet.herokuapp.com/download");
        SelenideElement session6DownloadLink = $x("//a[@href='download/Session6.pdf']");
        try {
            File downloadedFile = session6DownloadLink.download();
            if (downloadedFile.exists()){
                System.out.println("File downloaded successfully");
            }
            else {
                System.out.println("Some kind of error happened");
            }
        }
        catch (FileNotDownloadedError e){
            e.printStackTrace();
        }
    }

    public void complexSendKeys() {
        open("https://redbubble.com");
        SelenideElement searchBar = $x("//input[@type='search']");

        searchBar.sendKeys("Search query");

        actions()
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .keyDown(Keys.BACK_SPACE)
                .keyUp(Keys.BACK_SPACE)
                .build()
                .perform();
    }

    @Test
    public void filterTest() {
        open("https://www.telerik.com/support/demos");

        // find (დააკვირდით, რომ find(WebElementCondition condition) მეთოდი წვდომადია ElementsCollection ობიექტებიდან მხოლოდ,
        // პარამეტრად იღებს WebElementCondition-ს, აბრუნებს SelenideElement).
        // როდის ვიყენებთ? - როდესაც გვაქვს კოლექცია და გვინდა იქიდან რამე ერთი ელემენტი ამოვიღოთ რაღაც კონდიციის საფუძველზე.
        SelenideElement navBar = $x("//div[@data-tlrk-plugin='navspy']"); // მოგვაქვს ნავბარი
        SelenideElement desktopLink = navBar
                .$$(byTagName("a")) // მოგვაქვს ყველა ენქორ ტეგი
                .find(Condition.exactText("Desktop")); // ვიღებთ იმ ენქორ ტეგს, რომლის ტექსტი არის ზუსტად 'Desktop'

        // findAll (დააკვირდით, რომ findAll მეთდი წვდომადია SelenideElement ობიექტებიდან მხოლოდ,
        // პარამეტრად იღებს By-ს (რამე ლოკატორს), აბრუნებს ElementsCollection-ს).
        // როდის ვიყენებთ? - როდესაც გვაქვს ერთეულოვანი ვებელემენტი და გვინდა მასში მოვძებნოთ *რამდენიმე* ელემენტი.
        // SelenideElement-საც აქვს find მეთოდი, ოღონდ ის პარამეტრად იღებს By-ს და არა WebElementConditions.
        ElementsCollection anchorLinks = navBar.findAll(byTagName("a")); // ჩათვალეთ, რომ $$ და findAll ერთი და იგივეა.

        // filter (წვდომადია მხოლოდ ElementsCollection-ის ობიექტებიდან მხოლოდ,
        // პარამეტრად იღებს WebElementCondition-ს, აბრუნებს კოლექციას)
        // როდის ვიყენებთ? - როცა გვაქვს კოლექცია და აქედან გვინდა გავფილტროთ ელემენტები რაღაც ქონდიშენის საფუძველზე
        // და ისევ კოლექცია მივიღოთ (გაფილტრული)
        ElementsCollection someKindOfAnchorLinks = navBar.findAll(byTagName("a"))
                .filter(Condition.partialText("p")); // მოგვაქვს ყველა ენქორ ტეგი, რომლის ტექსტიც შეიცავს p-ს.

    }

    public static WebElementCondition textOfLength(int expectedLength){
        return new WebElementCondition("text of length " + expectedLength) {
            @Nonnull
            @Override
            public CheckResult check(Driver driver, WebElement webElement) {
                String text = webElement.getText();
                return new CheckResult(text.length() == expectedLength, "Check Length");
            }
        };
    }
}
