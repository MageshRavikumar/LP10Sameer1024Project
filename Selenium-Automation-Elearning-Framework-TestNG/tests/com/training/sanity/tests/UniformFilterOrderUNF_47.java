package com.training.sanity.tests;

import com.training.pom.LoginPOM;
import com.training.pom.CustomerListPOM;
import com.training.pom.OrderPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UniformFilterOrderUNF_47 {

	private WebDriver driver;
	private String adminUrl;
	private LoginPOM loginPOM;
	private OrderPOM filterOrderPOM;
	private static Properties properties;

	@Test
	public void filterOrder() {
		filterOrderPOM.orderList();
		filterOrderPOM.validatestep1();
		filterOrderPOM.validatestep2();
		filterOrderPOM.searchByID();
		filterOrderPOM.searchByStatus();
		filterOrderPOM.searchByCustomer();
		filterOrderPOM.searchByTotalPrice();
		filterOrderPOM.searchByAddedDate();
		filterOrderPOM.searchByModifiedDate();
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
		adminUrl = properties.getProperty("adminURL");

		// Login In ADMIN
		driver.get(adminUrl);
		loginPOM = new LoginPOM(driver);
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();

		filterOrderPOM = new OrderPOM (driver);

	}

	@AfterMethod
	public void afterMethod() {
		//driver.findElement(By.xpath("//li[@id='dashboard']/a")).click();
	}

	@AfterClass
	public void afterClass() {
		//driver.findElement(By.xpath("//*[@id='header']//span[@class='hidden-xs hidden-sm hidden-md']")).click();
		//driver.close();
	}

}
