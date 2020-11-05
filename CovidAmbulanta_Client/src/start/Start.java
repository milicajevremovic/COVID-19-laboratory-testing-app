/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import gui.LoginForma;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import konstante.Konstante;
import kontroler.Kontroler;
import session.Session;

/**
 *
 * @author Milica
 */
public class Start {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        try {
            Socket socket = new Socket("localhost", Konstante.PORT);
            Session.getInstance().setSocket(socket);
            Kontroler.getInstance().initStreams();
            JFrame loginForm = new LoginForma();
            loginForm.setVisible(true);
        } catch (IOException ex) {
            //Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Server nije pokrenut, pokušajte kasnije.");
        }
    }
}
