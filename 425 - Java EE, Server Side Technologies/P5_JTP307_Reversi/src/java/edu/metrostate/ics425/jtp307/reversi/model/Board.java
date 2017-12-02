package edu.metrostate.ics425.jtp307.reversi.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple game board with a fixed width, fixed height, and containing pieces modelled by {@link Piece} -- that is, which may be one of two states, white or black.
 * 
 * @author Joseph T. Parsons
 */
public class Board implements Serializable {
    /** The total number of columns on the board. */
    private int width;
    
    /** The total number of rows on the board. */
    private int height;
    
    /** The pieces on the board, grouped into rows and then seperated as columns. */
    private Piece[][] pieces;
    
    /** The colour of the last piece that was played. If a player's turn is skipped, this will be the skipped player.*/
    private Piece lastPiece;
    
    /** The number pieces of a certain type on the board. */
    private Map<Piece, Integer> pieceCount = new HashMap<>();

    
    
    /**
     * Create a board with a default width and height of 8.
     */
    public Board() {
        this(8, 8);
    }
    
    
    /**
     * Create a board with a given width and height, and sets all pieces to {@link Piece}.BLANK.
     * 
     * @param width The width (number of columns) of the board.
     * @param height The height (number of rows) of the board.
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        
        this.pieces = new Piece[height][width];
    
        // Set all pieces to Piece.BLANK.
        for(int row = 0; row < this.pieces.length; row++) {
            for (int column = 0; column < this.pieces[0].length; column++) {
                this.setPiece(Piece.BLANK, column, row);
            }
        }
    }
    
    
    
    /**
     * @return {@link Board#width}.
     */
    public int getWidth() {
        return this.width;
    }
    
    
    /**
     * @return {@link Board#height}.
     */    
    public int getHeight() {
        return this.height;
    }
    
    
    /**
     * @return {@link Board#pieces}.
     */
    public Piece[][] getPieces() {
        return this.pieces;
    }
    
    
    /**
     * @param xPos The x offset, 0-indexed.
     * @param yPos The y offset, 0-indexed.
     * 
     * @return The piece currently placed at the location.
     */
    public Piece getPiece(int xPos, int yPos) {
        if (!this.isInsideBoard(xPos, yPos)) {
            return null;
        }
        
        else {
           return this.pieces[yPos][xPos];
        }
    }
    
    
    /**
     * @return {@link Board#lastPiece}
     */
    public Piece getLastPiece() {
        return this.lastPiece;
    }

    
    /**
     * @param piece The piece type to search for.
     * 
     * @return The number of matching pieces on the board.
     */    
    public int getPieceCount(Piece piece) {
        return this.pieceCount.get(piece);
    }
    
    
    /**
     * @param xPos The x offset.
     * @param yPos The y offset.
     * 
     * @return True if the given offsets are a valid location on the board, false otherwise.
     */
    public boolean isInsideBoard(int xPos, int yPos) {
        return xPos >= 0
                && yPos >= 0
                && xPos < this.getWidth()
                && yPos < this.getHeight();
    }
    
    
    /**
     * Get the player who's turn it is to place pieces.
     * 
     * @return Piece
     */
    public Piece getCurrentPlayer() {
        return this.lastPiece.opposite();
    }
    
    
    /**
     * Place the given piece in the given position.
     * 
     * @param piece The piece to set.
     * @param xPos The x offset, 0-indexed.
     * @param yPos The y offset, 0-indexed.
     */
    protected final void setPiece(Piece piece, int xPos, int yPos) {
        // Decrement the piece count from removing the old piece.
        Piece oldPiece = this.getPiece(xPos, yPos);
        if (oldPiece != null) {
            this.pieceCount.put(oldPiece, this.pieceCount.get(oldPiece) - 1);
        }
        
        // Update the pieces array.
        this.pieces[yPos][xPos] = piece;
        
        // Update the last piece.
        this.lastPiece = piece;
        
        // Increment new piece count.
        this.pieceCount.put(piece, this.pieceCount.containsKey(piece) ? this.pieceCount.get(piece) + 1 : 1);
    }

    
    /**
     * Skip the current player's turn.
     */
    protected void skipTurn() {
        this.lastPiece = this.lastPiece.opposite();
    }
    
    
    /**
     * @return String containing very basic information about the game board.
     */
    public String toString() {
        String string = "[Game Board; Width = " + this.width + "; Height = " + this.height + ";";
        
        for (Piece[] row : this.getPieces()) {
            string += System.getProperty("line.separator") + "    |";
            for (Piece piece : row) {
                string += piece.name() + "|";
            }
        }
        
        string += System.getProperty("line.separator") + "]";
        
        return string;
    }
}