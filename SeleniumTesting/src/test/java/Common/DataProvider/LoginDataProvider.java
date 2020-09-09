package Common.DataProvider;

import org.testng.annotations.DataProvider;

public class LoginDataProvider {

    private LoginDataProvider(){

    }

    @DataProvider
    public static final Object[][] getValidateEmailToCreateAccountTestData(){
        return new Object[][]{
            {"randimal123@gmail.com", true},
            {"randimals1@gmail.com", false}
        };
    }


}
