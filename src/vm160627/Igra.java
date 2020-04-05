package vm160627;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

enum Tezina {EASY,MEDIUM,HARD}
enum vrstaIgre {PVP,PVC,CVC}

public class Igra extends Thread{

	static private Tezina tezina;
	static private vrstaIgre vrsta;
	static protected Polje matrica[][]=new Polje[5][5];
	protected Igrac i1,i2;
	static protected CrtacMatrice platno;
	protected String pobednik;
	static protected int cpuVreme,naRedu;
	private boolean kraj;
	static protected boolean MX=false,importing=false;
	private boolean cpuPause=true;
	static protected class cvorPotez{
		Potez p;
		cvorPotez sled, pre;
		public cvorPotez(Potez pp) {
			p=pp;
			sled=null;
		}
		public void setPre(cvorPotez cp) {
			pre=cp;
		}
	}
	protected cvorPotez prvi,tek,posl;
	public int brojac=0;
	public void ispisiPartiju() {
			    BufferedWriter writer;
				try {
					writer = new BufferedWriter(new FileWriter("Partija.txt"));
					cvorPotez pom=prvi;
					while (pom!=null) {
						char c1,c2,c3;
						c1=(char)(pom.p.getSa().getY()+65);
						c2=(char)(pom.p.getNa().getY()+65);
						String str=""+c1+(pom.p.getSa().getX()+1)+" "+c2+(pom.p.getNa().getX()+1);
						if(pom.p.getGradnja()!=null) {
							c3=(char)(pom.p.getGradnja().getY()+65);
							String extra=" "+c3+(pom.p.getGradnja().getX()+1);
							str=str+extra;
						}
						writer.write(str.toString());
						writer.newLine();
						pom=pom.sled;
					}
					writer.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	public void importuj(File f) {
		
			 BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(f));
			
			  importing=true;
				String st; 
				while ((st = br.readLine()) != null) {
					String str1,str2,str3=null;
					str1=st.substring(0, 2);
					str2=st.substring(3, 5);
					if (st.length()>6)
						str3=st.substring(6, 8);
					Polje p1,p2,p3;
					System.out.println(""+str1.charAt(1)+"="+(int)(str1.charAt(1)));
					p1=new Polje(((int)(str1.charAt(1)))-49,((int)(str1.charAt(0)))-65);
					p2=new Polje(((int)(str2.charAt(1)))-49,((int)(str2.charAt(0)))-65);
					if(str3==null)
						p3=null;
					else
						p3=new Polje(((int)(str3.charAt(1)))-49,((int)(str3.charAt(0)))-65);
					Potez p;
					if (p3!=null)
						p=new Potez(Igra.matrica[p1.getX()][p1.getY()],Igra.matrica[p2.getX()][p2.getY()],Igra.matrica[p3.getX()][p3.getY()]);
					else
						p=new Potez(Igra.matrica[p1.getX()][p1.getY()],Igra.matrica[p2.getX()][p2.getY()],null);
					dodajPotez(p);
					platno.repaint();
					ispisiPoteze();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			importing=false;
	}
	
	public Igra(vrstaIgre vi,CrtacMatrice cm) {
		for (int i =0;i<5;i++)
			for(int j=0;j<5;j++)
				matrica[i][j]=new Polje(i,j);
		
		platno=cm;
		vrsta=vi;
		cpuVreme=1000;
		naRedu=1;
		kraj=false;
		if (vrsta==vrstaIgre.PVP) {
			i1=new Player(this);
			i2=new Player(this);
		}
		else 
		if(vrsta==vrstaIgre.PVC) {
			i1=new Player(this);
			i2=new CPU(this);
		}
		else
		if(vrsta==vrstaIgre.CVC) {
			i1=new CPU(this);
			i2=new CPU(this);
		}
		
		tezina = tezina.EASY;
		prvi=tek=posl=null;
		
		
	}
	
	@Override
	public void run() {
		try {
			while(!interrupted()) {
				if(naRedu==1) {
					if (i1 instanceof Player)
					synchronized(this) {
						while (!Igrac.isSpreman()) wait();
					}
					if(i1 instanceof CPU) {
						sleep(cpuVreme);
						if(vrsta==vrstaIgre.CVC)
						synchronized(this) {
							while (cpuPause) wait();
						}
					}
					dodajPotez(i1.odigraj());
					i1.setSpreman(false);
					imamoLiPobednika();
				}
				else {
					if (i2 instanceof Player)
					synchronized(this) {
						while (!Igrac.isSpreman()) wait();
					}
					if(i2 instanceof CPU) {
						sleep(cpuVreme);
						if(vrsta==vrstaIgre.CVC)
						synchronized(this) {
							while (cpuPause)wait();
						}
					}
					dodajPotez(i2.odigraj());
					i2.setSpreman(false);
					imamoLiPobednika();
				}
				//ispisiMatricu();
				//ispisiPoteze();
				//System.out.println("Igrac 1 Figura 1: "+i1.getF1().getPolje().getY()+"/"+i1.getF1().getPolje().getX());
				//System.out.println("Igrac 1 Figura 2: "+i1.getF2().getPolje().getY()+"/"+i1.getF2().getPolje().getX());
				//if (i2.getF1().getPolje()!=null ) {
				//System.out.println("Igrac 2 Figura 1: "+i2.getF1().getPolje().getY()+"/"+i2.getF1().getPolje().getX());
				//System.out.println("Igrac 2 Figura 2: "+i2.getF2().getPolje().getY()+"/"+i2.getF2().getPolje().getX());
				//}
				//System.out.println();
				platno.repaint();
			}
		}catch (InterruptedException e) {}
	}
	public void exit() {interrupt();}
	
	public void dodajPotez(Potez p) {
		if (prvi==null) {
			prvi=new cvorPotez(p);
			prvi.setPre(null);
			tek=posl=prvi;
		}
		else {
			cvorPotez cp=new cvorPotez(p);
			cp.setPre(posl);
			posl.sled=cp;
			posl=tek=cp;
		}
		
		if ((naRedu==1 && i1 instanceof CPU)||(naRedu==2 && i2 instanceof CPU)||importing) {
		if (p.getGradnja()==null) {
			if(p.getNa().getBrPlocica()==3) {
				p.prebaciSaNa();
			}
			else {
				if(naRedu==1) {
					i1.f1.setPolje(Igra.matrica[p.getSa().getX()][p.getSa().getY()]); i1.f2.setPolje(Igra.matrica[p.getNa().getX()][p.getNa().getY()]);
					matrica[p.getSa().getX()][p.getSa().getY()].setIgrac(i1.f1);
					matrica[p.getNa().getX()][p.getNa().getY()].setIgrac(i1.f2);
				}
				else {
					i2.f1.setPolje(Igra.matrica[p.getSa().getX()][p.getSa().getY()]); i2.f2.setPolje(Igra.matrica[p.getNa().getX()][p.getNa().getY()]);
					matrica[p.getSa().getX()][p.getSa().getY()].setIgrac(i2.f1);
					matrica[p.getNa().getX()][p.getNa().getY()].setIgrac(i2.f2);
				}
			}
			
		}
		else {
			p.prebaciSaNa();
			p.izgradi();
		}
		}
		
		if(naRedu==1) naRedu=2;
		else naRedu=1;
	}
	public boolean undo() {
		if (tek==null || tek.pre==null) return false;
		tek.p.smanjiGradnju();
		tek.p.undoKretnja();
		tek=tek.pre;
		tek.sled=null;
		posl=tek;
	
		if(naRedu==1) naRedu=2;
		else naRedu=1;
		
		return true;
	}
	public boolean redo() {
		if(tek==null || tek.sled==null) return false;
		tek=tek.sled;
		return true;
	}
	
	//Osetljiva funkcija ako je polje null, neophodna provera pre poziva
		public int jelMozePomeraj(Figura ig) {
			int koliko=0;
			if(ig.getPolje()!=null)
			for (int i=ig.getPolje().getX()-1;i<ig.getPolje().getX()+2;i++) {
				if(i==-1) continue;
				if(i==5) continue;
				for (int j=ig.getPolje().getY()-1;j<ig.getPolje().getY()+2;j++) {
					if(j==-1) continue;
					if(j==5) continue;
					if(!matrica[i][j].isIgracNa() && matrica[i][j].getBrPlocica()-1 <= ig.getPolje().getBrPlocica()) 
						koliko++;
				}
			}
			return koliko;
		}
		public int jelMozeGradnja(Figura ig) {
			int koliko=0;
			if(ig.getPolje()!=null)
			for (int i=ig.getPolje().getX()-1;i<ig.getPolje().getX()+2;i++) {
				if(i==-1) continue;
				if(i==5) continue;
				for (int j=ig.getPolje().getY()-1;j<ig.getPolje().getY()+2;j++) {
					if(j==-1) continue;
					if(j==5) continue;
					if(matrica[i][j].getBrPlocica()<4 && !matrica[i][j].isIgracNa()) 
						koliko++;
				}
			}
			return koliko;
		}
		public void imamoLiPobednika() {
			if(Igra.matrica[i1.getF1().getPolje().getX()][i1.getF1().getPolje().getY()].getBrPlocica()==3 || Igra.matrica[i1.getF2().getPolje().getX()][i1.getF2().getPolje().getY()].getBrPlocica()==3) {
				pobednik="P1 WINS";
				kraj=true;
				platno.setIgra(this);
				platno.repaint();
				exit();
			}
			if((i2.getF1().getPolje()!=null && i2.getF2().getPolje()!=null)&&(Igra.matrica[i2.getF1().getPolje().getX()][i2.getF1().getPolje().getY()].getBrPlocica()==3 || Igra.matrica[i2.getF2().getPolje().getX()][i2.getF2().getPolje().getY()].getBrPlocica()==3)) {
				pobednik="P2 WINS";
				kraj=true;
				platno.setIgra(this);
				platno.repaint();
				exit();
			}
			if (!i1.prviPotez && ((jelMozePomeraj(i1.getF1())==0 && jelMozePomeraj(i1.getF2())==0) || (i1.getNaPotezu()!=null && jelMozeGradnja(i1.getNaPotezu())==0))) {
				pobednik="P2 WINS";
				kraj=true;
				platno.setIgra(this);
				platno.repaint();
				exit();
			}
			if (!i2.prviPotez && ((jelMozePomeraj(i2.getF1())==0 && jelMozePomeraj(i2.getF2())==0)||(i2.getNaPotezu()!=null && jelMozeGradnja(i2.getNaPotezu())==0 ))) {
				pobednik="P1 WINS";
				kraj=true;
				platno.setIgra(this);
				platno.repaint();
				exit();
			}
		}
	
	public void postaviIgracaNaPolje(int i,int j,Figura ig) {
		matrica[i][j].setIgrac(ig);
	}
		
	public static Tezina getTezina() {
		return tezina;
	}
	public static void setTezina(Tezina tezina) {
		Igra.tezina = tezina;
	}
	public static vrstaIgre getVrsta() {
		return vrsta;
	}
	public static void setVrsta(vrstaIgre vrsta) {
		Igra.vrsta = vrsta;
	}
	public static Polje[][] getMatrica() {
		return matrica;
	}
	public static void setMatrica(Polje[][] matrica) {
		Igra.matrica = matrica;
	}
	public Igrac getI1() {
		return i1;
	}
	public void setI1(Igrac i1) {
		this.i1 = i1;
	}
	public Igrac getI2() {
		return i2;
	}
	public void setI2(Igrac i2) {
		this.i2 = i2;
	}
	static public CrtacMatrice getPlatno() {
		return platno;
	}
	static public void setPlatno(CrtacMatrice platn) {
		platno = platn;
	}
	public String getPobednik() {
		return pobednik;
	}
	public void setPobednik(String pobednik) {
		this.pobednik = pobednik;
	}
	public int getNaRedu() {
		return naRedu;
	}
	public void setNaRedu(int naRedu) {
		this.naRedu = naRedu;
	}
	public Potez getPoslednjiPotez() {
		return posl.p;
	}
	public boolean jelKraj() {
		return kraj;
	}
	
	public boolean isCpuPause() {
		return cpuPause;
	}
	public void setCpuPause(boolean cpuPause) {
		this.cpuPause = cpuPause;
	}
	public void obavesti() {
		synchronized(this) {
		notifyAll();
		}
	}
	
	public void ispisiMatricu() {
		for (int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
				if (matrica[j][i].isIgracNa())
				System.out.print("Y["+matrica[j][i].getBrPlocica()+"] ");
				else
					System.out.print("N["+matrica[j][i].getBrPlocica()+"] ");
			}
			System.out.println("");
		}

		System.out.println("");
		System.out.println("");
	}

	public void ispisiPoteze() {
		cvorPotez pom=prvi;
		while (pom!=null) {
			System.out.println(pom.p);
			pom=pom.sled;
		}
	}
}
