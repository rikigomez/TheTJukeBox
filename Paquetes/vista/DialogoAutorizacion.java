/*Clase que implementa el dialogo para introducir el PIN de autorizacion para poder usar la API de twitter
 * 
 * Autor: Riki Gomez (twitter @ricardo_gomez95 )
 */
package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import twitter.Autorizacion;

/**
 *
 * @author RICARDO
 */
public class DialogoAutorizacion extends JFrame implements ActionListener{
    private Autorizacion autorizacion;
    private JPanel panel;
    private JLabel introducirPin;
    private JTextField aclaracionURL;  
    private JTextField PIN;
    private JTextField URL;
    private JLabel mensajeEstado;
    private JButton botom;
    private Semaphore semaforo;
    private Semaphore semaforo2;
    private Semaphore finAutorizacion;
    public DialogoAutorizacion(Autorizacion autorizacion,Semaphore semaforo,Semaphore semaforo2,Semaphore finAutorizacion)
    {   
        //TODO 1: Implementar ActionPermormed
        super("Ventana autorizacion(Se necesita conexion a internet)");
        this.finAutorizacion=finAutorizacion;
        this.semaforo=semaforo;
        this.semaforo2=semaforo2;
        this.autorizacion=autorizacion;
        this.setLocation(150, 150);
        this.setSize(850, 150);
        this.semaforo.release();
        System.out.println("Semaforo2 se ha creado la ventana, se libera semaforo");
        try {
        System.out.println("Semaforo2 se ha bloqueado ventana hasta que se consiga la autorizacion");
            this.semaforo2.acquire();
        System.out.println("Semaforo2 abierto se ha conseguido la autorizacion, se procede a llenar los datos");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.autorizacion.openURL(this.autorizacion.getURL());
        this.rellenarPanel();
        this.setContentPane(panel);
        this.setResizable(false);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
   /**
    * Este metodo rellenara el panel con los contenidos
    */
 private void rellenarPanel()
 { 
	 panel=new JPanel();
	 introducirPin=new JLabel("Introduzca pin:");
     aclaracionURL=new JTextField("Si no se abre el navegador, dirigase a esta pagina:");
     aclaracionURL.setEditable(false);
     PIN=new JTextField();
     mensajeEstado=new JLabel("Introduzca el Pin que aparece en la pagina web");
     botom=new JButton("Aceptar");
     botom.addActionListener(this);
     URL=new JTextField(autorizacion.getURL());
     GroupLayout distribuidor=new GroupLayout(panel);
     panel.setLayout(distribuidor);
     distribuidor.setAutoCreateGaps(true);
     distribuidor.setAutoCreateContainerGaps(true);
     //creamos la distribucion
     distribuidor.setHorizontalGroup(distribuidor.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
             distribuidor.createSequentialGroup()
                        .addComponent(introducirPin)
                        .addComponent(PIN)
            )
            .addGroup(distribuidor.createSequentialGroup().addComponent(aclaracionURL).addComponent(URL))
            .addComponent(mensajeEstado)
             .addComponent(botom, GroupLayout.Alignment.TRAILING)
    );
     distribuidor.setVerticalGroup(distribuidor.createSequentialGroup().addGroup(
             distribuidor.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(introducirPin)
                        .addComponent(PIN)
            )
            .addGroup(distribuidor.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(aclaracionURL).addComponent(URL))
            .addComponent(mensajeEstado)
            .addComponent(botom)
    );
     
     
 }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(autorizacion.ObtenerCLave(PIN.getText()))
        {
            mensajeEstado.setText("Pin correcto, iniciando apliacion...");
            botom.setEnabled(false);
            PIN.setEnabled(false);
            finAutorizacion.release();
            this.dispose();
        }
        else
        {
            mensajeEstado.setText("Pin incorrecto, intentelo de nuevo");
            PIN.setText("");
        }
        
    }
    
    public Autorizacion getAutorizacion()
    {
    	return autorizacion;
    }
}
