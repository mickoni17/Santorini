package vm160627;

public class CPU extends Igrac {

	private Minimax mx;
	
	public CPU(Igra i) {
		super(i);
		mx=new Minimax(Tezina.EASY);
	}

	@Override
	public Potez odigraj() {
		if (prviPotez) {
			Potez[] p=getSviMoguciPotezi();
			int ik=(int)(Math.random()*p.length);
			Polje p1=p[ik].getSa();
			Polje p2=p[ik].getNa();
			i.matrica[p1.getX()][p1.getY()].setIgrac(f1);
			i.matrica[p2.getX()][p2.getY()].setIgrac(f2);
			prviPotez=false;
			spreman=true;
			i.obavesti();
			return p[ik];
		}
		else {
			Polje [][]mat2=new Polje[5][5];
			for(int i=0;i<5;i++)
				for(int j=0;j<5;j++) {
					mat2[i][j]=new Polje(i,j);
					mat2[i][j].setIgrac(Igra.matrica[i][j].getIgrac());
					mat2[i][j].setBrPlocica(Igra.matrica[i][j].getBrPlocica());
				}
			i.MX=true;
			mx.minimaxPotez(1, i, true);
			i.MX=false;
			for(int i=0;i<5;i++)
				for(int j=0;j<5;j++) {
					Igra.matrica[i][j]=new Polje(i,j);
					Igra.matrica[i][j].setIgrac(mat2[i][j].getIgrac());
					Igra.matrica[i][j].setBrPlocica(mat2[i][j].getBrPlocica());
				}
			
			if (f1.getPolje()!=null && f1.getPolje()==mx.getPotez().getSa())
				i.matrica[mx.getPotez().getSa().getX()][mx.getPotez().getSa().getY()].setIgrac(f1);
			else
				i.matrica[mx.getPotez().getSa().getX()][mx.getPotez().getSa().getY()].setIgrac(f2);
			spreman=true;
			i.obavesti();
			return mx.getPotez();
		}
	}

}
