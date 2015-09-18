/* Clase que contiene la interfaz de la aplicacion y la funcionalidad
 * 
 * Autor: Riki Gomez (twitter @ricardo_gomez95 )
 */
package vista;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JLayeredPane;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JScrollBar;

import java.awt.ScrollPane;
import java.awt.Button;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import twitter.Autorizacion;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaAplicacionGUI implements ActionListener {
	ArrayList<CancionPanel> listaCanciones;
	private JFrame frame;
	private Autorizacion autorizacion;
	private JButton BotomEliminarCanc;
	private JButton BotomAniadirCanc;
	private String ruta;
	private JPanel panelCanciones;
	private JPanel panelPrincipal;
	private JScrollPane scrollPane;
	private JMenuItem mntmCargarDatos;
	private JMenuItem mntmGuardarDatos;
	private JMenuItem mntmReinicarDatos;
	private JFileChooser fc = new JFileChooser();
	/**
	 * Launch the application.
	 */
	public void launch() {
		final Autorizacion auto=autorizacion;
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAplicacionGUI window = new VentanaAplicacionGUI(auto);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void main(String[] argv)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAplicacionGUI window = new VentanaAplicacionGUI(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/* METODO DE PRUEBA 
	public static ArrayList<CancionPanel> cargarCanciones(String ruta, ArrayList<CancionPanel> listaCanciones)
	{
		File file=new File(ruta,"datos.jkb");
		FileReader archivo=null;
		BufferedReader buffer=null;
		String contenido=null;
		listaCanciones.clear();
		try{
			if(file.exists())
			{
				archivo=new FileReader(ruta);
				buffer=new BufferedReader(archivo);
				if((contenido=buffer.readLine())!=null)
				{
					int numCanciones=Integer.parseInt(contenido);
					CancionPanel cancion=null;
					for(int i=0;i<numCanciones;i++)
					{
						String nombre=buffer.readLine();
						String url=buffer.readLine();
						cancion=new CancionPanel(nombre,url,null);
						listaCanciones.add(cancion);
						
					}
				}	
			}	
			else
			{
				FileWriter archivoE=new FileWriter(file);
				BufferedWriter bufferE=new BufferedWriter(archivoE);
				bufferE.write("0");

			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(archivo!=null)
				try{
					archivo.close();
				}catch(Exception e)
			{
					e.printStackTrace();
			}
		}
		return listaCanciones;
	}
		
	*/
	/** Metodo que se encarga de guardar los datos de las canciones **/
	private void guardarCanciones(String ruta)
	{
		FileWriter archivo=null;
		BufferedWriter buffer=null;
		
		try{
			archivo=new FileWriter(ruta,false);
			buffer=new BufferedWriter(archivo);
			buffer.flush();
			buffer.write(Integer.toString(listaCanciones.size()));
			System.out.println("GUARDANDO FICHERO");
			System.out.println(listaCanciones.size());
			buffer.newLine();
			for(int i=0;i<listaCanciones.size();i++)
			{
				buffer.write(listaCanciones.get(i).getNombre()); buffer.newLine();
				System.out.println(listaCanciones.get(i).getNombre());
				buffer.write(listaCanciones.get(i).getUrl()); buffer.newLine();
				System.out.println(listaCanciones.get(i).getUrl());
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(archivo!=null)
				try{
					if(buffer!=null)
						buffer.close();
					if(archivo!=null)
						archivo.close();	
				}catch(Exception e)
				{
					e.printStackTrace();
				}
		}
	}
		
	
	/**Metodo que se encargar de cargar los datos de las canciones**/
	private void cargarCanciones(String ruta)
	{

		File file=new File(ruta);
		FileReader archivo=null;
		BufferedReader buffer=null;
		String contenido=null;
		listaCanciones.clear();
		try{
			if(file.exists())
			{
				archivo=new FileReader(ruta);
				buffer=new BufferedReader(archivo);
				if((contenido=buffer.readLine())!=null)
				{
					int numCanciones=Integer.parseInt(contenido);
					CancionPanel cancion=null;
					for(int i=0;i<numCanciones;i++)
					{
						String nombre=buffer.readLine();
						String url=buffer.readLine();
						cancion=new CancionPanel(nombre,url,autorizacion);
						listaCanciones.add(cancion);
						
					}
				}	
			}	
			else
			{
				if(file.createNewFile())
				{
					System.out.println("se ha creado el archivo");
					FileWriter archivoE=new FileWriter(file);
					BufferedWriter bufferE=new BufferedWriter(archivoE);
					try{
					bufferE.write("0");
					}
					finally{
						bufferE.close();
					}
				}
				else
				{
					System.out.println("No se ha creado el archivo");
				}

			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(archivo!=null)
				try{
					archivo.close();
				}catch(Exception e)
			{
					e.printStackTrace();
			}
			
		}
	}
	/**
	 * Create the application.
	 */
	public VentanaAplicacionGUI(Autorizacion autorizacion) {
		this.autorizacion=autorizacion;
		listaCanciones=new ArrayList<CancionPanel>();
		ruta=this.crearRutaDefecto("datos.jkb");
		this.cargarCanciones(ruta);
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("The TJukeBox");
		frame.setBounds(100, 100, 911, 445);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnOpciones = new JMenu("Opciones");
		menuBar.add(mnOpciones);
		
		mntmCargarDatos = new JMenuItem("Cargar datos");
		mntmCargarDatos.addActionListener(this);
		mnOpciones.add(mntmCargarDatos);
		
		
		mntmGuardarDatos = new JMenuItem("Guardar datos");
		mntmGuardarDatos.addActionListener(this);
		mnOpciones.add(mntmGuardarDatos);
		
		mntmReinicarDatos = new JMenuItem("Reinicar datos");
		mntmReinicarDatos.addActionListener(this);
		mnOpciones.add(mntmReinicarDatos);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frame.getContentPane().add(panel, gbc_panel);
		
		BotomAniadirCanc = new JButton("A\u00F1adir Cancion");
		BotomAniadirCanc.setBounds(754, 362, 136, 22);
		BotomAniadirCanc.addActionListener(this);
		panel.add(BotomAniadirCanc);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 880, 340);
		panel.add(scrollPane);
		
		panelPrincipal = new JPanel();
		panelPrincipal.setBounds(10, 11, 880, 340);
		panel.add(panelPrincipal);
		panelPrincipal.setLayout(new BorderLayout(0, 0));
		
		
		BotomEliminarCanc = new JButton("Eliminar Canciones");
		BotomEliminarCanc.setBounds(599, 362, 145, 23);
		BotomEliminarCanc.addActionListener(this);
		panel.add(BotomEliminarCanc);
		panelCanciones = new JPanel();
		panelCanciones.setBounds(10, 11, 880, 306);
		panel.add(panelCanciones);
		panelCanciones.setLayout(new BorderLayout(0, 0));
		
		
		//TODO: Crear un Layout con todas las canciones y asignarlas al panelCanciones
		
		this.rellenarPanelCancion();
		scrollPane.getViewport().add(panelCanciones);
		
	}
	@Override
	public void actionPerformed(ActionEvent componente) {
		// TODO Auto-generated method stub
		if(componente.getSource()==BotomAniadirCanc)
		{
			DialogoAniadirCancion dialogo=new DialogoAniadirCancion(frame,true,listaCanciones,autorizacion);
			dialogo.setVisible(true);
			listaCanciones=dialogo.getListaCanciones();
			guardarCanciones(ruta);
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					panelCanciones.removeAll();
					rellenarPanelCancion();
				}
			});
			System.out.println("cancion aniadida y  repintada la interfaz");
			
			
		}
		else if(componente.getSource()==BotomEliminarCanc)
		{
			DialogoBorrarCancion dialogo=new DialogoBorrarCancion(frame,true,listaCanciones);
			dialogo.setVisible(true);
			listaCanciones=dialogo.getListaCanciones();
			System.out.println(listaCanciones.size());
			guardarCanciones(ruta);
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					panelCanciones.removeAll();
					rellenarPanelCancion();
				}
			});
			System.out.println("canciones eliminada  y  repintada la interfaz");	
		}
		else if(componente.getSource()==mntmCargarDatos)
		{
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo TJukeBox(.jkb)","jkb");
			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(mntmCargarDatos);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            ruta=file.getAbsolutePath();
	            this.cargarCanciones(ruta);
	            SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						panelCanciones.removeAll();
						rellenarPanelCancion();
					}
	            });
	            //This is where a real application would open the file.
	           System.out.println("Opening: " + file.getName() + ".");
	        } else {
	            System.out.println("Open command cancelled by user.");
	        }
			
		}
		else if(componente.getSource()==mntmGuardarDatos)
		{
			int returnVal = fc.showSaveDialog(mntmGuardarDatos);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo TJukeBox(.jkb)","jkb");
			fc.setFileFilter(filter);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file=null;
	            file=fc.getSelectedFile();
	            System.out.println("nombre del archivo:"+fc.getName());
	            if(file==null)
	            {//hay que crear el archivo
	            	File directorio=fc.getCurrentDirectory();
	            	DialogoCrearNuevoArchivo dialogo=new DialogoCrearNuevoArchivo(frame,true);
	    			dialogo.setVisible(true);
	    			file=new File(directorio.getAbsolutePath()+"/"+dialogo.getnombreArchivo()+".jkb");
	            	if(!file.exists())
	            	{
	            		try {
							if(file.createNewFile())
							{
								System.out.println("Archivo creado correctamente");
							}
							else
							{
								System.out.println("error a la hora de crear el archivo");
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            	}
	            }
	            else{
	            	ruta=file.getAbsolutePath();
	            }
	            System.out.println(file.getAbsolutePath());
	            ruta=file.getAbsolutePath();
	            this.guardarCanciones(ruta);
	            //This is where a real application would open the file.
	           System.out.println("Saving: " + file.getName() + ".");
	        } else {
	            System.out.println("Open command cancelled by user.");
	        }
			
			
		}
		else if(componente.getSource()==mntmReinicarDatos)
		{
			listaCanciones.clear();
			this.guardarCanciones(ruta);
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					panelCanciones.removeAll();
					rellenarPanelCancion();
				}
			});
			System.out.println("se han borrado todos los datos");
		}
	}
	
	private void rellenarPanelCancion()
	{
		GroupLayout layout=new GroupLayout(panelCanciones);
		panelCanciones.setLayout(layout);
		ParallelGroup cancionesP=layout.createParallelGroup(Alignment.CENTER);
		SequentialGroup cancionesS=layout.createSequentialGroup();
		for(int i=0;i<listaCanciones.size();i++)
		{
			cancionesP.addComponent(listaCanciones.get(i));
			cancionesS.addComponent(listaCanciones.get(i));
		}
		layout.setHorizontalGroup(cancionesP);
		layout.setVerticalGroup(cancionesS);
	}

	private String crearRutaDefecto(String archivo)
	{
		String ruta;
		File directorio=new File(System.getProperty("user.home")+"/TJukeBoxFiles");
		if(directorio.exists())
		{
			System.out.println("directorio ya existe");
			ruta=directorio.getAbsolutePath()+"/"+archivo;
			return ruta;
		}
		else
		{
			System.out.println("direcotio no existe, se va a crear uno");
			directorio.mkdir();
			ruta=directorio.getAbsolutePath()+"/"+archivo;
			return ruta;	
		}
	}
	
}
