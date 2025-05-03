package ge.tbc.testautomation.data;

import org.testng.annotations.DataProvider;

public class DataSupplier {

    @DataProvider(name = "CategoryDataProvider")
    public static Object[][] categoryDataProvider(){
        return new Object[][]{
                {"კვება", "კვება", "ragac"},
                {"კინო", "კინო"},
                {"დასვენება", "დასვენება"},
                {"გართობა", "გართობა"},
                {"საბავშვო", "საბავშვო"},
                {"კურსები", "კურსები"},
        };
    }
}
