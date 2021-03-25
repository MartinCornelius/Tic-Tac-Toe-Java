package application;
	
import com.sun.glass.ui.MenuItem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class TicTacToeGame extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	Button[][] tiles = new Button[3][3];
	String player = "X";
	
	int xWins = 0;
	int oWins = 0;
	
	Label lblXWins;
	Label lblOWins;
	
	@Override
	public void start(Stage Game) {
		Game.setTitle("Tic Tac Toe Java");
		
		GridPane grid = new GridPane();

		// Grid Setup
		int tileID = 1;
		for (int col = 0; col < 3; col++) {
			for (int row = 0; row < 3; row++) {
				Button newTile = new Button();
				newTile.setId(String.valueOf(tileID));
				newTile.setPrefHeight(200);
				newTile.setPrefWidth(200);
				newTile.setStyle("-fx-text-fill: #F3f5f5; -fx-border-color: #5ca4b3; -fx-border-width: 3px; -fx-background-color: #85d2e3; -fx-font-size: 72px;");
				
				tileID++;
				
				// Changes text to current player when hovering over and clears it when left
				newTile.hoverProperty().addListener((event) -> tileHoverHandling(newTile));
				newTile.setOnMousePressed((event) -> tileClicked(newTile));
				
				tiles[col][row] = newTile;
				grid.add(newTile, col, row);
			}
		}
		
		// Score
		lblXWins = new Label("X Wins: " + xWins + " ");
		lblXWins.setFont(new Font("Arial", 24));
		lblXWins.setStyle("-fx-text-fill: #F3f5f5;");
		lblXWins.setAlignment(Pos.BASELINE_LEFT);
		lblXWins.setPadding(new Insets(10,10,10,10));
		
		lblOWins = new Label(" O Wins: " + oWins);
		lblOWins.setFont(new Font("Arial", 24));
		lblOWins.setStyle("-fx-text-fill: #F3f5f5;");
		lblOWins.setAlignment(Pos.CENTER_LEFT);
		lblOWins.setPadding(new Insets(10,10,10,10));
		
		HBox hbox = new HBox(lblXWins, lblOWins);
		hbox.setAlignment(Pos.CENTER);
		hbox.setStyle("-fx-background-color: #5ca4b3;");
		
		VBox mainScreen = new VBox(grid, hbox);
		Scene scene = new Scene(mainScreen, 600, 600);
		Game.setScene(scene);
		Game.show();
	}
	
	private void tileHoverHandling(Button tile) {
		if (tile.isHover() && !tile.isDisabled()) {
			tile.setText(player);
		}
		else if (!tile.isHover() && !tile.isDisabled()) {
			tile.setText("");
		}
	}
	
	private void tileClicked(Button tile) {
		tile.setDisable(true);
		tile.setOpacity(0.8);
		tile.setText(player);
		
		// Check for win horizontal
		if (isWin(tiles[0][0], tiles[1][0], tiles[2][0])) {GameOver(tiles[0][0].getText());}
		if (isWin(tiles[0][1], tiles[1][1], tiles[2][1])) {GameOver(tiles[0][1].getText());}
		if (isWin(tiles[0][2], tiles[1][2], tiles[2][2])) {GameOver(tiles[0][2].getText());}
		
		// Check for win vertical
		if (isWin(tiles[0][0], tiles[0][1], tiles[0][2])) {GameOver(tiles[0][0].getText());}
		if (isWin(tiles[1][0], tiles[1][1], tiles[1][2])) {GameOver(tiles[1][0].getText());}
		if (isWin(tiles[2][0], tiles[2][1], tiles[2][2])) {GameOver(tiles[2][0].getText());}
		
		// Check for win diagonal
		if (isWin(tiles[0][0], tiles[1][1], tiles[2][2])) {GameOver(tiles[0][0].getText());}
		if (isWin(tiles[2][0], tiles[1][1], tiles[0][2])) {GameOver(tiles[2][0].getText());}
		
		// Check for draw (no more tiles left)
		if (tiles[0][0].isDisabled() && tiles[1][0].isDisabled() && tiles[2][0].isDisabled() &&
			tiles[0][1].isDisabled() && tiles[1][1].isDisabled() && tiles[2][1].isDisabled() &&
			tiles[0][2].isDisabled() && tiles[1][2].isDisabled() && tiles[2][2].isDisabled()) {
			GameOver("");
		}
		
		player = player == "X" ? "O" : "X"; // Change player
	}
	
	private void GameOver(String winner) {
		if (winner.equals("X")) {
			xWins++;
			lblXWins.setText("X Wins: " + xWins);
			
			Alert alert = new Alert(AlertType.NONE, "Start new game", ButtonType.YES, ButtonType.CLOSE);
			alert.setTitle(winner + " WON!!");
			alert.setContentText("START NEW GAME");
			alert.showAndWait();
			
			if (alert.getResult() == ButtonType.YES) {
				newGame();
			}
			else {
				Platform.exit();
			}

		}
		else if (winner.equals("O")) {
			oWins++;
			lblOWins.setText("O Wins: " + oWins);
			
			Alert alert = new Alert(AlertType.NONE, "Start new game", ButtonType.YES, ButtonType.CLOSE);
			alert.setTitle(winner + " WON!!");
			alert.setContentText("START NEW GAME");
			alert.showAndWait();
			
			if (alert.getResult() == ButtonType.YES) {
				newGame();
			}
			else {
				Platform.exit();
			}
		}
		else {
			Alert alert = new Alert(AlertType.NONE, "Start new game", ButtonType.YES, ButtonType.CLOSE);
			alert.setTitle("DRAW!!");
			alert.setContentText("START NEW GAME");
			alert.showAndWait();
			
			if (alert.getResult() == ButtonType.YES) {
				newGame();
			}
			else {
				Platform.exit();
			}
		}
	}
	
	private Boolean isWin(Button tile1, Button tile2, Button tile3) {
		if (tile1.isDisabled() && tile1.getText().equals(tile2.getText()) && tile1.getText().equals(tile3.getText())) {
			return true;
		}
		return false;
	}
	
	private void newGame() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tiles[i][j].setDisable(false);
				tiles[i][j].setOpacity(1);
				tiles[i][j].setText("");
			}
		}
	}
	
}
