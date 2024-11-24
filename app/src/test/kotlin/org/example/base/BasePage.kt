import com.codeborne.selenide.*
import com.codeborne.selenide.Condition.exist
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selectors.*
import com.codeborne.selenide.Selenide.*
import org.checkerframework.checker.calledmethods.qual.EnsuresCalledMethodsVarArgs
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import com.codeborne.selenide.drivercommands.Navigator
import org.openqa.selenium.By

open class BasePage(){
    fun pageLoaded(webElement: SelenideElement, condition: WebElementCondition){
        webElement.shouldBe(condition)
    }

    fun runJS(script: String, vararg args: Any){
        executeJavaScript<Any>(script, *args)
    }

    fun convertPrices(price: String): Double{
        return price.trim().replace("€", "").toDouble()
    }

    fun goBack(expectedElement: String){
        back()
        Selenide.switchTo().frame(element(By.id("framelive")))
        element(expectedElement).should(exist)

    }
    fun doubleClick(){
        actions().doubleClick().perform()
    }
}