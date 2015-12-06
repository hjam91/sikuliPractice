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
import org.testng.annotations.DataProvider;
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


    }

        @Test(dataProvider="getDimension")
        public void headerTest(int x, int y, String size, String menu ) {

            driver.manage().window().setPosition(new Point(0,0));
            driver.manage().window().setSize(new Dimension(x,y));
            driver.get(prop.getProperty("environment"));

            Screen s = new Screen();
            try {

                Thread.sleep(2000);
                File dimension = new File(size);
                s.click(dimension);

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

    @DataProvider
    public Object[][] getDimension()
    {
        //Rows - Number of times your test has to be repeated.
        //Columns - Number of parameters in test data.
        Object[][] data = new Object[4][4];

        // 1st row
        data[0][0] = 1200;
        data[0][1] =  800;
        data[0][2] = prop.getProperty("image1200");
        data[0][3] = prop.getProperty("image1200menu");

        // 2nd row
        data[1][0] = 1024;
        data[1][1] =  600;
        data[1][2] = prop.getProperty("image1024");
        data[1][3] = prop.getProperty("image1024menu");

        // 3rd row
        data[2][0] = 960;
        data[2][1] = 640;
        data[2][2] = prop.getProperty("image960");
        data[2][3] = prop.getProperty("image960menu");

        data[3][0] = 480;
        data[3][1] = 800;
        data[3][2] = prop.getProperty("image480");
        data[3][3] = prop.getProperty("image480menu");

        return data;
    }


        @AfterClass
        public static void breakDown(){
            driver.close();
        }
    }

