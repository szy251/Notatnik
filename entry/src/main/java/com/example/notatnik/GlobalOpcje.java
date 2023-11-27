package com.example.notatnik;

import com.example.notatnik.slice.GlobalOpcjeSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class GlobalOpcje extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(GlobalOpcjeSlice.class.getName());
    }
}
