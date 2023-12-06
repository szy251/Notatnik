package com.example.notatnik.providers;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
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

    public class KafelekHolder{
        DirectionalLayout directionalLayout;
        public Text textWiekszy;
        Text textMniejszy;
        KafelekHolder(Component component){
             directionalLayout = (DirectionalLayout) component.findComponentById(ResourceTable.Id_kafelek);
             textWiekszy = (Text) component.findComponentById(ResourceTable.Id_wieksze);
             textMniejszy = (Text) component.findComponentById(ResourceTable.Id_mniejsze);
             textWiekszy.setTruncationMode(Text.TruncationMode.AUTO_SCROLLING);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public void notifyDataChanged() {
        super.notifyDataChanged();
        DataHolder.getInstance().setStraznik(true);
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        final Component cpt;
        Kafelek d =  dane.get(i);
        KafelekHolder holder;
        if(component ==  null)
        {
            cpt = LayoutScatter.getInstance(abilitySlice).parse(ResourceTable.Layout_kafelek,null,false);
            holder = new KafelekHolder(cpt);
            holder.textWiekszy.setText(d.getWiekszy());
            holder.directionalLayout.setClickedListener(new Component.ClickedListener() {
                @Override
                public void onClick(Component component) {
                    Intent intent = new Intent();
                    Operation operation = new Intent.OperationBuilder()
                            .withDeviceId("")
                            .withBundleName("com.example.notatnik")
                            .withAbilityName(d.getSlice())
                            .build();
                    intent.setOperation(operation);
                    DataHolder.getInstance().setKafelekId(i);
                    abilitySlice.startAbility(intent);
                }
            });
            cpt.setTag(holder);
        }
        else {
            cpt = component;
            holder =(KafelekHolder) cpt.getTag();
        }

        holder.textMniejszy.setText(d.getMniejszy());

        return cpt;
    }
}