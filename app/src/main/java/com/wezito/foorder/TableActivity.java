package com.wezito.foorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TableActivity extends AppCompatActivity {

    private TextView subtitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        LinkedElements();

        subtitleText.setText(Html.fromHtml("<u>Menú</u>"));
    }

    private void LinkedElements(){
        subtitleText = (TextView) findViewById(R.id.Subtitle);
    }
}