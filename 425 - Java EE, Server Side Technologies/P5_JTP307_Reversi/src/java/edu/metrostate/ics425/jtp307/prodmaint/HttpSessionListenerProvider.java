package edu.metrostate.ics425.jtp307.prodmaint;

import edu.metrostate.ics425.jtp307.prodmaint.model.Reversi;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * A basic servlet context listener, which ensures that the catalogue singleton is
 * initialized once the Java EE server starts.
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
