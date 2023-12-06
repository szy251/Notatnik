package com.example.notatnik;

import com.example.notatnik.slice.AddTrescSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AddTresc extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AddTrescSlice.class.getName());
        setSwipeToDismiss(true);
    }
}
