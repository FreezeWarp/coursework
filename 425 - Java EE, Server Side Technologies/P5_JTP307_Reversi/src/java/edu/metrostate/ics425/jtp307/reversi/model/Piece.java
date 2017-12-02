package edu.metrostate.ics425.jtp307.reversi.model;

/**
 * A basic enum for game pieces, and frequently used as a stand-in for players.
 * Has three values: BLACK, WHITE, and BLANK.
 * 
 * @author Joseph T. Parsons
 */
public enum Piece {
    BLANK, WHITE, BLACK;
    
    /** The piece opposite the current piece (if current piece is BLACK, then WHITE, and vice-versa. **/
    private Piece opposite;
    
    static {
        BLANK.opposite = BLANK;
        WHITE.opposite = BLACK;
        BLACK.opposite = WHITE;
    }
    
    /**
     * @return {@link Piece#opposite}
     */
    public Piece opposite() {
        return this.opposite;
    }
}
