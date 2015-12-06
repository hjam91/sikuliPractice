import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by fortegs on 12/6/15.
 */
public class sikuli {

    static WebDriver driver;
    static Properties prop;
    static String BROWSER;

    @BeforeClass
    public static void beforeClass() throws IOException {

        prop = loadProp();
        BROWSER = prop.getProperty("browser");

        if (BROWSER.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", prop.getProperty("chromeDriverLocation"));
            driver = new ChromeDriver();
        }
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1200,1024));
        driver.get(prop.getProperty("environment"));

    }

        @Test
        public void headerTest() {

            Screen s = new Screen();
            try {

                Thread.sleep(2000);
                File x1200 = new File(prop.getProperty("image1200"));
               s.click(x1200);

            } catch (FindFailed e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            headerItems headerItems = new headerItems(driver);

            Actions builder = new Actions(driver);
            builder.moveToElement(headerItems.getmoreLink()).perform();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

            public static Properties loadProp() throws IOException {

                File file = new File("/home/fortegs/IdeaProjects/sikuliPractice/src/properties.prop");
                FileInputStream fileInput = new FileInputStream(file);
                Properties prop = new Properties();
                prop.load(fileInput);
                fileInput.close();
                return prop;
            }

        @AfterClass
        public static void breakDown(){
            driver.close();
        }
    }

