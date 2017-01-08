package it.algos.vaadbio.attivita;

import it.algos.vaadbio.antro.AntroTablePortal;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.toolbar.TableToolbar;

/**
 * Created by gac on 07/01/17.
 * Portale specifico per le attività
 */
public class AttivitaTablePortal extends AntroTablePortal {


    /**
     * Costruttore base
     */
    public AttivitaTablePortal(ModulePop modulo) {
        super(modulo);
    }// end of constructor

    /**
     * Creates the toolbar
     * Barra standard con 5 bottoni (nuovo, modifica, elimina, cerca, selezione)
     * Sovrascrivibile, per aggiungere/modificare bottoni
     */
    @Override
    public TableToolbar createToolbar() {

        cmdPagina = "Attività";
        cmdLista = "Attività";
        linkPagina = "Liste attività";
        linkLista = "Liste attività";

        return super.createToolbar();
    }// end of method

}// end of class
