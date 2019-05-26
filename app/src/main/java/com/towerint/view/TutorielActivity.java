package com.towerint.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.towerint.R;


public class TutorielActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutoriel);


        Intent parentIntent=getIntent();
;

        /*Déclaration des boutons*/
        final Button returnButton=findViewById(R.id.returnButton);

        final Button rulesButton = findViewById(R.id.rulesButton); ;
        final Button interfaceButton = findViewById(R.id.interfaceButton); ;
        final Button towersButton = findViewById(R.id.towersButton); ;
        final Button attackersButton = findViewById(R.id.attackersButton); ;


        /*Connecte le bouton de retour*/
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult();
                finish();
            }
        });


        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorielActivity.this, RulesActivity.class);
                startActivityForResult(intent, 0);

            }
        });
        interfaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorielActivity.this, InterfaceActivity.class);
                startActivityForResult(intent, 0);

            }
        });
        towersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorielActivity.this, TowersActivity.class);
                startActivityForResult(intent, 0);

            }
        });
        attackersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorielActivity.this, AttackersActivity.class);
                startActivityForResult(intent, 0);

            }
        });

    }


    @Override
    public void onBackPressed() {
        sendResult();
        super.onBackPressed();
    }

    private void sendResult(){
        Intent out=new Intent();
        setResult(RESULT_OK, out);
    }
}