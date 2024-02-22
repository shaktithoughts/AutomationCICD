package Shaktiacademy.tests;


import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Shaktiacademy.TestComponents.BaseTest;
import Shaktiacademy.pageobjects.CartPage;
import Shaktiacademy.pageobjects.CheckoutPage;
import Shaktiacademy.pageobjects.ConfirmationPage;
import Shaktiacademy.pageobjects.LandingPage;
import Shaktiacademy.pageobjects.OrdersPage;
import Shaktiacademy.pageobjects.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest extends BaseTest {
	
	String productName = "ZARA COAT 3";

	@Test(dataProvider="getData", groups= {"purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException
	{
		
		
		ProductCatalogue ProductCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = ProductCatalogue.getProductList();
		ProductCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage  = ProductCatalogue.goToCartPage();
        Boolean match = cartPage.verifyProductDisplay(input.get("product"));
        Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.SelectCountry("India");
        ConfirmationPage confirmationPage = checkoutPage.submitOrder();
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void  checkorder()
	{
		ProductCatalogue ProductCatalogue = landingPage.loginApplication("mishra.shakti2023@gmail.com", "Vikramrathore@123");
		OrdersPage ordersPage= ProductCatalogue.goToOrderPage();
		Assert.assertTrue(ordersPage.verifyOrderDisplay(productName));
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\Shaktiacademy\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	
//	HashMap<String, String> map = new HashMap<String, String>();
//	map.put("email", "mishra.shakti2023@gmail.com");
//	map.put("password", "Vikramrathore@123");
//	map.put("product", "ZARA COAT 3");
//	
//	HashMap<String, String> map1 = new HashMap<String, String>();
//	map1.put("email", "shaktisworup1998@gmail.com");
//	map1.put("password", "Shakti@07");
//	map1.put("product", "ADIDAS ORIGINAL");
}
