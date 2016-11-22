package njit.temp3;

import java.awt.Graphics;

public class HorseTravel {

	int stepCount = 1; // 步数记载
	int position[][] = new int[8][8]; // 棋盘
	int x = 0, y = 0; // 马当前的位置
	int pathCount = 0; // 解计数

	public HorseTravel() {
		stepSave(position, x, y);

		horse(position);

	}

	void horse(int position[][]) {

		int xBuf = this.x; // 暂存当前位置

		int yBuf = this.y;

		for (int i = 1; i <= 8; i++) {
			this.x = xBuf;

			this.y = yBuf; // 恢复当前位置，为下一步做准备

			nextStep(i);

			if (safe(position, x, y)) {

				stepSave(position, x, y);

				if (this.stepCount < 8 * 8 + 1)

					horse(position);

				else {

					print(position);

					back(position, x, y);

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
		// System.out.println("save");
	}

	boolean safe(int position[][], int x, int y) {
		if ((x < 0) || (x > 8 - 1) || (y < 0) || (y > 8 - 1)
				|| (position[x][y] != 0))

			return false;

		else

			return true;
	}

	void print(int position[][]) {
		int j, k;

		System.out.printf("path:%d\n", ++this.pathCount);

		System.out.printf("   *  ");

		for (k = 1; k <= 8; k++)

			System.out.printf("%3d", k);

		System.out.printf("\n\n");

		for (k = 0; k < 8; k++) {
			System.out.printf("%4d  ", k + 1);

			for (j = 0; j < 8; j++)

				System.out.printf("%3d", position[k][j]);

			System.out.printf("\n");
		}
		System.out.printf("--------------------------\n");
	}

}
