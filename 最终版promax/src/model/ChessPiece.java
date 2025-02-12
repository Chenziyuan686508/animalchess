package model;


public class ChessPiece {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        if (name.equals("Elephant")){
            if (target.rank != 1){
                return true;
            }
            else {
                return false;
            }
        }
        else if (name.equals("Rat")){
            if (target.rank == 8 || target.rank == 0){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if (target.rank <= rank){
                return true;
            }
            else {
                return false;
            }
        }
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }
}
