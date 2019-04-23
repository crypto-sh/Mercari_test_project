package com.mrecun


import android.os.Bundle
import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.viewpager.widget.ViewPager
import com.mrecun.model.PageModel
import com.mrecun.ui.activitis.MainActivity
import com.mrecun.ui.fragments.PageFragment
import com.mrecun.utils.GenerateResponse
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainInstrumentedTest {

    @get:Rule
    var testRule = CountingTaskExecutorRule()

    @get:Rule
    var rule = ActivityTestRule(MainActivity::class.java, false, true)

    @Test
    fun step_0_useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().context
        assertEquals("com.mrecun.test", appContext.packageName)
    }

    @Test
    fun step_1_progressShowingStatus() {
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        onView(withId(R.id.viewPager)).check(matches(isDisplayed()))
        onView(withId(R.id.errorTextView)).check(matches(not(isDisplayed())))
    }

    @Test
    fun step_2_startLoadingData() {
        val scenario = launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity {
            assertTrue(it.viewModel.checkStartingValue()!!)
        }
        scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun step_3_loadViewPager() {
        val tabCount = 3
        val senario = launch(MainActivity::class.java)
        senario.moveToState(Lifecycle.State.CREATED)
        senario.onActivity {
            val items = GenerateResponse.generateViewPagerResponse(tabCount)
            it.initPager(items)
        }
        senario.moveToState(Lifecycle.State.RESUMED)
        senario.onActivity {
            val pager = it.findViewById<ViewPager>(R.id.viewPager)
            assertEquals(pager.adapter?.count, tabCount)
        }
    }

    @Test
    fun step_5_PageFragmentScenario() {
        val testPageMode = PageModel(
            "All",
            "https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/all.json"
        )
        val args = Bundle().apply {
            putParcelable(PageFragment.ARG_PAGE, testPageMode)
        }
        val scenario = launchFragmentInContainer<PageFragment>(args)
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onFragment {
            it.loadData()
        }
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onFragment {
            val page = it.viewModel.page.value
            assertEquals(page?.name, testPageMode.name)
            assertEquals(page?.data, testPageMode.data)
        }
    }
}
