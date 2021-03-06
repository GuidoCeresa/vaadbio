package it.algos.vaadbio.anno;

import it.algos.vaadbio.crono.CronoTablePortal;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.toolbar.TableToolbar;

/**
 * Created by gac on 27 nov 2016.
 * .
 */
public class AnnoTablePortal extends CronoTablePortal {


    /**
     * Costruttore base
     */
    public AnnoTablePortal(ModulePop modulo) {
        super(modulo);
    }// end of constructor


    /**
     * Creates the toolbar
     * Barra standard con 5 bottoni (nuovo, modifica, elimina, cerca, selezione)
     * Sovrascrivibile, per aggiungere/modificare bottoni
     */
    @Override
    public TableToolbar createToolbar() {

        cmdPagina = "Statistiche";
        linkPagina = "Anni";

        return super.createToolbar();
    }// end of method

}// end of class
