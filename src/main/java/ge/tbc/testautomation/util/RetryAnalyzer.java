package ge.tbc.testautomation.util;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    int count;

    @Override
    public boolean retry(ITestResult iTestResult) {
        RetryCount annotation = iTestResult.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(RetryCount.class);
        if (count < annotation.count()){
            count++;
            return true;
        }
        return false;
    }
}
