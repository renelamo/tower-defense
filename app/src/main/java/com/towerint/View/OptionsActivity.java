package com.towerint.View;

import android.support.v7.app.AppCompatActivity;
import com.towerint.View.MainActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.towerint.R;


public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        /*Déclaration des boutons*/
        final Button returnButton=findViewById(R.id.returnButton);
        final Button musicButton=findViewById(R.id.musicButton);
        /*Connecte le bouton de retour*/
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}