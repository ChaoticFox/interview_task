package org.example.pageobjects

import BasePage
import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.selector.ByText

class ProductPage:BasePage() {

    fun setCount(finalCount:Int){
        element("#quantity_wanted").setValue(finalCount.toString())
    }

    fun getItemPrice():Double{
        val price = element(".current-price-value").getAttribute("content")
        if (price != null) {
            return price.toDouble()
        }else{
            throw IllegalArgumentException()
        }
    }

    fun addToCart(){
        element(".add-to-cart").click()
    }

}