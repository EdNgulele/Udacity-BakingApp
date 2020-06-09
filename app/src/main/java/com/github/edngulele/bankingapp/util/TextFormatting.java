package com.github.edngulele.bankingapp.util;

import android.annotation.SuppressLint;

import androidx.test.espresso.IdlingResource;

import com.github.edngulele.bankingapp.IdlingResource.RecipesIdlingResource;


public class TextFormatting {


    @SuppressLint("DefaultLocale")
    public static String fmt(double d) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s", d);
    }


    private static RecipesIdlingResource sIdlingResource;

    public static IdlingResource getIdlingResource() {

        if (sIdlingResource == null) {
            sIdlingResource = new RecipesIdlingResource();
        }
        return sIdlingResource;
    }

    public static void setIdleResourceTo(boolean isIdleNow) {
        if (sIdlingResource == null) {
            sIdlingResource = new RecipesIdlingResource();
        }
        sIdlingResource.setIdleState(isIdleNow);
    }
}
