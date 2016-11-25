package it.algos.vaadbio.cognome;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.TablePortal;
import it.algos.webbase.web.toolbar.TableToolbar;

/**
 * Created by gac on 25 nov 2016.
 * .
 */
public class CognomeTablePortal extends TablePortal {

    public static final String CMD_COGNOMI = "Cognomi";
    public static final String CMD_LISTE = "Liste";
    public static final Resource ICON_MOVE_UP = FontAwesome.FILE_TEXT;


    public static final String WIKI_BASE = "https://it.wikipedia.org/";
    public static final String WIKI_URL = WIKI_BASE + "wiki/";
    public static final String WIKI_ANTRO = WIKI_URL + "Progetto:Antroponimi/";

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
        toolbar.addButton(CMD_LISTE, ICON_MOVE_UP, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getPage().open(WIKI_ANTRO + "Liste cognomi", "_blank");
            }// end of inner method
        });// end of anonymous inner class

        toolbar.addButton(CMD_COGNOMI, ICON_MOVE_UP, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getPage().open(WIKI_ANTRO + "Cognomi", "_blank");
            }// end of inner method
        });// end of anonymous inner class
    }// end of method


}// end of class
