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
public class Diamond extends Card
{
	public Diamond(int num)
	{
		super(num);
		
		if (num == 1)
		{
			img = Toolkit.getDefaultToolkit().getImage("Diamonds_Ace.png");
		}
		else if (num == 2)
		{
			img = Toolkit.getDefaultToolkit().getImage("Diamonds_2.png");
		}
		else if (num == 3)
		{
			img = Toolkit.getDefaultToolkit().getImage("Diamonds_3.png");
		}
		else if (num == 4)
		{
			img = Toolkit.getDefaultToolkit().getImage("Diamonds_4.png");
		}
		else if (num == 5)
		{
			img = Toolkit.getDefaultToolkit().getImage("Diamonds_5.png");
		}
		else if (num == 6)
		{
			img = Toolkit.getDefaultToolkit().getImage("Diamonds_6.png");
		}
		else if (num == 7)
		{
			img = Toolkit.getDefaultToolkit().getImage("Diamonds_7.png");
		}
		else if (num == 8)
		{
			img = Toolkit.getDefaultToolkit().getImage("Diamonds_8.png");
		}
		else if (num == 9)
		{
			img = Toolkit.getDefaultToolkit().getImage("Diamonds_9.png");
		}
		else if (num == 10)
		{
			img = Toolkit.getDefaultToolkit().getImage("Diamonds_10.png");
		}
		else if (num == 11)
		{
			img = Toolkit.getDefaultToolkit().getImage("Diamonds_Jack.png");
		}
		else if (num == 12)
		{
			img = Toolkit.getDefaultToolkit().getImage("Diamonds_Queen.png");
		}
		else if (num == 13)
		{
			img = Toolkit.getDefaultToolkit().getImage("Diamonds_King.png");
		}
		else
			throw new IllegalArgumentException("Diamond constructed with number: " + num + "??? Programming Error");
	}
	
	private Image img;
	
	@Override
	public Color getColor()
	{
		return Color.RED;
	}
	
	@Override
	public Image getImage()
	{
		return img;
	}
	
	@Override
	public String getSuit()
	{
		return "Diamond";
	}
	
	@Override
	public String toString()
	{
		return super.getRank() + " of " + getSuit();
	}
}
