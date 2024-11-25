package org.example.base

import com.aventstack.extentreports.ExtentReports
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.WebDriverRunner
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import com.aventstack.extentreports.reporter.ExtentSparkReporter

open class BaseTest() {
    companion object {
        lateinit var extent: ExtentReports
        lateinit var spark: ExtentSparkReporter
        @JvmStatic
        @BeforeAll
        fun setUp() {
            spark = ExtentSparkReporter("report/spark.html")
            extent = ExtentReports()
            extent.attachReporter(spark)
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
            extent.flush()
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
}
