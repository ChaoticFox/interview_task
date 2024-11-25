package org.example.pageobjects

import BasePage
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.element
import org.junit.jupiter.api.Assertions.assertEquals

class CartOverlay : BasePage() {

    fun sumIsValid(sum: Double) {
        assertEquals(sum, convertPrices(element(".subtotal").text()))
    }

    fun close() {
        element("#blockcart-modal button.close").click()
        element("#blockcart-modal").shouldNotBe(visible)
    }
}