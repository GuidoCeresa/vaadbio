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

    protected LinkedHashMap<Nome, Integer> mappaNomi;

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


    /**
     * La mappa delle biografie arriva non ordinata
     * Occorre riordinare in base agli accenti
     * Sovrascritto
     */
    @Override
    protected void ordinaMappaBiografie() {
        String nomeTxt = "";
        HashMap<String, Nome> mappaTmp = new HashMap<>();
        ArrayList lista = null;

        if (mappaNomi != null) {
            lista = new ArrayList();
            for (Object obj : mappaNomi.keySet()) {
                nomeTxt = ((Nome) obj).getNome();
                lista.add(nomeTxt);
                mappaTmp.put(nomeTxt, (Nome) obj);
            }// end of for cycle
        }// end of if cycle

        Collator usCollator = Collator.getInstance(Locale.US); //Your locale here
        usCollator.setStrength(Collator.PRIMARY); //desired strength
        Collections.sort(lista, usCollator);

//        mappaNomi=new HashMap<>()
        int a = 87;
    }// end of method

    public static String removeAccents(String text) {
        return text == null ? null : Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }// end of method
}// end of class
