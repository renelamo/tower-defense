package towerINT;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TOWER'INT");

        /*Création des boutons du menu*/
        //TODO: connecter ces boutons aux events appropriés
        Button startButton=new Button("Start");
        startButton.setDefaultButton(true);
        startButton.setLayoutX(100);
        startButton.setLayoutY(200);
        Button optionsButton=new Button("Options");
        optionsButton.setLayoutX(100);
        optionsButton.setLayoutY(300);
        Button exitButton=new Button("Exit");
        exitButton.setCancelButton(true);
        exitButton.setLayoutX(100);
        exitButton.setLayoutY(400);
        Image soundOn= new Image("soundOn.png",50,50,false,false);
        Image soundOff=new Image("soundOff.png",50,50,false,false);
        Canvas soundCanvas=new Canvas(50,50);
        GraphicsContext soundGC= soundCanvas.getGraphicsContext2D();
        soundGC.drawImage(soundOn, 0 ,0);
        Button soundButton=new Button("", soundCanvas);
        soundButton.setLayoutX(0);
        soundButton.setLayoutY(450);
        Group buttonsGroup=new Group(startButton, optionsButton, exitButton, soundButton);

        /*Création de l'image et du texte du menu*/
        Image towerImage=new Image("tower.png", 400, 400, true, false);
        Canvas cnv=new Canvas(800, 500);
        GraphicsContext gc=cnv.getGraphicsContext2D();
        gc.drawImage(towerImage, 400, 0);
        gc.fillText("MENU", 100, 50);

        Group menuGroup=new Group(cnv, buttonsGroup);
        Scene menu=new Scene(menuGroup);
        primaryStage.setScene(menu);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
