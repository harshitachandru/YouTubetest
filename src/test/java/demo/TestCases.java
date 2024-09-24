package demo;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;

import bsh.commands.dir;
import java.util.List;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Wrapper;
import java.time.Duration;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
import demo.wrappers.Wrappers;
import dev.failsafe.internal.util.Durations;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }

        @Test(enabled = true)
        public void testCase01() throws InterruptedException {
                System.out.println("Testcase01 Starts");
                driver.get("https://www.youtube.com");
                Thread.sleep(2000);
                System.out.println("Wait 1");

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));

                Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/", "URL is incorrect");

                WebElement aboutLink = wait.until(ExpectedConditions
                                .elementToBeClickable(By.xpath("//div[@id='guide-links-primary']/a[text()='About']")));
                Wrappers.movetoelementtoclick(driver, aboutLink);

                Thread.sleep(2000);
                System.out.println("Wait 2");

                List<WebElement> aboutcontent1 = wait.until(ExpectedConditions
                                .visibilityOfAllElementsLocatedBy(By.xpath("//section[@class='ytabout__content']")));
                for (WebElement aboutContent : aboutcontent1) {
                        System.out.println(aboutContent.getText());
                }

                System.out.println("TestCase 01 Ends");

        }

        @Test(enabled = true)
        public void testCase02() throws InterruptedException {
                System.out.println("Testcase02 Starts");
                driver.get("https://www.youtube.com");
                Thread.sleep(2000);
                System.out.println("Wait 1");

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

                WebElement movieslink = wait.until(ExpectedConditions
                                .elementToBeClickable(By.xpath("//yt-formatted-string[contains(text(),'Movies')]")));
                movieslink.click();

                WebElement rightarrow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                                "//span[contains(text(),'Top selling')]//ancestor::div[@id='dismissible']//div[@id='right-arrow']//button")));

                Wrappers.clickonrightarrow(driver, 10, rightarrow);

                List<WebElement> movieparent = driver.findElements(By.xpath(
                                "//span[contains(text(),'Top selling')]//ancestor::div[@id='dismissible']//ytd-grid-movie-renderer[contains(@class, 'horizontal-list-renderer')]"));

                if (!movieparent.isEmpty()) {
                        WebElement lastmovie = movieparent.get(movieparent.size() - 1);

                        String category = lastmovie.findElement(By.xpath(".//span[contains(@class,'metadata')]"))
                                        .getText();
                        String rating = lastmovie.findElement(By.xpath(
                                        ".//div[contains(@class, 'badge  badge-style-type-simple style-scope')]//p"))
                                        .getText();

                        SoftAssert sa = new SoftAssert();

                        if (rating.contains("A")) {
                                System.out.println("The movie is rated 'A' for Mature.");
                        } else {
                                System.out.println("The movie is NOT rated 'A'. It is rated: " + rating);
                        }

                        sa.assertTrue(rating.contains("A"), "The movie rating is not marked 'A' for Mature.");
                        Thread.sleep(2000);
                        System.out.println("Wait 2");

                        sa.assertTrue(category.matches("Comedy|Animation|Drama"),
                                        "Movie category is not in expected categories (Comedy, Animation, Drama).");
                }

                System.out.println("TestCase 02 Ends");

        }

        @Test(enabled = true)
        public void testCase03() throws InterruptedException {
                System.out.println("Testcase03 Starts");
                driver.get("https://www.youtube.com");
                Thread.sleep(2000);
                System.out.println("Wait 1");

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

                WebElement musiclink = wait.until(ExpectedConditions
                                .elementToBeClickable(By.xpath("//yt-formatted-string[text()='Music']")));
                musiclink.click();

                WebElement rightarrow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                                "//span[text()=\"India's Biggest Hits\"]//ancestor::div[@id='dismissible']//div[@id='right-arrow']//button")));

                Wrappers.clickonrightarrow(driver, 10, rightarrow);

                Thread.sleep(2000);
                System.out.println("Wait 2");

                List<WebElement> playLists = driver.findElements(
                                By.xpath("//span[@id='title' and contains(text(),'Biggest Hits')]//ancestor::div[@id='dismissible']//div[contains(@class,'flex-container')]"));

                SoftAssert sa = new SoftAssert();

                if (!playLists.isEmpty()) {
                        WebElement lastplaylist = playLists.get(playLists.size() - 1);

                        String title = lastplaylist.findElement(By.xpath(".//h3[contains(@class,'style-scope ')]"))
                                        .getText();
                        System.out.println("The Playlist Name is: " + title);
                        Thread.sleep(2000);
                        System.out.println("Wait 3");

                        String trackcountstring = lastplaylist.findElement(By.xpath(".//p[@id='video-count-text']"))
                                        .getText();
                        int trackcount = Wrappers.extractNumericValue(trackcountstring);

                        System.out.println("The Track count number is: " + trackcount);
                        sa.assertTrue(trackcount <= 50, "Playlist " + title + " has more than 50 tracks.");
                }

                System.out.println("TestCase 03 Ends");

        }

        @Test(enabled = true)
        public void testCase04() throws InterruptedException {
                System.out.println("Testcase04 Starts");
                driver.get("https://www.youtube.com");
                Thread.sleep(2000);
                System.out.println("Wait 1");

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

                WebElement newslink = wait.until(ExpectedConditions
                                .elementToBeClickable(By.xpath("//yt-formatted-string[contains(text(),'News')]")));
                newslink.click();

                Thread.sleep(2000);
                System.out.println("Wait 2");

                List<WebElement> latestcontentcard = driver.findElements(By.xpath(
                                "//span[contains(text(),'Latest news posts')]//ancestor::div[@id='dismissible']//ytd-rich-item-renderer[contains(@class,'style-scope ')]"));

                if (latestcontentcard.isEmpty()) {
                        System.out.println("No latest news posts found.");
                        return; // Exit the test case if no posts are found
                }

                long sumOfVotes = 0;
                for (int i = 0; i < 3; i++) {
                        WebElement latestthree = latestcontentcard.get(i);

                        String newstitle = latestthree.findElement(By.xpath(".//a[@id='author-text']/span")).getText();
                        System.out.println("Latest News Title is : " + newstitle);

                        String newsbody = latestthree
                                        .findElement(By.xpath(".//yt-formatted-string[@id='home-content-text']"))
                                        .getText();
                        System.out.println("Latest News Body is: " + newsbody);

                        String liketext;
                        try {
                                liketext = latestthree.findElement(By.xpath(".//span[@id='vote-count-middle']"))
                                                .getText();
                        } catch (NoSuchElementException e) {
                                liketext = "0 likes";
                        }

                        long likecount = Wrappers.extractlikescount(liketext);
                        Thread.sleep(2000);
                        System.out.println("Wait 3");
                        sumOfVotes = sumOfVotes + likecount;
                }
                System.out.println("The total count = " + sumOfVotes);

                System.out.println("TestCase 04 Ends");

        }

        @DataProvider(name = "searchItems")
        public Object[][] getData() throws IOException {
                FileInputStream fis = new FileInputStream(
                                "src/test/resources/data.xlsx");
                XSSFWorkbook workbook = new XSSFWorkbook(fis);
                XSSFSheet sheet = workbook.getSheetAt(0);
                DataFormatter formatter = new DataFormatter();

                int rowcount = sheet.getPhysicalNumberOfRows();
                Object[][] data = new Object[rowcount - 1][1];

                for (int i = 1; i < rowcount; i++) {
                        data[i - 1][0] = formatter.formatCellValue(sheet.getRow(i).getCell(0));
                }
                return data;
        }

        @Test(dataProvider = "searchItems")
        public void testCase05(String searchItems) throws InterruptedException {
                System.out.println("Testcase05 Starts");
                driver.get("https://www.youtube.com");
                Thread.sleep(2000);
                System.out.println("Wait 1");

                WebElement searchbox = driver.findElement(By.xpath("//div[@id='search-input']//input[@id='search']"));
                Wrappers.enterText(searchbox, searchItems);

                Thread.sleep(2000);
                System.out.println("Wait 2");

                Wrappers.pressenter(searchbox);

                Thread.sleep(2000);
                System.out.println("Wait 3");

                long totalViews = 0;
                long targetViews = 100000000L;

                while (totalViews < targetViews) {
                        List<WebElement> videolikes = driver.findElements(By.xpath(
                                        "//ytd-video-renderer//div[@id='metadata-line']/span[contains(text(), 'views')]"));

                        for (WebElement videolike : videolikes) {
                                String viewtext = videolike.getText();
                                long videoviews = Wrappers.extractlikescount(viewtext);

                                totalViews = totalViews + videoviews;

                                System.out.println("Video views : " + videoviews + "| Total View : " + totalViews);

                                if (totalViews >= targetViews) {
                                        System.out.println("Reached the Traget 10Cr");
                                        break;
                                }
                        }

                        if (totalViews < targetViews) {
                                Wrappers.scrollpage(driver);
                                Thread.sleep(2000);
                                System.out.println("Wait 4");
                        }

                }

                System.out.println("TestCase 05 Ends");

        }

}