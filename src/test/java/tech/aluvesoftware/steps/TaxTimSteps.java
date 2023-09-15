package tech.aluvesoftware.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import tech.aluvesoftware.TestContext;
import tech.aluvesoftware.pages.TaxTimPage;

import static org.junit.Assert.assertTrue;

public class TaxTimSteps {
    WebDriver driver;
    TaxTimPage taxTimPage;
    TestContext testContext;

    public TaxTimSteps() {
        testContext = new TestContext(30);
        this.driver = testContext.getDriver();
        driver.get("https://www.taxtim.com/za/calculators/income-tax");
        this.taxTimPage = new TaxTimPage(driver);
        taxTimPage.closePopUp();
    }

    @Given("the tax year is {string}")
    public void theTaxYearIs(String taxYear){
        taxTimPage.selectTaxYear(taxYear);
    }

    @Given("the salary before deductions is {string}")
    public void theSalaryBeforeDeductionsIs(String gross) {
        taxTimPage.enterTotalSalaryBeforeDeductions(gross);
    }

    @Given("the salary is received {string}")
    public void theSalaryIsReceived(String salaryFrequency) {
        taxTimPage.enterSalaryFrequency(salaryFrequency);
    }

    @Given("the user is {string} years old")
    public void theUserIsYearsOld(String age) {
        taxTimPage.enterUserAge(age);
    }

    @When("the user calculates their tax")
    public void theUserCalculatesTheirTax() {
        taxTimPage.calculate();
    }

    @Then("the take home should be R{string}")
    public void theTakeHomeShouldBeR(String expectedTakeHome) {
        boolean isTakeHomeCorrect = taxTimPage.isTakeHomeThisValue(expectedTakeHome);
        assertTrue(isTakeHomeCorrect);
    }

    @Then("the tax should be R{string}")
    public void theTaxShouldBeR(String tax) {
        boolean isTaxCorrect = taxTimPage.isTaxThisAmount(tax);
        assertTrue(isTaxCorrect);
    }

    @After
    public void quitDriver(){
        driver.quit();
    }
}
