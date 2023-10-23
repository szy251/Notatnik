package com.example.notatnik;

import com.example.notatnik.slice.AddListyTrescSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AddListyTresc extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AddListyTrescSlice.class.getName());
    }
}
