package com.pomohouse.message.tools;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

/**
 * Created by SITTIPONG on 22/9/2559.
 */

public class Tools {

    public static void setTypeface(Context c, View view) {
        Typeface t = Typeface.createFromAsset(c.getAssets(), "fonts/Quicksand-Bold.otf");
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(t);
        }
    }

    public static String subStringName(String name) {
        if (name.length() > 12)
            name = name.substring(0, 12) + "..";
        else
            return name;
        return name;
    }
}
