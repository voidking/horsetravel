/**
 *�����������������һ�������������̤�����̵���ʾ����
 *������Ҫ�󡿣�����������ڹ��������8*8����Board[8][8]��ĳ�������У����������������ƶ���
 *Ҫ��ÿ������ֻ����һ�Σ��߱�������ȫ��64�����񡣱��Ʒǵݹ��������������·�ߣ��������������·�ߣ�
 *������1��2��3������64��������һ��8*8�ķ������֮���������ݿ�������ָ��һ����ĳ�ʼλ�ã�i,j��,0��i,j��7��
 *��ѡ�����ݡ���1�������ĳһ�������Ķ�������ȫ������·�ߡ�
 *		   2����ʾѰ������·�ߵĻ��ݹ��̡�
 */
package njit.horseTravel4;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class HorseTravel extends JFrame implements ActionListener {
	public static void main(String[] args) {
		HorseTravel horseTravel = new HorseTravel();
	}

	MyPanel boardPanel;
	ChessBoard chessBoard;

	public HorseTravel() {

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu menu = new JMenu("�˵�");
		JMenuItem foresee = new JMenuItem("Ԥ���㷨");
		foresee.addActionListener(this);
		foresee.setActionCommand("createARoute");
		menu.add(foresee);

		JMenuItem backTracking = new JMenuItem("�����㷨");
		backTracking.addActionListener(this);
		backTracking.setActionCommand("createRoutes");
		menu.add(backTracking);

		JMenuItem set = new JMenuItem("����");
		set.addActionListener(this);
		set.setActionCommand("set");
		menu.add(set);

		JMenuItem clear = new JMenuItem("���");
		clear.addActionListener(this);
		clear.setActionCommand("clear");
		menu.add(clear);

		JMenuItem exit = new JMenuItem("�˳�");
		exit.addActionListener(this);
		exit.setActionCommand("exit");
		menu.add(exit);

		menuBar.add(menu);

		boardPanel = new MyPanel();

		this.add(boardPanel);
		this.setTitle("��ʿ����");
		this.setSize(Global.wide, Global.height);
		// �����������ô��ھ���
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class MyPanel extends Panel implements Runnable {

		public MyPanel() {
			chessBoard = new ChessBoard();
		}

		public void paint(Graphics g) {
			chessBoard.drawChessBoard(g);
			drawHorse(g);
		}

		public void drawHorse(Graphics g) {
			Image im = Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/00.jpg"));
			g.drawImage(im, chessBoard.horse.x * Global.gridSize,
					chessBoard.horse.y * Global.gridSize, Global.gridSize,
					Global.gridSize, this);

			g.setColor(Color.blue);
			g.setFont(new Font("����", Font.BOLD, 30));
			for (int i = 0; i < chessBoard.traces.size(); i++) {
				Horse tmp = chessBoard.traces.get(i);
				g.drawString(String.valueOf(tmp.count), tmp.x * Global.gridSize
						+ 10, tmp.y * Global.gridSize + 30);
			}
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
				repaint();
				if (chessBoard.traces.size() == Global.rowOrLine
						* Global.rowOrLine) {
					break;
				}
			}
		}
	}

	class SetFrame extends JFrame implements ActionListener {
		JComboBox<Integer> comboX, comboY, comboSize, comboNum;
		JComboBox<String> comboPrint;

		public SetFrame() {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(3, 3));

			JLabel sizeAndNum = new JLabel("�����С������");
			panel.add(sizeAndNum);

			Integer chooseSize[] = new Integer[4];
			for (int i = 0; i < 4; i++) {
				chooseSize[i] = (i + 5) * 10;
			}
			comboSize = new JComboBox<Integer>(chooseSize);
			comboSize.addActionListener(this);
			panel.add(comboSize);

			Integer chooseNum[] = new Integer[4];
			for (int i = 0; i < 4; i++) {
				chooseNum[i] = i + 5;
			}
			comboNum = new JComboBox<Integer>(chooseNum);
			comboNum.addActionListener(this);
			panel.add(comboNum);

			JLabel start = new JLabel("��ʼ����");
			panel.add(start);

			Integer chooseX[] = new Integer[5];
			for (int i = 0; i < 5; i++) {
				chooseX[i] = i + 1;
			}
			comboX = new JComboBox<Integer>(chooseX);
			//comboX.addActionListener(this);�������ᱨ��Ϊʲô��
			panel.add(comboX);

			Integer chooseY[] = new Integer[5];
			for (int i = 0; i < 5; i++) 
			{
				chooseY[i] = i + 1;
			}
			comboY = new JComboBox<Integer>(chooseY);
			panel.add(comboY);

			JLabel print = new JLabel("��ӡ���ݹ���");
			panel.add(print);

			String choosePrint[] = { "��", "��" };
			comboPrint = new JComboBox<String>(choosePrint);
			panel.add(comboPrint);

			JButton confirm = new JButton("ȷ��");
			confirm.addActionListener(this);
			confirm.setActionCommand("confirm");
			panel.add(confirm);

			this.add(panel);
			this.setTitle("����");
			this.setSize(400, 150);
			this.setLocationRelativeTo(null);
			this.setVisible(true);

		}

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == comboNum) {
				comboX.removeAllItems();
				comboY.removeAllItems();
				for (int i = 0; i < (int) comboNum.getSelectedItem(); i++) {
					comboX.addItem(i + 1);
					comboY.addItem(i + 1);
				}
			}

			chessBoard.horse.x = (int) comboX.getSelectedItem() - 1;
			Global.startX = (int) comboX.getSelectedItem() - 1;
			chessBoard.horse.y = (int) comboY.getSelectedItem() - 1;
			Global.startY = (int) comboY.getSelectedItem() - 1;

			Global.gridSize = (int) comboSize.getSelectedItem();
			Global.rowOrLine = (int) comboNum.getSelectedItem();

			if ((String) comboPrint.getSelectedItem() == "��") {
				Global.printTrace = false;
			} else if ((String) comboPrint.getSelectedItem() == "��")
			{
				Global.printTrace = true;
			}

			if (e.getActionCommand().equals("confirm")) {
				boardPanel.repaint();
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("createARoute")) {
			System.out.println("CreateARoute");
			chessBoard.start(chessBoard.horse.x, chessBoard.horse.y);
			Thread thread = new Thread(chessBoard);
			thread.start();
			Thread panelThread = new Thread(boardPanel);
			panelThread.start();
		} else if (e.getActionCommand().equals("createRoutes")) {
			System.out.println("CreateRoutes......Please wait......");
			new HorseRoute();
			System.out.println("Finished !");
		} else if (e.getActionCommand().equals("set")) {
			System.out.println("set");
			new SetFrame();
			boardPanel.repaint();
		} else if (e.getActionCommand().equals("clear")) {
			this.remove(boardPanel);
			boardPanel = new MyPanel();
			this.add(boardPanel);
			this.setVisible(true);

		} else if (e.getActionCommand().equals("exit")) {
			System.exit(0);
		}

	}

}
