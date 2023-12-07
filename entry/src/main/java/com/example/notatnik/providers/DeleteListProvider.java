package com.example.notatnik.providers;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.Data;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.utils.Kolor;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteListProvider extends BaseItemProvider {
    private List<Data> dane;
    private AbilitySlice slice;
    private List<Boolean> usun;
    private RgbColor dochecka;
    ShapeElement bcg1, bcg2;


    public DeleteListProvider(List<Data> dane, AbilitySlice slice) {
        this.dane =  dane;
        this.slice = slice;
        usun = new ArrayList<Boolean>();
        for (int i = 0; i < dane.size(); i++) {
            usun.add(false);
        }
        dochecka = Arrays.stream(new ShapeElement(slice.getContext(), DataHolder.getInstance().getOpcjeData().getPrzycTloId()).getRgbColors()).findFirst().get();
        bcg1 = new ShapeElement(slice.getContext(), DataHolder.getInstance().getOpcjeData().getNormalTytId());
        bcg2 = new ShapeElement(slice.getContext(), DataHolder.getInstance().getOpcjeData().getListTytId());

    }
    public class DeleteHolder{
        DirectionalLayout directionalLayout, directionalLayout2;
        Text text;
        Checkbox checkbox;
        DeleteHolder(Component component){
            directionalLayout =  (DirectionalLayout) component.findComponentById(ResourceTable.Id_tytul_usun);
            directionalLayout2 = (DirectionalLayout) component.findComponentById(ResourceTable.Id_tytul_usun_tlo);
            text =  (Text) component.findComponentById(ResourceTable.Id_nazwa_usun);
            checkbox = (Checkbox)component.findComponentById(ResourceTable.Id_checkbox);
            text.setTextSize((int) (30 * DataHolder.getInstance().getOpcjeData().getTextSize()));
            text.setMultipleLine(true);
            directionalLayout.setMinHeight(90);
            checkbox.setBackground(Kolor.check_background(dochecka));
        }
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
        Data d =  dane.get(i);
        boolean dousun = usun.get(i);
        DeleteHolder holder;
        if(component ==  null)
        {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_tytul_usun,null,false);
            holder = new DeleteHolder(cpt);
            cpt.setTag(holder);
        }
        else {
            cpt = component;
            holder = (DeleteHolder) cpt.getTag();
        }
        holder.checkbox.setCheckedStateChangedListener((cos, state)->{
            usun.set(i,state);
            if(state){
                DataHolder.getInstance().addUsuwane(d);
            }
            else{
                DataHolder.getInstance().removeUsuwane(d);
            }
        });
        if(d.getTyp().equals("Norm")){
            holder.directionalLayout2.setBackground(bcg1);
        }
        else{
            holder.directionalLayout2.setBackground(bcg2);
        }

        holder.checkbox.setChecked(dousun);
        holder.text.setText(d.getNazwa());
        return cpt;
    }
}
