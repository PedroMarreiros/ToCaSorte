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


public class EuromilionActivity extends Activity {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:d:HH:mm:ss");
    private final String EUROMILLION_FILENAME = "[CHAVE GERADA]" + format.format(calendar.getTime()) + ".txt";

    TextView tv_EuroNumbers, tv_EuroStars;
    Button b_genEuroKey, b_insertKey, b_euromillionView;
    private static String SETTINGS_FILE = "settings.txt";
    int userSettings;
    OutputStream fOut = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String DIRECTORY_NAME = "euromillion_folder.txt";
        final String fullPath = getFilesDir() + "/" + DIRECTORY_NAME;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_euromilion);

        fileExists(this, DIRECTORY_NAME);

        userSettings=getUserSettings();

        tv_EuroNumbers = findViewById(R.id.tv_EuroNumbers);
        tv_EuroStars = findViewById(R.id.tv_EuroStars);
        b_insertKey = findViewById(R.id.b_insertKey);
        b_euromillionView = findViewById(R.id.b_euromillionview);

        b_genEuroKey = findViewById(R.id.b_genEuroKey);
        b_genEuroKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                generateEuroNumbers(fullPath);
                generateEuroStars();
            }

        });

        b_insertKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInsertActivity();
            }
        });

        b_euromillionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewActivity();
            }
        });

    }

    @Override
    protected void onDestroy(){
        File euromillionDir = new File(getFilesDir() + "/" + "euromillion_folder.txt");

        super.onDestroy();

        applyUserSettings(euromillionDir);
    }

    public void openInsertActivity()
    {
        Intent intent = new Intent(this, EuromillionInsert.class);
        startActivity(intent);
    }

    public void openViewActivity(){
        Intent intent = new Intent(this, EuroMillionView.class);
        startActivity(intent);
    }

    public void generateEuroNumbers(String fullPath){
        File f = new File(fullPath, EUROMILLION_FILENAME);
        int[] output = new int[5];
        int minNumber=1;
        int maxNumber=50;
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
        String EuroNumbersOutput = Arrays.toString(output);

        tv_EuroNumbers.setText(EuroNumbersOutput);

    }
    public void generateEuroStars(){
        int[] output = new int[2];
        int minNumber = 1;
        int maxNumber = 12;
        Random rd = new Random();


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
        for(int i = 0; i < output.length;i++)
        {
            String number = "[ESTRELA]:" + Integer.toString(output[i]) +"\n";

            try{
                fOut.write(number.getBytes());
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

        if(fOut != null)
        {
            try{
                fOut.close();
            }catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        String EuroStarsOutput = Arrays.toString(output);

        tv_EuroStars.setText(EuroStarsOutput);

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
