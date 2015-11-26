package it.algos.vaadbio.bio;

import com.vaadin.data.Item;
import com.vaadin.event.Action;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.CicloDown;
import it.algos.vaadbio.CicloElabora;
import it.algos.vaadbio.DownloadBio;
import it.algos.vaadbio.ElaboraOnly;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.dialog.ConfirmDialog;
import it.algos.webbase.web.dialog.EditDialog;
import it.algos.webbase.web.form.AForm;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.navigator.NavPlaceholder;
import it.algos.webbase.web.table.ATable;

import javax.persistence.metamodel.Attribute;

/**
 * Gestione (minimale) del modulo
 * Passa alla superclasse il nome e la classe specifica
 */
@SuppressWarnings("serial")
public class BioMod extends ModulePop {

    public static final String WIKI_BASE = "https://it.wikipedia.org/";
    public static final String WIKI_URL = WIKI_BASE + "wiki/";
    public static final String WIKI_TITLE = WIKI_BASE + "w/index.php?title=";
    public static final String WIKI_CRONO = WIKI_TITLE;
    public static final String WIKI_CRONO_END = "&action=history";


    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Bio";
    private Action actionDownload = new Action("Download", FontAwesome.ARROW_DOWN);
    private Action actionElabora = new Action("Elabora", FontAwesome.REFRESH);
    private Action actionUpload = new Action("Upload", FontAwesome.ARROW_UP);
    private Action actionVoce = new Action("Pagina", FontAwesome.SEARCH);
    private Action actionCrono = new Action("Cronologia", FontAwesome.HISTORY);


    public BioMod() {
        super(Bio.class, MENU_ADDRESS);
    }// end of constructor

    /**
     * Crea i campi visibili nella lista (table)
     * <p/
     * Come default spazzola tutti i campi della Entity <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     * Serve anche per l'ordine con cui vengono presentati i campi nella lista <br>
     */
    @Override
    protected Attribute<?, ?>[] creaFieldsList() {
        return new Attribute[]{Bio_.pageid, Bio_.title, Bio_.templateEsiste, Bio_.templateValido, Bio_.templatesUguali, Bio_.ultimaLettura, Bio_.ultimaElaborazione};
    }// end of method

    /**
     * Crea i campi visibili nella scheda (form)
     * <p>
     * Come default spazzola tutti i campi della Entity <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     * Serve anche per l'ordine con cui vengono presentati i campi nella scheda <br>
     */
    protected Attribute<?, ?>[] creaFieldsForm() {
        return new Attribute[]{Bio_.pageid, Bio_.title, Bio_.templateEsiste, Bio_.templateValido, Bio_.templatesUguali, Bio_.ultimaLettura, Bio_.ultimaElaborazione};
    }// end of method


    /**
     * Returns the table used to shows the list. <br>
     * The concrete subclass must override for a specific Table.
     *
     * @return the Table
     */
    public ATable createTable() {
        ATable tavola = new BioTable(this);

        addActionHandler(tavola);
        return tavola;
    }// end of method

    /**
     * Returns the form used to edit an item. <br>
     * The concrete subclass must override for a specific Form.
     *
     * @param item singola istanza della classe
     * @return the Form
     */
    public AForm createForm(Item item) {
        return (new BioForm(this, item));
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
                actions = new Action[5];
                actions[0] = actionDownload;
                actions[1] = actionElabora;
                actions[2] = actionUpload;
                actions[3] = actionVoce;
                actions[4] = actionCrono;
                return actions;
            }// end of inner method

            public void handleAction(Action action, Object sender, Object target) {
                if (action.equals(actionDownload)) {
                    esegueDownload();
                }// end of if cycle
                if (action.equals(actionElabora)) {
                    esegueElabora();
                }// end of if cycle
                if (action.equals(actionUpload)) {
                    esegueUpload();
                }// end of if cycle
                if (action.equals(actionVoce)) {
                    esegueVoce();
                }// end of if cycle
                if (action.equals(actionCrono)) {
                    esegueCrono();
                }// end of if cycle
            }// end of inner method
        });// end of anonymous inner class

    }// end of method


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

        addCommandCicloDown(menuItem);
        addCommandCicloElabora(menuItem);
        addCommandDownloadDialog(menuItem);
//        addCommandDownload(menuItem);
        addCommandElabora(menuItem);
        addCommandUpload(menuItem);

        return menuItem;
    }// end of method


    /**
     * Comando bottone/item New Ciclo
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandCicloDown(MenuBar.MenuItem menuItem) {
        final String msg = "Messaggio di controllo";

        // bottone New Ciclo
        menuItem.addItem("Ciclo down", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                if (Pref.getBool(CostBio.USA_DIALOGHI_CONFERMA, true)) {
                    final String nomeCat;
                    final boolean usaDebug = Pref.getBool(CostBio.USA_DEBUG, false);
                    final boolean usaLimite = Pref.getBool(CostBio.USA_LIMITE_DOWNLOAD, false);
                    final boolean usaLog = Pref.getBool(CostBio.USA_LOG_DOWNLOAD, false);
                    final boolean usaUpdate = Pref.getBool(CostBio.USA_UPLOAD_DOWNLOADATA, false);
                    final boolean usaCancella = Pref.getBool(CostBio.USA_CANCELLA_VOCE_MANCANTE, false);
                    if (usaDebug) {
                        nomeCat = "<b><span style=\"color:red\">" + CicloDown.TAG_CAT_DEBUG + "</span></b>";
                    } else {
                        nomeCat = "<b><span style=\"color:red\">" + CicloDown.TAG_BIO + "</span></b>";
                    }// end of if/else cycle
                    int maxDowloadNew = Pref.getInt(CostBio.MAX_DOWNLOAD, 1000);
                    String newMsg = "Esegue un ciclo di sincronizzazione tra le pagine della categoria " + nomeCat + " ed i records della tavola Bio<br/>";
                    newMsg += "<br/>Esegue un ciclo (<b><span style=\"color:green\">new</span></b>) di controllo e creazione di nuovi records esistenti nella categoria e mancanti nel database";
                    newMsg += "<br/>Esegue un ciclo (<b><span style=\"color:green\">delete</span></b>) di cancellazione di records esistenti nel database e mancanti nella categoria";
                    newMsg += "<br/>Esegue un ciclo (<b><span style=\"color:green\">update</span></b>) di controllo e aggiornamento di tutti i records esistenti nel database<br/>";
                    if (usaDebug) {
                        newMsg += "<br>Le preferenze prevedono di usare la categoria di debug " + nomeCat;
                    } else {
                        newMsg += "<br>Le preferenze prevedono di usare la categoria " + nomeCat;
                    }// end of if/else cycle
                    if (usaLimite) {
                        newMsg += "<br>Le preferenze prevedono di scaricare <b><span style=\"color:red\">" + LibNum.format(maxDowloadNew) + "</span></b> voci dal server";
                    } else {
                        newMsg += "<br>Le preferenze prevedono di scaricare dal server <b><span style=\"color:red\">tutte</span></b> le voci della categoria";
                    }// end of if/else cycle
                    if (usaLog) {
                        newMsg += "<br>Le preferenze prevedono di registrare il risultato nei <b><span style=\"color:red\">log</span></b>";
                    } else {
                        newMsg += "<br>Le preferenze <b><span style=\"color:red\">non</span></b> prevedono di registrare il risultato nei log";
                    }// end of if/else cycle
                    if (usaUpdate) {
                        newMsg += "<br>Le preferenze prevedono un <b><span style=\"color:red\">upload</span></b> della voce se il templateStandard è diverso dal templateServer";
                    } else {
                        newMsg += "<br>Le preferenze <b><span style=\"color:red\">non</span></b> prevedono un upload della voce";
                    }// end of if/else cycle
                    if (usaCancella) {
                        newMsg += "<br>Le preferenze prevedono di <b><span style=\"color:red\">cancellare</span></b> le voci non più presenti nella categoria";
                    } else {
                        newMsg += "<br>Le preferenze <b><span style=\"color:red\">non</span></b> prevedono di cancellare le voci";
                    }// end of if/else cycle
                    ConfirmDialog dialog = new ConfirmDialog(msg, newMsg,
                            new ConfirmDialog.Listener() {
                                @Override
                                public void onClose(ConfirmDialog dialog, boolean confirmed) {
                                    if (confirmed) {
                                        new CicloDown();
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
                    new CicloDown();
                }// fine del blocco if-else
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item Elabora Ciclo
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandCicloElabora(MenuBar.MenuItem menuItem) {
        final String msg = "Messaggio di controllo";

        menuItem.addItem("Ciclo elabora", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                if (Pref.getBool(CostBio.USA_DIALOGHI_CONFERMA)) {
                    int maxElabora = Pref.getInt(CostBio.MAX_DOWNLOAD);
                    String newMsg = "Elabora le informazioni dopo che una pagina è stata scaricata/aggiornata dal server";
                    newMsg += "<br>Recupera i dati dal record della tavola BioWiki";
                    newMsg += "<br>Crea/aggiorna il corrispondente record della tavola BioOriginale estraendo i singoli parametri";
                    newMsg += "<br>Le preferenze prevedono di elaborare ";
                    newMsg += LibNum.format(maxElabora);
                    newMsg += " voci";
                    newMsg += "<br>Occorre diverso tempo";
                    ConfirmDialog dialog = new ConfirmDialog(msg, newMsg,
                            new ConfirmDialog.Listener() {
                                @Override
                                public void onClose(ConfirmDialog dialog, boolean confirmed) {
                                    if (confirmed) {
                                        new CicloElabora();
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
                    new CicloElabora();
                }// fine del blocco if-else
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item Download voce scelta da dialogo di ricerca
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandDownloadDialog(MenuBar.MenuItem menuItem) {
        final String msg = "Messaggio di controllo";

        menuItem.addItem("Download...", FontAwesome.ARROW_DOWN, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                String titolo = getTitleSelected();

                EditDialog dialog = new EditDialog("Download", "Download", "Titolo della pagina", new EditDialog.EditListener() {
                    @Override
                    public void onClose() {
                    }// end of method

                    @Override
                    public void onClose(String title) {
                        esegueDownload(title);
                        Log.setInfo("download", "Singolo download della voce " + LibWiki.setQuadre(title));
                    }// end of method
                });// end of anonymous inner class
                dialog.getField().setValue(titolo);
                UI ui = getUI();
                if (ui != null) {
                    dialog.show(ui);
                } else {
                    Notification.show("Avviso", "Devi prima entrare nel modulo BioWiki per eseguire questo comando", Notification.Type.WARNING_MESSAGE);
                }// end of if/else cycle
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item Download singola voce
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandDownload(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Download", FontAwesome.ARROW_DOWN, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                esegueDownload();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item Elabora singola voce
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandElabora(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Elabora", FontAwesome.REFRESH, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                esegueElabora();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item Upload singola voce
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandUpload(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Upload", FontAwesome.ARROW_UP, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                esegueUpload();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Esegue il download della singola voce
     */
    public void esegueDownload() {
        ATable tavola = getTable();
        long idSelected = 0;
        long pageid = 0;
        Bio bio = null;

        if (tavola != null) {
            idSelected = tavola.getSelectedKey();
        }// end of if cycle

        if (idSelected > 0) {
            bio = Bio.find(idSelected);
        }// end of if cycle

        if (bio != null) {
            pageid = bio.getPageid();
        }// end of if cycle

        if (pageid > 0) {
            new DownloadBio(pageid);
        }// end of if cycle

    }// end of method

    /**
     * Esegue il download della singola voce
     */
    public void esegueDownload(String title) {
        if (!title.equals("")) {
            new DownloadBio(title);
        }// end of if cycle
    }// end of method


    /**
     * Esegue l'elaborazione della singola voce
     */
    public void esegueElabora() {
        ATable tavola = getTable();
        long idSelected = 0;
        Bio bio = null;

        if (tavola != null) {
            idSelected = tavola.getSelectedKey();
        }// end of if cycle

        if (idSelected > 0) {
            bio = Bio.find(idSelected);
        }// end of if cycle

        if (bio != null) {
            new ElaboraOnly(bio);
        }// end of if cycle

    }// end of method

    /**
     * Esegue l'upload della singola voce
     */
    public void esegueUpload() {
    }// end of method

    /**
     * Apre la voce di wikipedia
     */
    private void esegueVoce() {
        String title = getTitle();

        if (title != null && !title.equals("")) {
            this.getUI().getPage().open(WIKI_URL + title, "_blank");
        } else {
            Notification.show("Devi selezionare una riga per visualizzare la pagina corrispondente su wikipedia");
        }// end of if/else cycle
    }// end of method


    /**
     * Apre la cronologia delle modifiche della voce
     */
    private void esegueCrono() {
        String title = getTitle();

        if (title != null && !title.equals("")) {
            this.getUI().getPage().open(WIKI_CRONO + title + WIKI_CRONO_END, "_blank");
        } else {
            Notification.show("Devi selezionare una riga per visualizzare la cronologia della voce su wikipedia");
        }// end of if/else cycle
    }// end of method


    /**
     * Titolo della pagina sul server
     *
     * @return Wiki title della riga (singola) selezionata
     **/
    private String getTitleSelected() {
        String title = "";
        Long idKey;
        Bio bio;
        ATable table = getTable();

        if (table.isSingleRowSelected()) {
            idKey = table.getSelectedKey();
            bio = Bio.find(idKey);
            if (bio != null) {
                title = bio.getTitle();
            }// fine del blocco if
        }// fine del blocco if

        return title;
    }// end of method

    /**
     * Recupera il titolo della voce selezionata
     */
    public String getTitle() {
        String title = "";
        Bio bio = getBio();

        if (bio != null) {
            title = bio.getTitle();
        }// end of if cycle

        return title;
    }// end of method

    /**
     * Recupera la voce selezionata
     */
    public Bio getBio() {
        Bio bio = null;
        long idSelected = 0;
        ATable tavola = getTable();

        if (tavola != null) {
            idSelected = tavola.getSelectedKey();
        }// end of if cycle

        if (idSelected > 0) {
            bio = Bio.find(idSelected);
        }// end of if cycle

        return bio;
    }// end of method

}// end of class
