package vm160627;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame {
	
	private ImageIcon pozadina;
	private JButton pvp=new JButton("Player VS Player"),pvc=new JButton("Player VS CPU"),cvc=new JButton("CPU VS CPU"),opt=new JButton("Options"),back=new JButton("Back");
	private JPanel prikaz=new JPanel();
	private JLabel glavniMeni, opcijeMeni,options,difficulty;
	private JRadioButton easy,medium,hard;
	private ButtonGroup tezine = new ButtonGroup();
	private Igra igra;
	private CrtacMatrice crtac = new CrtacMatrice(this);
	private class Dijalog extends Dialog{
		private JLabel izbor;
		private JButton newGame,fromFile;
		private Frame parent;
				public Dijalog(Frame arg) {
			super(arg,"Window",false);
			parent=arg;
			setSize(400,150);
			//addWindowListener(new WindowAdapter() {
			//	public void windowClosing(WindowEvent we) {setVisible(false);}
			//});
			izbor=new JLabel("How do you wish to start the game?");
			newGame=new JButton("New Game");
			fromFile=new JButton("From File");
			
			setResizable(false);
			setLayout(null);
			
			izbor.setBounds(100, 30, 380, 40);
			newGame.setBounds(30, 75, 160, 45);
			fromFile.setBounds(210, 75, 160, 45);
			add(izbor);
			add(newGame);
			add(fromFile);
			
			AkcijaNewGame ang=new AkcijaNewGame();
			AkcijaFromFile aff=new AkcijaFromFile();
			newGame.addActionListener(ang);
			fromFile.addActionListener(aff);
		}

	}
	private Dijalog dijalog;
	private JFileChooser chooser = new JFileChooser();
	
	public Main() {
		super("Santorini");
		setSize(1000,800);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		dijalog=new Dijalog(this);
		
		praviPozadinu();
		kreirajGlavniMeni();
		kreirajOpcijeMeni();
		
		prikaz.add(glavniMeni);
		add(prikaz);
		
		setVisible(true);
	}
	
	public void kreirajGlavniMeni() {
		glavniMeni = new JLabel(pozadina);
		
		//Dodavanje dugmica
		glavniMeni.setLayout(null);
		pvp.setBounds(50, 50, 140, 50);
		pvc.setBounds(50, 130, 140, 50);
		cvc.setBounds(50, 210, 140, 50);
		opt.setBounds(50, 290, 140, 50);
		glavniMeni.add(pvp);
		glavniMeni.add(pvc);
		glavniMeni.add(cvc);
		glavniMeni.add(opt);
		
		AkcijaOptions aopt=new AkcijaOptions(); opt.addActionListener(aopt);
		AkcijaIgraPVP aipvp=new AkcijaIgraPVP(); 
		AkcijaIgraPVC aipvc=new AkcijaIgraPVC(); 
		AkcijaIgraCVC aicvc=new AkcijaIgraCVC(); 
		pvp.addActionListener(aipvp); 
		pvc.addActionListener(aipvc);
		cvc.addActionListener(aicvc);
	}
	
	public void kreirajOpcijeMeni() {
		opcijeMeni = new JLabel(pozadina);
		
		opcijeMeni.setLayout(null);
		options=new JLabel("Options:");difficulty = new JLabel("Difficulty:");
		options.setFont(new Font("Serif",Font.BOLD,30));
		difficulty.setFont(new Font("Serif",Font.CENTER_BASELINE,26));
		easy=new JRadioButton("Easy"); medium = new JRadioButton("Medium");hard = new JRadioButton("Hard");
		
		easy.setSelected(true);
		tezine.add(easy); tezine.add(medium);tezine.add(hard);
		
		easy.setOpaque(false);easy.setContentAreaFilled(false);easy.setBorderPainted(false);
		medium.setOpaque(false);medium.setContentAreaFilled(false);medium.setBorderPainted(false);
		hard.setOpaque(false);hard.setContentAreaFilled(false);hard.setBorderPainted(false);
		
		options.setBounds(50, 20, 200, 100);
		difficulty.setBounds(70, 100, 250, 80);
		easy.setBounds(80, 180, 80, 30);
		medium.setBounds(80, 210, 80, 30);
		hard.setBounds(80, 240, 80, 30);
		back.setBounds(50, 600, 80, 50);
		
		opcijeMeni.add(options);
		opcijeMeni.add(difficulty);
		opcijeMeni.add(easy);
		opcijeMeni.add(medium);
		opcijeMeni.add(hard);
		opcijeMeni.add(back);
		
		AkcijaBack ab=new AkcijaBack(); back.addActionListener(ab);
		AkcijaTezina at = new AkcijaTezina();easy.addActionListener(at);medium.addActionListener(at);hard.addActionListener(at);
	}
	
	public void praviPozadinu() {
		Image image=null;
		try {
			image = ImageIO.read(new File("Santorini2.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pozadina = new ImageIcon(image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
	}
	
	private class AkcijaBack implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			getContentPane().removeAll();
			prikaz=new JPanel();
			prikaz.add(glavniMeni);
			add(prikaz);
			setVisible(true);
		}
		
	}
	private class AkcijaOptions implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			getContentPane().removeAll();
			prikaz=new JPanel();
			prikaz.add(opcijeMeni);
			add(prikaz);
			setVisible(true);
		}
		
	}
	private class AkcijaTezina implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(easy.isSelected()) Igra.setTezina(Tezina.EASY);
			else if(medium.isSelected()) Igra.setTezina(Tezina.MEDIUM);
			else Igra.setTezina(Tezina.HARD);
		}
		
	}
	private class AkcijaIgraPVP implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			getContentPane().removeAll();
			igra=new Igra(vrstaIgre.PVP,crtac);
			crtac.setIgra(igra);
			add(crtac);
			setVisible(true);
			dijalog.setVisible(true);
		}
		
	}
	private class AkcijaIgraPVC implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			getContentPane().removeAll();
			igra=new Igra(vrstaIgre.PVC,crtac);
			crtac.setIgra(igra);
			add(crtac);
			setVisible(true);
			dijalog.setVisible(true);
		}
		
	}
	private class AkcijaIgraCVC implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			getContentPane().removeAll();
			igra=new Igra(vrstaIgre.CVC,crtac);
			crtac.setIgra(igra);
			add(crtac);
			setVisible(true);
			dijalog.setVisible(true);
		}
		
	}
	private class AkcijaNewGame implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			igra.start();
			dijalog.setVisible(false);
		}
		
	}
	private class AkcijaFromFile implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT","txt");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(dijalog);
		   
		    File f=chooser.getSelectedFile();
		    
		    igra.importuj(f);
		    
			igra.start();
			dijalog.setVisible(false);
		}
		
	}
	public static void main(String[]args) {
		new Main();
	}
}