import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by fortegs on 12/6/15.
 */
public class headerItems {

    static WebDriver driver;

    @FindBy(xpath = ".//*[@id='nav']/li[11]/a")
    WebElement moreLink;


    @FindBy(xpath = ".//*[@id='nav']")
    WebElement nav;

    public headerItems(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public WebElement getNav(){
        return nav;
    }

    public WebElement getmoreLink(){
        return moreLink;
    }
}
