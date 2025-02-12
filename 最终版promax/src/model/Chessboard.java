package model;

import view.ChessGameFrame;

import java.util.ArrayList;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
   // public ChessGameFrame statusLabel;
    private Cell[][] grid;
    public ArrayList<ChessPiece> blueDead;
    public ArrayList<ChessPiece> redDead;
    public  ArrayList<Way> ways;

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19
        blueDead = new ArrayList<>();
        redDead = new ArrayList<>();
        ways = new ArrayList<>();
        initGrid();
        initPieces();
    }

    public void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public void initPieces() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].removePiece();
            }
        }
        grid[0][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion",7));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger",6));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog",3));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat",2));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat",1));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard",5));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf",4));
        grid[2][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.RED, "Lion",7));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.RED, "Tiger",6));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.RED, "Dog",3));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.RED, "Cat",2));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.RED, "Rat",1));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.RED, "Leopard",5));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.RED, "Wolf",4));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.RED, "Elephant",8));
    }
    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    //��ȥ����

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }


    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
        if (Trap(point , chessPiece.getOwner())){
            chessPiece.setRank(0);
        }
        else {
            if (chessPiece.getName().equals("Elephant")){
                chessPiece.setRank(8);
            }if (chessPiece.getName().equals("lion")){
                chessPiece.setRank(7);
            }if (chessPiece.getName().equals("Tiger")){
                chessPiece.setRank(6);
            }if (chessPiece.getName().equals("Leopard")){
                chessPiece.setRank(5);
            }if (chessPiece.getName().equals("Wolf")){
                chessPiece.setRank(4);
            }if (chessPiece.getName().equals("Dog")){
                chessPiece.setRank(3);
            }if (chessPiece.getName().equals("Cat")){
                chessPiece.setRank(2);
            }if (chessPiece.getName().equals("Rat")){
                chessPiece.setRank(1);
            }
        }
    }



    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        ChessPiece chessPiece=removeChessPiece(src);
        setChessPiece(dest, chessPiece);
        ways.add(new Way(src,dest, chessPiece.getOwner()));

    }

    //

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        ChessPiece eater = removeChessPiece(src);
        ChessPiece beeater = removeChessPiece(dest);
        setChessPiece(dest, eater);
        if (eater.getOwner() == PlayerColor.BLUE){
            redDead.add(beeater);
        }
        else {
            blueDead.add(beeater);
        }
        ways.add(new Way(src, dest, eater.getOwner(), beeater));
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    //һ����Ч���˶���1.���Ⱦ������1�� 2.������ӡ�ʨ��Ծ�ӣ�3.�������ﲻ�ܽ��ӡ�����Ծ�ӣ�4.���ܽ��Լ���Ѩ


    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece movepiece = getChessPieceAt(src);
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        if (wDens(dest, movepiece.getOwner())) {
            return false;
        }
        boolean distance = calculateDistance(src, dest) == 1;
        if (movepiece.getName().equals("Rat")){
            return distance;
        }
        if (movepiece.getName().equals("Lion")){
            return distance && !River(dest) || jumpRiver(src, dest);
        }
        if (movepiece.getName().equals("Tiger")){
            return distance && !River(dest) || jumpRiver(src, dest);
        }
        if (movepiece.getName().equals("Elephant")){
            return distance && !River(dest);
        }
        if (movepiece.getName().equals("Leopard")){
            return distance && !River(dest);
        }
        if (movepiece.getName().equals("Wolf")){
            return distance && !River(dest);
        }
        if (movepiece.getName().equals("Dog")){
            return distance && !River(dest);
        }
        if (movepiece.getName().equals("Cat")){
            return distance && !River(dest);
        }
        return false;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece eater = getChessPieceAt(src);
        ChessPiece dead = getChessPieceAt(dest);
        if (eater == null || dead == null){
            return false;
        }
        if (eater.getOwner() == dead.getOwner()){
            return false;
        }
        boolean b = calculateDistance(src, dest) == 1;
        if (eater.getName().equals("Elephant")){
            return  b && !River(dest) && dead.getRank() != 1;
        }
        if (eater.getName().equals("Lion")){
            return ((b && !River(dest)) || jumpRiver(src, dest)) && dead.getRank() <= 7;
        }
        if (eater.getName().equals("Tiger")){
            return ((b && !River(dest)) || jumpRiver(src, dest)) && dead.getRank() <= 6;
        }
        if (eater.getName().equals("Leopard")){
            return b && !River(dest) && dead.getRank() <= 5;
        }
        if (eater.getName().equals("Wolf")){
            return b && !River(dest) && dead.getRank() <= 4;
        }
        if (eater.getName().equals("Dog")){
            return b && !River(dest) && dead.getRank() <= 3;
        }
        if (eater.getName().equals("Cat")){
            return b && !River(dest) && dead.getRank() <= 2;
        }
        if (eater.getName().equals("Rat")){
            return b && (dead.getRank() <= 1 || dead.getRank() == 8) && !(River(src) && !River(dest));
        }
        return false;
    }
    private boolean River(ChessboardPoint point){
        if (point.getRow() >= 3 && point.getRow() <= 5 &&
                (point.getCol() >= 1 && point.getCol() <=2 || point.getCol() >= 4 && point.getCol() <= 5)){
            return true;
        }
        else {
            return false;
        }
    }
    private boolean wDens(ChessboardPoint point, PlayerColor color){
        if (color == PlayerColor.RED){
            if (point.getRow() == 8 && point.getCol() == 3){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if( point.getRow() == 0 && point.getCol() == 3){
                return true;
            }
            else {
                return false;
            }
        }
    }
    public boolean dDens(ChessboardPoint point, PlayerColor color){
        if (color == PlayerColor.BLUE){
            if (point.getRow() == 8 && point.getCol() == 3){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if( point.getRow() == 0 && point.getCol() == 3){
                return true;
            }
            else {
                return false;
            }
        }
    }
    private boolean Trap(ChessboardPoint point, PlayerColor color){
        if (color == PlayerColor.RED){
            if( (point.getRow() == 1 && point.getCol() == 3)
                    || (point.getRow() == 0 && point.getCol() == 2)
                    || (point.getRow() == 0 && point.getCol() == 4)){
                return true;
            }
            else {
                return false;
            }
        } else {
            if( (point.getRow() == 7 && point.getCol() == 3)
                    || (point.getRow() == 8 && point.getCol() == 2)
                    || (point.getRow() == 8 && point.getCol() == 4)){
                return true;
            }
            else {
                return false;
            }
        }
    }
    private boolean jumpRiver(ChessboardPoint src, ChessboardPoint dest){
        if (src.getRow() == 3 && src.getCol() == 0 && dest.getRow() == 3 && dest.getCol() == 3){
            return ratIntheRiverforrow(src,dest);
        }
        if (src.getRow() == 3 && src.getCol() == 3 && dest.getRow() == 3 && dest.getCol() == 0){
            return ratIntheRiverforrow(src,dest);
        }
        if (src.getRow() == 4 && src.getCol() == 0 && dest.getRow() == 4 && dest.getCol() == 3){
            return ratIntheRiverforrow(src,dest);
        }
        if (src.getRow() == 4 && src.getCol() == 3 && dest.getRow() == 4 && dest.getCol() == 0){
            return ratIntheRiverforrow(src,dest);
        }
        if (src.getRow() == 5 && src.getCol() == 0 && dest.getRow() == 5 && dest.getCol() == 3){
            return ratIntheRiverforrow(src,dest);
        }
        if (src.getRow() == 5 && src.getCol() == 3 && dest.getRow() == 5 && dest.getCol() == 0){
            return ratIntheRiverforrow(src,dest);
        }
        if (src.getRow() == 3 && src.getCol() == 3 && dest.getRow() == 3 && dest.getCol() == 6){
            return ratIntheRiverforrow(src,dest);
        }
        if (src.getRow() == 3 && src.getCol() == 6 && dest.getRow() == 3 && dest.getCol() == 3){
            return ratIntheRiverforrow(src,dest);
        }
        if (src.getRow() == 4 && src.getCol() == 3 && dest.getRow() == 4 && dest.getCol() == 6){
            return ratIntheRiverforrow(src,dest);
        }
        if (src.getRow() == 4 && src.getCol() == 6 && dest.getRow() == 4 && dest.getCol() == 3){
            return ratIntheRiverforrow(src,dest);
        }
        if (src.getRow() == 5 && src.getCol() == 3 && dest.getRow() == 5 && dest.getCol() == 6){
            return ratIntheRiverforrow(src,dest);
        }
        if (src.getRow() == 5 && src.getCol() == 6 && dest.getRow() == 5 && dest.getCol() == 3){
            return ratIntheRiverforrow(src,dest);
        }
        /////////////////////////////////////////////////////////////////////////////////////////

        if (src.getRow() == 2 && src.getCol() == 1 && dest.getRow() == 6 && dest.getCol() == 1){
            return ratIntheRiverforcol(src);
        }
        if (src.getRow() == 6 && src.getCol() == 1 && dest.getRow() == 2 && dest.getCol() == 1){
            return ratIntheRiverforcol(src);
        }
        if (src.getRow() == 2 && src.getCol() == 2 && dest.getRow() == 6 && dest.getCol() == 2){
            return ratIntheRiverforcol(src);
        }
        if (src.getRow() == 6 && src.getCol() == 2 && dest.getRow() == 2 && dest.getCol() == 2){
            return ratIntheRiverforcol(src);
        }
        if (src.getRow() == 2 && src.getCol() == 4 && dest.getRow() == 6 && dest.getCol() == 4){
            return ratIntheRiverforcol(src);
        }
        if (src.getRow() == 6 && src.getCol() == 4 && dest.getRow() == 2 && dest.getCol() == 4){
            return ratIntheRiverforcol(src);
        }
        if (src.getRow() == 2 && src.getCol() == 5 && dest.getRow() == 6 && dest.getCol() == 5){
            return ratIntheRiverforcol(src);
        }
        if (src.getRow() == 6 && src.getCol() == 5 && dest.getRow() == 2 && dest.getCol() == 5){
            return ratIntheRiverforcol(src);
        }
        return false;
    }

    private boolean ratIntheRiverforrow(ChessboardPoint src, ChessboardPoint dest){
        if(src.getCol() == 0){
            if (getChessPieceAt(new ChessboardPoint(src.getRow(), 1)) == null
                    && getChessPieceAt(new ChessboardPoint(src.getRow(), 2)) == null){
                return true;
            }
        }
        if (src.getCol() == 6){
            if (getChessPieceAt(new ChessboardPoint(src.getRow(), 4)) == null
                    && getChessPieceAt(new ChessboardPoint(src.getRow(), 5)) == null){
                return true;
            }
        }
        if (src.getCol() == 3){
            if (dest.getCol() == 0) {
                if (getChessPieceAt(new ChessboardPoint(src.getRow(), 1)) == null
                        && getChessPieceAt(new ChessboardPoint(src.getRow(), 2)) == null) {
                    return true;
                }
            }
            if (dest.getCol() == 6){
                if (getChessPieceAt(new ChessboardPoint(src.getRow(), 4)) == null
                        && getChessPieceAt(new ChessboardPoint(src.getRow(), 5)) == null) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean ratIntheRiverforcol(ChessboardPoint src){
        if(src.getRow() == 2){
            if (getChessPieceAt(new ChessboardPoint(3, src.getCol())) == null
                    && getChessPieceAt(new ChessboardPoint(4, src.getCol())) == null
                    && getChessPieceAt(new ChessboardPoint(5, src.getCol())) == null){
                return true;
            }
        }
        if(src.getRow() == 6){
            if (getChessPieceAt(new ChessboardPoint(3, src.getCol())) == null
                    && getChessPieceAt(new ChessboardPoint(4, src.getCol())) == null
                    && getChessPieceAt(new ChessboardPoint(5, src.getCol())) == null){
                return true;
            }
        }
        return false;
    }
}
