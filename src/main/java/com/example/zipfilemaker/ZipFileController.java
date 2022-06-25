package com.example.zipfilemaker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileController implements Initializable {
    @FXML
    private TextField fileNameField;
    @FXML
    private TextField urlField;

    @FXML
    private Button addBtn;


    private ArrayList<File> allFiles = new ArrayList<>();

    //Todo: implement file chooser, finish the listView
    //

    @FXML
    private ListView<String> listView;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
    }

    public void deleteFile(ActionEvent e ){
        String file = listView.getSelectionModel().getSelectedItem();
        File f = new File(file);
        allFiles.remove(f);
        listView.getItems().remove(file);
    }

    public void addFile(ActionEvent e){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to zip");
        Node node = (Node) e.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        fileChooser.setInitialDirectory(new File("C:\\Users\\yasse\\Desktop"));
        File selectedFile = fileChooser.showOpenDialog(thisStage);
        if(selectedFile !=null){
            allFiles.add(selectedFile);
            listView.getItems().add(String.valueOf(selectedFile));
        }
    }

    public void zip(ActionEvent e ) throws IOException{
            FileOutputStream fos = new FileOutputStream("compressed.zip");
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            for(File f : allFiles){

                FileInputStream  fis = new FileInputStream(f);
                ZipEntry zipEntry = new ZipEntry(f.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int len;
                while((len = fis.read(bytes))>= 0 ){
                    zipOut.write(bytes,0,len);
                }
                fis.close();
            }
            zipOut.close();
            fos.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("File Zipper info");
        alert.setHeaderText("Zipper process");
        alert.setContentText("All files were zipped successfully");
        alert.showAndWait();
    }
    public void download(ActionEvent e){

        String url = urlField.getText();
        String fileName = fileNameField.getText();
        File file = new File(fileName);
        try {

            InputStream is = new URL(url).openStream();
            Files.copy(is, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }finally {
            allFiles.add(file);
            listView.getItems().add(fileName);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("File Zipper");
            alert.setHeaderText("Downloading process");
            alert.setContentText("Downloaded content was successfully copied into the path specified");
            alert.showAndWait();
        }


    }
}