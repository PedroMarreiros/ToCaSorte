package com.example.tocasorte;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TotobolaActivity extends Activity {
    Button b_insertTotobola, b_viewTotobola;
    private static String SETTINGS_FILE = "settings.txt";
    int userSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totobola);

        userSettings = getUserSettings();

        b_insertTotobola = findViewById(R.id.b_insertTotobola);

        b_insertTotobola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInsert();
            }
        });

        b_viewTotobola = findViewById(R.id.b_viewTotobola);

        b_viewTotobola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewBulletin();

            }
        });


    }

    @Override
    protected void onDestroy() {
        File totobolaDir = new File(getFilesDir() + "/" + "totobola_folder.txt");

        super.onDestroy();

        applyUserSettings(totobolaDir);


    }

    public void openInsert() {
        Intent intent = new Intent(this, TotoBolaInsert.class);
        startActivity(intent);
    }

    public void openViewBulletin() {
        Intent intent = new Intent(this, TotoBolaView.class);
        startActivity(intent);
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
                    Toast.makeText(this, "Files Were Deleted", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Files were not deleted", Toast.LENGTH_SHORT).show();
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
