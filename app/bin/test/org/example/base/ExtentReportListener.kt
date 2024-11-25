import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler
import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.ExtentTest
import com.aventstack.extentreports.Status
import com.aventstack.extentreports.reporter.ExtentSparkReporter

class ExtentReportListener : TestExecutionExceptionHandler {

    companion object {
        private val extentReports: ExtentReports = ExtentReports().apply {
            attachReporter(ExtentSparkReporter("report/test.html"))
        }
        var extentTest: ExtentTest? = null

        fun createTest(testName: String) {
            extentTest = extentReports.createTest(testName)
        }

        fun flush() {
            extentReports.flush()
        }
    }

    override fun handleTestExecutionException(context: ExtensionContext?, throwable: Throwable?) {
        extentTest?.log(Status.FAIL, "Test failed: ${throwable?.message}")
        throwable?.printStackTrace()
    }
}
