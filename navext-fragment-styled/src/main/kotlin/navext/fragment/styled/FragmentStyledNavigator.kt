package navext.fragment.styled

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.content.withStyledAttributes
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator

@Navigator.Name("fragment-styled")
class FragmentStyledNavigator(
    private val navigatorProvider: NavigatorProvider
) : Navigator<FragmentStyledNavigator.Destination>() {

    companion object {

        const val styleArgumentKey = "fragment-styled-navigator:style-argument"
        const val nextIdArgumentKey = "fragment-styled-navigator:next-id-argument"
    }

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ): NavDestination? {
        val bundle = args ?: Bundle()

        destination.style?.also {
            bundle.putInt(styleArgumentKey, it)
        }
        destination.nextId?.also {
            bundle.putInt(nextIdArgumentKey, it)
        }

        return navigatorProvider[FragmentNavigator::class].navigate(destination, bundle, navOptions, navigatorExtras)
    }

    override fun createDestination(): Destination {
        return Destination(this)
    }

    override fun popBackStack(): Boolean {
        return navigatorProvider[FragmentNavigator::class].popBackStack()
    }

    override fun onSaveState(): Bundle? {
        return navigatorProvider[FragmentNavigator::class].onSaveState()
    }

    override fun onRestoreState(savedState: Bundle) {
        navigatorProvider[FragmentNavigator::class].onRestoreState(savedState)
    }

    @NavDestination.ClassType(Fragment::class)
    class Destination : FragmentNavigator.Destination {

        var style: Int? = null
        var nextId: Int? = null

        constructor(navigatorProvider: NavigatorProvider) : this(navigatorProvider.getNavigator(FragmentStyledNavigator::class.java))

        constructor(navigator: Navigator<out Destination>) : super(navigator)

        override fun onInflate(context: Context, attrs: AttributeSet) {
            super.onInflate(context, attrs)

            context.withStyledAttributes(attrs, R.styleable.FragmentStyledNavigator) {
                style = getResourceIdOrThrow(R.styleable.FragmentStyledNavigator_style)
                nextId = if (hasValue(R.styleable.FragmentStyledNavigator_nextId))
                    getResourceId(R.styleable.FragmentStyledNavigator_nextId, 0)
                else null
            }
        }
    }
}