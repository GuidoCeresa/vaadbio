package it.algos.vaadbio;

import it.algos.vaad.wiki.WikiLogin;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.AlgosApp;

/**
 * Contenitore di costanti della applicazione
 * Regola alcuni flag di comportamento del framwork di base
 */
public class VaadbioApp extends AlgosApp {

    /**
     * Static initialisation block
     *
     * Sovrascrive alcune variabili statiche della classe generale,
     * per modificarne il comportamento solo in questa applicazione
     */
    static {
        DISPLAY_TOOLTIPS = true;
    }// end of static method

}// end of  static class
