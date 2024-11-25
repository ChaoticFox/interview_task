package org.example.pageobjects

import BasePage
import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.selector.ByText
import org.junit.jupiter.api.Assertions.assertTrue

class ItemList : BasePage() {

    fun checkFilteredByPrice(min: Double, max: Double) {
        val prices = elements(".price")
        var itemPrice = 0.0
        prices.forEach { price ->
            itemPrice = convertPrices(price.text())
            assertTrue(itemPrice in min..max)
        }
    }

    fun openItemByIndex(itemNumber: Int) {
        elements(".js-product")[itemNumber].click()
        element(".product-information").should(visible)

    }

    fun getItemCount(): Int {
        return elements(".js-product").size()
    }

    fun setSubCategory(subCategory: String) {
        element(".category-sub-menu").find(ByText(subCategory)).click()
        element("#js-product-list-header h1").shouldHave(text(subCategory))
    }

    fun setPriceFilter(min: Double, max: Double) {
        val slider = element(".faceted-slider")
        //slider min and max values
        var left = slider.getAttribute("data-slider-min")?.toDouble() ?: 0.0
        val right = slider.getAttribute("data-slider-max")?.toDouble() ?: 100.0
        var fullRange: Double = right - left
        //calculate how much to shift
        val sliderWidth: Int = element("div .ui-slider").getCssValue("width").replace("px", "").toInt()
        val shiftLeft = (sliderWidth * (min - left) / fullRange).toInt()
        val shiftRight = (sliderWidth * (right - max) / fullRange).toInt()
        actions().dragAndDropBy(elements(".ui-slider-handle")[0], shiftLeft, 0).perform()
        element(".active_filters").should(exist)
        actions().dragAndDropBy(elements(".ui-slider-handle")[1], -shiftRight, 0).perform()
        val finalFilterText =
            "Active filters Price: €" + String.format("%.2f", min) + " - €" + String.format("%.2f", max)
        element(".active_filters").shouldHave(text(finalFilterText))
    }

}