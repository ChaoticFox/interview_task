package org.example.testcases

import org.example.base.BaseTest
import org.junit.jupiter.api.Test
import pageobjects.TopMenu
import org.junit.jupiter.api.BeforeEach
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Configuration
import org.example.data.UserData
import org.example.pageobjects.*
import org.openqa.selenium.By

class Registration : BaseTest() {
    private lateinit var topMenu: TopMenu
    private lateinit var loginRegister: LoginRegister
    private lateinit var menu: Menu
    private lateinit var itemList: itemList
    private lateinit var productPage: ProductPage
    private lateinit var cartOverlay: CartOverlay
    private lateinit var shoppingCart: ShoppingCart
    private lateinit var checkout: Checkout
    //set data for positive scenario, can be altered for negative scenarios
    private val allValidUser = UserData("Name",
        "Lname",
        "test8@example.com",
        "test@1231#",
        "Street 100 - 28",
        "12345",
        "France",
        "City")
    @BeforeEach 
    fun setupTopMenu() {
        Selenide.open(Configuration.baseUrl)
        //device switch
        if(System.getProperty("platform")!=null){
            Selenide.element("#devices ."+System.getProperty("platform")).click()
        }else{
            Selenide.element("#devices .desktop").click()
        }
        Selenide.switchTo().frame(element(By.id("framelive")))
        topMenu = TopMenu()
        menu = Menu()
        shoppingCart = ShoppingCart()
        itemList = itemList()
        productPage = ProductPage()
        cartOverlay = CartOverlay()
        checkout = Checkout()
        loginRegister = LoginRegister()
    }

    fun registerUserPositiveFlow(){
        topMenu.clickSignIn()
        loginRegister.proceedToRegistration()
        loginRegister.fillOnlyRequiredValidData(allValidUser.firstName,
            allValidUser.lastName,
            allValidUser.email,
            allValidUser.password)
        topMenu.userSignedIn(allValidUser.firstName,allValidUser.lastName)
    }

    fun openHomeAccessories(){
        menu.selectItem("Accessories")
        itemList.setSubCategory("Home Accessories")
    }

    fun filterByPrice(min: Double, max: Double){
        itemList.setPriceFilter(min, max)
        itemList.checkFilteredByPrice(min,max)
    }

    fun addToCardFromPDP(count: Int):Double{
        productPage.setCount(count)
        productPage.addToCart()
        val itemPrice = productPage.getItemPrice()
        val total = itemPrice*count
        cartOverlay.sumIsValid(total)
        cartOverlay.close()
        return total
    }


    //full user journey
    @Test
    fun ujRegisterBuy() {
        registerUserPositiveFlow()
        openHomeAccessories()
        filterByPrice(18.00,23.00)
        //add log which number it was
        var randomItem = (0..itemList.getItemCount()-1).shuffled().first()
        itemList.openItemByIndex(0)
        var sumOfOrder = addToCardFromPDP(2)
        productPage.goBack(".products")
        itemList.openItemByIndex(0)
        sumOfOrder+=addToCardFromPDP(1)
        topMenu.openCard()
        shoppingCart.sumIsValid(sumOfOrder)
        shoppingCart.proceedToCheckout()
        checkout.fillMandatoryAddress(allValidUser.address,
            allValidUser.zip,
            allValidUser.country,
            allValidUser.city)
        checkout.continueToShipping()
        checkout.continueToPayment()
        checkout.sumIsValid(sumOfOrder)
        checkout.agreeToTerms()
        checkout.placeOrder()
        //checks order details section
        checkout.checkOrderDetails("Payments by check", "Pick up in-store")
        topMenu.logout()
    }
}
