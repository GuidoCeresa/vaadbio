package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibTime;

import java.util.Date;

/**
 * Created by gac on 14 gen 2016.
 * .
 */
public abstract class Statistiche {

    public final static String A_CAPO = "\n";
    //    protected static String PATH = "Progetto:Biografie/";
    protected static String PATH_BIO = "Progetto:Biografie/";
    protected static String PATH_NOMI = "Progetto:Antroponimi/";
        protected static String DISCUSSIONI = "Discussioni ";
    protected static String TAG_INDICE = "__FORCETOC__";
    protected static String TAG_NO_INDICE = "__NOTOC__";
    protected static String PAGINA_PROVA = "Utente:Biobot/2";
    protected static boolean usaSpazi;
    protected String titoloPagina;
    protected String tagPath;
    protected boolean usaHeadToc;
    protected boolean usaHeadTocIndice;
    protected boolean usaHeadInclude; // vero per Sintesi
    protected boolean usaHeadTemplateAvviso; // uso del template StatBio
    protected String tagHeadTemplateAvviso; // template 'StatBio'
    protected boolean usaHeadRitorno; // prima del template di avviso
    protected boolean usaFooterNote;
    protected boolean usaFooterCorrelate;

    /**
     * Costruttore completo
     */
    public Statistiche() {
        super();
        doInit();
    }// end of constructor


    /**
     */
    public void doInit() {
        elaboraParametri();
        elaboraMappaBiografie();
        elaboraPagina();
        elaboraPreferenze();
    }// end of method

    /**
     * Regola alcuni (eventuali) parametri specifici della sottoclasse
     * <p>
     * Nelle sottoclassi va SEMPRE richiamata la superclasse PRIMA di regolare localmente le variabili <br>
     * Sovrascritto
     */
    protected void elaboraParametri() {
        // head
        usaHeadToc = true; //--tipicamente sempre true.
        usaHeadTocIndice = true; //--normalmente true. Sovrascrivibile da preferenze
        usaHeadInclude = true; //--tipicamente sempre true. Si attiva solo se c'è del testo (iniziale) da includere
        usaHeadRitorno = false; //--normalmente false. Sovrascrivibile da preferenze
        usaHeadTemplateAvviso = true; //--normalmente true. Sovrascrivibile nelle sottoclassi
        tagHeadTemplateAvviso = "StatBio"; //--Sovrascrivibile da preferenze
        tagPath = PATH_BIO;

        // body
        usaSpazi = true;

        // footer
        usaFooterNote = true;
        usaFooterCorrelate = false;
    }// fine del metodo

    /**
     * Costruisce una mappa di valori varii
     * Sovrascritto
     */
    protected void elaboraMappaBiografie() {
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
        String titolo;

        //header
        testo += this.elaboraHead();

        //body
        //a capo, ma senza senza righe di separazione
        testo += CostBio.A_CAPO;
        testo += this.elaboraBody();

        //footer
        //di fila nella stessa riga, senza ritorno a capo (se inizia con <include>)
        testo += CostBio.A_CAPO;
        testo += this.elaboraFooter();

        //registra la pagina
        if (!testo.equals(CostBio.VUOTO)) {
            testo = testo.trim();

            if (debug) {
                titolo = PAGINA_PROVA;
                testo = titoloPagina + CostBio.A_CAPO + testo;
            } else {
                titolo = tagPath + titoloPagina;
            }// fine del blocco if-else

            Api.scriveVoce(titolo, testo, summary);
        }// fine del blocco if

    }// fine del metodo


    /**
     * Costruisce il testo iniziale della pagina (header)
     * <p>
     * Non sovrascrivibile <br>
     * Ogni blocco esce trimmato (per l'inizio) e con un solo ritorno a capo per fine riga. <br>
     * Eventuali spazi gestiti da chi usa il metodo <br>
     */
    private String elaboraHead() {
        String text = CostBio.VUOTO;

        // Posiziona il TOC
        text += elaboraTOC();

        // Posiziona il ritorno
        text += elaboraRitorno();

        // Posizione il template di avviso
        text += elaboraTemplateAvviso();

        // Ritorno ed avviso vanno (eventualmente) protetti con 'include'
        text = elaboraInclude(text);

        return text;
    }// fine del metodo


    /**
     * Costruisce il TOC (tavola contenuti)
     * <p>
     * Non sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo di una delle due possibilità <br>
     */
    private String elaboraTOC() {
        String text = CostBio.VUOTO;

        if (usaHeadToc) {
            if (usaHeadTocIndice) {
                text += TAG_INDICE;
            } else {
                text += TAG_NO_INDICE;
            }// fine del blocco if-else
        }// end of if cycle

        return text;
    }// fine del metodo

    /**
     * Corpo della pagina
     * Sovrascritto
     */
    protected String elaboraBody() {
        return CostBio.VUOTO;
    }// fine del metodo


    /**
     * Costruisce il ritorno alla pagina 'madre'
     * <p>
     * Non sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo e la formulazione <br>
     */
    private String elaboraRitorno() {
        String text = CostBio.VUOTO;

        if (usaHeadRitorno) {
            if (!titoloPagina.equals(CostBio.VUOTO)) {
                text += "Torna a|" + titoloPagina;
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

        if (usaHeadTemplateAvviso) {
            text += tagHeadTemplateAvviso;
            text += "|data=";
            text += dataCorrente.trim();
            text = LibWiki.setGraffe(text);
        }// end of if cycle

        return text;
    }// fine del metodo

    /**
     * Incorpora il testo iniziale nel tag 'include'
     * <p>
     * Non sovrascrivibile <br>
     * Tipicamente sempre true. Si attiva solo se c'è del testo (iniziale) da includere
     */
    private String elaboraInclude(String testoIn) {
        String testoOut = testoIn;

        if (usaHeadInclude) {
            testoOut = LibBio.setNoInclude(testoIn);
        }// fine del blocco if

        return testoOut;
    }// fine del metodo

    /**
     * Eventuali spazi vuoti prima e dopo il testo
     * Vale per tutte le righe
     */
    protected String regolaSpazi(String testoIn) {
        String testoOut = testoIn;

        if (!testoOut.equals(CostBio.VUOTO)) {
            if (usaSpazi) {
                testoOut = CostBio.SPAZIO + testoIn + CostBio.SPAZIO;
            }// fine del blocco if
        }// fine del blocco if

        return testoOut;
    }// fine del metodo


    /**
     * Registra nelle preferenze i nuovi valori che diventeranno i vecchi per la prossima sintesi
     * Sovrascritto
     */
    protected void elaboraPreferenze() {
    }// fine del metodo


    /**
     * Costruisce il testo finale della pagina
     * Aggiungere (di solito) inizialmente la chiamata al metodo elaboraFooter <br>
     * Sovrascritto
     */
    protected String elaboraFooter() {
        String text = CostBio.VUOTO;

        // Inizio del footer
        // Note
        text += elaboraFooterNote();

        // Voci correlate
        text += elaboraFooterCorrelate();

        // Categorie del footer
        text += elaboraFooterCategorie();

        return text;
    }// fine del metodo


    /**
     * Inizio del footer
     * Sovrascritto
     */
    protected String elaboraFooterNote() {
        String text = CostBio.VUOTO;

        if (usaFooterNote) {
            text += "==Note==";
            text += A_CAPO;
            text += "<references/>";
            text += A_CAPO;
        }// end of if cycle

        return text;
    }// fine del metodo

    /**
     * Corpo del footer
     * Sovrascritto
     */
    protected String elaboraFooterCorrelate() {
        String text = CostBio.VUOTO;

        if (usaFooterCorrelate) {
            text += "{{BioCorrelate}}";
            text += A_CAPO;
        }// end of if cycle

        return text;
    }// fine del metodo

    /**
     * Categorie del footer
     * Sovrascritto
     */
    protected String elaboraFooterCategorie() {
        return A_CAPO + LibBio.setNoInclude("[[Categoria:Progetto Biografie|{{PAGENAME}}]]");
    }// fine del metodo

}// end of class
