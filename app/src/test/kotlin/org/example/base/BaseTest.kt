package org.example.base

import com.codeborne.selenide.Configuration
import org.openqa.selenium.WebDriver
import org.openqa.selenium.JavascriptExecutor
import com.codeborne.selenide.WebDriverRunner
import com.codeborne.selenide.drivercommands.Navigator
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import java.io.FileInputStream
import java.util.*


open class BaseTest(){
    companion object{

        @JvmStatic
        @BeforeAll
        fun setUp(){
            Configuration.timeout = 12000
            Configuration.baseUrl = "https://demo.prestashop.com/#/en/front"
            if(System.getenv("BROWSER")!=null){
                Configuration.browser=System.getenv("BROWSER")
            }else{
                Configuration.browser="chrome"
            }
            Configuration.browserSize = "1920x1080"
            WebDriverRunner.setWebDriver(webDriver())
            WebDriverRunner.getWebDriver().manage().window().maximize()
        }

        @JvmStatic
        @AfterAll
        fun tearDown(){
            WebDriverRunner.closeWebDriver()
        }

        private fun webDriver(): WebDriver {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\diana\\Desktop\\Test_task_Bilderling\\drivers\\chromedriver.exe")
            return org.openqa.selenium.chrome.ChromeDriver() }
        }
}