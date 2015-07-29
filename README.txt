Coding exercise: Simple Tic-Tac-Toe game
Author:  Abigail Siner
July 26, 2015
==========================================

This is the simple implementation of Tic-Tac-Toe.

It is presumed to be the first iteration of a more complex version, so only the most basis features have been implemented.

The application currently only works for a console user.  In lieu of the future GUI, the user is presented with a text representation of the current Tic-Tac-Toe Board, and asked to input a number that represents one of the free squares.  
Squares are numbered from 0 to 8.

For example, here is a sample game output:

X - - 
- X - 
O - O 

Pick one of the free squares [1, 2, 3, 5, 7]: 



The app is currently single-threaded, and always uses the computer as the other player.
However, the back-end TttBoard class was implemented in a thread-safe manner, in expectation that a future iteration would allow multiple simultaneous games and players.  

This exercise uses:
 1) Java 1.8
 2) Maven
 3) Spring
 4) I18N (French supported; English is default)



To run the application:
 - unzip asiner-simple-ttt.zip
 - cd simple-ttt
 - mvn compile (not necessary)
 - mvn exec:java -Dexec.mainClass=com.siner.ttt.TicTacToeApp
  
 To view Javadoc files:
 - Point browser to:  simple-ttt/target/apidocs/index.html

