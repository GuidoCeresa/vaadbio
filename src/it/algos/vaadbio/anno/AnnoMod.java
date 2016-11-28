package it.algos.vaadbio.anno;


import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Notification;
import it.algos.vaadbio.crono.CronoMod;
import it.algos.vaadbio.esegue.Esegue;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.liste.ListaAnnoMorto;
import it.algos.vaadbio.liste.ListaAnnoNato;
import it.algos.vaadbio.upload.UploadAnni;
import it.algos.webbase.web.table.ATable;
import it.algos.webbase.web.table.TablePortal;

import javax.persistence.metamodel.Attribute;

/**
 * Gestione (minimale) del modulo
 * Passa alla superclasse il nome e la classe specifica
 */
@SuppressWarnings("serial")
public class AnnoMod extends CronoMod {

    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Anni";


    /**
     * Costruttore senza parametri
     * <p>
     * Invoca la superclasse passando i parametri:
     * (obbligatorio) la Entity specifica
     * (facoltativo) etichetta del menu (se manca usa il nome della Entity)
     * (facoltativo) icona del menu (se manca usa un'icona standard)
     */
    public AnnoMod() {
        super(Anno.class, MENU_ADDRESS, FontAwesome.LIST_UL);
    }// end of constructor


    /**
     * Returns the table used to shows the list. <br>
     * The concrete subclass must override for a specific Table.
     *
     * @return the Table
     */
    public ATable createTable() {
        return new AnnoTable(this);
    }// end of method

    /**
     * Create the Table Portal
     *
     * @return the TablePortal
     */
    @Override
    public TablePortal createTablePortal() {
        return new AnnoTablePortal(this);
    }// end of method

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
        return new Attribute[]{Anno_.ordinamento, Anno_.titolo, Anno_.secolo};
    }// end of method


    /**
     * Messaggio del dialogo di avviso/conferma
     * Sovrascritto
     */
    @Override
    protected String getMsg() {
        return "Esegue un ciclo (<b><span style=\"color:green\">lista</span></b>) per la creazione di 3030 pagine di nati e di morti per ogni anno</br>";
    }// end of method


    /**
     * Apre la pagina di wikipedia della lista di nati corrispondente
     * Sovrascritto
     */
    @Override
    protected void eseguePaginaNati() {
        String titoloPagina = CostBio.VUOTO;
        Anno anno = getAnno();

        if (anno != null) {
            titoloPagina = anno.getTitoloLista("Nati ");
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
        Anno anno = getAnno();

        if (anno != null) {
            titoloPagina = anno.getTitoloLista("Morti ");
            this.getUI().getPage().open(WIKI_URL + titoloPagina, "_blank");
        }// end of if/else cycle

    }// end of method

    /**
     * Esegue l'upload di tutti i record
     * Sovrascritto
     */
    @Override
    protected void esegueUploadAll() {
        Esegue.uploadAnni();
    }// end of method

    /**
     * Esegue l'upload di tutti i record (in ordine inverso)
     * Sovrascritto
     */
    @Override
    protected void esegueUploadAllContrario() {
        new UploadAnni(false);
    }// end of method

    /**
     * Esegue l'upload per la lista dei nati di questo record
     * Sovrascritto
     */
    @Override
    protected void esegueUploadNato() {
        Anno anno = getAnno();

        if (anno != null) {
            new ListaAnnoNato(anno);
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
        Anno anno = getAnno();

        if (anno != null) {
            new ListaAnnoMorto(anno);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle
    }// end of method

    /**
     * Esegue l'upload per la lista dei nati di questo record
     * Esegue l'upload per la lista dei morti di questo record
     */
    protected void esegueUploadBothNatoMorto() {
        Anno anno = getAnno();

        if (anno != null) {
            new ListaAnnoNato(anno);
            new ListaAnnoMorto(anno);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle
    }// end of method

    /**
     * Esegue la creazione delle pagine statistiche
     */
    protected void esegueStatistiche() {
        Esegue.statisticaAnni();
    }// end of method

    /**
     * Recupera la voce selezionata
     */
    private Anno getAnno() {
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



