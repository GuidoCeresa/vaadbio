package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nazionalita.Nazionalita;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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

    public static String PATH = "Progetto:Biografie/Statistiche";

    /**
     * Costruttore completo
     */
    public Sintesi() {
        super();
        doInit();
    }// end of constructor

    /**
     * Corpo della pagina
     * Sovrascritto
     */
    protected String elaboraBody() {
        HashMap mappa = new HashMap();
        String caption="";
        ArrayList lista = new ArrayList();

//        caption = getCaption();

//        lista.add(getRigaVoci());
//        lista.add(getRigaGiorni());
//        lista.add(getRigaAnni());
//        lista.add(getRigaAttivita());
//        lista.add(getRigaNazionalita());
//        lista.add(getRigaAttesa());

        mappa.put(KEY_MAPPA_TITOLI, caption);
        mappa.put(KEY_MAPPA_LISTA, lista);
        mappa.put(KEY_MAPPA_LISTA_SORTABLE, false);

//        return WikiLib.creaTable(mappa);
        return"";
    }// fine del metodo


//    /**
//     * Titoli della tabella di sintesi
//     */
//    private String getCaption() {
//        String titoli=CostBio.VUOTO;
//        String statistiche = "Statistiche";
//        String vecchiaData = Pref.getStr(LibBio.ULTIMA_SINTESI);
//        String nuovaData = LibTime.getGioMeseAnnoLungo(new Date());
//        String differenze = "diff.";
//
//        //valore per le preferenze
//        mappaSintesi.put(LibBio.ULTIMA_SINTESI, nuovaData);
//
//        statistiche = regolaSpazi(statistiche);
//        vecchiaData = regolaSpazi(vecchiaData);
//        nuovaData = regolaSpazi(nuovaData);
//        differenze = regolaSpazi(differenze);
//
//        titoli = [statistiche, vecchiaData, nuovaData, differenze];
//
//        return titoli;
//    }// fine del metodo

//    /**
//     * Riga col numero di voci
//     */
//    private String getRigaVoci() {
//        String descrizione = ":Categoria:BioBot|Template bio";
//        int oldValue = Pref.getInt(LibBio.VOCI);
//        int newValue = BioGrails.count();
//
//        //valore per le preferenze
//        mappaSintesi.put(LibBio.VOCI, newValue);
//
//        descrizione = LibWiki.setQuadre(descrizione);
//        descrizione = LibWiki.setBold(descrizione);
//
//        return getRigaBase(descrizione, oldValue, newValue);
//    }// fine del metodo


//    /**
//     * Riga col numero di giorni
//     */
//    private String getRigaGiorni() {
//        String descrizione = "Giorni interessati";
//        int oldValue = Pref.getInt(LibBio.GIORNI);
//        int newValue = Giorno.count();
//        String nota = "Previsto il [[29 febbraio]] per gli [[Anno bisestile|anni bisestili]]";
//
//        //valore per le preferenze
//        mappaSintesi.put(LibBio.GIORNI, newValue);
//
//        descrizione = LibWiki.setBold(descrizione);
//        nota = SPAZIO + LibWiki.setRef(nota);
//        descrizione += nota;
//
//        return getRigaBase(descrizione, oldValue, newValue);
//    }// fine del metodo

//    /**
//     * Riga col numero di anni
//     */
//    private String getRigaAnni() {
//        int anniPreCristo = 1000;
//        String descrizione = "Anni interessati";
//        int oldValue = Pref.getInt(LibBio.ANNI);
//        int newValue = getAnnoCorrenteNum() + anniPreCristo;
//        String nota = "Potenzialmente dal [[1000 a.C.]] al [[{{CURRENTYEAR}}]]";
//
//        //valore per le preferenze
//        mappaSintesi.put(LibBio.ANNI, newValue);
//
//        descrizione = LibWiki.setBold(descrizione);
//        nota = SPAZIO + LibWiki.setRef(nota);
//        descrizione += nota;
//
//        return getRigaBase(descrizione, oldValue, newValue);
//    }// fine del metodo

//    /**
//     * Riga col numero di attività
//     */
//    private String getRigaAttivita() {
//        String descrizione = PATH + "Attività|Attività utilizzate";
//        int oldValue = Pref.getInt(LibBio.ATTIVITA);
//        int newValue = Attivita.executeQuery("select distinct plurale from Attivita").size();
//
//        //valore per le preferenze
//        mappaSintesi.put(LibBio.ATTIVITA, newValue);
//
//        descrizione = LibWiki.setQuadre(descrizione);
//        descrizione = LibWiki.setBold(descrizione);
//
//        return getRigaBase(descrizione, oldValue, newValue);
//    }// fine del metodo

//    /**
//     * Riga col numero di nazionalità
//     */
//    private String getRigaNazionalita() {
//        String descrizione = PATH + "Nazionalità|Nazionalità utilizzate";
//        int oldValue = Pref.getInt(LibBio.NAZIONALITA);
//        int newValue = Nazionalita.executeQuery("select distinct plurale from Nazionalita").size();
//
//        //valore per le preferenze
//        mappaSintesi.put(LibBio.NAZIONALITA, newValue);
//
//        descrizione = LibWiki.setQuadre(descrizione);
//        descrizione = LibWiki.setBold(descrizione);
//
//        return getRigaBase(descrizione, oldValue, newValue);
//    }// fine del metodo

//    /**
//     * Riga coi giorni di attesa
//     */
//    private String getRigaAttesa() {
//        String descrizione = "Giorni di attesa";
//        int oldValue = Pref.getInt(LibBio.ATTESA);
//        int newValue = NUOVA_ATTESA;
//        String nota = "Giorni di attesa indicativi prima che ogni singola voce venga ricontrollata per registrare eventuali modifiche intervenute nei parametri significativi.";
//
//        //valore per le preferenze
//        mappaSintesi.put(LibBio.ATTESA, newValue);
//
//        descrizione = LibWiki.setBold(descrizione);
//        nota = SPAZIO + LibWiki.setRef(nota);
//        descrizione += nota;
//
//        return getRigaBase(descrizione, oldValue, newValue);
//    }// fine del metodo


}// end of class
