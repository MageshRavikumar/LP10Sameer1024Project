package com.training.pom;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CreateCategoryPOM {
	private WebDriver driver;

	public CreateCategoryPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//div[@id='content']//h1[text()='Categories']/preceding-sibling::div/a[@data-original-title='Add New']")
	private WebElement addBtnCategory;
	
	@FindBy(xpath="//form[@id='form-category']/ul/li[1]/a")
	private WebElement tabGeneral;
	
	@FindBy(xpath="//form[@id='form-category']/ul/li[2]/a")
	private WebElement tabData;
	
	@FindBy(xpath="//form[@id='form-category']/ul/li[3]/a")
	private WebElement tabDesign;
	
	@FindBy(id="input-name1")
	private WebElement categoryName;
	
	@FindBy(xpath="//textarea[@id='input-description1']/following-sibling::div/div[3]/div[@class='note-editable panel-body']")
	private WebElement categoryDescription;
	
	@FindBy(id="input-meta-title1")
	private WebElement categoryMetaTag;
	
	@FindBy(id="input-meta-description1")
	private WebElement categoryMetaTagDesc;
	
	@FindBy(xpath="//input[@id='input-product']/following-sibling::ul/li/a")
	private List<WebElement> selectProduct;
	
	@FindBy(xpath="//div[@id='content']//button[@data-original-title='Save']")
	private WebElement saveBtn;
	

	public void clickAddBtnCategory() {
		this.addBtnCategory.click();
	}
	
	public void setCategoryName(String name) {
		this.categoryName.clear();
		this.categoryName.sendKeys(name);
	}
	
	public void setCategoryDescription(String desc) {
		this.categoryDescription.clear();
		this.categoryDescription.sendKeys(desc);
	}
	
	public void setCategoryMetaTag(String tag) {
		this.categoryMetaTag.clear();
		this.categoryMetaTag.sendKeys(tag);
	}
	
	public void setMetaTagDesc(String tagDesc) {
		this.categoryMetaTagDesc.clear();
		this.categoryMetaTagDesc.sendKeys(tagDesc);
	}
	
	
	public void selectSize(String value) {
		(new Select(this.categoryMetaTagDesc)).selectByValue(value);
	}
	
	public void selectProduct(String product) {
		for (WebElement sel:selectProduct) {
			if(sel.getText().contains(product))
				sel.click();
		}
	}
	
	public void clickTabData() {
		this.tabData.click();
	}
	
	public void clickTabDesign() {
		this.tabDesign.click();
	}

	public void clickSaveBtn() {
		this.saveBtn.click();
		
	}

}
