package ge.tbc.testautomation;

import org.testng.annotations.Factory;

public class LanguageFactory {
    @Factory
    public Object[] languageFactory(){
        return new Object[]{
                new ParametrizedTestClass("GE"),
                new ParametrizedTestClass("EN"),
        };
    }
}
