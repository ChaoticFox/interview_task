import org.junit.jupiter.api.Test
import org.example.pageobjects.TopMenu
import org.junit.jupiter.api.BeforeEach
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Configuration

class registration : BaseTest() {
    //private val topMenu = topMenu()
    private lateinit var topMenu: TopMenu

    @BeforeEach 
    fun setupTopMenu() {
        Selenide.open(Configuration.baseUrl)
        topMenu = TopMenu()
        topMenu.pageLoaded()
    }

    @Test
    fun startRegistration(){
        topMenu.clickSignIn()
    }
}
