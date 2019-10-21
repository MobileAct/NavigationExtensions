package navext.activity.request

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.res.getIntOrThrow
import androidx.core.content.withStyledAttributes
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.NavigatorProvider

/**
 * Note: Not Support Animation
 */
@Navigator.Name("activity-request")
class ActivityRequestNavigator(private val context: Context) : Navigator<ActivityRequestNavigator.Destination>() {

    companion object {

        private const val extraNavSource = "android-support-navigation:ActivityNavigator:source"
        private const val extraNavCurrent = "android-support-navigation:ActivityNavigator:current"
    }

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
        val intent = Intent(destination.intent)

        if (args != null) {
            intent.putExtras(args)
        }

        if (navigatorExtras is Extra) {
            navigatorExtras.flags?.also {
                intent.flags = it
            }
        }

        if (context !is Activity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        if (navOptions != null && navOptions.shouldLaunchSingleTop()) {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        if (hostActivity != null) {
            val hostIntent = hostActivity.intent
            val hostCurrentId = hostIntent?.getIntExtra(extraNavCurrent, 0)
            if (hostCurrentId != null) {
                intent.putExtra(extraNavSource, hostCurrentId)
            }
        }

        val destinationId = destination.id
        intent.putExtra(extraNavCurrent, destinationId)

        val extra = navigatorExtras as? Extra

        when (val requestCode = destination.request) {
            null -> when (val activityOptions = extra?.activityOptionsCompat) {
                null -> context.startActivity(intent)
                else -> ActivityCompat.startActivity(context, intent, activityOptions.toBundle())
            }
            else -> when (val activity = hostActivity) {
                null -> throw IllegalStateException("startActivityForResult require activity context")
                else -> when (val activityOptions = extra?.activityOptionsCompat) {
                    null -> hostActivity.startActivityForResult(intent, requestCode)
                    else -> ActivityCompat.startActivityForResult(
                        activity,
                        intent,
                        requestCode,
                        activityOptions.toBundle()
                    )
                }
            }
        }

        return null
    }

    override fun popBackStack(): Boolean {
        if (hostActivity != null) {
            hostActivity.finish()
            return true
        }
        return false
    }

    @NavDestination.ClassType(Activity::class)
    class Destination : NavDestination {

        var intent = Intent()

        var request: Int? = null

        constructor(navigatorProvider: NavigatorProvider) : this(navigatorProvider.getNavigator(ActivityRequestNavigator::class.java))

        constructor(navigator: Navigator<out Destination>) : super(navigator)

        override fun onInflate(context: Context, attrs: AttributeSet) {
            super.onInflate(context, attrs)

            context.withStyledAttributes(attrs, R.styleable.ActivityRequestNavigator) {
                val name = getString(R.styleable.ActivityRequestNavigator_android_name)
                if (name != null) {
                    intent.component = ComponentName(context, parseClassFromName(context, name, Activity::class.java))
                }

                intent.action = getString(R.styleable.ActivityRequestNavigator_action)
                request = getIntOrThrow(R.styleable.ActivityRequestNavigator_request)
            }
        }
    }

    data class Extra(val flags: Int?, val activityOptionsCompat: ActivityOptionsCompat?) : Navigator.Extras
}
