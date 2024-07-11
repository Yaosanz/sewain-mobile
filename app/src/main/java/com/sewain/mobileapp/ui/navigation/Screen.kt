package com.sewain.mobileapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailCatalog : Screen("detail_catalog/{id}")
    object Checkout : Screen("checkout/{id}")

    object Payment : Screen("payment/{url}")
    object ListTransaction : Screen("list_transaction")
    object Notification : Screen("notification")
    object Profile : Screen("profile")
    object DetailProfile : Screen("profile/detail/{id}") {
        fun createRoute(id: String) = "profile/detail/$id"
    }
    object ChangePassword : Screen("profile/change/{id}") {
        fun createRoute(id: String) = "profile/change/$id"
    }

    object Adresses : Screen("profile/address/{id}") {
        fun createRoute(id: String) = "profile/address/$id"
    }

    object SocialMedia : Screen("profile/socialMedia/{id}") {
        fun createRoute(id: String) = "profile/socialMedia/$id"
    }

    object ShopAccount : Screen("profile/shopAccount/{id}") {
        fun createRoute(id: String) = "profile/shopAccount/$id"
    }

    object Maps : Screen("profile/address/maps/{id}") {
        fun createRoute(id: String) = "profile/address/maps/$id"
    }

    object CreateCatalog : Screen("create_catalog/{shop_id}") {
        fun createRoute(shopId: String?) = "create_catalog/$shopId"
    }

    object Login : Screen("login")
    object Register : Screen("register")
}
