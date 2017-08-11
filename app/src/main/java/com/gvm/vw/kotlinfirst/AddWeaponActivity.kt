package com.gvm.vw.kotlinfirst

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_add_weapon.*

class AddWeaponActivity : AppCompatActivity() {

    companion object {

        val INTENT_NEW_WEAPON = "newWeapon"
        private val INTENT_LAST_WEAPON_ID = "lastWeaponId"

        fun newIntent(context: Context, lastId: Int) : Intent {
            val intent = Intent(context, AddWeaponActivity::class.java)
            intent.putExtra(INTENT_LAST_WEAPON_ID, lastId)
            return intent
        }
    }

    var lastWeaponId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_weapon)
        lastWeaponId = intent.getIntExtra(INTENT_LAST_WEAPON_ID, -1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_weapon, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuSaveWeapon -> {
                saveIntent()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun saveIntent() {
        val newWeapon = Weapon(
                id = ++lastWeaponId,
                name = inputLayoutWeaponName.editText?.text.toString(),
                damage = inputLayoutWeaponDamage.editText?.text.toString().toInt(),
                description = inputLayoutWeaponDescription.editText?.text.toString()
        )

        val intent = Intent().putExtra(INTENT_NEW_WEAPON, newWeapon)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
