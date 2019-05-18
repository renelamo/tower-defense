package com.towerint.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.towerint.R;


public class TowersActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_towers);


        Intent parentIntent=getIntent();
;

        /*Déclaration des boutons*/
        final Button returnButton=findViewById(R.id.returnButton);

        /*Connecte le bouton de retour*/
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult();
                finish();
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