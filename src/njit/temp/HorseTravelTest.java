package njit.temp;

import java.applet.Applet; 
import java.awt.*; 
import java.awt.event.*; 
import java.net.URL; 
import java.util.ArrayList; 
public class HorseTravelTest extends Applet 
{ 
	// 线程延迟以毫秒为单位 
	public final static int DELAY = 500; 
	// 开始骑士巡游线程 
	private Thread thread; 
	// 初始化小程序 
	public void init() 
	{ 
		this.setSize(500,400);

		final ChessBoard chessBoard = new ChessBoard (this); 
		//把ChessBoard对象加入到小程序的面板容器 
		add (chessBoard); 
		// 创建一个Panel对象来保存Label,Choice和按钮对象. 
		Panel choose = new Panel (); 
		//创建一个标签来标明Choice对象并把它添加到Panel中 
		choose.add (new Label ("Choose starting position:")); 
		//创建一个Choice对象,用来选择骑士的开始位置（棋盘的四个角落） 
		//并把它添加到Panel中. 
		final Choice choice = new Choice (); 
		choice.add ("Upperleft corner"); 
		choice.add ("Upperright corner"); 
		//创建Choice的item listener,这个监听器按选择结果来重设骑士的开始位置. 
		choice.addItemListener 
		(new ItemListener () 
			{ 
				public void itemStateChanged (ItemEvent e) 
				{ 
					Choice c = (Choice) e.getSource (); 
					if (c.getSelectedIndex () == 0) 
						chessBoard.moveKnight (1); 
					else 
						chessBoard.moveKnight (8); 
					chessBoard.reset (); 
				} 
			}
		); 
		choose.add (choice); 
		//把Panel加入到小程序的面板容器 
		add (choose); 
		//创建一个按钮对象用来开始骑士巡游. 
		final Button btn = new Button ("Take the Tour"); 
		//创建按钮的Action listener(动作监听器),用来确定骑士巡游的位置. 
		//按照规则将骑士从一个位置移动到另一个位置. 
		ActionListener listener; 
		listener = new ActionListener () 
		{ 
			public void actionPerformed (ActionEvent e) 
			{ 
				Runnable r; 
				r = new Runnable () 
				{ 
					int [] boardPositions1 = 
						{ 1, 18, 33, 50, 60, 54, 
							64, 47, 32, 15, 5, 11, 
							17, 34, 49, 59, 53, 63, 
							48, 31, 16, 6, 12, 2, 19, 
							25, 42, 57, 51, 61, 55, 
							40, 23, 8, 14, 4, 10, 27, 
							44, 38, 21, 36, 46, 29, 35, 
							41, 58, 52, 62, 56, 39, 24, 
							7, 13, 3, 9, 26, 43, 
							37, 22, 28, 45, 30, 20 
						}; 
					int [] boardPositions2 = 
						{ 8, 23, 40, 55, 61, 51, 
							57, 42, 25, 10, 4, 14, 
							24, 39, 56, 62, 52, 58,
							41, 26, 9, 3, 13, 7, 
							22, 32, 47, 64, 54, 60,
							50, 33, 18, 1, 11, 5, 
							15, 30, 45, 35, 20, 37, 
							43, 28, 38, 48, 63, 53, 
							59, 49, 34, 17, 2, 12, 
							6, 16, 31, 46, 36, 19, 
							29, 44, 27, 21 
						};
					public void run () 
					{ 
						chessBoard.reset (); 
					// thd用来检查用户离开小程序网页 
					// 以便停止小程序的运行. 
						for (int i = 0; i < boardPositions1.length && thread != null; i++) 
						{ 
							if (choice.getSelectedIndex () == 0) 
								chessBoard.moveKnight (boardPositions1 [i]); 
							else chessBoard.moveKnight (boardPositions2 [i]); 
							try { Thread.sleep (DELAY); } 
							catch (InterruptedException e2) { } 
						} 
						choice.setEnabled (true); 
						btn.setEnabled (true); 
					} 
				}; 
				choice.setEnabled (false); 
				btn.setEnabled (false); 
				thread = new Thread (r); 
				thread.start (); 
			} 
		}; 
		btn.addActionListener (listener);
		//添加按钮到小程序面板容器 
		add (btn); 
	} 
	//停止小程序 
	public void stop () 
	{ 
		//用户离开网页时必须停止”骑士巡游”线程 
		thread = null; 
	}
}

final class ChessBoard extends Canvas 
{ 
	//非白色方格的颜色 
	private final static Color SQUARECOLOR = new Color (195, 214, 242); 
	//棋盘方格的尺寸 
	private final static int SQUAREDIM = 40; 
	//棋盘方格的尺寸（包括黑边框） 
	private final static int BOARDDIM = 8 * SQUAREDIM + 2; 
	//棋盘左上角的左坐标（X坐标） 
	private int boardx; 
	//棋盘左上角的顶坐标(Y坐标) 
	private int boardy; 
	//棋盘长度 
	private int width; 
	// 棋盘宽度 
	private int height; 
	// 图像缓冲 
	private Image imBuffer; 
	// Graphics context associated with image buffer. 
	private Graphics imG; 
	// 骑士图像 
	private Image imKnight; 
	// 骑士图像的长度 
	private int knightWidth; 
	// 骑士图像的宽度 
	private int knightHeight; 
	//骑士轨迹的坐标 
	private ArrayList trail; 
	// Left coordinate of knight rectangle origin (upper-left corner). 
	private int ox; 
	// Top coordinate of knight rectangle origin (upper-left corner). 
	private int oy; 
	// 创建ChessBoard的Applet--调用它的getImage()和getDocumentBase()方法, 
	// 并且我们将使用它作为drawImage()方法的image observer 
	Applet a; 
	// 构造棋盘 
	ChessBoard (Applet a) 
	{ 
		// 确定部件的大小 
		width = BOARDDIM+1; 
		height = BOARDDIM+1; 
		// 初始化棋盘, 使它处于中央 
		boardx = (width - BOARDDIM) / 2 + 1; 
		boardy = (height - BOARDDIM) / 2 + 1; 
		//使用MediaTracker来确保骑士图像在我们获取它的长和宽之前被完全导入
		MediaTracker mt = new MediaTracker (this); 
		// 导入骑士图像 
		imKnight = a.getImage (a.getDocumentBase (), "knight.gif"); 
		mt.addImage (imKnight, 0); 
		try { mt.waitForID (0); } 
		catch (InterruptedException e) {} 
		//获得骑士的长度和宽度, 帮助骑士定位于方格中央
		knightWidth = imKnight.getWidth (a); 
		knightHeight = imKnight.getHeight (a); 
		//初始化骑士图像, 使骑士定位于左上角方格的中央 
		ox = boardx + (SQUAREDIM - knightWidth) / 2 + 1; 
		oy = boardy + (SQUAREDIM - knightHeight) / 2 + 1; 
		//创建一个数据结构, 用来保存骑士巡游时的轨迹 
		trail = new ArrayList (); 
		//保存applet引用以便后面调用drawImage()时使用. 
		this.a = a; 
	} 

	public void addNotify () 
	{ 
		// Given this object's Canvas "layer" a chance to create a Canvas peer. 
		super.addNotify (); 
		//创建图像缓冲 
		imBuffer = createImage (width, height); 
		//得到图像缓冲的内容。 
		imG = imBuffer.getGraphics (); 
	} 
	//当小程序的布局管理器布置它的组件时，会调用这个方法。
	//如果可能，组件会显示为首选大小。 
	public Dimension getPreferredSize () 
	{ 
		return new Dimension (width, height); 
	} 
	
	//移动骑士到指定的棋盘位置。如果位置小于1或大于64则抛出一个异常
	public void moveKnight (int boardPosition) 
	{ 
		if (boardPosition < 1 || boardPosition > 64) 
			throw new IllegalArgumentException ("invalid board position: " + boardPosition); 
		int rebasedBoardPosition = boardPosition-1; 
		int col = rebasedBoardPosition % 8; 
		int row = rebasedBoardPosition / 8; 
		ox = boardx + col * SQUAREDIM + (SQUAREDIM - knightWidth) / 2 + 1; 
		oy = boardy + row * SQUAREDIM + (SQUAREDIM - knightHeight) / 2 + 1; 
		trail.add (new Point (ox + knightWidth / 2, oy + knightHeight / 2)); 
		repaint ();
	} 
	//画出所有部件――先棋盘然后是骑士 
	public void paint (Graphics g) 
	{ 
		//画出棋盘 
		paintChessBoard (imG, boardx, boardy); 
		//画出骑士 
		paintKnight (imG, ox, oy); 
		//画出骑士的轨迹 
		paintTrail (imG); 
		//画出图像缓冲的内容 
		g.drawImage (imBuffer, 0, 0, this); 
	} 
	//画出棋盘――（x, y）是左上角坐标 
	void paintChessBoard (Graphics g, int x, int y) 
	{ 
		// 画出棋盘的边框 
		g.setColor (Color.black); 
		g.drawRect (x, y, 8 * SQUAREDIM + 1, 8 * SQUAREDIM + 1); 
		//画出棋盘 
		for (int row = 0; row < 8; row++) 
		{ 
			g.setColor (((row & 1) != 0) ? SQUARECOLOR : Color.white); 
			for (int col = 0; col < 8; col++) 
			{ 
				g.fillRect (x + 1 + col * SQUAREDIM, y + 1 + row * SQUAREDIM, SQUAREDIM, SQUAREDIM); 
				g.setColor ((g.getColor () == SQUARECOLOR) ? Color.white : SQUARECOLOR); 
			} 
		} 
	} 
	//画出骑士――（x, y）是图片左上角坐标 
	void paintKnight (Graphics g, int x, int y) 
	{ g.drawImage (imKnight, x, y, a); } 
	//画出骑士的轨迹 
	void paintTrail (Graphics g) 
	{ 
		g.setColor (Color.black); 
		int len = trail.size (); 
		if (len == 0) return; 
		Point pt = (Point) trail.get (0); 
		int ox = pt.x; 
		int oy = pt.y; 
		for (int i = 1; i < len; i++) 
		{ 
			pt = (Point) trail.get (i); 
			g.drawLine (ox, oy, pt.x, pt.y); 
			ox = pt.x; oy = pt.y; 
		} 
	} 
	//清空ArrayList来重设棋盘 
	public void reset () 
	{ 
		trail.clear (); 
	} 

	public void update (Graphics g) 
	{ 
		paint (g); 
	} 
}

			
		
				
			
		
		
			
		
		
		
	
	
					
				
			
