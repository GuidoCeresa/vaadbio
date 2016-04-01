package it.algos.vaadbio.liste;

import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;

/**
 * Created by gac on 27 dic 2015.
 * <p>
 * Crea la lista cronologica
 * <p>
 * Crea la lista dei nati nel giorno e la carica sul server wiki
 * Crea la lista dei morti nel giorno e la carica sul server wiki
 * Crea la lista dei nati o dei morti nell'anno e la carica sul server wiki
 * Crea la lista dei morti nell'anno e la carica sul server wiki
 */
public abstract class ListaCrono extends ListaBio {


    /**
     * Costruttore
     */
    public ListaCrono(Object oggetto) {
        super(oggetto);
    }// fine del costruttore

    /**
     * Regola alcuni (eventuali) parametri specifici della sottoclasse
     * <p>
     * Nelle sottoclassi va SEMPRE richiamata la superclasse PRIMA di regolare localmente le variabili <br>
     * Sovrascritto
     */
    @Override
    protected void elaboraParametri() {
        super.elaboraParametri();

        // head
        usaHeadTocIndice = Pref.getBool(CostBio.USA_TOC_INDICE_CRONO, false);
        usaHeadRitorno = Pref.getBool(CostBio.USA_RITORNO_CRONO, true);
        tagHeadTemplateAvviso = Pref.getStr(CostBio.TEMPLATE_AVVISO_CRONO, "ListaBio");

        // body
        usaSuddivisioneParagrafi = false; //--escluso per le voci crono
        usaBodySottopagine = false; //--escluso per le voci crono
        usaBodyRigheMultiple = Pref.getBool(CostBio.USA_BODY_RIGHE_MULTIPLE_CRONO, true);
        usaBodyTemplate = true; //--obbligatorio per le voci crono

        // footer
        usaFooterPortale = Pref.getBool(CostBio.USA_FOOTER_PORTALE_CRONO, true);
        usaFooterCategorie = Pref.getBool(CostBio.USA_FOOTER_CATEGORIE_CRONO, true);

    }// fine del metodo

    /**
     * Incapsula il testo come parametro di un (eventuale) template
     * Se non viene incapsulato, restituisce il testo in ingresso
     * Sovrascritto
     */
    @Override
    protected String elaboraTemplate(String testoIn) {
        return testoIn;
    }// fine del metodo


    /**
     * Incapsula il testo come parametro di un (eventuale) template
     */
    protected String elaboraTemplate(String testoBody, String titoloTemplate) {
        String testoOut = testoBody;
        String testoIni = CostBio.VUOTO;
        String testoEnd = "}}";

        testoIni += "{{" + titoloTemplate;
        testoIni += CostBio.A_CAPO;
        testoIni += "|titolo=" + titoloPagina;
        testoIni += CostBio.A_CAPO;
        testoIni += "|voci=" + numPersone;
        testoIni += CostBio.A_CAPO;
        testoIni += "|testo=";
        testoIni += CostBio.A_CAPO;

        if (!testoBody.equals(CostBio.VUOTO)) {
            testoOut = testoIni + testoBody + testoEnd;
        }// fine del blocco if

        return testoOut;
    }// fine del metodo

    /**
     * Piede della pagina
     * <p>
     * Sovrascritto
     */
    @Override
    protected String elaboraFooter() {
        String text = CostBio.VUOTO;
        String progressivoCategoria = getProgressivoCategoria();
        String testoCategoria = getTestoCategoria();
        boolean usaInclude = usaFooterPortale || usaFooterCategorie;

        if (usaInclude) {
            text += "<noinclude>";
        }// end of if cycle

        if (usaFooterPortale) {
            text += CostBio.A_CAPO;
            text += "{{Portale|biografie}}";
        }// end of if cycle

        if (usaFooterCategorie) {
            text += CostBio.A_CAPO;
            text += "[[Categoria:" + testoCategoria + "| " + progressivoCategoria + "]]";
            text += CostBio.A_CAPO;
            text += "[[Categoria:" + titoloPagina + "| ]]";
        }// end of if cycle

        if (usaInclude) {
            text += CostBio.A_CAPO;
            text += "</noinclude>";
        }// end of if cycle

        return text;
    }// fine del metodo

    /**
     * Categoria specifica da inserire a piede pagina
     * <p>
     * Sovrascritto
     */
    protected String getTestoCategoria() {
        return CostBio.VUOTO;
    }// fine del metodo


    /**
     * Recupera il singolo Giorno come ordinamento
     * Comprende il 29 febbraio per gli anni bisestili
     * Sovrascritto
     */
    protected String getProgressivoCategoria() {
        return CostBio.VUOTO;
    }// fine del metodo


}// fine della classe

