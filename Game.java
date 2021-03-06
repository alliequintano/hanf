/* this is one game, consisting of 4 rounds
controlls the board (and everything...)

create jframe
  draw on jpanel representation of the board and the current players hand


responsible for asking board for its state and asking player for hand, then the game can draw itself

------------

/* there are four rounds in one game


each round has different initial point values


responsibilities:

keep track of whose turn it is, who is taking the next turn


methods:
taketurn - take something about game state (board?) - round will have player take this method (which ever player's turn)
 ex: player.taketurn(board);


 */

/*  methods defined:

 main(String[] args);
 start();


 */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JFrame;

public class Game extends Canvas { // Extends Canvas, must
									// have somewhere to
									// draw.

	boolean finished = false;
	int[] initialPoints = { 50, 90, 120, 150 };
	int round = 0;
	final int WIDTH = 800, HEIGHT = 700; // size of canvas,
											// in pixels.
	Team[] teams = new Team[2];
	Deck drawPile;
	DiscardPile discardPile;

	public Game() {
		// teams[0] = new Team();
		// teams[1] = new Team();

		/* GUI STUFF */
		JFrame frame = new JFrame("Hand and Foot");
		frame.add(this);
		// frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		/* END GUI STUFF */
	}

	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(50, 50, WIDTH, HEIGHT);
		repaint();
	}

	public static void main(String[] args) {

		new Game().start();

	}

	public void start() {
		while (!finished) {
			round(initialPoints[round]);

		}
	}

	public void round(int initialPoints) {
    discardPile = new DiscardPile(); // clears discard pile
    drawPile = drawPile.fullDeck();
    drawPile.shuffle();

    for (int i = 0; i < 11; i++) {
      for (Team t : teams) {
        for (Player p : t.players) { 
          p.hand.addCard(drawPile.drawCard());
          p.foot.addCard(drawPile.drawCard());
        }
      }
    }

    boolean roundFinished = false;

    while (!roundFinished) {


      team[0] // invoke player to take its turn;



          if (/*player has won = tablou has red and black book,
		  no threes in either players hand(foot),
		  both players on team in their foot,
		  must discard last card in hand*/)
            roundFinished = true;

    }

    round++;

    if (round > 3)
      finished = true;

  }

	/*
	 * begin mess of code for turn method(s)
	 */

	public void draw(Player p, String input){
		boolean hasDrawn = false;
		
		Scanner scan = new Scanner(System.in);
		System.out.print(p+"'s turn. Draw from drawPile or discardPile? ");
		String input = scan.nextLine();
		
		while (!hasDrawn) {
			if (input.equals("drawPile")) {
				p.hand.addCard(drawPile.drawCard());
				p.hand.addCard(drawPile.drawCard());
				
				hasDrawn = true;
			}
			
			if (input.equals("discardPile")) {
				// if tablou is open
				//// select at least two cards in hand that match topcard of discardPile
				
				//else --if tablou not open--
				//// select enough cards in hand to meet (initial starting point value - point value of topCard)
				
				
				// need a way to check if the player has two of the same cards as the top card in discard pile, and that they have the required initial points to open up their tablou using only the top card if neccassry
				if ((p.hand.cardFrequency(discardPile.topCard()) >= 2)
						&& (p.getTablou().isOpen() || /* (p.getTablou.count() +  ) */ ) {				
					
					hasDrawn = true;
				}
			}
		}
	}

	public void open(Player p) {
		// have to select enough cards in hand to LEGALLY
		// add up to required
		// initial point value.
		boolean opened = false;
		List<Card> selected = new ArrayList<Card>();

		while (!opened) {
			Scanner scan = new Scanner(System.in);
			System.out.println("\nType 'cancel' to cancel: ");
			String input = scan.nextLine();

			if (input.equals("cancel")) {
				return;
			} else {
				selected.add(selectCard(p));
			}

			// TO DO: error check for adding cards to
			// 'selected' arraylist -- must be same
			// value as prior with expection of wilds

			System.out.print("\nType (1) to select another card, (2) to play selected card(s), (3) to finish opening: ");
			input = scan.next();

			switch (input) {
			case "1":
				break;
			case "2":
				play(p, selected);
				break;
			case "3":
				if (count(/* arraylist of all cards on tablou */) >= initialPoints[round]) {
					opened = true;
				} else {
					System.out.println("\nYou do not have enough points to open.");
				}
				break;
			default:
				System.out.println("\n Must choose a valid option.");
				break;
			}

		}

	}

	public static int count(List<Card> onTablou) {
		int count = 0;
		for (Card c : selected) {
			count += c.getPointValue();
		}
		return count;
	}

	public Card selectCard(Player p) {
		Scanner scan = new Scanner(System.in);
		System.out.print("\nSelect a card: ");
		String input = scan.next();
		Card selected = null;

		for (Card c : p.hand.deck) {
			if (input.equalsIgnoreCase(c.name())) {
				selected = c;
				break;
			}
		}
		if (selected == null) {
			// wont have this problem when using graphics
			// since they will only
			// be able to click on the cards they see
			System.out.print("Not in your hand!");
		}
		return selected;
	}

	public void play(Player p, List<Card> selected) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Specify which book to play cards on or type 'new': ");
		input = scan.next();
		Card bookKey;
		try {
			bookKey = Card.fromString(input);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			break;
		}

		// should the game or the tablou handle the acutal
		// placement? we have
		// the information we need to put the cards in the
		// desired location, but
		// we have not yet checked if the move is legal or
		// acutally done
		// anything with it
		p.tablou.addCards(selected, bookKey);

		// play selected card(s) -- add cards in 'selected' arraylist to tablou, they also
		// need to specify which book to play wilds on, also cannot play threes on tablou,
		// also must have selected at least 2 natural w/ one wild to open new book

	}

	public void discard(Player p, Card discardCard) {
		discardPile.addCard(discardCard);
		p.hand./*remove method to be written in Deck*/(discardCard);
	}

	public void turn(Player p) {
		boolean hasDiscarded = false;
		Scanner scan = new Scanner(System.in);
		List<Card> selected = new ArrayList<Card>();

		/*
		 * PHASE 1 player cannot do NAYTHING until they have EITHER drawn 2 cards from
		 * drawPile OR drawn top 7 cards from discardPile (as long as top card matches two
		 * natural cards in hand AND they must lay it down immediately AND can only use
		 * top card to count towards opening tablou if need be
		 */

		draw(p, input);

		// PHASE 2
		while (!hasDiscarded) {

			if (!p.tablou.isTablouOpen()) {
				System.out.print("\nType (1) to open tableau, or (2) to discard: ");
				String input = scan.nextLine();

				if (input.equals("1")) {
					open(p);
				} else if (input.equals("2")) {
					discard(p, selectCard(p));
					hasDiscarded = true;
				} else {
					System.out.println("\nNot a valid option.");
				}

			} else {

				selected.add(selectCard(p));

				// TO DO: error check for adding cards to
				// 'selected' arraylist -- must be same
				// value as prior with expection of wilds

				System.out.print("\nType (1) to select another card, (2) to play selected card(s), (3) to discard: ");
				input = scan.next();

				switch (input) {
				case "1":
					break;
				case "2":
					play(p, selected);
					break;
				case "3":
					if (selected.size() == 1) {
						discard(selected.get(0));
						hasDiscarded = true;
					} else {
						System.out.println("You can only have one card selected to discard.");
						selected.clear();
					}
					break;
				default:
					System.out.println("\n Must choose a valid option.");
					break;
				}
			}
		}
	}
}
