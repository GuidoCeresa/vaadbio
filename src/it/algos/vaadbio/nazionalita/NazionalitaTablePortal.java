package it.algos.vaadbio.nazionalita;

import it.algos.vaadbio.liste.ListeTablePortal;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.toolbar.TableToolbar;

/**
 * Created by gac on 31/01/17.
 * Portale specifico per le nazionalità
 */
public class NazionalitaTablePortal extends ListeTablePortal {

    /**
     * Costruttore base
     */
    public NazionalitaTablePortal(ModulePop modulo) {
        super(modulo);
    }// end of constructor

    @Override
    protected void init() {
        wikiProgetto = "Biografie";
        cmdPagina = "Nazionalità";
        linkPagina = "Nazionalità";

        super.init();
    }// end of method

}// end of class
