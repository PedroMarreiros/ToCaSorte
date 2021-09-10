package com.example.tocasorte;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class TotolotoActivity extends Activity {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:d:HH:mm:ss");
    private final String TOTOLOTO_FILENAME = "[CHAVE GERADA]" + format.format(calendar.getTime()) + ".txt";

    TextView tv_totolotoNumbers, tv_totolotoLucky;
    Button b_genTotolotoKey, b_insertTotoloto, b_viewTotoloto;
    private static String SETTINGS_FILE = "settings.txt";
    int userSettings;
    OutputStream fOut = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String DIRECTORY_NAME = "totoloto_folder.txt";
        final String fullPath = getFilesDir() + "/" + DIRECTORY_NAME;

        fileExists(this, DIRECTORY_NAME);

        userSettings=getUserSettings();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totoloto);

        tv_totolotoNumbers = findViewById(R.id.tv_totolotoNumbers);
        tv_totolotoLucky =findViewById(R.id.tv_totolotoLucky);

        b_genTotolotoKey=findViewById(R.id.b_genTotolotoKey);
        b_insertTotoloto=findViewById(R.id.b_insertTotoloto);
        b_viewTotoloto = findViewById(R.id.b_viewTotoloto);

        b_genTotolotoKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                generateTotolotoNumbers(fullPath);
                generateTotolotoLucky();
            }
        });

        b_insertTotoloto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTotolotoInsert();
            }
        });

        b_viewTotoloto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTotolotoView();
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        File totolotoDir = new File(getFilesDir() + "/" + "totoloto_folder.txt");
        super.onDestroy();

        applyUserSettings(totolotoDir);
    }

    public void openTotolotoInsert()
    {
        Intent intent = new Intent(this, TotoLotoInsert.class);
        startActivity(intent);
    }

    public void openTotolotoView()
    {
        Intent intent = new Intent(this, TotoLotoView.class);
        startActivity(intent);
    }

    public void generateTotolotoNumbers(String fullPath){
        File f = new File(fullPath, TOTOLOTO_FILENAME);
        int[] output = new int[5];
        int minNumber=1;
        int maxNumber=49;
        Random rd = new Random();

        try{
            f.createNewFile();
        }catch(IOException e)
        {
            e.printStackTrace();
        }

        try{
            fOut = new FileOutputStream(f);
        }catch(java.io.FileNotFoundException e)
        {
            e.printStackTrace();
        }


        for(int i = 0; i < output.length; i++)
        {
            output[i] = rd.nextInt((maxNumber - minNumber) + 1) + minNumber;
        }
        for(int i = 0; i < output.length; i++)
        {
            for(int j = 0; j < output.length ; j++)
            {
                if(j == i)
                    continue;
                else
                if(output[i] == output[j])
                    output[i] = rd.nextInt((maxNumber - minNumber) + 1) + minNumber;
            }
        }
        for(int i = 0; i < output.length; i++)
        {
            String number = "[NÚMERO]:" + Integer.toString(output[i]) + "\n";

            try
            {
                fOut.write(number.getBytes());
            }catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        String TotoNumbersOutput = Arrays.toString(output);

        tv_totolotoNumbers.setText(TotoNumbersOutput);

    }

    public void generateTotolotoLucky(){
        int output;
        int minNumber=1;
        int maxNumber=13;
        Random rd = new Random();

        output = rd.nextInt((maxNumber - minNumber) + 1) + minNumber;

        String number = "[NÚMERO DA SORTE]:" + Integer.toString(output) + "\n";

        try
        {
            fOut.write(number.getBytes());
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        if(fOut != null)
        {
            try
            {
                fOut.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        String TotoLuckyOutput = Integer.toString(output);

        tv_totolotoLucky.setText(TotoLuckyOutput);

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

    public void fileExists(Context context, String filename)
    {
        File file = context.getFileStreamPath(filename);

        if(filename==null || !file.exists())
        {
            File newDir = new File(getFilesDir() + "/" + filename);
            if(!newDir.exists())
            {
                newDir.mkdir();
            }
            //Toast.makeText(context, "file not exist", Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(context, "file is exist", Toast.LENGTH_SHORT).show();
    }
}
