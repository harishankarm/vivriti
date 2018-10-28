package pageclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InitiateTransactionPage extends BasePage{
	

	public InitiateTransactionPage(WebDriver _driver) {
		super(_driver);
	}
	
	@FindBy(xpath = "//h4[text()='PTC']")
	private WebElement ptc_Link;
	
	@FindBy(xpath = "//h4[text()='NCD']")
	private WebElement ncd_Link;
	
	@FindBy(xpath = "//h4[text()='CP']")
	private WebElement cp_link;
	
	@FindBy(xpath = "//h4[text()='Term Loan']")
	private WebElement termLoan_Link;
	
	@FindBy(xpath = "//h4[text()='Preference Shares']")
	private WebElement preferenceShares_Link;

	public void click_InitiateTransactionPTCLink() {
		ptc_Link.clear();
	}
	
	public void click_InitiateTransactionNCDLink() {
		ncd_Link.clear();
	}
	
	public void click_InitiateTransactionCPLink() {
		cp_link.clear();
	}
	
	public void click_InitiateTransactionTermLoanLink() {
		termLoan_Link.clear();
	}
	
	public void click_InitiateTransactionPreferenceSharesLink() {
		preferenceShares_Link.clear();
	}

}
