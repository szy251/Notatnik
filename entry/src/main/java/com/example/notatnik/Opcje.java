package com.example.notatnik;

import com.example.notatnik.slice.OpcjeSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class Opcje extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(OpcjeSlice.class.getName());
    }
}
