package antuere.macrobenchmark

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.uiautomator.By
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalBaselineProfilesApi::class)
class BaselineProfileGenerator {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun startup() = baselineProfileRule.collectBaselineProfile(
        packageName = "antuere.how_are_you",
        profileBlock = {
            pressHome()
            startActivityAndWait()

            if (device.findObject(By.res("onboard_pager")) != null){
                scrollOnboardAndEnterApp()
            }
        }
    )
}