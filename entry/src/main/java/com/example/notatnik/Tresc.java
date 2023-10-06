package com.example.notatnik;

import com.example.notatnik.slice.TrescSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class Tresc extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(TrescSlice.class.getName());
    }
}
