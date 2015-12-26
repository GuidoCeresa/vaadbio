package it.algos.vaadbio.anno;


import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Notification;
import it.algos.vaadbio.annogiorno.AnnoGiornoMod;
import it.algos.vaadbio.liste.ListaAnnoMorto;
import it.algos.vaadbio.liste.ListaAnnoNato;
import it.algos.webbase.web.table.ATable;

import javax.persistence.metamodel.Attribute;

/**
 * Gestione (minimale) del modulo
 * Passa alla superclasse il nome e la classe specifica
 */
@SuppressWarnings("serial")
public class AnnoMod extends AnnoGiornoMod {


    /**
     * Costruttore senza parametri
     * <p/>
     * Invoca la superclasse passando i parametri:
     * (obbligatorio) la Entity specifica
     * (facoltativo) etichetta del menu (se manca usa il nome della Entity)
     * (facoltativo) icona del menu (se manca usa un'icona standard)
     */
    public AnnoMod() {
        super(Anno.class, FontAwesome.LIST_UL);
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
     * Messaggio del dialogo di avviso/conferma
     * Sovrascritto
     */
    @Override
    protected String getMsg() {
        return "Esegue un ciclo (<b><span style=\"color:green\">lista</span></b>) per la creazione di 3030 pagine di nati e di morti per ogni anno</br>";
    }// end of method

    /**
     * Esegue l'upload per la lista dei nati
     * Sovrascritto
     */
    @Override
    protected void esegueUploadNati() {
        Anno anno = getAnno();

        if (anno != null) {
            new ListaAnnoNato(anno);
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
     * Sovrascritto
     */
    @Override
    protected void esegueUploadBoth() {
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



