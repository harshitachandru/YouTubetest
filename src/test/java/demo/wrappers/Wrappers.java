package demo.wrappers;

import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.NoSuchElementException;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    public static void enterText(WebElement locator, String text) {
        try {
            locator.click();
            locator.clear();
            locator.sendKeys(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void movetoelementtoclick(ChromeDriver driver, WebElement locator) {
        try {

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", locator);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pressenter(WebElement locator) {
        try {
            locator.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int extractNumericValue(String text) {
        try {

            String numberPart = text.replaceAll("[^0-9]", "");
            return Integer.parseInt(numberPart);

        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void clickonrightarrow(WebDriver driver, int maxIteration, WebElement rightarrow) {

        for (int i = 0; i < maxIteration; i++) {
            try {
                if (rightarrow.isDisplayed()) {
                    rightarrow.click();
                    Thread.sleep(1000);
                } else {
                    // System.out.println("Right arrow button is not visible. Exiting loop.");
                    break;
                }

            } catch (Exception e) {
                // System.out.println("Right arrow button not visible on iteration: " + i + ".
                // Breaking the loop.");
                break;
            }
        }
    }

    public static String findElementAndPrint(WebElement driver, By locator, int elementno) {
        WebElement we = driver.findElements(locator).get(elementno);
        String text = we.getText();
        return text;
    }

    public static long extractlikescount(String likes) {
        likes = likes.trim().toUpperCase();

        if (likes.endsWith(" VIEWS")) {
            likes = likes.substring(0, likes.length() - 6).trim();
        }

        char lastchar = likes.charAt(likes.length() - 1);
        int multiplier = 1;

        switch (lastchar) {
            case 'K':
                multiplier = 1000;
                break;

            case 'M':
                multiplier = 100000;
                break;

            case 'B':
                multiplier = 1000000000;
                break;

            default:
                if (Character.isDigit(lastchar)) {
                    return Long.parseLong(likes);
                }
                throw new IllegalArgumentException("invalid format" + likes);
        }

        String numberpart = likes.substring(0, likes.length() - 1).trim();
        double number = Double.parseDouble(numberpart);

        return (long) (number * multiplier);

    }

    public static void scrollpage(WebDriver driver) {
        try {

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 1000)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
