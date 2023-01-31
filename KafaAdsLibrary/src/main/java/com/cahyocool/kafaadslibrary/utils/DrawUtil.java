package com.cahyocool.kafaadslibrary.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.Px;

/**
 * <p>
 * DrawUtil.java
 */
@SuppressWarnings("unused")
public class DrawUtil {
    public static final String TAG = DrawUtil.class.getSimpleName();

    /**
     *
     * @param view
     * @param color
     */
    public static int getColor(View view,
                               @ColorRes int color) {
        return getColor(view.getContext(), color);
    }

    /**
     *
     * @param context
     * @param color
     */
    public static int getColor(Context context,
                               @ColorRes int color) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ?
                context.getResources().getColor(color) :
                context.getResources().getColor(color, null);
    }

    /**
     *
     * @param textView
     * @param color
     */
    public static void setTextColor(TextView textView,
                                    @ColorRes int color) {
        textView.setTextColor(getColor(textView, color));
    }

    /**
     *
     * @param textView
     * @param fillColor
     */
    public static void drawTextUnderLine(TextView textView,
                                         @ColorRes int fillColor) {
        GradientDrawable shape = new GradientDrawable();
        GradientDrawable[] shapes = {shape};
        LayerDrawable layerDrawable = new LayerDrawable(shapes);

        shape.setColor(getColor(textView, fillColor));
        shape.setShape(GradientDrawable.RECTANGLE);

        textView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        layerDrawable.setLayerInset(0, 0,
                textView.getMeasuredHeight() - SizeUtil.convertDpToPx(textView.getContext(), 1),
                0, 0
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(layerDrawable);
        } else {
            textView.setBackgroundDrawable(layerDrawable);
        }
    }

    /**
     *
     * @param view
     * @param fillColor
     */
    public static void drawRectangleBackground(View view,
                                               @ColorRes int fillColor) {
        view.setBackgroundColor(getColor(view, fillColor));
    }

    /**
     *
     * @param view
     * @param fillColor
     * @param borderSize
     * @param borderColor
     */
    public static void drawRectangleBackground(View view,
                                               @ColorRes int fillColor,
                                               @Px int borderSize,
                                               @ColorRes int borderColor) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        drawBackground(view, fillColor, new float[]{
                0, 0, 0, 0, 0, 0, 0, 0}, borderSize, borderColor);
    }

    /**
     *
     * @param view
     * @param fillColor
     */
    public static void drawRoundBackground(View view,
                                           @ColorRes int fillColor) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        int radius = view.getMeasuredHeight() / 2;

        drawBackground(view, fillColor,
                new float[]{radius, radius, radius, radius, radius, radius, radius, radius},
                0, fillColor);
    }

    /**
     *
     * @param view
     * @param fillColor
     * @param radius
     */
    public static void drawRoundBackground(View view,
                                           @ColorRes int fillColor,
                                           float[] radius) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        drawBackground(view, fillColor, radius, 0, fillColor);
    }

    /**
     *
     * @param view
     * @param fillColor
     * @param borderSize
     * @param borderColor
     */
    public static void drawRoundBackground(View view,
                                           @ColorRes int fillColor,
                                           @Px int borderSize,
                                           @ColorRes int borderColor) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        int radius = view.getMeasuredHeight() / 2;

        drawBackground(view, fillColor,
                new float[]{radius, radius, radius, radius, radius, radius, radius, radius},
                borderSize, borderColor);
    }

    /**
     *
     * @param view
     * @param fillColor
     * @param radius
     * @param borderSize
     * @param borderColor
     */
    public static void drawRoundBackground(View view,
                                           @ColorRes int fillColor,
                                           float[] radius,
                                           @Px int borderSize,
                                           @ColorRes int borderColor) {
        drawBackground(view, fillColor, radius, borderSize, borderColor);
    }

    /**
     *
     * @param view
     * @param fillColor
     * @param radius
     * @param borderSize
     * @param borderColor
     */
    private static void drawBackground(View view,
                                       @ColorRes int fillColor,
                                       float[] radius,
                                       @Px int borderSize,
                                       @ColorRes int borderColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(getColor(view, fillColor));
        shape.setStroke(borderSize, getColor(view, borderColor));
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(radius);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(shape);
        } else {
            view.setBackgroundDrawable(shape);
        }
    }

    /**
     *
     * @param view
     * @param startColor
     * @param endColor
     * @param orientation
     */
    public static void drawRectangleShadow(View view,
                                           @ColorRes int startColor,
                                           @ColorRes int endColor,
                                           GradientDrawable.Orientation orientation) {
        GradientDrawable shape = new GradientDrawable(orientation,
                new int[]{getColor(view, startColor), getColor(view, endColor)});
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(shape);
        } else {
            view.setBackgroundDrawable(shape);
        }
    }

    /**
     *
     * @param view
     * @param startColor
     * @param centerColor
     * @param endColor
     * @param orientation
     */
    public static void drawRectangleShadow(View view,
                                           @ColorRes int startColor,
                                           @ColorRes int centerColor,
                                           @ColorRes int endColor,
                                           GradientDrawable.Orientation orientation) {
        GradientDrawable shape = new GradientDrawable(orientation,
                new int[]{getColor(view, startColor), getColor(view, centerColor), getColor(view, endColor)});
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(shape);
        } else {
            view.setBackgroundDrawable(shape);
        }
    }

    /**
     *
     * @param view
     * @param startColor
     * @param endColor
     * @param orientation
     * @param radius
     * @param borderSize
     * @param borderColor
     */
    public static void drawRoundShadow(View view,
                                       @ColorRes int startColor,
                                       @ColorRes int endColor,
                                       GradientDrawable.Orientation orientation,
                                       float[] radius,
                                       @Px int borderSize,
                                       @ColorRes int borderColor) {
        GradientDrawable shape = new GradientDrawable(orientation,
                new int[]{getColor(view, startColor), getColor(view, endColor)});
        shape.setStroke(borderSize, getColor(view, borderColor));
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        shape.setCornerRadii(radius);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(shape);
        } else {
            view.setBackgroundDrawable(shape);
        }
    }

    /**
     *
     * @param window
     * @param color
     */
    public static void setStatusBarColor(Window window,
                                         int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getColor(window.getContext(), color));
        }
    }
}
