package org.example.pageobjects

import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selectors.*

class TopMenu(){
    fun pageLoaded() {
        //element(byText("Sign in")).should(exist)
    }

    fun clickSignIn() {
        element(byText("Sign in")).click()
    }
}