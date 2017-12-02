package edu.metrostate.ics425.jtp307.reversi;

import edu.metrostate.ics425.jtp307.reversi.model.Reversi;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * A basic session context listener, which ensures that a reversi instance is
 * always in the session.
 *
 * @author Joseph T. Parsons
 */

public class HttpSessionListenerProvider implements HttpSessionListener {
    
    @Override
    public void sessionCreated(HttpSessionEvent se)  {
      
        HttpSession session = se.getSession();
        
        Reversi reversiInstance;
        if (session.getAttribute("reversiInstance") == null
                || !(session.getAttribute("reversiInstance") instanceof Reversi)) {
            session.setAttribute("reversiInstance", new Reversi());
        }
        
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
    
}
