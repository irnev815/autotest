package com.example.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTest {
	  private WebDriver driver;
	  private String baseUrl;
	  private String mailUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  private String emailAddress = "sample.email@server.com";
	  private String originalPassword = "replaceWithOriginalPass";
	  private String newPassword = "replaceWithNewPass";
	  private String emailPassword = "replaceWithEmailPass";

	  
	  @Before
	  public void setUp() throws Exception {
	    System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\geckodriver.exe");
	    driver = new FirefoxDriver();
	    baseUrl = "https://news360.com/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testLoginAndResetScreen() throws Exception {

		  // perform sign-up using 360
		  // initiate reset password using 360
		  performSignUpAndResetRequest(emailAddress, originalPassword);

		  // authorise password reset and recovery using email
		  String resetlink = getResetPasswordLink(emailAddress, emailPassword);
		  System.out.print(resetlink);
		  
		  // login with new password using 360
		  resetPasswordAndValidateLogin(resetlink, emailAddress, newPassword);
		  
	  }

	  
	  private void performSignUpAndResetRequest(String email, String password){
		  	driver.get(baseUrl);
		    assertEquals(driver.getTitle(), "News360: Your personalized news reader app");
		    driver.findElement(By.linkText("Sign in with email")).click();
		    driver.findElement(By.xpath("(//a[contains(text(),'Sign up')])[2]")).click();
		    driver.findElement(By.id("signupemail")).clear();
		    driver.findElement(By.id("signupemail")).sendKeys(email);
		    driver.findElement(By.id("password")).clear();
		    driver.findElement(By.id("password")).sendKeys(password);
		    driver.findElement(By.xpath("//button[@type='submit']")).click();
		    assertEquals(driver.getTitle(), "News360");
		    driver.findElement(By.xpath("//div[2]")).click();
		    driver.findElement(By.cssSelector("span.username.ng-binding")).click();
		    driver.findElement(By.xpath("//div[2]/div/div")).click();
		    driver.findElement(By.linkText("Sign in with email")).click();
		    driver.findElement(By.linkText("Forgot your password?")).click();
		    driver.findElement(By.id("resetemail")).clear();
		    driver.findElement(By.id("resetemail")).sendKeys("inevsky78@gmail.com");
		    driver.findElement(By.xpath("//form[2]/button")).click();

	  }
	  
	  private String getResetPasswordLink(String username, String password){
		    
		    driver.get("https://www.gmail.com");
		    assertEquals("Gmail", driver.getTitle());
		    driver.findElement(By.id("identifierId")).click();
		    driver.findElement(By.id("identifierId")).clear();
		    driver.findElement(By.id("identifierId")).sendKeys(username);
		    driver.findElement(By.cssSelector("span.RveJvd.snByac")).click();
		    driver.findElement(By.name("password")).clear();
		    driver.findElement(By.name("password")).sendKeys(password);
		    driver.findElement(By.cssSelector("span.RveJvd.snByac")).click();
		    driver.findElement(By.id(":36")).click();
		    driver.findElement(By.xpath("//a[contains(@href, 'ct.sendgrid.net/wf/click')]")).click();

		    
		    WebElement link = driver.findElement(By.xpath("//a[contains(@href, 'ct.sendgrid.net/wf/click')]"));
		    String linkLocatin = link.getAttribute("href");
		    return linkLocatin;
	  }

	  private void resetPasswordAndValidateLogin(String link, String email, String newPassword){
		    driver.get(link);
		    assertEquals("News360: Your personalized news reader app", driver.getTitle());
		    driver.findElement(By.id("resetpassword")).click();
		    driver.findElement(By.id("resetpassword")).clear();
		    driver.findElement(By.id("resetpassword")).sendKeys(newPassword);
		    driver.findElement(By.cssSelector("input.confirmpassword.textbox")).clear();
		    driver.findElement(By.cssSelector("input.confirmpassword.textbox")).sendKeys(newPassword);
		    driver.findElement(By.xpath("//button")).click();
		    driver.findElement(By.id("signinemail")).clear();
		    driver.findElement(By.id("signinemail")).sendKeys(email);
		    driver.findElement(By.name("password")).clear();
		    driver.findElement(By.name("password")).sendKeys(newPassword);
		    driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
		    assertEquals("News360", driver.getTitle());
	  }
	  
	  @After
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }

}
