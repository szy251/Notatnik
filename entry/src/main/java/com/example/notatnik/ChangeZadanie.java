package com.example.notatnik;

import com.example.notatnik.slice.ChangeZadanieSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ChangeZadanie extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ChangeZadanieSlice.class.getName());
    }
}