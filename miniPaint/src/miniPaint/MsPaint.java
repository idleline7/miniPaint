package miniPaint;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MsPaint extends JFrame {
	private JLabel x1L, y1L, x2L, y2L, z1L, z2L;
	private JTextField x1T, y1T, x2T, y2T, z1T, z2T;
	private JCheckBox fill;
	private JRadioButton line, circle, rect, roundRect, pen;
	private JComboBox<String> combo;
	private JButton drawB, deleteB;
	private DrCanvas can;
	private ArrayList<ShapeDTO> list;
	private int penNum;

	public MsPaint() {
		x1L = new JLabel("X1");
		x2L = new JLabel("X2");
		y1L = new JLabel("Y1");
		y2L = new JLabel("Y2");
		z1L = new JLabel("Z1");
		z2L = new JLabel("Z2");

		x1T = new JTextField("0", 5);
		y1T = new JTextField("0", 5);
		x2T = new JTextField("0", 5);
		y2T = new JTextField("0", 5);
		z1T = new JTextField("50", 5);
		z2T = new JTextField("50", 5);

		fill = new JCheckBox("채우기", false);
		line = new JRadioButton("선", true);
		circle = new JRadioButton("원");
		rect = new JRadioButton("사각형");
		roundRect = new JRadioButton("둥근사각형");
		pen = new JRadioButton("연필");

		ButtonGroup group = new ButtonGroup(); // 그룹으로 묶어서 버튼을 하나만 선택가능
		group.add(line);
		group.add(circle);
		group.add(rect);
		group.add(roundRect);
		group.add(pen);

		String[] name = { "빨강", "초록", "파랑", "보라", "하늘" };
		combo = new JComboBox<String>(name);
		drawB = new JButton("그리기");
		deleteB = new JButton("지우기");

		can = new DrCanvas(this);
		list = new ArrayList<ShapeDTO>();

		JPanel p = new JPanel();
		p.add(x1L);
		p.add(x1T);
		p.add(x2L);
		p.add(x2T);
		p.add(y1L);
		p.add(y1T);
		p.add(y2L);
		p.add(y2T);
		p.add(z1L);
		p.add(z1T);
		p.add(z2L);
		p.add(z2T);

		p.add(fill);

		JPanel p2 = new JPanel();
		p2.add(line);
		p2.add(circle);
		p2.add(rect);
		p2.add(roundRect);
		p2.add(pen);

		p2.add(combo);
		p2.add(drawB);
		p2.add(deleteB);

		Container c = this.getContentPane();
		c.add("North", p);
		c.add("South", p2);
		c.add("Center", can);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setBounds(200, 300, 700, 500);
		this.setVisible(true);
		this.setResizable(false);

		drawB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				can.repaint();
			}

		});

		deleteB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.clear();
				can.repaint();
			}
		});

		// v = new Vector<Point>();
		// 클릭했을때 그 포인트의 좌표를 저장
		can.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				x1T.setText(e.getX() + "");
				y1T.setText(e.getY() + "");

				// setVector(null);
			}
		});

		// 드래그 했을때의 좌표를 계속해서 repaint();
		can.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				x2T.setText(e.getX() + "");
				y2T.setText(e.getY() + "");
				can.repaint();

				if (pen.isSelected()) {
					ShapeDTO dto = new ShapeDTO();
					dto.setX1(Integer.parseInt(x1T.getText()));
					dto.setY1(Integer.parseInt(y1T.getText()));
					dto.setX2(Integer.parseInt(x2T.getText()));
					dto.setY2(Integer.parseInt(y2T.getText()));
					dto.setZ1(Integer.parseInt(z1T.getText()));
					dto.setZ2(Integer.parseInt(z2T.getText()));

					dto.setFill(fill.isSelected());
					dto.setShape(Figure.PEN);
					dto.setColor(combo.getSelectedIndex());

					list.add(dto);

					x1T.setText(x2T.getText()); // 끝점을 앞으로 옮긴다
					y1T.setText(y2T.getText());

				}

			}
		});

		can.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				// ArrayList<ArrayList<String>> arr =new ArrayList<ArrayList<String>>();
				ShapeDTO dto = new ShapeDTO();
				dto.setX1(Integer.parseInt(x1T.getText()));
				dto.setY1(Integer.parseInt(y1T.getText()));
				dto.setX2(Integer.parseInt(x2T.getText()));
				dto.setY2(Integer.parseInt(y2T.getText()));
				dto.setZ1(Integer.parseInt(z1T.getText()));
				dto.setZ2(Integer.parseInt(z2T.getText()));

				dto.setFill(fill.isSelected());

				if (line.isSelected())
					dto.setShape(Figure.LINE);
				else if (circle.isSelected())
					dto.setShape(Figure.CIRCLE);
				else if (rect.isSelected())
					dto.setShape(Figure.RECT);
				else if (roundRect.isSelected())
					dto.setShape(Figure.ROUNDRECT);

				dto.setColor(combo.getSelectedIndex());
				list.add(dto);
				System.out.println("Arraylist 개수 : " + list.size());

			}
		});

	}

	public JTextField getX1T() {
		return x1T;
	}

	public JTextField getX2T() {
		return x2T;
	}

	public JTextField getY1T() {
		return y1T;
	}

	public JTextField getY2T() {
		return y2T;
	}

	public JTextField getZ1T() {
		return z1T;
	}

	public JTextField getZ2T() {
		return z2T;
	}

	public JComboBox<String> getCombobox() {
		return combo;
	}

	public JCheckBox getFill() {
		return fill;
	}

	public JRadioButton getLine() {
		return line;
	}

	public JRadioButton getCircle() {
		return circle;
	}

	public JRadioButton getRect() {
		return rect;
	}

	public JRadioButton getRoundRect() {
		return roundRect;
	}

	public JRadioButton getPen() {
		return pen;
	}

	public void setPenNum(int penNum) {
		this.penNum = penNum;
	}

	public int getPenNum() {
		return penNum;
	}

	public ArrayList<ShapeDTO> getList() {
		return list;
	}

	public static void main(String[] args) {
		new MsPaint();
	}
}