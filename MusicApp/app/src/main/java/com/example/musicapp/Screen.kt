package com.example.musicapp

import androidx.annotation.DrawableRes

sealed  class Screen(val title : String, val route : String) {
    sealed class BottomScreen(val btitle: String, val broute : String
    ,@DrawableRes val icon: Int) : Screen(btitle,broute){
                object Home : BottomScreen("Home","home",R.drawable.baseline_music_video_24)
                object Library : BottomScreen("Library", "library",R.drawable.library)
                object Browse : BottomScreen("Browse","browse",R.drawable.browse)
    }
    sealed class DrawerScreen(val dtitle: String,
                            val dRoute : String, @DrawableRes val icon : Int): Screen(dtitle,dRoute){
                                object Accounts : DrawerScreen(
                                    "Account",
                                    "account",
                                    R.drawable.ic_addaccount // Changed from R.drawable.ic_account
                                )
                                object Subscription : DrawerScreen(
                                    "Subscription",
                                    "subscription",
                                    R.drawable.ic_subscribe
                                )
                                object Addaccount : DrawerScreen(
                                    "Add Account",
                                    "add_account",
                                    R.drawable.ic_addaccount
                                )
                            }
}

val screensInBottom = listOf(
    Screen.BottomScreen.Home,
    Screen.BottomScreen.Browse,
    Screen.BottomScreen.Library
)
val screensInDrawer = listOf(
    Screen.DrawerScreen.Addaccount,
    Screen.DrawerScreen.Subscription,
    Screen.DrawerScreen.Accounts
)