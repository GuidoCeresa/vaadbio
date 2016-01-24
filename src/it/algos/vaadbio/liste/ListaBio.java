package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

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
    protected LinkedHashMap<String, ArrayList<String>> mappaBiografie;
    protected int numPersone = 0;

    protected boolean usaHeadInclude; // vero per Giorni ed Anni
    protected boolean usaHeadToc;
    protected boolean usaHeadTocIndice;
    protected boolean usaHeadRitorno; // prima del template di avviso
    protected boolean usaHeadTemplateAvviso; // uso del template StatBio
    protected String tagHeadTemplateAvviso; // template 'StatBio'
    protected boolean usaHeadIncipit; // dopo il template di avviso

    protected boolean usaBodySottopagine;
    protected boolean usaBodyRigheMultiple;
    protected boolean usaBodyDoppiaColonna;
    protected boolean usaBodyTemplate;

    //        tagTemplateBio == Pref.getStr(LibBio.NOME_TEMPLATE_AVVISO_LISTE_GIORNI_ANNI, 'ListaBio')
    protected String titoloPaginaMadre = "";
    protected boolean usaFooterPortale;
    protected boolean usaFooterCategorie;
    private Object oggetto;  //Giorno, Anno, Attivita, Nazionalita, Antroponimo, ecc

    /**
     * Costruttore
     */
    public ListaBio(Object oggetto) {
        super();
        this.oggetto = oggetto;
        doInit();
    }// fine del costruttore


    protected void doInit() {
        elaboraParametri();
        elaboraTitolo();

        elaboraMappaBiografie();
        ordinaMappaBiografie();
        elaboraPagina();
    }// fine del metodo

    /**
     * Regola alcuni (eventuali) parametri specifici della sottoclasse
     * <p>
     * Nelle sottoclassi va SEMPRE richiamata la superclasse PRIMA di regolare localmente le variabili <br>
     * Sovrascritto
     */
    protected void elaboraParametri() {
        // head
        usaHeadInclude = true; //--tipicamente sempre true. Si attiva solo se c'è del testo (iniziale) da includere
        usaHeadToc = true; //--tipicamente sempre true.
        usaHeadTocIndice = true; //--normalmente true. Sovrascrivibile da preferenze
        usaHeadRitorno = false; //--normalmente false. Sovrascrivibile da preferenze
        usaHeadTemplateAvviso = true; //--normalmente true. Sovrascrivibile nelle sottoclassi
        tagHeadTemplateAvviso = "StatBio"; //--Sovrascrivibile da preferenze
        usaHeadIncipit = false; //--normalmente false. Sovrascrivibile da preferenze

        // body
        usaBodySottopagine = true; //--normalmente true. Sovrascrivibile nelle sottoclassi
        usaBodyRigheMultiple = false; //--normalmente false. Sovrascrivibile da preferenze
        usaBodyDoppiaColonna = true; //--normalmente true. Sovrascrivibile nelle sottoclassi
        usaBodyTemplate = false; //--normalmente false. Sovrascrivibile nelle sottoclassi

        // footer
        usaFooterPortale = false;
        usaFooterCategorie = false;

//        usaSuddivisioneUomoDonna = false
//        usaSuddivisioneParagrafi = true
//        usaAttivitaMultiple = false
//        usaParagrafiAlfabetici = false
//        usaTitoloParagrafoConLink = true
//        usaTitoloSingoloParagrafo = false
//        usaSottopagine = true
//        maxVociParagrafo = 100
//        tagLivelloParagrafo = '=='
//        tagParagrafoNullo = 'Altre...'
//        usaSottopaginaAltri == Pref.getBool(LibBio.USA_SOTTOPAGINA_ALTRI, false)
    }// fine del metodo


    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    protected void elaboraTitolo() {
    }// fine del metodo

    /**
     * Costruisce una mappa di biografie che hanno una valore valido per il link specifico
     */
    protected void elaboraMappaBiografie() {
        List vettore;
        String queryTxt = getQueryCrono();
//        queryTxt = "select bio.giornoNatoPunta.nome,bio.didascaliaAnnoNato,bio.giornoNatoPunta.bisestile from Bio bio where bio.annoNatoPunta.id=" + 2950 + " order by bio.giornoNatoPunta.bisestile,bio.cognome";
        queryTxt = "select bio.didascaliaAnnoNato from Bio bio where bio.annoNatoPunta.id=" + 2950;
        EntityManager manager = EM.createEntityManager();
        Query query = manager.createQuery(queryTxt);
        Object[] riga;
        String giornoanno;
        String didascalia;
        String didascaliaShort = CostBio.VUOTO;
        ArrayList<String> lista;

        vettore = query.getResultList();
        if (vettore != null) {
            mappaBiografie = new LinkedHashMap<String, ArrayList<String>>();
            for (Object rigaTmp : vettore) {
                if (rigaTmp instanceof Object[]) {
                    riga = (Object[]) rigaTmp;
                    giornoanno = (String) riga[0];
                    didascalia = (String) riga[1];

                    if (didascalia.contains(CostBio.TAG_SEPARATORE)) {
                        didascaliaShort = didascalia.substring(didascalia.indexOf(CostBio.TAG_SEPARATORE) + CostBio.TAG_SEPARATORE.length());
                    } else {
                        didascaliaShort = didascalia;
                    }// end of if/else cycle

                    if (mappaBiografie.containsKey(giornoanno)) {
                        lista = mappaBiografie.get(giornoanno);
                        lista.add(didascaliaShort);
                    } else {
                        lista = new ArrayList<>();
                        lista.add(didascaliaShort);
                        mappaBiografie.put(giornoanno, lista);
                    }// end of if/else cycle
                }// end of if cycle
            }// end of for cycle
            numPersone = vettore.size();
        }// end of if cycle
        manager.close();

    }// fine del metodo


    /**
     * Patch
     * La mappa delle biografie arriva NON ordinata @todo Da sistemare
     *
     * @todo Ordine cognome (e se manca?)
     */
    protected void ordinaMappaBiografie() {
    }// fine del metodo

    /**
     * Costruisce la query specifica per la ricerca della lista biografica
     * Sovrascritto
     */
    protected String getQueryCrono() {
        return CostBio.VUOTO;
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
                titolo = PAGINA_PROVA;
            } else {
                titolo = titoloPagina;
            }// fine del blocco if-else

            if (LibBio.checkModificaSostanzialeCrono(titolo, testo, tagHeadTemplateAvviso)) {
                Api.scriveVoce(titolo, testo, summary);
            }// end of if cycle

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
        boolean usaColonne = this.usaBodyDoppiaColonna;
        int maxRigheColonne = 10;//@todo mettere la preferenza
        boolean usaRaggruppamentoRigheMultiple = this.usaBodyRigheMultiple;

        if (mappaBiografie != null && mappaBiografie.size() > 0) {
            if (usaRaggruppamentoRigheMultiple) {
                text = righeRaggruppate();
            } else {
                text = righeSemplici();
            }// end of if/else cycle
        }// end of if cycle

        if (usaColonne && (numPersone > maxRigheColonne)) {
            text = LibWiki.listaDueColonne(text.trim());
        }// fine del blocco if

        if (usaBodyTemplate) {
            if (Pref.getBool(CostBio.USA_DEBUG, true)) {
                text = elaboraTemplate("") + text;
            } else {
                text = elaboraTemplate(text);
            }// end of if/else cycle
        }// end of if cycle

        return text;
    }// fine del metodo

    /**
     * Raggruppa le didascalie per anno uguale
     */
    protected String righeRaggruppate() {
        String text = CostBio.VUOTO;
        ArrayList<String> lista;

        for (Map.Entry<String, ArrayList<String>> mappa : mappaBiografie.entrySet()) {
            if (mappa.getValue().size() == 1) {
                text += CostBio.ASTERISCO;
                if (!mappa.getKey().equals(CostBio.VUOTO)) {
                    text += LibWiki.setQuadre(mappa.getKey());
                    text += CostBio.TAG_SEPARATORE;
                }// end of if cycle
                text += mappa.getValue().get(0);
                text += CostBio.A_CAPO;
            } else {
                text += CostBio.ASTERISCO;
                text += LibWiki.setQuadre(mappa.getKey());
                text += CostBio.A_CAPO;
                lista = mappa.getValue();
                for (String didascalia : lista) {
                    text += CostBio.ASTERISCO;
                    text += CostBio.ASTERISCO;
                    text += didascalia;
                    text += CostBio.A_CAPO;
                }// end of for cycle
            }// end of if/else cycle

        }// end of for cycle

        return text;
    }// fine del metodo


    /**
     * Nessun raggruppamento
     */
    protected String righeSemplici() {
        String text = CostBio.VUOTO;
        ArrayList<String> lista;

        for (Map.Entry<String, ArrayList<String>> mappa : mappaBiografie.entrySet()) {
            if (mappa.getValue().size() == 1) {
                text += CostBio.ASTERISCO;
                text += LibWiki.setQuadre(mappa.getKey());
                text += CostBio.TAG_SEPARATORE;
                text += mappa.getValue().get(0);
                text += CostBio.A_CAPO;
            } else {
                lista = mappa.getValue();
                for (String didascalia : lista) {
                    text += CostBio.ASTERISCO;
                    text += LibWiki.setQuadre(mappa.getKey());
                    text += CostBio.TAG_SEPARATORE;
                    text += didascalia;
                    text += CostBio.A_CAPO;
                }// end of for cycle
            }// end of if/else cycle

        }// end of for cycle

        return text;
    }// fine del metodo

    /**
     * Trim iniziale
     * <p>
     * Ogni blocco esce trimmato (per l'inizio) e con un solo ritorno a capo per fine riga. <br>
     */
    private String finale(String testoIn) {
        String testoOut = testoIn;

        // trim finale
        if (!testoIn.equals(CostBio.VUOTO)) {
            testoOut = testoIn.trim();
        }// fine del blocco if

        // valore di ritorno
        return testoOut;
    }// fine del metodo

    /**
     * Incapsula il testo come parametro di un (eventuale) template
     * Se non viene incapsulato, restituisce il testo in ingresso
     * Sovrascritto
     */
    protected String elaboraTemplate(String testoIn) {
        return testoIn;
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
     * Costruisce il ritorno alla pagina 'madre'
     * <p>
     * Non sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo e la formulazione <br>
     */
    private String elaboraRitorno() {
        String text = CostBio.VUOTO;
        String titoloPaginaMadre = getTitoloPaginaMadre();

        if (usaHeadRitorno) {
            if (!titoloPaginaMadre.equals(CostBio.VUOTO)) {
                text += "Torna a|" + titoloPaginaMadre;
                text = LibWiki.setGraffe(text);
            }// fine del blocco if
        }// fine del blocco if

        return text;
    }// fine del metodo

    /**
     * Titolo della pagina 'madre'
     * <p>
     * Sovrascritto
     */
    protected String getTitoloPaginaMadre() {
        return titoloPaginaMadre;
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

        if (usaHeadTemplateAvviso) {
            text += tagHeadTemplateAvviso;
            text += "|bio=";
            text += personeTxt;
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
