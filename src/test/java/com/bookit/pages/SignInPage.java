package com.bookit.pages;

import com.bookit.utilities.BookitUtils;
import com.bookit.utilities.BrowserUtils;
import com.bookit.utilities.ConfigurationReader;
import com.bookit.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;


public class SignInPage {
	
	public SignInPage() {
		PageFactory.initElements(Driver.get(), this);
	}	
	
	@FindBy(name="email")
	public WebElement emailField;

	@FindBy(name = "password")
	public WebElement passwordField;
	
	@FindBy(xpath = "//button[.='sign in']")
	public WebElement signInButton;


	public void login(String role) {

		// Get Credentials
		Map<String, String> roleCredentials = BookitUtils.returnCredentials(role);
		String email = roleCredentials.get("email");
		String password = roleCredentials.get("password");

		// login
		login(email,password);

	}

	public void login(String email,String password) {

		emailField.sendKeys(email);
		passwordField.sendKeys(password);
		BrowserUtils.waitFor(1);
		signInButton.click();

	}
	
}
