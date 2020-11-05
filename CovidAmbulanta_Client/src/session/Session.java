/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;




import domen.Administrator;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Milica
 */
public class Session {
    private static Administrator currentUser;
    private int currentUseCase;
    private final Map<String, Object> useCaseParams;
    private static Session instance;
    private Socket socket;
    

    private Session() {
        useCaseParams = new HashMap<>();
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    
    public void setCurrentUser(Administrator user)
    {
        currentUser=user;
    }
    
    public Administrator getCurrentUser()
    {
        return currentUser;
    }
 
    public int getCurrentUseCase() {
        return currentUseCase;
    }
    
    public void setCurrentUseCase(int currentUseCase)
    {
        this.currentUseCase = currentUseCase;
    }
    public Map<String, Object> getUseCaseParams() 
    {
        return useCaseParams;
    }

    /**
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * @param socket the socket to set
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    
    
    
}
