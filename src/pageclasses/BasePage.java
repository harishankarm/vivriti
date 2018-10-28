package pageclasses;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BasePage {
	
	protected WebDriver driver;
	protected WebDriverWait wait ;
	public JavascriptExecutor javaScriptExecutor=null;

	public BasePage(WebDriver _driver) {
		driver=_driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 20);
		javaScriptExecutor=(JavascriptExecutor) driver;
	}

}
