package vm160627;

import vm160627.Minimax.Cvor;

public class Easy extends Strategija {

	@Override
	public Cvor proracun(Igra i) {
		
		Potez p=i.getPoslednjiPotez();
		
		Cvor c=new Cvor();
		
		int m,l;
		
		if(p.getGradnja()!=null) {
			m=p.getNa().getBrPlocica()*50;
			l=p.getGradnja().getBrPlocica() * Math.abs((( Polje.vazdusnoRastojanje(i.getI1().getF1().getPolje(), p.getGradnja()) + 
					Polje.vazdusnoRastojanje(i.getI1().getF2().getPolje(), p.getGradnja()))-
						(Polje.vazdusnoRastojanje(i.getI2().getF1().getPolje(), p.getGradnja())+
							Polje.vazdusnoRastojanje(i.getI2().getF2().getPolje(), p.getGradnja()))));
		}
		else {
			if(p.getNa().getBrPlocica()!=3){
				m=(int)(Math.random()*10);
				l=(int)(Math.random()*10);
			}
			else {
				m=1000;
				l=0;
			}
		}
		c.potez=p;
		c.vrednost = m+l;
		return c;
	}

}
