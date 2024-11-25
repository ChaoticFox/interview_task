package pageobjects

import BasePage
import com.codeborne.selenide.Condition.exist
import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Selectors.byText
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.selector.ByText

class TopMenu : BasePage() {

    fun clickSignIn() {
        element(byText("Sign in")).click()
        verifyPageHeader("Log in to your account")
    }

    fun userSignedIn(userName: String) {
        element(".account span").shouldHave(text(userName))
    }

    fun openCard() {
        element(".shopping-cart").click()
    }

    fun logout() {
        element(".logout").click()
        element(byText("Sign in")).should(exist)
    }

    fun selectItem(option: String) {
        element("#top-menu").find(ByText(option)).click()
    }
}