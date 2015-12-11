package it.algos.vaadbio.bootstrap;

import it.algos.vaadbio.VaadbioApp;
import it.algos.vaadbio.daemons.DaemonCicloDownload;
import it.algos.webbase.web.AlgosApp;
import it.algos.webbase.web.bootstrap.ABootStrap;
import it.algos.webbase.web.toolbar.Toolbar;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * Bootstrap dell'applicazione
 * Executed on container startup
 * Setup non-UI logic here
 * <p>
 * Classe eseguita solo quando l'applicazione viene caricata/parte nel server (Tomcat od altri) <br>
 * Eseguita quindi ad ogni avvio/riavvio del server e NON ad ogni sessione <br>
 * È OBBLIGATORIO aggiungere questa classe nei listeners del file web.WEB-INF.web.xml
 */
public class VaadbioBootStrap extends ABootStrap {

    /**
     * Executed on container startup
     * Setup non-UI logic here
     * <p>
     * This method is called prior to the servlet context being
     * initialized (when the Web application is deployed).
     * You can initialize servlet context related data here.
     * <p>
     * Viene normalmente sovrascritta dalla sottoclasse per regolare alcuni flag dell'applicazione <br>
     * Deve (DEVE) richiamare anche il metodo della superclasse (questo)
     * prima (PRIMA) di eseguire le regolazioni specifiche <br>
     */
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        super.contextInitialized(contextEvent);
        ServletContext svltCtx = ABootStrap.getServletContext();

        // registra il servlet context non appena è disponibile
        VaadbioApp.setServletContext(svltCtx);

        // eventuali modifiche allle dimensioni dei bottoni
        Toolbar.ALTEZZA_BOTTONI = 40;
        Toolbar.LARGHEZZA_BOTTONI = 140;

        // eventuali modifiche ai flag generali di regolazione
        AlgosApp.USE_SECURITY = true;
        AlgosApp.USE_LOG = false; //usa una propria sottoclasse del modulo
        AlgosApp.USE_PREF = false; //lo uso, ma lo creo in VaadbioUI per averlo nell'ordine voluto
        AlgosApp.USE_VERS = false; //lo uso, ma lo creo in VaadbioUI per averlo nell'ordine voluto

        // avvia lo scheduler ciclo download new che esegue due volte al giorno
        DaemonCicloDownload.getInstance().start();

        // avvia lo scheduler ciclo elabora new che esegue ogni ora al minuto 30
//        DaemonBioCicloElabora.getInstance().start();
    }// end of method


    /**
     * This method is invoked when the Servlet Context
     * (the Web application) is undeployed or
     * WebLogic Server shuts down.
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        // arresta gli schedulers
        DaemonCicloDownload.getInstance().stop();

        super.contextDestroyed(servletContextEvent);
    }// end of method

}// end of bootstrap class
