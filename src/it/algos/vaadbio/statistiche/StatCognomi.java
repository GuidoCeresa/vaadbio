package it.algos.vaadbio.statistiche;

/**
 * Created by gac on 05 nov 2016.
 * Pagine di controllo del progetto Antroponimi
 * Crea 2 pagine:
 * - Progetto:Antroponimi/Cognomi: Elenco dei xxx cognomi che hanno più di yy ricorrenze nelle voci biografiche
 * - Progetto:Antroponimi/Liste cognomi: Elenco dei xxx cognomi differenti utilizzati nelle yyy voci biografiche con occorrenze maggiori di zz
 */
public class StatCognomi extends Statistiche{
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
        tagPath = PATH_ANTRO;
        usaHeadInclude = false;
        usaHeadTocIndice = false;

        // footer
        usaFooterNote = false;
        usaFooterCorrelate = true;
    }// fine del metodo

}// end of class
