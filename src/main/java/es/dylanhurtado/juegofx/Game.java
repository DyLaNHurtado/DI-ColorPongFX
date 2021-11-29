package es.dylanhurtado.juegofx;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class Game extends BorderPane {

    private final Rectangle borderLeft;
    private final Rectangle borderRight;
    private final Rectangle borderUp;
    private final Rectangle borderDown;
    private final Rectangle racquet1;
    private final Rectangle racquet2;
    private final Circle ball;
    private final GameController controller;
    private final StackPane panelGame;
    private final Label labelScore1;
    private final Label labelScore2;
    private final Label p1WinMessage;
    private final Label p2WinMessage;
    private final Label pressStartLabel;
    private final Label pressPauseLabel;


    public Game() {
        //Instance
        this.borderLeft = new Rectangle();
        this.borderRight = new Rectangle();
        this.borderUp = new Rectangle();
        this.borderDown = new Rectangle();
        this.racquet1 = new Rectangle();
        this.racquet2 = new Rectangle();
        this.ball = new Circle();
        this.panelGame = new StackPane();
        this.labelScore1 = new Label(0 + "\t");
        this.labelScore2 = new Label("\t" + 0);
        this.p1WinMessage = new Label("P1 WINNER");
        this.p2WinMessage = new Label("P2 WINNER");
        this.pressStartLabel = new Label("PRESS SPACE TO START");
        this.pressPauseLabel = new Label("PRESS P TO PAUSE");
        controller = new GameController(borderLeft, borderRight, borderUp, borderDown, racquet1,
                racquet2, ball, labelScore1, labelScore2, p1WinMessage, p2WinMessage, panelGame);


        initGame();
    }

    private void initGame() {

        // --- WALLS ---
        borderLeft.setFill(Color.TRANSPARENT);
        borderLeft.heightProperty().bind(panelGame.heightProperty());
        borderLeft.widthProperty().bind(panelGame.widthProperty().divide(20));

        borderRight.setFill(Color.TRANSPARENT);
        borderRight.heightProperty().bind(borderLeft.heightProperty());
        borderRight.widthProperty().bind(borderLeft.widthProperty());

        borderDown.setFill(Color.CORAL);
        borderDown.heightProperty().bind(borderLeft.widthProperty());
        borderDown.widthProperty().bind(panelGame.widthProperty());

        borderUp.setFill(Color.CORAL);
        borderUp.heightProperty().bind(borderLeft.widthProperty());
        borderUp.widthProperty().bind(panelGame.widthProperty());

        // --- OBJECTS ---
        racquet1.setFill(Color.HOTPINK);
        racquet1.translateXProperty().bind(borderLeft.widthProperty());
        racquet1.heightProperty().bind(borderLeft.heightProperty().divide(5));
        racquet1.widthProperty().bind(borderLeft.widthProperty().divide(2));
        racquet1.setEffect(new DropShadow());

        racquet2.setFill(Color.SPRINGGREEN);
        racquet2.translateXProperty().bind(borderRight.widthProperty().multiply(-1));
        racquet2.heightProperty().bind(borderLeft.heightProperty().divide(5));
        racquet2.widthProperty().bind(borderLeft.widthProperty().divide(2));
        racquet2.setEffect(new DropShadow());

        ball.setFill(Color.BLACK);
        ball.radiusProperty().bind(borderLeft.widthProperty().divide(3));
        ball.setEffect(new InnerShadow());

        //Sets and Adds
        this.panelGame.setMinSize(0, 0);
        this.panelGame.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        panelGame.setEffect(new InnerShadow());
        p1WinMessage.setVisible(false);
        p2WinMessage.setVisible(false);
        panelGame.getChildren().addAll(labelScore1, labelScore2, borderLeft, borderRight, borderDown, borderUp
                , racquet1, racquet2, ball, p1WinMessage, p2WinMessage, pressStartLabel,pressPauseLabel);

        StackPane.setAlignment(borderLeft, Pos.CENTER_LEFT);
        StackPane.setAlignment(borderRight, Pos.CENTER_RIGHT);
        StackPane.setAlignment(borderDown, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(borderUp, Pos.TOP_CENTER);
        StackPane.setAlignment(racquet1, Pos.CENTER_LEFT);
        StackPane.setAlignment(racquet2, Pos.CENTER_RIGHT);
        StackPane.setAlignment(ball, Pos.CENTER);
        StackPane.setAlignment(p1WinMessage, Pos.CENTER);
        StackPane.setAlignment(p2WinMessage, Pos.CENTER);
        StackPane.setAlignment(pressStartLabel,Pos.TOP_CENTER);
        StackPane.setAlignment(pressPauseLabel,Pos.BOTTOM_CENTER);

        labelScore1.setFont(Font.font(120));
        labelScore2.setFont(Font.font(120));

        p1WinMessage.setFont(Font.font("Impact",64));
        p2WinMessage.setFont(Font.font("Impact",64));

        pressStartLabel.setFont(Font.font(16));
        pressStartLabel.setTextFill(Color.WHITESMOKE);
        pressStartLabel.setEffect(new DropShadow());

        pressPauseLabel.setFont(Font.font(16));
        pressPauseLabel.setEffect(new DropShadow());
        pressPauseLabel.setTextFill(Color.WHITESMOKE);

        p1WinMessage.setTextFill(Color.PINK);
        p2WinMessage.setTextFill(Color.SPRINGGREEN);


        this.setCenter(panelGame);
    }

}