package vm160627;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Player extends Igrac implements MouseListener{

	private Potez potez;
	private int akcija=0;
	private boolean mozeDalje;
	
	public Player(Igra i) {
		super(i);
		Igra.platno.addMouseListener(this);
	}
	
	@Override
	public Potez odigraj() {
		return potez;
		//if (spreman) return potez;
		//else return null;
	}

	public Polje koordPolje(int x,int y) {
		int px=(x-230)/110, py=(y-60)/110;
		if (px<0 ||px>4 ||py<0 ||py>4)
			return null;
		else {
			if (x>px*110+310 || y>py*110 +140 || x<230|| y<60) 
				return null;
		}
			return Igra.matrica[px][py];
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (prviPotez) {
				if(f1.getPolje()==null) {
					setF1(new Figura(this));
					mozeDalje=postaviFiguruPrviPotez(1,koordPolje(e.getX(),e.getY()));
					if (mozeDalje) {
						Igra.platno.repaint();
						
						potez=new Potez(koordPolje(e.getX(),e.getY()),null,null);
					}
				}
				else {
					setF2(new Figura(this));
					mozeDalje=postaviFiguruPrviPotez(2,koordPolje(e.getX(),e.getY()));
					if(mozeDalje) {
						Igra.platno.repaint();
						potez.setNa(koordPolje(e.getX(),e.getY()));
						prviPotez=false;
						spreman=true;
						i.obavesti();

					}
				}
		}
		else {
			if (akcija==0) {
				if(koordPolje(e.getX(),e.getY())!=null && Igra.matrica[koordPolje(e.getX(),e.getY()).getX()][koordPolje(e.getX(),e.getY()).getY()].isIgracNa()==true) {
						if(((f1.getPolje().getX()==koordPolje(e.getX(),e.getY()).getX() && f1.getPolje().getY()==koordPolje(e.getX(),e.getY()).getY()) ||
								(f2.getPolje().getX()==koordPolje(e.getX(),e.getY()).getX() && f2.getPolje().getY()==koordPolje(e.getX(),e.getY()).getY()) )
									&&(f1.getVlasnik().getId()==Igra.naRedu || f2.getVlasnik().getId()==Igra.naRedu) ) { 
							setNaPotezu(koordPolje(e.getX(),e.getY()).getIgrac());
							akcija=1;

							potez=new Potez(koordPolje(e.getX(),e.getY()),null,null);	
						}
				}
			}
			else
			if(akcija==1) {
				//Postavljanje igraca
				if (koordPolje(e.getX(),e.getY())!=null) {
						mozeDalje=pomeriFiguruNaPotezu(koordPolje(e.getX(),e.getY()));
					if(mozeDalje) {
						
						akcija=2;
						Igra.platno.repaint();
						
						potez.setNa(koordPolje(e.getX(),e.getY()));
						if(koordPolje(e.getX(),e.getY()).getBrPlocica()==3) {
							spreman=true;
							i.obavesti();
						}
					}
				}
			}
			else
			if (akcija==2) {
				if (koordPolje(e.getX(),e.getY())!=null&&naPotezu.jelMozeGradjenje(koordPolje(e.getX(),e.getY())))
					if (koordPolje(e.getX(),e.getY())!=null) {
						koordPolje(e.getX(),e.getY()).dodajPlocicu();
						Igra.platno.repaint();
			
						akcija=0;
						
						potez.setGradnja(koordPolje(e.getX(),e.getY()));
						
						spreman=true;
						i.obavesti();
					}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
