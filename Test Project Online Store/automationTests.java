import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class pogonShop {

    private static WebDriver driver;
    private static WebDriverWait wait;
    String productNameXPath = "Personalizowana koszulka meczowa 2020/21 HOME";
    String loopXPath = "//*[@id=\"searchbox\"]/button";
    String addToCartXPath = "//*[@id=\"add-to-cart-or-refresh\"]/div[1]/div[3]/div/button";
    String searchingFieldXPath = "//*[@id=\"search_query_top\"]";
    String searchedProductXPath = "//*[@id=\"box-product-grid\"]/div/div[1]/div/div/div[1]/a";
    String firstBasketProductXPath =  "//*[@id=\"main\"]/div/div[1]/div/div[2]/ul/li/div/div[2]/div[1]/a";
    String dostosowywanieProduktuXPath = "//*[@id=\"main\"]/div[1]/div[3]/div[2]/section/div/h3";
    String wordOnTShirtXPath = "//*[@id=\"main\"]/div[1]/div[3]/div[2]/section/div/form/ul/li[1]/textarea";
    String wordOnTShirt = "Andrzej";
    String numberOnTShirtXPath ="//*[@id=\"main\"]/div[1]/div[3]/div[2]/section/div/form/ul/li[2]/textarea";
    String numberOnTShirt = "102";
    String zapiszDostosowywanieXPath = "//*[@id=\"main\"]/div[1]/div[3]/div[2]/section/div/form/div/button";
    String mojeKontoXPath = "//*[@id=\"mojekonto\"]/a";
    String emailXPath = "//*[@id=\"login-form\"]/section/div[1]/div[1]/input";
    String email = "wrongEmaiLogin@login.com";
    String passwordXPath = "//*[@id=\"login-form\"]/section/div[2]/div[1]/div/input";
    String password = "badPassword123";
    String zalogujXPath = "//*[@id=\"login-form\"]/div[2]/footer/button";
    String loggingErrorXPath = "//*[@id=\"content\"]/section/div/ul/li";


    @BeforeAll
    public static void setUp(){
        System.setProperty("webdriver.chrome.driver", "C://chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver,10);
    }

    @Test
    public void enterPage() {

        driver.get("https://sklep.pogonszczecin.pl/");
        Assertions.assertEquals("Oficjalny sklep Pogoni Szczecin",driver.getTitle());
    }

    @Test
    public void searchingAndAddingToCart() {
        driver.get("https://sklep.pogonszczecin.pl/");
        driver.findElement(By.xpath(searchingFieldXPath)).sendKeys(productNameXPath);
        driver.findElement(By.xpath(loopXPath)).click();
        driver.findElement(By.xpath(searchedProductXPath)).click();
        List<WebElement> link = driver.findElements(By.xpath(dostosowywanieProduktuXPath));
        if (link.size()>0) {
            driver.findElement(By.xpath(wordOnTShirtXPath)).sendKeys(wordOnTShirt);
            driver.findElement(By.xpath(numberOnTShirtXPath)).sendKeys(numberOnTShirt);
            driver.findElement(By.xpath(zapiszDostosowywanieXPath)).click();
        }
            driver.findElement(By.xpath(addToCartXPath)).click();
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Przejdź do realizacji zamówienia")));
            driver.findElement(By.linkText("Przejdź do realizacji zamówienia")).click();
            Assertions.assertEquals(productNameXPath, driver.findElement(By.xpath(firstBasketProductXPath)).getText());

    }

    @Test
    public void loggingIn(){
        driver.get("https://sklep.pogonszczecin.pl/");
        driver.findElement(By.xpath(mojeKontoXPath)).click();
        driver.findElement(By.xpath(emailXPath)).sendKeys(email);
        driver.findElement(By.xpath(passwordXPath)).sendKeys(password);
        driver.findElement(By.xpath(zalogujXPath)).click();
        Assertions.assertEquals("Błąd uwierzytelniania.", driver.findElement(By.xpath(loggingErrorXPath)).getText());

    }

    @AfterAll
    public static void tearDown() {
        driver.close();
    }
}