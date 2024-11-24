package pageobjects

import BasePage
import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.Condition.exist
import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Selectors
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selectors.byText

class TopMenu:BasePage(){

    fun clickSignIn() {
        element(byText("Sign in")).click()
    }

    fun userSignedIn(firstname: String, lastname:String){
        element(".account span").shouldHave(text("$firstname $lastname"))
    }

    fun openCard(){
        element(".shopping-cart").click()
    }

    fun logout(){
        element(".logout").click()
        element(byText("Sign in")).should(exist)
    }
}