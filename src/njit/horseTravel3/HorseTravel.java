/**
 *�����������������һ�������������̤�����̵���ʾ����
 *������Ҫ�󡿣�����������ڹ��������8*8����Board[8][8]��ĳ�������У����������������ƶ���
 *Ҫ��ÿ������ֻ����һ�Σ��߱�������ȫ��64�����񡣱��Ʒǵݹ��������������·�ߣ��������������·�ߣ�
 *������1��2��3������64��������һ��8*8�ķ������֮���������ݿ�������ָ��һ����ĳ�ʼλ�ã�i,j��,0��i,j��7��
 *��ѡ�����ݡ���1�������ĳһ�������Ķ�������ȫ������·�ߡ�
 *		   2����ʾѰ������·�ߵĻ��ݹ��̡�
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
		
		JLabel foresee = new JLabel("Ԥ���㷨");
		
		JButton createARoute = new JButton("����һ��·��");
		createARoute.addActionListener(boardPanel);
		createARoute.setActionCommand("createARoute");
		
		JLabel backTracking = new JLabel("�����㷨");
		
		JButton createRoutes = new JButton("����·��");
		createRoutes.addActionListener(boardPanel);
		createRoutes.setActionCommand("createRoutes");
		
		JPanel choose = new JPanel();
		choose.add(foresee);
		choose.add(createARoute);
		choose.add(backTracking);
		choose.add(createRoutes);
				
		this.add(boardPanel);
		this.add(choose,BorderLayout.SOUTH);
		this.setTitle("��ʿ����");
		this.setSize(Global.wide, Global.height);
		// �����������ô��ھ���
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
