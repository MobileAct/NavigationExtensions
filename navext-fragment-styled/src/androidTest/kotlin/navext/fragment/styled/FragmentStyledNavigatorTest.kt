package navext.fragment.styled

import androidx.navigation.NavigatorProvider
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.plusAssign
import androidx.test.annotation.UiThreadTest
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import navext.fragment.styled.test.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

@LargeTest
class FragmentStyledNavigatorTest {

    companion object {

        private const val firstId = 1
        private const val secondId = 2
        private const val thirdId = 3
    }

    @get:Rule
    val activityRule = ActivityTestRule(EmptyActivity::class.java)

    @UiThreadTest
    @Test
    fun testSimple() {
        val activity = activityRule.activity
        val fragmentManager = activity.supportFragmentManager
        val navigationProvider = NavigatorProvider().apply {
            this += FragmentNavigator(activity, fragmentManager, R.id.container)
        }
        val fragmentStyledNavigator = FragmentStyledNavigator(navigationProvider)
        val destination = fragmentStyledNavigator.createDestination()

        /**
         * Push FirstFragment
         */
        destination.apply {
            id = firstId
            className = FirstFragment::class.java.name
            nextId = secondId
            style = R.style.Screen1
        }
        fragmentStyledNavigator.navigate(destination, null, null, null)
        fragmentManager.executePendingTransactions()

        fragmentManager.findFragmentById(R.id.container).also {
            assertNotNull(it)
            assertEquals(true, it is FirstFragment)
            it as FirstFragment
            assertEquals(secondId, it.nextId)
            assertEquals("Title1", it.title)
        }

        /**
         * Push SecondFragment
         */
        destination.apply {
            id = secondId
            className = SecondFragment::class.java.name
            nextId = thirdId
            style = R.style.Screen2
        }
        fragmentStyledNavigator.navigate(destination, null, null, null)
        fragmentManager.executePendingTransactions()

        fragmentManager.findFragmentById(R.id.container).also {
            assertNotNull(it)
            assertEquals(true, it is SecondFragment)
            it as SecondFragment
            assertEquals(thirdId, it.nextId)
            assertEquals("Title2", it.title)
        }

        /**
         * Push ThirdFragment
         */
        destination.apply {
            id = thirdId
            className = ThirdFragment::class.java.name
            nextId = null
            style = null
        }
        fragmentStyledNavigator.navigate(destination, null, null, null)
        fragmentManager.executePendingTransactions()

        fragmentManager.findFragmentById(R.id.container).also {
            assertNotNull(it)
            assertEquals(true, it is ThirdFragment)
            it as ThirdFragment
            assertEquals(null, it.nextId)
            assertEquals(null, it.title)
        }

        /**
         * Pop to SecondFragment
         */
        fragmentStyledNavigator.popBackStack()
        fragmentManager.executePendingTransactions()

        fragmentManager.findFragmentById(R.id.container).also {
            assertNotNull(it)
            assertEquals(true, it is SecondFragment)
            it as SecondFragment
            assertEquals(thirdId, it.nextId)
            assertEquals("Title2", it.title)
        }

        /**
         * Pop to FirstFragment
         */
        fragmentStyledNavigator.popBackStack()
        fragmentManager.executePendingTransactions()

        fragmentManager.findFragmentById(R.id.container).also {
            assertNotNull(it)
            assertEquals(true, it is FirstFragment)
            it as FirstFragment
            assertEquals(secondId, it.nextId)
            assertEquals("Title1", it.title)
        }
    }

    @UiThreadTest
    @Test
    fun testStyledArgument() {
        val activity = activityRule.activity
        val fragmentManager = activity.supportFragmentManager
        val navigationProvider = NavigatorProvider().apply {
            this += FragmentNavigator(activity, fragmentManager, R.id.container)
        }
        val fragmentStyledNavigator = FragmentStyledNavigator(navigationProvider)
        val destination = fragmentStyledNavigator.createDestination()

        /**
         * Push FirstFragment With Screen1
         */
        destination.apply {
            id = firstId
            className = FirstFragment::class.java.name
            style = R.style.Screen1
        }
        fragmentStyledNavigator.navigate(destination, null, null, null)
        fragmentManager.executePendingTransactions()

        fragmentManager.findFragmentById(R.id.container).also {
            assertNotNull(it)
            assertEquals(true, it is FirstFragment)
            it as FirstFragment
            assertEquals("Title1", it.title)
        }

        /**
         * Push FirstFragment With Screen2
         */
        destination.apply {
            id = firstId
            className = FirstFragment::class.java.name
            style = R.style.Screen2
        }
        fragmentStyledNavigator.navigate(destination, null, null, null)
        fragmentManager.executePendingTransactions()

        fragmentManager.findFragmentById(R.id.container).also {
            assertNotNull(it)
            assertEquals(true, it is FirstFragment)
            it as FirstFragment
            assertEquals("Title2", it.title)
        }
    }

    @UiThreadTest
    @Test
    fun testWithFragmentNavigator() {
        val activity = activityRule.activity
        val fragmentManager = activity.supportFragmentManager
        val fragmentNavigator = FragmentNavigator(activity, fragmentManager, R.id.container)
        val navigationProvider = NavigatorProvider().apply {
            this += fragmentNavigator
        }
        val fragmentStyledNavigator = FragmentStyledNavigator(navigationProvider)

        /**
         * Push FirstFragment By FragmentNavigator
         */
        fragmentNavigator.navigate(
            fragmentNavigator.createDestination().apply {
                id = firstId
                className = FirstFragment::class.java.name
            },
            null,
            null,
            null
        )
        fragmentManager.executePendingTransactions()

        fragmentManager.findFragmentById(R.id.container).also {
            assertNotNull(it)
            assertEquals(true, it is FirstFragment)
        }

        /**
         * Push SecondFragment By FragmentStyledNavigator
         */
        fragmentStyledNavigator.navigate(
            fragmentStyledNavigator.createDestination().apply {
                id = secondId
                className = SecondFragment::class.java.name
            },
            null,
            null,
            null
        )
        fragmentManager.executePendingTransactions()

        fragmentManager.findFragmentById(R.id.container).also {
            assertNotNull(it)
            assertEquals(true, it is SecondFragment)
        }

        /**
         * Push ThirdFragment By FragmentNavigator
         */
        fragmentNavigator.navigate(
            fragmentNavigator.createDestination().apply {
                id = thirdId
                className = ThirdFragment::class.java.name
            },
            null,
            null,
            null
        )
        fragmentManager.executePendingTransactions()

        fragmentManager.findFragmentById(R.id.container).also {
            assertNotNull(it)
            assertEquals(true, it is ThirdFragment)
        }

        /**
         * Pop to SecondFragment By FragmentStyledNavigator
         */
        fragmentStyledNavigator.popBackStack()
        fragmentManager.executePendingTransactions()

        fragmentManager.findFragmentById(R.id.container).also {
            assertNotNull(it)
            assertEquals(true, it is SecondFragment)
        }

        /**
         * Pop to FirstFragment By FragmentNavigator
         */
        fragmentNavigator.popBackStack()
        fragmentManager.executePendingTransactions()

        fragmentManager.findFragmentById(R.id.container).also {
            assertNotNull(it)
            assertEquals(true, it is FirstFragment)
        }
    }
}