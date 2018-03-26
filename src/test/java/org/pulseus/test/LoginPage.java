package org.pulseus.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

		WebDriver driver;
		By LoginUserName = By.id("username");
		By LoginPassword = By.id("password");
		By LoginButton = By.id("login-button");
		
		public LoginPage(WebDriver driver)
		{
			this.driver = driver;
		}
		
		public void setUserName(String strUserName)
		{
			driver.findElement(LoginUserName).sendKeys(strUserName);
			
		}
		
		public void setPassword(String strPassword)
		{
			driver.findElement(LoginPassword).sendKeys(strPassword);
		}
		
		public void clickLogin()
		{
			driver.findElement(LoginButton).click();
		}
		
		public void loginToPulse(String strUserName,String strPassword)
		{
			this.setUserName(strUserName);
			this.setPassword(strPassword);
			this.clickLogin();
		}
}

