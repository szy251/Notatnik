package com.example.notatnik;

import com.example.notatnik.slice.ChangeListyTrescSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ChangeListyTresc extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ChangeListyTrescSlice.class.getName());
    }
}
