package AutomationFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class SearchPage {
	
	WebDriver driver;
	By SelectAcfPrefix = By.id("selectAcfPrefix");
	By SelectAcfSuffix = By.id("selectAcfSuffix");
	By AcfSelect = By.id("acfSelect");
	By SearchGivenName = By.id("given");
	By SearchFamily = By.id("family");
	By SearchGender= By.id("gender");
	By SearchDOBMonth = By.id("dobMonth");
	By SearchDOBDay = By.id("dobDay");
	By SearchDOBYear = By.id("dobYear");
	By SearchSSN  = By.id("ssn");
	By SearchStreet = By.id("line");
	By SearchCity = By.id("city");
	By SearchState = By.id("state");
	By SearchZip = By.id("zipcode");
	By SearchSubmit = By.id("queryFormSubmit");
	
	public SearchPage(WebDriver driver) 	{		this.driver = driver; 	}
	
	public void setSelectAcfPrefix(String acfPrefix) 	{ 
		Select selAcfPfx = new Select(driver.findElement(SelectAcfPrefix));
		selAcfPfx.selectByVisibleText(acfPrefix);
	}
	public void setSelectAcfSuffix(String acfSuffix)	{
		Select selAcfSfx = new Select(driver.findElement(SelectAcfSuffix));
		selAcfSfx.selectByVisibleText(acfSuffix);
	}
	public void clickAcfSelect(String acfPrefix,String acfSuffix) 	{
		setSelectAcfPrefix(acfPrefix);
		setSelectAcfSuffix(acfSuffix);
		driver.findElement(AcfSelect).click();
	}
	public void setSearchGivenName(String strGivenName) {
		driver.findElement(SearchGivenName).sendKeys(strGivenName);;
	}
	public void setSearcFamily(String strFamilyName) {
		driver.findElement(SearchFamily).sendKeys(strFamilyName);;
	}
	public void setSearchGender(String strGender) {
		Select selSrchGen = new Select(driver.findElement(SearchGender));
		selSrchGen.selectByVisibleText(strGender);
	}
	public void setSearchDOBMonth(String strDOBMonth) {
		Select selSrchMM = new Select(driver.findElement(SearchDOBMonth));
		selSrchMM.selectByVisibleText(strDOBMonth);
		}
	public void setSearchDOBDay(String strDOBDay) {
		Select selSrchDD = new Select(driver.findElement(SearchDOBDay));
		selSrchDD.selectByVisibleText(strDOBDay);
		}
	public void setSearchDOBYear(String strDOBYear) {
		driver.findElement(SearchDOBYear).sendKeys(strDOBYear);
		}
	public void setSearchSSN(String strSSN) {
		driver.findElement(SearchSSN).sendKeys(strSSN);
		}
	public void setSearchStreet(String strStreet) {
		driver.findElement(SearchStreet).sendKeys(strStreet);
		}
	public void setSearchCity(String strCity) {
		driver.findElement(SearchCity).sendKeys(strCity);
		}
	public void setSearchState(String strState) {
		Select selSrchDD = new Select(driver.findElement(SearchState));
		selSrchDD.selectByVisibleText(strState);
		}
	public void setSearchZip(String strZip) {
		driver.findElement(SearchZip).sendKeys(strZip);
		}
	
	public void clickSearchSubmit(String strGivenName,String strFamilyName,
			String strGender,String strDOBMonth,String strDOBDay,String strDOBYear,
			String strSSN,String strStreet,String strCity,String strState,String strZip
			)
	{
		this.setSearchGivenName(strGivenName);
		this.setSearcFamily(strFamilyName);
		this.setSearchGender(strGender);
		this.setSearchDOBMonth(strDOBMonth);
		this.setSearchDOBDay(strDOBDay);
		this.setSearchDOBYear(strDOBYear);
		this.setSearchSSN(strSSN);
		this.setSearchStreet(strStreet);
		this.setSearchCity(strCity);
		this.setSearchState(strState);
		this.setSearchZip(strZip);
		driver.findElement(SearchSubmit).click();
	}

	

}
