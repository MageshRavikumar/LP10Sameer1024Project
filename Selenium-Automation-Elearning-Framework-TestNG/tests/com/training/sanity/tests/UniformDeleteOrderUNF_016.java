package com.training.sanity.tests;

import org.testng.annotations.Test;

import com.training.generics.ScreenShot;
import com.training.pom.AddUniformPOM;
import com.training.pom.LoginPOM;
import com.training.pom.OrderPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class UniformDeleteOrderUNF_016 {

	private  WebDriver driver;
	private String baseUrl;
	private String adminUrl;
	private LoginPOM loginPOM;
	private AddUniformPOM addUniformPOM;
	private OrderPOM deleteOrderPOM;
	private static Properties properties;

	@Test 
	public void deleteOrder() {
		
		deleteOrderPOM = new OrderPOM(driver);
		deleteOrderPOM.validatestep1();
		deleteOrderPOM.orderList();
		deleteOrderPOM.validatestep2();
		deleteOrderPOM.validatestep3();		
		deleteOrderPOM.validateDeleteOrder();	
	}

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);		
	}

	@BeforeMethod
	public void setUp() throws Exception {
		
		driver = DriverFactory.getDriver(DriverNames.CHROME);
		baseUrl = properties.getProperty("baseURL");
		adminUrl = properties.getProperty("adminURL");
		
		//Adding a product
		//driver.get(baseUrl);
		//addUniformPOM=new AddUniformPOM(driver);
		//addUniformPOM.selectUniform();
		//addUniformPOM.checkout();
		
		
		//Login In ADMIN
		driver.get(adminUrl);
		loginPOM = new LoginPOM(driver);
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		Assert.assertEquals(driver.findElement(By.xpath("//h1[contains(text(),'Dashboard')]")).getText(), "Dashboard");
		Assert.assertTrue(driver.findElement(By.xpath("//a/i[@class='fa fa-sign-out fa-lg']")).isDisplayed());
	}

	@AfterMethod
	public void afterMethod() {
		driver.findElement(By.xpath("//li[@id='dashboard']/a")).click();
		
	}

	@AfterClass
	public void afterClass() {
		driver.findElement(By.xpath("//*[@id='header']//span[@class='hidden-xs hidden-sm hidden-md']")).click();
		driver.close();
	}

}
