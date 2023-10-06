package com.example.notatnik;

import com.example.notatnik.slice.Add_tytulSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class Add_tytul extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(Add_tytulSlice.class.getName());
    }
}
