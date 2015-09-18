/*Clase que se usa de lanzadera de la aplicacion es decir, es una clase intermedia entre la autorizacion y 
 * la aplicacion principal. para ello primero inicia los ajustes de la autorizacion, luego inicia el dialogo
 * de autorizacion para pedir permiso a twitter y por ultimo lanza la aplicacion pudiendo ya esta utilizar
 * el api de twitter.
 * Autor: Riki Gomez (twitter @ricardo_gomez95 )
 */

package tjukebox;

import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import twitter.Autorizacion;
import twitter4j.TwitterException;
import vista.DialogoAutorizacion;
import vista.VentanaAplicacionGUI;

/**
 *
 * @author RICARDO
 */
public class Lanzadera{
    
     private Autorizacion autorizacion;
    public Lanzadera()
    {
        System.out.println("Iniciando lanzadera");
        try{
		        autorizacion=new Autorizacion();
		        final Semaphore semaforo=new Semaphore(0);
		        final Semaphore semaforo2=new Semaphore(0);   
		        Semaphore finAutorizacion=new Semaphore(0);
        
        new Thread(){
            public void run()
            {
                try{
                System.out.println("\n **************** Rellenando Autorizacion *****************************");
                System.out.println("Semaforo1 bloqueado esperando a que se cree la ventana");
                semaforo.acquire();
                System.out.println("Semaforo1 Iniciando la autorizacion");
                try{
                	autorizacion.inicializar();
                }catch(TwitterException e)
                	{
                		e.printStackTrace();
                		System.out.println("no se puede iniciar");
                	}
                
                semaforo2.release();
                System.out.println("Semaforo 1 Autorizacion conseguida, rellenar la interfaz");
                System.out.println("\n **************** FIN Rellenando Autorizacion *****************************");
                }catch(InterruptedException ex)
                {
                    ex.printStackTrace();
                }
                
                
            }
        }.start();
        System.out.println("Se lanza el dialogo de autorizacion");
        new DialogoAutorizacion(autorizacion,semaforo,semaforo2,finAutorizacion);
        finAutorizacion.acquire();
        System.out.println("-----------------Se sigue la ejecucion-------------------");
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        System.out.println("\n **************** Se lanza la aplicacion principal *****************************");
        new VentanaAplicacionGUI(autorizacion).launch();
        
    }
    
        
}
