package org.example.base

import ExtentReportListener
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.WebDriverRunner
import org.junit.jupiter.api.*
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(ExtentReportListener::class)
open class BaseTest() {
    companion object {
        private lateinit var driver: WebDriver
        @JvmStatic
        @BeforeAll
        fun setUp() {
            Configuration.timeout = 12000
            Configuration.baseUrl = "https://demo.prestashop.com/#/en/front"
            Configuration.browser = System.getenv("BROWSER") ?: "chrome"
            Configuration.browserSize = "1920x1080"
            val options = ChromeOptions().apply {
                addArguments("--disable-save-password-bubble")
                setExperimentalOption("prefs", mapOf("autofill.profile_enabled" to false))
            }

            driver = createWebDriver(Configuration.browser, options)
            WebDriverRunner.setWebDriver(driver)
            WebDriverRunner.getWebDriver().manage().window().maximize()
        }

        @JvmStatic
        @AfterAll
        fun finalTearDown() {
            driver.quit()
        }

        private fun createWebDriver(browser: String, options: ChromeOptions): WebDriver {
            return when (browser) {
                "chrome" -> {
                    System.setProperty(
                        "webdriver.chrome.driver",
                        "drivers/chromedriver.exe"
                    )
                    ChromeDriver(options)
                }

                else -> throw IllegalArgumentException("Unsupported browser: $browser")
            }
        }
    }

    @BeforeEach
    fun setUpTest(testInfo: TestInfo){
        ExtentReportListener.createTest(testInfo.displayName)
    }

    @AfterEach
    fun tearDown(){
        ExtentReportListener.flush()
    }
}
