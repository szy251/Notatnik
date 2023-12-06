package com.example.notatnik;

import com.example.notatnik.slice.AddZadanieSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AddZadanie extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AddZadanieSlice.class.getName());
        setSwipeToDismiss(true);
    }
}
