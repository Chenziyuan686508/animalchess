package controller;


import listener.GameListener;
import model.*;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
 */
public class GameController implements GameListener {


    public Chessboard model;
    public ChessboardComponent view;
    public PlayerColor currentPlayer;
    public PlayerColor winner;
    public ChessGameFrame chessGameFrame;
    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    public Thread thread;
    public ArrayList<ChessboardPoint> canStepPoints;
    public boolean AIPlaying;
    public JLabel timeLabel;
    public static CountdownTimer timer;
    public int seconds;
   // public JLabel statusLabel0;
    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        this.timeLabel = view.timeLabel;
        this.seconds = 30;

    //    statusLabel0=this.selectedPoint;
        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }
    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // after a valid move swap the player
    public void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        if (currentPlayer == PlayerColor.BLUE){
        //    view.statusLabel.setText("Turn " + (Chessboard.steps.size()/2 + 1) + ": BLUE");
            view.statusLabel.setText("蓝方回合"+(model.ways.size()/2+1));
            view.statusLabel.setForeground(Color.BLUE);}
        else{
            view.statusLabel.setText("红方回合"+(model.ways.size()/2+1));
            view.statusLabel.setForeground(Color.RED);}
    }

    public void swapTime(){
      /*  timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (seconds <= 0) {
                    if (timer != null) {
                        timer.cancel();
                    }
                } else {
                    timeLabel.setText("剩余时间： " + seconds);
                    seconds--;
                }
            }
        }, 0, 1000);*/
    }

    public void Win() {
        if( model.getGrid()[8][3].getPiece() != null || model.redDead.size() == 8 ){
            this.winner = PlayerColor.BLUE;
        }
        else if(model.getGrid()[0][3].getPiece() != null || model.blueDead.size() == 8 ) {
            this.winner = PlayerColor.RED;
        }
    }
    public void show(){
        JOptionPane.showMessageDialog(view, (winner == PlayerColor.BLUE ? "蓝方" : "红方") + "你厉害\n给你个大拇哥");
    }

    public void restart(){
        model.initGrid();
        model.initPieces();
        view.removeChessComponent();
        view.initiateChessComponent(model);
        view.repaint();
        view.revalidate();
        view.statusLabel.setText("蓝方回合");
        view.statusLabel.setForeground(Color.BLUE);
        currentPlayer = PlayerColor.BLUE;
        selectedPoint = null;
        winner = null;
        model.redDead = new ArrayList<>();
        model.blueDead = new ArrayList<>();
        model.ways = new ArrayList<>();
        CountdownTimer.time = 30;
        swapTime();
    }
    public void regret(){
        model.ways.remove(model.ways.size() - 1);
        ArrayList<Way> newways = model.ways;
        restart();
        for (int i = 0; i < newways.size() ; i++) {
            Way step = newways.get(i);
            ChessboardPoint src = step.src;
            ChessboardPoint dest = step.dest;
            int casee = 0;
            if (step.captured == null){
                casee = 1;
                cancapture(src, dest, casee);
            }
            else {
                casee = 2;
                cancapture(src, dest, casee);
            }
        }
    }
    public void playback(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Way> newways = model.ways;
                restart();
                for (int i = 0; i < newways.size() ; i++) {
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    Way step = newways.get(i);
                    ChessboardPoint src = step.src;
                    ChessboardPoint dest = step.dest;
                    int casee = 0;
                    if (step.captured == null){
                        casee = 1;
                        cancapture(src, dest, casee);
                    }
                    else {
                        casee = 2;
                        cancapture(src, dest, casee);
                    }
                }
            }
        });
        thread.start();
    }
    public void cancapture(ChessboardPoint src, ChessboardPoint dest, int a){
        switch (a){
            case 1:
                model.moveChessPiece(src, dest);
                view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                selectedPoint = null;
                swapColor();
                view.repaint();
                break;
            case 2:
                model.captureChessPiece(src, dest);
                view.removeChessComponentAtGrid(dest);
                view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                selectedPoint = null;
                swapColor();
                view.repaint();
                view.revalidate();
                break;
            default:
                break;
        }
    }
    // click an empty cell
    public void saveGame(String fileName) {
        String location = "save\\" + fileName + ".txt";
        File file = new File(location);

        try {
            if(file.exists()){     // 若文档存在，询问是否覆盖
                int n = JOptionPane.showConfirmDialog(view, "存档已存在，是否覆盖?", "", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    file.delete();
                }
            }

            // 创建文档
            FileWriter fileWriter = new FileWriter(location,true);

            fileWriter.write(model.ways.size() + "");
            fileWriter.write("\n");

            for (int i = 0; i <model.ways.size(); i++){
                fileWriter.write(model.ways.get(i).toString());
                fileWriter.write("\n");
            }

            fileWriter.write(currentPlayer == PlayerColor.BLUE ? "b" : "r");
            fileWriter.write("\n");

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 7; j++) {
                    ChessPiece chess = model.getGrid()[i][j].getPiece();
                    fileWriter.write(animal2Str(chess) + " ");
                }
                fileWriter.write("\n");
            }

            fileWriter.close();
            System.out.println("Save Done");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private static String animal2Str(ChessPiece chess){
        if (chess == null) return "+";
        else if (chess.getName().equals("Elephant")) return "E";
        else if (chess.getName().equals("Lion")) return "L";
        else if (chess.getName().equals("Tiger")) return "T";
        else if (chess.getName().equals("Leopard")) return "l";
        else if (chess.getName().equals("Wolf")) return "w";
        else if (chess.getName().equals("Dog")) return "d";
        else if (chess.getName().equals("Cat")) return "c";
        else if (chess.getName().equals("Rat")) return "r";
        else return "";
    }



    public boolean loadGame(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("save"));
        chooser.showOpenDialog(view);
        File file = chooser.getSelectedFile();

        if (!file.getName().endsWith(".txt")){
            JOptionPane.showMessageDialog(null,   "文件后缀错误","检测到非法修改存档\n已重新开始",
                   JOptionPane.ERROR_MESSAGE);
            //System.out.println("检测到非法修改存档！重新开始游戏");
            //System.out.println("后缀错误");
            restart();
            return false;
        }

        try {
            String temp;
            ArrayList<String> readList = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));

            while((temp = reader.readLine()) != null && !"".equals(temp)){
                readList.add(temp);
                //System.out.println(temp);
            }

            int num = Integer.parseInt(readList.remove(0));
//            System.out.println(num);
//            for (int i = 0; i < readList.size(); i++) {
//                System.out.println(readList.get(i));
//            }
            for (int i = 0; i <= num; i++) {
                String str = readList.get(i);
                if (i % 2 == 0 && str.charAt(0) != 'b'){
                    //System.out.println(str);
                    JOptionPane.showMessageDialog(null,  "行棋方错误","检测到非法修改存档\n已重新开始",
                            JOptionPane.ERROR_MESSAGE);
                    restart();
                    return false;
                }
                if (i % 2 == 1 && str.charAt(0) != 'r'){
                    //System.out.println(str);
                    JOptionPane.showMessageDialog(null, "行棋方错误","检测到非法修改存档\n已重新开始",
                             JOptionPane.ERROR_MESSAGE);
                    restart();
                    return false;
                }
            }

            try {
                for (int i = num + 1; i < num + 10; i++) {
                    boolean b = true;
                    String[] chess= readList.get(i).split(" ");
                    if (chess.length != 7){
                        JOptionPane.showMessageDialog(null, "棋盘错误，并非7*9","检测到非法修改存档\n已重新开始"
                                , JOptionPane.ERROR_MESSAGE);
                        restart();
                        return false;
                    }
                    if (!checkName(chess)) b = false;
                    if (!b){
                        JOptionPane.showMessageDialog(null, "棋子错误", "检测到非法修改存档\n已重新开始",
                                JOptionPane.ERROR_MESSAGE);
                        restart();
                        return false;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "棋盘错误，并非7*9",
                        "检测到非法修改存档\n已重新开始", JOptionPane.ERROR_MESSAGE);
                restart();
                return false;
            }

            restart();
            for (int i = 0; i < num; i++) {
                String[] info = readList.get(i).split(" ");
                ChessboardPoint src = new ChessboardPoint(Integer.parseInt(info[1].charAt(1) + ""),
                        Integer.parseInt(info[1].charAt(3) + ""));
                ChessboardPoint dest = new ChessboardPoint(Integer.parseInt(info[2].charAt(1) + ""),
                        Integer.parseInt(info[2].charAt(3) + ""));
                boolean isCapture = !info[3].equals("null");

                if (!isCapture){
                    if (!model.isValidMove(src, dest)){
                        JOptionPane.showMessageDialog(null, "行棋步骤错误","检测到非法修改存档\n已重新开始",
                                 JOptionPane.ERROR_MESSAGE);
                        restart();
                        return false;
                    }
                    model.moveChessPiece(src, dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    selectedPoint = null;
                    swapColor();
                    view.repaint();

                } else {
                    if (!model.isValidCapture(src, dest)){
                        JOptionPane.showMessageDialog(null, "行棋步骤错误", "检测到非法修改存档\n已重新开始",
                                JOptionPane.ERROR_MESSAGE);
                        restart();
                        return false;
                    }
                    model.captureChessPiece(src, dest);
                    view.removeChessComponentAtGrid(dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    swapColor();
                    view.repaint();
                    view.revalidate();
                }
            }

        } catch (Exception ex){
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(null,   "错误", "无此存档\n已重新开始",
                  JOptionPane.ERROR_MESSAGE);
            restart();
        }
        return true;
    }
    private static boolean checkName(String[] chess){
        for (int i = 0; i < chess.length; i++) {
            if (!chess[i].equals("E") && !chess[i].equals("L") && !chess[i].equals("T") && !chess[i].equals("l")
                    && !chess[i].equals("w") && !chess[i].equals("d") && !chess[i].equals("c") && !chess[i].equals("r")
                    && !chess[i].equals("+")){
                return false;
            }
        }
        return true;
    }
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            setCanStepFalse();
            canStepPoints = null;
            view.repaint();
            swapColor();
            swapTime();
            component.revalidate();
            Win();
            if (winner != null){
                show();
                restart();
                return;
            }
            if (AIPlaying){
                AI();
            }
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ElephantChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                canStepPoints = getCanStepPoints(point);
                selectedPoint = point;
                component.setSelected(true);
                component.revalidate();
                component.repaint();
                view.repaint();
                view.revalidate();
            }
        } else if (selectedPoint.equals(point)) {
            canStepPoints = null;
            selectedPoint = null;
            setCanStepFalse();
            component.setSelected(false);
            component.repaint();
            component.revalidate();
            view.repaint();
            view.revalidate();
        }
        else if (model.isValidCapture(selectedPoint, point)){
            model.captureChessPiece(selectedPoint, point);
            view.removeChessComponentAtGrid(point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            setCanStepFalse();
            swapColor();
            swapTime();
            view.repaint();
            view.revalidate();
            component.revalidate();
        }
        Win();
        if (winner != null){
            show();
            restart();
            return;
        }if (AIPlaying){
            AI();
        }
    }
    public void onPlayerClickChessPiece(ChessboardPoint point, AnimalChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                canStepPoints = getCanStepPoints(point);
                selectedPoint = point;
                component.setSelected(true);
                component.revalidate();
                component.repaint();
                view.repaint();
                view.revalidate();
            }
        } else if (selectedPoint.equals(point)) {
            canStepPoints = null;
            selectedPoint = null;
            setCanStepFalse();
            component.setSelected(false);
            component.repaint();
            component.revalidate();
            view.repaint();
            view.revalidate();
        }
        else if (model.isValidCapture(selectedPoint, point)){
            model.captureChessPiece(selectedPoint, point);
            view.removeChessComponentAtGrid(point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            setCanStepFalse();
            swapColor();
            view.repaint();
            view.revalidate();
            component.revalidate();
        }
        Win();
        if (winner != null){
            show();
            restart();
            return;
        }
    }
    public void AI() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (Exception e){
                    e.printStackTrace();
                }

                ArrayList<ChessboardPoint> canMove = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 7; j++) {
                        if (model.getGrid()[i][j].getPiece() != null && model.getGrid()[i][j].getPiece().getOwner() == currentPlayer){
                            ArrayList<ChessboardPoint> list = getCanStepPoints(new ChessboardPoint(i, j));
                            if (list.size() != 0) canMove.add(new ChessboardPoint(i, j));
                        }
                    }
                }

                int size = canMove.size();
                Random random = new Random();
                int index = random.nextInt(size);
                ChessboardPoint src = canMove.get(index);

                ArrayList<ChessboardPoint> list = getCanStepPoints(src);
                size = list.size();
                index = random.nextInt(size);
                ChessboardPoint dest = list.get(index);


                if (model.getChessPieceAt(dest) == null){
                    model.moveChessPiece(src, dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                } else {
                    model.captureChessPiece(src, dest);
                    view.removeChessComponentAtGrid(dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                }
                canStepPoints = null;
                setCanStepFalse();
                swapColor();
                view.repaint();
                view.gridComponents[dest.getRow()][dest.getCol()].revalidate();
                Win();
                if (winner != null){
                    show();
                    restart();
                }
            }
        });
        thread.start();
    }

    public ChessboardPoint[] aIGetPoint(){
        ArrayList<ChessboardPoint> canMove = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (model.getGrid()[i][j].getPiece() != null && model.getGrid()[i][j].getPiece().getOwner() == currentPlayer){
                    ArrayList<ChessboardPoint> list = getCanStepPoints(new ChessboardPoint(i, j));
                    if (list.size() != 0) canMove.add(new ChessboardPoint(i, j));
                }
            }
        }

        int size = canMove.size();
        Random random = new Random();
        int index = random.nextInt(size);
        ChessboardPoint src = canMove.get(index);

        ArrayList<ChessboardPoint> list = getCanStepPoints(src);
        size = list.size();
        index = random.nextInt(size);
        ChessboardPoint dest = list.get(index);

        return new ChessboardPoint[]{src, dest};
    }

    public void setCanStepFalse() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                view.gridComponents[i][j].canStep = false;
            }
        }
    }
    public ArrayList<ChessboardPoint> getCanStepPoints(ChessboardPoint src) {
        ArrayList<ChessboardPoint> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                ChessboardPoint dest = new ChessboardPoint(i, j);
                if (model.isValidMove(src, dest)){
                    view.gridComponents[i][j].canStep = true;
                    list.add(dest);
                }
                if (model.isValidMove(src, dest)){
                    view.gridComponents[i][j].canStep = true;
                    list.add(dest);
                }
            }
        }
        return list;
    }
}
