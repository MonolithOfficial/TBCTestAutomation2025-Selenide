import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.FileNotDownloadedError;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
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

    @Test
    public void filterTest() {
        open("https://the-internet.herokuapp.com/download");

        ElementsCollection anchorTags = $$x("//div[@id='content']//a");
        SelenideElement manualTestDocx = anchorTags.find(Condition.exactText("Manual Tester.docx"));

        SelenideElement containerDiv = $x("//div[@id='content']//div[@class='example']");
        ElementsCollection anchorTags2 = containerDiv.findAll(byTagName("a"));

        ElementsCollection anchorTagsContainingA = anchorTags.filter(Condition.partialText("a"));
//        ElementsCollection anchorTagsContainingA = anchorTags.filter(textOfLength(8));
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
