package vm160627;

public class Figura {
	
	private Polje polje;
	private Igrac vlasnik;
	
	public Figura(Igrac v) {
		vlasnik=v;
		polje=null;
	}
	//Proverava da li moze da se pomeri figura i ako moze prebacuje figuru na dato polje
	public boolean jelMozePomeraj(Polje p) {
		if (!jelPoljeDostizno(p)) {return false;}
		if (p==null) return false;
		if (p.isIgracNa()) return false;
		if (polje!=null && (Igra.matrica[p.getX()][p.getY()].getBrPlocica()-1 > Igra.matrica[polje.getX()][polje.getY()].getBrPlocica() || p.getBrPlocica()==4 || p.isIgracNa())) {
			return false;
		}
		else {
			if (polje!=null){
				polje.setIgrac(null);
				Igra.matrica[polje.getX()][polje.getY()].setIgrac(null);
			}
			polje = p;
			p.setIgrac(this);
			Igra.matrica[p.getX()][p.getY()].setIgrac(this);
		}
		return true;
	}
	//Proverava da li je moguca gradnja na datom polju
	public boolean jelMozeGradjenje(Polje p) {
		if (p.getBrPlocica()==4) return false;
		if (!jelPoljeDostizno(p)) return false;
		if (p.isIgracNa()) return false;
		return true;
	}
	//Provera da li je udaljenost izmedju polja 1
	public boolean jelPoljeDostizno(Polje p) {
		if(polje==null) return true;
		if ((p.getX()==polje.getX() || p.getX()-1 == polje.getX() || p.getX()+1==polje.getX()) &&(p.getY()==polje.getY() || p.getY()-1 == polje.getY() || p.getY()+1==polje.getY()))
			return true;
		return false;
	}
	public Polje getPolje() {return polje;}
	
	public void setPolje(Polje polj) {
		if(polj!=null) {
		polje = polj;
		}
	}
	public Igrac getVlasnik() {
		return vlasnik;
	}
	public void setVlasnik(Igrac vlasnik) {
		this.vlasnik = vlasnik;
	}
	
}
