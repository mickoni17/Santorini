package vm160627;

public class Polje {

	private int brPlocica,x,y;
	protected Figura igrac;
	
	public Polje(int x1,int y1) {
		x=x1;y=y1;
		brPlocica=0;
		igrac=null;
	}
	
	public boolean isIgracNa() {
		return (igrac!=null);
	}
	public void postaviIgraca(Figura igracNa) {
		this.igrac = igracNa;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Figura getIgrac() {
		return igrac;
	}
	public void setIgrac(Figura igrac) {
		this.igrac = igrac;
	}
	public int getBrPlocica() {
		return brPlocica;
	}
	public void setBrPlocica(int k) {
		brPlocica=k;
	}
	public boolean dodajPlocicu() {
		if(brPlocica<4) {
			//Igra.matrica[x][y].brPlocica++;
			brPlocica++;
			return true;
		}
		return false;
	}
	public boolean skiniPlocicu() {
		if (brPlocica>0) {
			Igra.matrica[x][y].brPlocica--;
			brPlocica--;
			return true;
		}
		return false;
	}
	public void ujednaciPlocicaFigura() {
		if (igrac!=null)
			igrac.setPolje(this);
	}
	public void kaziIgracuDaSeBrisem() {
		if (igrac!=null)
			igrac.setPolje(null);
	}
	static public int vazdusnoRastojanje(Polje p1,Polje p2) {
		if(p1==null || p2==null) return 0;
		if (p1.getX()==p2.getX())
			return Math.abs(p1.getY()-p2.getY());
		else {
			if (p1.getY()==p2.getY())
				return Math.abs(p1.getX()-p2.getX());
		}
		
		int k=0,x1=p1.getX(),x2=p2.getX(),y1=p1.getY(),y2=p2.getY();
		while(x1!=x2 && y1!=y2) {
			if(x1>x2) x1--;
			else x2--;
			if(y1>y2) y1--;
			else y2--;
			k++;
			if(x1==x2) return k+Math.abs(y1-y2);
			if(y1==y2) return k+Math.abs(x1-x2);
		}
		return k;
		
		
	}
}
