/**
 * Member.java用来存放用到的类
 */
package njit.horseTravel4;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;
import javax.swing.*;

class Audio extends Thread {
	private String fileName;

	public Audio(String wavfile) {
		fileName = wavfile;
	}

	public void run() {

		File soundFile = new File(fileName);

		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine auline = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

		try {
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		auline.start();
		int nBytesRead = 0;
		// 缓冲
		byte[] buffer = new byte[512];

		try {
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(buffer, 0, buffer.length);
				if (nBytesRead >= 0)
					auline.write(buffer, 0, nBytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			auline.drain();
			auline.close();
		}

	}

}// End of Audio

class ChessBoard implements Runnable {
	public int nextDirection = 0;
	Horse horse = new Horse(Global.startX, Global.startY);
	int route[][] = new int[Global.rowOrLine][Global.rowOrLine];

	public ChessBoard() {
		for (int i = 0; i < Global.rowOrLine; i++) {
			for (int j = 0; j < Global.rowOrLine; j++) {
				route[i][j] = 0;
			}
		}
	}

	Vector<Horse> traces = new Vector<Horse>();
	int helpCount = 1;

	public void start(int x, int y) {
		int count = 1;
		Horse horse = new Horse(x, y);

		int direction = 1;
		int n = Global.rowOrLine;
		while (count <= n * n && direction != 0) {
			this.route[horse.x][horse.y] = count;
			direction = this.selectDirection(horse);
			if (direction == 0 && count < n * n) {
				System.out.println("第" + count + "步无路可通");
			} else {
				count++;
				horse = this.moveAStep(horse, direction);
			}
		}
		helpCount = count;
	}

	public int selectDirection(Horse horse) {
		int direction = 0;
		int minroad = 8;
		for (int i = 1; i <= 8; i++) {
			int road = 0;
			Horse next1 = moveAStep(horse, i);
			if (next1 != null) {
				for (int j = 1; j <= 8; j++) {
					Horse next2 = moveAStep(next1, j);
					if (next2 != null) {
						road++;
					}
				}
				if (road < minroad) {
					minroad = road;
					direction = i;
				}
			}
		}
		return direction;

	}

	public Horse moveAStep(Horse horse, int nextDirection) {
		int x = horse.x;
		int y = horse.y;

		switch (nextDirection) {
		case 1:
			x -= 2;
			y++;
			break;
		case 2:
			x--;
			y += 2;
			break;
		case 3:
			x++;
			y += 2;
			break;
		case 4:
			x += 2;
			y++;
			break;
		case 5:
			x += 2;
			y--;
			break;
		case 6:
			x++;
			y -= 2;
			break;
		case 7:
			x--;
			y -= 2;
			break;
		case 8:
			x -= 2;
			y--;
			break;
		default:
			break;
		}
		int n = Global.rowOrLine;
		if (x >= 0 && x < n && y >= 0 && y < n && this.route[x][y] == 0) {
			return new Horse(x, y);
		} else {
			return null;
		}
	}

	public void drawChessBoard(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(0, 0, Global.rowOrLine * Global.gridSize, Global.rowOrLine
				* Global.gridSize);
		for (int i = 0; i < Global.rowOrLine; i++) {
			g.setColor(((i & 1) != 0) ? Color.black : Color.white);
			for (int j = 0; j < Global.rowOrLine; j++) {
				g.fillRect(i * Global.gridSize, j * Global.gridSize,
						Global.gridSize, Global.gridSize);
				g.setColor((g.getColor() == Color.black) ? Color.white
						: Color.black);
			}
		}
	}

	public void run() {

		try {
			for (int k = 1; k <= Global.rowOrLine * Global.rowOrLine; k++) {
				for (int i = 0; i < Global.rowOrLine; i++) {
					for (int j = 0; j < Global.rowOrLine; j++) {
						if (route[i][j] == k) {
							Thread.sleep(600);
							Horse tmp = new Horse(i, j);
							tmp.count = k;
							traces.add(tmp);
							horse.x = i;
							horse.y = j;
							Audio audio = new Audio("./00.wav");
							audio.start();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}// End of ChessBord

class Horse {
	public int x = 0;
	public int y = 0;
	public int count = 1;

	public Horse(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class HorseRoute {

	int stepCount = 1; // 步数记载
	int position[][] = new int[Global.rowOrLine][Global.rowOrLine]; // 棋盘
	int x = Global.startX, y = Global.startY; // 马当前的位置
	int pathCount = 0; // 解计数

	public HorseRoute() {
		stepSave(position, x, y);
		horse(position);
	}

	void horse(int position[][]) {

		int xBuf = this.x; // 暂存当前位置
		int yBuf = this.y;

		if (Global.printTrace) {
			System.out.println("当前位置(" + this.x + "," + this.y + ")");
		}
		for (int i = 1; i <= 8; i++) {
			this.x = xBuf;
			this.y = yBuf; // 恢复当前位置，为下一步做准备
			
			nextStep(i);
			if (Global.printTrace) {
				System.out.println("测试位置(" + this.x + "," + this.y + ")");
			}

			if (safe(position, this.x, this.y)) {
				stepSave(position, this.x, this.y);
				if (this.stepCount < Global.rowOrLine * Global.rowOrLine + 1) {
					horse(position);
					if (Global.printTrace) {
						System.out.println("后退到(" + xBuf + "," + yBuf + ")");
					}
				} else {
					print(position);
					back(position, x, y);
				}
			} else {
				if (Global.printTrace) {
					System.out.println("退回到(" + xBuf + "," + yBuf + ")");
				}
			}
		}
		back(position, xBuf, yBuf);
	}

	void nextStep(int n) {
		switch (n) {
			case 1: {
				this.x = this.x - 2;
				this.y = this.y + 1;
				break;
			}	
			case 2: {
				this.x = this.x - 2;
				this.y = this.y - 1;
				break;
			}	
			case 3: {
				this.x = this.x - 1;
				this.y = this.y + 2;
				break;
			}	
			case 4: {
				this.x = this.x - 1;
				this.y = this.y - 2;
				break;
			}	
			case 5: {
				this.x = this.x + 1;
				this.y = this.y + 2;
				break;
			}	
			case 6: {
				this.x = this.x + 1;
				this.y = this.y - 2;
				break;
			}	
			case 7: {
				this.x = this.x + 2;
				this.y = this.y + 1;
				break;
			}	
			case 8: {
				this.x = this.x + 2;
				this.y = this.y - 1;
				break;
			}
		}
	}

	void back(int position[][], int x, int y) {
		position[x][y] = 0;
		this.stepCount = this.stepCount - 1;
	}

	void stepSave(int position[][], int x, int y) {
		position[x][y] = this.stepCount;
		this.stepCount = this.stepCount + 1;
	}

	boolean safe(int position[][], int x, int y) {
		if ((x < 0) || (x > Global.rowOrLine - 1) || (y < 0)
				|| (y > Global.rowOrLine - 1) || (position[x][y] != 0))
			return false;
		else
			return true;
	}

	void print(int position[][]) {
		int j, k;
		System.out.printf("path:%d\n", ++this.pathCount);
		System.out.printf("   *  ");
		for (k = 1; k <= Global.rowOrLine; k++)
			System.out.printf("%3d", k);
		System.out.printf("\n\n");
		for (k = 0; k < Global.rowOrLine; k++) {
			System.out.printf("%4d  ", k + 1);
			for (j = 0; j < Global.rowOrLine; j++)
				System.out.printf("%3d", position[k][j]);
			System.out.printf("\n");
		}
		System.out.printf("--------------------------\n");
	}
}
