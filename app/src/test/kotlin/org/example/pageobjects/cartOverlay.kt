package org.example.pageobjects

import BasePage
import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.selector.ByText
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertAll

class CartOverlay:BasePage() {

    fun sumIsValid(sum: Double){
        assertEquals(sum, convertPrices(element(".subtotal").text()))
    }

    fun close(){
        element("#blockcart-modal button.close").click()
    }
}