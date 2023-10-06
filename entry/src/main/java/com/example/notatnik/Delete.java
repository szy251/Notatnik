package com.example.notatnik;

import com.example.notatnik.slice.DeleteSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class Delete extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(DeleteSlice.class.getName());
    }
}
