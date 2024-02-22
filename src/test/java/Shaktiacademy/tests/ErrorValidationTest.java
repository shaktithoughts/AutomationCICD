package Shaktiacademy.tests;


import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import Shaktiacademy.TestComponents.BaseTest;
import Shaktiacademy.pageobjects.CartPage;
import Shaktiacademy.pageobjects.ProductCatalogue;

public class ErrorValidationTest extends BaseTest {

	@Test(groups= {"ErrorHandling"})
	public void loginErrorValidation() throws IOException, InterruptedException
	{
		
		landingPage.loginApplication("miss.shakti2023@gmail.com", "Vil@123");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
	
	@Test
	public void productErrorValidation() throws IOException, InterruptedException
	{
		
		String productName = "ZARA COAT 3";
		ProductCatalogue ProductCatalogue = landingPage.loginApplication("shaktisworup1998@gmail.com", "Shakti@07");
		List<WebElement> products = ProductCatalogue.getProductList();
		ProductCatalogue.addProductToCart(productName);
		CartPage cartPage  = ProductCatalogue.goToCartPage();
        Boolean match = cartPage.verifyProductDisplay("ZARA COAT 3333");
        Assert.assertFalse(match);
    }
}
