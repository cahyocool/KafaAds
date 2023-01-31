package com.cahyocool.kafaadslibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * <p>
 * SizeUtil.java<br/>
 * 사이즈와 관련된 유틸 함수들이 모여있는 클래스<br/>
 * </p><br/>
 * Created by OneKey on 2017-02-09.<br/>
 * Updated by OneKey at 2018.<br/>
 */
@SuppressWarnings("unused")
public class SizeUtil {
    private static final String TAG = SizeUtil.class.getSimpleName();

    /**
     * 현재 디바이스의 밀도 비율를 가져오는 함수
     *
     * @param context 문맥
     * @return density ratio
     */
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * px을 밀도에 따라 dp로 변경해 반환하는 함수
     *
     * @param context 문맥
     * @param px      pixel value
     * @return density indepedent pixel value
     */
    public static int convertPxToDp(Context context,
                                    int px) {
        float density = context.getResources().getDisplayMetrics().density;

        return (int) (px / density);
    }

    /**
     * dp를 밀도에 따라 px로 변경해 반환하는 함수
     *
     * @param context 문맥
     * @param dp      density indepedent pixel value
     * @return pixel value
     */
    public static int convertDpToPx(Context context, double dp) {
        float density = context.getResources().getDisplayMetrics().density;

        return (int) (dp * density + 0.5);
    }

    /**
     * screen의 width를 px로 반환하는 함수
     *
     * @param activity 액티비티
     * @return pixel value of screen width
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        return displaymetrics.widthPixels;
    }

    /**
     * screen의 height를 px로 반환하는 함수
     *
     * @param activity 액티비티
     * @return pixel value of screen height
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        return displaymetrics.heightPixels;
    }

    /**
     * screen의 width를 px로 반환하는 함수
     *
     * @param context 문맥
     * @return pixel value of screen width
     */
    @Deprecated
    public static int getScreenWidth(Context context) {
        Display dis = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        return dis.getWidth();
    }

    /**
     * screen의 height를 px로 반환하는 함수
     *
     * @param context 문맥
     * @return pixel value of screen height
     */
    @Deprecated
    public static int getScreenHeight(Context context) {
        Display dis = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        return dis.getHeight();
    }

    /**
     *  action bar의 height를 px로 반환하는 함수
     *  ?attr/actionBarSize 참고
     *
     * @param context 문맥
     * @return pixel value of action bar height
     */
    public static int getActionBarHeight(Context context) {
        TypedValue typedValue = new TypedValue();

        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize,
                typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data,
                    context.getResources().getDisplayMetrics());
        }
        return convertDpToPx(context, 56);
    }
}
