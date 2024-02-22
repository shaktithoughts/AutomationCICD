package Shaktiacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Shaktiacademy.AbstractComponents.AbstractComponent;

public class OrdersPage extends AbstractComponent {
	
	WebDriver driver;
	
	public OrdersPage(WebDriver driver)
	{ 
		//Initialization
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);	
	}
	
	@FindBy(css="tr td:nth-child(3)")
	List<WebElement> orderProducts;
	
	
	public Boolean verifyOrderDisplay(String productName)
	{
	    Boolean match = orderProducts.stream().anyMatch(cartProduct->cartProduct.getText().
	    equalsIgnoreCase(productName));
	    return match;
	}

	
	

}
