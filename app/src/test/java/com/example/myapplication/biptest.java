//package ismi otomatik verilir
package com.example.myapplication;

//kullanılan kütüphaneler
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.appium.java_client.android.AndroidDriver;

/**
 * Yeni proje açarken proje ismi vermek dışında yapılması gereken bir şey yok
 * Proje ilk başlatıldığında libs klasörüne Appium Jars klasöründeki tüm "Executable Jar File' türündeki dosyalar kopyalanır.
 * Sonra libsteki tüm dosyalar seçilip add as libraray seçilir. (bu işlem biraz yavaş olabilir)
 */



//class ismi dosya ismiyle aynı olmak zorunda (otomatik olarak aynı verir zaten)
public class biptest {

    WebDriver phone ; //telefona(sisteme) karşılık gelen obje

    //test başlamadan önce programın başladığı yer @Before yazılmak zorunda
    @Before
    public void setUp() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        //telefonun özellikleri
        capabilities.setCapability("deviceName", "General Mobile 4G Dual");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
        capabilities.setCapability(CapabilityType.VERSION, "7.1.1");
        capabilities.setCapability("platformName", "Android");

        //Uygulamayla ilgili kısım. Bunlar cmd den bulunabilir.
        // Bağlı telefondan test edilecek uygulamanın ana sayfası açılır. Daha sonra;
        /* cmd
         * adb devices
         * adb shell
         * dumpsys window windows | grep -E ‘mCurrentFocus|mFocusedApp’
         * ilk satırda '/' öncesi kısım appPackage, kalan yerden '}' kadar olan kısım ise appActivity
         */
        capabilities.setCapability("appPackage", "com.turkcell.bip");
        capabilities.setCapability("appActivity","com.turkcell.bip.ui.main.BiPActivity");

        //no reset true olmalı. (Uygulamaın kayıtlı bilgi ve izinlerini sıfırlamaması için)
        capabilities.setCapability("noReset",true);

        //verilen özelliklerin telefon objesine bağlandığı yer, sabit (şimdilik?)
        phone = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        phone.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    //Her case için ayrı method oluşturulabilir, testlerden önce @Test kullanılmak zorunda
    @Test
    public void test_first(){
         /*
          Veirlen 'id' ler Appiumda yeni session başlatarak(start new session) bulunur. Her butona özel id vardır.
          */
        List<WebElement> chatListe ; //sobbet listesi
        chatListe = phone.findElements(By.id("com.turkcell.bip:id/mainListTexts")); //sohbet listesi bulundu
        chatListe.get(0).click(); // ilk sohbete tıkla
        phone.findElement(By.id("com.turkcell.bip:id/chatEditText")).sendKeys("Merhaba, ben Arneca"); //mesajı yaz
        phone.findElement(By.id("com.turkcell.bip:id/textPanelSendIcon")).click(); //mesajı gönder
    }

    // sabit - @After yazılmak zorunda
    @After
    public void End() {
        phone.quit();
    }


}


