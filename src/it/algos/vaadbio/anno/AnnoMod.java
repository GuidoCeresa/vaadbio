package it.algos.vaadbio.anno;


import com.vaadin.event.Action;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import it.algos.vaadbio.ciclo.CicloDownload;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.liste.*;
import it.algos.vaadbio.upload.UploadAnni;
import it.algos.vaadbio.upload.UploadGiorni;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.dialog.ConfirmDialog;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.navigator.NavPlaceholder;
import it.algos.webbase.web.table.ATable;

import javax.persistence.metamodel.Attribute;

/**
 * Gestione (minimale) del modulo
 * Passa alla superclasse il nome e la classe specifica
 */
@SuppressWarnings("serial")
public class AnnoMod extends ModulePop {

    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Anno";

    private Action actionUploadNati = new Action("Upload nati", FontAwesome.ARROW_UP);
    private Action actionUploadMorti = new Action("Upload morti", FontAwesome.ARROW_UP);
    private Action actionUploadAll = new Action("Upload both", FontAwesome.ARROW_UP);

    /**
     * Costruttore standard senza parametri
     */
    public AnnoMod() {
        super(Anno.class, MENU_ADDRESS);
        ATable tavola = getTable();
        addActionHandler(tavola);
    }// end of constructor

    /**
     * Crea i campi visibili
     * <p>
     * Come default spazzola tutti i campi della Entity <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     * NON garantisce l'ordine con cui vengono presentati i campi nella scheda <br>
     * Può mostrare anche il campo ID, oppure no <br>
     * Se si vuole differenziare tra Table, Form e Search, <br>
     * sovrascrivere creaFieldsList, creaFieldsForm e creaFieldsSearch <br>
     */
    protected Attribute<?, ?>[] creaFieldsAll() {
        return new Attribute[]{Anno_.ordinamento, Anno_.nome, Anno_.secolo};
    }// end of method

    /**
     * Registers a new action handler for this container
     *
     * @see com.vaadin.event.Action.Container#addActionHandler(Action.Handler)
     */
    private void addActionHandler(ATable tavola) {
        tavola.addActionHandler(new Action.Handler() {
            public Action[] getActions(Object target, Object sender) {
                Action[] actions = null;
                actions = new Action[3];
                actions[0] = actionUploadNati;
                actions[1] = actionUploadMorti;
                actions[2] = actionUploadAll;
                return actions;
            }// end of inner method

            public void handleAction(Action action, Object sender, Object target) {
                if (action.equals(actionUploadNati)) {
                    esegueUploadNati();
                }// end of if cycle
                if (action.equals(actionUploadMorti)) {
                    esegueUploadMorti();
                }// end of if cycle
                if (action.equals(actionUploadAll)) {
                    esegueUploadBoth();
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
        addCommandUploadAnni(menuItem);
    }// end of method

    /**
     * Comando bottone/item New Ciclo
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandUploadAnni(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Upload all", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                boolean usaDialoghi = Pref.getBool(CostBio.USA_DIALOGHI_CONFERMA, true);
                boolean usaDebug = Pref.getBool(CostBio.USA_DEBUG, false);
                boolean usaLimite = Pref.getBool(CostBio.USA_LIMITE_DOWNLOAD, false);
                boolean usaLog = Pref.getBool(CostBio.USA_LOG_DOWNLOAD, false);
                boolean usaUpdate = Pref.getBool(CostBio.USA_UPLOAD_DOWNLOADATA, false);
                boolean usaCancella = Pref.getBool(CostBio.USA_CANCELLA_VOCE_MANCANTE, false);
                if (usaDialoghi) {
                    String nomePagina = "<b><span style=\"color:red\">" + ListaBio.PAGINA_PROVA + "</span></b>";
                    String newMsg;
                    if (usaDebug) {
                        newMsg = "Crea la lista dei nati nella pagina: " + nomePagina + "<br/>";
                    } else {
                        newMsg = "Esegue un ciclo (<b><span style=\"color:green\">lista</span></b>) per la creazione di 3030 pagine di nati e di morti per ogni anno</br>";
                    }// end of if/else cycle
//                     newMsg = "Esegue un ciclo di sincronizzazione tra le pagine della categoria " + nomeCat + " ed i records della tavola Bio<br/>";
//                    newMsg += "<br/>Esegue un ciclo (<b><span style=\"color:green\">new</span></b>) di controllo e creazione di nuovi records esistenti nella categoria e mancanti nel database";
//                    newMsg += "<br/>Esegue un ciclo (<b><span style=\"color:green\">delete</span></b>) di cancellazione di records esistenti nel database e mancanti nella categoria";
//                    newMsg += "<br/>Esegue un ciclo (<b><span style=\"color:green\">update</span></b>) di controllo e aggiornamento di tutti i records esistenti nel database<br/>";
                    if (usaDebug) {
                        newMsg += "<br>Le preferenze prevedono di usare la singola pagina di debug: " + nomePagina;
                    } else {
                        newMsg += "<br>Le preferenze prevedono di creare <b><span style=\"color:red\">3030</span></b> pagine di nati e di morti per ogni anno</br>";
                    }// end of if/else cycle
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
                                        new UploadAnni();
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
     * Esegue l'upload per la lista dei nati
     */
    public void esegueUploadNati() {
        Anno anno = getAnno();

        if (anno != null) {
            new ListaAnnoNato(anno);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle
    }// end of method

    /**
     * Esegue l'upload per la lista dei morti
     */
    public void esegueUploadMorti() {
        Anno anno = getAnno();

        if (anno != null) {
            new ListaAnnoMorto(anno);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle
    }// end of method

    /**
     * Esegue l'upload per la lista dei nati
     * Esegue l'upload per la lista dei morti
     */
    public void esegueUploadBoth() {
        Anno anno = getAnno();

        if (anno != null) {
            new ListaAnnoNato(anno);
            new ListaAnnoMorto(anno);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle
    }// end of method

    /**
     * Recupera la voce selezionata
     */
    public Anno getAnno() {
        Anno anno = null;
        long idSelected = 0;
        ATable tavola = getTable();

        if (tavola != null) {
            idSelected = tavola.getSelectedKey();
        }// end of if cycle

        if (idSelected > 0) {
            anno = Anno.find(idSelected);
        }// end of if cycle

        return anno;
    }// end of method

}// end of class



