package it.algos.vaadbio.statistiche;

import it.algos.vaadbio.nome.Nome;
import it.algos.webbase.web.lib.LibArray;

import java.text.Collator;
import java.text.Normalizer;
import java.util.*;

/**
 * Created by gac on 17 apr 2016.
 * Pagine di controllo del progetto Antroponimi
 * Crea 3 pagine:
 * - Progetto:Antroponimi/Nomi: Elenco dei xxx nomi che hanno più di yyy ricorrenze nelle voci biografiche
 * - Progetto:Antroponimi/Liste nomi: Elenco dei xxx nomi differenti utilizzati nelle yyy voci biografiche con occorrenze maggiori di zzz
 * - Progetto:Antroponimi/Didascalie: Pagina di servizio per il controllo delle didascalie utilizzate nelle Liste di persone di nome...
 */
public abstract class StatNomi extends Statistiche {

    protected LinkedHashMap<String, Integer> mappaNomi;

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
