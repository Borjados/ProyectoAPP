package com.example.tut;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;

import com.example.tut.databinding.ActivityListBinding;
import com.example.tut.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    ActivityListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] username = {"b", "b", "b"};
        String[] potable = {"POTABLE", "POTABLE", "NO POTABLE"};
        int[] estacion = {2, 3, 2};
        Double[] ph = {7.2, 8.6, 12.0};
        Double[] TDS = {250.2, 245.8, 54.0};
        Double[] ORP = {785.3, 653.5, 4.0};
        Double[] turb = {3.0, 2.6, 5.1};

        ArrayList<Medicion> medicionArrayList = new ArrayList<>();

        for(int i = 0; i<username.length;i++){
            Medicion medicion = new Medicion(ph[i], TDS[i], ORP[i], turb[i], estacion[i], username[i], potable[i]);
            medicionArrayList.add(medicion);
        }

        list_Adapter listAdapter = new list_Adapter(List.this, medicionArrayList);

        binding.listview.setAdapter(listAdapter);

    }
}