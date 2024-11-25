package org.example.pageobjects

import BasePage
import com.codeborne.selenide.Condition.value
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements

class ProductPage : BasePage() {

    fun increaseCountWithButtons(targetCount: Int) {
        var value = element("#quantity_wanted").value?.toInt()
        if (value != null) {
            while (value < targetCount) {
                elements(".qty button")[0].click()
                value++
            }
        }
        element("#quantity_wanted").shouldHave(value(targetCount.toString()))
    }

    fun getItemPrice(): Double {
        val price = element(".current-price-value").getAttribute("content")
        if (price != null) {
            return price.toDouble()
        } else {
            throw IllegalArgumentException()
        }
    }

    fun addToCart() {
        element(".add-to-cart").click()
        element("#blockcart-modal").should(visible)
    }

}