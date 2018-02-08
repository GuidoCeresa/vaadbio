package it.algos.vaadbio.nome;

import com.vaadin.ui.MenuBar;
import it.algos.vaadbio.liste.ListeTablePortal;
import it.algos.webbase.web.lib.LibText;
import it.algos.webbase.web.module.ModulePop;

/**
 * Created by gac on 26 nov 2016.
 * Portale specifico per i nomi
 */
public class NomeTablePortal extends ListeTablePortal {


    protected String cmdDoppi;
    protected String linkDoppi;

    /**
     * Costruttore base
     */
    public NomeTablePortal(ModulePop modulo) {
        super(modulo);
    }// end of constructor


    @Override
    protected void init() {
        wikiProgetto = "Antroponimi";
        cmdPagina = "Nomi";
        cmdLista = "Liste";
        linkPagina = "Nomi";
        linkLista = "Liste nomi";

        cmdDoppi="Nomi doppi";
        linkDoppi="Nomi doppi";
        super.init();
    }// end of method

    /*
 * Link alle pagine di servizio.
 */
    protected void addMenuLinkWikipedia() {
        String wikiLink = WIKI_BASE + wikiProgetto + TAG;
        super.addMenuLinkWikipedia();

        if (LibText.isValida(cmdDoppi)) {
            toolbar.addButton(cmdDoppi, ICON_MOVE_UP, new MenuBar.Command() {
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    getUI().getPage().open(wikiLink + linkDoppi, "_blank");
                }// end of inner method
            });// end of anonymous inner class
        }// end of if cycle

    }// end of method

}// end of class
