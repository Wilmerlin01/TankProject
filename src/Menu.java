import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
//Pops up everytime you die/lose
public class Menu
{

	private int highScore; //Displays your highscore

	//at some point make panels inMenu false
	void draw(Graphics g, ImageLibrary l)
	{
		//draw background
		g.drawImage(l.background, 0, 0, null);

		//TODO draw title
		g.setFont(new Font("TimesRoman", Font.PLAIN, 150)); //Times New Roman font; size 200
		g.setColor(Color.BLACK); //Black colored text
		g.drawString("TANK GAME ", 50, 150); //Constructs the text and draws it on panel
		
		//TODO draw high scores


		//TODO draw button to start classsic game
		g.setColor(Color.BLACK); //Red colored rectangle
		g.fillRect(900, 300, 440, 100); //Makes rectangle containing start button

		g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); //Times New Roman font; size 200
		g.setColor(Color.GREEN); //Black colored text
		g.drawString("Play Classic Mode", 935, 370); //Constructs the text and draws it on panel

		
		
		//TODO draw button to start game mode 2
		g.setColor(Color.BLACK); //Red colored rectangle
		g.fillRect(900, 500, 440, 100); //Makes rectangle containing start button

		g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); //Times New Roman font; size 200
		g.setColor(Color.BLUE); //Black colored text
		g.drawString("Play Survival Mode", 925, 570); //Constructs the text and draws it on panel

		
		
	}



	public boolean clickedButton1(int x, int y){ //to start classic game
		if((x >= 1000 && x <= 1340) 
				&&
				(y >= 300 && y <= 400)
				)
		{
			return true;
		}
		return false;
	}

	public boolean clickedButton2(int x, int y){ //to start survival mode
		if((x >= 1000 && x <= 1320) 
				&&
				(y >= 500 && y <= 600)
				)
		{
			return true;
		}
		return false;
	}
}
