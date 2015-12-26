package it.algos.vaadbio.giorno;


import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Notification;
import it.algos.vaadbio.annogiorno.AnnoGiornoMod;
import it.algos.vaadbio.liste.ListaGiornoMorto;
import it.algos.vaadbio.liste.ListaGiornoNato;
import it.algos.webbase.web.table.ATable;

/**
 * Gestione (minimale) del modulo
 */
@SuppressWarnings("serial")
public class GiornoMod extends AnnoGiornoMod {

    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Giorno";


    /**
     * Costruttore senza parametri
     * <p/>
     * Invoca la superclasse passando i parametri:
     * (obbligatorio) la Entity specifica
     * (facoltativo) etichetta del menu (se manca usa il nome della Entity)
     * (facoltativo) icona del menu (se manca usa un'icona standard)
     */
    public GiornoMod() {
        super(Giorno.class, FontAwesome.LIST_UL);
    }// end of constructor


    /**
     * Messaggio del dialogo di avviso/conferma
     * Sovrascritto
     */
    @Override
    protected String getMsg() {
        return "Esegue un ciclo (<b><span style=\"color:green\">lista</span></b>) per la creazione di 366+366 pagine di nati e di morti per ogni giorno dell'anno</br>";
    }// end of method

    /**
     * Esegue l'upload per la lista dei nati
     * Sovrascritto
     */
    @Override
    protected void esegueUploadNati() {
        Giorno giorno = getGiorno();

        if (giorno != null) {
            new ListaGiornoNato(giorno);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle
    }// end of method

    /**
     * Esegue l'upload per la lista dei morti
     * Sovrascritto
     */
    @Override
    protected void esegueUploadMorti() {
        Giorno giorno = getGiorno();

        if (giorno != null) {
            new ListaGiornoMorto(giorno);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle
    }// end of method

    /**
     * Esegue l'upload per la lista dei nati
     * Esegue l'upload per la lista dei morti
     * Sovrascritto
     */
    @Override
    protected void esegueUploadBoth() {
        Giorno giorno = getGiorno();

        if (giorno != null) {
            new ListaGiornoNato(giorno);
            new ListaGiornoMorto(giorno);
        } else {
            Notification.show("Devi selezionare una riga per creare la lista su wikipedia");
        }// end of if/else cycle
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

