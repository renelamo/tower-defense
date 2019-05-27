package com.towerint.view;

import com.towerint.model.TowerType3;
import com.towerint.model.Vector2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.towerint.model.Tower;
import com.towerint.controller.GameEngine;
import com.towerint.model.TowerType1;
import com.towerint.model.TowerType2;
import com.towerint.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;


public class GameActivity extends AppCompatActivity {
    public com.towerint.controller.GameEngine gameEngine;
    public boolean isTouch = false;
    private boolean paused=false;
    private boolean bruitages;
    private boolean saved=false;
    private String m_Text;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the pixel dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();

        // Initialize the result into a Point object
        Point size = new Point();
        display.getSize(size);

        // Create a new instance of the gameEngine class
        gameEngine = new GameEngine(this, size);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Intent parentIntent=getIntent();
        bruitages=parentIntent.getBooleanExtra("bruitages", true);
        gameEngine.setBruitages(bruitages);

        gameEngine.addOnLooseListener(new GameEngine.onLooseListener() {
            @Override
            public void onLoose() {
                saveScore();
            }
        });

        // Make gameEngine the view of the Activity
        setContentView(R.layout.activity_game);
        setContentView(gameEngine);

    }

    @Override
    public void onBackPressed() {
        onPause();
    }

    // Start the thread in gameEngine
    @Override
    protected void onResume() {
        super.onResume();
        paused=false;
        gameEngine.resume();
    }

    // Stop the thread in gameEngine
    @Override
    protected void onPause() {
        super.onPause();
        paused=true;
        gameEngine.pause();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventAction = event.getAction();
        int partX=(int)(gameEngine.screenX*.15);
        if(eventAction==MotionEvent.ACTION_DOWN) {
            if(X>gameEngine.screenX-partX && Y<partX){//Bouton de pause
                if(paused)
                {
                    onResume();
                }else {
                    onPause();
                }
                gameEngine.begin=true;
            }
            else if(X<=partX && Y>gameEngine.screenY-partX) //Choix tour type 1
            {
                gameEngine.tower =1 ;
            }
            else if(X>= partX&& X<2*partX && Y>gameEngine.screenY-partX) //choix tour tyoe 2
            {
                gameEngine.tower =2 ;
            }
            else if(X>= 2*partX&& X<3*partX && Y>gameEngine.screenY-partX) //choix tour tyoe 2
            {
                gameEngine.tower =3 ;
            }
            else if(X>= 3*partX&& X<4*partX && Y>gameEngine.screenY-partX){// Bouton start
                gameEngine.begin=true;
            }
            else if(X>= 4*partX&& X<5*partX && Y>gameEngine.screenY-partX) { //Bouton Menu
                saveScore();
                finish();
            }
            else if(X>= gameEngine.screenX-partX&& X<gameEngine.screenX && Y>gameEngine.screenY-partX && gameEngine.endlevel) { //Bouton restart
                gameEngine.newGame();
            }else{
                createTower(new Vector2(X, Y));
            }
            //Toast.makeText(this, "ACTION_DOWN AT COORDS "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
            isTouch = true;
        }
        return true;
    }

    private boolean createTower(Vector2 position){
        if(gameEngine.isPlaying & !gameEngine.endlevel) {
            int partX = (int) (gameEngine.screenX * .15);
            if (gameEngine.getPath().isOnPath(position)) {
                System.out.println("Tour sur le chemin");
                Toast.makeText(this, "IMPOSSIBLE DE METTRE UNE TOUR SUR LE CHEMIN ", Toast.LENGTH_SHORT).show();

                return false;
            }
            for (Tower t : gameEngine.towers) {
                if (Vector2.distance(t.getPosition(), position) < partX) {
                    System.out.println("Tour sur Tour");
                    Toast.makeText(this, "IMPOSSIBLE DE METTRE UNE TOUR SUR UNE AUTRE TOUR", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            switch (gameEngine.tower) {
                case 1:
                    if (gameEngine.money < TowerType1.cost) {
                        int prixManquant = TowerType1.cost - gameEngine.money;
                        Toast.makeText(this, "IL MANQUE " + prixManquant + "$", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if (bruitages) {
                        MediaPlayer construction = MediaPlayer.create(gameEngine.getContext(), R.raw.construction);
                        construction.setLooping(false);
                        construction.start();
                    }
                    gameEngine.towers.add(new TowerType1(position, gameEngine));
                    gameEngine.money -= TowerType1.cost;
                    break;
                case 2:
                    if (gameEngine.money < TowerType2.cost) {
                        int prixManquant = TowerType2.cost - gameEngine.money;
                        Toast.makeText(this, "IL MANQUE " + prixManquant + "$", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    gameEngine.towers.add(new TowerType2(position, gameEngine));
                    gameEngine.money -= TowerType2.cost;
                    break;
                case 3:
                    if (gameEngine.money < TowerType3.cost) {
                        int prixManquant = TowerType3.cost - gameEngine.money;
                        Toast.makeText(this, "IL MANQUE " + prixManquant + "$", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    gameEngine.towers.add(new TowerType3(position, gameEngine));
                    gameEngine.money -= TowerType3.cost;
                    break;
            }
            return true;
        }
        else {
            if (gameEngine.gg) {
                Toast.makeText(this, "APPUYEZ SUR NEXTLEVEL POUR CONTINUER", Toast.LENGTH_SHORT).show();
            }
            if (!gameEngine.gg) {
                Toast.makeText(this, "APPUYEZ SUR RESTART POUR RECOMMENCER", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }

    public void saveScore(){
        if(!saved) {
            final Context context=this;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.save_score_title);

// Set up the input
                    final EditText input = new EditText(context);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setMessage(R.string.username_prompt);
                    builder.setView(input);

// Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();
                            try {
                                File save = new File(getCacheDir(), "scores.csv");

                                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(save, true), StandardCharsets.UTF_16);
                                writer.write(m_Text);
                                writer.write(',');
                                writer.write(String.valueOf(gameEngine.score));
                                writer.write('\n');
                                writer.flush();
                                writer.close();
                                System.out.println("saved");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    builder.setNegativeButton(R.string.skip_save_score, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.show();
                }
            });
            saved=true;
        }
    }

}