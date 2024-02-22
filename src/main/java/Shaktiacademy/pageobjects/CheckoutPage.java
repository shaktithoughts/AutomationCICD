package Shaktiacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Shaktiacademy.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent  {
	
	WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}

	@FindBy (css=".ta-item:nth-of-type(2)")
    WebElement selectCountry;
	
	@FindBy (css="[placeholder='Select Country']")
    WebElement country;
	
	@FindBy (css=".action__submit")
    WebElement submit;
	
	By results = By.cssSelector(".ta-results");
	
	public void SelectCountry(String countryName)
	{
		Actions a = new Actions(driver);
        a.sendKeys(country, countryName).build().perform();
        waitForElementToAppear(results);
        selectCountry.click();
	}
	
	public ConfirmationPage submitOrder()
	{
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    js.executeScript("window.scrollBy(0,750)");
	    waitForElementToBeClickable(submit);
	    submit.click();
	    return new ConfirmationPage(driver);
		
	}
	}
	
	

