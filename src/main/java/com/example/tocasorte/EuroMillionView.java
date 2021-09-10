package com.example.tocasorte;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.BlendMode.COLOR;

public class EuroMillionView extends Activity {
    Spinner sp_fileList;
    TextView tv_display;
    Button b_display, b_share;
    String fileNameToDisplay;
    Intent shareIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        File euromillionDir = new File(getFilesDir() + "/" + "euromillion_folder.txt");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_euro_million_view);

        sp_fileList = findViewById(R.id.sp_fileList);
        tv_display = findViewById(R.id.tv_display);
        b_display = findViewById(R.id.b_display);
        b_share = findViewById(R.id.b_share);

        getFileNames(euromillionDir);

        sp_fileList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.YELLOW);
                fileNameToDisplay = parent.getItemAtPosition(position).toString();
                Toast.makeText(EuroMillionView.this, "Selected " + fileNameToDisplay, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        b_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_display.setText(readFile(fileNameToDisplay));
            }
        });

        b_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ToCaSorte");
                shareIntent.putExtra(Intent.EXTRA_TEXT, readFile(fileNameToDisplay));
                startActivity(Intent.createChooser(shareIntent, "Partilhar via"));

            }
        });
    }

    public void getFileNames(File f){
        List<String> listFile = new ArrayList<String>();
        File[] files = f.listFiles();
        listFile.clear();
        for(File file:files)
        {
            listFile.add(file.getName());
        }
        ArrayAdapter<String> filenameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listFile);
        sp_fileList.setAdapter(filenameAdapter);
    }

    public String readFile(String filename)
    {
        File fileToRead = new File(getFilesDir() + "/" + "euromillion_folder.txt" + "/" + filename);

        StringBuilder text = new StringBuilder();

        try{
            BufferedReader br = new BufferedReader(new FileReader(fileToRead));
            String line;
            while((line = br.readLine()) != null)
            {
                text.append(line);
                text.append("\n");
            }
            br.close();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        String result = text.toString();
        return result;
    }

}
