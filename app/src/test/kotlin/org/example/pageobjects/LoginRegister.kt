package org.example.pageobjects

import BasePage
import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.element


class LoginRegister:BasePage() {
    fun proceedToRegistration(){
        pageLoaded(element(".page-header"), Condition.innerText("Log in to your account"))
        element(".no-account").click()
    }

    fun fillInFirstName(firstName: String){
        element("#field-firstname").setValue(firstName)
    }

    fun fillInLastName(lastName: String){
        element("#field-lastname").setValue(lastName)
    }

    fun fillInEmail(email: String){
        element("#field-email").setValue(email)
    }

    fun fillInPassword(password: String){
        element("#field-password").setValue(password)
    }

    fun setAgreeToTerms(){
        element("[name=\"psgdpr\"]").scrollTo()
        //for some reason selenide see this checkbox as invisible
        runJS("arguments[0].click();", element("[name=\"psgdpr\"]"))
    }

    fun setDataPrivacy(){
        element("[name=\"customer_privacy\"]").scrollTo()
        runJS("arguments[0].click();", element("[name=\"customer_privacy\"]"))
    }

    fun saveUser(){
        element("[data-link-action=\"save-customer\"]").scrollTo().click()
    }

    fun fillOnlyRequiredValidData(firstName: String, lastName: String, email: String, password: String){
        fillInFirstName(firstName)
        fillInLastName(lastName)
        fillInEmail(email)
        fillInPassword(password)
        setAgreeToTerms()
        setDataPrivacy()
        saveUser()
    }
}