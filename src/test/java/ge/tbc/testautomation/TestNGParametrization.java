package ge.tbc.testautomation;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.data.DataSupplier;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class TestNGParametrization {
    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
    }

    @Test(dataProvider = "CategoryDataProvider", dataProviderClass = DataSupplier.class)
    public void testCategoryLabel(String categoryName, String label) {
        open("https://swoop.ge");
        SelenideElement categoryIcon = $x(String.format("//img[@alt='%s']", categoryName));
        categoryIcon.click();
        SelenideElement labelElement = $x("//h3[@type='h3']");
        labelElement.shouldHave(Condition.text(label));
    }
}
