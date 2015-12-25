package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gac on 21 dic 2015.
 * .
 */
public abstract class ListaBio {

    public static String PAGINA_PROVA = "Utente:Biobot/2";
    protected static String TAG_INDICE = "__FORCETOC__";
    protected static String TAG_NO_INDICE = "__NOTOC__";

    protected String titoloPagina;
    protected ArrayList<String> listaBiografie;
    protected int numPersone = 0;

    protected boolean usaTavolaContenuti = true; //@todo mettere la preferenza
//    protected boolean usaTavolaContenuti = Pref.getBool(LibBio.USA_TAVOLA_CONTENUTI, true)

    protected boolean usaHeadRitorno = true; // prima del template di avviso
    protected String tagTemplateBio = "ListaBio"; // in alternativa 'StatBio'
    protected boolean usaInclude = true; // vero per Giorni ed Anni
    protected boolean usaHeadIncipit = true; // dopo il template di avviso
    protected String titoloPaginaMadre = "";
    private Object oggetto;  //Giorno, Anno, Attivita, Nazionalita, Antroponimo, ecc

    /**
     * Costruttore
     */
    public ListaBio(Object oggetto) {
        super();
        this.oggetto = oggetto;
        doInit();
    }// fine del costruttore

    /**
     * Trim iniziale
     * <p>
     * Ogni blocco esce trimmato (per l'inizio) e con un solo ritorno a capo per fine riga. <br>
     */
    private static String finale(String testoIn) {
        String testoOut = testoIn;

        // trim finale
        if (!testoIn.equals(CostBio.VUOTO)) {
            testoOut = testoIn.trim();
        }// fine del blocco if

        // valore di ritorno
        return testoOut;
    }// fine del metodo

    protected void doInit() {
//        numDidascalie = 0
//        elaboraParametri()
        elaboraTitolo();

        elaboraListaBiografie();
        elaboraPagina();
    }// fine del metodo

    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    protected void elaboraTitolo() {
    }// fine del metodo

    /**
     * Costruisce una lista di biografie che hanno una valore valido per il link specifico
     * Sovrascritto
     */
    protected void elaboraListaBiografie() {
        if (listaBiografie != null) {
            numPersone = listaBiografie.size();
        }// fine del blocco if
    }// fine del metodo

    /**
     * Elaborazione principale della pagina
     * <p>
     * Costruisce head <br>
     * Costruisce body <br>
     * Costruisce footer <br>
     * Ogni blocco esce trimmato (inizio e fine) <br>
     * Gli spazi (righe) di separazione vanno aggiunti qui <br>
     * Registra la pagina <br>
     */
    protected void elaboraPagina() {
        boolean debug = Pref.getBool(CostBio.USA_DEBUG, false);
        String summary = LibWiki.getSummary();
        String testo = CostBio.VUOTO;
//        EditBio paginaModificata
//        Risultato risultato

        if (numPersone > 0) {
            //header
            testo += this.elaboraHead();

            //body
            //a capo, ma senza senza righe di separazione
            testo += CostBio.A_CAPO;
            testo += this.elaboraBody();

            //footer
            //di fila nella stessa riga, senza ritorno a capo (se inizia con <include>)
            testo += this.elaboraFooter();
        }// fine del blocco if

        //registra la pagina
        if (!testo.equals(CostBio.VUOTO)) {
            testo = testo.trim();
            if (debug) {
                testo = titoloPagina + CostBio.A_CAPO + CostBio.A_CAPO + testo;
                Api.scriveVoce(PAGINA_PROVA, testo);
//                testo = LibWiki.setBold(titoloPagina) + CostBio.A_CAPO + testo;
//                paginaModificata = new EditBio(PAGINA_PROVA, testo, summary);
//                registrata = paginaModificata.registrata;
            } else {
//                paginaModificata = new EditBio(titoloPagina, testo, summary);
//                registrata = paginaModificata.registrata;
            }// fine del blocco if-else
        }// fine del blocco if

    }// fine del metodo

    /**
     * Costruisce il testo iniziale della pagina (header)
     * <p>
     * Non sovrascrivibile <br>
     * Posiziona il TOC <br>
     * Posiziona il ritorno (eventuale) <br>
     * Posizione il template di avviso <br>
     * Posiziona l'incipit della pagina (eventuale) <br>
     * Ritorno ed avviso vanno (eventualmente) protetti con 'include' <br>
     * Ogni blocco esce trimmato (per l'inizio) e con un solo ritorno a capo per fine riga. <br>
     * Eventuali spazi gestiti da chi usa il metodo <br>
     */
    private String elaboraHead() {
        String text = CostBio.VUOTO;
        String testoIncluso = CostBio.VUOTO;

        // Posiziona il TOC
        testoIncluso += elaboraTOC();

        // Posiziona il ritorno
        testoIncluso += elaboraRitorno();

        // Posizione il template di avviso
        testoIncluso += elaboraTemplateAvviso();

        // Ritorno ed avviso vanno (eventualmente) protetti con 'include'
        text += elaboraInclude(testoIncluso);

        // Posiziona l'incipit della pagina
        text += elaboraIncipit();

        // valore di ritorno
        return finale(text);
    }// fine del metodo

    /**
     * Corpo della pagina
     * Decide se c'è la doppia colonna
     * Controlla eventuali template di rinvio
     * Sovrascritto
     */
    protected String elaboraBody() {
        String text = CostBio.VUOTO;

//        boolean usaColonne = usaDoppiaColonna
//        int maxRigheColonne = Pref.getInt(LibBio.MAX_RIGHE_COLONNE)
//        testo = elaboraBodyDidascalie()
//
//        if (usaColonne && (numPersone > maxRigheColonne)) {
//            testo = WikiLib.listaDueColonne(testo.trim())
//        }// fine del blocco if
//
//        testo = elaboraTemplate(testo)

        if (listaBiografie != null && listaBiografie.size() > 0) {
            for (String riga : listaBiografie) {
                text += "*";
                text += riga;
                text += CostBio.A_CAPO;
            }// end of for cycle
        }// end of if cycle

        return text;
    }// fine del metodo

    /**
     * Piede della pagina
     * <p>
     * Aggiungere (di solito) inizialmente la chiamata al metodo elaboraFooterSpazioIniziale <br>
     * Sovrascritto
     */
    protected String elaboraFooter() {
        return CostBio.VUOTO;
    }// fine del metodo

    /**
     * Costruisce il TOC (tavola contenuti)
     * <p>
     * Non sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo di una delle due possibilità <br>
     */
    private String elaboraTOC() {
        String text = CostBio.VUOTO;

        if (usaTavolaContenuti) {
            text += TAG_INDICE;
        } else {
            text += TAG_NO_INDICE;
        }// fine del blocco if-else

        return text;
    }// fine del metodo

    /**
     * Costruisce il ritorno alla pagina 'madre'
     * <p>
     * Sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo e la formulazione <br>
     */
    protected String elaboraRitorno() {
        String text = CostBio.VUOTO;

        if (usaHeadRitorno) {
            if (!titoloPaginaMadre.equals(CostBio.VUOTO)) {
                text += "Torna a|" + titoloPaginaMadre;
                text = LibWiki.setGraffe(text);
            }// fine del blocco if
        }// fine del blocco if

        return text;
    }// fine del metodo

    /**
     * Costruisce il template di avviso
     * <p>
     * Non sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) il nome del template da usare <br>
     */
    private String elaboraTemplateAvviso() {
        String text = CostBio.VUOTO;
        String dataCorrente = LibTime.getGioMeseAnnoLungo(new Date());
        String personeTxt = LibNum.format(numPersone);

        text += tagTemplateBio;
        text += "|bio=";
        text += personeTxt;
        text += "|data=";
        text += dataCorrente.trim();
        text = LibWiki.setGraffe(text);

        return text;
    }// fine del metodo

    /**
     * Incorpora ilo testo nel tag 'include'
     * <p>
     * Non sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo <br>
     */
    private String elaboraInclude(String testoIn) {
        String testoOut = testoIn;

        if (usaInclude) {
            testoOut = LibBio.setNoInclude(testoIn);
        }// fine del blocco if

        return testoOut;
    }// fine del metodo

    /**
     * Costruisce la frase di incipit iniziale
     * <p>
     * Non sovrascrivibile <br>
     */
    private String elaboraIncipit() {
        String text = CostBio.VUOTO;

        if (usaHeadIncipit) {
            text += elaboraIncipitSpecifico();
        }// fine del blocco if

        return text;
    }// fine del metodo

    /**
     * Costruisce la frase di incipit iniziale
     * <p>
     * Sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo e la formulazione <br>
     */
    protected String elaboraIncipitSpecifico() {
        return CostBio.VUOTO;
    }// fine del metodo

    protected String getTitoloPagina() {
        return titoloPagina;
    }// end of getter method

    protected ArrayList<String> getListaBiografie() {
        return listaBiografie;
    }// end of getter method

    protected int getNumPersone() {
        return numPersone;
    }// end of getter

    protected Object getOggetto() {
        return oggetto;
    }// end of getter method

}// fine della classe
