/*Clase que implementa el dialogo para poder borrar canciones de la aplicacion
 * 
 * Autor: Riki Gomez (twitter @ricardo_gomez95 )
 */
package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

public class DialogoBorrarCancion extends JDialog implements ActionListener, ItemListener{

	private final JPanel contentPanel = new JPanel();
	private ArrayList<CancionPanel> listaCanciones;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel panelCanciones;
	private JScrollPane scrollPane;
	private JCheckBox[] seleccion;
	/**
	 * Launch the application.
	 */
	/* METODO PRUEBA 
	public static void main(String[] args) {
		try {
			ArrayList<CancionPanel> lista=new ArrayList<CancionPanel>();
			lista=VentanaAplicacionGUI.cargarCanciones("datos.txt", lista);
			DialogoBorrarCancion dialog = new DialogoBorrarCancion(lista);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	public DialogoBorrarCancion(JFrame frame, boolean modo,ArrayList<CancionPanel> lista){
		super(frame,modo);
		listaCanciones=lista;
		this.iniciar();
		
	}

	private void iniciar()
	{
		seleccion=new JCheckBox[listaCanciones.size()];
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		panelCanciones = new JPanel();
		panelCanciones.setBounds(10, 11, 414, 207);
		contentPanel.add(panelCanciones);
		panelCanciones.setLayout(null);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 414, 207);
		contentPanel.add(scrollPane);
		this.rellenarCanciones();
		scrollPane.setViewportView(panelCanciones);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("borrar Seleccionadas");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				okButton.setEnabled(false);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
	}
	
	/* CONSTRUCTOR DE PRUEBA
	public DialogoBorrarCancion(ArrayList<CancionPanel> listaCanciones) {
		seleccion=new JCheckBox[listaCanciones.size()];
		this.listaCanciones=listaCanciones;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		panelCanciones = new JPanel();
		panelCanciones.setBounds(10, 11, 414, 207);
		contentPanel.add(panelCanciones);
		panelCanciones.setLayout(null);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 414, 207);
		contentPanel.add(scrollPane);
		this.rellenarCanciones();
		scrollPane.setViewportView(panelCanciones);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("borrar Seleccionadas");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				okButton.setEnabled(false);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
	}
	*/
	private void rellenarCanciones()
	{
		GroupLayout distribuidor=new GroupLayout(panelCanciones);
		panelCanciones.setLayout(distribuidor);
		ParallelGroup cancionesP=distribuidor.createParallelGroup(Alignment.LEADING);
		SequentialGroup cancionesS=distribuidor.createSequentialGroup();
		System.out.println(listaCanciones.size());
		for(int i=0;i<listaCanciones.size();i++)
		{
			seleccion[i]= new JCheckBox(listaCanciones.get(i).getNombre());
			seleccion[i].setFont(new Font("Tahoma", Font.BOLD, 13));
			seleccion[i].addItemListener(this);
			cancionesP.addComponent(seleccion[i]);
			cancionesS.addComponent(seleccion[i]);
		}
		distribuidor.setHorizontalGroup(cancionesP);
		distribuidor.setVerticalGroup(cancionesS);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==okButton)
		{
			List<CancionPanel> elemBorrar=new ArrayList<CancionPanel>();
			for(int i=0;i<seleccion.length;i++)
			{
				if(seleccion[i].isSelected())
				{
					elemBorrar.add(listaCanciones.get(i));
				}
			}
			listaCanciones.removeAll(elemBorrar);
			
			System.out.println("numero total de canciones:"+listaCanciones.size());
			for(int i=0;i<listaCanciones.size();i++)
			{
				System.out.println(i+": "+listaCanciones.get(i).getNombre());
			}
			this.dispose();
		}
		else if(e.getSource()==cancelButton)
		{
			this.dispose();
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		boolean ningunoSeleccionado=true;
		for(int i=0;i<seleccion.length && ningunoSeleccionado;i++)
		{
			if(seleccion[i].isSelected())
				ningunoSeleccionado=false;
		}
		if(ningunoSeleccionado)
		{
			okButton.setEnabled(false);
		}
		else
		{
			okButton.setEnabled(true);
		}
		
	}
	
	public ArrayList<CancionPanel> getListaCanciones()
	{
		return listaCanciones;
	}
}
