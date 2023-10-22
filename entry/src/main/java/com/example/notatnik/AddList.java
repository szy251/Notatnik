package com.example.notatnik;

import com.example.notatnik.slice.AddListSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AddList extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AddListSlice.class.getName());
    }
}
