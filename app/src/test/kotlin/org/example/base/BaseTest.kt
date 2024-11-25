package org.example.base

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.WebDriverRunner
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

open class BaseTest() {
    companion object {

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

            val driver = createWebDriver(Configuration.browser, options)
            WebDriverRunner.setWebDriver(driver)
            WebDriverRunner.getWebDriver().manage().window().maximize()
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
        }

        private fun createWebDriver(browser: String, options: ChromeOptions): WebDriver {
            return when (browser) {
                "chrome" -> {
                    System.setProperty(
                        "webdriver.chrome.driver",
                        "C:\\Users\\diana\\Desktop\\Test_task\\drivers\\chromedriver.exe"
                    )
                    ChromeDriver(options)
                }

                else -> throw IllegalArgumentException("Unsupported browser: $browser")
            }
        }
    }
}
