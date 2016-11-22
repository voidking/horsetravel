/**
 *【问题描述】：设计一个国际象棋的马踏遍棋盘的演示程序。
 *【基本要求】：将马随机放在国际象棋的8*8棋盘Board[8][8]的某个方格中，马按照走棋规则进行移动。
 *要求每个方格只进入一次，走遍棋盘上全部64个方格。编制非递归程序，求出马的行走路线，并按求出的行走路线，
 *将数字1，2，3，…，64依次填入一个8*8的方阵，输出之。测试数据可以自行指定一个马的初始位置（i,j）,0≤i,j≤7。
 *【选作内容】：1、求出从某一起点出发的多条以致全部行走路线。
 *		   2、演示寻找行走路线的回溯过程。
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

		JMenu menu = new JMenu("菜单");
		JMenuItem foresee = new JMenuItem("预见算法");
		foresee.addActionListener(this);
		foresee.setActionCommand("createARoute");
		menu.add(foresee);

		JMenuItem backTracking = new JMenuItem("回溯算法");
		backTracking.addActionListener(this);
		backTracking.setActionCommand("createRoutes");
		menu.add(backTracking);

		JMenuItem set = new JMenuItem("设置");
		set.addActionListener(this);
		set.setActionCommand("set");
		menu.add(set);

		JMenuItem clear = new JMenuItem("清空");
		clear.addActionListener(this);
		clear.setActionCommand("clear");
		menu.add(clear);

		JMenuItem exit = new JMenuItem("退出");
		exit.addActionListener(this);
		exit.setActionCommand("exit");
		menu.add(exit);

		menuBar.add(menu);

		boardPanel = new MyPanel();

		this.add(boardPanel);
		this.setTitle("骑士游历");
		this.setSize(Global.wide, Global.height);
		// 加上这句可以让窗口居中
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
			g.setFont(new Font("宋体", Font.BOLD, 30));
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

			JLabel sizeAndNum = new JLabel("网格大小和数量");
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

			JLabel start = new JLabel("起始坐标");
			panel.add(start);

			Integer chooseX[] = new Integer[5];
			for (int i = 0; i < 5; i++) {
				chooseX[i] = i + 1;
			}
			comboX = new JComboBox<Integer>(chooseX);
			//comboX.addActionListener(this);加上这句会报错，为什么？
			panel.add(comboX);

			Integer chooseY[] = new Integer[5];
			for (int i = 0; i < 5; i++) 
			{
				chooseY[i] = i + 1;
			}
			comboY = new JComboBox<Integer>(chooseY);
			panel.add(comboY);

			JLabel print = new JLabel("打印回溯过程");
			panel.add(print);

			String choosePrint[] = { "否", "是" };
			comboPrint = new JComboBox<String>(choosePrint);
			panel.add(comboPrint);

			JButton confirm = new JButton("确定");
			confirm.addActionListener(this);
			confirm.setActionCommand("confirm");
			panel.add(confirm);

			this.add(panel);
			this.setTitle("设置");
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

			if ((String) comboPrint.getSelectedItem() == "否") {
				Global.printTrace = false;
			} else if ((String) comboPrint.getSelectedItem() == "是")
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
