package org.example.pageobjects

import BasePage
import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.exist
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import kotlin.test.assertEquals

class ShoppingCart:BasePage() {
    init{
        runJS("document.querySelectorAll('input').forEach(function(input) {" + "input.setAttribute('autocomplete', 'off');" + "});")
    }

    fun sumIsValid(sum: Double){
        assertEquals(sum,convertPrices(element(".cart-total .value").text()))
    }

    fun proceedToCheckout(){
        element(".checkout").click()

    }

    fun clearCart(){
        if(element(".cart-products-count").text()!="(0)"){
            element(".shopping-cart").click()
            val items = elements(".remove-from-cart")
            items.forEach { item ->
                item.click()
            }
            element(".no-items").should(exist)
        }
    }
}