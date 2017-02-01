package it.algos.vaadbio.nazionalita;


import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import it.algos.vaadbio.attivita.AttivitaService;
import it.algos.vaadbio.attivita.AttivitaTablePortal;
import it.algos.vaadbio.ciclo.CicloDownload;
import it.algos.vaadbio.esegue.Esegue;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.liste.ListaNazionalita;
import it.algos.vaadbio.statistiche.StatNazionalita;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.dialog.ConfirmDialog;
import it.algos.webbase.web.module.ModulePop;
import com.vaadin.event.Action;
import it.algos.webbase.web.table.ATable;
import it.algos.webbase.web.table.ModuleTable;
import it.algos.webbase.web.table.TablePortal;

/**
 * Gestione (minimale) del modulo
 */
@SuppressWarnings("serial")
public class NazionalitaMod extends ModulePop {

    // indirizzo interno del modulo (serve nei menu)
    public final static String MENU_ADDRESS = "Naz";

    private Action actionUpload = new Action("Upload lista", FontAwesome.ARROW_UP);

    /**
     * Costruttore senza parametri
     * <p/>
     * Invoca la superclasse passando i parametri:
     * (obbligatorio) la Entity specifica
     * (facoltativo) etichetta del menu (se manca usa il nome della Entity)
     * (facoltativo) icona del menu (se manca usa un'icona standard)
     */
    public NazionalitaMod() {
        super(Nazionalita.class, MENU_ADDRESS,FontAwesome.LIST_UL);
        this.getTable().setRowHeaderMode(Table.RowHeaderMode.INDEX);
    }// end of constructor


    /**
     * Returns the table used to shows the list. <br>
     * The concrete subclass must override for a specific Table.
     *
     * @return the Table
     */
    public ATable createTable() {
        return (new NazionalitaTable(this));
    }// end of method

    /**
     * Create the Table Portal
     *
     * @return the TablePortal
     */
    @Override
    public TablePortal createTablePortal() {
        return new NazionalitaTablePortal(this);
    }// end of method

    /**
     * Registers a new action handler for this container
     *
     * @see com.vaadin.event.Action.Container#addActionHandler(Action.Handler)
     */
    @Override
    protected void addActionHandler(ATable tavola) {
        tavola.addActionHandler(new Action.Handler() {
            public Action[] getActions(Object target, Object sender) {
                Action[] actions = null;
                actions = new Action[1];
                actions[0] = actionUpload;
                return actions;
            }// end of inner method

            public void handleAction(Action action, Object sender, Object target) {
                if (action.equals(actionUpload)) {
                    esegueUpload();
                }// end of if cycle
            }// end of inner method
        });// end of anonymous inner class
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
        addCommandDownload(menuItem);
        addCommandUploadAll(menuItem);
        addCommandUpload(menuItem);
        addCommandStatistichel(menuItem);
    }// end of method

    /**
     * Comando bottone/item New Ciclo
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandDownload(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Download", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                NazionalitaService.download();
            }// end of method
        });// end of anonymous class
    }// end of method


    /**
     * Comando bottone/item Upload
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandUploadAll(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Upload all", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                Esegue.uploadNazionalita();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item Upload
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandUpload(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Upload", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                esegueUpload();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item crea sul server wikii la pagina di statistiche
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandStatistichel(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Statistiche", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                Esegue.statisticheNazionalita();
            }// end of method
        });// end of anonymous class
    }// end of method


    /**
     * Esegue l'upload per la lista della nazionalità
     */
    public void esegueUpload() {
        Nazionalita nazionalita = getNazionalita();

        if (nazionalita != null) {
            new ListaNazionalita(nazionalita);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle
    }// end of method


    /**
     * Recupera la voce selezionata
     */
    public Nazionalita getNazionalita() {
        Nazionalita nazionalita = null;
        long idSelected = 0;
        ATable tavola = getTable();

        if (tavola != null) {
            idSelected = tavola.getSelectedKey();
        }// end of if cycle

        if (idSelected > 0) {
            nazionalita = Nazionalita.find(idSelected);
        }// end of if cycle

        return nazionalita;
    }// end of method

}// end of class


