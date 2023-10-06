package com.example.notatnik;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.utils.Color;

import java.util.Date;
import java.util.List;

public class NazwaListProvider extends BaseItemProvider {
    private List<Data> dane;
    private AbilitySlice slice;

    public NazwaListProvider(List<Data> dane, AbilitySlice slice) {
        this.dane =  dane;
        this.slice = slice;
    }

    @Override
    public int getCount() {
        return dane == null? 0:dane.size();
    }

    @Override
    public Object getItem(int i) {
        if(dane !=null && i >=0 && i < dane.size()) return dane.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        final Component cpt;
        if(component ==  null)
        {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_tytul,null,false);
        }
        else cpt = component;

        Data d =  dane.get(i);

        Text text =  (Text) cpt.findComponentById(ResourceTable.Id_nazwa);
        DirectionalLayout directionalLayout =  (DirectionalLayout) cpt.findComponentById(ResourceTable.Id_tytul);

        int a = d.getNazwa().length();
        String jak = d.getNazwa();
        text.setMultipleLine(true);
        text.setText(jak);
        directionalLayout.setMinHeight(100);
        text.setMinHeight(80);
        if (i == 0){
            RgbColor al = new RgbColor(255,255,255);
            text.setTextColor(new Color(Color.getIntColor("#FFFFFFFF")));
        }

        directionalLayout.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.notatnik")
                        .withAbilityName("com.example.notatnik.Tresc")
                        .build();
                intent.setOperation(operation);
                intent.setParam("title",jak);
                intent.setParam("tresc",d.getTresc());
                intent.setParam("Id_dane",d.getDataId());
                Date j = new Date();
                intent.setParam("data",j);
                slice.startAbility(intent);
                slice.terminateAbility();
            }
        });

        return cpt;
    }
}
