package com.example.zaliczenie;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ListView list;
    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    EditText arg1, arg2;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = getSharedPreferences("Prefs",0);
        String data = sharedPref.getString("data","");
        String[] itemsTab = data.split(",");
        Collections.reverse(Arrays.asList(itemsTab));

        list = findViewById(R.id.list);
        items = new ArrayList<String>(Arrays.asList(itemsTab));
        arg1 = findViewById(R.id.arg1);
        arg2 = findViewById(R.id.arg2);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemName = items.get(i);
                Toast.makeText(MainActivity.this, itemName, Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
        list.setAdapter(adapter);
    }

    public void addEntry(View view) {
        String entry = arg1.getText() + "\n" + arg2.getText();
        items.add(entry);
        adapter.notifyDataSetChanged();

        StringBuilder sb = new StringBuilder();
        for(String s : items){
            sb.append(s);
            sb.append(",");
        }

        sharedPref = this.getSharedPreferences("Prefs",0);
        editor = sharedPref.edit();
        editor.putString("data",sb.toString());
        editor.apply();
    }

    public void clearList(View view){
        items.clear();
        adapter.notifyDataSetChanged();

        sharedPref = this.getSharedPreferences("Prefs",0);
        editor = sharedPref.edit();
        editor.putString("data","");
        editor.apply();
    }

    public void invertValues(View view){
        Intent intent = new Intent(this, InvertedValuesActivity.class);
        intent.putExtra("arg1", arg1.getText().toString());
        intent.putExtra("arg2", arg2.getText().toString());
        startActivity(intent);
    }

    public void saveData(View view){
        try{
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"ProjectSave");
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fos);

            for(int i=0; i< items.size(); i++){
                String temp = items.get(i);
                temp = temp.replaceAll("\n",":");
                writer.write(temp+"\n");
            }
            writer.close();
            fos.close();
            Toast.makeText(this, "Saved to DOWNLOADS folder", Toast.LENGTH_SHORT).show();
        }catch(IOException exception){
            exception.printStackTrace();
        }

    }

}