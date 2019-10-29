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

public class ReturnOrderPOM {
	private List<WebElement> orderList;
	private WebElement order;
	private WebDriver driver;
	private Actions act;
	private ScreenShot ts;

	public ReturnOrderPOM(WebDriver driver) {
		this.driver = driver;
		this.ts = new ScreenShot(this.driver);
	}

	public void validateReturnOrderList() {
		order = driver.findElement(By.id("sale"));
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(order));

		act = new Actions(driver);
		act.moveToElement(order).build().perform();

		order = driver.findElement(By.xpath("//li[@id='sale']/ul/li[3]/a"));
		order.click();

		// validating table column names
		order = driver.findElement(By.xpath("//h3[@class='panel-title']"));
		Assert.assertEquals(order.getText().trim(), "Product Return List");

		order = driver.findElement(By.xpath("//form[@id='form-return']//table/thead/tr/td[2]/a"));
		Assert.assertEquals(order.getText().trim(), "Return ID");

		order = driver.findElement(By.xpath("//form[@id='form-return']//table/thead/tr/td[3]/a"));
		Assert.assertEquals(order.getText().trim(), "Order ID");

		order = driver.findElement(By.xpath("//form[@id='form-return']//table/thead/tr/td[10]"));
		Assert.assertEquals(order.getText().trim(), "Action");

		orderList = driver.findElements(By.xpath("//form[@id='form-return']//table/tbody/tr/td[2]"));
		Assert.assertTrue(orderList.size() > 0);

		order = driver.findElement(By.xpath("//form[@id='form-return']//table/tbody/tr/td[10]/a"));
		Assert.assertTrue(order.isEnabled());

		// taking screenshot
		ts.captureScreenShot("UNF_018_retrunOrderList");

	}

	public void searchByID() {

		// searching with id
		order = driver.findElement(By.xpath("//input[@id='input-return-id']"));
		order.clear();
		order.sendKeys("186");

		Assert.assertTrue(order.getAttribute("value").trim().equals("186"));

		// taking screenshot
		ts.captureScreenShot("UNF_018_inputID");

		// filter return order based on id
		driver.findElement(By.xpath("//button[@id='button-filter']")).click();

		order = driver.findElement(By.xpath("//form[@id='form-return']//table/tbody/tr/td[2]"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));

		// taking screenshot
		ts.captureScreenShot("UNF_018_returnOrderListByID");

		orderList = driver.findElements(By.xpath("//form[@id='form-return']//table/tbody/tr/td[2]"));

		if (orderList.size() == 0)
			Assert.fail();
		else {
			for (WebElement order : orderList) {
				if (!order.getText().equals("186")) {
					Assert.fail();
					break;
				} else if (orderList.size() == 1) {
					// validating record
					Assert.assertTrue(order.findElement(By.xpath("//form[@id='form-return']//table/tbody/tr[1]/td[3]"))
							.getText().trim().equals("53"));
					Assert.assertTrue(order.findElement(By.xpath("//form[@id='form-return']//table/tbody/tr[1]/td[4]"))
							.getText().trim().equals("Shiva Bharadwaj"));
					Assert.assertTrue(order.findElement(By.xpath("//form[@id='form-return']//table/tbody/tr[1]/td[5]"))
							.getText().trim().equals("Girl Blazer"));
					Assert.assertTrue(order.findElement(By.xpath("//form[@id='form-return']//table/tbody/tr[1]/td[6]"))
							.getText().trim().equals("Girl Blazer"));
					Assert.assertTrue(order.findElement(By.xpath("//form[@id='form-return']//table/tbody/tr[1]/td[7]"))
							.getText().trim().equals("Awaiting Products"));
					Assert.assertTrue(order.findElement(By.xpath("//form[@id='form-return']//table/tbody/tr[1]/td[8]"))
							.getText().trim().equals("09/08/2019"));
					Assert.assertTrue(order.findElement(By.xpath("//form[@id='form-return']//table/tbody/tr[1]/td[9]"))
							.getText().trim().equals("09/08/2019"));
				}
			}
		}
	}

	public void searchByCustomer() {
		// clearing id filed for new search
		order = driver.findElement(By.xpath("//input[@id='input-return-id']"));
		order.clear();

		// searching with customer
		order = driver.findElement(By.xpath("//input[@id='input-customer']"));
		order.click();
		order.clear();
		order.sendKeys("arun p");

		Assert.assertTrue(order.getAttribute("value").trim().equals("arun p"));

		// taking screenshot
		ts.captureScreenShot("UNF_018_inputCustomer");

		// filter return order based on id
		driver.findElement(By.xpath("//button[@id='button-filter']")).click();

		order = driver.findElement(By.xpath("//form[@id='form-return']//table/tbody/tr/td[3]"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));

		// taking screenshot
		ts.captureScreenShot("UNF_018_returnOrderListByCustomer");

		orderList = driver.findElements(By.xpath("//form[@id='form-return']//table/tbody/tr/td[4]"));

		if (orderList.size() == 0)
			Assert.fail();
		else {
			for (WebElement order : orderList) {
				int row = 1;
				if (!order.getText().equals("arun p")) {
					Assert.fail();
					break;
				} else {
					// validating record
					Assert.assertTrue(order
							.findElement(By.xpath(
									"//form[@id='form-return']//table/tbody/tr[" + String.valueOf(row) + "]/td[2]"))
							.isDisplayed());
					Assert.assertTrue(order
							.findElement(By.xpath(
									"//form[@id='form-return']//table/tbody/tr[" + String.valueOf(row) + "]/td[3]"))
							.isDisplayed());
					Assert.assertTrue(order
							.findElement(By.xpath(
									"//form[@id='form-return']//table/tbody/tr[" + String.valueOf(row) + "]/td[5]"))
							.isDisplayed());
					Assert.assertTrue(order
							.findElement(By.xpath(
									"//form[@id='form-return']//table/tbody/tr[" + String.valueOf(row) + "]/td[6]"))
							.isDisplayed());
					Assert.assertTrue(order
							.findElement(By.xpath(
									"//form[@id='form-return']//table/tbody/tr[" + String.valueOf(row) + "]/td[7]"))
							.isDisplayed());
					Assert.assertTrue(order
							.findElement(By.xpath(
									"//form[@id='form-return']//table/tbody/tr[" + String.valueOf(row) + "]/td[8]"))
							.isDisplayed());
					Assert.assertTrue(order
							.findElement(By.xpath(
									"//form[@id='form-return']//table/tbody/tr[" + String.valueOf(row) + "]/td[9]"))
							.isDisplayed());
				}

			}
		}

	}
}
