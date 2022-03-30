package com.example.tut;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class list_Adapter extends ArrayAdapter<Medicion>{

    public list_Adapter(Context context, ArrayList<Medicion> medicionArrayList){
        super(context, R.layout.list_item, medicionArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        Medicion med = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        //ImageView imageView = convertView.findViewById(R.id.)
        TextView txPh = convertView.findViewById(R.id.txph);
        TextView txORP = convertView.findViewById(R.id.txORP);
        TextView txTDS = convertView.findViewById(R.id.txTDS);
        TextView txTurb = convertView.findViewById(R.id.txTurb);
        TextView txUser = convertView.findViewById(R.id.txUsername);
        TextView txPotable = convertView.findViewById(R.id.txPotable);
        TextView estacion = convertView.findViewById(R.id.estacion);

        txUser.setText(med.username);
        estacion.setText(Integer.toString(med.numero_estacion));
        txPh.setText(Double.toString(med.ph));
        txTDS.setText(Double.toString(med.TDS));
        txORP.setText(Double.toString(med.ORP));
        txTurb.setText(Double.toString(med.turbidez));
        txPotable.setText(med.potable);

        if(txPotable.getText().equals("POTABLE")){
            txPotable.setTextColor(Color.rgb(144,238,144));
            txPotable.setBackgroundColor(Color.rgb(0,125,0));
        }
        else{
            txPotable.setTextColor(Color.rgb(100,0,0));
            txPotable.setBackgroundColor(Color.rgb(200,0,0));
        }



        return convertView;
    }
}
