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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


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
                InputStreamReader reader=new InputStreamReader(new FileInputStream(saves), StandardCharsets.UTF_16);
                while (reader.ready()){
                    int rd=reader.read();
                    if((char)rd == ','){
                        text.append(Character.toString('\t'));
                    }else {
                        text.append(Character.toString((char) rd));
                    }
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
        ArrayList<Score> liste = new ArrayList<>();
        File saves= new File(getCacheDir(), "scores.csv");
        try{
            InputStreamReader reader=new InputStreamReader(new FileInputStream(saves), StandardCharsets.UTF_16);
            String str="";
            while (reader.ready()){
                int rd=reader.read();
                if((char) rd == '\n'){
                    liste.add(new Score(str));
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
        Collections.sort(liste, new ScoreComparator());
        try{
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(saves, true), StandardCharsets.UTF_16);
            for(Score S:liste){
                writer.write(S.toString());
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

    private class Score{
        int val;
        String joueur;

        Score(String s){
            String[] splitted=s.split(",");
            joueur=splitted[0];
            val=Integer.parseInt(splitted[1]);
        }

        @Override
        public String toString() {
            return joueur+","+val;
        }
    }

    private class ScoreComparator implements Comparator<Score>{
        @Override
        public int compare(Score o1, Score o2) {
            return o2.val-o1.val;
        }
    }
}