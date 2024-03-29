/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import domein.highscoreSpeler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javafx.scene.layout.StackPane;
import persistentie.HighscoresMapper;

/**
 *
 * @author bjorn
 */
public class HighscoreScherm extends BorderPane {

    private final DomeinController dc;
    private final Startscherm sts;

    public HighscoreScherm(Startscherm sts, DomeinController dc) {
        this.sts = sts;
        this.dc = dc;
        buildGui();
    }

    private void buildGui() {

        getStylesheets().add("/css/highscoreScherm.css");

        BorderPane bp = new BorderPane();
        HBox knop = new HBox();
        VBox vbox = new VBox();
        GridPane gp = new GridPane();
        GridPane grid = new GridPane();
        Label lblHighscores = new Label("Highscores");

        HashMap<String, highscoreSpeler> spelers = HighscoresMapper.geefScore();
        ArrayList<highscoreSpeler> spelersByScore = new ArrayList<>(spelers.values());

        Collections.sort(spelersByScore, new ScoreComparator());

        int g = 0;

        for (highscoreSpeler p : spelersByScore) {
            Label highScore = new Label(p.getNaam() + " : " + p.getHighscore());
            grid.add(highScore, 0, g++);

        }

        lblHighscores.setId("lblHighscores");

        Button btnBack = new Button();
        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent evt) {

                Stage stage = (Stage) getScene().getWindow();
                stage.setScene(sts.getScene());
            }
        });

        btnBack.setId("btnBack");
        btnBack.setPrefSize(100, 100);

        // gp (gridpane) voor de highscores
        grid.setAlignment(Pos.CENTER);
        lblHighscores.setAlignment(Pos.CENTER);
        lblHighscores.setPadding(new Insets(-100, 0, 0, 0));

        knop.getChildren().add(btnBack);
        vbox.getChildren().addAll(lblHighscores, gp, grid);

        knop.setAlignment(Pos.BOTTOM_CENTER);
        knop.setPadding(new Insets(15));
        vbox.setAlignment(Pos.CENTER);

        bp.setCenter(vbox);
        bp.setBottom(knop);

        this.setCenter(bp);

    }

    public class ScoreComparator implements Comparator<highscoreSpeler> {

        @Override
        public int compare(highscoreSpeler o1, highscoreSpeler o2) {
            return o2.getHighscore() - o1.getHighscore();
        }

    }

}
