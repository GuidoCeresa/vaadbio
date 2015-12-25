package it.algos.vaadbio.didascalia;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;

/**
 * Created by gac on 23 dic 2015.
 * .
 */
public abstract class Didascalia {


    protected String nome = CostBio.VUOTO;
    protected String cognome = CostBio.VUOTO;
    protected String titolo = CostBio.VUOTO;
    protected String forzaOrdinamento = CostBio.VUOTO;
    protected String giornoMeseNascita = CostBio.VUOTO;
    protected String annoNascita = CostBio.VUOTO;
    protected String giornoMeseMorte = CostBio.VUOTO;
    protected String annoMorte = CostBio.VUOTO;
    protected String attivita = CostBio.VUOTO;
//    private String localitaNato = CostBio.VUOTO;
//    private String localitaMorto = CostBio.VUOTO;
    protected String attivita2 = CostBio.VUOTO;
    protected String attivita3 = CostBio.VUOTO;
    protected String nazionalita = CostBio.VUOTO;
    private String testo = "";

//    private Giorno giornoNatoPunta = null;
//    private Giorno giornoMortoPunta = null;
//    private Anno annoNatoPunta = null;
//    private Anno annoMortoPunta = null;

    /**
     * Costruttore
     *
     * @param bio istanza da cui costruire la didascalia specifica
     */
    public Didascalia(Bio bio) {
        super();
        if (bio != null) {
            this.doInit(bio);
        }// end of if cycle
    }// end of constructor

    private void doInit(Bio bio) {

        this.recuperaDatiAnagrafici(bio);
        this.recuperaDatiCrono(bio);
        this.recuperaDatiLocalita(bio);
        this.recuperaDatiAttNaz(bio);

//        this.regolaOrdineAlfabetico();
//        this.regolaOrdineCronologico();
        this.regolaDidascalia();
    }// end of method


    /**
     * Recupera dal record di biografia i valori anagrafici
     */
    private void recuperaDatiAnagrafici(Bio bio) {

        if (bio.getNome() != null) {
            this.nome = bio.getNome();
        }// fine del blocco if

        if (bio.getCognome() != null) {
            this.cognome = bio.getCognome();
        }// fine del blocco if

        if (bio.getTitle() != null) {
            this.titolo = bio.getTitle();
        }// fine del blocco if

        if (bio.getNome() != null) {
            this.nome = bio.getNome();
        }// fine del blocco if
    }// end of method


    /**
     * Recupera dal record di biografia i valori cronografici
     */
    private void recuperaDatiCrono(Bio bio) {

        if (bio.getGiornoNatoPunta() != null) {
            this.giornoMeseNascita = bio.getGiornoNatoPunta().getTitolo();
        }// fine del blocco if

        if (bio.getGiornoMortoPunta() != null) {
            this.giornoMeseMorte = bio.getGiornoMortoPunta().getTitolo();
        }// fine del blocco if

        if (bio.getAnnoNatoPunta() != null) {
            this.annoNascita = bio.getAnnoNatoPunta().getNome();
        }// fine del blocco if

        if (bio.getAnnoMortoPunta() != null) {
            this.annoMorte = bio.getAnnoMortoPunta().getNome();
        }// fine del blocco if
    }// end of method


    /**
     * Recupera dal record di biografia i valori delle località
     */
    private void recuperaDatiLocalita(Bio bio) {
//            String prefix = '<!'
//            String localitaNato
//            String localitaMorto
//
//            try { // prova ad eseguire il codice
//                localitaNato = bio.localitaNato
//                if (localitaNato) {
//                    if (localitaNato.contains(prefix)) {
//                        localitaNato = localitaNato.substring(0, localitaNato.indexOf(prefix))
//                    }// fine del blocco if
//                    this.localitaNato = localitaNato
//                }// fine del blocco if
//            } catch (Exception unErrore) { // intercetta l'errore
//                if (USA_WARN) {
//                    log.warn 'manca luogoNascita'
//                }// fine del blocco if
//            }// fine del blocco try-catch
//
//            try { // prova ad eseguire il codice
//                localitaMorto = bio.localitaMorto
//                if (localitaMorto) {
//                    if (localitaMorto.contains(prefix)) {
//                        localitaMorto = localitaMorto.substring(0, localitaMorto.indexOf(prefix))
//                    }// fine del blocco if
//                }// fine del blocco if
//                this.localitaMorto = localitaMorto
//            } catch (Exception unErrore) { // intercetta l'errore
//                if (USA_WARN) {
//                    log.warn 'manca luogoMorte'
//                }// fine del blocco if
//            }// fine del blocco try-catch
    }// end of method


    /**
     * Recupera dal record di biografia i valori delle attività e della nazionalità
     */
    private void recuperaDatiAttNaz(Bio bio) {

//        if (bio.getA) {
//            this.attivitaLink = bio.attivitaLink
//            this.attivita = this.attivitaLink.singolare
//            this.attivitaPlurale = this.attivitaLink.plurale
//        }// fine del blocco if
//
//        try { // prova ad eseguire il codice
//            if (bio.attivitaLink) {
//                this.attivitaLink = bio.attivitaLink
//                this.attivita = this.attivitaLink.singolare
//                this.attivitaPlurale = this.attivitaLink.plurale
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            if (USA_WARN) {
//                log.warn 'manca attivitaLink'
//            }// fine del blocco if
//        }// fine del blocco try-catch
//
//        try { // prova ad eseguire il codice
//            if (bio.attivita2Link) {
//                this.attivita2Link = bio.attivita2Link
//                this.attivita2 = this.attivita2Link.singolare
//                this.attivita2Plurale = this.attivita2Link.plurale
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            if (USA_WARN) {
//                log.warn 'manca attivita2Link'
//            }// fine del blocco if
//        }// fine del blocco try-catch
//
//        try { // prova ad eseguire il codice
//            if (bio.attivita3Link) {
//                this.attivita3Link = bio.attivita3Link
//                this.attivita3 = this.attivita3Link.singolare
//                this.attivita3Plurale = this.attivita3Link.plurale
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            if (USA_WARN) {
//                log.warn 'manca attivita3Link'
//            }// fine del blocco if
//        }// fine del blocco try-catch
//
//        try { // prova ad eseguire il codice
//            if (bio.nazionalitaLink) {
//                this.nazionalitaLink = bio.nazionalitaLink
//                this.nazionalita = this.nazionalitaLink.singolare
//                this.nazionalitaPlurale = this.nazionalitaLink.plurale
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            if (USA_WARN) {
//                log.warn 'manca nazionalitaLink'
//            }// fine del blocco if
//        }// fine del blocco try-catch

    }// end of method

    /**
     * Costruisce il testo della didascalia
     */
    public void regolaDidascalia() {
        testo = CostBio.VUOTO;

        // blocco iniziale (potrebbe non esserci)
        testo += getBloccoIniziale();

        // titolo e nome (obbligatori)
        testo += this.getNomeCognome();

        // attivitaNazionalita (potrebbe non esserci)
//        testo += this.getAttNaz();

        // blocco finale (potrebbe non esserci)
//        testo += this.getBloccoFinale();

    }// end of method

    /**
     * Costruisce il blocco iniziale (potrebbe non esserci)
     * Sovrascritto
     */
    protected String getBloccoIniziale() {
        return CostBio.VUOTO;
    }// end of method


    /**
     * Costruisce il nome e cognome (obbligatori)
     * Si usa il titolo della voce direttamente, se non contiene parentesi
     */
    protected String getNomeCognome() {
        String nomeCognome;
        String titoloVoce;
        String tagPar = "(";
        String tagPipe = "|";
        String nomePrimaDellaParentesi;
        titoloVoce = this.titolo;
//        boolean usaNomeCognomePerTitolo = Pref.getBool(LibBio.US, false);
        boolean usaNomeCognomePerTitolo = false;

        if (usaNomeCognomePerTitolo) {
            nomeCognome = this.nome + CostBio.SPAZIO + this.cognome;
            if (!nomeCognome.equals(titoloVoce)) {
                nomeCognome = titoloVoce + tagPipe + nomeCognome;
            }// fine del blocco if
        } else {
            // se il titolo NON contiene la parentesi, il nome non va messo perché coincide col titolo della voce
            if (titoloVoce.contains(tagPar)) {
                nomePrimaDellaParentesi = titoloVoce.substring(0, titoloVoce.indexOf(tagPar));
                nomeCognome = titoloVoce + tagPipe + nomePrimaDellaParentesi;
            } else {
                nomeCognome = titoloVoce;
            }// fine del blocco if-else
        }// fine del blocco if-else

        nomeCognome = nomeCognome.trim();
        nomeCognome = LibWiki.setQuadre(nomeCognome);

        return nomeCognome;
    }// end of method


    public String getTesto() {
        return testo;
    }// end of getter method


}// end of class
