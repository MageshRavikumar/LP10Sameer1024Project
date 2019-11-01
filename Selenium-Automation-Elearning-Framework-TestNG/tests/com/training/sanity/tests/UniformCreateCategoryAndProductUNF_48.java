package com.training.sanity.tests;

import com.training.pom.AddProductPOM;
import com.training.pom.CreateCategoryPOM;
import com.training.pom.LoginPOM;
import com.training.generics.ScreenShot;
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
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UniformCreateCategoryAndProductUNF_48 {

	private String adminUrl;
	private ScreenShot ts;
	private Actions act;
	private WebDriver driver;
	private WebElement order; // temp variable for storing web element
	private LoginPOM loginPOM;
	private CreateCategoryPOM createCategoryPOM;
	private AddProductPOM addProductPOM;
	
	private static Properties properties;

	@Test
	public void createCategoryAndProduct() {
		catalogList();
		addCategory();
		productList();
		addProduct();
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

		createCategoryPOM = new CreateCategoryPOM (driver);
		addProductPOM = new AddProductPOM (driver);
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
	
	public void catalogList() {
		
		//validate list under catalog

		order = driver.findElement(By.id("catalog"));
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(order));

		act = new Actions(driver);
		act.moveToElement(order).build().perform();
		
		ts.captureScreenShot("UNF_048_CategoriesList");

		order = driver.findElement(By.xpath("//li[@id='catalog']/ul/li[1]/a"));
		Assert.assertEquals(order.getText(), "Categories");
		order = driver.findElement(By.xpath("//li[@id='catalog']/ul/li[2]/a"));
		Assert.assertEquals(order.getText(), "Products");
		order = driver.findElement(By.xpath("//li[@id='catalog']/ul/li[3]/a"));
		Assert.assertEquals(order.getText(), "Recurring Profiles");
		order = driver.findElement(By.xpath("//li[@id='catalog']/ul/li[4]/a"));
		Assert.assertEquals(order.getText(), "Filters");
		order = driver.findElement(By.xpath("//li[@id='catalog']/ul/li[5]/a"));
		Assert.assertEquals(order.getText(), "Attributes");
		order = driver.findElement(By.xpath("//li[@id='catalog']/ul/li[6]/a"));
		Assert.assertEquals(order.getText(), "Options");
		order = driver.findElement(By.xpath("//li[@id='catalog']/ul/li[7]/a"));
		Assert.assertEquals(order.getText(), "Manufacturers");
		order = driver.findElement(By.xpath("//li[@id='catalog']/ul/li[8]/a"));
		Assert.assertEquals(order.getText(), "Downloads");
		order = driver.findElement(By.xpath("//li[@id='catalog']/ul/li[9]/a"));
		Assert.assertEquals(order.getText(), "Reviews");
		order = driver.findElement(By.xpath("//li[@id='catalog']/ul/li[10]/a"));
		Assert.assertEquals(order.getText(), "Information");

		//launched category page
		driver.findElement(By.xpath("//li[@id='catalog']/ul/li[1]/a")).click();
		
		//validate category page items
		
		order = driver.findElement(By.xpath("//form[@id='form-category']//thead/tr/td[2]/a"));
		Assert.assertEquals(order.getText(), "Category Name");
		order = driver.findElement(By.xpath("//form[@id='form-category']//thead/tr/td[3]/a"));
		Assert.assertEquals(order.getText(), "Sort Order");
		order = driver.findElement(By.xpath("//form[@id='form-category']//thead/tr/td[4]"));
		Assert.assertEquals(order.getText(), "Action");
		
		//validate atleast one category is displayed
		Assert.assertTrue(driver.findElements(By.xpath("//form[@id='form-category']//tbody/tr/td[4]/a")).size()>0);
		
	}

	private void addCategory() {
		
		createCategoryPOM.clickAddBtnCategory();
		
		order =driver.findElement(By.xpath("//h3[@class='panel-title']"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));
		
		Assert.assertEquals(order.getText().trim(), "Add Category");
		
		//validating tab name
		order=driver.findElement(By.xpath("//form[@id='form-category']/ul/li[@class='active']/a"));		
		Assert.assertTrue(order.getText().trim().equals("General"));
		
		createCategoryPOM.setCategoryName("A Sports Uniform");
		createCategoryPOM.setCategoryDescription("Uniform for Sports");
		createCategoryPOM.setCategoryMetaTag("Sports Uniform");
		createCategoryPOM.setMetaTagDesc("Uniform for Sports");
		
		createCategoryPOM.clickTabData();
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		order=driver.findElement(By.xpath("//form[@id='form-category']/ul/li[@class='active']/a"));
		Assert.assertTrue(order.getText().trim().equals("Data"));
		
		createCategoryPOM.clickTabDesign();
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		order=driver.findElement(By.xpath("//form[@id='form-category']/ul/li[@class='active']/a"));
		Assert.assertTrue(order.getText().trim().equals("Design"));
		
		createCategoryPOM.clickSaveBtn();
		
		order=driver.findElement(By.xpath("//div[@id='container']//div[@class='alert alert-success']"));
		Assert.assertTrue(order.getText().trim().contains("Success: You have modified categories!"));
		
		//ts.captureScreenShot("UNF_048_CategorySaved");
	}
	
	
	private void productList() {
		
		order = driver.findElement(By.id("catalog"));
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(order));

		act = new Actions(driver);
		act.moveToElement(order).build().perform();
		
		//launched product page
		driver.findElement(By.xpath("//li[@id='catalog']/ul/li[2]/a")).click();
		
		//validate category page items
		order = driver.findElement(By.xpath("//form[@id='form-product']//thead/tr/td[2]"));
		Assert.assertEquals(order.getText(), "Image");
		order = driver.findElement(By.xpath("//form[@id='form-product']//thead/tr/td[3]/a"));
		Assert.assertEquals(order.getText(), "Product Name");
		order = driver.findElement(By.xpath("//form[@id='form-product']//thead/tr/td[4]/a"));
		Assert.assertEquals(order.getText(), "Model");
		order = driver.findElement(By.xpath("//form[@id='form-product']//thead/tr/td[5]/a"));
		Assert.assertEquals(order.getText(), "Price");
		order = driver.findElement(By.xpath("//form[@id='form-product']//thead/tr/td[6]/a"));
		Assert.assertEquals(order.getText(), "Quantity");
		order = driver.findElement(By.xpath("//form[@id='form-product']//thead/tr/td[7]/a"));
		Assert.assertEquals(order.getText(), "Status");
		order = driver.findElement(By.xpath("//form[@id='form-product']//thead/tr/td[8]"));
		Assert.assertEquals(order.getText(), "Action");
		
		//validate atleast one product is displayed
		Assert.assertTrue(driver.findElements(By.xpath("//form[@id='form-product']//tbody/tr/td[8]/a")).size()>0);
		
		//ts.captureScreenShot("UNF_048_ProductList");
	}
	
	private void addProduct() {
		
		addProductPOM.clickAddBtnProduct();
		
		order =driver.findElement(By.xpath("//h3[@class='panel-title']"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));
		
		Assert.assertEquals(order.getText().trim(), "Add Product");
		
		//validating tab name
		order=driver.findElement(By.xpath("//form[@id='form-product']/ul/li[@class='active']/a"));		
		Assert.assertTrue(order.getText().trim().equals("General"));
		
		addProductPOM.setProductName("Sports Wear");
		Assert.assertEquals(driver.findElement(By.id("input-name1")).getAttribute("value"), "Sports Wear");
		addProductPOM.setProductMetaTag("Sports Wear (8-10)");
		Assert.assertEquals(driver.findElement(By.id("input-meta-title1")).getAttribute("value"), "Sports Wear (8-10)");
		
		//validating tab name
		driver.findElement(By.xpath("//form[@id='form-product']/ul/li[2]/a")).click();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		order=driver.findElement(By.xpath("//form[@id='form-product']/ul/li[@class='active']/a"));
		Assert.assertTrue(order.getText().trim().equals("Data"));
		
		addProductPOM.setProductModel("SPU-03");
		Assert.assertEquals(driver.findElement(By.id("input-model")).getAttribute("value"), "SPU-03");
		
		//validating tab name
		driver.findElement(By.xpath("//form[@id='form-product']/ul/li[3]/a")).click();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		order=driver.findElement(By.xpath("//form[@id='form-product']/ul/li[@class='active']/a"));
		Assert.assertTrue(order.getText().trim().equals("Links"));
		
		addProductPOM.setProductCategory("Sports Uniform");
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@id='input-category']/following-sibling::ul/li/a"))));
		addProductPOM.selectProduct("Sports Uniform");
		
		order = driver.findElement(By.xpath("//div[@id='product-category']/div"));
		Assert.assertEquals(order.getText(), "Sports Uniform");
		
		//saving product
		addProductPOM.clickSaveBtn();
		order=driver.findElement(By.xpath("//div[@id='container']//div[@class='alert alert-success']"));
		Assert.assertTrue(order.getText().trim().contains("Success: You have modified products!"));
		
		//ts.captureScreenShot("UNF_048_ProductSaved");
	}

}
