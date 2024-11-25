import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.SelenideElement
import com.codeborne.selenide.WebElementCondition
import java.math.BigDecimal
import java.math.RoundingMode

open class BasePage() {
    fun pageLoaded(webElement: SelenideElement, condition: WebElementCondition) {
        webElement.shouldBe(condition)
    }

    fun runJS(script: String, vararg args: Any) {
        executeJavaScript<Any>(script, *args)
    }

    fun convertPrices(price: String): Double {
        var priced = price.trim().replace("€", "").toDouble()
        return BigDecimal(priced).setScale(2, RoundingMode.HALF_UP).toDouble()
    }

    fun verifyPageHeader(header: String) {
        element(".page-header h1").shouldHave(text(header))
    }

    fun goBackXTimes(expectedElement: String, x: Int) {
        for (i in 1..x) {
            runJS("window.history.back();")
        }
        element(expectedElement).shouldBe(visible)

    }

    fun doubleClick() {
        actions().doubleClick().perform()
    }
}