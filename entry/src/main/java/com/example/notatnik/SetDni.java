package com.example.notatnik;

import com.example.notatnik.slice.SetDniSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class SetDni extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(SetDniSlice.class.getName());
    }
}
