/**
 * Created by Joseph on 02/12/2016.
 * ...I got lazy. Copy/modified from Calculator example.
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;


public class StudentApplication extends Application {
    TextField tfId; // ...I've slowly developed my own personal naming convention where none otherwise exists; this involves "ID" (and other such capitalised abbreviations) being camel-cased to Id, which is controversial, but still defensible (since it's easier to read: tfIdExample vs tfIDExample, at which point "IDE" pops out, and boy if that isn't a different meaning).
    TextField tfName;
    TextField tfAge;
    TextField tfGpa;
    TextField tfSearchId;

    TextField tfResultName;
    TextField tfResultAge;
    TextField tfResultGpa;

    StudentList students;



    @Override
    public void start(Stage primaryStage){
        // init stuff
        this.students = new StudentList(10);

        // create the components
        Label lblId = new Label("ID:");
        tfId = new TextField("ID");
        Label lblName = new Label("Name");
        tfName = new TextField("Name");
        Label lblAge = new Label("Age");
        tfAge = new TextField("Age");
        Label lblGpa = new Label("GPA");
        tfGpa = new TextField("GPA");
        Button btInsert = new Button("Insert");

        Label lblResultName = new Label("Name:");
        tfResultName = new TextField("");
        Label lblResultAge = new Label("Age:");
        tfResultAge = new TextField("");
        Label lblResultGpa = new Label("GPA:");
        tfResultGpa = new TextField("");

        tfSearchId = new TextField("Search [ID]");
        Button btSearchId = new Button("Search");

        //create the layout
        HBox overallFrame = new HBox(100);

        VBox leftFrame = new VBox(5);

        HBox leftFrameId = new HBox();
        HBox leftFrameName = new HBox();
        HBox leftFrameAge = new HBox();
        HBox leftFrameGpa = new HBox();

        HBox rightFrame = new HBox(25);
        VBox rightLeftFrame = new VBox(10);
        VBox rightRightFrame = new VBox(5);

        HBox rightRightFrameName = new HBox();
        HBox rightRightFrameAge = new HBox();
        HBox rightRightFrameGpa = new HBox();

        //add layout stuff to the layout
        overallFrame.getChildren().add(leftFrame);
        overallFrame.getChildren().add(rightFrame);

        rightFrame.getChildren().add(rightLeftFrame);
        rightFrame.getChildren().add(rightRightFrame);

        leftFrame.getChildren().add(leftFrameId);
        leftFrame.getChildren().add(leftFrameName);
        leftFrame.getChildren().add(leftFrameAge);
        leftFrame.getChildren().add(leftFrameGpa);
        leftFrame.getChildren().add(btInsert);

        rightRightFrame.getChildren().add(rightRightFrameName);
        rightRightFrame.getChildren().add(rightRightFrameAge);
        rightRightFrame.getChildren().add(rightRightFrameGpa);

        // add components to the layout
        leftFrameId.getChildren().add(lblId);
        leftFrameId.getChildren().add(tfId);
        leftFrameName.getChildren().add(lblName);
        leftFrameName.getChildren().add(tfName);
        leftFrameAge.getChildren().add(lblAge);
        leftFrameAge.getChildren().add(tfAge);
        leftFrameGpa.getChildren().add(lblGpa);
        leftFrameGpa.getChildren().add(tfGpa);

        rightLeftFrame.getChildren().add(tfSearchId);
        rightLeftFrame.getChildren().add(btSearchId);

        rightRightFrameName.getChildren().add(lblResultName);
        rightRightFrameName.getChildren().add(tfResultName);
        rightRightFrameAge.getChildren().add(lblResultAge);
        rightRightFrameAge.getChildren().add(tfResultAge);
        rightRightFrameGpa.getChildren().add(lblResultGpa);
        rightRightFrameGpa.getChildren().add(tfResultGpa);

        // pad
        overallFrame.setPadding(new Insets(10, 20, 20, 20));
        // center frames
        leftFrame.setAlignment(Pos.CENTER);
        rightLeftFrame.setAlignment(Pos.CENTER);
        rightRightFrame.setAlignment(Pos.CENTER);

        // make the labels fixed-width
        lblId.setMinWidth(100);
        lblName.setMinWidth(100);
        lblAge.setMinWidth(100);
        lblGpa.setMinWidth(100);

        lblResultName.setPrefWidth(80);
        lblResultAge.setPrefWidth(80);
        lblResultGpa.setPrefWidth(80);

        // make the insert button wider
        btInsert.setPrefWidth(150);

        // Disable the result textboxes.
        tfResultName.setDisable(true);
        tfResultAge.setDisable(true);
        tfResultGpa.setDisable(true);

        // Assign listeners to buttons
        InsertListenerClass insertListener = new InsertListenerClass();
        btInsert.setOnAction(insertListener);

        SearchListenerClass searchListener = new SearchListenerClass();
        btSearchId.setOnAction(searchListener);

        // add the layout to scene
        Scene scene = new Scene(overallFrame);

        // add scene to the stage
        primaryStage.setScene(scene);

        // Title and show
        primaryStage.setTitle("Student Interface");
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }




    class InsertListenerClass implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            int sId = Integer.parseInt(tfId.getText());
            String sName = tfName.getText();
            int iAge = Integer.parseInt(tfAge.getText());
            double dGpa = Double.parseDouble(tfGpa.getText());

            students.addStudent(sId, sName, iAge, dGpa);
        }
    }

    class SearchListenerClass implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            Student student = students.findStudent(Integer.parseInt(tfSearchId.getText()));

            if (student == null) {
                tfResultName.setText("(Not Found)");
                tfResultAge.setText("");
                tfResultGpa.setText("");
            }
            else {
                tfResultName.setText(student.getName());
                tfResultAge.setText(String.valueOf(student.getAge()));
                tfResultGpa.setText(String.valueOf(student.getGpa()));
            }
        }
    }

}