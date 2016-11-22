/**
 *【问题描述】：设计一个国际象棋的马踏遍棋盘的演示程序。
 *【基本要求】：将马随机放在国际象棋的8*8棋盘Board[8][8]的某个方格中，马按照走棋规则进行移动。
 *要求每个方格只进入一次，走遍棋盘上全部64个方格。编制非递归程序，求出马的行走路线，并按求出的行走路线，
 *将数字1，2，3，…，64依次填入一个8*8的方阵，输出之。测试数据可以自行指定一个马的初始位置（i,j）,0≤i,j≤7。
 *【选作内容】：1、求出从某一起点出发的多条以致全部行走路线。
 *		   2、演示寻找行走路线的回溯过程。
 */
package njit.horseTravel3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.omg.CORBA.PUBLIC_MEMBER;

public class HorseTravel extends JFrame
{	
	public static void main(String[] args) 
	{
		HorseTravel horseTravel = new HorseTravel();
	}
	
	MyPanel boardPanel;
	public HorseTravel() 
	{		
		boardPanel = new MyPanel();
		
		JLabel foresee = new JLabel("预见算法");
		
		JButton createARoute = new JButton("生成一条路线");
		createARoute.addActionListener(boardPanel);
		createARoute.setActionCommand("createARoute");
		
		JLabel backTracking = new JLabel("回溯算法");
		
		JButton createRoutes = new JButton("生成路线");
		createRoutes.addActionListener(boardPanel);
		createRoutes.setActionCommand("createRoutes");
		
		JPanel choose = new JPanel();
		choose.add(foresee);
		choose.add(createARoute);
		choose.add(backTracking);
		choose.add(createRoutes);
				
		this.add(boardPanel);
		this.add(choose,BorderLayout.SOUTH);
		this.setTitle("骑士游历");
		this.setSize(Global.wide, Global.height);
		// 加上这句可以让窗口居中
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
}

class MyPanel extends Panel implements ActionListener
{
	ChessBoard chessBoard;
	public MyPanel()
	{
		chessBoard = new ChessBoard();
	}	
	public void paint(Graphics g)
	{
		chessBoard.drawChessBoard(g);
		chessBoard.drawHorse(g);
		chessBoard.drawChange(g);		
	}
	
	public void actionPerformed(ActionEvent e)
	{

		if(e.getActionCommand().equals("createARoute"))
		{
			System.out.println("create");
			chessBoard.start(0, 0);
			repaint();
		}
		if(e.getActionCommand().equals("createRoutes"))
		{
			System.out.println("createRoutes");
			new HorseRoute();
		}
			
	}
}
