package Shaktiacademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Shaktiacademy.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;
	
	
	public WebDriver initializeDriver() throws IOException
	
	{
		//properties class
		Properties pro = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+
				"\\src\\main\\java\\Shaktiacademy\\resources\\GlobalData.properties");
		pro.load(fis);
		String BrowserName = System.getProperty("browser")!=null ? System.getProperty("browser") :pro.getProperty("browser");
		
		if(BrowserName.contains("chrome")) {
	    ChromeOptions options = new ChromeOptions();
	    WebDriverManager.chromedriver().setup();
	    if(BrowserName.contains("headless")) {
	    options.addArguments("headless");
		}
		driver=new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1440,900));// full screen
		}
		else if(BrowserName.equalsIgnoreCase("firefox"))
		{
			
		}
		else if(BrowserName.equalsIgnoreCase("edge"))
		{
			EdgeOptions opt = new EdgeOptions();
			opt.addArguments("--guest");
			WebDriverManager.edgedriver().setup();
			driver=new EdgeDriver(opt);
		}
	
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}

		
		public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
		{
			//read Json to String
			String JsonContent= FileUtils.readFileToString(new File(filePath), 
					StandardCharsets.UTF_8);
		
			// String to HashMap Jackson Databind
			ObjectMapper mapper = new ObjectMapper();
			List<HashMap<String, String>> data = mapper.readValue(JsonContent, new TypeReference<List<HashMap<String, String>>>(){});
			return data;
					
		}
		
		public String getScreenshot(String testCaseName) throws IOException
		{
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			File file = new File(System.getProperty("User.dir") + "//reports//" + testCaseName + ".png");
			FileUtils.copyFile(source, file);
			return System.getProperty("User.dir") + "//reports//" + testCaseName + ".png";
				
		}
		
		
		
		
		
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.quit();
	}

}
