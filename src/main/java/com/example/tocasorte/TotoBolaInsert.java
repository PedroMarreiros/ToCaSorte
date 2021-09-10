package com.example.tocasorte;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TotoBolaInsert extends Activity {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:d:HH:mm:ss");
    private final String TOTOBOLA_FILENAME = "TOTOBOLA" + format.format(calendar.getTime()) + ".txt";
    EditText et_equipa1, et_equipa2, et_resultado;
    Button b_insertNextTeam, b_terminate;
    TextView tv_insertCounter;
    int insertCounter = 0;
    OutputStream fOut = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        final String DIRECTORY_NAME = "totobola_folder.txt";
        final String fullPath = getFilesDir() + "/" + DIRECTORY_NAME;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toto_bola_insert);

        fileExists(this, DIRECTORY_NAME);

        File f = new File(fullPath, TOTOBOLA_FILENAME);
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

        et_equipa1 = findViewById(R.id.et_equipa1);
        et_equipa2 = findViewById(R.id.et_equipa2);
        et_resultado = findViewById(R.id.et_resultado);
        b_insertNextTeam = findViewById(R.id.b_insertNextTeam);
        tv_insertCounter = findViewById(R.id.tv_insertCounter);

        String initDisplayCounter = "Está a inserir a chave " + (insertCounter + 1);
        tv_insertCounter.setText(initDisplayCounter);


        b_insertNextTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (insertCounter < 14) {
                        if (insertCounter == 13) {
                            String equipa1 = "[EQUIPA 1]:" + et_equipa1.getText().toString() + " ";
                            String equipa2 = "[EQUIPA 2]:" + et_equipa2.getText().toString() + " ";
                            String resultado = "[RESULTADO]:" + et_resultado.getText().toString() + "\n";

                            try {
                                fOut.write(equipa1.getBytes());
                                fOut.write(equipa2.getBytes());
                                fOut.write(resultado.getBytes());
                            }catch(IOException e)
                            {
                                e.printStackTrace();
                            }
                            et_equipa1.getText().clear();
                            et_equipa2.getText().clear();
                            et_resultado.getText().clear();
                            Toast.makeText(TotoBolaInsert.this, "Todas as chaves foram inseridas. Consulte 'Ver Boletim", Toast.LENGTH_SHORT).show();
                            if (fOut != null) {
                                try {
                                    fOut.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            finish();
                        } else {
                            if (insertCounter == 12) {
                                String equipa1 = "[EQUIPA 1]:" + et_equipa1.getText().toString() + " ";
                                String equipa2 = "[EQUIPA 2]:" + et_equipa2.getText().toString() + " ";
                                String resultado = "[RESULTADO]:" + et_resultado.getText().toString() + "\n";

                                try {
                                    fOut.write(equipa1.getBytes());
                                    fOut.write(equipa2.getBytes());
                                    fOut.write(resultado.getBytes());
                                }catch(IOException e)
                                {
                                    e.printStackTrace();
                                }
                                et_equipa1.getText().clear();
                                et_equipa2.getText().clear();
                                et_resultado.getText().clear();
                                String displayCounter = "Está a inserir a super " + (insertCounter + 2);
                                tv_insertCounter.setText(displayCounter);
                                insertCounter = insertCounter + 1;
                            } else {
                                String equipa1 = "[EQUIPA 1]:" + et_equipa1.getText().toString() + " ";
                                String equipa2 = "[EQUIPA 2]:" + et_equipa2.getText().toString() + " ";
                                String resultado = "[RESULTADO]:" + et_resultado.getText().toString() + "\n";

                                try {
                                    fOut.write(equipa1.getBytes());
                                    fOut.write(equipa2.getBytes());
                                    fOut.write(resultado.getBytes());
                                }catch(IOException e)
                                {
                                    e.printStackTrace();
                                }
                                et_equipa1.getText().clear();
                                et_equipa2.getText().clear();
                                et_resultado.getText().clear();
                                String displayCounter = "Está a inserir a chave " + (insertCounter + 2);
                                tv_insertCounter.setText(displayCounter);
                                insertCounter = insertCounter + 1;
                            }

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
                //Toast.makeText(context, "file not exist", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(context, "file is exist", Toast.LENGTH_SHORT).show();
        }
}
