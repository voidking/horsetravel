package njit.horseTravel2;

import java.awt.*;
import java.util.*;
import javax.swing.*;


class ChessBoard
{
	public ChessBoard()
	{
		start(1, 0);
	}
	
	public int nextDirection = 0 ;
	Horse horse = new Horse(Global.startX, Global.startY);

	Vector<int[][]> routesVector= new Vector<int[][]>();
	
	public void start(int x,int y)
	{
		int route[][] = new int[Global.rowOrLine][Global.rowOrLine];
		routesVector.add(route);
		
		Horse horse = new Horse(x,y);
		int count = 1;
		int direction = 1;
		int n = Global.rowOrLine;
		while(count <= n*n && direction != 0)
		{
			route[horse.x][horse.y] = count;
			direction = this.selectDirection(horse);
			if(direction == 0 && count < n*n)
			{
				System.out.println("第"+count+"步无路可通");
			}
			else 
			{
				count++;
				horse = this.moveAStep(horse, direction);
			}
		}
	}
	
	public int selectDirection(Horse horse)
	{
		
		int direction = 0;
		int minroad = 8;
		for(int i = 1;i<=8;i++)
		{
			int road = 0;
			Horse next1 = moveAStep(horse,i);
			if(next1 != null)
			{
				for(int j = 1; j <= 8;j++)
				{
					Horse next2 = moveAStep(next1, j);
					if(next2 != null)
					{
						road++;
					}
				}
				if(road < minroad)
				{
					minroad = road;
					direction = i;
				}
			}
		}
		return direction;
		
	}
	
	public Horse moveAStep(Horse horse,int nextDirection)
	{
		int x = horse.x;
		int y = horse.y;
		
	    switch (nextDirection)
	    {
	        case 1: x-=2; y++;  break; 
	        case 2: x--;  y+=2; break; 
	        case 3: x++;  y+=2; break; 
	        case 4: x+=2; y++;  break; 
	        case 5: x+=2; y--;  break; 
	        case 6: x++;  y-=2; break; 
	        case 7: x--;  y-=2; break; 
	        case 8: x-=2; y--;  break; 
	        default : break;
	    }
	    int n = Global.rowOrLine;
	    int route[][] = routesVector.get(0);
		if(x >= 0 && x < n && y >= 0 && y < n && route[x][y] == 0)
		{
			return new Horse(x,y);
		}
		else {
			return null;
		}
	}

	
	public void drawChessBoard(Graphics g) 
	{
		g.setColor (Color.black); 
		g.drawRect (0, 0, Global.rowOrLine * Global.gridSize, Global.rowOrLine * Global.gridSize); 
		for (int i = 0; i < Global.rowOrLine; i++)
		{
			g.setColor (((i & 1) != 0) ? Color.black : Color.white);
			for (int j = 0; j < Global.rowOrLine; j++) 
			{				  
				g.fillRect(i*Global.gridSize, j*Global.gridSize, Global.gridSize, Global.gridSize);
				g.setColor ((g.getColor () == Color.black) ? Color.white : Color.black); 
			}
		}
	}
	
	public void drawHorse(Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect(horse.x*Global.gridSize, horse.y*Global.gridSize, Global.gridSize, Global.gridSize);		
	}
	
	public void drawChange(Graphics g)
	{
		int route[][] = routesVector.get(0);
		g.setColor(Color.blue);
		g.setFont(new Font("隶书 ",Font.BOLD,30));
		for(int i = 0 ; i < Global.rowOrLine ; i++)
		{
			for(int j = 0; j < Global.rowOrLine; j++)
			{
				if(route[i][j] != 0)
				{
					//g.fillRect(i*Global.gridSize,j*Global.gridSize, Global.gridSize, Global.gridSize);
					g.drawString(String.valueOf(route[i][j]), i*Global.gridSize+10, j*Global.gridSize+30);
					System.out.println(route[i][j]);
				}
				
			}
		}		
	}

}

class Horse
{
	public int x = 0;
	public int y = 0;
		
	public Horse(int x,int y) 
	{
		this.x = x;
		this.y = y;
	}	
}








