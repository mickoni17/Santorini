package vm160627;

public class Potez {

	private Polje gradnja, sa, na;
	
	public Potez (Polje k1,Polje k2,Polje g){
		sa=k1; na=k2; gradnja=g;
	}
	public Polje getGradnja() {
		return gradnja;
	}
	
	public void setGradnja(Polje gradnja) {
		this.gradnja = gradnja;
	}
	public boolean smanjiGradnju() {
		if (gradnja==null) return true;
		return gradnja.skiniPlocicu();
	}
	public Polje getSa() {
		return sa;
	}

	public void setSa(Polje sa) {
		this.sa = sa;
	}

	public Polje getNa() {
		return na;
	}

	public void setNa(Polje na) {
		this.na = na;
	}
	//Prebaci igraca sa polja sa na polje na
	public void prebaciSaNa() {
		na.setIgrac(sa.getIgrac());
		Igra.matrica[sa.getX()][sa.getY()].setIgrac(null);
		Igra.matrica[na.getX()][na.getY()].setIgrac(na.getIgrac());
		sa.setIgrac(null);
		Figura f=na.getIgrac();
		if(f!=null) {
			f.setPolje(na);
		}
	}
	public void undoKretnja() {
		if(gradnja==null && na.getBrPlocica()!=3) {
			sa.kaziIgracuDaSeBrisem();
			sa.setIgrac(null);
			na.kaziIgracuDaSeBrisem();
			na.setIgrac(null);
			return;
		}
		sa.setIgrac(na.getIgrac());
		Igra.matrica[sa.getX()][sa.getY()].setIgrac(sa.getIgrac());
		na.setIgrac(null);
		Igra.matrica[na.getX()][na.getY()].setIgrac(null);
		sa.ujednaciPlocicaFigura();
	}
	//Doda plocicu 
	public void izgradi() {
		if(!Igra.importing)
		Igra.matrica[gradnja.getX()][gradnja.getY()].dodajPlocicu();
		gradnja.dodajPlocicu();
	}
	@Override
	public String toString() {
		if (gradnja!=null)
		return "Potez [sa=" + sa.getY()+":"+sa.getX() + ", na=" + na.getY()+":"+na.getX() +", gradnja=" + gradnja.getY()+":"+gradnja.getX() +  "]";
		else
		return "Potez [sa=" + sa.getY()+":"+sa.getX() + ", na=" + na.getY()+":"+na.getX() + "]";
	}
	
}
