import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import org.sikuli.script.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by fortegs on 12/6/15.
 */
public class sikuli {

    static WebDriver driver;
    static Properties prop;
    static String BROWSER;
    static String URL;

    @BeforeClass
    public static void beforeClass() throws IOException {

        prop = loadProp();
        BROWSER = prop.getProperty("browser");

        if (BROWSER.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", prop.getProperty("chromeDriverLocation"));
            driver = new ChromeDriver();
        }

        else if (BROWSER.equals("IE")) {
            System.setProperty("webdriver.chrome.driver", prop.getProperty("chromeDriverLocation"));
            driver = new ChromeDriver();
        }

        else
            driver = new FirefoxDriver();

    //    Cookie newCookie= new Cookie("geo_cookie", "USA", "cnbc.com");
    //    driver.manage().addCookie(newCookie);

        URL = prop.getProperty("URL");
        driver.manage().window().setPosition(new Point(0, 0));

        }


        @Test(dataProvider="getDimension")
        public void headerTest(int width, int height, String baseImage, String baseImageMenu ) throws FindFailed, InterruptedException, IOException {

            String ID;
            driver.manage().window().setSize(new Dimension(width, height));

            File testDataSrc = new File(prop.getProperty("testDataLocation"));
            FileInputStream testData = new FileInputStream(testDataSrc);
            XSSFWorkbook wb = new XSSFWorkbook(testData);
            XSSFSheet sheet1 = wb.getSheetAt(0);
            headerItems headerItems1 = new headerItems(driver);

            System.out.println("Physical Number of Rows:" + (sheet1.getPhysicalNumberOfRows()-1));
            for (int i = 1; i < 3; i++) {

                ID = sheet1.getRow(i).getCell(1).getStringCellValue();
                driver.get(URL + "/id/" + ID);
                verifyImage(baseImage);
                System.out.println("Image 1 match Success");

                if (width > 960) {

                    Actions builder = new Actions(driver);
                    builder.moveToElement(headerItems1.getmoreLink()).perform();
                    Thread.sleep(3000);
                    verifyImage(baseImageMenu);
                    System.out.println("Image 2 match Success");
                }

                else {
                    headerItems1.getNav().click();
                    Thread.sleep(3000);
                    verifyImage(baseImageMenu);
                    System.out.println("Image 2 match Success");
                }

            }


        }

    @Test(dataProvider="getDimension")
    public void headerTestXfinity(int width, int height, String baseImage, String baseImageMenu ) throws FindFailed, InterruptedException, IOException {

        String ID;
        URL = prop.getProperty("URL");
        //driver.get(URL+"/xfinity-money/");
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(width, height));

        File testDataSrc = new File(prop.getProperty("testDataLocation"));
        FileInputStream testData = new FileInputStream(testDataSrc);
        XSSFWorkbook wb = new XSSFWorkbook(testData);
        XSSFSheet sheet1 = wb.getSheetAt(0);
        headerItems headerItems1 = new headerItems(driver);
        Thread.sleep(3000);

        System.out.println("Physical Number of Rows:" + (sheet1.getPhysicalNumberOfRows()-1));
        for (int i = 1; i < 3; i++) {

            ID = sheet1.getRow(i).getCell(1).getStringCellValue();
            driver.get(URL + "/id/" + ID);
            if (i<2){
                Cookie newCookie= new Cookie("active_partner_exp", "xfinity", "/");
                driver.manage().addCookie(newCookie);
                driver.navigate().refresh();
            }
            Thread.sleep(4000);
            verifyImage(baseImage);
            System.out.println("Image 1 match Success");

            if (width > 960) {

                Actions builder = new Actions(driver);
                builder.moveToElement(headerItems1.getmoreLink()).perform();
                Thread.sleep(4000);
                verifyImage(baseImageMenu);
                System.out.println("Image 2 match Success");
            }

            else {
                headerItems1.getNav().click();
                Thread.sleep(3000);
                verifyImage(baseImageMenu);
                System.out.println("Image 2 match Success");
            }

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

    public void verifyImage(String size) throws FindFailed {

        Screen screen = new Screen();
        Pattern image = new Pattern(size);
        screen.find(image);
        //  screen.wait(image, 10);
        //   screen.click();
        Match m = screen.find(image);

        if (!(m == null)){
            System.out.println("Images Matched" + m);
        }
        else
            System.out.println("Did not match" + m);


    }



    @DataProvider
    public Object[][] getDimension()
    {
        //Rows - Number of times your test has to be repeated.
        //Columns - Number of parameters in test data.
        Object[][] data = new Object[4][4];

        // 1st row
        data[0][0] = 1280;
        data[0][1] =  800;
        data[0][2] = prop.getProperty("image1200");
        data[0][3] = prop.getProperty("image1200menu");

        // 2nd row
        data[1][0] = 1024;
        data[1][1] =  768;
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


    /*Screen screen = new Screen();
            Pattern image = new Pattern(size);
            screen.find(image);
            screen.wait(image, 10);
          //  screen.click();
            Match m = screen.find(image);*/

           /* if (!(m == null)){
                System.out.println("Images Matched" + m);
            }
            else
                System.out.println("Did not match" + m);*/



          /*  if (width > 960) {
                builder.moveToElement(headerItems.getmoreLink()).perform();
                Thread.sleep(3000);
                verifyImage(menu);
            } else {

               // builder.moveToElement(headerItems.getNav()).click().perform();
                headerItems.getNav().click();
                Thread.sleep(3000);
                verifyImage(menu);

            }
        }
*/


/*   @Test(dataProvider = "getDimension")
        public void menuTest(int width, int height, String baseImage, String baseImageMenu) throws FindFailed, InterruptedException, IOException {

            String ID;
            URL = prop.getProperty("URL");
            driver.manage().window().setPosition(new Point(0, 0));
            driver.manage().window().setSize(new Dimension(width, height));

            File testDataSrc = new File(prop.getProperty("testDataLocation"));
            FileInputStream testData = new FileInputStream(testDataSrc);
            XSSFWorkbook wb = new XSSFWorkbook(testData);
            XSSFSheet sheet1 = wb.getSheetAt(0);


            headerItems headerItems = new headerItems(driver);
            Actions builder = new Actions(driver);

            System.out.println("Physical Number of Rows:" + (sheet1.getPhysicalNumberOfRows() - 1));

            for (int i = 1; i < 3; i++) {

                if (width > 960) {

                    ID = sheet1.getRow(i).getCell(1).getStringCellValue();
                    driver.get(URL + "/id/" + ID);
                    builder.moveToElement(headerItems.getmoreLink()).perform();
                    Thread.sleep(3000);
                    verifyImage(baseImageMenu);

                    System.out.println("Image match Success");
                } else {

                    ID = sheet1.getRow(i).getCell(1).getStringCellValue();
                    driver.get(URL + "/id/" + ID);
                    headerItems.getNav().click();
                    Thread.sleep(3000);
                    verifyImage(baseImageMenu);
                }
            }
        }
*/
