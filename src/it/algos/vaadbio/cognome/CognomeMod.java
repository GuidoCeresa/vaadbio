package it.algos.vaadbio.cognome;


import com.vaadin.event.Action;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import it.algos.vaadbio.esegue.Esegue;
import it.algos.vaadbio.liste.ListaAntroCognome;
import it.algos.vaadbio.nome.Nome_;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ATable;
import it.algos.webbase.web.table.TablePortal;

import javax.persistence.metamodel.Attribute;

/**
 * Gestione (minimale) del modulo specifico
 */
@SuppressWarnings("serial")
public class CognomeMod extends ModulePop {


    private static final long serialVersionUID = 1L;

    // indirizzo interno del modulo - etichetta del menu
    public static String MENU_ADDRESS = "Cognomi";

    // icona (eventuale) del modulo
    public static Resource ICON = FontAwesome.LIST_UL;

    private Action actionUpload = new Action("Upload", FontAwesome.ARROW_UP);

    /**
     * Costruttore senza parametri
     * <p>
     * Invoca la superclasse passando i parametri:
     * (obbligatorio) la Entity specifica
     * (facoltativo) etichetta del menu (se manca usa il nome della Entity)
     * (facoltativo) icona del menu (se manca usa un'icona standard)
     */
    public CognomeMod() {
        super(Cognome.class, MENU_ADDRESS, ICON);
        this.getTable().setRowHeaderMode(Table.RowHeaderMode.INDEX);
    }// end of constructor


    /**
     * Returns the table used to shows the list. <br>
     * The concrete subclass must override for a specific Table.
     *
     * @return the Table
     */
    public ATable createTable() {
        return new CognomeTable(this);
    }// end of method

    /**
     * Create the Table Portal
     *
     * @return the TablePortal
     */
    @Override
    public TablePortal createTablePortal() {
        return new CognomeTablePortal(this);
    }// end of method

    /**
     * Crea i campi visibili nella scheda (form)
     * <p>
     * Come default spazzola tutti i campi della Entity <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     * Serve anche per l'ordine con cui vengono presentati i campi nella scheda <br>
     */
    protected Attribute<?, ?>[] creaFieldsForm() {
        return new Attribute[]{Cognome_.cognome, Cognome_.voci};
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
        addCommandCrea(menuItem);
        addCommandCiclo(menuItem);
        addCommandUploadAll(menuItem);
        addCommandUpload(menuItem);
        addCommandStatistichel(menuItem);
    }// end of method

    /**
     * Comando bottone/item cancella e ricrea tutti i cognomi individuati nei records di Bio
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandCrea(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Crea", FontAwesome.BEER, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                CognomeService.crea();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item ciclo completo: crea + upload + statistiche
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandCiclo(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Ciclo", FontAwesome.BEER, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                Esegue.cicloCognomi();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item upload di tutte le liste dei cognomi sul server wiki
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandUploadAll(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Upload all", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                Esegue.uploadCognomi();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item upload di una singola con la lista di un cognome sul server wiki
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
     * Comando bottone/item crea sul server wikii le due pagine di statistiche sui cognomi
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandStatistichel(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Statistiche", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                Esegue.statisticheCognomi();
            }// end of method
        });// end of anonymous class
    }// end of method


    /**
     * Esegue l'upload di un singolo cognome
     */
    private void esegueUpload() {
        Cognome cognome = getCognome();

        if (cognome != null) {
            new ListaAntroCognome(cognome);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle

    }// end of method


    /**
     * Recupera la voce selezionata
     */
    private Cognome getCognome() {
        Cognome cognome = null;
        long idSelected = 0;
        ATable tavola = getTable();

        if (tavola != null) {
            idSelected = tavola.getSelectedKey();
        }// end of if cycle

        if (idSelected > 0) {
            cognome = Cognome.find(idSelected);
        }// end of if cycle

        return cognome;
    }// end of method

}// end of class

