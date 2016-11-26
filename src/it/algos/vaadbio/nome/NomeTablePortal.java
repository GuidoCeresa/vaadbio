package it.algos.vaadbio.nome;

import it.algos.vaadbio.antro.AntroTablePortal;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.toolbar.TableToolbar;

/**
 * Created by gac on 26 nov 2016.
 * Portale specifico per i nomi
 */
public class NomeTablePortal extends AntroTablePortal {

    /**
     * Costruttore base
     */
    public NomeTablePortal(ModulePop modulo) {
        super(modulo);
    }// end of constructor

    /**
     * Creates the toolbar
     * Barra standard con 5 bottoni (nuovo, modifica, elimina, cerca, selezione)
     * Sovrascrivibile, per aggiungere/modificare bottoni
     */
    @Override
    public TableToolbar createToolbar() {

        cmdPagina = "Nomi";
        cmdLista = "Liste";
        linkPagina = "Liste nomi";
        linkLista = "Nomi";

        return super.createToolbar();
    }// end of method


}// end of class
