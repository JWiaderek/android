package com.example.zaliczenie;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InvertedValuesActivity extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inverted);

        String arg1 = "";
        String arg2 = "";
        StringBuilder sb = new StringBuilder();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            arg1 = extras.getString("arg1");
            arg2 = extras.getString("arg2");
        }

        for(int i=arg1.length()-1; i>=0; i--){
            sb.append(arg1.charAt(i));
        }
        arg1 = sb.toString();
        sb.setLength(0);

        for(int i=arg2.length()-1; i>=0; i--){
            sb.append(arg2.charAt(i));
        }
        arg2 = sb.toString();

        text = findViewById(R.id.textViewInverted);
        text.setText(arg2 + arg1);
    }


}