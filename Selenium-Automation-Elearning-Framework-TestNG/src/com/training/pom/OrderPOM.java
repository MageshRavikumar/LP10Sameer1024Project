package com.training.pom;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.training.generics.ScreenShot;

public class OrderPOM {
	private WebElement order; // temp variable for storing web element
	private List<WebElement> orderList; // temp variable for storing web element list
	private WebDriver driver;
	private Actions act;
	private Alert alert;
	private ScreenShot ts;
	private int index = 0;
	

	public OrderPOM(WebDriver driver) {
		this.driver = driver;
		this.ts = new ScreenShot(this.driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="input-order-id")
	private WebElement orderID; 
	
	@FindBy(id="input-order-status")
	private WebElement orderStatus;
	
	@FindBy(id="input-customer")
	private WebElement orderCustomer;
	
	@FindBy(id="input-total")
	private WebElement orderTotal;
	
	@FindBy(id="input-date-added")
	private WebElement orderAddedDate;
	
	@FindBy(id="input-date-modified")
	private WebElement orderModifiedDate;
	
	public void sendOrderID(String orderID) {
		this.orderID.clear();
		this.orderID.sendKeys(orderID);
	}
	
	public void sendOrderStatus(String status) {
		new Select(this.orderStatus).selectByValue(status);
	}
	
	public void sendOrderCustomer(String customer) {
		this.orderCustomer.clear();
		this.orderCustomer.sendKeys(customer);
	}
	
	public void sendTotalPrice(String total) {
		this.orderTotal.clear();
		this.orderTotal.sendKeys(total);
	}
	
	public void sendAddedDate(String addedDate) {
		this.orderAddedDate.clear();
		this.orderAddedDate.sendKeys(addedDate);
	}
	
	public void sendModifiedDate(String modifiedDate) {
		this.orderModifiedDate.clear();
		this.orderModifiedDate.sendKeys(modifiedDate);
	}
	
	public void validatestep1() {

		order = driver.findElement(By.id("sale"));
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(order));

		act = new Actions(driver);
		act.moveToElement(order).build().perform();

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

		order = driver.findElement(By.xpath("//form[@id='form-order']//table/tbody/tr/td[2]"));
		Assert.assertTrue(order.isDisplayed());

	}

	public void validatestep3() {
		List<WebElement> orderids;
		String xpath;
		index = 0;

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
		ts.captureScreenShot("UNF_016_OrderBeforeDelete");

	}

	public void validateDeleteOrder() {
		String xpath = "//form[@id='form-order']//table/tbody/tr[" + String.valueOf(index + 1) + "]";

		// storing order id for deleting order
		String deletedorder = driver
				.findElement(
						By.xpath("//form[@id='form-order']//table/tbody/tr[" + String.valueOf(index + 1) + "]/td[2]"))
				.getText();
		System.out.println("index: " + index + " deleted order: " + deletedorder);

		// clicking delete button
		order = driver.findElement(By.xpath(xpath + "/td[8]/button"));
		order.click();
		// ts.captureScreenShot("UNF_016_step4");
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// handling alert and validating pop up message
		try {
			alert = driver.switchTo().alert();
			Assert.assertEquals(alert.getText(), "Are you sure?");
			alert.accept();
		} catch (Exception e) {
			System.out.println("no such alert found");
		}

		// step 5 validations continues
		driver.switchTo().defaultContent();

		// waiting for record to be removed from display
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementWithText(
				By.xpath("//form[@id='form-order']//table/tbody/tr[" + String.valueOf(index + 1) + "]/td[2]"),
				deletedorder));

		// new record after deletion
		order = driver.findElement(
				By.xpath("//form[@id='form-order']//table/tbody/tr[" + String.valueOf(index + 1) + "]/td[2]"));

		// validating record deleted from table row
		Assert.assertNotEquals(order.getText(), deletedorder);
		System.out.println("should not match on index: " + index + " " + deletedorder + " " + order.getText());
		ts.captureScreenShot("UNF_016_OrderAfterDelete");

	}

	public void orderList() {

		// navigating to order list

		order = driver.findElement(By.id("sale"));
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(order));

		act = new Actions(driver);
		act.moveToElement(order).build().perform();

		order = driver.findElement(By.xpath("//li[@id='sale']/ul/li/a[text()='Orders']"));
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(order));

		act = new Actions(driver);
		act.moveToElement(order).build().perform();
		order.click();

	}

	public void searchByID(String orderid) {

		boolean pass = false;

		// input id
		sendOrderID(orderid);

		Assert.assertTrue(orderID.getAttribute("value").trim().equals(orderid));

		// filter return order based on id
		driver.findElement(By.xpath("//button[@id='button-filter']")).click();

		order = driver.findElement(By.xpath("//form[@id='form-order']//table/thead/tr/td[2]"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));

		orderList = driver.findElements(By.xpath("//form[@id='form-order']//table/tbody/tr/td[2]"));

		if (orderList.size() == 0) {
			pass = false;
		} else {
			for (WebElement order : orderList) {
				if (!order.getText().equals(orderid)) {
					pass = false;
					break;
				} else
					pass = true;
			}
		}

		// taking screenshot
		ts.captureScreenShot("UNF_047_orderListByID");

		Assert.assertTrue(pass);
		orderID.clear();

	}

	public void searchByStatus(String status) {

		boolean pass = false;

		// input Status
		sendOrderStatus(status);

		Assert.assertTrue((new Select(orderStatus)).getAllSelectedOptions().get(0).getText().trim().equals("Pending"));

		// filter return order based on status
		driver.findElement(By.xpath("//button[@id='button-filter']")).click();

		order = driver.findElement(By.xpath("//form[@id='form-order']//table/thead/tr/td[2]"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));

		orderList = driver.findElements(By.xpath("//form[@id='form-order']//table/tbody/tr/td[4]"));

		if (orderList.size() == 0) {
			pass = false;
		} else {
			for (WebElement order : orderList) {
				if (!order.getText().trim().equals("Pending")) {
					pass = false;
					break;
				} else
					pass = true;
			}
		}

		// taking screenshot
		// ts.captureScreenShot("UNF_047_orderListByStatus");

		Assert.assertTrue(pass);
		// status.selectByValue("*");
		driver.findElement(By.xpath("//select[@id='input-order-status']/option[@value='*']")).click();
	}

	public void searchByCustomer(String customer) {

		boolean pass = false;

		// input customer
		sendOrderCustomer(customer);

		Assert.assertTrue(orderCustomer.getAttribute("value").trim().equals(customer));

		// filter return order based on customer
		driver.findElement(By.xpath("//button[@id='button-filter']")).click();

		order = driver.findElement(By.xpath("//form[@id='form-order']//table/thead/tr/td[2]"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));

		orderList = driver.findElements(By.xpath("//form[@id='form-order']//table/tbody/tr/td[3]"));

		if (orderList.size() == 0) {
			pass = false;
		} else {
			for (WebElement order : orderList) {
				if (!order.getText().trim().equals(customer)) {
					pass = false;
					break;
				} else
					pass = true;
			}
		}

		// taking screenshot
		// ts.captureScreenShot("UNF_047_orderListByCustomer");

		Assert.assertTrue(pass);
		orderCustomer.clear();

	}

	public void searchByTotalPrice(String total) {

		boolean pass = false;

		// input total price
		sendTotalPrice(total);

		Assert.assertTrue(orderTotal.getAttribute("value").trim().equals(total));

		// filter return order based on total price
		driver.findElement(By.xpath("//button[@id='button-filter']")).click();

		order = driver.findElement(By.xpath("//form[@id='form-order']//table/thead/tr/td[2]"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));

		orderList = driver.findElements(By.xpath("//form[@id='form-order']//table/tbody/tr/td[5]"));

		if (orderList.size() == 0) {
			pass = false;
		} else {
			for (WebElement order : orderList) {
				if (!order.getText().trim().contains(total)) {
					pass = false;
					break;
				} else
					pass = true;
			}
		}

		// taking screenshot
		// ts.captureScreenShot("UNF_047_orderListByTotalPrice");

		Assert.assertTrue(pass);
		orderTotal.clear();

	}

	public void searchByAddedDate(String date) {

		boolean pass = false;

		// input added date
		sendAddedDate(date);

		Assert.assertTrue(orderAddedDate.getAttribute("value").trim().equals(date));

		// filter return order based on added date
		driver.findElement(By.xpath("//button[@id='button-filter']")).click();

		order = driver.findElement(By.xpath("//form[@id='form-order']//table/thead/tr/td[2]"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));

		orderList = driver.findElements(By.xpath("//form[@id='form-order']//table/tbody/tr/td[6]"));
		
		//convert date to format matching in result
		date = date.subSequence(8, 10)+"/"+date.subSequence(5, 7)+"/"+date.subSequence(0, 4);

		if (orderList.size() == 0) {
			pass = false;
		} else {
			for (WebElement order : orderList) {
				if (!order.getText().trim().equals(date)) {
					pass = false;
					break;
				} else
					pass = true;
			}
		}

		// taking screenshot
		// ts.captureScreenShot("UNF_047_orderListByAddedDate");

		Assert.assertTrue(pass);
		orderAddedDate.clear();

	}

	public void searchByModifiedDate(String date) {

		boolean pass = false;

		// input modified date
		sendModifiedDate(date);

		Assert.assertTrue(orderModifiedDate.getAttribute("value").trim().equals(date));

		// filter return order based on modified date
		driver.findElement(By.xpath("//button[@id='button-filter']")).click();

		order = driver.findElement(By.xpath("//form[@id='form-order']//table/thead/tr/td[2]"));
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(order));

		orderList = driver.findElements(By.xpath("//form[@id='form-order']//table/tbody/tr/td[7]"));		
		
		//convert date to format matching in result
		date = date.subSequence(8, 10)+"/"+date.subSequence(5, 7)+"/"+date.subSequence(0, 4);

		if (orderList.size() == 0) {
			pass = false;
		} else {
			for (WebElement order : orderList) {
				if (!order.getText().trim().equals(date)) {
					pass = false;
					break;
				} else
					pass = true;
			}
		}

		// taking screenshot
		// ts.captureScreenShot("UNF_047_orderListByModifiedDate");

		Assert.assertTrue(pass);
		orderModifiedDate.clear();

	}

}
