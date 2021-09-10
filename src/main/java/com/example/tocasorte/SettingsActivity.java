package com.example.tocasorte;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsActivity extends Activity {
    private static final String USER_NAME = "username.txt";
    private static final String SETTINGS_FILE = "settings.txt";
    EditText et_username, et_userSettings;
    Button b_saveUserName, b_saveSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        et_username=findViewById(R.id.et_username);
        et_userSettings=findViewById(R.id.et_userSettings);
        b_saveUserName=findViewById(R.id.b_saveUserName);
        b_saveSettings=findViewById(R.id.b_saveSettings);

        b_saveUserName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveUserName();
            }
        });

        b_saveSettings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveSettings();
            }
        });
    }

    public void saveSettings()
    {
        String userSettings = et_userSettings.getText().toString();
        FileOutputStream fos = null;

        try{
            fos = openFileOutput(SETTINGS_FILE, MODE_PRIVATE);
            fos.write(userSettings.getBytes());

            //Toast.makeText(this, "Saved settings to " + getFilesDir() + "/" + SETTINGS_FILE, Toast.LENGTH_LONG).show();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(fos != null){
                try{
                    fos.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveUserName()
    {
        String userName = et_username.getText().toString();
        FileOutputStream fos = null;

        try{
            fos = openFileOutput(USER_NAME, MODE_PRIVATE);
            fos.write(userName.getBytes());

            //Toast.makeText(this, "Saved username to " + getFilesDir() + "/" + USER_NAME, Toast.LENGTH_LONG).show();

        } catch (IOException e){
            e.printStackTrace();
        } finally{
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
