package com.tan.savingdata;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    TextView text_data;
    EditText edit_filename;
    FileOutputStream fos;
    FileInputStream fis;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        edit_filename = (EditText) findViewById(R.id.edit_filename);
        text_data = (TextView) findViewById(R.id.text_data);
        preferences = getSharedPreferences(edit_filename.getText().toString(), Context.MODE_PRIVATE);
    }
    public void callMainActivity(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void loadIntStorage (View view) {
        StringBuffer buffer = new StringBuffer();
        int read = 0;
        try {
            fis = openFileInput(edit_filename.getText().toString());
            while ((read = fis.read()) != -1) {
                buffer.append((char)read);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        text_data.setText(buffer.toString());
    }
    public void loadPreferences(View view) {
        String data = preferences.getString("Data","");
        text_data.setText(data);
    }
    public void readExternalStorage(View view){
        StringBuffer buffer = new StringBuffer();
        File folder = getExternalFilesDir("Temp");
        File file = new File(folder, edit_filename.getText().toString());

        String data = readData(file);
        text_data.setText(data);

    }
    public void readExternalCache(View view) {
        File folder = getExternalCacheDir();
        File file = new File(folder, edit_filename.getText().toString());

        String data = readData(file);
        text_data.setText(data);
    }
    public void readExternalPublic(View view) {
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder, edit_filename.getText().toString());

        String data = readData(file);
        text_data.setText(data);
    }
    public void loadInternalCache(View view) {
        File folder = getCacheDir();
        File file = new File(folder, edit_filename.getText().toString());

        String data = readData(file);
        text_data.setText(data);
    }
    public String readData (File filedir) {
        StringBuffer buffer = new StringBuffer();
        int read = 0;
        try {
            fis = new FileInputStream(filedir);
            while ((read = fis.read()) != -1) {
                buffer.append((char)read);
            }
            fis.close();
            return buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}