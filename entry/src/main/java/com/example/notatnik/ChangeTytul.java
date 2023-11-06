package com.example.notatnik;

import com.example.notatnik.slice.ChangeTytulSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ChangeTytul extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ChangeTytulSlice.class.getName());
    }
}
