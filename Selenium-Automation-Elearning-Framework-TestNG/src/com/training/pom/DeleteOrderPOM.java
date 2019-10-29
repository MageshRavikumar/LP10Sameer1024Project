package com.training.pom;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.training.generics.ScreenShot;

public class DeleteOrderPOM {
	private WebElement order;
	private WebDriver driver;
	private Actions act;
	private Alert alert;
	private int index = 0;
	private ScreenShot ts;

	public DeleteOrderPOM(WebDriver driver) {
		this.driver = driver;
		this.ts = new ScreenShot(this.driver);
	}

	public void validatestep1() {

		order = driver.findElement(By.id("sale"));
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(order));

		act = new Actions(driver);
		act.moveToElement(order).build().perform();

		ts.captureScreenShot("UNF_016_step1");

		order = driver.findElement(By.xpath("//li[@id='sale']/ul/li[1]/a"));
		Assert.assertEquals(order.getText(), "Orders");
		order = driver.findElement(By.xpath("//li[@id='sale']/ul/li[2]/a"));
		Assert.assertEquals(order.getText(), "Recurring Orders");
		order = driver.findElement(By.xpath("//li[@id='sale']/ul/li[3]/a"));
		Assert.assertEquals(order.getText(), "Returns");
		order = driver.findElement(By.xpath("//li[@id='sale']/ul/li[4]/a"));
		Assert.assertEquals(order.getText(), "Gift Vouchers");
		order = driver.findElement(By.xpath("//li[@id='sale']/ul/li[5]/a"));
		Assert.assertEquals(order.getText(), "PayPal");

	}

	public void validatestep2() {

		order = driver.findElement(By.xpath("//li[@id='sale']/ul/li/a[text()='Orders']"));
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(order));

		act = new Actions(driver);
		act.moveToElement(order).build().perform();
		order.click();

		// validating table column names
		order = driver.findElement(By.xpath("//h3[@class='panel-title']"));
		Assert.assertEquals(order.getText().trim(), "Order List");

		order = driver.findElement(By.xpath("//form[@id='form-order']//table/thead/tr/td[2]/a"));
		Assert.assertEquals(order.getText().trim(), "Order ID");

		order = driver.findElement(By.xpath("//form[@id='form-order']//table/thead/tr/td[3]/a"));
		Assert.assertEquals(order.getText().trim(), "Customer");

		order = driver.findElement(By.xpath("//form[@id='form-order']//table/thead/tr/td[4]/a"));
		Assert.assertEquals(order.getText().trim(), "Status");

		order = driver.findElement(By.xpath("//form[@id='form-order']//table/thead/tr/td[5]/a"));
		Assert.assertEquals(order.getText().trim(), "Total");

		order = driver.findElement(By.xpath("//form[@id='form-order']//table/thead/tr/td[6]/a"));
		Assert.assertEquals(order.getText().trim(), "Date Added");

		order = driver.findElement(By.xpath("//form[@id='form-order']//table/thead/tr/td[7]/a"));
		Assert.assertEquals(order.getText().trim(), "Date Modified");

		order = driver.findElement(By.xpath("//form[@id='form-order']//table/thead/tr/td[8]"));
		Assert.assertEquals(order.getText().trim(), "Action");
		
		order =driver.findElement(By.xpath("//form[@id='form-order']//table/tbody/tr/td[2]"));
		Assert.assertTrue(order.isDisplayed());

	}

	public void validatestep3() {
		List<WebElement> orderids;
		String xpath;

		// search order
		orderids = driver.findElements(By.xpath("//form[@id='form-order']//table/tbody/tr/td[3]"));

		for (WebElement order : orderids) {
			if (order.getText().trim().equals("Admin Test")) {
				index = orderids.indexOf(order);
				break;
			}
		}

		// identified row of order to be deleted
		order = orderids.get(index);
		Assert.assertEquals(order.getText(), "Admin Test");

		// creating xpath for row
		xpath = "//form[@id='form-order']//table/tbody/tr[" + String.valueOf(index + 1) + "]";

		// selecting row for deletion
		order = driver.findElement(By.xpath(xpath + "/td[1]/input[@type='checkbox']"));
		order.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Assert.assertTrue(order.isSelected());
		ts.captureScreenShot("UNF_016_step2-3");
		
	}

	public void validateDeleteOrder(){
		String xpath = "//form[@id='form-order']//table/tbody/tr[" + String.valueOf(index + 1) + "]";
		
		//storing order id for deleting order 
		String deletedorder=driver.findElement(By.xpath("//form[@id='form-order']//table/tbody/tr[" + String.valueOf(index + 1) + "]/td[2]")).getText();
		System.out.println("index: "+index+ " deleted order: "+deletedorder);
		
		// clicking delete button
		order = driver.findElement(By.xpath(xpath + "/td[8]/button"));
		order.click();
		//ts.captureScreenShot("UNF_016_step4");
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// handling alert and  validating pop up message
		try {
			alert = driver.switchTo().alert();
			Assert.assertEquals(alert.getText(), "Are you sure?");
			alert.accept();		
		}catch(Exception e) {
			System.out.println("no such alert found");
		}
		
		//step 5 validations continues
		driver.switchTo().defaultContent();
		
		//waiting for record to be removed from display 
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//form[@id='form-order']//table/tbody/tr[" + String.valueOf(index + 1) + "]/td[2]"), deletedorder));
		
		//new record after deletion 
		order = driver.findElement(By.xpath("//form[@id='form-order']//table/tbody/tr[" + String.valueOf(index + 1) + "]/td[2]"));
		
		//validating record deleted from table row
		Assert.assertNotEquals(order.getText(),deletedorder);		
		System.out.println("should not match on index: "+index+" "+deletedorder+" "+order.getText());
		ts.captureScreenShot("UNF_016_step4-5");
		
	}
}
