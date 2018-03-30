package org.pulseus.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

		WebDriver driver;
//		By SigninLink = By.className("sign-in text-default");
//		By LoginUserName = By.id("username");
		//By LoginPassword = By.id("password");
		//By LoginButton = By.id("login-button");
		By LoginUserName = By.id("_com_liferay_login_web_portlet_LoginPortlet_login");
		By LoginPassword = By.id("_com_liferay_login_web_portlet_LoginPortlet_password");
		//By LoginButton   = //By.id("_com_liferay_login_web_portlet_LoginPortlet_mmrq.btn.btn-lg.btn-primary.btn-default");
							//By.className("btn btn-lg btn-primary btn-default");
				//By.xpath("//*[@id=\"_com_liferay_login_web_portlet_LoginPortlet_mmrq\"]");
				//By.id("_com_liferay_login_web_portlet_LoginPortlet_dpyb");
				//By.xpath("//*[@id=\"_com_liferay_login_web_portlet_LoginPortlet_dpyb\"]");
		
		public LoginPage(WebDriver driver)
		{
			this.driver = driver;
		}
		
		/*
		public void clickSignin()
		{
			driver.findElement(SigninLink).click();
			WebDriverWait wait = new WebDriverWait(driver,2);

			WebElement nextmessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("error-box")));

		}
		*/
		public void setUserName(String strUserName)
		{
			driver.findElement(LoginUserName).clear();
			driver.findElement(LoginUserName).sendKeys(strUserName);
			
		}
		
		public void setPassword(String strPassword)
		{
			driver.findElement(LoginPassword).clear();
			driver.findElement(LoginPassword).sendKeys(strPassword);
		}
		
		public void clickLogin()
		{
			driver.findElement(LoginPassword).submit();
		}
		
		public void loginToPulse(String strUserName,String strPassword)
		{	
			this.setUserName(strUserName);
			this.setPassword(strPassword);
			this.clickLogin();
		}
}

