package com.example.notatnik.providers;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;

import java.util.Arrays;
import java.util.List;

public class DniListProvider extends BaseItemProvider {
    private List<String> dane;
    private AbilitySlice slice;
    private boolean[] dzien;
    private RgbColor dochecka;


    public DniListProvider(List<String> dane, AbilitySlice slice, boolean[] dzien) {
        this.dane = dane;
        this.slice = slice;
        this.dzien = new boolean[dzien.length];
        System.arraycopy(dzien, 0, this.dzien, 0, dzien.length);
        dochecka = Arrays.stream(new ShapeElement(slice.getContext(), DataHolder.getInstance().getOpcjeData().getPrzycTloId()).getRgbColors()).findFirst().get();

    }

    public class DniHolder{
        Text text;
        DirectionalLayout directionalLayout;
        Checkbox checkbox;
        DniHolder(Component component){
            text =  (Text) component.findComponentById(ResourceTable.Id_nazwa_usun);
            directionalLayout =  (DirectionalLayout) component.findComponentById(ResourceTable.Id_tytul_usun_tlo);
            checkbox = (Checkbox)component.findComponentById(ResourceTable.Id_checkbox);

            ShapeElement shapeElement1 = new ShapeElement(component.getContext(),ResourceTable.Graphic_tytuly_gray);
            directionalLayout.setBackground(shapeElement1);
        }
    }

    @Override
    public int getCount() {
        return dane == null ? 0 : dane.size();
    }

    @Override
    public Object getItem(int i) {
        if (dane != null && i >= 0 && i < dane.size()) return dane.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public boolean[] dostan(){
        return dzien;
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        final Component cpt;
        String d =  dane.get(i);
        DniHolder holder;
        if(component ==  null)
        {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_tytul_usun,null,false);
            holder = new DniHolder(cpt);
            holder.text.setText(d);
            cpt.setTag(holder);
            holder.checkbox.setCheckedStateChangedListener((cos, state)->{
                dzien[i] = state;
            });
            if(dzien[i]){
                holder.checkbox.setChecked(true);
            }

        }
        else
        {
            cpt = component;
            holder = (DniHolder) cpt.getTag();
        }

        return cpt;
    }
}