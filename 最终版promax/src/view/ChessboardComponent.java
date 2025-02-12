package view;


import controller.GameController;
import model.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashSet;

import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JPanel {
    public   CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private int CHESS_SIZE;
    private  Set<ChessboardPoint> riverCell = new HashSet<>();
    private  Set<ChessboardPoint> trapCell = new HashSet<>();
    private  Set<ChessboardPoint> caveCell = new HashSet<>();
    public GameController gameController;
    public JLabel statusLabel;
    public JLabel timeLabel;
    public ChessboardComponent(int chessSize,JLabel statusLabel,JLabel timeLabel) {
        this.statusLabel=statusLabel;
        this.timeLabel = timeLabel;
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        initiateGridComponents();
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    if (chessPiece.getName().equals("Elephant")) {
                        gridComponents[i][j].add(new ElephantChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                    }
                    if (chessPiece.getName().equals("Lion")) {
                        gridComponents[i][j].add(new LionChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                    }
                    if (chessPiece.getName().equals("Tiger")) {
                        gridComponents[i][j].add(new TigerChesscomponent(chessPiece.getOwner(), CHESS_SIZE));
                    }
                    if (chessPiece.getName().equals("Leopard")) {
                        gridComponents[i][j].add(new LeopardChesscomponent(chessPiece.getOwner(), CHESS_SIZE));
                    }
                    if (chessPiece.getName().equals("Wolf")) {
                        gridComponents[i][j].add(new WolfChesscomponent(chessPiece.getOwner(), CHESS_SIZE));
                    }
                    if (chessPiece.getName().equals("Dog")) {
                        gridComponents[i][j].add(new DogChesscomponent(chessPiece.getOwner(), CHESS_SIZE));
                    }
                    if (chessPiece.getName().equals("Cat")) {
                        gridComponents[i][j].add(new CatChesscomponent(chessPiece.getOwner(), CHESS_SIZE));
                    }
                    if (chessPiece.getName().equals("Rat")) {
                        gridComponents[i][j].add(new RatChesscomponent(chessPiece.getOwner(), CHESS_SIZE));
                    }
                }
            }
        }
        //repaint();
    }
    public void removeChessComponent() {
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                try {
                    gridComponents[i][j].remove(0);
                } catch (Exception e){}
            }
        }
    }

    public void initiateGridComponents() {

        riverCell.add(new ChessboardPoint(3, 1));
        riverCell.add(new ChessboardPoint(3, 2));
        riverCell.add(new ChessboardPoint(4, 1));
        riverCell.add(new ChessboardPoint(4, 2));
        riverCell.add(new ChessboardPoint(5, 1));
        riverCell.add(new ChessboardPoint(5, 2));

        riverCell.add(new ChessboardPoint(3, 4));
        riverCell.add(new ChessboardPoint(3, 5));
        riverCell.add(new ChessboardPoint(4, 4));
        riverCell.add(new ChessboardPoint(4, 5));
        riverCell.add(new ChessboardPoint(5, 4));
        riverCell.add(new ChessboardPoint(5, 5));

        caveCell.add(new ChessboardPoint(0, 3));
        caveCell.add(new ChessboardPoint(8, 3));

        trapCell.add(new ChessboardPoint(0, 2));
        trapCell.add(new ChessboardPoint(0, 4));
        trapCell.add(new ChessboardPoint(1, 3));

        trapCell.add(new ChessboardPoint(8, 2));
        trapCell.add(new ChessboardPoint(8, 4));
        trapCell.add(new ChessboardPoint(7, 3));

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(calculatePoint(i, j),CHESS_SIZE,CellType.RIVER);
                    this.add(cell);
                }
                else if (trapCell.contains(temp)) {
                    cell = new CellComponent(calculatePoint(i, j),CHESS_SIZE,CellType.TRAP);
                    this.add(cell);
                }
                else if (caveCell.contains(temp)) {
                    cell = new CellComponent(calculatePoint(i,j),CHESS_SIZE,CellType.CAVE);
                    this.add(cell);
                }
                else {
                cell = new CellComponent( calculatePoint(i, j), CHESS_SIZE,CellType.GRASS);
                this.add(cell);
                }
                gridComponents[i][j] = cell;
               // this.repaint();
            }
        }
    }
        /*for (int i=0;i<CHESSBOARD_ROW_SIZE.getNum();i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent celll;

                gridComponents[i][j] = celll;
            }
        }*/


    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, AnimalChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public AnimalChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        AnimalChessComponent chess = (AnimalChessComponent) getGridComponentAt(point).getComponents()[0];
        /*LionChessComponent chess2 = (LionChessComponent) getGridComponentAt(point).getComponents()[0];
        RatChesscomponent chess3 = (RatChesscomponent) getGridComponentAt(point).getComponents()[0];
        CatChesscomponent chess4 = (CatChesscomponent) getGridComponentAt(point).getComponents()[0];
        TigerChesscomponent chess5 = (TigerChesscomponent) getGridComponentAt(point).getComponents()[0];
        LeopardChesscomponent chess6 = (LeopardChesscomponent) getGridComponentAt(point).getComponents()[0];
        DogChesscomponent chess7 = (DogChesscomponent) getGridComponentAt(point).getComponents()[0];
        WolfChesscomponent chess8 = (WolfChesscomponent) getGridComponentAt(point).getComponents()[0];*/
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        /*chess2.setSelected(false);
        chess3.setSelected(false);
        chess4.setSelected(false);
        chess5.setSelected(false);
        chess6.setSelected(false);
        chess7.setSelected(false);
        chess8.setSelected(false);*/
        return chess;
       /* return chess2;
        return chess3;
        return chess4;
        return chess5;
        return chess6;
        return chess7;
        return chess8;*/
    }


    private CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y / CHESS_SIZE + ", " + point.x / CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y / CHESS_SIZE, point.x / CHESS_SIZE);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            if (gameController.AIPlaying && gameController.currentPlayer == PlayerColor.RED) {
            } else{
                try {
                    File musicPath = new File("resource\\click.wav");

                    if (musicPath.exists()) {
                        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInput);
                        clip.start();
                        clip.loop(0);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
                if (clickedComponent.getComponentCount() == 0) {
                    System.out.print("None chess here and ");
                    gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
                } else {
                    System.out.print("One chess here and ");
                    gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (AnimalChessComponent) clickedComponent.getComponents()[0]);
                }
            /*
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (CatChesscomponent) clickedComponent.getComponents()[0]);}
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (RatChesscomponent) clickedComponent.getComponents()[0]);}
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (WolfChesscomponent) clickedComponent.getComponents()[0]);}
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else { gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (TigerChesscomponent) clickedComponent.getComponents()[0]);}
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else { gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (LionChessComponent)  clickedComponent.getComponents()[0]);}
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else { gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (DogChesscomponent) clickedComponent.getComponents()[0]);}
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {  gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (LeopardChesscomponent) clickedComponent.getComponents()[0]);}*/
            }
        }
        }
    @Override
    public void processMouseMotionEvent(MouseEvent e){
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.println("pressed");
        } else if (e.getID() == MouseEvent.MOUSE_DRAGGED){
            System.out.println("dragged");
        } else if (e.getID() == MouseEvent.MOUSE_MOVED){
            System.out.println("moved");
            CellComponent component = (CellComponent) getComponentAt(e.getX(), e.getY());
            setMouseAtFalse();
            component.mouseAt = true;
            component.repaint();
            component.revalidate();
            repaint();
            revalidate();
        } else if (e.getID() == MouseEvent.MOUSE_CLICKED){
            System.out.println("clicked");
        } else if (e.getID() == MouseEvent.MOUSE_ENTERED){
            System.out.println("entered");
        } else if (e.getID() == MouseEvent.MOUSE_EXITED){
            System.out.println("exited");
        } else if (e.getID() == MouseEvent.MOUSE_RELEASED){
            System.out.println("released");
        } else if (e.getID() == MouseEvent.MOUSE_WHEEL){
            System.out.println("wheel");
        }

    }

    private void setMouseAtFalse() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                gridComponents[i][j].mouseAt = false;
            }
        }
    }
    }



