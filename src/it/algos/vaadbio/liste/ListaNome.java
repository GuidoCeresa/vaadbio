package it.algos.vaadbio.liste;

import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.nome.Nome;
import it.algos.webbase.web.lib.LibText;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by gac on 27 dic 2015.
 * Crea la lista delle persone col nome indicato e la carica sul server wiki
 */
public class ListaNome extends ListaBio {


    /**
     * Costruttore
     *
     * @param nome
     */
    public ListaNome(Nome nome) {
        super(nome);
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

        // body
        usaSuddivisioneParagrafi = true;
        usaBodyRigheMultiple = false;
        usaBodyDoppiaColonna = false;

        // footer

    }// fine del metodo

    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    @Override
    protected void elaboraTitolo() {
        String tag = "Persone di nome ";

        titoloPagina = tag + getNomeTxt();
    }// fine del metodo

    /**
     * Costruisce una mappa di biografie che hanno una valore valido per il link specifico
     */
    @Override
    protected void elaboraMappaBiografie() {
        ArrayList<Bio> listaNomi = null;
        Nome nome = getNome();
        Attivita attivita;
        String attivitaText;
        String didascalia;
        int soglia = 1;
        ArrayList<String> lista;

        if (nome != null) {
            listaNomi = nome.bioNome();
        }// end of if cycle

        if (listaNomi != null && listaNomi.size() >= soglia) {
            mappaBiografie = new LinkedHashMap<String, ArrayList<String>>();
            for (Bio bio : listaNomi) {
                attivitaText = CostBio.VUOTO;
                attivita = bio.getAttivitaPunta();
                if (attivita != null) {
                    attivitaText = attivita.getPlurale();
                }// end of if cycle
                if (attivitaText.equals(CostBio.VUOTO)) {
                    attivitaText = bio.getAttivita();
                }// end of if cycle
                attivitaText= LibText.primaMaiuscola(attivitaText);
                didascalia = bio.getDidascaliaListe();

                if (mappaBiografie.containsKey(attivitaText)) {
                    lista = mappaBiografie.get(attivitaText);
                    lista.add(didascalia);
                } else {
                    lista = new ArrayList<>();
                    lista.add(didascalia);
                    mappaBiografie.put(attivitaText, lista);
                }// end of if/else cycle
            }// end of if cycle
        }// end of for cycle
        numPersone = listaNomi.size();
    }// fine del metodo


    public Nome getNome() {
        return (Nome) getOggetto();
    }// end of getter method

    public String getNomeTxt() {
        String nomeTxt = "";
        Nome nome = getNome();

        if (nome != null) {
            nomeTxt = nome.getNome();
        }// end of if cycle

        return nomeTxt;
    }// end of getter method

}// fine della classe
