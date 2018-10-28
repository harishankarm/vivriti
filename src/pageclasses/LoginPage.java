package pageclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver _driver) {
		super(_driver);
	}
	
	@FindBy(name = "email")
	private WebElement email_txt;
	
	@FindBy(name = "password")
	private WebElement password_txt;
	
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement login_btn;
	

	public void set_Email(String email) {
		email_txt.sendKeys(email);
	}
	
	public void set_Password(String password) {
		password_txt.sendKeys(password);
	}
	
	public void click_Login() {
		login_btn.click();
	}

}
