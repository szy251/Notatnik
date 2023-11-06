package com.example.notatnik;

import com.example.notatnik.slice.ChangeDniSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ChangeDni extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ChangeDniSlice.class.getName());
    }
}
