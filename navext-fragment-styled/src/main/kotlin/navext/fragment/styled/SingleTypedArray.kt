package navext.fragment.styled

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import androidx.annotation.RequiresApi

inline class SingleTypedArray(private val typedArray: TypedArray) {

    fun getBoolean(defaultValue: Boolean): Boolean = typedArray.getBoolean(0, defaultValue)

    fun getColor(defaultValue: Int): Int = typedArray.getColor(0, defaultValue)

    fun getColorStateList(): ColorStateList? = typedArray.getColorStateList(0)

    fun getDimension(defaultValue: Float): Float = typedArray.getDimension(0, defaultValue)

    fun getDimensionPixelOffset(defaultValue: Int): Int = typedArray.getDimensionPixelOffset(0, defaultValue)

    fun getDimensionPixelSize(defaultValue: Int): Int = typedArray.getDimensionPixelSize(0, defaultValue)

    fun getDrawable(): Drawable? = typedArray.getDrawable(0)

    fun getFloat(defaultValue: Float): Float = typedArray.getFloat(0, defaultValue)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFont(): Typeface? = typedArray.getFont(0)

    fun getFraction(base: Int, pbase: Int, defaultValue: Float): Float =
        typedArray.getFraction(0, base, pbase, defaultValue)

    fun getInt(defaultValue: Int): Int = typedArray.getInt(0, defaultValue)

    fun getInteger(defaultValue: Int): Int = typedArray.getInteger(0, defaultValue)

    fun getLayoutDimension(defaultValue: Int): Int = typedArray.getLayoutDimension(0, defaultValue)

    fun getLayoutDimension(name: String): Int = typedArray.getLayoutDimension(0, name)

    fun getNonResourceString(): String = typedArray.getNonResourceString(0)

    fun getResourceId(defaultValue: Int): Int = typedArray.getResourceId(0, defaultValue)

    fun getString(): String? = typedArray.getString(0)

    fun getText(): CharSequence? = typedArray.getText(0)

    fun getTextArray(): Array<CharSequence>? = typedArray.getTextArray(0)

    fun getType(): Int = typedArray.getType(0)

    fun getValue(outValue: TypedValue): Boolean = typedArray.getValue(0, outValue)

    fun hasValue(): Boolean = typedArray.hasValue(0)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun hasValueOrEmpty(): Boolean = typedArray.hasValueOrEmpty(0)

    fun recycle() = typedArray.recycle()
}