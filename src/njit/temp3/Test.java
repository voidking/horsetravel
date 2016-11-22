package njit.temp3;

import javax.swing.*;
import java.awt.*;

public class Test extends JFrame
{

	public static void main(String[] args) {

		Test test = new Test();
	}
	
	public Test()
	{
		this.setTitle("图形界面输出数字");
		this.setSize(500, 400);	
		this.setLocationRelativeTo(null);			
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}

}

class MyPanel extends JPanel
{
	public MyPanel()
	{
		HorseTravel horseTravel = new HorseTravel();
	}
	public void paint(Graphics g)
	{
		
	}
}
