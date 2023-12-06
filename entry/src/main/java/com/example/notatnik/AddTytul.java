package com.example.notatnik;

import com.example.notatnik.slice.AddTytulSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AddTytul extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AddTytulSlice.class.getName());
        setSwipeToDismiss(true);
    }
}
