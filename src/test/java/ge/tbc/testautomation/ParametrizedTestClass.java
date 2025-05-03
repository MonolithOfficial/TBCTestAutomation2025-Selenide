package ge.tbc.testautomation;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ParametrizedTestClass {
    private String language;

    public ParametrizedTestClass(String language) {
        this.language = language;
    }

    @BeforeClass
    public void setUp(){
        System.out.println(language);
    }

    @Test
    public void testLanguage() {
        System.out.println(language);
    }
}
