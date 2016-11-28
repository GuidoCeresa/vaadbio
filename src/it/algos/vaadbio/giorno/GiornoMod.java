package it.algos.vaadbio.giorno;


import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Notification;
import it.algos.vaadbio.crono.CronoMod;
import it.algos.vaadbio.esegue.Esegue;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.liste.ListaGiornoMorto;
import it.algos.vaadbio.liste.ListaGiornoNato;
import it.algos.webbase.web.table.ATable;
import it.algos.webbase.web.table.TablePortal;

/**
 * Gestione (minimale) del modulo
 */
@SuppressWarnings("serial")
public class GiornoMod extends CronoMod {


    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Giorni";


    /**
     * Costruttore senza parametri
     * <p>
     * Invoca la superclasse passando i parametri:
     * (obbligatorio) la Entity specifica
     * (facoltativo) etichetta del menu (se manca usa il nome della Entity)
     * (facoltativo) icona del menu (se manca usa un'icona standard)
     */
    public GiornoMod() {
        super(Giorno.class, MENU_ADDRESS, FontAwesome.LIST_UL);
    }// end of constructor


    /**
     * Returns the table used to shows the list. <br>
     * The concrete subclass must override for a specific Table.
     *
     * @return the Table
     */
    public ATable createTable() {
        return new GiornoTable(this);
    }// end of method

    /**
     * Create the Table Portal
     *
     * @return the TablePortal
     */
    @Override
    public TablePortal createTablePortal() {
        return new GiornoTablePortal(this);
    }// end of method


    /**
     * Messaggio del dialogo di avviso/conferma
     * Sovrascritto
     */
    @Override
    protected String getMsg() {
        return "Esegue un ciclo (<b><span style=\"color:green\">lista</span></b>) per la creazione di 366+366 pagine di nati e di morti per ogni giorno dell'anno</br>";
    }// end of method


    /**
     * Apre la pagina di wikipedia della lista di nati corrispondente
     * Sovrascritto
     */
    @Override
    protected void eseguePaginaNati() {
        String titoloPagina = CostBio.VUOTO;
        Giorno giorno = getGiorno();

        if (giorno != null) {
            titoloPagina = giorno.getTitoloListaNati();
            this.getUI().getPage().open(WIKI_URL + titoloPagina, "_blank");
        }// end of if/else cycle

    }// end of method

    /**
     * Apre la pagina di wikipedia della lista di morti corrispondente
     * Sovrascritto
     */
    @Override
    protected void eseguePaginaMorti() {
        String titoloPagina = CostBio.VUOTO;
        Giorno giorno = getGiorno();

        if (giorno != null) {
            titoloPagina = giorno.getTitoloListaMorti();
            this.getUI().getPage().open(WIKI_URL + titoloPagina, "_blank");
        }// end of if/else cycle

    }// end of method

    /**
     * Esegue l'upload di tutti i record
     * Sovrascritto
     */
    @Override
    protected void esegueUploadAll() {
        Esegue.uploadGiorni();
    }// end of method

    /**
     * Esegue l'upload per la lista dei nati di questo record
     * Sovrascritto
     */
    @Override
    protected void esegueUploadNato() {
        Giorno giorno = getGiorno();

        if (giorno != null) {
            new ListaGiornoNato(giorno);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle
    }// end of method

    /**
     * Esegue l'upload per la lista dei morti di questo record
     * Sovrascritto
     */
    @Override
    protected void esegueUploadMorto() {
        Giorno giorno = getGiorno();

        if (giorno != null) {
            new ListaGiornoMorto(giorno);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle
    }// end of method

    /**
     * Esegue l'upload per la lista dei nati di questo record
     * Esegue l'upload per la lista dei morti di questo record
     */
    protected void esegueUploadBothNatoMorto() {
        Giorno giorno = getGiorno();

        if (giorno != null) {
            new ListaGiornoNato(giorno);
            new ListaGiornoMorto(giorno);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle
    }// end of method

    /**
     * Esegue la creazione delle pagine statistiche
     */
    protected void esegueStatistiche() {
        Esegue.statisticaGiorni();
    }// end of method

    /**
     * Recupera la voce selezionata
     */
    private Giorno getGiorno() {
        Giorno giorno = null;
        long idSelected = 0;
        ATable tavola = getTable();

        if (tavola != null) {
            idSelected = tavola.getSelectedKey();
        }// end of if cycle

        if (idSelected > 0) {
            giorno = Giorno.find(idSelected);
        }// end of if cycle

        return giorno;
    }// end of method


}// end of class

