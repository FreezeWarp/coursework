package edu.metrostate.ics425.jtp307.reversi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This models Reversi, implementing move checking extended from the base {@link Board} class.
 * 
 * @author Joseph T. Parsons
 */
public class Reversi extends Board implements Serializable {
    /** Whether the game has been forced to end by a player. If set, no moves are considered legal, and this class should be readonly. */
    private boolean hasGivenUp = false;
    
    /** Offsets that form directional lines. All possible lines in a square grid are included. */
    private final Integer VALID_SHIFTS[][] = {
        {-1, -1}, // Up and left
        {0, -1}, // Up
        {1, -1}, // Up and right
        
        {-1, 0}, // Left
        {1, 0}, // Right
        
        {-1, 1}, // Down and left
        {0, 1}, // Down
        {1, 1}, // Down and right
    };
    
    
    
    /**
     * Create a Reversi board with the default width and height, 8.
     */
    public Reversi() {
        this(8, 8);
    }
    
    
    /**
     * Create a Reversi board with a given width and height, and place the four starting pieces in the middle of the board.
     * 
     * @param width The width (number of columns) of the board.
     * @param height The height (number of rows) of the board.
     */
    public Reversi(int width, int height) {
        super(width, height);
        
        /* Place the initial pieces on the board. */
        this.setPiece(Piece.WHITE, width / 2 - 1, height / 2 - 1);
        this.setPiece(Piece.BLACK, width / 2,     height / 2 - 1);
        this.setPiece(Piece.BLACK, width / 2 - 1, height / 2    );
        this.setPiece(Piece.WHITE, width / 2,     height / 2    );
    }
    
    
    
    /**
     * @param piece The piece type to search for.
     * 
     * @return The number of matching pieces on the board, or the number of squares on the board if the opposite piece has none. (Naturally, garbage input results in garbage output: if there are somehow no pieces on the board, this will return the number of squares on the board regardless of the input.)
     */
    @Override
    public int getPieceCount(Piece piece) {
        return super.getPieceCount(piece.opposite()) == 0
                ? this.getHeight() * this.getWidth()
                : super.getPieceCount(piece);
    }
    
    
    /**
     * @return The number of {@link Piece}.WHITE pieces on the board.
     */
    public int getWhitePieceCount() {
        return this.getPieceCount(Piece.WHITE);
    }
    
    /**
     * @return The number of {@link Piece}.BLACK pieces on the board.
     */    
    public int getBlackPieceCount() {
        return this.getPieceCount(Piece.BLACK);
    }
    
    
    /**
     * Determine whether, from the starting position, the line in the direction of [shiftX, shiftY] is formed of one colour of piece and then ended with the opposite color piece.
     * 
     * @param endingWith The piece that should be at the end of the line.
     * @param startX The X coordinate to start forming a line at.
     * @param startY The Y coordinate to start forming a line at.
     * @param shiftX The X shift to use when searching for strings of pieces.
     * @param shiftY The Y shift to use when searching for strings of pieces.
     * 
     * @return True iff a line of pieces matching the above criteria exists, false otherwise.
     */
    public boolean isLegalLine(Piece endingWith, int startX, int startY, int shiftX, int shiftY) {
        return !this.hasGivenUp // The game mustn't have been ended manually.
               && this.isInsideBoard(startX + shiftX, startY + shiftY) // The target piece must be legal.
               && this.getPiece(startX, startY) == endingWith.opposite() // Our starting piece must be opposite our ending piece.
               && ( // The next piece must either be...
                    this.getPiece(startX + shiftX, startY + shiftY) == endingWith // The ending piece.
                    || this.isLegalLine(endingWith, startX + shiftX, startY + shiftY, shiftX, shiftY) // Or a row of the opposite piece ending with the ending piece.
                );
    }
    
    
    /**
     * Get all legal lines, as defined by {@link Reversi#getLegalLines(edu.metrostate.ics425.jtp307.reversi.model.Piece, int, int)}, that exist for a given starting position and piece.
     * This will check the eight lines defined by {@link Reversi#VALID_SHIFTS}, and return some combination of them.
     * 
     * @param piece The piece being played in the [X, Y] position.
     * @param posX The X position of the piece being played, 0-indexed.
     * @param posY The Y position of the piece being played, 0-indexed.
     * 
     * @return Some combination of {@link Reversi#VALID_SHIFTS}.
     */
    public Collection<Integer[]> getLegalLines(Piece piece, int posX, int posY) {
        Collection<Integer[]> matchedLines = new ArrayList<>();
        
        for (Integer[] shiftPair : this.VALID_SHIFTS) {
            if (this.isLegalLine(piece, posX + shiftPair[0], posY + shiftPair[1], shiftPair[0], shiftPair[1])) {
                matchedLines.add(shiftPair);
            }
        }
        
        return matchedLines;
    }
    
    
    /**
     * Determine whether a piece can be legally played in a given position.
     * 
     * @param piece The piece being played in the [X, Y] position.
     * @param posX The X position of the piece being played, 0-indexed.
     * @param posY The Y position of the piece being played, 0-indexed.
     * 
     * @return True if the given piece placement is legal, false otherwise.
     */
    public boolean isLegalMove(Piece piece, int posX, int posY) {
        return this.getPiece(posX, posY) == Piece.BLANK
                && !this.getLegalLines(piece, posX, posY).isEmpty();
    }
    
    
    /**
     * Set the piece whose turn it is on the board at the given location.
     * Will not do anything if the move is illegal.
     * 
     * @param posX The x offset, 0-indexed.
     * @param posY The y offset, 0-indexed.
     * 
     * @return True if the attempted move is legal, false otherwise.
     */
    public boolean setPiece(int posX, int posY) {
        Piece piece = this.getCurrentPlayer();
        
        // Don't place on non-blank squares.
        if (this.getPiece(posX, posY) == Piece.BLANK) {
            Collection<Integer[]> legalLines = getLegalLines(piece, posX, posY);

            // Don't place if no legal lines are found.
            if (!legalLines.isEmpty()) {

                // Flip all pieces that are part of legal lines.
                for (Integer[] shiftPair : legalLines) {
                    // Set the piece itself.
                    this.setPiece(piece, posX, posY);

                    // Set all pieces in the line.
                    int posXLine = posX;
                    int posYLine = posY;

                    while (this.getPiece(posXLine += shiftPair[0], posYLine += shiftPair[1]) != piece) {
                        this.setPiece(piece, posXLine, posYLine);
                    }
                }
                
                // Skip the next player's turn if they have no available move.
                if (!this.isMoveAvailable(this.getCurrentPlayer())) {
                    this.skipTurn();
                } 

                // Return true to indicate that a piece has been placed.
                return true;

            }
        }
        
        return false;
    }
    
    
    /**
     * @param piece The color of a piece being played.
     * 
     * @return True if the given piece can be placed at some location on the board, false otherwise.
     */
    public boolean isMoveAvailable(Piece piece) {
        for (int row = 0; row < this.getHeight(); row++) {
            for (int column = 0; column < this.getWidth(); column++) {
                if (this.isLegalMove(piece, column, row)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    
    /**
     * @return If the game is over (because no legal moves are available).
     */
    public boolean isGameOver() {
        // When a piece is placed, the next player's turn is automatically skipped if no move is available. Thus, this will only be true when neither player has a legal move.
        return !this.isMoveAvailable(this.getCurrentPlayer());
    }
    
    
    /**
     * Set the give up flag, ending the game prematurely.
     */
    public void giveUp() {
        this.hasGivenUp = true;
    }
}
