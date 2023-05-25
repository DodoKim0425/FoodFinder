package com.ssafy.foodfind

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ssafy.foodfind.data.entity.OrderDetail
import com.ssafy.foodfind.data.entity.Truck
import com.ssafy.foodfind.data.entity.User

object SharedPrefs {
    private const val LOGIN_SESSION = "login.session"
    private var user: User? = null
    private var truckId: Int = -1
    private var shoppingList : MutableList<OrderDetail> = mutableListOf()

    fun openSharedPrep(context: Context): SharedPreferences {
        return context.getSharedPreferences(LOGIN_SESSION, Context.MODE_PRIVATE)
    }

    fun getTruckId() : Int {
        return App.prefs.getInt("truckId", -1)
    }

    fun saveTruckId(truckId : Int) {
        this.truckId = truckId
        val editor = App.prefs.edit()
        editor.putInt("truckId", truckId)
        editor.apply()
    }
    fun getUserInfo() = user

    fun saveUserInfo(user: User) {
        this.user = user
        val editor = App.prefs.edit()
        editor.putInt("userId", user.userId)
        editor.putString("username", user.username)
        editor.putString("phoneNumber", user.phoneNumber)
        editor.putString("userType", user.userType)
        editor.apply()
    }

    fun getShoppingList() : MutableList<OrderDetail> {
        val cartItemsJson = App.prefs.getString("cartItems", null)
        val cartItems = if (cartItemsJson != null) {
            Gson().fromJson(cartItemsJson, object : TypeToken<List<OrderDetail>>() {}.type)
        } else {
            mutableListOf<OrderDetail>()
        }

        this.shoppingList = cartItems
        return shoppingList
    }

    fun addShoppingList(detail: OrderDetail, truck: Truck) {
        val cartItemsJson = App.prefs.getString("cartItems", null)
        val cartItems = if (cartItemsJson != null && App.prefs.getInt("truckId", -1) == detail.item.truckId) {
            Gson().fromJson(cartItemsJson, object : TypeToken<List<OrderDetail>>() {}.type)
        } else {
            val editor = App.prefs.edit()
            editor.putInt("truckId", truck.truckId)
            editor.putString("truckName", truck.name)
            editor.apply()
            mutableListOf<OrderDetail>()
        }

        val existingItem = cartItems.find { it.item.itemId == detail.item.itemId }
        if (existingItem != null) {
            existingItem.count += detail.count
        } else {
            cartItems.add(detail)
        }

        val editor = App.prefs.edit()
        editor.putString("cartItems", Gson().toJson(cartItems))
        editor.apply()
    }

    fun removeShoppingList(detail: OrderDetail) {
        val cartItemsJson = App.prefs.getString("cartItems", null)
        val cartItems = if (cartItemsJson != null) {
            Gson().fromJson(cartItemsJson, object : TypeToken<List<OrderDetail>>() {}.type)
        } else {
            mutableListOf<OrderDetail>()
        }

        cartItems.remove(detail)

        val editor = App.prefs.edit()
        editor.putString("cartItems", Gson().toJson(cartItems))
        editor.apply()
    }

    fun clearShoppingList() {
        val editor = App.prefs.edit()
        editor.remove("cartItems")
        editor.remove("truckId")
        editor.remove("truckName")
        editor.apply()
    }

    fun getTruckName() : String? {
        return App.prefs.getString("truckName", "")
    }
}