/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.metrostate.ics425.jtp307.prodmaint.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * A simple game board with a fixed width, fixed height, and containing pieces modelled by {@link Piece} -- that is, which may be one of two states, white or black.
 * 
 * @author Joseph T. Parsons
 */
public class Board implements Serializable {
    /* The total number of columns on the board. */
    int width;
    
    /* The total number of rows on the board. */
    int height;
    
    Piece[][] pieces;
    
    private Piece lastPiece;
    
    public Board() {
        this(10, 10);
    }
    
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        
        this.pieces = new Piece[width][height];
        
        for(int row = 0; row < this.pieces.length; row++) {
            Arrays.fill(this.pieces[row], Piece.BLANK);
        }
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public Piece[][] getPieces() {
        return this.pieces;
    }
    
    public Piece getPiece(int xPos, int yPos) {
        return this.pieces[yPos][xPos];
    }
    
    public Piece getLastPiece() {
        return this.lastPiece;
    }
    
    protected void setPiece(Piece piece, int xPos, int yPos) {
        this.pieces[yPos][xPos] = piece;
        this.lastPiece = piece;
    }
    
    public boolean isInsideBoard(int xPos, int yPos) {
        return xPos >= 0
                && yPos >= 0
                && xPos < this.getWidth()
                && yPos < this.getHeight();
    }
}