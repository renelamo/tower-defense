package com.towerint.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.towerint.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;


public class ScoreActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        Intent parentIntent=getIntent();
;

        /*Déclaration des boutons*/
        final Button returnButton=findViewById(R.id.returnButton);

        final Button resetButton=findViewById(R.id.resetScoreButton);

        final TextView text=findViewById(R.id.scoresTextView);

        /*Connecte le bouton de retour*/
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetScore(text);
            }
        });

        File saves=new File(getCacheDir(), "scores.csv");
        if(saves.exists()){
            sortScore();
            try {
                FileInputStream reader = new FileInputStream(saves);
                while (reader.available()>0){
                    int rd=reader.read();
                    text.append(Character.toString((char)rd));
                }
                reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else {
            text.setText(R.string.no_save_found);
        }
    }

    private void resetScore(TextView text){
        new File(getCacheDir(), "scores.csv").delete();
        if(text !=null){
            text.setText(R.string.no_save_found);
        }
    }

    private void sortScore(){
        ArrayList<Integer> liste = new ArrayList<>();
        File saves= new File(getCacheDir(), "scores.csv");
        try{
            FileInputStream reader = new FileInputStream(saves);
            String str="";
            while (reader.available()>0){
                int rd=reader.read();
                if((char) rd == '\n'){
                    liste.add(Integer.parseInt(str));
                    str="";
                }else{
                    str+=(char)rd;
                }
            }
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        resetScore(null);
        Collections.sort(liste, Collections.reverseOrder());
        try{
            FileWriter writer=new FileWriter(saves, true);
            for(Integer I:liste){
                int i=I;
                writer.write(String.valueOf(i));
                writer.write('\n');
                writer.flush();
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}