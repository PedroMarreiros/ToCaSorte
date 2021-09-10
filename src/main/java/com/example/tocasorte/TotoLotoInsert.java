package com.example.tocasorte;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TotoLotoInsert extends Activity {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:d:HH:mm");
    private final String TOTOLOTO_FILENAME = "TOTOLOTO" + format.format(calendar.getTime()) + ".txt";
    EditText et_keyInsert;
    Button b_insertNextNumber;
    TextView tv_insertCounter;
    int insertCounter = 0;
    OutputStream fOut = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        final String DIRECTORY_NAME = "totoloto_folder.txt";
        final String fullPath = getFilesDir() + "/" + DIRECTORY_NAME;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toto_loto_insert);

        fileExists(this, DIRECTORY_NAME);

        File f = new File(fullPath, TOTOLOTO_FILENAME);
        try{
            f.createNewFile();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        try {
            fOut = new FileOutputStream(f);
        }catch(java.io.FileNotFoundException e)
        {
            e.printStackTrace();
        }

        et_keyInsert = findViewById(R.id.et_keyInsert);
        b_insertNextNumber = findViewById(R.id.b_insertNextNumber);
        tv_insertCounter = findViewById(R.id.tv_insertCounter);

        tv_insertCounter.setText("Está a inserir os números");


        b_insertNextNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (insertCounter < 6) {
                    if (insertCounter == 5) {
                        String number = "[NÚMERO DA SORTE]:" + et_keyInsert.getText().toString() + "\n";

                        try {
                            fOut.write(number.getBytes());
                        }catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                        et_keyInsert.getText().clear();
                        Toast.makeText(TotoLotoInsert.this, "Todas os algarismos foram inseridas. Consulte 'Ver Boletim", Toast.LENGTH_SHORT).show();
                        if (fOut != null) {
                            try {
                                fOut.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        finish();
                    } else {
                            String number = "[NÚMERO]:" + et_keyInsert.getText().toString() + "\n";

                            try {
                                fOut.write(number.getBytes());
                            }catch(IOException e)
                            {
                                e.printStackTrace();
                            }
                            et_keyInsert.getText().clear();
                            if(insertCounter == 4)
                                tv_insertCounter.setText("Está a inserir o número da sorte");
                            insertCounter = insertCounter + 1;



                    }
                }
            }
        });


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
            Toast.makeText(context, "file not exist", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(context, "file is exist", Toast.LENGTH_SHORT).show();
    }
}
