package it.algos.vaadbio.attivita;

import it.algos.vaadbio.liste.ListeTablePortal;
import it.algos.webbase.web.module.ModulePop;

/**
 * Created by gac on 07/01/17.
 * Portale specifico per le attività
 */
public class AttivitaTablePortal extends ListeTablePortal {

    /**
     * Costruttore base
     */
    public AttivitaTablePortal(ModulePop modulo) {
        super(modulo);
    }// end of constructor

    @Override
    protected void init() {
        wikiProgetto = "Biografie";
        cmdPagina = "Attività";
        linkPagina = "Attività";

        super.init();
    }// end of method

}// end of class
