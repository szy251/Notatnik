package com.example.notatnik;

import com.example.notatnik.slice.ChangeTrescSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ChangeTresc extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ChangeTrescSlice.class.getName());
        setSwipeToDismiss(true);
    }
}
