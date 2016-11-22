package njit.temp;

import java.applet.Applet; 
import java.awt.*; 
import java.awt.event.*; 
import java.net.URL; 
import java.util.ArrayList; 
public class HorseTravelTest extends Applet 
{ 
	// �߳��ӳ��Ժ���Ϊ��λ 
	public final static int DELAY = 500; 
	// ��ʼ��ʿѲ���߳� 
	private Thread thread; 
	// ��ʼ��С���� 
	public void init() 
	{ 
		this.setSize(500,400);

		final ChessBoard chessBoard = new ChessBoard (this); 
		//��ChessBoard������뵽С������������ 
		add (chessBoard); 
		// ����һ��Panel����������Label,Choice�Ͱ�ť����. 
		Panel choose = new Panel (); 
		//����һ����ǩ������Choice���󲢰�����ӵ�Panel�� 
		choose.add (new Label ("Choose starting position:")); 
		//����һ��Choice����,����ѡ����ʿ�Ŀ�ʼλ�ã����̵��ĸ����䣩 
		//��������ӵ�Panel��. 
		final Choice choice = new Choice (); 
		choice.add ("Upperleft corner"); 
		choice.add ("Upperright corner"); 
		//����Choice��item listener,�����������ѡ������������ʿ�Ŀ�ʼλ��. 
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
		//��Panel���뵽С������������ 
		add (choose); 
		//����һ����ť����������ʼ��ʿѲ��. 
		final Button btn = new Button ("Take the Tour"); 
		//������ť��Action listener(����������),����ȷ����ʿѲ�ε�λ��. 
		//���չ�����ʿ��һ��λ���ƶ�����һ��λ��. 
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
					// thd��������û��뿪С������ҳ 
					// �Ա�ֹͣС���������. 
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
		//��Ӱ�ť��С����������� 
		add (btn); 
	} 
	//ֹͣС���� 
	public void stop () 
	{ 
		//�û��뿪��ҳʱ����ֹͣ����ʿѲ�Ρ��߳� 
		thread = null; 
	}
}

final class ChessBoard extends Canvas 
{ 
	//�ǰ�ɫ�������ɫ 
	private final static Color SQUARECOLOR = new Color (195, 214, 242); 
	//���̷���ĳߴ� 
	private final static int SQUAREDIM = 40; 
	//���̷���ĳߴ磨�����ڱ߿� 
	private final static int BOARDDIM = 8 * SQUAREDIM + 2; 
	//�������Ͻǵ������꣨X���꣩ 
	private int boardx; 
	//�������ϽǵĶ�����(Y����) 
	private int boardy; 
	//���̳��� 
	private int width; 
	// ���̿�� 
	private int height; 
	// ͼ�񻺳� 
	private Image imBuffer; 
	// Graphics context associated with image buffer. 
	private Graphics imG; 
	// ��ʿͼ�� 
	private Image imKnight; 
	// ��ʿͼ��ĳ��� 
	private int knightWidth; 
	// ��ʿͼ��Ŀ�� 
	private int knightHeight; 
	//��ʿ�켣������ 
	private ArrayList trail; 
	// Left coordinate of knight rectangle origin (upper-left corner). 
	private int ox; 
	// Top coordinate of knight rectangle origin (upper-left corner). 
	private int oy; 
	// ����ChessBoard��Applet--��������getImage()��getDocumentBase()����, 
	// �������ǽ�ʹ������ΪdrawImage()������image observer 
	Applet a; 
	// �������� 
	ChessBoard (Applet a) 
	{ 
		// ȷ�������Ĵ�С 
		width = BOARDDIM+1; 
		height = BOARDDIM+1; 
		// ��ʼ������, ʹ���������� 
		boardx = (width - BOARDDIM) / 2 + 1; 
		boardy = (height - BOARDDIM) / 2 + 1; 
		//ʹ��MediaTracker��ȷ����ʿͼ�������ǻ�ȡ���ĳ��Ϳ�֮ǰ����ȫ����
		MediaTracker mt = new MediaTracker (this); 
		// ������ʿͼ�� 
		imKnight = a.getImage (a.getDocumentBase (), "knight.gif"); 
		mt.addImage (imKnight, 0); 
		try { mt.waitForID (0); } 
		catch (InterruptedException e) {} 
		//�����ʿ�ĳ��ȺͿ��, ������ʿ��λ�ڷ�������
		knightWidth = imKnight.getWidth (a); 
		knightHeight = imKnight.getHeight (a); 
		//��ʼ����ʿͼ��, ʹ��ʿ��λ�����ϽǷ�������� 
		ox = boardx + (SQUAREDIM - knightWidth) / 2 + 1; 
		oy = boardy + (SQUAREDIM - knightHeight) / 2 + 1; 
		//����һ�����ݽṹ, ����������ʿѲ��ʱ�Ĺ켣 
		trail = new ArrayList (); 
		//����applet�����Ա�������drawImage()ʱʹ��. 
		this.a = a; 
	} 

	public void addNotify () 
	{ 
		// Given this object's Canvas "layer" a chance to create a Canvas peer. 
		super.addNotify (); 
		//����ͼ�񻺳� 
		imBuffer = createImage (width, height); 
		//�õ�ͼ�񻺳�����ݡ� 
		imG = imBuffer.getGraphics (); 
	} 
	//��С����Ĳ��ֹ����������������ʱ����������������
	//������ܣ��������ʾΪ��ѡ��С�� 
	public Dimension getPreferredSize () 
	{ 
		return new Dimension (width, height); 
	} 
	
	//�ƶ���ʿ��ָ��������λ�á����λ��С��1�����64���׳�һ���쳣
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
	//�������в����D�D������Ȼ������ʿ 
	public void paint (Graphics g) 
	{ 
		//�������� 
		paintChessBoard (imG, boardx, boardy); 
		//������ʿ 
		paintKnight (imG, ox, oy); 
		//������ʿ�Ĺ켣 
		paintTrail (imG); 
		//����ͼ�񻺳������ 
		g.drawImage (imBuffer, 0, 0, this); 
	} 
	//�������̨D�D��x, y�������Ͻ����� 
	void paintChessBoard (Graphics g, int x, int y) 
	{ 
		// �������̵ı߿� 
		g.setColor (Color.black); 
		g.drawRect (x, y, 8 * SQUAREDIM + 1, 8 * SQUAREDIM + 1); 
		//�������� 
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
	//������ʿ�D�D��x, y����ͼƬ���Ͻ����� 
	void paintKnight (Graphics g, int x, int y) 
	{ g.drawImage (imKnight, x, y, a); } 
	//������ʿ�Ĺ켣 
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
	//���ArrayList���������� 
	public void reset () 
	{ 
		trail.clear (); 
	} 

	public void update (Graphics g) 
	{ 
		paint (g); 
	} 
}

			
		
				
			
		
		
			
		
		
		
	
	
					
				
			
