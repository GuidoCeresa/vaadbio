package it.algos.vaadbio.nome;


import com.vaadin.event.Action;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.cognome.CognomeService;
import it.algos.vaadbio.cognome.CognomeTablePortal;
import it.algos.vaadbio.esegue.Esegue;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.liste.ListaAntroNome;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.search.SearchManager;
import it.algos.webbase.web.table.ATable;
import it.algos.webbase.web.table.TablePortal;

import java.util.ArrayList;

/**
 * Gestione (minimale) del modulo
 */
public class NomeMod extends ModulePop {

    private static final long serialVersionUID = 1L;

    // indirizzo interno del modulo - etichetta del menu
    public static String MENU_ADDRESS = "Nomi";

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
    public NomeMod() {
        super(Nome.class, MENU_ADDRESS, ICON);
        this.getTable().setRowHeaderMode(Table.RowHeaderMode.INDEX);
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
     * Create the Table Portal
     *
     * @return the TablePortal
     */
    @Override
    public TablePortal createTablePortal() {
        return new NomeTablePortal(this);
    }// end of method


    @Override
    public SearchManager createSearchManager() {
        return new NomeSearch(this);
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
        addCommandDownloadDoppi(menuItem);
        addCommandAggiunge(menuItem);
        addCommandElabora(menuItem);
        addCommandUpload(menuItem);
        addCommandStatistiche(menuItem);
        addCommandTestIncipit(menuItem);
    }// end of method

    /**
     * Comando bottone/item crea e/o aggiunge nuovi cognomi individuati nei records di Bio
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandCrea(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Crea", FontAwesome.BEER, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                NomeService.crea();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item download dei nomi doppi
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandDownloadDoppi(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Nomi doppi", FontAwesome.BEER, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                esegueDownloadDoppi();
            }// end of method
        });// end of anonymous class
    }// end of method


    /**
     * Comando bottone/item aggiounge nuovi nomi individuati nei records di Bio
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
     * Comando bottone/item elabora i records esistenti
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandElabora(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Elabora", FontAwesome.BEER, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                esegueElabora();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item Upload
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandUpload(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Upload all", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                esegueUploadAll();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item Statistiche
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandStatistiche(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Statistiche", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                esegueStatistiche();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item Test Incipit
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandTestIncipit(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Test", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                esegueTestIncipit();
            }// end of method
        });// end of anonymous class
    }// end of method


    /**
     * Esegue l'aggiornamento della lista dei nomi doppi
     */
    protected void esegueDownloadDoppi() {
        Esegue.downloadNomiDoppi();
    }// end of method


    /**
     * Esegue l'aggiunta dei nuovi records
     */
    protected void esegueAggiorna() {
        Esegue.aggiornaNomi();
    }// end of method

    /**
     * Esegue l'elaborazione dei records esistenti
     */
    protected void esegueElabora() {
        Esegue.elaboraNomi();
    }// end of method

    /**
     * Esegue l'upload di tutti i record
     */
    protected void esegueUploadAll() {
        Esegue.uploadNomi();
    }// end of method

    /**
     * Crea le pagine delle statistiche (3)
     */
    protected void esegueStatistiche() {
        Esegue.statisticheNomi();
    }// end of method

    /**
     * Crea la pagina di test per l'incipit
     */
    protected void esegueTestIncipit() {
        ArrayList<Bio> lista = Bio.findAll();
        int k = 0;

        for (Bio bio : lista) {
            if (!bio.getAttivita().equals(CostBio.VUOTO)) {
                k++;
                if (bio.getAttivita().equals(bio.getAttivita2())) {
                    int a = 87;
                }// end of if cycle
            }// end of if cycle
        }// end of for cycle


    }// end of method

    /**
     * Esegue l'upload di un singolo nome
     */
    protected void esegueUpload() {
        Nome nome = getNome();

        if (nome != null) {
            new ListaAntroNome(nome);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle

    }// end of method


    /**
     * Recupera la voce selezionata
     */
    private Nome getNome() {
        Nome nome = null;
        long idSelected = 0;
        ATable tavola = getTable();

        if (tavola != null) {
            idSelected = tavola.getSelectedKey();
        }// end of if cycle

        if (idSelected > 0) {
            nome = Nome.find(idSelected);
        }// end of if cycle

        return nome;
    }// end of method

}// end of class

