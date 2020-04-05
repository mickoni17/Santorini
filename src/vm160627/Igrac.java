package vm160627;

import java.util.Arrays;


public abstract class Igrac {
	
	protected Figura f1,f2,naPotezu;
	static protected boolean spreman=false;
	private static int ID=0;
	protected int id;
	protected Igra i;
	protected boolean prviPotez=true;
	
	public Igrac(Igra i) {
		id=++ID;
		this.i=i;
		f1=new Figura(this);
		f2=new Figura(this);
		naPotezu=null;
	}
	
	public abstract Potez odigraj();
	
	public boolean postaviFiguruPrviPotez(int redBr,Polje p) {
		boolean rez=false;
		if (redBr == 1) rez=f1.jelMozePomeraj(p);
		else if (redBr==2) rez=f2.jelMozePomeraj(p);
		return rez;
	}
	public boolean pomeriFiguruNaPotezu(Polje p) {
		boolean rez=naPotezu.jelMozePomeraj(p);
		return rez;
	}
	
	public Potez[] getSviMoguciPotezi() {
		Potez potezi[]=new Potez[625];
		
		int koliko=0;
		
		if (f1.getPolje()==null && f2.getPolje()==null) {
			for (int i=0;i<5;i++) {
				for (int j=0;j<5;j++) {
					if (Igra.matrica[i][j].isIgracNa()) continue;
					else {
						for (int i2=0;i2<5;i2++)
							for (int j2=0;j2<5;j2++) {
								if (Igra.matrica[i2][j2].isIgracNa() || (i2==i && j2==j)) continue;
								else
									potezi[koliko++]=new Potez(Igra.matrica[i][j],Igra.matrica[i2][j2],null);
							}
					}
				}
			}
		}
		else
		for(int k=0;k<2;k++) {
			Polje pom;
			if (k==0) pom=f1.getPolje();
			else pom=f2.getPolje();
			for (int i=pom.getX()-1;i<pom.getX()+2;i++) {
				if (i==-1 || i==5) continue;
				for (int j=pom.getY()-1;j<pom.getY()+2;j++){
					if (j==-1 || j==5 || (i==pom.getX() && j==pom.getY())) continue;
					
					if(Igra.matrica[i][j].isIgracNa() || Igra.matrica[i][j].getBrPlocica()>3 
							|| Igra.matrica[i][j].getBrPlocica()-1 > Igra.matrica[pom.getX()][pom.getY()].getBrPlocica()) continue;
					
						for (int i2=i-1;i2<i+2;i2++) {
						if (i2==-1 || i2==5) continue;
						for (int j2=j-1;j2<j+2;j2++){
							if (j2==-1 || j2==5 || (i2==i && j2==j) ||(i2==pom.getX() && j2==pom.getY()) ) continue;
							
							if(Igra.matrica[i2][j2].getBrPlocica()>=4 || Igra.matrica[i2][j2].isIgracNa()) continue;
							
							potezi[koliko++]=new Potez(pom,Igra.matrica[i][j],Igra.matrica[i2][j2]);
						}
					}
				}
			}
		}
		Potez newArray[] = Arrays.copyOf(potezi, koliko);
		return newArray;
		
	}
	
	public Figura getF1() {
		return f1;
	}
	public void setF1(Figura f1) {
		this.f1 = f1;
	}
	public Figura getF2() {
		return f2;
	}
	public void setF2(Figura f2) {
		this.f2 = f2;
	}
	public Figura getNaPotezu() {
		return naPotezu;
	}
	public void setNaPotezu(Figura naPotezu) {
		this.naPotezu = naPotezu;
	}
	static public boolean isSpreman() {
		return spreman;
	}
	static public void setSpreman(boolean sreman) {
		spreman = sreman;
	}
	public int getId() {
		return id;
	}

	
}
