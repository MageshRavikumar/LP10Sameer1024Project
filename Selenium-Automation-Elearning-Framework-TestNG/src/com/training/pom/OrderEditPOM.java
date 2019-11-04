package com.training.pom;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class OrderEditPOM {
	private WebDriver driver;

	public OrderEditPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="button-customer")
	private WebElement nextBtnCustomer;

	@FindBy(id="button-cart")
	private WebElement nextBtnCart;
	
	@FindBy(id="button-payment-address")
	private WebElement nextBtnPayment;
	
	@FindBy(id="button-shipping-address")
	private WebElement nextBtnAddress;
	
	@FindBy(id="button-save")
	private WebElement saveBtn;
	
	@FindBy(id="button-product-add")
	private WebElement addBtnProduct;
	
	@FindBy(xpath="//tbody[@id='cart']/tr/td[6]/button")
	private WebElement removeBtnProduct;
	
	@FindBy(id="input-product")
	private WebElement addProduct;
	
	@FindBy(id="input-quantity")
	private WebElement addQuantity;
	
	@FindBy(xpath="//select[contains(@id,'input-option')]")
	private WebElement addSize;
	
	@FindBy(xpath="//input[@id='input-product']/following-sibling::ul/li/a")
	private List<WebElement> selectProduct;
	
	@FindBy(id="input-shipping-method")
	private WebElement shippingMethod;

	public void clickNextBtnCustomer() {
		this.nextBtnCustomer.click();		
	}

	public void clickNextBtnCart() {
		this.nextBtnCart.click();
	}
	
	public void clickNextBtnPayment() {
		this.nextBtnPayment.click();
	}
	
	public void clickNextBtnAddress() {
		this.nextBtnAddress.click();
	}
	
	public void clickSaveBtn() {
		this.saveBtn.click();
	}
	
	public void clickAddBtnProduct() {
		this.addBtnProduct.click();
	}
	
	public void clickRemoveBtnProduct() {
		this.removeBtnProduct.click();
	}
	
	public void addProduct(String product) {
		this.addProduct.clear();
		this.addProduct.sendKeys(product);
	}
	
	public void addQuantity(String qty) {
		this.addQuantity.clear();
		this.addQuantity.sendKeys(qty);
	}
	
	public void selectSize(String value) {
		(new Select(this.addSize)).selectByValue(value);
	}
	
	public void selectProduct(String product) {
		for (WebElement sel:selectProduct) {
			if(sel.getText().contains(product)) {
				sel.click();
				break;
			}
		}
	}
	
	public void selectShippingMethod(String value) {
		(new Select(this.shippingMethod)).selectByValue(value);
	}
}
