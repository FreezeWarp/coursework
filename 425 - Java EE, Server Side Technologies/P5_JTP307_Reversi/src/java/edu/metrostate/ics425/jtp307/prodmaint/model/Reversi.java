/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.metrostate.ics425.jtp307.prodmaint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This models Reversi, implementing move checking extended from the base {@link Board} class.
 * 
 * @author joseph
 */
public class Reversi extends Board implements Serializable {
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
    
    public Reversi() {
        this(10, 10);
    }
    
    public Reversi(int width, int height) {
        super(width, height);
        
        /* Place the initial pieces on the board. */
        this.setPiece(Piece.WHITE, width / 2 - 1, height / 2 - 1);
        this.setPiece(Piece.BLACK, width / 2,     height / 2 - 1);
        this.setPiece(Piece.BLACK, width / 2 - 1, height / 2    );
        this.setPiece(Piece.WHITE, width / 2,     height / 2    );
    }
    
    public boolean isLegalLine(Piece endingWith, int startX, int startY, int shiftX, int shiftY) {
        return this.isInsideBoard(startX + shiftX, startY + shiftY) // Target piece must be legal.
               && this.getPiece(startX, startY) == endingWith.opposite() // Our starting piece must be opposite our ending piece.
               && ( // The next piece must either be...
                    this.getPiece(startX + shiftX, startY + shiftY) == endingWith // The ending piece.
                    || this.isLegalLine(endingWith, startX + shiftX, startY + shiftY, shiftX, shiftY) // Or a row of the opposite piece ending with the ending piece.
                );
    }
    
    public Collection<Integer[]> getLegalLines(Piece piece, int posX, int posY) {
        Collection<Integer[]> matchedLines = new ArrayList<>();
        
        for (Integer[] shiftPair : this.VALID_SHIFTS) {
            if (this.isLegalLine(piece, posX + shiftPair[0], posY + shiftPair[1], shiftPair[0], shiftPair[1])) {
                matchedLines.add(shiftPair);
            }
        }
        
        return matchedLines;
    }
    
    public boolean setPiece(int posX, int posY) {
        Piece piece = this.getLastPiece().opposite();
        
        if (this.getPiece(posX, posY) == Piece.BLANK) {
            Collection<Integer[]> legalLines = getLegalLines(piece, posX, posY);

            if (!legalLines.isEmpty()) {
                for (Integer[] shiftPair : legalLines) {
                    System.out.println("Legal Line: " + shiftPair[0] + "," + shiftPair[1]);


                    this.setPiece(piece, posX, posY);

                    int posXLine = posX;
                    int posYLine = posY;

                    while (this.getPiece(posXLine += shiftPair[0], posYLine += shiftPair[1]) != piece) {
                        this.setPiece(piece, posXLine, posYLine);
                    }
                }
                System.out.println();

                return true;
            }
        }
        
        return false;
    }
    
    public String toString() {
        return "[Reversi Game; Width = " + this.width + "; Height = " + this.height + "]";
    }
    
}
