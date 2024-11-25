package org.example.pageobjects

import BasePage
import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import org.example.data.UserData

class Checkout : BasePage() {

    fun fillMandatoryAddress(address: String, zip: String, country: String, city: String) {
        element("#field-address1").setValue(address)
        element("#field-postcode").should(enabled).setValue(zip)
        element("#field-id_country").should(enabled).selectOption(country)
        element("#field-city").should(enabled).setValue(city)
    }

    fun continueToShipping() {
        element("[name=\"confirm-addresses\"]").doubleClick()
        element("[name=\"confirmDeliveryOption\"]").should(visible)
    }

    fun setShippingMethod(option: Int) {
        runJS("arguments[0].click();", element("#delivery_option_$option"))
    }

    fun continueToPayment() {
        element("[name=\"confirmDeliveryOption\"]").click()
        element("#payment-confirmation button").shouldBe(visible)
    }

    fun setPaymentMethod(option: Int) {
        runJS("arguments[0].click();", element("#payment-option-$option"))
    }

    fun sumIsValid(sum: Double) {
        assert(sum == convertPrices(element(".cart-total .value").text()))
    }

    fun agreeToTerms() {
        //element("#conditions-to-approve input").setSelected(true)
        runJS("arguments[0].click();", element("#conditions-to-approve input"))
    }

    fun placeOrder() {
        element("#payment-confirmation button").click()
        element("#order-confirmation").shouldBe(visible)
    }

    fun checkOrderDetails(payment: String, shipping: String) {
        //reference not empty
        element("#order-reference-value").shouldHave(matchText("""Order reference: \w+"""))
        //payment and shipping method correct
        elements("#order-reference-value  ~ li")[0].shouldHave(exactText("Payment method: $payment"))
        elements("#order-reference-value  ~ li")[1].find("em").shouldHave(exactText(shipping))
    }

    fun completeCheckout(sum: Double, allValidUser: UserData) {
        fillMandatoryAddress(
            allValidUser.address,
            allValidUser.zip,
            allValidUser.country,
            allValidUser.city
        )
        continueToShipping()
        setShippingMethod(1)
        continueToPayment()
        sumIsValid(sum)
        agreeToTerms()
        setPaymentMethod(3)
        placeOrder()
    }
}