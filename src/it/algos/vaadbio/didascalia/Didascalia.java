package it.algos.vaadbio.didascalia;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;

/**
 * Created by gac on 23 dic 2015.
 * <p>
 * Didascalia specializzata per le liste costruibili a partire dal template Bio.
 * Cronologiche (in namespace principale) di nati e morti nel giorno o nell'anno
 * Attività e nazionalità (in Progetto:Biografie).
 * <p>
 * Sovrascritta nelle sottoclassi concrete
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
    protected String localitaNato = CostBio.VUOTO;
    protected String localitaMorto = CostBio.VUOTO;
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

        if (bio.getAttivitaPunta() != null) {
            this.attivita = bio.getAttivitaPunta().getSingolare();
//            this.attivitaLink = bio.attivitaLink
//            this.attivita = this.attivitaLink.singolare
//            this.attivitaPlurale = this.attivitaLink.plurale
        }// fine del blocco if

        if (bio.getAttivita2Punta() != null) {
            this.attivita2 = bio.getAttivita2Punta().getSingolare();
        }// fine del blocco if

        if (bio.getAttivita3Punta() != null) {
            this.attivita3 = bio.getAttivita3Punta().getSingolare();
        }// fine del blocco if

        if (bio.getNazionalitaPunta() != null) {
            this.nazionalita = bio.getNazionalitaPunta().getSingolare();
        }// fine del blocco if

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
        testo += this.getAttNaz();

        // blocco finale (potrebbe non esserci)
        testo += this.getBloccoFinale();

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
        String nomeCognome = CostBio.VUOTO;
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

    /**
     * Costruisce la stringa attività e nazionalità della didascalia
     * <p>
     * I collegamenti alle tavole Attività e Nazionalità, potrebbero esistere nella biografia
     * anche se successivamente i corrispondenti records (di Attività e Nazionalità) sono stati cancellati
     * Occorre quindi proteggere il codice dall'errore (dovuto ad un NON aggiornamento dei dati della biografia)
     *
     * @return testo
     */
    protected String getAttNaz() {
        String attNazDidascalia = CostBio.VUOTO;
        String attivita = this.attivita;
        String attivita2 = this.attivita2;
        String attivita3 = this.attivita3;
        String nazionalita = this.nazionalita;
        String tagAnd = CostBio.SPAZIO + "e" + CostBio.SPAZIO;
        String tagSpa = CostBio.SPAZIO;
        String tagVir = "," + CostBio.SPAZIO;
        boolean virgolaDopoPrincipale = false;
        boolean andDopoPrincipale = false;
        boolean andDopoSecondaria = false;

        // la virgolaDopoPrincipale c'è se è presente la seconda attività e la terza
        if (!attivita2.equals(CostBio.VUOTO) && !attivita3.equals(CostBio.VUOTO)) {
            virgolaDopoPrincipale = true;
        }// fine del blocco if

        // la andDopoPrincipale c'è se è presente la seconda attività e non la terza
        if (!attivita2.equals(CostBio.VUOTO) && attivita3.equals(CostBio.VUOTO)) {
            andDopoPrincipale = true;
        }// fine del blocco if

        // la andDopoSecondaria c'è se è presente terza attività
        if (!attivita3.equals(CostBio.VUOTO)) {
            andDopoSecondaria = true;
        }// fine del blocco if

        // attività principale
        if (!attivita.equals(CostBio.VUOTO)) {
            attNazDidascalia += attivita;
        }// fine del blocco if

        // virgola
        if (virgolaDopoPrincipale) {
            attNazDidascalia += tagVir;
        }// fine del blocco if

        // and
        if (andDopoPrincipale) {
            attNazDidascalia += tagAnd;
        }// fine del blocco if

        // attività secondaria
        if (!attivita2.equals(CostBio.VUOTO)) {
            attNazDidascalia += attivita2;
        }// fine del blocco if

        // and
        if (andDopoSecondaria) {
            attNazDidascalia += tagAnd;
        }// fine del blocco if

        // attività terziaria
        if (!attivita3.equals(CostBio.VUOTO)) {
            attNazDidascalia += attivita3;
        }// fine del blocco if

        // nazionalità facoltativo
        if (!nazionalita.equals(CostBio.VUOTO)) {
            attNazDidascalia += tagSpa;
            attNazDidascalia += nazionalita;
        }// fine del blocco if

        if (!attNazDidascalia.equals(CostBio.VUOTO)) {
            attNazDidascalia = tagVir + attNazDidascalia + tagSpa;
        }// fine del blocco if

        // valore di ritorno
        return attNazDidascalia.trim();
    }// end of getter method

    /**
     * Costruisce il blocco finale (potrebbe non esserci)
     * Sovrascritto
     *
     * @return testo
     */
    protected String getBloccoFinale() {
        String text = CostBio.VUOTO;
        String annoNascita = this.annoNascita;
        String annoMorte = this.annoMorte;
        String luogoNascita = this.localitaNato;
        String luogoNascitaLink = CostBio.VUOTO;
        String luogoMorte = this.localitaMorto;
        String luogoMorteLink = CostBio.VUOTO;
        String tagParIni = CostBio.SPAZIO + "(";
        String tagParEnd = ")";
        String tagParVir = "," + CostBio.SPAZIO;
        String tagParMezzo = CostBio.SPAZIO + "-" + CostBio.SPAZIO;
        boolean parentesi = false;
        boolean trattino = false;
        boolean simboli = false;
        boolean virgolaNato = false;
        boolean virgolaMorto = false;
        String tagAnnoNato = CostBio.PUNTI;
        String tagAnnoMorto = CostBio.PUNTI;

        //patch @todo da controllare
        //se il luogo di nascita (mancante) è indicato con 3 puntini (car 8230), li elimino
        if (luogoNascita.length() == 1) {
            luogoNascita = CostBio.VUOTO;
        }// fine del blocco if

        //    //se non c'è ne anno ne luogo di nascita, metto i puntini
        //    //se non c'è ne anno ne luogo di morte, metto i puntini
        //    //se manca tutto non visualizzo nemmeno i puntini
        //    if (tipoDidascalia == DidascaliaTipo.estesa || tipoDidascalia == DidascaliaTipo.estesaSimboli) {
        //        if (!luogoNascita && !annoNascita) {
        //            annoNascita = tagAnnoNato
        //        }// fine del blocco if
        //        //if (!luogoMorte && !annoMorte) {
        //        //    annoMorte = tagAnnoMorto
        //        //}// fine del blocco if
        //        if (annoNascita.equals(tagAnnoNato) && annoMorte.equals(tagAnnoNato)) {
        //            annoNascita = ''
        //            annoMorte = ''
        //        }// fine del blocco if
        //    }// fine del blocco if

        // se manca l'anno di nascita
        // metto i puntini SOLO se esiste l'anno di morte @todo controllare
        if (annoNascita.equals(CostBio.VUOTO) && !annoMorte.equals(CostBio.VUOTO)) {
            annoNascita = tagAnnoNato;
        }// fine del blocco if

        // la parentesi c'è se anche solo uno dei dati è presente
        if (!luogoNascita.equals(CostBio.VUOTO) || !annoNascita.equals(CostBio.VUOTO) || !luogoMorte.equals(CostBio.VUOTO) || !annoMorte.equals(CostBio.VUOTO)) {
            parentesi = true;
        }// fine del blocco if

        // il trattino c'è se è presente un dato della nascita (anno o luogo) ed uno della morte (anno o luogo)
        if (!luogoNascita.equals(CostBio.VUOTO) && !annoNascita.equals(CostBio.VUOTO) && !luogoMorte.equals(CostBio.VUOTO) || !annoMorte.equals(CostBio.VUOTO)) {
            trattino = true;
        }// fine del blocco if

        // i simboli ci sono se c'è solo una data
        if (!annoNascita.equals(CostBio.VUOTO) || !annoMorte.equals(CostBio.VUOTO)) {
            simboli = true;
        }// fine del blocco if

        // la virgolaNato c'è se solo se entrambi i dati di nascita sono presenti
        if (!luogoNascita.equals(CostBio.VUOTO) && !annoNascita.equals(CostBio.VUOTO)) {
            virgolaNato = true;
        }// fine del blocco if

        // la virgolaMorto c'è se solo se entrambi i dati di morte sono presenti
        if (!luogoMorte.equals(CostBio.VUOTO) && !annoMorte.equals(CostBio.VUOTO)) {
            virgolaMorto = true;
        }// fine del blocco if

        // costruisce il blocco finale (potrebbe non esserci)
        if (!annoMorte.equals(CostBio.VUOTO)) {
            text = tagParIni + CostBio.TAG_MORTO_CRONO;
            text += LibWiki.setQuadre(annoMorte);
            text += tagParEnd;
        }// fine del blocco if

        if (text.equals(tagParIni + tagParEnd)) {
            text = CostBio.VUOTO;
        }// fine del blocco if

        return text;
    }// end of getter method


    public String getTesto() {
        return testo;
    }// end of getter method


}// end of class
