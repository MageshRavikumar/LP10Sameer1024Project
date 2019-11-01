package com.training.sanity.tests;

import com.training.pom.LoginPOM;
import com.training.pom.OrderEditPOM;
import com.training.generics.ScreenShot;

import com.training.pom.OrderPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

import org.testng.Assert;
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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UniformEditOrderUNF_46 {

	private WebDriver driver;
	private ScreenShot ts;
	private WebElement order; // temp variable for storing web element
	private String adminUrl;
	private LoginPOM loginPOM;
	private OrderPOM filterOrderPOM;
	private OrderEditPOM editOrderPOM;
	private static Properties properties;

	@Test
	public void modifyOrder() {
		filterOrderPOM.orderList();
		filterOrderPOM.validatestep1();
		filterOrderPOM.validatestep2();
		filterOrderPOM.searchByID("120");
		selectEdit();
		editOrder();
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

		filterOrderPOM = new OrderPOM(driver);
		editOrderPOM = new OrderEditPOM(driver);
		ts = new ScreenShot(driver);
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
	
	private void selectEdit() {
		//clicking edit button on filtered order 
		driver.findElement(By.xpath("//form[@id='form-order']//table/tbody/tr/td[8]/a[@data-original-title='Edit']")).click();		
	}
	
	private void editOrder() {
		String product;
		
		//validating edit page
		
		order = driver.findElement(By.xpath("//h3[@class='panel-title']"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));	

		Assert.assertEquals(order.getText().trim(), "Edit Order");
		
		//ts.captureScreenShot("UNF_046_EditPage");
		
		driver.findElement(By.xpath("//button[@id='button-customer']")).click();
		driver.findElement(By.xpath("//button[@id='button-customer']")).click();
		
		if (driver.findElement(By.xpath("//button[@id='button-customer']")).isEnabled()) {
			System.out.println("still displayed");
			editOrderPOM.clickNextBtnCustomer();
		}
		
		System.out.println("Going to sleep for handling next button issue....");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//validating product page
		
		order = driver.findElement(By.xpath("//ul[@id='order']/li[2]/a[@aria-expanded='true']"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));
		
		Assert.assertEquals(order.getText().trim(), "2. Products");
		
		//product to be removed
		order=driver.findElement(By.xpath("//tbody[@id='cart']/tr/td"));
		product=order.getText().trim();	
		
		
		editOrderPOM.clickRemoveBtnProduct();

		(new WebDriverWait(driver, 20)).until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//tbody[@id='cart']/tr/td"),product));
		
		order=driver.findElement(By.xpath("//tbody[@id='cart']/tr/td"));
		Assert.assertNotEquals(order.getText().trim(), product);
		System.out.println("product deleted : "+ product +" after: "+order.getText());
		
		//ts.captureScreenShot("UNF_046_ProductDeleted");
		
		editOrderPOM.addProduct("regular");
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@id='input-product']/following-sibling::ul/li/a"))));
		editOrderPOM.selectProduct("Rust");
		editOrderPOM.addQuantity("1");
		editOrderPOM.selectSize("971");
		
		editOrderPOM.clickAddBtnProduct();
		
		order = driver.findElement(By.xpath("//tbody[@id='cart']/tr/td[2]"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));
		
		order = driver.findElement(By.xpath("//tbody[@id='cart']/tr/td[1]"));
		System.out.println("added product: "+order.getText());
		Assert.assertTrue(order.getText().toLowerCase().contains("regular"));
		Assert.assertTrue(order.getText().contains("Rust"));
		
		//ts.captureScreenShot("UNF_046_ProductAdded");

		editOrderPOM.clickNextBtnCart();
		
		//validating payment page
		
		order = driver.findElement(By.xpath("//ul[@id='order']/li[3]/a[@aria-expanded='true']"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));
		
		Assert.assertEquals(order.getText().trim(), "3. Payment Details");
		
		//order = driver.findElement(By.xpath("//div[@id='content']//div[@class='alert alert-success']"));		
		//Assert.assertEquals(order.getText().trim(), "Success: You have modified your shopping cart!");
		
		editOrderPOM.clickNextBtnPayment();
		
		//validating shipping page
		
		order = driver.findElement(By.xpath("//ul[@id='order']/li[4]/a[@aria-expanded='true']"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));
		
		Assert.assertEquals(order.getText().trim(), "4. Shipping Details");
		
		editOrderPOM.clickNextBtnAddress();
		
		
		//validating shipping page
		
		order = driver.findElement(By.xpath("//ul[@id='order']/li[5]/a[@aria-expanded='true']"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));
		
		Assert.assertEquals(order.getText().trim(), "5. Totals");
		
		//validating product added
		order = driver.findElement(By.xpath("//tbody[@id='total']/tr[1]/td[1]"));
		Assert.assertTrue(order.getText().toLowerCase().contains("regular"));
		Assert.assertTrue(order.getText().contains("Rust"));

		editOrderPOM.selectShippingMethod("free.free");
		
		editOrderPOM.clickSaveBtn();
		
		order = driver.findElement(By.xpath("//div[@id='container']//div[@class='alert alert-success']"));		
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.textToBePresentInElement(order, "Success: You have modified orders!"));
		
		Assert.assertTrue(order.getText().trim().contains("Success: You have modified orders!"));
		
		//ts.captureScreenShot("UNF_046_ProductSaved");
		
		driver.findElement(By.xpath("//div[@id='container']//div[@class='alert alert-success']/button")).click();;
		driver.findElement(By.xpath("//div[@id='content']//a[text()=' Cancel']")).click();

	} 

}
