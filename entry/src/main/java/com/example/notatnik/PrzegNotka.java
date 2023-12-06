package com.example.notatnik;

import com.example.notatnik.slice.PrzegNotkaSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class PrzegNotka extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(PrzegNotkaSlice.class.getName());
        setSwipeToDismiss(true);
    }
}
