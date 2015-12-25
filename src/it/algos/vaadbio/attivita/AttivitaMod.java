package it.algos.vaadbio.attivita;


import com.vaadin.event.Action;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import it.algos.vaadbio.ciclo.CicloDownload;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.dialog.ConfirmDialog;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ATable;

/**
 * Gestione (minimale) del modulo
 * Passa alla superclasse il nome e la classe specifica
 */
@SuppressWarnings("serial")
public class AttivitaMod extends ModulePop {

    public static String MENU_ADDRESS = "Attivita";

    private Action actionUpload = new Action("Upload lista", FontAwesome.ARROW_UP);

    /**
     * Costruttore standard senza parametri
     */
    public AttivitaMod() {
        super(Attivita.class, MENU_ADDRESS);
        ATable tavola = getTable();
        addActionHandler(tavola);
    }// end of constructor


    /**
     * Registers a new action handler for this container
     *
     * @see com.vaadin.event.Action.Container#addActionHandler(Action.Handler)
     */
    private void addActionHandler(ATable tavola) {
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
    }// end of method

    /**
     * Comando bottone/item New Ciclo
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandDownload(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Download", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                boolean usaDialoghi = Pref.getBool(CostBio.USA_DIALOGHI_CONFERMA, true);
                boolean usaDebug = Pref.getBool(CostBio.USA_DEBUG, false);
                boolean usaLimite = Pref.getBool(CostBio.USA_LIMITE_DOWNLOAD, false);
                boolean usaLog = Pref.getBool(CostBio.USA_LOG_DOWNLOAD, false);
                boolean usaUpdate = Pref.getBool(CostBio.USA_UPLOAD_DOWNLOADATA, false);
                boolean usaCancella = Pref.getBool(CostBio.USA_CANCELLA_VOCE_MANCANTE, false);
                if (usaDialoghi) {
                    String newMsg;
                    newMsg = "Esegue un ciclo (<b><span style=\"color:green\">update</span></b>) di aggiunta attività</br>";
                    if (usaLog) {
                        newMsg += "<br>Le preferenze prevedono di registrare il risultato nei <b><span style=\"color:red\">log</span></b>";
                    } else {
                        newMsg += "<br>Le preferenze <b><span style=\"color:red\">non</span></b> prevedono di registrare il risultato nei log";
                    }// end of if/else cycle
                    ConfirmDialog dialog = new ConfirmDialog(CostBio.MSG, newMsg,
                            new ConfirmDialog.Listener() {
                                @Override
                                public void onClose(ConfirmDialog dialog, boolean confirmed) {
                                    if (confirmed) {
                                        esegueDownload();
                                    }// end of if cycle
                                }// end of inner method
                            });// end of anonymous inner class
                    UI ui = getUI();
                    if (ui != null) {
                        dialog.show(ui);
                    } else {
                        Notification.show("Avviso", "Devi prima entrare nel modulo Bio per eseguire questo comando", Notification.Type.WARNING_MESSAGE);
                    }// end of if/else cycle
                } else {
                    new CicloDownload();
                }// fine del blocco if-else
            }// end of method
        });// end of anonymous class
    }// end of method


    /**
     * Esegue il download
     */
    public void esegueDownload() {
        AttivitaService.download();
    }// end of method

    /**
     * Esegue l'upload per la lista dei nati
     */
    public void esegueUpload() {
        Attivita attivita = getAttivita();

        if (attivita != null) {
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle
    }// end of method

    /**
     * Recupera la voce selezionata
     */
    public Attivita getAttivita() {
        Attivita attivita = null;
        long idSelected = 0;
        ATable tavola = getTable();

        if (tavola != null) {
            idSelected = tavola.getSelectedKey();
        }// end of if cycle

        if (idSelected > 0) {
            attivita = Attivita.find(idSelected);
        }// end of if cycle

        return attivita;
    }// end of method

}// end of class

