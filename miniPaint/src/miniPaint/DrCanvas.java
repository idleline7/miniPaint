package miniPaint;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class DrCanvas extends Canvas {
	private MsPaint mp; // mp 받을 걸 보관
	private Image bufferImage;
	private Graphics bufferG;

	public DrCanvas(MsPaint mp) {
		this.mp = mp;
		setBackground(new Color(255, 255, 255));

	}

	@Override
	public void update(Graphics g) {
		int x1, x2, y1, y2, z1, z2;
		Dimension d = this.getSize();

		if (bufferG == null) {
			// 실제화면 사이즈 만큼의 이미지버퍼를 생성
			bufferImage = this.createImage(d.width, d.height);
			bufferG = bufferImage.getGraphics();// bufferImage로부터 객체를 반환하여 bufferG에 저장
		}
		bufferG.setColor(this.getBackground());
		bufferG.fillRect(0, 0, d.width, d.height);

		// ArrayList에 담아놓은 도형을 다시 그린다
		for (ShapeDTO dto : mp.getList()) {
			x1 = dto.getX1();
			y1 = dto.getY1();
			x2 = dto.getX2();
			y2 = dto.getY2();
			z1 = dto.getZ1();
			z2 = dto.getZ2();

			switch (dto.getColor()) {
			case 0:
				bufferG.setColor(Color.RED);
				break;
			case 1:
				bufferG.setColor(Color.GREEN);
				break;
			case 2:
				bufferG.setColor(Color.BLUE);
				break;
			case 3:
				bufferG.setColor(Color.MAGENTA);
				break;
			case 4:
				bufferG.setColor(Color.ORANGE);
				break;
			}

			if (dto.getFill()) {
				// fill = true; (채워지면 true)
				if (dto.getShape() == Figure.LINE) {
					bufferG.drawLine(x1, y1, x2, y2);
				} else if (dto.getShape() == Figure.CIRCLE) {
					bufferG.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
				} else if (dto.getShape() == Figure.RECT) {
					bufferG.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
				} else if (dto.getShape() == Figure.ROUNDRECT) {
					bufferG.fillRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1), z1,
							z2);
				} else if (dto.getShape() == Figure.PEN) {
					bufferG.drawLine(x1, y1, x2, y2);
				}
			} else {
				if (dto.getShape() == Figure.LINE) {
					bufferG.drawLine(x1, y1, x2, y2);
				} else if (dto.getShape() == Figure.CIRCLE) {
					bufferG.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
				} else if (dto.getShape() == Figure.RECT) {
					bufferG.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
				} else if (dto.getShape() == Figure.ROUNDRECT) {
					bufferG.drawRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1), z1,
							z2);
				} else if (dto.getShape() == Figure.PEN) {
					bufferG.drawLine(x1, y1, x2, y2);
				}
			}
		}

		// 좌표 얻어오기
		x1 = Integer.parseInt(mp.getX1T().getText());
		y1 = Integer.parseInt(mp.getY1T().getText());
		x2 = Integer.parseInt(mp.getX2T().getText());
		y2 = Integer.parseInt(mp.getY2T().getText());
		z1 = Integer.parseInt(mp.getZ1T().getText());
		z2 = Integer.parseInt(mp.getZ2T().getText());

		switch (mp.getCombobox().getSelectedIndex()) {
		case 0:
			bufferG.setColor(Color.RED);
			break;
		case 1:
			bufferG.setColor(Color.GREEN);
			break;
		case 2:
			bufferG.setColor(Color.BLUE);
			break;
		case 3:
			bufferG.setColor(Color.MAGENTA);
			break;
		case 4:
			bufferG.setColor(Color.ORANGE);
			break;
		}

		if (mp.getFill().isSelected()) {
			if (mp.getLine().isSelected()) {
				bufferG.drawLine(x1, y1, x2, y2);
			} else if (mp.getCircle().isSelected()) {
				bufferG.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
			} else if (mp.getRect().isSelected()) {
				bufferG.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
			} else if (mp.getRoundRect().isSelected()) {
				bufferG.fillRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1), z1, z2);
			}
		} else {
			if (mp.getLine().isSelected()) {
				bufferG.drawLine(x1, y1, x2, y2);
			} else if (mp.getCircle().isSelected()) {
				bufferG.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
			} else if (mp.getRect().isSelected()) {
				bufferG.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
			} else if (mp.getRoundRect().isSelected()) {
				bufferG.drawRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1), z1, z2);
			}
		}
		paint(g);
	}

	@Override
	public void paint(Graphics g) { // 페인트함수가 콜백 함수라서 빈값을 가져옴(문제) -> 초기값을 0으로 설정
		g.drawImage(bufferImage, 0, 0, this);
	}

}
