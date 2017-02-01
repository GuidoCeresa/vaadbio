package it.algos.vaadbio.nazionalita;

import it.algos.vaadbio.attnaz.AttNazTablePortal;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.toolbar.TableToolbar;

/**
 * Created by gac on 31/01/17.
 * .
 */
public class NazionalitaTablePortal extends AttNazTablePortal{

    /**
     * Costruttore base
     */
    public NazionalitaTablePortal(ModulePop modulo) {
        super(modulo);
    }// end of constructor

    /**
     * Creates the toolbar
     * Barra standard con 5 bottoni (nuovo, modifica, elimina, cerca, selezione)
     * Sovrascrivibile, per aggiungere/modificare bottoni
     */
    @Override
    public TableToolbar createToolbar() {
        cmdPagina = "Nazionalità";
        cmdLista = "Nazionalità";
        linkPagina = "Liste nazionalità";
        linkLista = "Liste nazionalità";

        return super.createToolbar();
    }// end of method

}
