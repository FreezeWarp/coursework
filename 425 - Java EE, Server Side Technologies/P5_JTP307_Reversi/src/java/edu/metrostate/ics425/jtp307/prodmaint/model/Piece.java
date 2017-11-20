/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.metrostate.ics425.jtp307.prodmaint.model;

/**
 *
 * @author joseph
 */
public enum Piece {
    BLANK, WHITE, BLACK;
    
    private Piece opposite;
    
    static {
        BLANK.opposite = BLANK;
        WHITE.opposite = BLACK;
        BLACK.opposite = WHITE;
    }
    
    public Piece opposite() {
        return this.opposite;
    }
}
