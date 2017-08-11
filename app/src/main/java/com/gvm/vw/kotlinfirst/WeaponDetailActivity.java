package com.gvm.vw.kotlinfirst;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class WeaponDetailActivity extends AppCompatActivity {

    public static final String EX_WEAPON = "exWeapon";

    private TextView tvWeaponId, tvWeaponName, tvWeaponDamage, tvWeaponDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_detail);
        tvWeaponId = (TextView) findViewById(R.id.tvWeaponId);
        tvWeaponName = (TextView) findViewById(R.id.tvWeaponName);
        tvWeaponDamage = (TextView) findViewById(R.id.tvWeaponDamage);
        tvWeaponDescription = (TextView) findViewById(R.id.tvWeaponDescription);

        Weapon weapon = getIntent().getParcelableExtra(EX_WEAPON);

        if (weapon != null) {
            tvWeaponId.append(String.valueOf(weapon.getId()));
            tvWeaponName.append(weapon.getName());
            tvWeaponDamage.append(String.valueOf(weapon.getDamage()));
            tvWeaponDescription.append(weapon.getDescription());
        }

    }
}
