package pageclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{
	
		public HomePage(WebDriver _driver) {
			super(_driver);
		}
		
		@FindBy(xpath = "//a[text()='Initiate Transaction']")
		private WebElement email_txt;
		

		public void click_InitiateTransaction() {
			email_txt.click();
		}
		
}
