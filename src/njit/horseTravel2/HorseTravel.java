/**
 *�����������������һ�������������̤�����̵���ʾ����
 *������Ҫ�󡿣�����������ڹ��������8*8����Board[8][8]��ĳ�������У�
 *���������������ƶ���Ҫ��ÿ������ֻ����һ�Σ��߱�������ȫ��64������
 *���Ʒǵݹ��������������·�ߣ��������������·�ߣ�������1��2��3������64
 *��������һ��8*8�ķ������֮���������ݿ�������ָ��һ����ĳ�ʼλ�ã�i,j��,0��i,j��7��
 *��ѡ�����ݡ���1�������ĳһ�������Ķ�������ȫ������·�ߡ�
 *		   2����ʾѰ������·�ߵĻ��ݹ��̡�
 */
package njit.horseTravel2;

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
		
		JButton create = new JButton("����·��");
		create.addActionListener(boardPanel);
		create.setActionCommand("create");
		
		String degree[] = {"·��1","·��2"};
		JComboBox<String> chooseRoute = new JComboBox<String>(degree);
		
		JButton start = new JButton("��ʼ");
		start.addActionListener(boardPanel);
		start.setActionCommand("start");
		
		JPanel choose = new JPanel();
		choose.add(create);
		choose.add(chooseRoute);
		choose.add(start);
				
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
		if(e.getActionCommand().equals("start"))
		{	
			System.out.println("start");
			chessBoard.start(0, 0);
			repaint();
		}
		if(e.getActionCommand().equals("create"))
			System.out.println("create");
	}
}
