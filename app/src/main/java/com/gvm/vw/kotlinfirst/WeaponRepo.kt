package com.gvm.vw.kotlinfirst

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

data class Weapon(val name: String, val id: Int, val damage: Int)

operator fun JSONArray.iterator(): Iterator<JSONObject>
        = (0 until length()).asSequence().map { get(it) as JSONObject }.iterator()

fun generateWeapon (context: Context) : List<Weapon>{
    var weapons = mutableListOf<Weapon>()
    var json: String

    val inputStream = context.assets.open("weapon.json")
    val size = inputStream.available()
    val buffer = kotlin.ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    json = String(buffer)

    var obj = JSONObject(json)
    var objArr = obj.getJSONArray("weapons")
    (0..(objArr.length() - 1))
            .map { objArr.getJSONObject(it) }
            .mapTo(weapons) {
                Weapon(
                        id = it.getInt("id"),
                        name = it.getString("name"),
                        damage = it.getInt("damage"))
            }


    return weapons
}