package tech.aluvesoftware.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import tech.aluvesoftware.WebActions;

import java.time.Duration;

public class TaxTimPage {
    private By bySelectTaxYear = By.id("yearsel");
    private By byGross = By.id("gross");
    private By byPeriod = By.id("period");
    private By byAge = By.id("age");
    private By byTakeHome = By.xpath("//*[text()='Take home pay:']/following-sibling::span");
    private By byTaxText = By.xpath("//div[@id='calcs']");
    private By byCalculateButton = By.xpath("//a[contains(@class, 'button') and text()='Calculate!']");
    private By byCloseWindow = By.xpath("//div[@class='close']/a");
    private WebDriver driver;
    WebActions webActions;

    public TaxTimPage(WebDriver driver) {
        this.driver = driver;
        this.webActions= new WebActions(driver, 30);
    }

    public void closePopUp(){
        webActions.clickElement(byCloseWindow);
    }

    public void selectTaxYear(String taxYear){
        WebElement taxYearElement = driver.findElement(bySelectTaxYear);
        new Select(taxYearElement).selectByValue(taxYear);
    }

    public void enterTotalSalaryBeforeDeductions(String totalSalary){
        webActions.updateField(byGross, totalSalary);
    }

    public void enterSalaryFrequency(String salaryFrequency){
        WebElement salaryFrequencyElement = driver.findElement(byPeriod);
        new Select(salaryFrequencyElement).selectByValue(salaryFrequency);
    }

    public void enterUserAge(String age){

        try{
            webActions.updateField(byAge, age);
        }
        catch (InvalidElementStateException e){
            webActions.updateField(byAge, age);
        }

    }

    public void calculate(){
//        webActions.clickElement(byCalculateButton);
        driver.findElement(byCalculateButton).click();
    }

    public boolean isTakeHomeThisValue(String expectedTakeHome){
        return webActions.isTextPresentInElement(byTakeHome, expectedTakeHome);
    }

    public boolean isTaxThisAmount(String expectedTaxAmount){
        return webActions.getElementText(byTaxText).contains(expectedTaxAmount);
    }
}
