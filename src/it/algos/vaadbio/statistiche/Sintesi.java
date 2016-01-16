package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nazionalita.Nazionalita;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.domain.pref.TypePref;
import it.algos.webbase.web.lib.LibDate;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gac on 14 gen 2016.
 * Crea la tabella ed aggiorna la pagina Progetto:Biografie/Statistiche
 * <p>
 * La pagina di sintesi viene chiamato da DaemonUpload (con frequenza ?)
 * La pagina di sintesi può essere invocata dal bottone 'Sintesi' nella tavola Bio
 * <p>
 * Legge la terza colonna della tabella esistente
 * Recupera i dati per costruire la terza colonna
 * Elabora i dati per costruire la quarta colonna
 */
public class Sintesi extends Statistiche {

    private static HashMap<String, Object> mappaSintesi = new HashMap();
    private static int NUOVA_ATTESA = 5;

    /**
     * Costruttore completo
     */
    public Sintesi() {
        super();
    }// end of constructor

    /**
     * Regola alcuni (eventuali) parametri specifici della sottoclasse
     * <p>
     * Nelle sottoclassi va SEMPRE richiamata la superclasse PRIMA di regolare localmente le variabili <br>
     * Sovrascritto
     */
    @Override
    protected void elaboraParametri() {
        super.elaboraParametri();
        titoloPagina = "Statistiche";
    }// fine del metodo

    /**
     * Corpo della pagina
     * Sovrascritto
     */
    protected String elaboraBody() {
        HashMap<String, Object> mappa = new HashMap<String, Object>();
        ArrayList<String> caption;
        ArrayList lista = new ArrayList();

        caption = getCaption();

        lista.add(getRigaVoci());
        lista.add(getRigaGiorni());
        lista.add(getRigaAnni());
        lista.add(getRigaAttivita());
        lista.add(getRigaNazionalita());
        lista.add(getRigaAttesa());

        mappa.put(KEY_MAPPA_TITOLI, caption);
        mappa.put(KEY_MAPPA_LISTA, lista);
        mappa.put(KEY_MAPPA_LISTA_SORTABLE, false);

        return LibWiki.creaTable(mappa);
    }// fine del metodo


    /**
     * Titoli della tabella di sintesi
     */
    private ArrayList<String> getCaption() {
        ArrayList<String> titoli = new ArrayList<String>();
        String statistiche = "Statistiche";
        Date oldDate = Pref.getData(CostBio.STAT_DATA_ULTIMA_SINTESI, LibTime.adesso());
        String vecchiaData = LibTime.getGioMeseAnnoLungo(oldDate);
        String nuovaData = LibTime.getGioMeseAnnoLungo(new Date());
        String differenze = "diff.";

        //valore per le preferenze
        mappaSintesi.put(CostBio.STAT_DATA_ULTIMA_SINTESI, nuovaData);

        statistiche = regolaSpazi(statistiche);
        vecchiaData = regolaSpazi(vecchiaData);
        nuovaData = regolaSpazi(nuovaData);
        differenze = regolaSpazi(differenze);

        titoli.add(statistiche);
        titoli.add(vecchiaData);
        titoli.add(nuovaData);
        titoli.add(differenze);

        return titoli;
    }// fine del metodo

    /**
     * Riga col numero di voci
     */
    private ArrayList<String> getRigaVoci() {
        String prefCode = CostBio.STAT_NUM_VOCI;
        String descrizione = ":Categoria:BioBot|Template bio";
        int oldValue = Pref.getInt(prefCode, 300000);
        int newValue = Bio.count();
        String nota = "Una differenza di '''poche unità''' tra le pagine della categoria e le voci gestite dal bot è '''fisiologica''' e dovuta ad imprecisioni nella redazione del tmpl Bio";
        boolean wikiLink = true;

        return getRigaBase(wikiLink, descrizione, nota, oldValue, newValue, prefCode);
    }// fine del metodo


    /**
     * Riga col numero di giorni
     */
    private ArrayList<String> getRigaGiorni() {
        String prefCode = CostBio.STAT_NUM_GIORNI;
        String descrizione = "Giorni interessati";
        int oldValue = Pref.getInt(prefCode, 366);
        int newValue = Giorno.count();
        String nota = "Previsto il [[29 febbraio]] per gli [[Anno bisestile|anni bisestili]]";
        boolean wikiLink = false;

        return getRigaBase(wikiLink, descrizione, nota, oldValue, newValue, prefCode);
    }// fine del metodo

    /**
     * Riga col numero di anni
     */
    private ArrayList<String> getRigaAnni() {
        String prefCode = CostBio.STAT_NUM_ANNI;
        int anniPreCristo = 1000;
        String descrizione = "Anni interessati";
        int oldValue = Pref.getInt(prefCode, 3000);
        int newValue = LibDate.getYear() + anniPreCristo;
        String nota = "Potenzialmente dal [[1000 a.C.]] al [[{{CURRENTYEAR}}]]";
        boolean wikiLink = false;

        return getRigaBase(wikiLink, descrizione, nota, oldValue, newValue, prefCode);
    }// fine del metodo

    /**
     * Riga col numero di attività
     */
    private ArrayList<String> getRigaAttivita() {
        String prefCode = CostBio.STAT_NUM_ATTIVITA;
        String descrizione = PATH + "Attività|Attività utilizzate";
        int oldValue = Pref.getInt(prefCode, 600);
        int newValue = Attivita.countDistinct();
        String nota="Le attività sono quelle '''convenzionalmente''' previste dalla comunità ed inserite nell'elenco utilizzato dal template Bio";
        boolean wikiLink = true;

        return getRigaBase(wikiLink, descrizione, nota, oldValue, newValue, prefCode);
    }// fine del metodo

    /**
     * Riga col numero di nazionalità
     */
    private ArrayList<String> getRigaNazionalita() {
        String prefCode = CostBio.STAT_NUM_NAZIONALITA;
        String descrizione = PATH + "Nazionalità|Nazionalità utilizzate";
        int oldValue = Pref.getInt(prefCode, 300);
        int newValue = Nazionalita.countDistinct();
        String nota="Le nazionalità sono quelle '''convenzionalmente''' previste dalla comunità ed inserite nell'elenco utilizzato dal template Bio";
        boolean wikiLink = true;

        return getRigaBase(wikiLink, descrizione, nota, oldValue, newValue, prefCode);
    }// fine del metodo

    /**
     * Riga coi giorni di attesa
     */
    private ArrayList<String> getRigaAttesa() {
        String prefCode = CostBio.STAT_GIORNI_ATTESA;
        String descrizione = "Giorni di attesa";
        int oldValue = Pref.getInt(prefCode, 5);
        int newValue = NUOVA_ATTESA;
        String nota = "Giorni di attesa '''indicativi''' prima che ogni singola voce venga ricontrollata per registrare eventuali modifiche intervenute nei parametri significativi.";
        boolean wikiLink = false;

        return getRigaBase(wikiLink, descrizione, nota, oldValue, newValue, prefCode);
    }// fine del metodo


    /**
     * Riga base
     *
     * @param wikiLink      collegamento a pagina wiki con doppie quadre
     * @param descrizione   della riga
     * @param eventualeNota a piè pagina
     * @param oldValue      stringa del valore precedente
     * @param newValue      stringa del valore attuale
     * @param prefCode      codice della preferenza da aggiornare
     */
    private ArrayList<String> getRigaBase(boolean wikiLink, String descrizione, String eventualeNota, int oldValue, int newValue, String prefCode) {
        ArrayList<String> riga = new ArrayList<String>();
        int differenza = newValue - oldValue;

        if (differenza == 0) {
//            differenza ="";
        }// fine del blocco if

        if (wikiLink) {
            descrizione = LibWiki.setQuadre(descrizione);
        }// end of if cycle

        descrizione = LibWiki.setBold(descrizione);
        if (!eventualeNota.equals(CostBio.VUOTO)) {
            eventualeNota = CostBio.SPAZIO + LibBio.setRef(eventualeNota);
            descrizione += eventualeNota;
        }// end of if cycle

        riga.add(descrizione);
        riga.add(LibNum.format(oldValue));
        riga.add(LibNum.format(newValue));
        riga.add(LibNum.format(differenza));

        //valore per le preferenze
        mappaSintesi.put(prefCode, newValue);

        return riga;
    }// fine del metodo


    /**
     * Registra nelle preferenze i nuovi valori che diventeranno i vecchi per la prossima sintesi
     * Sovrascritto
     */
    @Override
    protected void elaboraPreferenze() {
        Pref pref;
        String chiave;
        Object valore;

        for (Map.Entry mappa : mappaSintesi.entrySet()) {
            chiave = (String) mappa.getKey();
            valore = mappa.getValue();
            pref = Pref.findByCode(chiave);
            registraPreferenza(pref, chiave, valore);
        }// end of for cycle

        mappaSintesi.clear();
    }// fine del metodo

    /**
     * Registra la singola preferenza
     * Sovrascritto
     */
    private void registraPreferenza(Pref pref, String chiave, Object valore) {
        TypePref type;

        if (pref != null) {
            type = pref.getType();

            switch (type) {
                case booleano:
                    if (valore instanceof Boolean) {
                        pref.setBool((Boolean) valore);
                    }// fine del blocco if
                    break;
                case intero:
                    if (valore instanceof Integer) {
                        pref.setIntero((Integer) valore);
                    }// fine del blocco if
                    break;
                case stringa:
                    if (valore instanceof String) {
                        pref.setStringa((String) valore);
                    }// fine del blocco if
                    break;
                case data:
                    if (valore instanceof Date) {
                        pref.setData((Date) valore);
                    }// fine del blocco if
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

            pref.save();
        }// fine del blocco if

    }// fine del metodo

}// end of class
