package it.algos.vaadbio.cognome;

import it.algos.vaadbio.antro.AntroTablePortal;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.toolbar.TableToolbar;

/**
 * Created by gac on 25 nov 2016.
 * Portale specifico per i cognomi
 */
public class CognomeTablePortal extends AntroTablePortal {


    /**
     * Costruttore base
     */
    public CognomeTablePortal(ModulePop modulo) {
        super(modulo);
    }// end of constructor

    /**
     * Creates the toolbar
     * Barra standard con 5 bottoni (nuovo, modifica, elimina, cerca, selezione)
     * Sovrascrivibile, per aggiungere/modificare bottoni
     */
    @Override
    public TableToolbar createToolbar() {

        cmdPagina = "Cognomi";
        cmdLista = "Liste";
        linkPagina = "Liste cognomi";
        linkLista = "Cognomi";

        return super.createToolbar();
    }// end of method

}// end of class