/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package text_editor;

/**
 *
 * @author STAR PC
 */
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.undo.UndoManager;

public class TextEditor extends JFrame implements ActionListener{

 JTextArea textArea; // text area where we will add our text
 JTextField textField; // find textfield
 JTextField textField1; // replace textfield
 JScrollPane scrollPane;
 JLabel fontLabel;
 JLabel find;
 JLabel replace;
 JSpinner fontSizeSpinner;
 JButton fontColorButton;
 JButton Find; // find button
 JButton Findall; // find button
 JButton Replace; // replace button
 JButton Replaceall; // replace button
 JComboBox fontBox;
 Highlighter h;
 
 JMenuBar menuBar;
 JMenu fileMenu;
 JMenuItem openItem;
 JMenuItem newItem;
 JMenuItem saveItem;
 JMenuItem exitItem;
 
 JMenu Edit;
 JMenuItem undo;
 JMenuItem redo;
 JMenuItem copy;
 JMenuItem cut;
 JMenuItem paste;
 
 JMenu format;
 JMenuItem Bold;
 JMenuItem italic;
 
 JMenu Help;

//undo  -Redo functions
function_edit edit;
UndoManager um = new UndoManager();

KeyHandler key = new KeyHandler(this){
    
    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode()==KeyEvent.VK_Z){
            gui.um.undo();
        }
        else if(ke.getKeyCode()==KeyEvent.VK_Y){
            gui.um.redo();
        }
    }
};

 TextEditor(){
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.setTitle("Text Editor");
  this.setSize(830, 600);
  this.setLayout(new FlowLayout());
  this.setLocationRelativeTo(null);
  
  textArea = new JTextArea();
    
  textArea.addKeyListener(key);
  //editable text area
  textArea.getDocument().addUndoableEditListener( 
            new UndoableEditListener() {
      @Override
      public void undoableEditHappened(UndoableEditEvent uee) {
          um.addEdit(uee.getEdit());
      }
  });

  textArea.setLineWrap(true);
  textArea.setWrapStyleWord(true);
  textArea.setFont(new Font("Arial",Font.PLAIN,20));
  
  scrollPane = new JScrollPane(textArea);
  scrollPane.setPreferredSize(new Dimension(800,600));
  scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
  
  fontLabel = new JLabel("Font: ");
   find = new JLabel("Find ");
  replace = new JLabel("Replace ");
  
  //find text field dimension
  textField = new JTextField();
  textField.addActionListener(this);
  textField.setPreferredSize(new Dimension(70,25));
  
  //Replace text field dimension
  textField1 = new JTextField();
  textField1.addActionListener(this);
  textField1.setPreferredSize(new Dimension(70,25));
  
   
  fontSizeSpinner = new JSpinner();
  fontSizeSpinner.setPreferredSize(new Dimension(50,25));
  fontSizeSpinner.setValue(20);
  fontSizeSpinner.addChangeListener(new ChangeListener() {

   @Override
   public void stateChanged(ChangeEvent e) {
    
    textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) fontSizeSpinner.getValue())); 
   }
   
  });
  
 
  
  fontColorButton = new JButton("Color");
  fontColorButton.addActionListener(this);
  
  //find button action
  Find = new JButton("Find");
  Find.addActionListener(this);
  
   //findall button action
  Findall = new JButton("Find All");
  Findall.addActionListener(this);
  
  
  //Replace button action
  Replace = new JButton("Replace");
  Replace.addActionListener(this);
  
  
  //Replaceall button action
   Replaceall = new JButton("Replace All");
  Replaceall.addActionListener(this);
  
  
  String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
  
  fontBox = new JComboBox(fonts);
  fontBox.addActionListener(this);
  fontBox.setPreferredSize(new Dimension(80,25));
  fontBox.setSelectedItem("Arial");
  
  for(String s:GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()){
      fontBox.addItem(s); 
  }  
  // ------ menubar ------
  
   menuBar = new JMenuBar();
   fileMenu = new JMenu("File");
   newItem = new JMenuItem("New");
   openItem = new JMenuItem("Open");
   saveItem = new JMenuItem("Save");
   exitItem = new JMenuItem("Exit");

   Edit = new JMenu("Edit");
   undo = new JMenuItem("Undo");
   redo = new JMenuItem("Redo");
    copy = new JMenuItem("Copy");
   cut = new JMenuItem("Cut");
    paste = new JMenuItem("Paste");

   format = new JMenu("Format");
   Bold = new JMenuItem("Bold");
   italic = new JMenuItem("Italic");

   Help = new JMenu("Help");
   
   edit = new function_edit(this); //undo  - redo function
   
   newItem.addActionListener(this);
   openItem.addActionListener(this);
   saveItem.addActionListener(this);
   exitItem.addActionListener(this);
   undo.addActionListener(this);
   redo.addActionListener(this);
   copy.addActionListener(this);
   cut.addActionListener(this);
   paste.addActionListener(this);
   Bold.addActionListener(this);
   italic.addActionListener(this);
   Help.addActionListener(this);
   
   menuBar.add(fileMenu);
   fileMenu.add(openItem);
   fileMenu.add(newItem);
   fileMenu.add(saveItem);
   fileMenu.add(exitItem);
   
   menuBar.add(Edit);
   Edit.add(undo);
   Edit.add(redo);
   Edit.add(copy);
   Edit.add(cut);
   Edit.add(paste);

   
   menuBar.add(format);
   format.add(Bold);
   format.add(italic);
   
   menuBar.add(Help);
   
  
  // ------ /menubar ------
  
   this.setJMenuBar(menuBar);

  this.add(fontLabel);
  
  this.add(fontSizeSpinner);
  this.add(fontColorButton);
  this.add(fontBox);
  this.add(find); // find label
  this.add(textField); // find text field
  this.add(Find); // find button
  this.add(Findall); // find button
  this.add(replace); // replace label
  this.add(textField1); // replace textfield
  this.add(Replace); // replace button
  this.add(Replaceall); // replaceall button
  this.add(scrollPane);
  this.setVisible(true);
 }
 
 class function_edit{
    TextEditor gui;
    
    public function_edit(TextEditor gui){
        this.gui = gui;
    }
    public void undo(){
        gui.um.undo();
    }
    public void redo(){
        gui.um.redo();
    }
}
 
  int currentpos = 0;
 @Override
 public void actionPerformed(ActionEvent e) {
  
  if(e.getSource()==fontColorButton) {
   JColorChooser colorChooser = new JColorChooser();
   
   Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
   
   textArea.setForeground(color);
  }
  
  if(e.getSource()==fontBox) {
   textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
  }
  
  if(e.getSource()==openItem) {
   JFileChooser fileChooser = new JFileChooser();
   fileChooser.setCurrentDirectory(new File("."));
   FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
   fileChooser.setFileFilter(filter);
   
   int response = fileChooser.showOpenDialog(null);
   
   if(response == JFileChooser.APPROVE_OPTION) {
    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
    Scanner fileIn = null;
    
    try {
     fileIn = new Scanner(file);
     if(file.isFile()) {
      while(fileIn.hasNextLine()) {
       String line = fileIn.nextLine()+"\n";
       textArea.append(line);
      }
     }
    } catch (FileNotFoundException e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
    }
    finally {
     fileIn.close();
    }
   }
  }
  if(e.getSource()==saveItem) {
   JFileChooser fileChooser = new JFileChooser();
   fileChooser.setCurrentDirectory(new File("."));
   
   int response = fileChooser.showSaveDialog(null);
   
   if(response == JFileChooser.APPROVE_OPTION) {
    File file;
    PrintWriter fileOut = null;
    
    file = new File(fileChooser.getSelectedFile().getAbsolutePath());
    try {
     fileOut = new PrintWriter(file);
     fileOut.println(textArea.getText());
    } 
    catch (FileNotFoundException e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
    }
    finally {
     fileOut.close();
    }   
   }
  }
  if(e.getSource()==exitItem) {
   System.exit(0);
  }

    if(e.getSource()==newItem){
    new TextEditor().setVisible(true);
    }
    
    if(e.getSource()==Find){
       
    String findfromtext = textArea.getText();
    String tofindtext = textField.getText();
    int indexOf = findfromtext.indexOf(tofindtext, currentpos);
    int length = tofindtext.length();
    h = textArea.getHighlighter();
    h.removeAllHighlights();
    try{
        h.addHighlight(indexOf, indexOf + length,new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
    }
    catch(BadLocationException ex){
    
    }
    currentpos= indexOf + length;
        if (currentpos>=findfromtext.length()) {
            
            currentpos = 0;
        }
        if (findfromtext.indexOf(tofindtext,currentpos)==-1) {
            currentpos = 0;
        }
    
    }
    
        if(e.getSource()==Findall){
       
    String findfromtext = textArea.getText();
    String tofindtext = textField.getText();
    h = textArea.getHighlighter();
    h.removeAllHighlights();
    while(findfromtext.indexOf(tofindtext,currentpos)!=-1){
        int indexOf = findfromtext.indexOf(tofindtext, currentpos);
        int length = tofindtext.length();
    try{
        h.addHighlight(indexOf, indexOf + length,new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
    }
    catch(BadLocationException ex){
    }
    currentpos= indexOf + length;
    }
     if (currentpos>=findfromtext.length()) {   
            currentpos = 0;
        }
        if (findfromtext.indexOf(tofindtext,currentpos)==-1) {
            currentpos = 0;
        }
    }
    
     if(e.getSource()==Replace){
        String findfromtext = textArea.getText();
        String tofindtext = textField.getText();
        String replacefirst = textField1.getText();
        textArea.setText(findfromtext.replaceFirst(tofindtext, replacefirst));
        currentpos = 0;
     }
     if(e.getSource()==Replaceall){
        String findfromtext = textArea.getText();
        String tofindtext = textField.getText();
        String replacefirst = textField1.getText();
        textArea.setText(findfromtext.replaceAll(tofindtext, replacefirst));
        currentpos = 0;
     }
     if (e.getSource()==copy) {
        textArea.copy();
     }
    if (e.getSource()==paste) {
        textArea.paste();
    }
    if (e.getSource()==cut) {
        textArea.cut();
    }
    if(e.getSource()==undo){
      edit.undo();
    }
    if(e.getSource()==redo){
      edit.redo();
    }
  
    String f = (String)fontBox.getSelectedItem();
    
    if(e.getSource()==Bold){
      textArea.setFont(new Font(f,Font.BOLD,textArea.getFont().getSize()));
    }
    if(e.getSource()==italic){
      textArea.setFont(new Font(f,Font.ITALIC , textArea.getFont().getSize()));
    }
     
 }
 


}