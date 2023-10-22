package com.example.notatnik;

import com.example.notatnik.slice.AddListySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AddListy extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AddListySlice.class.getName());
    }
}
