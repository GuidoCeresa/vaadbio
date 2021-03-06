package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.Cost;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nazionalita.Nazionalita;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.domain.pref.PrefType;
import it.algos.webbase.web.lib.LibDate;
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
public class StatSintesi extends Statistiche {

    private static HashMap<String, Object> mappaSintesi = new HashMap();
//    private static int NUOVA_ATTESA = 4;

    /**
     * Costruttore completo
     */
    public StatSintesi() {
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
        usaHeadTocIndice = false;
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

        mappa.put(Cost.KEY_MAPPA_TITOLI, caption);
        mappa.put(Cost.KEY_MAPPA_RIGHE_LISTA, lista);
        mappa.put(Cost.KEY_MAPPA_SORTABLE_BOOLEAN, false);

        return LibWiki.creaTable(mappa);
    }// fine del metodo


    /**
     * Titoli della tabella di sintesi
     */
    private ArrayList<String> getCaption() {
        ArrayList<String> titoli = new ArrayList<String>();
        String statistiche = "Statistiche";
        Date oldDate = Pref.getDate(CostBio.STAT_DATA_ULTIMA_SINTESI, LibTime.adesso());
        String vecchiaData = LibTime.getGioMeseAnnoLungo(oldDate);
        String nuovaData = LibTime.getGioMeseAnnoLungo(new Date());
        String differenze = "&nbsp;&nbsp;&nbsp;&nbsp;Δ";

        //valore per le preferenze
        mappaSintesi.put(CostBio.STAT_DATA_ULTIMA_SINTESI, new Date());

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
    private ArrayList getRigaVoci() {
        String prefCode = CostBio.STAT_NUM_VOCI;
        String descrizione = ":Categoria:BioBot|Template bio";
        int oldValue = Pref.getInt(prefCode, 300000);
        int newValue = Bio.count();
        String nota = "Una differenza di '''poche unità''' tra le pagine della categoria e le voci gestite dal bot è '''fisiologica''' e dovuta ad imprecisioni nella redazione del tmpl Bio";

        return getRigaBase(true, descrizione, nota, oldValue, newValue, prefCode);
    }// fine del metodo


    /**
     * Riga col numero di giorni
     */
    private ArrayList getRigaGiorni() {
        String prefCode = CostBio.STAT_NUM_GIORNI;
        String descrizione = tagPath + "Giorni|Giorni interessati";
        int oldValue = Pref.getInt(prefCode, 366);
        int newValue = Giorno.count();
        String nota = "Previsto il [[29 febbraio]] per gli [[Anno bisestile|anni bisestili]]";

        return getRigaBase(true, descrizione, nota, oldValue, newValue, prefCode);
    }// fine del metodo

    /**
     * Riga col numero di anni
     */
    private ArrayList getRigaAnni() {
        String prefCode = CostBio.STAT_NUM_ANNI;
        int anniPreCristo = 1000;
        String descrizione = tagPath + "Anni|Anni interessati";
        int oldValue = Pref.getInt(prefCode, 3000);
        int newValue = LibDate.getYear() + anniPreCristo;
        String nota = "Potenzialmente dal [[1000 a.C.]] al [[{{CURRENTYEAR}}]]";

        return getRigaBase(true, descrizione, nota, oldValue, newValue, prefCode);
    }// fine del metodo

    /**
     * Riga col numero di attività
     */
    private ArrayList getRigaAttivita() {
        String prefCode = CostBio.STAT_NUM_ATTIVITA;
        String descrizione = tagPath + "Attività|Attività utilizzate";
        int oldValue = Pref.getInt(prefCode, 600);
        int newValue = Attivita.countDistinct();
        String nota = "Le attività sono quelle '''convenzionalmente''' previste dalla comunità ed inserite nell'elenco utilizzato dal template Bio";

        return getRigaBase(true, descrizione, nota, oldValue, newValue, prefCode);
    }// fine del metodo

    /**
     * Riga col numero di nazionalità
     */
    private ArrayList getRigaNazionalita() {
        String prefCode = CostBio.STAT_NUM_NAZIONALITA;
        String descrizione = tagPath + "Nazionalità|Nazionalità utilizzate";
        int oldValue = Pref.getInt(prefCode, 300);
        int newValue = Nazionalita.countDistinct();
        String nota = "Le nazionalità sono quelle '''convenzionalmente''' previste dalla comunità ed inserite nell'elenco utilizzato dal template Bio";

        return getRigaBase(true, descrizione, nota, oldValue, newValue, prefCode);
    }// fine del metodo

    /**
     * Riga coi giorni di attesa
     */
    private ArrayList getRigaAttesa() {
        String prefCode = CostBio.STAT_GIORNI_ATTESA;
        String descrizione = "Giorni di attesa";
        int oldValue = Pref.getInt(prefCode, 5);
        int newValue = oldValue;
        String nota = "Giorni di attesa '''indicativi''' prima che ogni singola voce venga ricontrollata per registrare eventuali modifiche intervenute nei parametri significativi.";

        return getRigaBase(false, descrizione, nota, oldValue, newValue, prefCode);
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
    private ArrayList getRigaBase(boolean wikiLink, String descrizione, String eventualeNota, int oldValue, int newValue, String prefCode) {
        ArrayList riga = new ArrayList();
        int diff = newValue - oldValue;

        if (wikiLink) {
            descrizione = LibWiki.setQuadre(descrizione);
        }// end of if cycle

        descrizione = LibWiki.setBold(descrizione);
        if (!eventualeNota.equals(CostBio.VUOTO)) {
            eventualeNota = CostBio.SPAZIO + LibBio.setRef(eventualeNota);
            descrizione += eventualeNota;
        }// end of if cycle

        riga.add(descrizione);
        riga.add(oldValue);
        riga.add(newValue);
        if (diff != 0) {
            riga.add(diff);
        } else {
            riga.add(CostBio.SPAZIO);
        }// end of if/else cycle

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
            pref.setValore(valore);
            pref.save();
        }// end of for cycle

        mappaSintesi.clear();
    }// fine del metodo


}// end of class
