package com.example.tocasorte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.*;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class HomeActivity extends Activity {
    private Button EuromilionButton, TotobolaButton, TotolotoButton, SettingsButton, MiniGameButton;
    TextView tv_displayUsername;
    private static String FILE_NAME="username.txt";
    private static String SETTINGS_FILE="settings.txt";
    int userSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userSettings=getUserSettings();

        tv_displayUsername = findViewById(R.id.tv_displayUsername);
        displayUsername();

        EuromilionButton = findViewById(R.id.Euromilionbutton);
        EuromilionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openEuromilionActivity();
            }
        });

        TotobolaButton = findViewById(R.id.Totobolabutton);
        TotobolaButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openTotobolaActivity();
            }
        });

        TotolotoButton = findViewById(R.id.Totolotobutton);
        TotolotoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openTotolotoActivity();
            }
        });

        SettingsButton = findViewById(R.id.Settingsbutton);
        SettingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSettingsActivity();
            }
        });

        MiniGameButton = findViewById(R.id.MiniGameButton);
        MiniGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openMiniGameActivity();
            }
        });

    }

    @Override
    protected void onDestroy()
    {
        File euromillionDir = new File (getFilesDir() + "/" + "euromillion_folder.txt");
        File totolotoDir = new File(getFilesDir() + "/" + "totoloto_folder.txt");
        File totobolaDir = new File(getFilesDir() + "/" + "totobola_folder.txt");

        super.onDestroy();

        applyUserSettings(euromillionDir);
        applyUserSettings(totolotoDir);
        applyUserSettings(totobolaDir);
    }

    public void openEuromilionActivity(){
        Intent intent = new Intent(this, EuromilionActivity.class);
        startActivity(intent);
    }

    public void openTotobolaActivity(){
        Intent intent = new Intent(this, TotobolaActivity.class);
        startActivity(intent);
    }

    public void openTotolotoActivity(){
        Intent intent = new Intent(this, TotolotoActivity.class);
        startActivity(intent);
    }

    public void openSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    public void openMiniGameActivity(){
        Intent intent = new Intent(this, MiniGame.class);
        startActivity(intent);
    }

    public void displayUsername(){
        FileInputStream fis = null;

        try{
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String username;

            while((username = br.readLine()) != null)
            {
                sb.append(username).append("\n");
            }
            tv_displayUsername.setText("Bem-Vindo, " + sb.toString());

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (fis != null){
                try{
                    fis.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }

        }
    }

    public void applyUserSettings(File f) {
        File[] filesArray = f.listFiles();

        if(filesArray.length == userSettings)
        {
            return;
        }

        Arrays.sort(filesArray, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Long.valueOf(o2.lastModified()).compareTo(o1.lastModified());
            }
        });


        for (int i = 0; i < filesArray.length; i++) {
            if (i >= userSettings) {

                if (filesArray[i].delete()) {
                    //Toast.makeText(this, "Files Were Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public int getUserSettings() {
        FileInputStream fis = null;
        int result=1;

        try {
            fis = openFileInput(SETTINGS_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            result = Integer.parseInt(br.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
