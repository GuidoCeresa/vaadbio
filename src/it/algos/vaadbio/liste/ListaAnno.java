package it.algos.vaadbio.liste;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.web.lib.Secolo;

/**
 * Created by gac on 21 dic 2015.
 * <p>
 * Crea la lista dei nati nell'anno e la carica sul server wiki
 * Crea la lista dei morti nell'anno e la carica sul server wiki
 */
public abstract class ListaAnno extends ListaCrono {

    /**
     * Costruttore
     *
     * @param anno di cui creare la lista
     */
    public ListaAnno(Anno anno) {
        super(anno);
    }// fine del costruttore


    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    @Override
    protected void elaboraTitolo() {
        String titolo = "";
        Anno anno = getAnno();
        String tag = getTagTitolo();
        String articolo = "nel";
        String articoloBis = "nell'";
        String tagAC = CostBio.SPAZIO + Secolo.TAG_AC;

        if (anno != null) {
            titolo = anno.getNome();
        }// fine del blocco if

        if (!titolo.equals(CostBio.VUOTO)) {
            if (titolo.equals("1")
                    || titolo.equals("1" + Secolo.TAG_AC)
                    || titolo.equals("11")
                    || titolo.equals("11" + Secolo.TAG_AC)
                    || titolo.startsWith("8")
                    ) {
                titolo = tag + articoloBis + titolo;
            } else {
                titolo = tag + articolo + CostBio.SPAZIO + titolo;
            }// fine del blocco if-else
        }// fine del blocco if

        titoloPagina = titolo;
    }// fine del metodo

    /**
     * Titolo della pagina 'madre'
     * <p>
     * Sovrascritto
     */
    @Override
    protected String getTitoloPaginaMadre() {
        String titolo = CostBio.VUOTO;
        Anno anno = this.getAnno();

        if (anno != null) {
            titolo += anno.getNome();
        }// fine del blocco if

        return titolo;
    }// fine del metodo


    /**
     * Incapsula il testo come parametro di un (eventuale) template
     * Sovrascritto
     */
    @Override
    protected String elaboraTemplate(String testoIn) {
        return elaboraTemplate(testoIn, "Lista persone per anno");
    }// fine del metodo


    /**
     * Recupera il singolo Anno come progressivo dall'inizio
     */
    @Override
    protected String getProgressivoCategoria() {
        int annoProgressivo = 0;
        Anno anno = getAnno();

        if (anno != null) {
            annoProgressivo = anno.getOrdinamento();
        }// fine del blocco if

        return CostBio.VUOTO + annoProgressivo;
    }// fine del metodo

    /**
     * Recupera il tag specifico nati/morti
     * Sovrascritto
     */
    protected String getTagTitolo() {
        return CostBio.VUOTO;
    }// fine del metodo


    public Anno getAnno() {
        return (Anno) getOggetto();
    }// end of getter method

}// fine della classe
