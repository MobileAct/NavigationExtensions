package navext.activity.result

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.NavigatorProvider

/**
 * Note: Not Support Animation
 */
@Navigator.Name("activity-result")
class ActivityResultNavigator(private val context: Context) : Navigator<ActivityResultNavigator.Destination>() {

    private val hostActivity: Activity?

    init {
        var context: Context? = context
        var hostActivity: Activity? = null

        while (context is ContextWrapper) {
            if (context is Activity) {
                hostActivity = context
                break
            }
            context = context.baseContext
        }

        this.hostActivity = hostActivity
    }

    override fun createDestination(): Destination {
        return Destination(this)
    }

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ): NavDestination? {

        val data = destination.data

        if(navigatorExtras is Extra){
            data.putExtras(navigatorExtras.data)
        }
        if (args != null) {
            data.putExtras(args)
        }

        if (hostActivity != null) {
            hostActivity.setResult(Activity.RESULT_OK, data)
            hostActivity.finish()
        }

        return null
    }

    override fun popBackStack(): Boolean {
        if (hostActivity != null) {
            hostActivity.setResult(Activity.RESULT_CANCELED)
            hostActivity.finish()
            return true
        }
        return false
    }

    // ToDo: Result.Cancel
    @NavDestination.ClassType(Activity::class)
    class Destination : NavDestination {

        val data = Intent()

        constructor(navigatorProvider: NavigatorProvider) : this(navigatorProvider.getNavigator(ActivityResultNavigator::class.java))

        constructor(navigator: Navigator<out Destination>) : super(navigator)
    }

    data class Extra(val data: Intent) : Navigator.Extras
}