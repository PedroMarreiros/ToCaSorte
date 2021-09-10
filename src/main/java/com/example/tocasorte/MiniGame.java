package com.example.tocasorte;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MiniGame extends Activity {
    TextView tv_genNumber, tv_result;
    EditText et_UserNumber;
    Button b_genNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_game);

        tv_result=findViewById(R.id.tv_result);
        tv_genNumber=findViewById(R.id.tv_genNumber);
        et_UserNumber=findViewById(R.id.et_userNumber);
        b_genNumber=findViewById(R.id.b_genNumber);

        b_genNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateNumber();
            }
        });
    }
    public void generateNumber(){
        int output;
        int minNumber = 0;
        int maxNumber = 5;
        Random rd = new Random();

        String userInput = et_UserNumber.getText().toString();

        output = rd.nextInt((maxNumber - minNumber) + 1) + minNumber;

        String GenNumberOutput = Integer.toString(output);

        if(userInput.equals(GenNumberOutput))
        {
            tv_result.setText("ACERTOU!");
        }
        else
            tv_result.setText("FALHOU");

        tv_genNumber.setText(GenNumberOutput);

    }
}
