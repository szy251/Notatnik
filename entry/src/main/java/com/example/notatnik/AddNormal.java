package com.example.notatnik;

import com.example.notatnik.slice.AddNormalSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AddNormal extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AddNormalSlice.class.getName());
        setSwipeToDismiss(true);
    }
}
