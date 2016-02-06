package it.algos.vaadbio.nome;


import com.vaadin.data.Item;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import it.algos.vaadbio.bio.BioForm;
import it.algos.vaadbio.bio.BioSearch;
import it.algos.vaadbio.bio.BioTable;
import it.algos.vaadbio.esegue.Esegue;
import it.algos.webbase.web.form.ModuleForm;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.search.SearchManager;
import it.algos.webbase.web.table.ATable;

import javax.persistence.metamodel.Attribute;

/**
 * Gestione (minimale) del modulo
 */
@SuppressWarnings("serial")
public class NomeMod extends ModulePop {


    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Nomi";


    /**
     * Costruttore senza parametri
     * <p>
     * Invoca la superclasse passando i parametri:
     * (obbligatorio) la Entity specifica
     * (facoltativo) etichetta del menu (se manca usa il nome della Entity)
     * (facoltativo) icona del menu (se manca usa un'icona standard)
     */
    public NomeMod() {
        super(Nome.class, MENU_ADDRESS, FontAwesome.LIST_UL);
    }// end of constructor

    /**
     * Returns the table used to shows the list. <br>
     * The concrete subclass must override for a specific Table.
     *
     * @return the Table
     */
    public ATable createTable() {
        return new NomeTable(this);
    }// end of method


    /**
     * Crea i sottomenu specifici del modulo
     * <p>
     * Invocato dal metodo AlgosUI.addModulo()
     * Sovrascritto dalla sottoclasse
     *
     * @param menuItem principale del modulo
     */
    @Override
    public void addSottoMenu(MenuBar.MenuItem menuItem) {
        addCommandDownloadDoppi(menuItem);
        addCommandUpload(menuItem);
    }// end of method

    /**
     * Comando bottone/item download
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandDownloadDoppi(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Download", FontAwesome.BEER, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                esegueDownloadDoppi();
            }// end of method
        });// end of anonymous class
    }// end of method


    /**
     * Comando bottone/item upload
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandUpload(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Upload", FontAwesome.BEER, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                esegueUpload();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Esegue l'aggiornamento della lista dei nomi doppi
     */
    protected void esegueDownloadDoppi() {
        Esegue.esegueDownloadDoppi();
    }// end of method

    /**
     * Esegue l'upload delle pagine
     */
    protected void esegueUpload() {
        Esegue.uploadNomi();
    }// end of method


}// end of class

