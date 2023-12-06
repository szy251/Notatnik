package com.example.notatnik;

import com.example.notatnik.slice.NormalOpcjeSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class NormalOpcje extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(NormalOpcjeSlice.class.getName());
        setSwipeToDismiss(true);
    }
}
