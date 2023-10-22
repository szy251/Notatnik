package com.example.notatnik.providers;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.Kafelek;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;

import java.util.List;

public class KafelekListProvider extends BaseItemProvider{

    private List<Kafelek> dane;
    private AbilitySlice abilitySlice;
    public KafelekListProvider(List<Kafelek> dane, AbilitySlice abilitySlice) {
        this.dane =  dane;
        this.abilitySlice = abilitySlice;
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
            cpt = LayoutScatter.getInstance(abilitySlice).parse(ResourceTable.Layout_kafelek,null,false);
        }
        else cpt = component;

        Kafelek d =  dane.get(i);

        DirectionalLayout directionalLayout = (DirectionalLayout) cpt.findComponentById(ResourceTable.Id_kafelek);
        Text textWiekszy = (Text) cpt.findComponentById(ResourceTable.Id_wieksze);
        Text textMniejszy = (Text) cpt.findComponentById(ResourceTable.Id_mniejsze);
        textWiekszy.setText(d.getWiekszy());
        textMniejszy.setText(d.getMniejszy());
        directionalLayout.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.notatnik")
                        .withAbilityName(d.getSlice())
                        .build();
                intent.setOperation(operation);
                abilitySlice.startAbility(intent);
            }
        });
        return cpt;
    }
}