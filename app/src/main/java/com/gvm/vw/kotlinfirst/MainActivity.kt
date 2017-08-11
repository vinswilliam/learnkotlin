package com.gvm.vw.kotlinfirst

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.*
import android.view.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_weapon.view.*

class MainActivity : AppCompatActivity(), IWeaponAdapterListener, ICallbackListener {

    private val REQUEST_CODE_ADD_WEAPON = 1

    var weapons : MutableList<Weapon> = mutableListOf()
    var weaponAdapter = WeaponAdapter(mutableListOf(), this)
    val weaponTouchHelper = ItemTouchHelper(WeaponItemTouchHelperCallback(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weapons = generateWeapon(this)


        weaponAdapter.weapons = weapons
        rvWeapon.layoutManager = LinearLayoutManager(this)
        rvWeapon.addItemDecoration(WeaponDecorator())
        weaponTouchHelper.attachToRecyclerView(rvWeapon)

        rvWeapon.adapter = weaponAdapter
    }

    override fun onClickWeapon(weapon: Weapon) {
        val intent = Intent(this, WeaponDetailActivity::class.java)
        intent.putExtra(WeaponDetailActivity.EX_WEAPON, weapon)
        startActivity(intent)
    }

    override fun onSwiped(pos: Int?) {
        if (pos != null) {
            weaponAdapter.remove(pos)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_weapons, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.addWeapon -> {
                val intent = AddWeaponActivity.newIntent(this, weapons.last().id)
                startActivityForResult(intent, REQUEST_CODE_ADD_WEAPON)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_WEAPON && resultCode == Activity.RESULT_OK) {
            val newWeapon = data?.getParcelableExtra<Weapon>(AddWeaponActivity.INTENT_NEW_WEAPON)
            if (newWeapon != null) {
                weaponAdapter.insert(newWeapon)
            }
        }
    }

    class WeaponAdapter(var weapons: MutableList<Weapon>, val listener: IWeaponAdapterListener) :
            RecyclerView.Adapter<WeaponAdapter.weaponViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): weaponViewHolder {
            val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.row_weapon, parent, false)
            return weaponViewHolder(view)
        }

        override fun getItemCount(): Int  = weapons.size

        override fun onBindViewHolder(holder: weaponViewHolder?, position: Int) {
            holder?.bind(weapons[position], listener)
        }

        class weaponViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

            fun bind(weapon: Weapon, listener: IWeaponAdapterListener) {
                itemView.tvWeaponId.text = weapon.id.toString()
                itemView.tvWeaponName.text = weapon.name
                itemView.tvWeaponDamage.text = (itemView.context.getString(R.string.damage)
                        + weapon.damage.toString())
                itemView.setOnClickListener {
                    listener.onClickWeapon(weapon)
                }
            }
        }

        fun insert(newWeapon: Weapon) {
            weapons.add(newWeapon)
            notifyItemInserted(itemCount)
        }

        fun remove(position: Int) {
            weapons.removeAt(position)
            notifyItemRemoved(position)
            notifyItemChanged(position, itemCount)
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

    class WeaponItemTouchHelperCallback(val listener: ICallbackListener) : ItemTouchHelper.Callback() {

        override fun getMovementFlags(recyclerView: RecyclerView?,
                                      viewHolder: RecyclerView.ViewHolder?): Int {
            return makeFlag(ACTION_STATE_IDLE, RIGHT) or makeFlag(ACTION_STATE_SWIPE, LEFT or RIGHT)
        }

        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?,
                            target: RecyclerView.ViewHolder?): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
            listener.onSwiped(viewHolder?.layoutPosition)
        }

    }
}

interface ICallbackListener {
    fun onSwiped(pos: Int?)
}

interface IWeaponAdapterListener {
    fun onClickWeapon(weapon: Weapon)
}