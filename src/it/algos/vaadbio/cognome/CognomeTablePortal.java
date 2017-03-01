package it.algos.vaadbio.cognome;

import it.algos.vaadbio.liste.ListeTablePortal;
import it.algos.webbase.web.module.ModulePop;

/**
 * Created by gac on 25 nov 2016.
 * Portale specifico per i cognomi
 */
public class CognomeTablePortal extends ListeTablePortal {


    /**
     * Costruttore base
     */
    public CognomeTablePortal(ModulePop modulo) {
        super(modulo);
    }// end of constructor


    @Override
    protected void init() {
        wikiProgetto = "Antroponimi";
        cmdPagina = "Cognomi";
        cmdLista = "Liste";
        linkPagina = "Cognomi";
        linkLista = "Liste cognomi";

        super.init();
    }// end of method

}// end of class
