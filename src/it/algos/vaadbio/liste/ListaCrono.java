package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
        tagHeadTemplateAvviso = Pref.getString(CostBio.TEMPLATE_AVVISO_CRONO, "ListaBio");

        // body
        usaSuddivisioneParagrafi = false; //--escluso per le voci crono
        usaBodySottopagine = false; //--escluso per le voci crono
        usaBodyRigheMultiple = Pref.getBool(CostBio.USA_BODY_RIGHE_MULTIPLE_CRONO, true);
        usaBodyTemplate = true; //--obbligatorio per le voci crono
        usaSortCronologico = true;

        // footer
        usaFooterPortale = Pref.getBool(CostBio.USA_FOOTER_PORTALE_CRONO, true);
        if (Pref.getBool(CostBio.USA_DEBUG, false)) {
            usaFooterCategorie = false;
        } else {
            usaFooterCategorie = Pref.getBool(CostBio.USA_FOOTER_CATEGORIE_CRONO, true);
        }// end of if/else cycle
    }// fine del metodo


    /**
     * Costruisce una singola mappa
     */
    protected void elaboraMappaOld(Bio bio) {
        String key = getChiave(bio);
        String didascalia = getDidascalia(bio);
        ArrayList<String> lista;
        HashMap<String, Object> mappa;

        if (didascalia.contains(CostBio.TAG_SEPARATORE)) {
            didascalia = didascalia.substring(didascalia.indexOf(CostBio.TAG_SEPARATORE) + CostBio.TAG_SEPARATORE.length());
        }// end of if cycle

        if (mappaBio.containsKey(key)) {
            lista = (ArrayList<String>) mappaBio.get(key).get(KEY_MAP_LISTA);
            lista.add(didascalia);
        } else {
            mappa = new HashMap<String, Object>();
            lista = new ArrayList<>();
            lista.add(didascalia);
            mappa.put(KEY_MAP_LISTA, lista);
            mappaBio.put(key, mappa);
        }// end of if/else cycle

    }// fine del metodo


    /**
     * La mappa delle biografie arriva non ordinata
     * Occorre spostare in basso il paragrafo vuoto
     * Occorre raggruppare i paragrafi con lo stesso link visibile
     * Occorre riordinare in base al link visibile
     * Sovrascritto
     */
    protected void ordinaMappaBiografie() {
        LinkedHashMap<String, HashMap> mappa = new LinkedHashMap<>();
        HashMap<Object, Object> mappaOrd = new HashMap<>();
        HashMap<String, Object> mappaTmp;
        String titoloParagrafo = "";
        ArrayList<Integer> keyList = new ArrayList<>();
        int ord;
        String chiave;
        HashMap value;

        for (Map.Entry<String, HashMap> mappaEntry : mappaBio.entrySet()) {
            mappaTmp = mappaEntry.getValue();
            titoloParagrafo = (String) mappaTmp.get(KEY_MAP_TITOLO);
            ord = getOrdineCrono(mappaTmp);
            keyList.add(ord);
            mappaOrd.put(ord, titoloParagrafo);
        }// end of for cycle

        keyList = LibArray.sort(keyList);
        for (Integer key : keyList) {
            chiave = (String) mappaOrd.get(key);
            value = mappaBio.get(chiave);
            mappa.put(chiave, value);
        }// end of for cycle
        mappaBio = mappa;

    }// fine del metodo


    /**
     * Ordine progressivo del paragrafo (giorno oppure anno)
     * Sovrascritto
     */
    protected int getOrdineCrono(HashMap<String, Object> mappa) {
        return 0;
    }// fine del metodo

    /**
     * Raggruppa le biografie
     */
    protected String righeRaggruppate() {
        String text = CostBio.VUOTO;
        ArrayList<Bio> lista;
        String key;
        HashMap<String, Object> mappa;
        String didascalia;

        for (Map.Entry<String, HashMap> mappaTmp : mappaBio.entrySet()) {
            key = mappaTmp.getKey();
            mappa = (HashMap) mappaTmp.getValue();
            lista = (ArrayList<Bio>) mappa.get(KEY_MAP_LISTA);

            if (lista.size() == 1) {
                text += CostBio.ASTERISCO;
                didascalia = getDidascalia(lista.get(0));
                text += didascalia;
            } else {
                if (!key.equals(CostBio.VUOTO)) {
                    text += CostBio.ASTERISCO;
                    text += LibWiki.setQuadre(key);
                    text += CostBio.A_CAPO;
                }// end of if cycle
                for (Bio bio : lista) {
                    if (!key.equals(CostBio.VUOTO)) {
                        text += CostBio.ASTERISCO;
                    }// end of if cycle
                    text += CostBio.ASTERISCO;
                    didascalia = getDidascalia(bio);
                    didascalia = LibBio.troncaDidascalia(didascalia);
                    text += didascalia;
                    text += CostBio.A_CAPO;
                }// end of for cycle
            }// end of if/else cycle
            text += CostBio.A_CAPO;
        }// end of for cycle

        return text;
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
     * Controlla che la modifica sia sostanziale
     * Se il flag è false, registra sempre
     * Se il flag è vero, controlla la differenza del testo
     * Sovrascritto
     */
    @Override
    protected boolean checkPossoRegistrare(String titolo, String testo) {
        if (Pref.getBool(CostBio.USA_REGISTRA_SEMPRE_CRONO, false)) {
            return true;
        } else {
            return LibBio.checkModificaSostanziale(titolo, testo, tagHeadTemplateAvviso, "}}");
        }// end of if/else cycle
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
            text = CostBio.A_CAPO + LibBio.setNoIncludeMultiRiga(text);
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

