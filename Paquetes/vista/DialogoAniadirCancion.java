/*Clase que implementa un dialogo para poder añadir canciones a la aplicacion, esta clase utiliza
 * el api de bitly para java #Bitlyj https://code.google.com/p/bitlyj/ acortando la URL de las canciones
 * para tener mas espacio en el tweet
 * 
 * Autor: Riki Gomez (twitter @ricardo_gomez95 ) 
 */
package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.JTextField;

import com.rosaloves.bitlyj.Bitly;
import com.rosaloves.bitlyj.BitlyException;
import com.rosaloves.bitlyj.ShortenedUrl;
import com.rosaloves.bitlyj.Url;

import static com.rosaloves.bitlyj.Bitly.*;
import twitter.Autorizacion;

public class DialogoAniadirCancion extends JDialog implements ActionListener,KeyListener{

	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JButton cancelButton;
	private JTextField nombreCancion;
	private JTextField URLCancion;
	private ArrayList<CancionPanel> listaCanciones;
	private Autorizacion autorizacion;

	/**
	 * Launch the application.
	 */
	public DialogoAniadirCancion(JFrame frame, boolean modo,ArrayList<CancionPanel> lista,Autorizacion autorizacion){
		super(frame,modo);
		this.autorizacion=autorizacion;
		listaCanciones=lista;
		this.iniciar();
		
	}
	/* CONSTRUCTOR DE PRUEBA 
	public DialogoAniadirCancion() {
		listaCanciones=VentanaAplicacionGUI.cargarCanciones("datos.txt",new ArrayList<CancionPanel>());
		setBounds(100, 100, 450, 157);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nombre de la cancion:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 21, 152, 14);
		contentPanel.add(lblNewLabel);
		
		nombreCancion = new JTextField();
		nombreCancion.addKeyListener(this);
		nombreCancion.addActionListener(this);
		nombreCancion.setBounds(163, 19, 261, 20);
		contentPanel.add(nombreCancion);
		nombreCancion.setColumns(10);
		
		JLabel lblUrl = new JLabel("URL:");
		lblUrl.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUrl.setBounds(10, 57, 36, 14);
		contentPanel.add(lblUrl);
		
		URLCancion = new JTextField();
		URLCancion.setBounds(56, 55, 368, 20);
		URLCancion.addKeyListener(this);
		contentPanel.add(URLCancion);
		URLCancion.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Guardar");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				okButton.setEnabled(false);
				buttonPane.add(okButton);
				
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(this);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public static void main(String[] argv) {
		
		
		try {
			DialogoAniadirCancion dialog = new DialogoAniadirCancion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void launch(ArrayList<CancionPanel> listaCanciones) {
		
		this.listaCanciones=listaCanciones;
		
		try {
			DialogoAniadirCancion dialog = new DialogoAniadirCancion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	/**
	 * Create the dialog.
	 */
	public void iniciar() {
		setBounds(100, 100, 450, 157);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nombre de la cancion:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 21, 152, 14);
		contentPanel.add(lblNewLabel);
		
		nombreCancion = new JTextField();
		nombreCancion.addKeyListener(this);
		nombreCancion.addActionListener(this);
		nombreCancion.setBounds(163, 19, 261, 20);
		contentPanel.add(nombreCancion);
		nombreCancion.setColumns(10);
		
		JLabel lblUrl = new JLabel("URL:");
		lblUrl.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUrl.setBounds(10, 57, 36, 14);
		contentPanel.add(lblUrl);
		
		URLCancion = new JTextField();
		URLCancion.setBounds(56, 55, 368, 20);
		URLCancion.addKeyListener(this);
		contentPanel.add(URLCancion);
		URLCancion.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Guardar");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				okButton.setEnabled(false);
				buttonPane.add(okButton);
				
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(this);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	//@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stu
		if(e.getSource()==okButton)
		{
			String nombreC=nombreCancion.getText();
			String URL=URLCancion.getText();
			ShortenedUrl urlAcortada=null;
			Provider bitly = as(/*Credencial de bitly*/,/*Credencial de bitly*/);
			try
			{
				urlAcortada=bitly.call(shorten(URL));
			}
			catch(BitlyException ex)
			{
				ex.printStackTrace();
				System.out.println(ex.getMessage());
				if(ex.getMessage().equals("INVALID_URI"))
					URLCancion.setText("introduzca una URL valida");
				else
					URLCancion.setText(ex.getMessage());
			}
			URL=urlAcortada.getShortUrl();
			CancionPanel cancion=new CancionPanel(nombreC, URL, autorizacion);
			listaCanciones.add(cancion);
			System.out.println("nuevaCancion: "+listaCanciones.get(listaCanciones.size()-1).getNombre()+","+listaCanciones.get(listaCanciones.size()-1).getUrl());
			System.out.println(listaCanciones.size());
			this.dispose();	
		}
		else if(e.getSource()==cancelButton)
		{
			this.dispose();
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
			boolean nombreVacio=nombreCancion.getText().equals("");
			boolean URLVacio=URLCancion.getText().equals("");
			if(!nombreVacio && !URLVacio)
			{
				okButton.setEnabled(true);
			}
			else
			{
				okButton.setEnabled(false);
			}
					
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub		
	}
	
	public ArrayList<CancionPanel> getListaCanciones()
	{
		return listaCanciones;
	}
}
