package it.algos.vaadbio.crono;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.TablePortal;
import it.algos.webbase.web.toolbar.TableToolbar;

/**
 * Created by gac on 26 nov 2016.
 * Disabilita i bottoni Nuovo e Cancella
 * Aggiunge due bottoni per linkare le pagine di wikipedia
 */
public abstract class CronoTablePortal extends TablePortal {


    private final static Resource ICON_MOVE_UP = FontAwesome.FILE_TEXT;
    private final static String WIKI_BASE = "https://it.wikipedia.org/";
    private final static String WIKI_URL = WIKI_BASE + "wiki/";
    private final static String WIKI_ANTRO = WIKI_URL + "Progetto:Biografie/";

    protected String cmdPagina;
    protected String linkPagina;

    /**
     * Costruttore base
     */
    public CronoTablePortal(ModulePop modulo) {
        super(modulo);
    }// end of constructor


    /**
     * Creates the toolbar
     * Barra standard con 5 bottoni (nuovo, modifica, elimina, cerca, selezione)
     * Sovrascrivibile, per aggiungere/modificare bottoni
     */
    @Override
    public TableToolbar createToolbar() {
        toolbar = super.createToolbar();

        toolbar.setCreate(false);
        toolbar.setCreateButtonVisible(false);
        toolbar.setDeleteButtonVisible(false);
        addMenuLinkWikipedia();

        return toolbar;
    }// end of method


    /*
     * Link alle pagine di servizio.
     */
    private void addMenuLinkWikipedia() {
        toolbar.addButton(cmdPagina, ICON_MOVE_UP, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getPage().open(WIKI_ANTRO + linkPagina, "_blank");
            }// end of inner method
        });// end of anonymous inner class
    }// end of method


}// end of class
