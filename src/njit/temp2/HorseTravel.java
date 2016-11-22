package njit.temp2;

import java.util.jar.JarException;

public class HorseTravel {
	
	private int chessboard[][];
	private boolean show;
	
	public HorseTravel(int n ,int x, int y,boolean show)
	{
		if(n < 5 || n > 8)
		{
			throw new java.lang.IllegalArgumentException("n="+n+",����̫���̫С��");
		}
		
		this.chessboard = new int[n][n];
		this.show = show ;
		this.start(x,y);
	}
	public HorseTravel (int n ,int x ,int y) 
	{
		this(n, x, y, false);
	}
	public HorseTravel(int n)
	{
		this(n,0,0,false);
	}
	public HorseTravel(){ this(8,0,0,false); }
	
	private class Position
	{
		int x,y;
		Position(int x,int y) 
		{
			int n = HorseTravel.this.chessboard.length;
			if(x >= 0 && x < n && y >= 0 && y < n)
			{
				this.x = x;
				this.y = y;
			}
			else throw new IndexOutOfBoundsException("x="+x+",y="+y);
		}
		Position()
		{
			this(0, 0);
		}
		Position(Position p)
		{
			this(p.x,p.y);
		}
		public String toString()
		{
			return "("+this.x+","+this.y+")";
		}
	}
	private void start(int x,int y)
	{
		Position p = new Position();
		int count = 1;
		int direction = 1;
		int n = this.chessboard.length;
		while(count <= n*n && direction != 0)
		{
			this.chessboard[p.x][p.y] = count;
			if(this.show)
			{
				System.out.print("��"+count+"��  ");
			}
			direction = this.select(p);
			if(direction == 0 && count < n*n)
			{
				System.out.println("��"+count+"����·��ͨ");
			}
			else 
			{
				count++;
				p = this.goaStep(p,direction);
			}
		}
		if(!this.show)
		{
			this.print();
		}
	}
	
	private int select(Position p)
	{
		if(this.show)
		{
			System.out.println("��ǰλ�ã�"+p.toString());
			this.print();
			System.out.println("����	��һλ��	��ͨ���� 	��ͨ·��");
		}
		int direction = 0;
		int minroad = 8;
		for(int i = 1;i<=8;i++)
		{
			int road = 0;
			Position next1 = goaStep(p,i);
			if(next1 != null)
			{
				if(this.show)
				{
					System.out.print(" "+i+"\t"+next1.toString()+"\t");
				}
				for(int j = 1; j <= 8;j++)
				{
					Position next2 = goaStep(next1,j);
					if(next2 != null)
					{
						road++;
						if(this.show)
						{
							System.out.print(j+",");
						}
					}
				}
				if(road < minroad)
				{
					minroad = road;
					direction = i;
				}
				if(this.show)
				{
					System.out.println("\t"+road);
				}
			}
		}
		if(this.show)
		{
			System.out.println("ѡ����һ������ direction ="+direction+"\r\n");
		}
		return direction;
		
	}
	
	private Position goaStep(final Position p,int direction)
	{
		int x = p.x;
		int y = p.y;
		switch (direction)
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
		int n = this.chessboard.length;
		if(x >= 0 && x < n && y >= 0 && y < n && this.chessboard[x][y] == 0)
		{
			return new Position(x,y);
		}
		else {
			return null;
		}
	}
	
	private void print()
	{
		
	}
	
	public static void main(String args[]) throws Exception
	{
		new HorseTravel(8,0,0,true);
	}

}

