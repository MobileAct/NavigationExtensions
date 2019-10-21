package navext.fragment.styled

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment

fun Fragment.styledNavNextId(): Lazy<Int?> {
    return StyledNavArg {
        var result = arguments?.getInt(FragmentStyledNavigator.nextIdArgumentKey, 0)
        if (result == 0) {
            result = null
        }
        result
    }
}

@SuppressLint("Recycle")
fun <T> Fragment.styledNavArg(attr: Int, finder: SingleTypedArray.() -> T): Lazy<T?> {
    return StyledNavArg {
        val style = arguments?.getInt(FragmentStyledNavigator.styleArgumentKey) ?: return@StyledNavArg null
        val typedArray = context?.obtainStyledAttributes(style, intArrayOf(attr)) ?: return@StyledNavArg null
        val singleTypedArray = SingleTypedArray(typedArray)
        val result = singleTypedArray.finder()
        singleTypedArray.recycle()
        result
    }
}

private class StyledNavArg<T>(private val finder: () -> T) : Lazy<T?> {

    private var wasFind = false

    override var value: T? = null
        get() {
            if (wasFind) {
                return field
            }
            field = finder.invoke()
            wasFind = true
            return field
        }


    override fun isInitialized(): Boolean {
        return wasFind
    }

}