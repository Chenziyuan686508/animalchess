package controller;

import controller.GameController;
import model.ChessboardPoint;
import model.PlayerColor;
import view.ChessboardComponent;

public class CountdownTimer extends Thread {
    public static int time = 30;
    public GameController controller;

    public CountdownTimer(GameController controller){
        this.controller = controller;
    }
    @Override
    public void run(){
        synchronized (this){
            while (true){
                PlayerColor player = controller.currentPlayer;
                boolean b = true;
                while(time > 0) {
                    time--;
                    try {
                        Thread.sleep(1000);
                        controller.timeLabel.setText("剩余时间: " + time);
                        if (controller.currentPlayer != player){
                            controller.swapColor();
                            b = false;
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                time = 30;

                if (b){
                    ChessboardPoint[] points = controller.aIGetPoint();
                    ChessboardPoint src = points[0];
                    ChessboardPoint dest = points[1];

                    if (controller.model.getChessPieceAt(dest) == null){
                        controller.model.moveChessPiece(src, dest);
                        controller.view.setChessComponentAtGrid(dest, controller.view.removeChessComponentAtGrid(src));
                    } else {
                        controller.model.captureChessPiece(src, dest);
                        controller.view.removeChessComponentAtGrid(dest);
                        controller.view.setChessComponentAtGrid(dest, controller.view.removeChessComponentAtGrid(src));
                    }
                    controller.canStepPoints = null;
                    controller.setCanStepFalse();
                    controller.swapColor();
                    controller.view.repaint();
                    controller.view.gridComponents[dest.getRow()][dest.getCol()].revalidate();
                    controller.Win();
                    if (controller.winner != null){
                        controller.show();
                        controller.restart();
                    }
                } else {
                    controller.swapColor();
                }

            }
        }

    }



}
