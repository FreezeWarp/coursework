package edu.metrostate.ics425.jtp307.prodmaint;

import edu.metrostate.ics425.prodmaint.data.ProductCatalog;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * A basic servlet context listener, which ensures that the catalogue singleton is
 * initialized once the Java EE server starts.
 * 
 * For the record, I have no real understanding of how this works. But I do believe it
 * ensures that the singleton will be available to all Servlets for the duration
 * of this package's existence.
 *
 * @author Joseph T. Parsons
 */

public class ServletContextListenerProvider implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent se)  {
       
        /* This is the servlet information for the session. */
        ServletContext context = se.getServletContext();
        
        /*
         * On my system (and no doubt other systems), using an non-existing catalogPath
         * with context.getRealPath() fails. This is a compromise that will work
         * on most (all?) systems.
         *
         * Note as well that using NetBeans "clean" function (or similar)
         * will of-course delete this path.
         */
        if (!ProductCatalog.init(context.getRealPath("/") + context.getInitParameter("catalogPath"))) {
            throw new RuntimeException("Unable to initialise catalog.");
        }
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent se) {
    }
    
}