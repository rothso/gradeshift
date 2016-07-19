@file:Suppress("NOTHING_TO_INLINE")

package io.gradeshift.ui.ext

import android.app.Fragment
import android.content.Context
import android.os.Build
import android.support.annotation.StyleRes
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.ctx
import org.jetbrains.anko.wrapContent

fun TextView.setTextAppearanceCompat(@StyleRes resId: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        @Suppress("DEPRECATION")
        setTextAppearance(context, resId)
    } else {
        setTextAppearance(resId)
    }
}

// Ferenc Boldog <https://github.com/fboldog>

// Waiting for a PR to get merged, in the meantime copy/paste
// https://github.com/Kotlin/anko/pull/144

fun Context.attr(attribute: Int): TypedValue {
    val typed = TypedValue()
    ctx.theme.resolveAttribute(attribute, typed, true)
    return typed
}

fun Context.dimenAttr(attribute: Int): Int = TypedValue.complexToDimensionPixelSize(attr(attribute).data, resources.displayMetrics) // -> px
fun Context.colorAttr(attribute: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        resources.getColor(attr(attribute).resourceId, ctx.theme)
    } else {
        @Suppress("DEPRECATION")
        resources.getColor(attr(attribute).resourceId)
    }
} // -> color

inline fun AnkoContext<*>.dimenAttr(attribute: Int): Int = ctx.dimenAttr(attribute)
inline fun AnkoContext<*>.colorAttr(attribute: Int): Int = ctx.colorAttr(attribute)
inline fun AnkoContext<*>.attribute(attribute: Int): TypedValue = ctx.attr(attribute)

inline fun View.dimenAttr(attribute: Int): Int = context.dimenAttr(attribute)
inline fun View.colorAttr(attribute: Int): Int = context.colorAttr(attribute)
inline fun View.attr(attribute: Int): TypedValue = context.attr(attribute)

inline fun Fragment.dimenAttr(attribute: Int): Int = activity.dimenAttr(attribute)
inline fun Fragment.colorAttr(attribute: Int): Int = activity.colorAttr(attribute)
inline fun Fragment.attr(attribute: Int): TypedValue = activity.attr(attribute)

// Anotha one
// https://github.com/Kotlin/anko/issues/153

private val defaultInit: Any.() -> Unit = {}
fun <T : android.view.View> T.ctlparams(
        width: kotlin.Int = wrapContent, height: kotlin.Int = wrapContent,
        init: android.support.design.widget.CollapsingToolbarLayout.LayoutParams.() -> kotlin.Unit = defaultInit): T {
    val layoutParams = android.support.design.widget.CollapsingToolbarLayout.LayoutParams(width, height)
    layoutParams.init()
    this@ctlparams.layoutParams = layoutParams
    return this
}