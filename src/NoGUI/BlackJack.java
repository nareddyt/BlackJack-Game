package NoGUI;


// BLACKJACK PROGRAM: This is not the official version. It is tweaked for the user to play against the computer...

// --------------------------------------------------------------------------------------------------------------\\
// TODO Better computer logic: slip of hand, better betting, and SPLITING \\
// TODO Fix boolean 'cFirst' --> Computer surrendered after hitting... \\
// TODO Fix: Don't ask for choice after computer surrenders on player hit, game progressing after computer > 21 \\
// TODO Fix: After player card total > 21, computer makes decision... \\
// TODO MAKE SURE: Once the computer stands, don't let it hit later!!!!! \\
// TODO FIX: After player hit, then stand, computer doesn't make a choice!!! \\
// TODO MAKE: PLAYER + Computer SPLITING. \\
// --------------------------------------------------------------------------------------------------------------\\

import java.util.Random;
import java.util.Scanner;

public class BlackJack
{
	public static Scanner console = new Scanner(System.in);
	public static Random rand = new Random();
	
	public static final int MONEY = 2500;
	public static final int MIN_BET = 100;
	
	public static int checkCardAce(int card)
	{
		if (card == 1)
			return 1;
		else
			return 0;
	}
	
	public static int getCardRandom()
	{
		int card = rand.nextInt(13) + 1;
		return card;
	}
	
	public static int getCardValue(int cardNum)
	{
		if (cardNum > 1 && cardNum < 10)
			return cardNum;
		else if (cardNum >= 10 && cardNum <= 13)
			return 10;
		else if (cardNum == 1)
			return 11;
		else
			throw new IllegalArgumentException("Number of the card must be between 1 and 13, not: " + cardNum);
	}
	
	public static int getComputerBet(int cMoney, int pBet)
	{
		int bet;
		int random;
		if (cMoney <= MIN_BET)
		{
			bet = cMoney;
			System.out.println("The computer is going all in!!!\n");
		}
		else
		{
			random = rand.nextInt(1000) - 500;
			bet = random + pBet;
			if (cMoney < 1000) // Bets less if money is low, more if money is high
			{
				bet -= rand.nextInt(cMoney / 5);
			}
			else
			{
				bet += rand.nextInt(cMoney / 5);
			}
			
			if (bet > cMoney)
			{
				bet = cMoney;
			}
			if (bet <= MIN_BET)
			{
				bet = MIN_BET;
			}
			System.out.println("The computer is now betting $" + bet + ".\n");
		}
		return bet;
	}
	
	public static String getComputerChoice(int total, int aces, int c1, int c2, boolean first)
	{
		String choice = "";
		int rand1 = rand.nextInt(3) - 1;
		int rand2 = rand.nextInt(4) + 1;
		int rand3 = rand.nextInt(2) + 1;
		if (aces > 1 && rand3 == 2)
		{
			choice = "hit";
		}
		else if (first && c1 == c2 && rand3 == 1)
		{
			choice = "split";
			
			System.out.println("The computer has choosen split, but will change to stand because split is not supported now...");
			choice = "stand";
		}
		else if (total < 19)
		{
			if (rand2 == 3 && total >= 16)
			{
				choice = "surrender";
			}
			else if (rand1 == 1 && total >= 16)
			{
				choice = "stand";
			}
			else if (rand3 == 1 && (total >= 9 && total <= 11) && first)
			{
				choice = "double";
			}
			else
			{
				choice = "hit";
			}
		}
		else
		{
			if (rand2 == 3)
			{
				choice = "hit";
			}
			else
			{
				choice = "stand";
			}
		}
		return choice;
	}
	
	public static int getPlayerBet(int money)
	{
		int pBet;
		boolean greater;
		do
		{
			greater = true;
			System.out.println("\n\nYou have $" + money + " to Bet.");
			System.out.println("The minimum you can Bet is $" + MIN_BET + ".");
			if (money > MIN_BET)
			{
				System.out.print("Enter your Bet: $");
				while (!console.hasNextInt())
				{
					console.next(); // to discard the input
					System.out.println("Not an integer! Try again.");
					System.out.print("Please enter in a number between $" + MIN_BET + " and $" + money + " (inclusive): $");
				}
				pBet = console.nextInt();
				if (pBet < MIN_BET)
				{
					greater = false;
				}
			}
			else
			{
				System.out.println("You are going all in! You have less than or the same amount of money as the minimum Bet...");
				pBet = money;
				waitEnter(true);
			}
		}
		while (pBet > money || !greater);
		return pBet;
	}
	
	public static int getPlayerChoice(int card1, int card2, boolean first)
	{
		String choice;
		boolean again;
		do
		{
			again = true;
			System.out.print("You can choose from: Hit, Stand");
			if (first)
			{
				System.out.print(", Surrender, Double");
			}
			if (card1 == card2 && first)
			{
				System.out.print(", Split");
			}
			System.out.println(".");
			System.out.println("If you want to quit, type in \"quit\"...");
			System.out.print("Which one? ");
			choice = console.next();
			choice = choice.toLowerCase();
			if (choice.contains("hit"))
				return 1;
			else if (choice.contains("sta"))
				return 2;
			else if (choice.contains("sur") && first)
				return 3;
			else if (choice.contains("do") && first)
				return 4;
			else if (choice.contains("spl") && card1 == card2)
				return 5;
			else if (choice.contains("quit") || choice.contains("terminate") || choice.contains("stop"))
			{
				System.out.println("\n\nYou have decided to quit! YOU LOSE!!!\n\n");
				System.exit(1);
			}
			else
			{
				again = false;
			}
		}
		while (!again);
		return 0; // Never reaches this point
	}
	
	public static void giveIntro()
	{
		System.out.println("INTRODUCTION: ");
		waitEnter(true);
	}
	
	public static void main(String[] args)
	{
		giveIntro();
		playGame();
		System.exit(0);
	}
	
	public static void playGame()
	{
		
		int pMoney = MONEY;
		int cMoney = MONEY;
		int rounds = 0;
		
		while (pMoney >= 1 && cMoney >= 1)
		{
			rounds++;
			int c1 = getCardRandom();
			int c2 = getCardRandom();
			int p1 = getCardRandom();
			int p2 = getCardRandom();
			int cAce = 0;
			int pAce = 0;
			int cTotal = getCardValue(c1) + getCardValue(c2);
			int pTotal = getCardValue(p1) + getCardValue(p2);
			int choice;
			int pBet = MIN_BET;
			int cBet = MIN_BET;
			boolean pSurrender = false;
			boolean cSurrender = false;
			boolean first = true;
			boolean cFirst = true;
			String cChoice = "";
			
			pBet = getPlayerBet(pMoney);
			cBet = getComputerBet(cMoney, pBet);
			System.out.println("\n\nYour Starting Cards: ");
			showCardPlayer(p1);
			showCardPlayer(p2);
			
			// Check for BlackJack
			if ((pTotal == 21 && (p1 == 11 || p2 == 11)) || (cTotal == 21 && (c1 == 11 || c2 == 11)))
			{
				if (pTotal == 21 && cTotal == 21)
				{
					System.out.println("Both you and the computer have a BlackJack...");
					System.out.println("No money lost of gained...");
				}
				else if (pTotal == 21)
				{
					System.out.println("You have a BlackJack! You get 3/2 of your bet!!!");
					pBet *= 3 / 2.0;
				}
				else if (cTotal == 21)
				{
					System.out.println("The computer has a BlackJack...");
					cBet *= 3 / 2.0;
				}
				else
					throw new IllegalArgumentException(
							"\n\n\n\nWhoops... Program broke while checking for BlackJack (Ace + Jack)");
			}
			else
			{
				slipHandComputer(c1, c2);
				pAce += checkCardAce(p1) + checkCardAce(p2);
				cAce += checkCardAce(c1) + checkCardAce(c2);
				if (pAce > 0 && pTotal > 21)
				{
					pAce--;
					pTotal -= 10;
				}
				if (cAce > 0 && cTotal > 21)
				{
					cAce--;
					cTotal -= 10;
				}
				System.out.println("You total is now " + pTotal + ".\n");
				System.out.println("You total is now " + pTotal + ".\n");
				System.out.println("The total value of your cards is " + pTotal + ".\n");
				choice = getPlayerChoice(p1, p2, first);
				first = false;
				
				if (choice == 4) // double
				{
					pBet *= (3 / 2.0);
					if (pBet >= pMoney)
					{
						pBet = pMoney;
						System.out.println("You are going all in!!!");
					}
					else
					{
						System.out.println("You are betting $" + pBet + " now.");
					}
					p1 = showCardPlayer(getCardRandom());
					pTotal += getCardValue(p1);
					pAce += checkCardAce(p1);
					if (pAce > 0 && pTotal > 21)
					{
						pAce--;
						pTotal -= 10;
					}
					System.out.println("You total is now " + pTotal + ".\n");
					
					cChoice = getComputerChoice(cTotal, cAce, c1, c2, cFirst);
					cFirst = false;
					
					if (cChoice.equals("hit"))
					{
						System.out.println("The computer decided to hit.");
						c1 = getCardRandom();
						cTotal += getCardValue(c1);
						cAce += checkCardAce(c1);
						// Ace optimizing
						if (cAce > 0 && cTotal > 21)
						{
							cAce--;
							cTotal -= 10;
						}
						slipHandComputer(c1, c1);
					}
					else if (cChoice.equals("stand"))
					{
						System.out.println("The computer decided to stand...");
					}
					else if (cChoice.equals("double"))
					{
						System.out.println("The computer had decided to double...");
						cBet *= (3 / 2.0);
						if (cBet >= cMoney)
						{
							System.out.println("The computer is now going all in!");
							cBet = cMoney;
						}
						else
						{
							System.out.println("The computer is now betting $" + cBet);
						}
						// Normal hit now
						c1 = getCardRandom();
						cTotal += getCardValue(c1);
						// Ace optimizing
						cAce += checkCardAce(c1);
						if (cAce > 0 && cTotal > 21)
						{
							cAce--;
							cTotal -= 10;
						}
						slipHandComputer(c1, c1);
					}
					else if (cChoice.equals("surrender"))
					{
						cSurrender = true;
						System.out.println("The computer surrendered!");
					}
					else if (cChoice.equals("split"))
					{
						cChoice = "hit"; // temp case
						// TODO add split logic
					}
					else
						throw new IllegalArgumentException("The choice is: " + cChoice);
					
				}
				if (choice == 1) // Hit
				{
					do
					{
						if (choice == 1)
						{
							p1 = showCardPlayer(getCardRandom());
							pTotal += getCardValue(p1);
							pAce += checkCardAce(p1);
							if (pAce > 0 && pTotal > 21)
							{
								pAce--;
								pTotal -= 10;
							}
							
							System.out.println("You total is now " + pTotal + ".\n");
							
							cChoice = getComputerChoice(cTotal, cAce, c1, c2, cFirst);
							cFirst = false;
							
							if (cChoice.equals("hit"))
							{
								System.out.println("The computer decided to hit.");
								c1 = getCardRandom();
								cTotal += getCardValue(c1);
								cAce += checkCardAce(c1);
								// Ace optimizing
								if (cAce > 0 && cTotal > 21)
								{
									cAce--;
									cTotal -= 10;
								}
								slipHandComputer(c1, c1);
							}
							else if (cChoice.equals("stand"))
							{
								System.out.println("The computer decided to stand...");
							}
							else if (cChoice.equals("double"))
							{
								System.out.println("The computer had decided to double...");
								cBet *= (3 / 2.0);
								if (cBet >= cMoney)
								{
									System.out.println("The computer is now going all in!");
									cBet = cMoney;
								}
								else
								{
									System.out.println("The computer is now betting $" + cBet);
								}
								// Normal hit now
								c1 = getCardRandom();
								cTotal += getCardValue(c1);
								// Ace optimizing
								cAce += checkCardAce(c1);
								if (cAce > 0 && cTotal > 21)
								{
									cAce--;
									cTotal -= 10;
								}
								slipHandComputer(c1, c1);
							}
							else if (cChoice.equals("surrender"))
							{
								cSurrender = true;
								System.out.println("The computer surrendered!");
							}
							else if (cChoice.equals("split"))
							{
								cChoice = "hit"; // temp case
								// TODO add split logic
							}
							else
								throw new IllegalArgumentException("The choice is: " + cChoice);
							
							if (pTotal <= 21 && cTotal <= 21)
							{
								choice = getPlayerChoice(p1, p2, first);
							}
						}
						else if (choice == 2) // stand
						{
							while (!cChoice.equals("stand") && !cChoice.equals("surrender") && cTotal <= 21)
							{
								cChoice = getComputerChoice(cTotal, cAce, c1, c2, cFirst);
								cFirst = false;
								
								if (cChoice.equals("hit"))
								{
									System.out.println("The computer decided to hit.");
									c1 = getCardRandom();
									cTotal += getCardValue(c1);
									cAce += checkCardAce(c1);
									// Ace optimizing
									if (cAce > 0 && cTotal > 21)
									{
										cAce--;
										cTotal -= 10;
									}
									slipHandComputer(c1, c1);
								}
								else if (cChoice.equals("stand"))
								{
									System.out.println("The computer decided to stand...");
								}
								else if (cChoice.equals("double"))
								{
									System.out.println("The computer had decided to double...");
									cBet *= (3 / 2.0);
									if (cBet >= cMoney)
									{
										System.out.println("The computer is now going all in!");
										cBet = cMoney;
									}
									else
									{
										System.out.println("The computer is now betting $" + cBet);
									}
									// Normal hit now
									c1 = getCardRandom();
									cTotal += getCardValue(c1);
									// Ace optimizing
									cAce += checkCardAce(c1);
									if (cAce > 0 && cTotal > 21)
									{
										cAce--;
										cTotal -= 10;
									}
									slipHandComputer(c1, c1);
								}
								else if (cChoice.equals("surrender"))
								{
									cSurrender = true;
									System.out.println("The computer surrendered!");
								}
								else if (cChoice.equals("split"))
								{
									cChoice = "hit"; // temp case
									// TODO add split logic
								}
								else
									throw new IllegalArgumentException("The choice is: " + cChoice);
								// TODO --------------------- finish + TEST computer play ----------------------------cChoice =
								// getComputerChoice(cTotal, cAce, c1, c2, cFirst);
								cFirst = false;
								
								if (cChoice.equals("hit"))
								{
									System.out.println("The computer decided to hit.");
									c1 = getCardRandom();
									cTotal += getCardValue(c1);
									cAce += checkCardAce(c1);
									// Ace optimizing
									if (cAce > 0 && cTotal > 21)
									{
										cAce--;
										cTotal -= 10;
									}
									slipHandComputer(c1, c1);
								}
								else if (cChoice.equals("stand"))
								{
									System.out.println("The computer decided to stand...");
								}
								else if (cChoice.equals("double"))
								{
									System.out.println("The computer had decided to double...");
									cBet *= (3 / 2.0);
									if (cBet >= cMoney)
									{
										System.out.println("The computer is now going all in!");
										cBet = cMoney;
									}
									else
									{
										System.out.println("The computer is now betting $" + cBet);
									}
									// Normal hit now
									c1 = getCardRandom();
									cTotal += getCardValue(c1);
									// Ace optimizing
									cAce += checkCardAce(c1);
									if (cAce > 0 && cTotal > 21)
									{
										cAce--;
										cTotal -= 10;
									}
									slipHandComputer(c1, c1);
								}
								else if (cChoice.equals("surrender"))
								{
									cSurrender = true;
									System.out.println("The computer surrendered!");
								}
								else if (cChoice.equals("split"))
								{
									cChoice = "hit"; // temp case
									// TODO add split logic
								}
								else
									throw new IllegalArgumentException("The choice is: " + cChoice);
							}
						}
					}
					while (pTotal <= 21 && choice == 1 && cTotal <= 21);
				}
				else if (choice == 2) // Stand
				{
					// Let computer play until it stands, surrenders, or loses...
					System.out.println("\nThe computer will now decide what to do.");
					while (!cChoice.equals("stand") && !cChoice.equals("surrender") && !cChoice.equals("double") && cTotal <= 21)
					{
						cChoice = getComputerChoice(cTotal, cAce, c1, c2, cFirst);
						cFirst = false;
						
						if (cChoice.equals("hit"))
						{
							System.out.println("The computer decided to hit.");
							c1 = getCardRandom();
							cTotal += getCardValue(c1);
							cAce += checkCardAce(c1);
							// Ace optimizing
							if (cAce > 0 && cTotal > 21)
							{
								cAce--;
								cTotal -= 10;
							}
							slipHandComputer(c1, c1);
						}
						else if (cChoice.equals("stand"))
						{
							System.out.println("The computer decided to stand...");
						}
						else if (cChoice.equals("double"))
						{
							System.out.println("The computer had decided to double...");
							cBet *= (3 / 2.0);
							if (cBet >= cMoney)
							{
								System.out.println("The computer is now going all in!");
								cBet = cMoney;
							}
							else
							{
								System.out.println("The computer is now betting $" + cBet);
							}
							// Normal hit now
							c1 = getCardRandom();
							cTotal += getCardValue(c1);
							// Ace optimizing
							cAce += checkCardAce(c1);
							if (cAce > 0 && cTotal > 21)
							{
								cAce--;
								cTotal -= 10;
							}
							slipHandComputer(c1, c1);
						}
						else if (cChoice.equals("surrender"))
						{
							cSurrender = true;
							System.out.println("The computer surrendered!");
						}
						else if (cChoice.equals("split"))
						{
							cChoice = "hit"; // temp case
							// TODO add split logic
						}
						else
							throw new IllegalArgumentException("The choice is: " + cChoice);
					}
				}
				else if (choice == 3) // Surrender
				{
					pSurrender = true;
				}
				else
				// Split: choice 5
				{
					int pTotal1 = p1;
					int pTotal2 = p2;
					int pAce1 = checkCardAce(p1);
					int pAce2 = checkCardAce(p2);
					do
					{
						if (choice == 1)
						{
							p1 = showCardPlayer(getCardRandom());
							pTotal1 += getCardValue(p1);
							pAce1 += checkCardAce(p1);
							if (pAce1 > 0 && pTotal1 > 21)
							{
								pAce1--;
								pTotal1 -= 10;
							}
							
							p2 = showCardPlayer(getCardRandom());
							pTotal2 += getCardValue(p2);
							pAce2 += checkCardAce(p2);
							if (pAce2 > 0 && pTotal2 > 21)
							{
								pAce2--;
								pTotal2 -= 10;
							}
							
							System.out.println("Your hand totals are now: " + pTotal1 + " and " + pTotal2);
							cChoice = getComputerChoice(cTotal, cAce, c1, c2, cFirst);
							first = false;
							
							if (cChoice.equals("hit"))
							{
								System.out.println("The computer decided to hit.");
								c1 = getCardRandom();
								cTotal += getCardValue(c1);
								cAce += checkCardAce(c1);
								// Ace optimizing
								if (cAce > 0 && cTotal > 21)
								{
									cAce--;
									cTotal -= 10;
								}
								slipHandComputer(c1, c1);
							}
							else if (cChoice.equals("stand"))
							{
								System.out.println("The computer decided to stand...");
							}
							else if (cChoice.equals("double"))
							{
								System.out.println("The computer had decided to double...");
								cBet *= (3 / 2.0);
								if (cBet >= cMoney)
								{
									System.out.println("The computer is now going all in!");
									cBet = cMoney;
								}
								else
								{
									System.out.println("The computer is now betting $" + cBet);
								}
								// Normal hit now
								c1 = getCardRandom();
								cTotal += getCardValue(c1);
								// Ace optimizing
								cAce += checkCardAce(c1);
								if (cAce > 0 && cTotal > 21)
								{
									cAce--;
									cTotal -= 10;
								}
								slipHandComputer(c1, c1);
							}
							else if (cChoice.equals("surrender"))
							{
								cSurrender = true;
								System.out.println("The computer surrendered!");
							}
							else if (cChoice.equals("split"))
							{
								cChoice = "hit"; // temp case
								// TODO add split logic
							}
							else
								throw new IllegalArgumentException("The choice is: " + cChoice);
							// TODO --------------------- finish + TEST computer play ----------------------------
							if (pTotal <= 21 && cTotal <= 21)
							{
								choice = getPlayerChoice(p1, p2, first);
							}
						}
						else if (choice == 3)
						{
							pSurrender = true;
						}
						if (choice == 2)
						{
							while (!cChoice.equals("stand") && !cChoice.equals("surrender") && cTotal <= 21)
							{
								cChoice = getComputerChoice(cTotal, cAce, c1, c2, cFirst);
								// TODO add computer play
							}
						}
					}
					while (pTotal <= 21 && choice == 1 && cTotal <= 21);
				}
			} // End of: 1st cards = 21 else statement
			System.out.println("\n\n<<< End of round >>>");
			System.out.println("\n< Round Insights >");
			System.out.println("Your card total: " + pTotal);
			System.out.println("Computer card total: " + cTotal);
			waitEnter(false);
			
			if (!pSurrender && !cSurrender)
			{
				if (pTotal > 21)
				{
					pMoney -= pBet;
					cMoney += cBet;
					System.out.println("\nYour card total is above 21...");
					System.out.println("The computer won against you... You lost $" + pBet + ".");
					System.out.println("Now you have $" + pMoney + ".");
					System.out.println("The computer now has $" + cMoney + ".");
				}
				else if (cTotal > 21)
				{
					pMoney += pBet;
					cMoney -= cBet;
					System.out.println("\nThe computer's card total is above 21...");
					System.out.println("The computer lost against you! You won $" + pBet + ".");
					System.out.println("Now you have $" + pMoney + ".");
					System.out.println("The computer now has $" + cMoney + ".");
				}
				else if (cTotal > pTotal)
				{
					pMoney -= pBet;
					cMoney += cBet;
					System.out.println("\nThe computer has a card total greater than yours but less than 22...");
					System.out.println("The computer won against you... You lost $" + pBet + ".");
					System.out.println("Now you have $" + pMoney + ".");
					System.out.println("The computer now has $" + cMoney + ".");
				}
				else if (pTotal > cTotal)
				{
					pMoney += pBet;
					cMoney -= cBet;
					System.out.println("\nYour card total is greater than the computer's and less than 22!");
					System.out.println("The computer lost against you! You won $" + pBet + ".");
					System.out.println("Now you have $" + pMoney + ".");
					System.out.println("The computer now has $" + cMoney + ".");
				}
				else
				{
					System.out.println("\nTie game, no money lost or gained......");
				}
			}
			else
			{
				if (pSurrender)
				{
					pMoney -= (1 / 2.0) * pBet;
					cMoney += cBet;
					System.out.println("\nYou surrendered... You lost $" + pBet * (1 / 2.0) + ".");
					System.out.println("However, you did not lose $" + pBet + " because you surrendered....");
					System.out.println("Now you have $" + pMoney + ".");
					System.out.println("The computer now has $" + cMoney + ".");
				}
				else
				{
					pMoney += pBet;
					cMoney -= (1 / 2.0) * cBet;
					System.out.println("\nThe computer surrendered!!! You got $" + pBet + ".");
					System.out.println("Now you have $" + pMoney + ".");
					System.out.println("The computer now has $" + cMoney + ".");
				}
			}
		} // end of while loop
		
		System.out.println("\n\n<---GAME OVER--->\n");
		if (cMoney > pMoney)
		{
			System.out.println("::: YOU LOST :::");
		}
		else
		{
			System.out.println("<<< YOU WON >>>");
		}
		showStats(rounds);
		return;
	}
	
	public static int showCardPlayer(int cardNum)
	{
		switch (cardNum)
		{
			case 1:
				System.out.println("You got an Ace!");
				break;
			case 2:
				System.out.println("You got a 2.");
				break;
			case 3:
				System.out.println("You got a 3.");
				break;
			case 4:
				System.out.println("You got a 4.");
				break;
			case 5:
				System.out.println("You got a 5.");
				break;
			case 6:
				System.out.println("You got a 6.");
				break;
			case 7:
				System.out.println("You got a 7.");
				break;
			case 8:
				System.out.println("You got a 8.");
				break;
			case 9:
				System.out.println("You got a 9.");
				break;
			case 10:
				System.out.println("You got a 10.");
				break;
			case 11:
				System.out.println("You got a Jack.");
				break;
			case 12:
				System.out.println("You got a Queen.");
				break;
			case 13:
				System.out.println("You got a King.");
				break;
			default:
				throw new IllegalArgumentException("Number of the card must be Between 1 and 13, not: " + cardNum);
		}
		return cardNum;
	}
	
	public static void showStats(int games)
	{
		// TODO Add more stats here
		System.out.println("\n\n---STATISTICS---\n");
		System.out.println("Rounds played: \t" + games);
		
	}
	
	public static void slipHandComputer(int cardNum1, int cardNum2) // TODO Check Probability
	{
		int random = rand.nextInt(15) + 1;
		int number = rand.nextInt(19) + 1;
		int card;
		int choice;
		if ((random == number || random == 2 * number || random == number / 2) && (random != number - 1 || random != number + 1))
		{
			card = rand.nextInt(2) + 1;
			choice = rand.nextInt(3) + 1;
			if (choice == 1)
			{
				System.out.print("The computer accidently showed you that one of its card is a ");
			}
			else if (choice == 2)
			{
				System.out.print("The computer dropped one of its cards! It is a ");
			}
			else
			{
				System.out.print("As the computer was drawing a card, you saw that it took a ");
			}
			if (card == 1)
			{
				switch (cardNum2)
				{
					case 1:
						System.out.print("Ace");
						break;
					case 2:
						System.out.print("2");
						break;
					case 3:
						System.out.print("3");
						break;
					case 4:
						System.out.print("4");
						break;
					case 5:
						System.out.print("5");
						break;
					case 6:
						System.out.print("6");
						break;
					case 7:
						System.out.print("7");
						break;
					case 8:
						System.out.print("8");
						break;
					case 9:
						System.out.print("9");
						break;
					case 10:
						System.out.print("10");
						break;
					case 11:
						System.out.print("Jack");
						break;
					case 12:
						System.out.print("Queen");
						break;
					case 13:
						System.out.print("King");
						break;
					default:
						throw new IllegalArgumentException("Number of the card must be Between 1 and 13, not: " + cardNum2);
				}
			}
			else
			{
				switch (cardNum1)
				{
					case 1:
						System.out.print("Ace");
						break;
					case 2:
						System.out.print("2");
						break;
					case 3:
						System.out.print("3");
						break;
					case 4:
						System.out.print("4");
						break;
					case 5:
						System.out.print("5");
						break;
					case 6:
						System.out.print("6");
						break;
					case 7:
						System.out.print("7");
						break;
					case 8:
						System.out.print("8");
						break;
					case 9:
						System.out.print("9");
						break;
					case 10:
						System.out.print("10");
						break;
					case 11:
						System.out.print("Jack");
						break;
					case 12:
						System.out.print("Queen");
						break;
					case 13:
						System.out.print("King");
						break;
					default:
						throw new IllegalArgumentException("Number of the card must be Between 1 and 13, not: " + cardNum2);
				}
			}
			System.out.println("...");
		}
	}
	
	public static void waitEnter(boolean first)
	{
		if (!first)
		{
			console.nextLine();
		}
		System.out.print("Press enter to continue...");
		console.nextLine();
	}
}
