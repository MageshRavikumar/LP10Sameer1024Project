package com.training.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.training.generics.ScreenShot;

public class CustomerListPOM {

	private List<WebElement> orderList;
	private WebElement order;
	private WebDriver driver;
	private Actions act;
	private ScreenShot ts;

	public CustomerListPOM(WebDriver driver) {
		this.driver = driver;
		this.ts = new ScreenShot(this.driver);
	}

	public void customerList() {
		order = driver.findElement(By.id("customer"));
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(order));

		act = new Actions(driver);
		act.moveToElement(order).build().perform();

		order = driver.findElement(By.xpath("//li[@id='customer']/ul/li[1]/a"));
		order.click();

		// validating table column names
		order = driver.findElement(By.xpath("//h3[@class='panel-title']"));
		Assert.assertEquals(order.getText().trim(), "Customer List");

		order = driver.findElement(By.xpath("//form[@id='form-customer']//table/thead/tr/td[2]/a"));
		Assert.assertEquals(order.getText().trim(), "Customer Name");

		order = driver.findElement(By.xpath("//form[@id='form-customer']//table/thead/tr/td[3]/a"));
		Assert.assertEquals(order.getText().trim(), "E-Mail");

		order = driver.findElement(By.xpath("//form[@id='form-customer']//table/thead/tr/td[4]/a"));
		Assert.assertEquals(order.getText().trim(), "Customer Group");

		order = driver.findElement(By.xpath("//form[@id='form-customer']//table/thead/tr/td[8]"));
		Assert.assertEquals(order.getText().trim(), "Action");

		orderList = driver.findElements(By.xpath("//form[@id='form-customer']//table/tbody/tr/td[2]"));
		Assert.assertTrue(orderList.size() > 0);

		order = driver.findElement(By.xpath("//form[@id='form-customer']//table/tbody/tr/td[8]/a"));
		Assert.assertTrue(order.isEnabled());

		// taking screenshot
		ts.captureScreenShot("UNF_017_CustomerList");

	}

	public void searchByCustomer() {

		// searching with customer
		order = driver.findElement(By.xpath("//input[@id='input-name']"));
		order.clear();
		order.sendKeys("arun p");

		Assert.assertTrue(order.getAttribute("value").trim().equals("arun p"));

		// taking screenshot
		ts.captureScreenShot("UNF_017_inputCustomer");

		// filter return order based on customer
		driver.findElement(By.xpath("//button[@id='button-filter']")).click();

		order = driver.findElement(By.xpath("//form[@id='form-customer']//table/tbody/tr/td[2]"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));

		// taking screenshot
		ts.captureScreenShot("UNF_017_filteredCustomerList");

		orderList = driver.findElements(By.xpath("//form[@id='form-customer']//table/tbody/tr/td[2]"));

		if (orderList.size() == 0)
			Assert.fail();
		else if (orderList.size() == 1) {
			// validating single record
			Assert.assertTrue(order.findElement(By.xpath("//form[@id='form-customer']//table/tbody/tr[1]/td[2]"))
					.getText().trim().equals("arun p"));
			Assert.assertTrue(order.findElement(By.xpath("//form[@id='form-customer']//table/tbody/tr[1]/td[3]"))
					.getText().trim().equals("arun.test@gmail.com"));
			Assert.assertTrue(order.findElement(By.xpath("//form[@id='form-customer']//table/tbody/tr[1]/td[4]"))
					.getText().trim().equals("Default"));
			Assert.assertTrue(order.findElement(By.xpath("//form[@id='form-customer']//table/tbody/tr[1]/td[5]"))
					.getText().trim().equals("Enabled"));
			Assert.assertTrue(order.findElement(By.xpath("//form[@id='form-customer']//table/tbody/tr[1]/td[6]"))
					.getText().trim().equals("129.41.84.92"));
			Assert.assertTrue(order.findElement(By.xpath("//form[@id='form-customer']//table/tbody/tr[1]/td[7]"))
					.getText().trim().equals("10/09/2019"));
		} else {
			for (WebElement order : orderList) {
				if (!order.getText().equals("arun p")) {
					Assert.fail();
					break;
				}
			}
		}
	}

}
