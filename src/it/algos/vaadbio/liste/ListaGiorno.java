package it.algos.vaadbio.liste;

import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;

/**
 * Created by gac on 21 dic 2015.
 * <p>
 * Crea la lista dei nati nel giorno e la carica sul server wiki
 * Crea la lista dei morti nel giorno e la carica sul server wiki
 */
public abstract class ListaGiorno extends ListaBio {


    /**
     * Costruttore
     *
     * @param giorno di cui creare la lista
     */
    public ListaGiorno(Giorno giorno) {
        super(giorno);
    }// fine del costruttore


    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    @Override
    protected void elaboraTitolo() {
        String titolo = "";
        String tag = getTagTitolo();
        String articolo = "il";
        String articoloBis = "l'";
        Giorno giorno = getGiorno();

        if (giorno != null) {
            titolo = giorno.getTitolo();
        }// fine del blocco if

        if (!titolo.equals("")) {
            if (titolo.startsWith("8") || titolo.startsWith("11")) {
                titolo = tag + articoloBis + titolo;
            } else {
                titolo = tag + articolo + CostBio.SPAZIO + titolo;
            }// fine del blocco if-else
        }// fine del blocco if

        titoloPagina = titolo;
    }// fine del metodo

    /**
     * Piede della pagina
     * Elaborazione base
     */
    protected String elaboraFooter(String categoriaTxt) {
        String text = CostBio.VUOTO;
        String progressivoCategoria = getProgressivoGiorno();

        text += "<noinclude>";
        text += CostBio.A_CAPO;
        text += "{{Portale|biografie}}";
        text += CostBio.A_CAPO;
        text += "[[Categoria:" + categoriaTxt + "| " + progressivoCategoria + "]]";
        text += CostBio.A_CAPO;
        text += "[[Categoria:" + titoloPagina + "| ]]";
        text += CostBio.A_CAPO;
        text += "</noinclude>";

        return text;
    }// fine del metodo


    /**
     * Recupera il singolo Giorno come ordinamento
     * Comprende il 29 febbraio per gli anni bisestili
     */
    private String getProgressivoGiorno() {
        String giornoTxt = CostBio.VUOTO;
        int giornoNumero = 0;
        Giorno giorno = getGiorno();
        String tag = "0";

        if (giorno != null) {
            giornoNumero = giorno.getBisestile();
        }// fine del blocco if

        if (giornoNumero > 0) {
            giornoTxt = CostBio.VUOTO + giornoNumero;
        }// fine del blocco if

        //--completamento a 3 cifre
        if (!giornoTxt.equals(CostBio.VUOTO)) {
            if (giornoTxt.length() == 1) {
                giornoTxt = tag + giornoTxt;
            }// fine del blocco if
            if (giornoTxt.length() == 2) {
                giornoTxt = tag + giornoTxt;
            }// fine del blocco if
        }// fine del blocco if

        return giornoTxt;
    }// fine del metodo


    /**
     * Recupera il tag specifico nati/morti
     * Sovrascritto
     */
    protected String getTagTitolo() {
        return CostBio.VUOTO;
    }// fine del metodo


    public Giorno getGiorno() {
        return (Giorno)getOggetto();
    }// end of getter method

}// fine della classe
