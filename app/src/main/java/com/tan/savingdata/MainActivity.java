package com.tan.savingdata;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText edit_data, edit_filename;
    SharedPreferences preferences;
    FileOutputStream fos = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_data = (EditText) findViewById(R.id.edit_data);
        edit_filename = (EditText) findViewById(R.id.edit_filename);
        preferences = getSharedPreferences(edit_filename.getText().toString(), Context.MODE_PRIVATE);
    }
    public void savePreference(View view) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("Filename", edit_filename.getText().toString());
        editor.putString("Data",edit_data.getText().toString());
        editor.commit();

        Toast.makeText(this,"Saved in Preferences",Toast.LENGTH_SHORT).show();
    }
    public void saveIntStorage(View view) {
        String message =  edit_data.getText().toString();
        try {
            fos = openFileOutput(edit_filename.getText().toString(), Context.MODE_PRIVATE);
            fos.write(message.getBytes());
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(this,"Saved in Internal Storage!", Toast.LENGTH_SHORT).show();
    }
    public void saveInternalCache(View view) {
        File folder = getCacheDir();
        File file = new File(folder, edit_filename.getText().toString());
        writeData(edit_data.getText().toString(), file);
        Toast.makeText(this, "Successfully written to Internal Cache!", Toast.LENGTH_SHORT).show();
    }
    public void saveExternalCache(View view) {
        File folder = getExternalCacheDir();
        File file = new File(folder, edit_filename.getText().toString());
        writeData(edit_data.getText().toString(), file);
        Toast.makeText(this, "Successfully written to External Cache!", Toast.LENGTH_SHORT).show();
    }
    public void saveExternalStorage(View view) {
        File folder = getExternalFilesDir("Temp");
        File file = new File(folder, edit_filename.getText().toString());
        writeData(edit_data.getText().toString(), file);
        Toast.makeText(this, "Successfully written to External Storage!", Toast.LENGTH_SHORT).show();
    }
    public void saveExternalPublic (View view) {
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder, edit_filename.getText().toString());
        writeData(edit_data.getText().toString(), file);
        Toast.makeText(this, "Successfully written to External Public!", Toast.LENGTH_SHORT).show();
    }
    public void writeData(String message,File filename){
        try{
            fos = new FileOutputStream(filename);
            fos.write(message.getBytes());
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void callSecondActivity(View view) {
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
}