package com.gvm.vw.kotlinfirst

import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_weapon.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weapons = generateWeapon(this)

        val weaponAdapter = WeaponAdapter(weapons)
        rvWeapon.layoutManager = LinearLayoutManager(this)
        rvWeapon.addItemDecoration(WeaponDecorator())
        rvWeapon.adapter = weaponAdapter
    }

    class WeaponAdapter(val weapons: List<Weapon>) :
            RecyclerView.Adapter<WeaponAdapter.weaponViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): weaponViewHolder {
            val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.row_weapon, parent, false)
            return weaponViewHolder(view)
        }

        override fun getItemCount(): Int  = weapons.size

        override fun onBindViewHolder(holder: weaponViewHolder?, position: Int) {
            holder?.bind(weapons[position])
        }

        class weaponViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

            fun bind(weapon: Weapon) {
                itemView.tvWeaponId.text = weapon.id.toString()
                itemView.tvWeaponName.text = weapon.name
                itemView.tvWeaponDamage.append(weapon.damage.toString())
            }
        }
    }

    class WeaponDecorator : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?,
                                    state: RecyclerView.State?) {
            super.getItemOffsets(outRect, view, parent, state)
            val pos = parent?.getChildLayoutPosition(view)
            if (pos == 0) {
                outRect?.set(8, 8, 8, 8)
            } else {
                outRect?.set(8, 0, 8, 8)
            }
        }
    }
}
