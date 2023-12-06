package com.example.notatnik;

import com.example.notatnik.slice.OpcjeTextSizeSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class OpcjeTextSize extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(OpcjeTextSizeSlice.class.getName());
        setSwipeToDismiss(true);
    }
}
