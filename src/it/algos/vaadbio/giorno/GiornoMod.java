package it.algos.vaadbio.giorno;


import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import it.algos.vaadbio.ciclo.CicloDownload;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.liste.ListaBio;
import it.algos.vaadbio.upload.UploadGiorni;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.dialog.ConfirmDialog;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.navigator.NavPlaceholder;

/**
 * Gestione (minimale) del modulo
 * Passa alla superclasse il nome e la classe specifica
 */
@SuppressWarnings("serial")
public class GiornoMod extends ModulePop {

    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Giorno";


    /**
     * Costruttore senza parametri
     * La classe implementa il pattern Singleton.
     * Per una nuova istanza, usare il metodo statico getInstance.
     * Usare questo costruttore SOLO con la Reflection dal metodo Module.getInstance
     * Questo costruttore è pubblico SOLO per l'uso con la Reflection.
     * Per il pattern Singleton dovrebbe essere privato.
     *
     * @deprecated
     */
    public GiornoMod() {
        super(Giorno.class, MENU_ADDRESS);
    }// end of constructor

    /**
     * Crea una sola istanza di un modulo per sessione.
     * Tutte le finestre e i tab di un browser sono nella stessa sessione.
     */
    public static GiornoMod getInstance() {
        return (GiornoMod) ModulePop.getInstance(GiornoMod.class);
    }// end of singleton constructor

    /**
     * Create the MenuBar Item for this module
     * <p>
     * Invocato dal metodo AlgosUI.creaMenu()
     * PUO essere sovrascritto dalla sottoclasse
     *
     * @param menuBar     a cui agganciare il menuitem
     * @param placeholder in cui visualizzare il modulo
     * @return menuItem appena creato
     */
    @Override
    public MenuBar.MenuItem createMenuItem(MenuBar menuBar, NavPlaceholder placeholder) {
        MenuBar.MenuItem menuItem = super.createMenuItem(menuBar, placeholder, FontAwesome.TASKS);

        addCommandUploadGiorni(menuItem);

        return menuItem;
    }// end of method

    /**
     * Comando bottone/item New Ciclo
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandUploadGiorni(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Upload all", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                boolean usaDialoghi = Pref.getBool(CostBio.USA_DIALOGHI_CONFERMA, true);
                boolean usaDebug = Pref.getBool(CostBio.USA_DEBUG, false);
                boolean usaLimite = Pref.getBool(CostBio.USA_LIMITE_DOWNLOAD, false);
                boolean usaLog = Pref.getBool(CostBio.USA_LOG_DOWNLOAD, false);
                boolean usaUpdate = Pref.getBool(CostBio.USA_UPLOAD_DOWNLOADATA, false);
                boolean usaCancella = Pref.getBool(CostBio.USA_CANCELLA_VOCE_MANCANTE, false);
                if (usaDialoghi) {
                    String  nomePagina = "<b><span style=\"color:red\">" + ListaBio.PAGINA_PROVA + "</span></b>";
                    String newMsg;
                    if (usaDebug) {
                         newMsg = "Crea la lista dei nati nella pagina: " + nomePagina + "<br/>";
                    } else {
                        newMsg = "Esegue un ciclo (<b><span style=\"color:green\">lista</span></b>) per la creazione di 366+366 pagine di nati e di morti per ogni giorno dell'anno</br>";
                    }// end of if/else cycle
//                     newMsg = "Esegue un ciclo di sincronizzazione tra le pagine della categoria " + nomeCat + " ed i records della tavola Bio<br/>";
//                    newMsg += "<br/>Esegue un ciclo (<b><span style=\"color:green\">new</span></b>) di controllo e creazione di nuovi records esistenti nella categoria e mancanti nel database";
//                    newMsg += "<br/>Esegue un ciclo (<b><span style=\"color:green\">delete</span></b>) di cancellazione di records esistenti nel database e mancanti nella categoria";
//                    newMsg += "<br/>Esegue un ciclo (<b><span style=\"color:green\">update</span></b>) di controllo e aggiornamento di tutti i records esistenti nel database<br/>";
                    if (usaDebug) {
                        newMsg += "<br>Le preferenze prevedono di usare la singola pagina di debug: " + nomePagina;
                    } else {
                        newMsg += "<br>Le preferenze prevedono di creare <b><span style=\"color:red\">366+366</span></b> pagine di nati e di morti per ogni giorno dell'anno</br>";
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
                                        new UploadGiorni();
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

}// end of class

