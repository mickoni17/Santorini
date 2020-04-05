package vm160627;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CrtacMatrice extends Canvas implements KeyListener{
	
	private Igra igra;
	private Main platno;
	private boolean showInfo=true;
	private String p1,p2;
	
	public CrtacMatrice(Main m) {
		
		platno=m;
		setSize(platno.getWidth(),platno.getHeight());	
		setBackground(Color.BLUE);
		addKeyListener(this);
	}
		
	public void setIgra(Igra ig) {
		igra=ig;
	}

	@Override
	public void paint(Graphics g) {

		//Crtanje matrice
		g.setColor(Color.WHITE);
		for (int i=0;i<5;i++)
			for (int j=0;j<5;j++) {
				for (int k=0 ; k<igra.matrica[i][j].getBrPlocica()+1 ; k++) {
					if(k<4)
						g.fill3DRect(i*110+230-k*5, j*110+60-k*5, 80, 80, true);
					else {
						g.setColor(Color.BLUE);
						g.fillOval(i*110+230, j*110+60, 50, 50);
						g.setColor(Color.WHITE);
					}
					if(showInfo && k==igra.matrica[i][j].getBrPlocica() && k!=4) {
						g.setColor(Color.BLACK);
						g.drawString(""+k,i*110+230-k*5+5, j*110+60-k*5+15);
						g.setColor(Color.WHITE);
					}
				}
			}
		
		//Crtanje igraca
		if (igra.getI1().getF1().getPolje()!=null) {
			g.setColor(Color.GREEN);
			g.fillOval(igra.getI1().getF1().getPolje().getX()*110+230+5*(4-igra.getI1().getF1().getPolje().getBrPlocica())/2, igra.getI1().getF1().getPolje().getY()*110+60+5*(4-igra.getI1().getF1().getPolje().getBrPlocica())/2, 50, 50);
			g.setColor(Color.WHITE);
			g.fillOval(igra.getI1().getF1().getPolje().getX()*110+240+5*(4-igra.getI1().getF1().getPolje().getBrPlocica())/2, igra.getI1().getF1().getPolje().getY()*110+70+5*(4-igra.getI1().getF1().getPolje().getBrPlocica())/2, 30, 30);
		}
		if (igra.getI1().getF2().getPolje()!=null) {
			g.setColor(Color.GREEN);
			g.fillOval(igra.getI1().getF2().getPolje().getX()*110+230+5*(4-igra.getI1().getF2().getPolje().getBrPlocica())/2, igra.getI1().getF2().getPolje().getY()*110+60+5*(4-igra.getI1().getF2().getPolje().getBrPlocica())/2, 50, 50);
			g.setColor(Color.WHITE);
			g.fillOval(igra.getI1().getF2().getPolje().getX()*110+240+5*(4-igra.getI1().getF2().getPolje().getBrPlocica())/2, igra.getI1().getF2().getPolje().getY()*110+70+5*(4-igra.getI1().getF2().getPolje().getBrPlocica())/2, 30, 30);
		}
		if (igra.getI2().getF1().getPolje()!=null) {
			g.setColor(Color.RED);
			g.fillOval(igra.getI2().getF1().getPolje().getX()*110+230+5*(4-igra.getI2().getF1().getPolje().getBrPlocica())/2, igra.getI2().getF1().getPolje().getY()*110+60+5*(4-igra.getI2().getF1().getPolje().getBrPlocica())/2, 50, 50);
			g.setColor(Color.WHITE);
			g.fillOval(igra.getI2().getF1().getPolje().getX()*110+240+5*(4-igra.getI2().getF1().getPolje().getBrPlocica())/2, igra.getI2().getF1().getPolje().getY()*110+70+5*(4-igra.getI2().getF1().getPolje().getBrPlocica())/2, 30, 30);
		}
		if (igra.getI2().getF2().getPolje()!=null) {
			g.setColor(Color.RED);
			g.fillOval(igra.getI2().getF2().getPolje().getX()*110+230+5*(4-igra.getI2().getF2().getPolje().getBrPlocica())/2, igra.getI2().getF2().getPolje().getY()*110+60+5*(4-igra.getI2().getF2().getPolje().getBrPlocica())/2, 50, 50);
			g.setColor(Color.WHITE);
			g.fillOval(igra.getI2().getF2().getPolje().getX()*110+240+5*(4-igra.getI2().getF2().getPolje().getBrPlocica())/2, igra.getI2().getF2().getPolje().getY()*110+70+5*(4-igra.getI2().getF2().getPolje().getBrPlocica())/2, 30, 30);
		}

		//Crtanje ispisa
		if (igra.getVrsta()==vrstaIgre.PVP) {p1="P1";p2="P2";} else
		if (igra.getVrsta()==vrstaIgre.PVC) {p1="P1";p2="CPU1";} else
		if (igra.getVrsta()==vrstaIgre.CVC) {p1="CPU1";p2="CPU2";}
		g.setFont(new Font("Times New Roman",Font.BOLD,40));
		g.setColor(Color.GREEN);
		g.drawString(p1, 20, 50);
		g.setColor(Color.RED);
		g.drawString(p2, 840, 50);
		
		//Linija za ciji je potez
		int duzinaLinije=50;
		if(igra.getNaRedu()==1) {
			g.setColor(Color.GREEN);
			if (igra.getI1() instanceof CPU)
				duzinaLinije=100;
			g.fillRect(18, 60, duzinaLinije, 10);
		}
		else
		if (igra.getNaRedu()==2) {
			g.setColor(Color.RED);
			if (igra.getI2()instanceof CPU)
				duzinaLinije=100;
			g.fillRect(838, 60, duzinaLinije, 10);
		}
		
		//Tekst za akciju
		/*if(showInfo) {
		g.setColor(Color.WHITE);
		if (igra.getAkcija()==0 && igra.isPrviPotez())
			g.drawString("Place Pawns", 390, 700);
		if (igra.getAkcija()==0 && !igra.isPrviPotez())
			g.drawString("Pick", 450, 700);
		if (igra.getAkcija()==1)
			g.drawString("Move", 450, 700);
		if (igra.getAkcija()==2)
			g.drawString("Build", 450, 700);
		}*/
		
		if(igra.jelKraj()) {
			g.setColor(Color.WHITE);
			g.drawString(igra.getPobednik(), 420, 700);
		}
		
		//Informacije
		g.setColor(Color.WHITE);
		g.setFont(new Font("Times New Roman",Font.ITALIC,20));;
		g.drawString("F1 for file export", 10, 640);
		g.drawString("Space to pause CPU vs CPU", 10, 670);
		g.drawString("Enter to simulate till the end", 10, 700);
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode()==KeyEvent.VK_I) {
			showInfo=!showInfo;
			repaint();
		}
		if (e.getKeyCode()==KeyEvent.VK_F1) {
			igra.ispisiPartiju();
		}
		if (e.getKeyCode()==KeyEvent.VK_ENTER) {
			igra.obavesti();
			igra.cpuVreme=0;
			igra.setCpuPause(false);
		}
		if (e.getKeyCode()==KeyEvent.VK_SPACE) {
			if(igra.getVrsta()==vrstaIgre.CVC) {
				igra.setCpuPause(!igra.isCpuPause());
				igra.obavesti();
			}
		}
	}
		
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
