package com.training.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddProductPOM {
	private WebDriver driver;

	public AddProductPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//div[@id='content']//h1[text()='Products']/preceding-sibling::div/a[@data-original-title='Add New']")
	private WebElement addBtnProduct;
	
	@FindBy(id="input-name1")
	private WebElement productName;
	
	@FindBy(id="input-meta-title1")
	private WebElement productMetaTag;
	
	@FindBy(id="input-model")
	private WebElement productModel;
	
	@FindBy(id="input-category")
	private WebElement productCategory;
	
	@FindBy(xpath="//div[@id='product-category']/div")
	private List<WebElement> selectedProductCategory;
	
	@FindBy(xpath="//div[@id='content']//button[@data-original-title='Save']")
	private WebElement saveBtn;
	

	public void clickAddBtnProduct() {
		this.addBtnProduct.click();
	}
	
	public void setProductName(String name) {
		this.productName.clear();
		this.productName.sendKeys(name);
	}
	
	public void setProductMetaTag(String tag) {
		this.productMetaTag.clear();
		this.productMetaTag.sendKeys(tag);
	}
	
	public void setProductModel(String model) {
		this.productModel.clear();
		this.productModel.sendKeys(model);
	}
	
	public void setProductCategory(String category) {
		this.productCategory.clear();
		this.productCategory.sendKeys(category);
	}
	
	public String selectProduct(String category) {
		
		List<WebElement> selectCategory = driver.findElements(By.xpath("//input[@id='input-category']/following-sibling::ul/li/a"));
		for (WebElement sel:selectCategory) {
			if(sel.getText().contains(category)) {
				sel.click();
				category=sel.getText();
				return category;
			}
		}		
		return "";
	}
	
	public void clickSaveBtn() {
		this.saveBtn.click();
		
	}

}
