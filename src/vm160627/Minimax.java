package vm160627;


public class Minimax {
	private Tezina tezina;
	private Strategija strat;
	static protected class Cvor{
		Potez potez;
		int vrednost;
	}
	private Potez potez;
	public Minimax(Tezina t) {
		tezina=t;
		if (t==Tezina.EASY)
			strat=new Easy();
	}
	
	public Cvor minimaxPotez(int depth,Igra i,boolean isMP) {
		
		if(depth==0) {
			return strat.proracun(i);
		
		}
		Potez potezi[];
		if(i.getNaRedu()==1)
			potezi=i.getI1().getSviMoguciPotezi();
		else
			potezi=i.getI2().getSviMoguciPotezi();
		
		Cvor c=new Cvor();
		if (isMP) c.vrednost=-100000; else c.vrednost=100000;
		
		for (int j=0 ; j < potezi.length-1 ; j++) {
			i.dodajPotez(potezi[j]);
			Cvor pom = minimaxPotez(depth-1,i,!isMP);
			if (isMP) {
				if (pom.vrednost > c.vrednost) {
					c.vrednost=pom.vrednost;
					c.potez=i.getPoslednjiPotez();
				}
			}
			else {
				if (pom.vrednost < c.vrednost) {
					c.vrednost=pom.vrednost;
					c.potez=i.getPoslednjiPotez();
				}
			}
			i.undo();
		}
		potez=c.potez;
		return c;
	}

	public Potez getPotez() {
		return potez;
	}

	public void setPotez(Potez potez) {
		this.potez = potez;
	}
}
