package org.example.pageobjects

import BasePage
import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.selector.ByText

class CartOverlay:BasePage() {

    fun sumIsValid(sum: Double){
        assert(sum==convertPrices(element(".subtotal").text()))
    }

    fun close(){
        element("#blockcart-modal button.close").click()
    }
}