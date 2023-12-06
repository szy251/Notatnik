package com.example.notatnik.providers;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.*;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;

public class NazwaListProvider extends BaseItemProvider {
    private List<Data> dane;
    private AbilitySlice slice;

    public NazwaListProvider(List<Data> dane, AbilitySlice slice) {
        this.dane =  dane;
        this.slice = slice;
    }

    public class NazwaHolder{
        Text text, text1;
        DirectionalLayout directionalLayout, directionalLayout2;
        NazwaHolder(Component component){
            text =  (Text) component.findComponentById(ResourceTable.Id_nazwa);
            text1 = (Text) component.findComponentById(ResourceTable.Id_alarm_info);
            directionalLayout =  (DirectionalLayout) component.findComponentById(ResourceTable.Id_tytul);
            directionalLayout2 =  (DirectionalLayout) component.findComponentById(ResourceTable.Id_tytul_tlo);
            directionalLayout.setMinHeight(100);
            text.setTextSize((int) (30*DataHolder.getInstance().getOpcjeData().getTextSize()));
            text.setMultipleLine(true);
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
        NazwaHolder holder;
        if(component ==  null)
        {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_tytul,null,false);
            holder = new NazwaHolder(cpt);
            cpt.setTag(holder);
        }
        else {
            cpt = component;
            holder = (NazwaHolder) cpt.getTag();
        }
        ShapeElement shapeElement;
        if(d.getTyp().equals("Norm")){
            shapeElement = new ShapeElement(cpt.getContext(), DataHolder.getInstance().getOpcjeData().getNormalTytId());
        }
        else{
            shapeElement = new ShapeElement(cpt.getContext(), DataHolder.getInstance().getOpcjeData().getListTytId());
        }
        holder.directionalLayout2.setBackground(shapeElement);
        if(d.getAlarm()){
            holder.text1.setVisibility(Component.VISIBLE);
        }
        else{
            holder.text1.setVisibility(Component.HIDE);
        }
        holder.directionalLayout.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                SmallDataHolder.getInstance().setData(d);
                DatabaseHelper databaseHelper = new DatabaseHelper(cpt.getContext());
                OrmContext ormContext = databaseHelper.getOrmContext("data","Notes.db", Dane.class);
                if(d.getTyp().equals("Norm")){
                    OrmPredicates ormPredicates = ormContext.where(NormalNot.class).equalTo("dataParentId",d.getDataId());
                    NormalNot normalNot = (NormalNot) ormContext.query(ormPredicates).get(0);
                    SmallDataHolder.getInstance().setNormalNot(normalNot);
                    Intent intent = new Intent();
                    Operation operation = new Intent.OperationBuilder()
                            .withDeviceId("")
                            .withBundleName("com.example.notatnik")
                            .withAbilityName("com.example.notatnik.Tresc")
                            .build();
                    intent.setOperation(operation);
                    slice.startAbility(intent);
                }
                else if(d.getTyp().equals("List")){
                    OrmPredicates ormPredicates = ormContext.where(ListNot.class).equalTo("dataParentId",d.getDataId());
                    List<ListNot> listNots = ormContext.query(ormPredicates);
                    SmallDataHolder.getInstance().setListNots(listNots);
                    Intent intent = new Intent();
                    Operation operation = new Intent.OperationBuilder()
                            .withDeviceId("")
                            .withBundleName("com.example.notatnik")
                            .withAbilityName("com.example.notatnik.PrzegNotka")
                            .build();
                    intent.setOperation(operation);
                    slice.startAbility(intent);
                }
            }
        });
        holder.text.setText(d.getNazwa());
        return cpt;
    }
}
