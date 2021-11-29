package es.dylanhurtado.juegofx;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class GameController {
    private final Rectangle borderLeft;
    private final Rectangle borderRight;
    private final Rectangle borderUp;
    private final Rectangle borderDown;
    private final Rectangle racquet1;
    private final Rectangle racquet2;
    private final Circle ball;
    private double movBallX, movBallY, movRacquet1, movRacquet2;
    private Timeline animation;
    private final StackPane panelGame;
    private final Label labelScore1;
    private final Label labelScore2;
    private final Label p1WinMessage;
    private final Label p2WinMessage;
    private int score1, score2;

    public GameController(Rectangle borderLeft, Rectangle borderRight, Rectangle borderUp,
                          Rectangle borderDown, Rectangle racquet1, Rectangle racquet2,
                          Circle ball, Label labelScore1, Label labelScore2,
                          Label p1WinMessage, Label p2WinMessage, StackPane panelGame) {
        this.borderLeft = borderLeft;
        this.borderRight = borderRight;
        this.borderUp = borderUp;
        this.borderDown = borderDown;
        this.racquet1 = racquet1;
        this.racquet2 = racquet2;
        this.ball = ball;
        this.labelScore1 = labelScore1;
        this.labelScore2 = labelScore2;
        this.p1WinMessage = p1WinMessage;
        this.p2WinMessage = p2WinMessage;

        this.movBallX = 2;
        this.movBallY = 2;
        this.panelGame = panelGame;
        this.score1 = 0;
        this.score2 = 0;

        initControls();
        initGame();

    }

    private void initGame() {


        animation = new Timeline(new KeyFrame(Duration.millis(17), t -> {

            moveRacquets();
            ballMove();
            collision();


        }));
        animation.setCycleCount(Animation.INDEFINITE);
    }

    // ------ MOVEMENTS ------


    private void moveRacquets() {
        controls();
        racquet1.setTranslateY(racquet1.getTranslateY() + movRacquet1);
        racquet2.setTranslateY(racquet2.getTranslateY() + movRacquet2);
    }

    private void ballMove() {
        ball.setTranslateY(ball.getTranslateY() + movBallY);
        ball.setTranslateX(ball.getTranslateX() + movBallX);
    }

    // ------ COLLISIONS ------

    private void collision() {
        //Racquets with ball
        if (ball.getBoundsInParent().intersects(racquet1.getBoundsInParent())
                || ball.getBoundsInParent().intersects(racquet2.getBoundsInParent())) {
            if (movBallX < 10 && movBallX > -10) {
                movBallX = -movBallX * 1.1;
                ball.setFill(Color.rgb((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));

            } else {
                movBallX = -movBallX;
            }
        }

        //Ball with left and right borders
        if (ball.getBoundsInParent().intersects(borderRight.getBoundsInParent())) {
            resetElements();
            if (score1 < 4) {
                score1++;
                labelScore1.setText(score1 + "\t");
            } else {
                winAnimation(p1WinMessage);
                restartScores();
            }
        }
        if (ball.getBoundsInParent().intersects(borderLeft.getBoundsInParent())) {
            resetElements();
            if (score2 < 4) {
                score2++;
                labelScore2.setText("\t" + score2);
            }else{

                winAnimation(p2WinMessage);
                restartScores();
            }
        }

        //Ball with up and down borders
        if (ball.getBoundsInParent().intersects(borderUp.getBoundsInParent())
                || ball.getBoundsInParent().intersects(borderDown.getBoundsInParent())) {
            if (movBallY < 10 && movBallY > -10) {
                movBallY = -movBallY * 1.1;

            } else {
                movBallY = -movBallY;
            }

            //Change colors
            borderDown.setFill(ball.getFill());
            borderUp.setFill(ball.getFill());
            ball.setFill(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        }

        //Racquets with borders
        if (racquet1.getBoundsInParent().intersects(borderUp.getBoundsInParent())) {
            racquet1.setTranslateY(racquet1.getTranslateY() + 4.5);
        }
        if (racquet1.getBoundsInParent().intersects(borderDown.getBoundsInParent())) {
            racquet1.setTranslateY(racquet1.getTranslateY() - 4.5);
        }
        if (racquet2.getBoundsInParent().intersects(borderUp.getBoundsInParent())) {
            racquet2.setTranslateY(racquet2.getTranslateY() + 4.5);
        }
        if (racquet2.getBoundsInParent().intersects(borderDown.getBoundsInParent())) {
            racquet2.setTranslateY(racquet2.getTranslateY() - 4.5);
        }
    }


    // ------ CONTROLS ------

    private void initControls() {
        panelGame.setOnKeyPressed(e ->{
            if (e.getCode() == KeyCode.SPACE) {
                animation.play();
            }
        });
        panelGame.setFocusTraversable(true);

    }

    private void controls() {
        panelGame.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    movRacquet1 = -4.5;
                    break;
                case S:
                    movRacquet1 = 4.5;
                    break;
                case UP:
                    movRacquet2 = -4.5;
                    break;
                case DOWN:
                    movRacquet2 = 4.5;
                    break;
                case P:
                    animation.pause();
                    break;
                case SPACE:
                    animation.play();
                    break;
            }
        });

        panelGame.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W:
                case S:
                    movRacquet1 = 0.0;
                    break;
                case UP:
                case DOWN:
                    movRacquet2 = 0.0;
                    break;
            }
        });
    }

    private void restartScores() {
        score1 = 0;
        score2 = 0;
        labelScore1.setText(score1 + "\t");
        labelScore2.setText("\t" + score2);

    }
    private void winAnimation(Label messageWin){
        animation.stop();
        FadeTransition winner = new FadeTransition(Duration.millis(1500), messageWin);
        messageWin.setVisible(true);
        winner.setFromValue(0.0);
        winner.setToValue(1.0);
        winner.setAutoReverse(true);
        winner.setCycleCount(4);
        winner.play();

    }
    private void resetElements(){
        ball.setTranslateX(0);
        ball.setTranslateY(0);
        racquet1.setTranslateY(0);
        racquet2.setTranslateY(0);
        ball.setTranslateX(0);
        ball.setTranslateY(0);
        movBallX = 2;
        movBallY = 2;
    }

}