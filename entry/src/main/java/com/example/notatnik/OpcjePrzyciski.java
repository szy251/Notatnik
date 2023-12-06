package com.example.notatnik;

import com.example.notatnik.slice.OpcjePrzyciskiSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class OpcjePrzyciski extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(OpcjePrzyciskiSlice.class.getName());
        setSwipeToDismiss(true);
    }
}
