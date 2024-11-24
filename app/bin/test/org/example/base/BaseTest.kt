import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach


open class BaseTest(){
    //protected lateinit var driver: WebDriver

    @BeforeEach
    fun setUp(){
        Configuration.timeout = 12000
        Configuration.baseUrl = "https://demo.prestashop.com/#/en/front"
        Configuration.browser = "chrome"
        Configuration.screenshots = true
        Configuration.reportsFolder = "build\reports\testreports"
        //driver = WebDriverRunner.getWebDriver()
    }

    @AfterEach
    fun quitBrowser(){
        closeWebDriver()
        //driver.quit()
        //closeWebDriver()
    }
}