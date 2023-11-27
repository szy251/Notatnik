package com.example.notatnik;

import com.example.notatnik.slice.OpcjeCheckedListSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class OpcjeCheckedList extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(OpcjeCheckedListSlice.class.getName());
    }
}
