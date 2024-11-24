package org.example.pageobjects

import BasePage
import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.selector.ByText

public class Menu: BasePage(){

    fun hoverOption(option: String){
        element(ByText(option)).hover()
    }

    fun selectItem(option: String){
        element("#top-menu").find(ByText(option)).click()
    }

}