/*
 * Clase que implementa el panel que contiene la cancion, en este se puede escribir el tweet en el textArea
 * y se puede enviar el tweet.
 * Tambien se ha implementado un contador de palabras que permita controlar el contenido para que no haya 
 * problemas con twitter
 * Autor: Riki Gomez (twitter @ricardo_gomez95 )
 */
package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.Dimension;

import javax.swing.JScrollPane;

import modelos.MCancion;

import java.awt.Font;

import javax.swing.border.MatteBorder;

import twitter.Autorizacion;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CancionPanel extends JPanel implements MCancion,KeyListener,ActionListener  {

	private String nombre;
	private String urlCancion;
	private int contadorPal;
	private int limitePal;
	private JButton btnTwittear;
	private JLabel lblNPal;
	private JLabel lblNombreCancion;
	private JTextArea textArea;
	private Twitter twitter;
	
	public String getNombre()
	{
		return nombre;
	}
	
	public String getUrl()
	{
		return urlCancion;
	}
		
	public CancionPanel(String nombre,String urlCancion,Autorizacion autorizacion) {
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		limitePal=140-(urlCancion.length()+18);
		twitter=autorizacion.getOAuthTwitter();
		contadorPal=limitePal;
		this.urlCancion=urlCancion;
		this.nombre=nombre;
		super.setSize(784,93);
		super.setMinimumSize(new Dimension(860,93));
		super.setMaximumSize(new Dimension(860,93));
		lblNombreCancion = new JLabel(this.nombre);
		lblNombreCancion.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblNPal = new JLabel(Integer.toString(contadorPal));
		lblNPal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		btnTwittear = new JButton("Twittear");
		btnTwittear.addActionListener(this);
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblNombreCancion, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNPal, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
							.addComponent(btnTwittear)
							.addGap(15))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNPal)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(43)
							.addComponent(lblNombreCancion)))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(44, Short.MAX_VALUE)
					.addComponent(btnTwittear)
					.addGap(25))
		);
		groupLayout.setHonorsVisibility(false);
		
		textArea = new JTextArea();
		textArea.addKeyListener(this);
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		setLayout(groupLayout);
		
		

	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("textArea");
	}
	//@Override
	public void keyTyped(final KeyEvent letra) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				if(textArea.getText().equals(""))
				{
					contadorPal=limitePal;
				}
				if(letra.getKeyChar()==KeyEvent.VK_BACK_SPACE && contadorPal<limitePal)
				{
					//contadorPal++;
					contadorPal=limitePal-textArea.getText().length();
				}
				else
				{
					if(letra.getKeyChar()!=KeyEvent.VK_BACK_SPACE)
						//contadorPal--;
						contadorPal=limitePal-textArea.getText().length();
				}
				//contadorPal=limitePal-textArea.getText().length();
				lblNPal.setText(Integer.toString(contadorPal));
				if(contadorPal<0)
				{
					lblNPal.setForeground(Color.RED);
					btnTwittear.setEnabled(false);
				}
				else
				{
					lblNPal.setForeground(Color.BLACK);
					btnTwittear.setEnabled(true);
				}
			}
		
		});
		
	}
	@Override
	public void actionPerformed(ActionEvent componente) {
		// TODO Auto-generated method stub	
	if(componente.getSource()==btnTwittear)
		{
			boolean correcto=true;
			String mensajeError="";
			String contenido=textArea.getText()+" "+this.getUrl()+" via @TheTJukeBox";//18 palabras
			System.out.println("contenido a enviar:"+contenido);
			System.out.println("espacio:"+contenido.length());
			try {
				twitter.updateStatus(contenido);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mensajeError=e.getErrorMessage();
				correcto=false;
			}
			if(correcto)
			{
				textArea.setText("Enviado correctamente");
			}
			else
			{
				textArea.setText("no se ha podido enviar el tweet, error:"+mensajeError);
			}
			
			btnTwittear.setEnabled(false);
		}
		
	}
	
}
