package antuere.macrobenchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startupBaselineOFF() = startup(CompilationMode.None())

    @Test
    fun startupBaselineON() = startup(CompilationMode.Partial())

    @Test
    fun onboardFlowBaselineOFF() = onboardFlow(CompilationMode.None())

    @Test
    fun onboardFlowBaselineON() = onboardFlow(CompilationMode.Partial())

    private fun startup(mode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "antuere.how_are_you",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = mode
    ) {
        pressHome()
        startActivityAndWait()
    }

    private fun onboardFlow(mode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "antuere.how_are_you",
        metrics = listOf(FrameTimingMetric()),
        iterations = 1,
        startupMode = StartupMode.COLD,
        compilationMode = mode
    ) {
        pressHome()
        startActivityAndWait()

        scrollOnboardAndEnterApp()
    }

}

fun MacrobenchmarkScope.scrollOnboardAndEnterApp() {
    val pager = device.findObject(By.res("onboard_pager"))
    val finishButton = device.findObject(By.res("finish_btn"))
    pager.setGestureMargin(80)

    repeat(4) {
        pager.scroll(Direction.RIGHT, 80f)
    }
    device.waitForIdle()

    finishButton.click()
}