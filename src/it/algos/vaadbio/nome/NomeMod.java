package it.algos.vaadbio.nome;


import com.vaadin.event.Action;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import it.algos.vaadbio.esegue.Esegue;
import it.algos.vaadbio.liste.ListaAnnoMorto;
import it.algos.vaadbio.liste.ListaAnnoNato;
import it.algos.vaadbio.liste.ListaNome;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ATable;

/**
 * Gestione (minimale) del modulo
 */
@SuppressWarnings("serial")
public class NomeMod extends ModulePop {


    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Nomi";

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
        super(Nome.class, MENU_ADDRESS, FontAwesome.LIST_UL);
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
        addCommandAggiorna(menuItem);
        addCommandElabora(menuItem);
        addCommandUpload(menuItem);
    }// end of method

    /**
     * Comando bottone/item download
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
     * Comando bottone/item elabora i records esistenti
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandAggiorna(MenuBar.MenuItem menuItem) {
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
     * Esegue l'aggiornamento della lista dei nomi doppi
     */
    protected void esegueDownloadDoppi() {
        Esegue.esegueDownloadDoppi();
    }// end of method


    /**
     * Esegue l'aggiunta dei nuovi records
     */
    protected void esegueAggiorna() {
        Esegue.aggiungeNomi();
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
     * Esegue l'upload di un nome solamente
     */
    protected void esegueUpload() {
        Nome nome = getNome();

        if (nome != null) {
            new ListaNome(nome);
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

