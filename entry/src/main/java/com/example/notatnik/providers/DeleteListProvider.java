package com.example.notatnik.providers;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.Data;
import com.example.notatnik.data.DataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;

import java.util.ArrayList;
import java.util.List;

public class DeleteListProvider extends BaseItemProvider {
    private List<Data> dane;
    private AbilitySlice slice;
    private List<Boolean> usun;


    public DeleteListProvider(List<Data> dane, AbilitySlice slice) {
        this.dane =  dane;
        this.slice = slice;
        usun = new ArrayList<Boolean>();
        for (int i = 0; i < dane.size(); i++) {
            usun.add(false);
        }

    }

    public List<Boolean> getUsun() {
        return usun;
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
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_tytul_usun,null,false);
        }
        else cpt = component;
        Data d =  dane.get(i);

        Text text =  (Text) cpt.findComponentById(ResourceTable.Id_nazwa_usun);
        DirectionalLayout directionalLayout =  (DirectionalLayout) cpt.findComponentById(ResourceTable.Id_tytul_usun);
        Checkbox checkbox = (Checkbox)cpt.findComponentById(ResourceTable.Id_checkbox);
        int a = d.getNazwa().length();
        String jak = d.getNazwa();
        text.setMultipleLine(true);
        text.setText(jak);
        directionalLayout.setMinHeight(100);
        text.setMinHeight(80);
        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setStroke(1,new RgbColor(255,255,255));
        shapeElement.setRgbColor(new RgbColor(0,0,0));
        shapeElement.setCornerRadius(5);
        checkbox.setBackground(shapeElement);


        checkbox.setCheckedStateChangedListener((cos, state)->{
            usun.set(i,state);
            if(state){
                shapeElement.setRgbColor(new RgbColor(0,148,125));
                shapeElement.setStroke(0, new RgbColor(255,255,255));
                DataHolder.getInstance().addUsuwane(d);
            }
            else{
                shapeElement.setRgbColor(new RgbColor(0,0,0));
                shapeElement.setStroke(1, new RgbColor(255,255,255));
                DataHolder.getInstance().removeUsuwane(d);
            }
        });



        return cpt;
    }
}
