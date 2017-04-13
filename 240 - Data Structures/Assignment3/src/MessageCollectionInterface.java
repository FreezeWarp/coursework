import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * An interface for managing a MessageList object.
 * Insert/Delete/Update operations are performed from an accordion in the left pane.
 * Searches occur from the top search bar above the message list table, while the total entries being stored are displayed below the table.
 * The table itself will display the entire list, or only what has been queried by the search field if available.
 * Based on instructor-provided example code.
 *
 * @author Joseph T Parsons <josephtparsons@gmail.com>
 * @version 2017-02-03
 */
public class MessageCollectionInterface extends Application {
    // The MessageList itself. Defaults to allocating space to 10 items, but per MessageList specification should resize on-demand.
    GenericSortedArrayBag list = new GenericSortedArrayBag<Message>(10);


	// Stage
	Stage window;


	// Create Insert/Update/Delete Text Fields
    TextField insertIdField = new TextField();
    TextField insertRoomIdField = new TextField();
    TextField insertUserIdField = new TextField();
    TextField insertTimeField = new TextField();
    TextField insertTextField = new TextField();

    TextField deleteTextField = new TextField();

    TextField updateIdField = new TextField();
    TextField updateRoomIdField = new TextField();
    TextField updateUserIdField = new TextField();
    TextField updateTimeField = new TextField();
    TextField updateTextField = new TextField();

    TextField displaySearchField = new TextField();


    // Label showing the total number of items in the list
    Label storedItemsLabel = new Label("Total Stored Messages: 0");


	// Create the table
    TableView<Message> table = new TableView<Message>();


    // Create The Listener Classes Used When an Insert/Update/Delete Button is Clicked
    InsertListenerClass messageInsertListener = new InsertListenerClass();
    DeleteListenerClass messageDeleteListener = new DeleteListenerClass();
    UpdateListenerClass messageUpdateListener = new UpdateListenerClass();


	public static void main(String[] args) {
		Application.launch(args);
	}


	@Override
	public void start(Stage primaryStage) {
        // Set up the table's columns and connect the table to them
        TableColumn displayIdCol = new TableColumn("Message ID");
        TableColumn displayRoomIdCol = new TableColumn("Room ID");
        TableColumn displayUserIdCol = new TableColumn("User ID");
        TableColumn displayTimeCol = new TableColumn("Time");
        TableColumn displayTextCol = new TableColumn("Text");

        displayIdCol.setCellValueFactory(new PropertyValueFactory<Message,Integer>("id"));
        displayRoomIdCol.setCellValueFactory(new PropertyValueFactory<Message,Integer>("roomId"));
        displayUserIdCol.setCellValueFactory(new PropertyValueFactory<Message,Integer>("userId"));
        displayTimeCol.setCellValueFactory(new PropertyValueFactory<Message,Integer>("time"));
        displayTextCol.setCellValueFactory(new PropertyValueFactory<Message,String>("text"));

        table.getColumns().addAll(displayTextCol, displayIdCol, displayRoomIdCol, displayUserIdCol, displayTimeCol);


        // Create Accordion & Sub-Panes
        Accordion accordion = new Accordion();

        VBox insertPane = new VBox();
        VBox updatePane = new VBox();
        VBox deletePane = new VBox();

        TitledPane insertPaneTitled = new TitledPane("Insert a Message", insertPane);
        TitledPane deletePaneTitled = new TitledPane("Delete a Message", deletePane);
        TitledPane updatePaneTitled = new TitledPane("Update a Message", updatePane);


        // Create Insert/Update/Delete Buttons
        Button deleteBtn = new Button("Delete");
        Button updateBtn = new Button("Update");
        Button insertBtn = new Button("Insert");


        // Set the window
		window = primaryStage;


        // Set the stage's properties and show it
		primaryStage.setTitle("Collection Interface");
		primaryStage.show();


        // Make Buttons Wide
        insertPane.setFillWidth(true);
        insertBtn.setMaxWidth(Double.POSITIVE_INFINITY);

        deletePane.setFillWidth(true);
        deleteBtn.setMaxWidth(Double.POSITIVE_INFINITY);

        updatePane.setFillWidth(true);
        updateBtn.setMaxWidth(Double.POSITIVE_INFINITY);


        // Connect button presses to their listeners
        insertBtn.setOnAction(messageInsertListener);
        updateBtn.setOnAction(messageUpdateListener);
        deleteBtn.setOnAction(messageDeleteListener);


		// Add elements to the insert, delete, and update panes
        insertPane.getChildren().add(new Label("Enter Message Text: "));
        insertPane.getChildren().add(insertTextField);
		insertPane.getChildren().add(new Label("Enter Message ID: ")); // If you were to make these translatable, I suppose you'd want the label to be referencing a function call, e.g. new Label(l("insertIdLabel")) instead of a string.
		insertPane.getChildren().add(insertIdField);
		insertPane.getChildren().add(new Label("Enter Room ID: "));
		insertPane.getChildren().add(insertRoomIdField);
		insertPane.getChildren().add(new Label("Enter User ID: "));
		insertPane.getChildren().add(insertUserIdField);
		insertPane.getChildren().add(new Label("Enter Message Time: "));
		insertPane.getChildren().add(insertTimeField);
		insertPane.getChildren().add(insertBtn);


        deletePane.getChildren().add(new Label("Where Message Text is..."));
        deletePane.getChildren().add(deleteTextField);
        deletePane.getChildren().add(deleteBtn);


        updatePane.getChildren().add(new Label("Where Message Text is..."));
        updatePane.getChildren().add(updateTextField);
        updatePane.getChildren().add(new Label("Set Message ID to..."));
        updatePane.getChildren().add(updateIdField);
        updatePane.getChildren().add(new Label("And Room ID to..."));
        updatePane.getChildren().add(updateRoomIdField);
        updatePane.getChildren().add(new Label("Set User ID to..."));
        updatePane.getChildren().add(updateUserIdField);
        updatePane.getChildren().add(new Label("And Time to..."));
        updatePane.getChildren().add(updateTimeField);
        updatePane.getChildren().add(updateBtn);


        // Set up the accordion
        accordion.getPanes().add(insertPaneTitled);
        accordion.getPanes().add(deletePaneTitled);
        accordion.getPanes().add(updatePaneTitled);

        accordion.setExpandedPane(insertPaneTitled);


        // Create the search bar
        HBox searchPane = new HBox();

        searchPane.getChildren().add(new Label("Search : "));
        searchPane.getChildren().add(displaySearchField);


        // Set up the main display pane
        VBox displayPane = new VBox();

        displayPane.getChildren().add(searchPane);
        displayPane.getChildren().add(table);
        displayPane.getChildren().add(storedItemsLabel);


        // Create the main group, add in accordion and displayPane
        HBox group = new HBox(10); // The 10 is the space around the whole container; adds some nice breathing room
        group.getChildren().add(accordion);
        group.getChildren().add(displayPane);


        // Make things grow inside of the main group
        HBox.setHgrow(displayPane, Priority.ALWAYS); // Ensures displayPane fills its space horizontally
        HBox.setHgrow(displaySearchField, Priority.ALWAYS); // Ensures the search field fills its space horizontally
        VBox.setVgrow(table, Priority.ALWAYS); // Ensures the table fills its space vertically (it should automatically fill its space horizontally)
        displayPane.setFillWidth(true);


        // Add some paddings to the stuff in the main group
        group.setPadding(new Insets(10, 10, 10, 10)); // Adds some padding between the accordion and displayPane (search, table, etc.)
        VBox.setMargin(searchPane, new Insets(0, 0, 10, 0)); // Adds some space below the search field
        VBox.setMargin(storedItemsLabel, new Insets(10, 0, 0, 0)); // Adds some space above the items label
        searchPane.setAlignment(Pos.CENTER); // Centers the search label
        displayPane.setAlignment(Pos.CENTER_RIGHT); // Aligns the list size label to the right


        // Create the scene with the recommended size (compatible with 800x600 monitors, as if anyone actually uses those anymore)
        window.setScene(new Scene(group, 800, 550));


        // Call drawTable() for first time -- won't do anything if there is no existing messages loaded.
        drawTable();


        // Update the table when the search field is updated
        displaySearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            drawTable();
        });
	}


    /**
     * Redraws the table when called, looking at the search field to see if a search parameter is provided.
     */
	void drawTable() {
        ObservableList<Message> data = FXCollections.observableArrayList();

        System.out.println("List Contents:");
        System.out.println(this.list);

        if (!displaySearchField.getText().equals("")) {
            data.add((Message) this.list.search(new Message(displaySearchField.getText())));
        }

        else {
            for (Object o : this.list) {
                Message m = (Message) o;
                // I'm... not sure why, but at least on my development machine, data.add(m) results in old values being listed after an update call (though the correct values will be listed once an insert or delete call takes place)
                // If this was C, I'd assume it was some kind-of compiler optimisation that shouldn't be taking place (and disabling whatever flags are appropriate, or use volatile), but I don't think that makes sense in Java.
                // I did attempt using volatile in a couple of places, since that seemed like something that could do it, but no dice there. (I don't know how Java's volatile works, so...)
                // In any case, if you see this comment, and know why, drop me a hint. I'm very curious why data.add(m) works differently than this line. (...Does observableArrayList have some sort-of reference cache?)
                data.add(new Message(m.getId(), m.getRoomId(), m.getUserId(), m.getTime(), m.getText()));
            }
        }

        this.table.setItems(data);

        storedItemsLabel.setText("Total Items in List: " + this.list.size());
    }


    /**
     * Shows a simple error with the given title and text.
     *
     * @param errorTitle The title to use both in the message body and for the window title.
     * @param errorText The text to display in the message body. Will wrap automatically.
     */
    void showSimpleError(String errorTitle, String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorTitle);
        alert.setHeaderText(errorTitle);
        alert.getDialogPane().setContent(new Label(errorText));
        alert.showAndWait();
    }


    /**
     * Looks at the insert fields and performs an insert operation when called.
     */
	class InsertListenerClass implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent ae) {
		    try {
                int id = Integer.parseInt(insertIdField.getText());
                int roomId = Integer.parseInt(insertRoomIdField.getText());
                int userId = Integer.parseInt(insertUserIdField.getText());
                int time = Integer.parseInt(insertTimeField.getText());
                String text = insertTextField.getText();

                if (list.insert(new Message(id, roomId, userId, time, text)) == -1) {
                    MessageCollectionInterface.this.showSimpleError("Insert Failed", "There probably is already a message with the same text.");
                }

                System.out.println(list);
            } catch(Exception ex) {
                showSimpleError("Unable to Perform Insertion", "Unable to parse insert data: " + ex);
            }

			drawTable();
		}	
	}


    /**
     * Looks at the delete fields and performs an delete operation when called.
     */
    class DeleteListenerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent ae) {
            try {
                String text = deleteTextField.getText();

                if (list.delete(new Message(text)) == -1) {
                    MessageCollectionInterface.this.showSimpleError("Delete Failed", "No entry was found with the given text.");
                }
            } catch (Exception ex) {
                showSimpleError("Unable to Perform Deletion", "Unable to parse delete conditions: " + ex);
            }

            drawTable();
        }
    }


    /**
     * Looks at the update fields and performs an update operation when called.
     */
    class UpdateListenerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent ae) {
            try {
                int id = Integer.parseInt(updateIdField.getText());
                int roomId = Integer.parseInt(updateRoomIdField.getText());
                int userId = Integer.parseInt(updateUserIdField.getText());
                int time = Integer.parseInt(updateTimeField.getText());
                String text = updateTextField.getText();

                if (!list.update(new Message(text), new Message(id, roomId, userId, time, text))) {
                    MessageCollectionInterface.this.showSimpleError("Update Failed", "There probably isn't a message with the given text.");
                }
            } catch (Exception ex) {
                showSimpleError("Unable to Perform Update", "Unable to parse update data: " + ex);
            }

            drawTable();
        }
    }
}
