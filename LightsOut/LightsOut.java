import java.util.Random;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LightsOut extends Application {

	static final int DEFAULT_LEVELS = 5, MAX_LEVELS = 9;
	Scene scene1, TxT; // home,3x3,4x4,5x5,6x6,7x7,8x8,9x9
	IntegerProperty levels = new SimpleIntegerProperty(DEFAULT_LEVELS);
	Button lightOff;
	RadioButton rb;
	private Button[][] buttons;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// This makes the first scene of radio buttons, button, and label
		VBox layout1 = new VBox(5);
		layout1.setAlignment(Pos.CENTER);
		Label label = new Label("Please select a size: ");
		ToggleGroup tg = new ToggleGroup();

		// Change scene buttons
		Button create = new Button("Create Puzzle");

		// Buttons 3-9
		layout1.getChildren().add(label);
		for (int i = 3; i <= MAX_LEVELS; i++) {
			RadioButton rb = new RadioButton("" + i);
			rb.setToggleGroup(tg);
			rb.setSelected(i == DEFAULT_LEVELS);
			rb.setUserData(i);
			layout1.getChildren().add(rb);
		}

		// Create Puzzle button action
		create.setOnAction(e -> {
			int selected_size = Integer.parseInt(tg.getSelectedToggle().getUserData().toString());

			// Layout 2
			BorderPane layout2 = new BorderPane();
			// Hbox with two buttons in the bottom
			HBox hbox = new HBox(20);
			hbox.setAlignment(Pos.BOTTOM_CENTER);
			hbox.setPrefHeight(60);
			// "Randomize": With 0.5 probability, toggles each light button.
			Button random = new Button("Randomize");

			Random x = new Random();
			// Action when randomize button is clicked
			random.setOnAction(b -> {
				for (int i = 0; i < selected_size; i++) {
					for (int j = 0; j < selected_size; j++) {
						if(x.nextDouble() >= 0.5) {
							toggleLight(i,j);
						}
					}
				}

			});
			Button chase = new Button("Chase Lights");

			chase.setOnAction(b -> {

				for (int i = 0; i < selected_size; i++)
					for (int j = 0; j < selected_size; j++) {
						if (i + 1 < buttons.length && (buttons[i][j].getId().matches("yellow"))) {
							toggleLights(i + 1, j);

						}
					}

			});

			hbox.getChildren().addAll(random, chase);
			// GridPane
			GridPane gridpane = new GridPane();
			gridpane.setAlignment(Pos.CENTER);
			layout2.setStyle("-fx-background: #373737;"); // needs to be added to the gridpane
			layout2.setBottom(hbox);
			layout2.setCenter(gridpane);

			TxT = new Scene(layout2, (60 * selected_size), (60 * selected_size) + 60);
			buttons = new Button[selected_size][selected_size];

			for (int i = 0; i < selected_size; i++) {
				for (int j = 0; j < selected_size; j++) {
					Button lightOff = new Button();
					buttons[i][j] = lightOff;
					lightOff.setStyle("-fx-background-color: #000000; -fx-background-radius: 10; -fx-background-insets: 1");
					lightOff.setPrefSize(50, 50);
					gridpane.add(lightOff, j, i);
					lightOff.setId("black");
					final int i2 = i, j2 = j;
					lightOff.setOnMouseClicked(a -> {
						toggleLights(i2, j2);
					});

				}
			}
			
			// Randomly toggle some lights to be on
		    for (int i = 0; i < selected_size; i++) {
		        for (int j = 0; j < selected_size; j++) {
		            if (x.nextDouble() >= 0.5) {
		                toggleLight(i, j);
		            }
		        }
		    }
			primaryStage.setScene(TxT);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Lights Out");
		});

		layout1.getChildren().addAll(create);
		scene1 = new Scene(layout1, 150, 210); //end of first scene

		primaryStage.setScene(scene1);
		primaryStage.setTitle("Lights Out Size");
		primaryStage.show();

	}

	private void toggleLights(int i, int j) { //Algorithm
		toggleLight(i, j);
		if (i - 1 >= 0) {
			toggleLight(i - 1, j);
		}
		if (i + 1 < buttons.length) {
			toggleLight(i + 1, j);
		}
		if (j - 1 >= 0) {
			toggleLight(i, j - 1);
		}
		if (j + 1 < buttons.length) {
			toggleLight(i, j + 1);
		}
	}

	private void toggleLight(int i, int j) {
		if (buttons[i][j].getId().matches("black")) {
			buttons[i][j].setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 10; -fx-background-insets: 1");
			buttons[i][j].setId("yellow");
		} else {
			buttons[i][j].setStyle("-fx-background-color: #000000; -fx-background-radius: 10; -fx-background-insets: 1");
			buttons[i][j].setId("black");
		}

	}

	public static void main(String[] args) {
		launch(args);

	}

}
