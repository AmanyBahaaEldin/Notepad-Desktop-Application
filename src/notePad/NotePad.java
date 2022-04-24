/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package notePad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 *
 * @author Bahaa eldin
 */
public class NotePad extends Application{
    
    public Menu createMenu(String menu){
        return(new Menu(menu));
    }
    public MenuItem createItem(String item , String keyCut){
        MenuItem menuItem = new MenuItem(item);
    if(keyCut != null){
        menuItem.setAccelerator(KeyCombination.keyCombination(keyCut));
    }
        return(menuItem);
    }
    @Override
    public void start(Stage  primaryStage) throws Exception{
        MenuBar bar = new MenuBar();
        TextArea text = new TextArea();
        DirectoryChooser directory = new DirectoryChooser();
        FileChooser fileChosen = new FileChooser();
 
//File menu.....................................
        Menu file = createMenu("File");

        MenuItem newItem =createItem("New" , "Alt+n");
        newItem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("images/iconNew.png"))));
        Dialog dialogNew = new Dialog();
        dialogNew.setTitle("Dialog");
        ButtonType type1 = new ButtonType("Ok");
        dialogNew.setContentText("Clicked on New Item");
        //Adding buttons to the dialog pane
        dialogNew.getDialogPane().getButtonTypes().add(type1);
         newItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
        @Override
          public void handle(ActionEvent event){
              dialogNew.showAndWait();
              text.clear();
        }
        });
        MenuItem openItem = createItem("Open" , "Alt+o");
        openItem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("images/iconOpen.png"))));
        openItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
        @Override
          public void handle(ActionEvent event){
            try{
                File file = fileChosen.showOpenDialog(primaryStage);
                FileInputStream fileRead = new FileInputStream(file.getAbsolutePath());
//System.out.println(file.length());
                byte[] fileContent = new byte[(int)file.length()];
                fileRead.read(fileContent);
                text.setText(new String(fileContent));
                fileRead.close();
                }catch(IOException ex){
                              ex.printStackTrace();
                }         
        }
        });
        MenuItem saveItem = createItem("Save" , "Alt+s");
        saveItem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("images/iconSave.png"))));
        saveItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){        
        @Override
          public void handle(ActionEvent event){
            try{
                File file = fileChosen.showOpenDialog(primaryStage);
                FileWriter fileWrite= new FileWriter(file.getAbsolutePath());
//        char[] chArr = {'a' , 'b' , 's'};
                fileWrite.write(text.getText());
                fileWrite.close();

            }catch(IOException ex){
                          ex.printStackTrace();
            }
                
        }
        });
        MenuItem exitItem = createItem("Exit" , "Alt+e");
        SeparatorMenuItem sep1 = new SeparatorMenuItem();

        file.getItems().addAll(newItem , openItem , saveItem , sep1 , exitItem);

//Edit menu.........................................
        Menu edit = createMenu("Edit");
        MenuItem undoItem = createItem("Undo" , "Alt+z");
        undoItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            text.undo();
        }
        });
        MenuItem cutItem = createItem("Cut" , "Alt+x");
        cutItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            text.cut();
        }
        });
        MenuItem copyItem = createItem("Copy" , "Alt+c");
        copyItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            text.copy();
        }
        });
        MenuItem pasteItem = createItem("Paste" , "Alt+v");
        pasteItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            text.paste();
        }
        });
        MenuItem deleteItem = createItem("Delete" , null);
        deleteItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            text.deletePreviousChar();
        }
        });
        MenuItem selectAllItem = createItem("Select All" , null);
        selectAllItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            text.selectAll();
        }
        });
        SeparatorMenuItem sep2 = new SeparatorMenuItem();
        SeparatorMenuItem sep3 = new SeparatorMenuItem();

        edit.getItems().addAll(undoItem ,sep2, cutItem , copyItem , pasteItem , deleteItem , sep3 , selectAllItem);

//About menu.............................................
        Menu about = createMenu("About");
        MenuItem aboutItem = createItem("About NotePad" , null);
        Dialog dialogAbout = new Dialog();
        dialogAbout.setTitle("Dialog");
        ButtonType type4 = new ButtonType("Ok");
        dialogAbout.setContentText("NotePad Application");
        //Adding buttons to the dialog pane
        dialogAbout.getDialogPane().getButtonTypes().add(type4);
        aboutItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
        @Override
          public void handle(ActionEvent event){
              dialogAbout.showAndWait();
        }
        });
        about.getItems().addAll(aboutItem);
//Adding all menus to the menu bar
        bar.getMenus().addAll(file , edit , about);
        BorderPane root = new BorderPane();
        root.setTop(bar);
        root.setCenter(text);

        Scene scene = new Scene(root , 700 , 500);
        primaryStage.setTitle("NotePad Application");
        primaryStage.setScene(scene);
        primaryStage.show();
}
    public static void main(String[] args){
    Application.launch(args);
    }

    
}
