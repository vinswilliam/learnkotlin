package com.gvm.vw.kotlinfirst

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import org.json.JSONArray
import org.json.JSONObject

data class Weapon(val name: String, val id: Int, val damage: Int, val description: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(id)
        parcel.writeInt(damage)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weapon> {
        override fun createFromParcel(parcel: Parcel): Weapon {
            return Weapon(parcel)
        }

        override fun newArray(size: Int): Array<Weapon?> {
            return arrayOfNulls(size)
        }
    }

}

operator fun JSONArray.iterator(): Iterator<JSONObject>
        = (0 until length()).asSequence().map { get(it) as JSONObject }.iterator()

fun generateWeapon (context: Context) : MutableList<Weapon>{
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
                        damage = it.getInt("damage"),
                        description = it.getString("description"))
            }

    return weapons
}