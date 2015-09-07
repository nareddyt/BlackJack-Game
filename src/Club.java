// Teju Nareddy, 5/5/13

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * Subclass of Card. Implements all abstract methods of Card.
 *
 * @author Teju
 *
 */
public class Club extends Card
{
	public Club(int num)
	{
		super(num);
		
		if (num == 1)
		{
			img = Toolkit.getDefaultToolkit().getImage("Clubs_Ace.png");
		}
		else if (num == 2)
		{
			img = Toolkit.getDefaultToolkit().getImage("Clubs_2.png");
		}
		else if (num == 3)
		{
			img = Toolkit.getDefaultToolkit().getImage("Clubs_3.png");
		}
		else if (num == 4)
		{
			img = Toolkit.getDefaultToolkit().getImage("Clubs_4.png");
		}
		else if (num == 5)
		{
			img = Toolkit.getDefaultToolkit().getImage("Clubs_5.png");
		}
		else if (num == 6)
		{
			img = Toolkit.getDefaultToolkit().getImage("Clubs_6.png");
		}
		else if (num == 7)
		{
			img = Toolkit.getDefaultToolkit().getImage("Clubs_7.png");
		}
		else if (num == 8)
		{
			img = Toolkit.getDefaultToolkit().getImage("Clubs_8.png");
		}
		else if (num == 9)
		{
			img = Toolkit.getDefaultToolkit().getImage("Clubs_9.png");
		}
		else if (num == 10)
		{
			img = Toolkit.getDefaultToolkit().getImage("Clubs_10.png");
		}
		else if (num == 11)
		{
			img = Toolkit.getDefaultToolkit().getImage("Clubs_Jack.png");
		}
		else if (num == 12)
		{
			img = Toolkit.getDefaultToolkit().getImage("Clubs_Queen.png");
		}
		else if (num == 13)
		{
			img = Toolkit.getDefaultToolkit().getImage("Clubs_King.png");
		}
		else
			throw new IllegalArgumentException("Diamond constructed with number: " + num + "??? Programming Error");
	}
	
	private Image img;
	
	@Override
	public Color getColor()
	{
		return Color.BLACK;
	}
	
	@Override
	public Image getImage()
	{
		return img;
	}
	
	@Override
	public String getSuit()
	{
		return "Club";
	}
	
	@Override
	public String toString()
	{
		return super.getRank() + " of " + getSuit();
	}
}
