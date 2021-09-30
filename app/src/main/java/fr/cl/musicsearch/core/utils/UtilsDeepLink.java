package fr.cl.musicsearch.core.utils;

import android.content.Intent;

import fr.cl.musicsearch.root.RootInteractor;



public class UtilsDeepLink {

    static Object intent;

    public static void setDeepLink(Intent intentLink, RootInteractor rootInteractor){
        rootInteractor.setDeepLink(intentLink);
        intent = intentLink;
    }

    public static Object getIntent(){
        return intent;
    }
}


