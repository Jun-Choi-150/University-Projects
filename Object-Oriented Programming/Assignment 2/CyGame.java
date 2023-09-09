package hw2;

/**
 * 
 * @author Wonjun Choi
 * 
 */

public class CyGame {
	
	/**
	 * Do nothing square type.
	 */
	public static final int DO_NOTHING = 0;
	
	/**
	 * Pass go square type.
	 */
	public static final int PASS_GO = 1;
	
	/**
	 * Cyclone square type.
	 */
	public static final int CYCLONE = 2;
	
	/**
	 * Pay the other player square type.
	 */
	public static final int PAY_PLAYER = 3;
	
	/**
	 * Get an extra turn square type.
	 */
	public static final int EXTRA_TURN = 4;
	
	/**
	 * Jump forward square type.
	 */
	public static final int JUMP_FORWARD = 5;
	
	/**
	 * Stuck square type.
	 */
	public static final int STUCK = 6;
	
	/**
	 * Points awarded when landing on or passing over go.
	 */
	public static final int PASS_GO_PRIZE = 200;
	
	/**
	 * The amount payed to the other player per unit when landing on a PAY_PLAYER
	 * square.
	 */
	public static final int PAYMENT_PER_UNIT = 20;
	
	/**
	 * The amount of money required to win.
	 */
	public static final int MONEY_TO_WIN = 400;
	
	/**
	 * The cost of one unit.
	 */
	public static final int UNIT_COST = 50;

	/**
	 * An instance variables representing player1 and player2 information required for the game
	 * Information includes the amount of money the player currently has, the number of units, and their position on the board.
	 */
	private  int player1Money, player1Units, player1Square;
	private  int player2Money, player2Units, player2Square;

	/**
	 * An instance variable to know the number of squares on the game board set at the start of the game
	 */
	private int numSquares;
	
	/**
	 * An instance variable indicating whether the player who will play the game is player1 and player2.
	 */
	private  int currentPlayer;
	
	/**
	 * An instance variable to know the type of a particular square on the game board.
	 */
	private  int[] typeSquares;
	
	/**
	 * An instance variable indicating the value of the dice rolled by the current player.
	 */
	private int value;
	
	/**
	 * An instance variable to check if the player1 and player2 has turned one lap in the board game.
	 */
	private int count1, count2;
	
	/**
	 * An instance variable that checks if a conditional statement that operates on a class called 'commonIf1' or 'commonIf2' exists
	 */
	private int check;
	
	
	/**
	 * Constructs a game by a given the number of squares and a given starting money
	 * 
	 * @param numSquares
	 * The parameter sets the number of squares required on the game board.
	 * 
	 * @param startingMoney
	 * The parameter sets starting money of player required on the game board.
	 */
	public CyGame(int numSquares, int startingMoney) {
		this.numSquares = numSquares;

		typeSquares = new int[numSquares];

		for (int i = 0; i < numSquares; ++i) {

			if (i == 0) {
				typeSquares[i] = PASS_GO;
			}

			else if (i == numSquares - 1) {
				typeSquares[i] = CYCLONE;
			}

			else if (i % 5 == 0) {
				typeSquares[i] = PAY_PLAYER;
			}

			else if ((i % 7 == 0) || (i % 11 == 0)) {
				typeSquares[i] = EXTRA_TURN;
			}

			else if (i % 3 == 0) {
				typeSquares[i] = STUCK;
			}

			else if (i % 2 == 0) {
				typeSquares[i] = JUMP_FORWARD;
			}

			else {
				typeSquares[i] = DO_NOTHING;
			}

		}

		// Setting player's money default before the game starts
		player1Money = startingMoney;
		player2Money = startingMoney;

		// Setting player's the number of units before the game starts
		player1Units = 1;
		player2Units = 1;

		// Setting the player's position before the game starts
		player1Square = 0;
		player2Square = 0;

		// Set the order of players
		currentPlayer = 1;

	}
	

	/**
	 * Void method that is applied according to the type of square after player 1 or player 2 roll dice
	 * 
	 * @param value
	 * The parameter indicating the value of the dice rolled by the current player.
	 *
	 */
	public void roll(int value) {

		this.value = value;
		
		if((isGameEnded()==false)) {
			
			//Player1
			if (getCurrentPlayer() == 1) {
				if (typeSquares[player1Square] != STUCK) {
					commonIf1(value);
					if (check != 1) {
						jumpForward1();
					}
				}

				else if (typeSquares[player1Square] == STUCK) {
					
					if(value % 2 == 0) {
						commonIf1(value);
						if (check != 1) {
							jumpForward1();
						}
					}
					
					if(value%2 != 0) {
						endTurn();
					}

				}
			}

			//Player2
			else if (getCurrentPlayer() == 2) {
				if (typeSquares[player2Square] != STUCK) {
					commonIf2(value);
					if (check != 1) {
						jumpForward2();
					}
				}

				else if (typeSquares[player2Square] == STUCK) {
					
					if(value % 2 == 0) {
						commonIf2(value);
						if (check != 1) {
							jumpForward2();
						}
					}
					
					if(value%2 != 0) {
						endTurn();
					}

				}
				
				
			}

			resetCount();
			check = 0;
		}

		
	}

	
	/**
	 * A void method that works when player 1 is positioned on a square called 'JUMP_FORWARD'.
	 */
	private void jumpForward1() {

			player1Square += value;
			count1 += value;
			if(player1Square>=numSquares) {
				player1Square -= numSquares;
			}
			

			if (count1 >= numSquares) {
				player1Money += PASS_GO_PRIZE;
				isGameEnded();
			}

			if ((((isGameEnded()) == false)) && (typeSquares[player1Square] == JUMP_FORWARD)) {
				player1Square += 4;
				count1 += 4;
				if(player1Square>=numSquares) {
					player1Square -= numSquares;
				}
				
				
				if (count1 >= numSquares) {
					player1Money += PASS_GO_PRIZE;
					isGameEnded();
				}

				if ((((isGameEnded()) == false)) && (typeSquares[player1Square] == JUMP_FORWARD)) {

					player1Square += 4;
					count1 += 4;
					if(player1Square>=numSquares) {
						player1Square -= numSquares;
					}
					
					if (count1 >= numSquares) {
						player1Money += PASS_GO_PRIZE;
						isGameEnded();
					}
				}

			
			}
			endTurn();
		}

	
	/**
	 * A void method that works when player 2 is positioned on a square called 'JUMP_FORWARD'.
	 */
	private void jumpForward2() {

			player2Square += value;
			count2 += value;
			if(player2Square>=numSquares) {
				player2Square -= numSquares;
			}

			
			if (count2 >= numSquares) {
				player2Money += PASS_GO_PRIZE;
				isGameEnded();
			}

			if ((((isGameEnded()) == false)) && (typeSquares[player2Square] == JUMP_FORWARD)) {
				player2Square += 4;
				count2 += 4;
				if(player2Square>=numSquares) {
					player2Square -= numSquares;
				}
				
				
				if (count2 >= numSquares) {
					player2Money += PASS_GO_PRIZE;
					isGameEnded();
				}

				if ((((isGameEnded()) == false)) && (typeSquares[player2Square] == JUMP_FORWARD)) {

					player2Square += 4;
					count2 += 4;
					if(player2Square>=numSquares) {
						player2Square -= numSquares;
					}
					
					if (count2 >= numSquares) {
						player2Money += PASS_GO_PRIZE;
						isGameEnded();
					}
				}

				endTurn();
			}

		}


	/**
	 * Int method that includes conditional statements for other types than 'jump forward',
	 * and allows player 1 to apply if there is a corresponding square where player 1 is positioned
	 * 
	 * @param a
	 * The parameter has same role as 'value', and is a parameter to get the value of the dice rolled by the current player.
	 * 
	 * @return
	 * Returns a variable called check, which indicates that if there is a square type to which player1 corresponds, it is 1, otherwise it is 0.
	 */
	private int commonIf1(int a) {

				player1Square += a;
				count1 += a;
				
				if(player1Square>=numSquares) {
					player1Square -= numSquares;
				}
				
				if (count1 > numSquares) {
					player1Money += PASS_GO_PRIZE;
					isGameEnded();
				}

				if (typeSquares[player1Square] == PASS_GO) {
					player1Money += PASS_GO_PRIZE;
					check = 1;
					isGameEnded();
					endTurn();
				}

				else if (typeSquares[player1Square] == CYCLONE) {
					player1Square = player2Square;
					check = 1;
					count1 = player1Square;
					endTurn();
				}

				else if (typeSquares[player1Square] == PAY_PLAYER) {
					player1Money -= player2Units * PAYMENT_PER_UNIT;
					player2Money += player2Units * PAYMENT_PER_UNIT;
					check = 1;
					isGameEnded();
					endTurn();
				}

				else if(typeSquares[player1Square] == EXTRA_TURN) {
					check = 1;
				}
				
				else if(typeSquares[player1Square] == DO_NOTHING) {
					check = 1;
					endTurn();
				}
				
				else if(typeSquares[player1Square] == STUCK) {
					check = 1;
					endTurn();
				}
				
				else {
					if (count1 >= numSquares) {
						player1Square += numSquares;
						player1Square -= a;
					} else {
						player1Square -= a;
					}
				}
				return check;
			}


	/**
	 * Int method that includes conditional statements for other types than 'jump forward',
	 * and allows player 2 to apply if there is a corresponding square where player 2 is positioned
	 * 
	 * @param a
	 * The parameter has same role as 'value', and is a parameter to get the value of the dice rolled by the current player.
	 * 
	 * @return
	 * Returns a variable called check, which indicates that if there is a square type to which player2 corresponds, it is 1, otherwise it is 0.
	 */
	private int commonIf2(int a) {

				player2Square += a;
				count2 += a;
				
				if(player2Square>=numSquares) {
					player2Square -= numSquares;
				}

				if (count2 > numSquares) {
					player2Money += PASS_GO_PRIZE;
					isGameEnded();
				}
				
				if (typeSquares[player2Square] == PASS_GO) {
					player2Money += PASS_GO_PRIZE;
					check = 1;
					isGameEnded();
					endTurn();
				}

				else if (typeSquares[player2Square] == CYCLONE) {
					player2Square = player1Square;
					check = 1;
					count2 = player2Square;
					endTurn();
				}

				else if (typeSquares[player2Square] == PAY_PLAYER) {
					player2Money -= player1Units * PAYMENT_PER_UNIT;
					player1Money += player1Units * PAYMENT_PER_UNIT;
					check = 1;
					isGameEnded();
					endTurn();
				}

				else if(typeSquares[player2Square] == EXTRA_TURN) {
					check = 1;
				}
				
				else if(typeSquares[player2Square] == DO_NOTHING) {
					check = 1;
					endTurn();
				}
				
				else if(typeSquares[player2Square] == STUCK) {
					check = 1;
					endTurn();
				}
				
				else {
					if (count2 >= numSquares) {
						player2Square += numSquares;
						player2Square -= a;
					} else {
						player2Square -= a;
					}
				}
				return check;
	}

	
	/**
	 * Void method that resets the 'count' value when player1 and player2 turned one lap on the board game.
	 */
	private void resetCount() {
		
		if(count1>=numSquares) {
			count1 = count1 - numSquares;
		}
		if(count2>=numSquares) {
			count2 = count2 - numSquares;
		}
	}
	
	/**
	 * Int method that lets you know what type of square the corresponding square is when you enter the number of the board game square.
	 * 
	 * @param square
	 * The parameter represents the number of the board game square.
	 * 
	 * @return
	 * Returns the type of the board game square of the entered number.
	 * 
	 */
	public int getSquareType(int square) {

		return typeSquares[square];
	}

	
	/**
	 * When a player sells a unit, a void method that checks whether the sales conditions are met and then causes the sales process to work.
	 */
	public void sellUnit() {
		if (isGameEnded() == false) {
			if ((getCurrentPlayer() == 1)&&(player1Units>0))  {
				player1Units -= 1;
				player1Money += UNIT_COST;
				endTurn();

			}

			else if ((getCurrentPlayer() == 2)&&(player2Units>0))  {
				player2Units -= 1;
				player2Money += UNIT_COST;
				endTurn();
			}
		}

	}
	
	
	/**
	 * When a player buy a unit, a void method that checks whether the buy conditions are met and then causes the buying process to work.
	 */
	public void buyUnit() {
		if (isGameEnded() == false) {

			if ((getCurrentPlayer() == 1) && (typeSquares[player1Square] == DO_NOTHING)
					&& (player1Money >= UNIT_COST)) {
				player1Money -= UNIT_COST;
				player1Units += 1;
				endTurn();

			}

			else if ((getCurrentPlayer() == 2) && (typeSquares[player2Square] == DO_NOTHING)
					&& (player2Money >= UNIT_COST)) {
				player2Money -= UNIT_COST;
				player2Units += 1;
				endTurn();
			}

		}
	}

	
	/**
	 * Int method indicating who the current player is.
	 * 
	 * @return
	 * Return the current player.
	 */
	public int getCurrentPlayer() {
		return currentPlayer;
	}

	
	/**
	 * Void method that works to end a player's turn
	 */
	public void endTurn() {
		if (getCurrentPlayer() == 1) {
			currentPlayer = 2;
		} else {
			currentPlayer = 1;
		}

	}

	
	/**
	 * Boolean method to check if the game is over by checking the player's current condition
	 * 
	 * @return
	 * Returns true or false whether the game is over
	 * 
	 */
	public boolean isGameEnded() {
		if ((player1Money >= MONEY_TO_WIN) || (player2Money >= MONEY_TO_WIN) || (player1Money < 0) || (player2Money < 0)) {
			return true;
		} 
		
		else {
			return false;
		}
	}

	
	/**
	 * Get method to check player 1's current money status.
	 * 
	 * @return
	 * Return player1's money.
	 */
	public int getPlayer1Money() {
		return player1Money;
	}

	/**
	 * Get method to check player 1's current the number of units.
	 * 
	 * @return
	 * Return player1's units.
	 */
	public int getPlayer1Units() {
		return player1Units;
	}

	/**
	 * Get method to check where player 1 is on now.
	 * 
	 * @return
	 * Return player1's square.
	 */
	public int getPlayer1Square() {
		return player1Square;
	}

	// Player2
	/**
	 * Get method to check player 2's current money status.
	 * 
	 * @return
	 * Return player2's money.
	 */
	public int getPlayer2Money() {
		return player2Money;
	}

	/**
	 * Get method to check player 2's current the number of units.
	 * 
	 * @return
	 * Return player2's units.
	 */
	public int getPlayer2Units() {
		return player2Units;
	}

	/**
	 * Get method to check where player 2 is on now.
	 * 
	 * @return
	 * Return player2's square.
	 */
	public int getPlayer2Square() {
		return player2Square;
	}

	
	// The method below is provided for you and you should not modify it.
	// The compile errors will go away after you have written stubs for the
	// rest of the API methods.

	/**
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * <p>
	 * <tt>Player 1*: (0, 0, $0) Player 2: (0, 0, $0)</tt>
	 * <p>
	 * The asterisks next to the player's name indicates which players turn it is.
	 * The numbers (0, 0, $0) indicate which square the player is on, how many units
	 * the player has, and how much money the player has respectively.
	 * 
	 * @return one-line string representation of the game state
	 */
	public String toString() {
		String fmt = "Player 1%s: (%d, %d, $%d) Player 2%s: (%d, %d, $%d)";
		String player1Turn = "";
		String player2Turn = "";
		if (getCurrentPlayer() == 1) {
			player1Turn = "*";
		} else {
			player2Turn = "*";
		}
		return String.format(fmt, player1Turn, getPlayer1Square(), getPlayer1Units(), getPlayer1Money(), player2Turn,
				getPlayer2Square(), getPlayer2Units(), getPlayer2Money());
	}
}
