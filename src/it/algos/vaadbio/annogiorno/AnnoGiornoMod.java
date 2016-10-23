package it.algos.vaadbio.annogiorno;


import com.vaadin.event.Action;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import it.algos.vaadbio.ciclo.CicloDownload;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.liste.ListaBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.dialog.ConfirmDialog;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ATable;

/**
 * Gestione (minimale) del modulo
 * Passa alla superclasse il nome e la classe specifica
 */
@SuppressWarnings("serial")
public abstract class AnnoGiornoMod extends ModulePop {


    protected static final String WIKI_BASE = "https://it.wikipedia.org/";
    protected static final String WIKI_URL = WIKI_BASE + "wiki/";


    private Action actionPaginaNati = new Action("Lista nati", FontAwesome.SEARCH);
    private Action actionPaginaMorti = new Action("Lista morti", FontAwesome.SEARCH);
    private Action actionUploadNati = new Action("Upload nati", FontAwesome.ARROW_UP);
    private Action actionUploadMorti = new Action("Upload morti", FontAwesome.ARROW_UP);
    private Action actionUploadAll = new Action("Upload both", FontAwesome.ARROW_UP);

    /**
     * Costruttore minimo
     *
     * @param entity di riferimento del modulo
     */
    public AnnoGiornoMod(Class entity) {
        this(entity, "", null);
    }// end of constructor

    /**
     * Costruttore
     *
     * @param entity    di riferimento del modulo
     * @param menuLabel etichetta visibile nella menu bar
     */
    public AnnoGiornoMod(Class entity, String menuLabel) {
        this(entity, menuLabel, null);
    }// end of constructor

    /**
     * Costruttore
     *
     * @param entity   di riferimento del modulo
     * @param menuIcon icona del menu
     */
    public AnnoGiornoMod(Class entity, Resource menuIcon) {
        this(entity, "", menuIcon);
    }// end of constructor

    /**
     * Costruttore completo
     * <p>
     *
     * @param entity    di riferimento del modulo
     * @param menuLabel etichetta visibile nella menu bar
     * @param menuIcon  icona del menu
     */
    public AnnoGiornoMod(Class entity, String menuLabel, Resource menuIcon) {
        super(entity, menuLabel, menuIcon);
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
                actions = new Action[5];
                actions[0] = actionPaginaNati;
                actions[1] = actionPaginaMorti;
                actions[2] = actionUploadNati;
                actions[3] = actionUploadMorti;
                actions[4] = actionUploadAll;
                return actions;
            }// end of inner method

            public void handleAction(Action action, Object sender, Object target) {
                if (action.equals(actionPaginaNati)) {
                    eseguePaginaNati();
                }// end of if cycle
                if (action.equals(actionPaginaMorti)) {
                    eseguePaginaMorti();
                }// end of if cycle
                if (action.equals(actionUploadNati)) {
                    esegueUploadNato();
                }// end of if cycle
                if (action.equals(actionUploadMorti)) {
                    esegueUploadMorto();
                }// end of if cycle
                if (action.equals(actionUploadAll)) {
                    esegueUploadBothNatoMorto();
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
        addCommandUpload(menuItem);
        addCommandUploadContrario(menuItem);
        addCommandStatistiche(menuItem);
    }// end of method

    /**
     * Comando bottone/item Upload
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandUpload(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Upload all", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                boolean usaDialoghi = Pref.getBoolean(CostBio.USA_DIALOGHI_CONFERMA, true);
                boolean usaDebug = Pref.getBoolean(CostBio.USA_DEBUG, false);
                boolean usaRegistraSempre = Pref.getBoolean(CostBio.USA_REGISTRA_SEMPRE_CRONO,false);
                boolean usaLog = Pref.getBoolean(CostBio.USA_LOG_DEBUG, false);
                if (usaDialoghi) {
                    String nomePagina = "<b><span style=\"color:red\">" + ListaBio.PAGINA_PROVA + "</span></b>";
                    String newMsg;
                    if (usaDebug) {
                        newMsg = "Crea la lista dei nati nella pagina: " + nomePagina + "<br/>";
                    } else {
                        newMsg = getMsg();
                    }// end of if/else cycle
                    if (usaDebug) {
                        newMsg += "<br>Le preferenze prevedono di usare la singola pagina di debug: " + nomePagina;
                    }// end of if cycle
                    if (usaRegistraSempre) {
                        newMsg += "<br>Le preferenze prevedono di registrare <b><span style=\"color:red\">sempre</span></b> la pagina";
                    } else {
                        newMsg += "<br>Le preferenze prevedono di registrare la pagina <b><span style=\"color:red\">solo</span></b> se modificata significativamente";
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
                                        esegueUploadAll();
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
     * Comando bottone/item Upload
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandUploadContrario(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Upload contrario", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                boolean usaDialoghi = Pref.getBoolean(CostBio.USA_DIALOGHI_CONFERMA, true);
                boolean usaDebug = Pref.getBoolean(CostBio.USA_DEBUG, false);
                boolean usaRegistraSempre = Pref.getBoolean(CostBio.USA_REGISTRA_SEMPRE_CRONO,false);
                boolean usaLog = Pref.getBoolean(CostBio.USA_LOG_DEBUG, false);
                if (usaDialoghi) {
                    String nomePagina = "<b><span style=\"color:red\">" + ListaBio.PAGINA_PROVA + "</span></b>";
                    String newMsg;
                    if (usaDebug) {
                        newMsg = "Crea la lista dei nati nella pagina: " + nomePagina + "<br/>";
                    } else {
                        newMsg = getMsg();
                    }// end of if/else cycle
                    if (usaDebug) {
                        newMsg += "<br>Le preferenze prevedono di usare la singola pagina di debug: " + nomePagina;
                    }// end of if cycle
                    if (usaRegistraSempre) {
                        newMsg += "<br>Le preferenze prevedono di registrare <b><span style=\"color:red\">sempre</span></b> la pagina";
                    } else {
                        newMsg += "<br>Le preferenze prevedono di registrare la pagina <b><span style=\"color:red\">solo</span></b> se modificata significativamente";
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
                                        esegueUploadAllContrario();
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
     * Comando bottone/item Statistiche
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandStatistiche(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Statistiche", FontAwesome.BEER, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                esegueStatistiche();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Messaggio del dialogo di avviso/conferma
     * Sovrascritto
     */
    protected String getMsg() {
        return CostBio.VUOTO;
    }// end of method

    /**
     * Esegue l'upload di tutti i record
     * Sovrascritto
     */
    protected void esegueUploadAll() {
    }// end of method

    /**
     * Esegue l'upload di tutti i record (in ordine inverso)
     * Sovrascritto
     */
    protected void esegueUploadAllContrario() {
    }// end of method

    /**
     * Apre la pagina di wikipedia della lista di nati corrispondente
     * Sovrascritto
     */
    protected void eseguePaginaNati() {
    }// end of method

    /**
     * Apre la pagina della lista di morti corrispondente
     * Sovrascritto
     */
    protected void eseguePaginaMorti() {
    }// end of method

    /**
     * Esegue l'upload per la lista dei nati di questo record
     * Sovrascritto
     */
    protected void esegueUploadNato() {
    }// end of method

    /**
     * Esegue l'upload per la lista dei morti di questo record
     * Sovrascritto
     */
    protected void esegueUploadMorto() {
    }// end of method

    /**
     * Esegue l'upload per la lista dei nati di questo record
     * Esegue l'upload per la lista dei morti di questo record
     */
    protected void esegueUploadBothNatoMorto() {
    }// end of method

    /**
     * Esegue la creazione delle pagine statistiche
     */
    protected void esegueStatistiche() {
    }// end of method

}// end of class



