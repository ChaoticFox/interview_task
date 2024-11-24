package org.example.pageobjects

import BasePage
import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import com.codeborne.selenide.WebDriverRunner
import com.codeborne.selenide.selector.ByText
import org.openqa.selenium.NoAlertPresentException

class Checkout:BasePage() {
    //check which form opened

    fun fillMandatoryAddress(address:String, zip:String, country:String, city:String){
        element("#field-address1").setValue(address)
        element("#field-postcode").should(enabled).setValue(zip)
        element("#field-id_country").should(enabled).selectOption(country)
        element("#field-city").should(enabled).setValue(city)
    }

    fun continueToShipping(){
        element("[name=\"confirm-addresses\"]").click()
        doubleClick()
        //element(".delivery-options-list").should(visible)
    }

    fun continueToPayment(){
        element("[name=\"confirmDeliveryOption\"]").click()
    }

    fun setPaymentMethod(option: Int){
        element("payment-option-"+option.toString()).setSelected(true)

    }

    fun sumIsValid(sum: Double){
        assert(sum==convertPrices(element(".cart-total .value").text()))
    }

    fun agreeToTerms(){
        element("#conditions_to_approve[terms-and-conditions]").setSelected(true)
    }

    fun placeOrder(){
        element("#payment-confirmation").click()
    }

    fun checkOrderDetails(payment: String, shipping: String){
        //reference not empty
        element("#order-reference-value").shouldHave(matchText("""Order reference: \w+"""))
        //payment method correct
        elements("#order-reference-value  ~ li")[0].shouldHave(exactText("Payment method: "+payment))
        elements("#order-reference-value  ~ li")[1].find("em").shouldHave(exactText(shipping))
    }
}