package demo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.checkerframework.checker.units.qual.min;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.List;

import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }

    @Test
    public void testCase01() throws InterruptedException{
        System.out.println("Start of testCase01");
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(6l));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ahS2Le']")));
        Thread.sleep(3000);
        //Enter Crio Learner in name text field
        WebElement nameField = driver.findElement(By.xpath("//div[@class='geS5n']/div[@class='AgroKb']//div[@class='Xb9hP']/input"));
        nameField.sendKeys("Crio Learner");

        //Write down "I want to be the best QA Engineer! and concat with epoc time in why are you practicing automation field
        long epoch = System.currentTimeMillis()/1000; 
        System.out.println("epoch : "+epoch);
        WebElement whyAutomationField = driver.findElement(By.xpath("//div[@class='Qr7Oae']//div[@class='AgroKb']//textarea"));
        whyAutomationField.sendKeys("I want to be the best QA Engineer! "+epoch);

        //select the experiance range under How much experience do you have in Automation Testing
       List<WebElement> experienceRange = driver.findElements(By.xpath("//div[@class='oyXaNc']//div[@class='ulDsOb']/span"));
       for(WebElement exp : experienceRange){
        if(exp.getText().equals("0 - 2")){
            exp.click();
            break;
        }
       }

       // select the automation subjects under Which of the following have you learned in Crio.Do for Automation Testing
       List<WebElement> learnedForAutomationSubjects = driver.findElements(By.xpath("//div[@class='Y6Myld']//span[@class='aDTYNe snByac n5vBHf OIC90c']"));
       for(WebElement subject : learnedForAutomationSubjects){
        js.executeScript("arguments[0].scrollIntoView()",subject);
        if(subject.getText().equals("Java") || subject.getText().equals("Selenium") || subject.getText().equals("TestNG")){
            subject.click();
        }
       }

        // select the prefix from the dropdown How should you be addressed?
       WebElement howToAddressDropDown = driver.findElement(By.xpath("//span[text()='Choose']"));
       howToAddressDropDown.click();
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='OA0qNb ncFHed QXL7Te']//span[@class='vRMGwf oJeWuf']")));
       List<WebElement> howToAddressDropDownElements = driver.findElements(By.xpath("//div[@class='OA0qNb ncFHed QXL7Te']//span[@class='vRMGwf oJeWuf']"));
       for(WebElement addressed : howToAddressDropDownElements){
        if(addressed.getText().equals("Mrs")){
          // wait.until(ExpectedConditions.elementToBeClickable(addressed));
          // js.executeScript("arguments[0].click();", addressed);
           addressed.click();
           Thread.sleep(1000);
            break;
        }
       }

        //select a date that is 7 days ago from current date
       LocalDate currentDate = LocalDate.now();
       LocalDate dateSevenDaysEarlier = currentDate.minusDays(7l);
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       String formattedDate = dateSevenDaysEarlier.format(formatter);
       System.out.println("formatted date "+formattedDate);
       WebElement dateIcon = driver.findElement(By.xpath("//div[@class='o7cIKf']/div[2]//input"));
      // js.executeScript("arguments[0].scrollIntoView()",dateIcon);
       dateIcon.sendKeys(formattedDate);

       //Provide the time 07:30 under What is the time right now
       WebElement hourInput = driver.findElement(By.xpath("//div[@class='Qr7Oae']/descendant::div[@class='ocBCTb']/div[1]/div[2]//input"));
       hourInput.sendKeys("7");
       WebElement minuteInput = driver.findElement(By.xpath("//div[@class='Qr7Oae']/descendant::div[@class='vEXS5c'][2]/descendant::div[@class='Xb9hP']/input"));
       minuteInput.sendKeys("30");

       //click on submit button
       WebElement submitButton = driver.findElement(By.xpath("//span[text()='Submit']"));
       submitButton.click();
       //verify the success message and print the same message on the console
       WebElement submissionMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='vHW8K']")));
       System.out.println("Submission Message : "+submissionMessage.getText()); 
       
       System.out.println("End of testCase01");

    }
}