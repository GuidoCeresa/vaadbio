package it.algos.vaadbio.cognome;


import com.vaadin.event.Action;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import it.algos.vaadbio.esegue.Esegue;
import it.algos.vaadbio.liste.ListaCognome;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ATable;

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
    }// end of constructor

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
        addCommandAggiunge(menuItem);
    }// end of method

    /**
     * Comando bottone/item aggiounge nuovi cognomi individuati nei records di Bio
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandAggiunge(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Aggiorna", FontAwesome.BEER, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                esegueAggiorna();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Esegue l'upload di un singolo cognome
     */
    protected void esegueUpload() {
        Cognome cognome = getCognome();

        if (cognome != null) {
            new ListaCognome(cognome);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle

    }// end of method

    /**
     * Esegue l'aggiunta dei nuovi records
     */
    protected void esegueAggiorna() {
        Esegue.aggiornaCognomi();
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

