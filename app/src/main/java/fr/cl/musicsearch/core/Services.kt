package fr.cl.musicsearch.core

import android.content.Context
import android.content.SharedPreferences
import android.view.Menu
import fr.cl.musicsearch.MainActivity
import java.util.concurrent.atomic.AtomicBoolean


class Services {


    companion object {

        private val menuAsUp = AtomicBoolean(false)
        lateinit var menuI : Menu
        var activity : MainActivity? = null


        //PREFERENCES NAME
        const val USER_PREFERENCES = "USER_PREFERENCES"


        @JvmStatic
        fun getSharedPreferences(context: Context, name: String):SharedPreferences{
            return checkNotNull(context).getSharedPreferences(name, Context.MODE_PRIVATE)
        }


        @JvmStatic
        fun setMenu(menu : Menu){
            menuI = menu
        }

        @JvmStatic
        fun getMenu() : Menu{
            return menuI
        }


        @JvmStatic
        fun setMainActivity(mActivity : MainActivity){
            activity = mActivity
        }

        @JvmStatic
        fun getMainActivity() : MainActivity?{
            return activity
        }


        /**
         * @return returns `true` if the menu is configure as UP button
         */
        fun isMenuAsUp(): Boolean {
            return menuAsUp.get()
        }
    }
}