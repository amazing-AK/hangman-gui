/*Sam Snarr
 * 2-5-2018
 * ITP 220 - Professor Cain
 * Project 1
 * Hangman with GUI
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Text; 
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.util.Random;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

public class HangmanGUI extends Application {
	
	 public Button button = new Button("Check Letter");

	 Text text1 = new Text(200, 425, "");
	 Text text2 = new Text(200, 450, "");
	 Text text3 = new Text(200, 475, "");
	 Text firstOut; 
	 
	 TextField field = new TextField();  //creates only text field
	 
	 //creates the 6 components of the body
	 Line leftArm = new Line(250, 150, 200, 180);
	 Line rightArm = new Line(250, 150, 300, 180);
	 Line body = new Line(250, 150, 250, 250);
	 Line leftLeg = new Line(250, 250, 200, 300);
	 Line rightLeg = new Line(250, 250, 300, 300);
	 Circle circle = new Circle(250, 125, 25, Color.TRANSPARENT);
	 
	 //creates hanging contraption
	 Line base = new Line(50, 400, 400, 400);
	 Line pole = new Line(75, 400, 75, 50);
	 Line topPole = new Line(75, 50, 250, 50);
	 Line rope = new Line(250, 50, 250, 100);
	 Pane pane = new Pane();
	  
	 int count = 0; //count for number of wrong guesses
	 
	 static Random rand = new Random(); //for picking word from word bank
	 
	 String input; //input from textfield
	 static String[] bank = {"community", "school", "dog", "bottle", "house", "java", "desk", "hangman", "game", "fun"};
	 static String word = bank[rand.nextInt(10)];  //this is the word that is picked from wordbank
	 static char[] charWord = word.toCharArray();  //creates array of the word 
	 static char[] correctGuess = new char[word.length()]; //array of correct guesses and '_'
	 
	 static String guesses = ""; //string of guesses that user guesses

	 @Override // Override the start method in the Application class
	 public void start(Stage primaryStage) {
		//adds the field and formats it
		pane.getChildren().add(field);
		field.setLayoutX(410);
		field.setLayoutY(450);
		field.setPrefWidth(50);
		
		// fills the array correctGuess and string with underscores
		String scrap = "";
		for(int i=0; i< correctGuess.length; i++){
			 correctGuess[i] = '_';
			 scrap += "_ ";
		 }
		//outputs initial underscores to let user know how long word is
		firstOut = new Text(75, 450, scrap);
		pane.getChildren().add(firstOut);
		
		//adds button and formats it
		pane.getChildren().add(button);
		button.setLayoutX(315);
		button.setLayoutY(450);
		  
		//adds contraption
		pane.getChildren().add(base);
		pane.getChildren().add(pole);
		pane.getChildren().add(topPole);
	    pane.getChildren().add(rope);
		circle.setStroke(Color.BLACK);
		
		 // Process events
		button.setOnAction(e -> {
			input = field.getText();
			checkInWord();
		});
		
		pane.setOnKeyPressed(e -> {
			switch(e.getCode()){
			case ENTER:
				input = field.getText();
				checkInWord();
			default:
				break;
			}
		});
		
		// Create a scene and place it in the stage
		Scene scene = new Scene(pane, 500, 550);
		primaryStage.setTitle("Hangman"); 
		primaryStage.setScene(scene); 
		primaryStage.show(); // Display the stage
	}
	
	public void checkInWord() {
		
		if(pane.getChildren().contains(firstOut)){
			pane.getChildren().remove(firstOut);
		}
		 
		char guessLetter = input.charAt(0);
		guessLetter = Character.toLowerCase(guessLetter);
		String correctOrNot = "";
		 
		if(charInWord(guessLetter)){ //if the user picks a correct letter
			correctOrNot = "Your guess is correct";
			if(isDuplicate(guessLetter)){ //if the letter has been picked before
				correctOrNot += " but you have already guessed it before.";
			}
		}
		else{  // if letter is not correct
			if(isDuplicate(guessLetter)){ //if letter is a duplicate
				correctOrNot = "You have already guessed that letter. ";
				count--; //compensates for the inevitable upcount two lines below
			}
			count++; //they guessed wrong so they lose one limb
			correctOrNot += "That letter is not in the word";
		}
		//removes original text and adds new text updated by first choice of letter
		pane.getChildren().remove(text1);
		text1 = new Text(75, 425, correctOrNot);
		pane.getChildren().add(text1);
		
		//adds the letter that was guessed to the string of letters guessed if its not a duplicate
		if(!isDuplicate(guessLetter)){
			guesses += guessLetter;
		}
		//manipulates array of letters if they match the guessed letter
		for(int i = 0; i< charWord.length; i++){
			if(guessLetter == charWord[i] ){
				correctGuess[i] = guessLetter;
			}
		}
		//combines all parts of this array back into a string for output and comparison
		String correct = "";
		String ex = "";
		for(int i = 0; i< charWord.length; i++){
			correct +=correctGuess[i];
			ex += correctGuess[i] + " ";
		}
		//removes old and replaces with updated text
		pane.getChildren().remove(text2); 
		text2 = new Text(75, 450, ex);
		pane.getChildren().add(text2);
		  
		pane.getChildren().remove(text3);
		text3 = new Text(75, 475, "Your guesses so far have been: " + guesses);
		pane.getChildren().add(text3);
		
		//looks at how many incorrect guesses there have been and adds parts of person's body depending on number
		switch(count){
		case 1:
			if(!pane.getChildren().contains(circle)){
				pane.getChildren().add(circle);
			}
			break;
		case 2: 
			if(!pane.getChildren().contains(body)){
				pane.getChildren().add(body);
			}
			break;
		case 3:
			if(!pane.getChildren().contains(rightArm)){
				pane.getChildren().add(rightArm);
			}
			break;
		case 4:
			if(!pane.getChildren().contains(leftArm)){
				pane.getChildren().add(leftArm);
			}
			break;
		case 5: 
			if(!pane.getChildren().contains(leftLeg)){
				pane.getChildren().add(leftLeg);
			}
			break;
		case 6:  //this one is triggered if the whole person is hanging and therefore the game is over
			if(!pane.getChildren().contains(rightLeg)){
				pane.getChildren().add(rightLeg);
			}
			pane.getChildren().removeAll(button, text1, text3); //remove button if player loses
			text2 = new Text(75, 475, "You Lost, the word was " + word);
			pane.getChildren().add(text2);
			break;
		}
		//checks for whether players word is correct, if it is then they win
		if(word.equals(correct)){
			pane.getChildren().removeAll(text1, text3);
			text1 = new Text(75, 425, "Good Job, You Won!");
			pane.getChildren().add(text1);
			pane.getChildren().remove(button);
		}
	}
	//method to check if the char guessed is in the actual word
	public static boolean charInWord(char c){
		for(char element: charWord){
			if(c == element){
				return true;
			}
		}
		return false;
	}
	//method to check if the char guessed has been guessed before
	public static boolean isDuplicate(char c){
		for(char element: guesses.toCharArray()){
			if(c == element){
				return true;
			}
		}
		return false;
	}

public static void main(String[] args) {
	    launch(args);
	  }
}