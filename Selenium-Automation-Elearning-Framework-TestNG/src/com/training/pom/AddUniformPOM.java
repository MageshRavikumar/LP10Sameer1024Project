package com.training.pom;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AddUniformPOM {
	private WebDriver driver; 
	
	public AddUniformPOM(WebDriver driver) {
		this.driver = driver; 
	}
	
	public void selectUniform() {
		WebElement uniform;
		
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,600)");
		
		uniform = driver.findElement(By.xpath("//a/img[@title = 'REGULAR T-SHIRTS (Rust)']"));
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(uniform));
		
		Actions act=new Actions(driver);
		act.moveToElement(uniform).build().perform();
		
		uniform = driver.findElement(By.xpath("//div/h4/a[contains(text(),'Rust')]/parent::h4/parent::div/following-sibling::div/button[@class='add_cart']/span"));
		Assert.assertEquals(uniform.getText(), "ADD TO CART");
		uniform.click();
		
		
		uniform=driver.findElement(By.xpath("//select[@id='input-option376']"));
		Select selectsize = new Select(uniform);
		selectsize.selectByValue("969");
		
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("button-cart"))));
		driver.findElement(By.id("button-cart")).click();
		
		uniform = driver.findElement(By.xpath("//div[@id='cart']/button/span[@id='cart-total']"));

		//Assert.assertTrue(uniform.getText().contains("1 item(s)"));
		//Assert.assertTrue(uniform.getText().contains("525"));
		uniform.click();
		driver.findElement(By.xpath("//*[@id='cart']/ul/li[2]/div/p/a[2]/strong[contains(text(),'Checkout')]")).click();
	}
	
	public void checkout() {
		
		String xpath = "//div[@id='accordion']/div/div[@class='panel-collapse collapse in']";
		Select element;
		
		//Step 1: Checkout Options 
		driver.findElement(By.xpath(xpath+"//input[@type='radio'][@value='guest']")).click();
		driver.findElement(By.xpath(xpath+"//input[@type='button'][@value='Continue']")).click();
		
		//Step 2: Billing Details
		driver.findElement(By.xpath(xpath+"//input[@type='text'][@name='firstname']")).sendKeys("Admin");
		driver.findElement(By.xpath(xpath+"//input[@type='text'][@name='lastname']")).sendKeys("Test");
		driver.findElement(By.xpath(xpath+"//input[@type='text'][@name='email']")).sendKeys("admin@test.com");
		driver.findElement(By.xpath(xpath+"//input[@type='text'][@name='telephone']")).sendKeys("111-111-1111");
		driver.findElement(By.xpath(xpath+"//input[@type='text'][@name='address_1']")).sendKeys("Manipal Training");
		driver.findElement(By.xpath(xpath+"//input[@type='text'][@name='city']")).sendKeys("Bengaluru");
		driver.findElement(By.xpath(xpath+"//input[@type='text'][@name='postcode']")).sendKeys("560029");
		
		element = new Select(driver.findElement(By.xpath(xpath+"//select[@id='input-payment-zone']")));
		element.selectByVisibleText("Karnataka");
		
		driver.findElement(By.xpath(xpath+"//input[@type='button'][@value='Continue']")).click();
		
		//Step 4: Delivery Method
		driver.findElement(By.xpath(xpath+"//input[@type='button'][@value='Continue']")).click();
		
		//Step 5: Payment Method
		driver.findElement(By.xpath(xpath+"//input[@type='checkbox'][@name='agree']")).click();
		driver.findElement(By.xpath(xpath+"//input[@type='button'][@value='Continue']")).click();
		
		//Step 6: Confirm Order
		driver.findElement(By.xpath(xpath+"//input[@type='button'][@value='Confirm Order']")).click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@class='buttons']/div/a[text()='Continue']"))));
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='content']/h1")).getText(), "YOUR ORDER HAS BEEN PLACED!");
		driver.findElement(By.xpath("//div[@class='buttons']/div/a[text()='Continue']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
}
