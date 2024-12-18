/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package text_editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class KeyHandler implements KeyListener{

    TextEditor gui;

    public KeyHandler(TextEditor gui) {
        this.gui = gui;
    }
    
    public void keyTyped(KeyEvent ke){
        
    }
    public void keyPressed(KeyEvent ke){
        
    }    
    public void keyReleased(KeyEvent ke){
        
        
    }
}