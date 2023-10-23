package com.example.notatnik.providers;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.*;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
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


        String jak = d.getNazwa();
        text.setMultipleLine(true);
        text.setText(jak);
        directionalLayout.setMinHeight(100);
        text.setMinHeight(80);

        directionalLayout.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                SmallDataHolder.getInstance().setData(d);
                DatabaseHelper databaseHelper = new DatabaseHelper(cpt.getContext());
                OrmContext ormContext = databaseHelper.getOrmContext("data","Data.db", Dane.class);
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
               // slice.terminateAbility();
                /*dane.remove(i);
                notifyDataChanged();*/
            }
        });

        return cpt;
    }
}
