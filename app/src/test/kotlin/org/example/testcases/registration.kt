package org.example.testcases

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import org.example.base.BaseTest
import org.example.data.UserData
import org.example.pageobjects.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import pageobjects.TopMenu
import java.math.BigDecimal
import java.math.RoundingMode

class Registration : BaseTest() {
    private lateinit var topMenu: TopMenu
    private lateinit var loginRegister: LoginRegister
    private lateinit var itemList: ItemList
    private lateinit var productPage: ProductPage
    private lateinit var cartOverlay: CartOverlay
    private lateinit var shoppingCart: ShoppingCart
    private lateinit var checkout: Checkout

    //set data for positive scenario, can be altered for negative scenarios
    private val allValidUser = UserData(
        "Name",
        "Lname",
        "test21@example.com",
        "test@1231#",
        "Street 100 - 28",
        "12345",
        "France",
        "City"
    )

    @BeforeEach
    fun setup() {
        Selenide.open(Configuration.baseUrl)
        //device switch (only desktop supported)
        when (System.getProperty(("platform"))) {
            "desktop", "tablet-v", "tablet-h", "mobile" -> {
                Selenide.element("#devices .${System.getProperty("platform")}").click()
            }

            else -> {
                Selenide.element("#devices .desktop").click()
            }
        }
        Selenide.switchTo().frame(element(By.id("framelive")))
        topMenu = TopMenu()
        shoppingCart = ShoppingCart()
        itemList = ItemList()
        productPage = ProductPage()
        cartOverlay = CartOverlay()
        checkout = Checkout()
        loginRegister = LoginRegister()
    }

    fun registerUserPositiveFlow() {
        topMenu.clickSignIn()
        loginRegister.proceedToRegistration()
        loginRegister.fillOnlyRequiredData(allValidUser)
        topMenu.userSignedIn(allValidUser.firstName + " " + allValidUser.lastName)
    }

    fun filterByPrice(min: Double, max: Double) {
        itemList.setPriceFilter(min, max)
        itemList.checkFilteredByPrice(min, max)
    }

    fun addToCardFromPDP(index: Int, count: Int, currentTotal: Double): Double {
        itemList.openItemByIndex(index)
        productPage.increaseCountWithButtons(count)
        productPage.addToCart()
        val itemPrice = BigDecimal(productPage.getItemPrice() * count).setScale(2, RoundingMode.HALF_UP).toDouble()
        val total = BigDecimal(currentTotal + itemPrice).setScale(2, RoundingMode.HALF_UP).toDouble()
        cartOverlay.sumIsValid(total)
        cartOverlay.close()
        return total
    }


    //full user journey
    @Test
    fun ujRegisterBuy() {
        registerUserPositiveFlow()
        topMenu.selectItem("Accessories")
        itemList.setSubCategory("Home Accessories")
        filterByPrice(18.00, 23.00)
        //add log which number it was
        var randomItem = (1..itemList.getItemCount() - 1).shuffled().first()
        var sumOfOrder = 0.0
        sumOfOrder += addToCardFromPDP(0, 2, sumOfOrder)
        //bug: after cart overlay closed, user has to press back two times to get to previous page
        productPage.goBackXTimes(".category-top-menu", 2)
        sumOfOrder = addToCardFromPDP(1, 1, sumOfOrder)
        topMenu.openCard()
        shoppingCart.sumIsValid(sumOfOrder)
        shoppingCart.proceedToCheckout()
        checkout.completeCheckout(sumOfOrder, allValidUser)
        //checks order details section
        checkout.checkOrderDetails("Payments by check", "Pick up in-store")
        topMenu.logout()
    }
}
