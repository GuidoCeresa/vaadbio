package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;

/**
 * Created by gac on 28 gen 2016.
 * Crea una tabella esemplificativa di didascalie cronografiche (giorni ed anni).
 */
public class StatDidascalieCrono extends Statistiche {


    /**
     * Costruttore completo
     */
    public StatDidascalieCrono() {
        super();
    }// end of constructor


    /**
     * Regola alcuni (eventuali) parametri specifici della sottoclasse
     * <p>
     * Nelle sottoclassi va SEMPRE richiamata la superclasse PRIMA di regolare localmente le variabili <br>
     * Sovrascritto
     */
    @Override
    protected void elaboraParametri() {
        super.elaboraParametri();
        titoloPagina = "Didascalie";
        usaHeadTocIndice = false;
        usaFooterNote = false;
        usaFooterCorrelate = true;
    }// fine del metodo


    /**
     * Corpo della pagina
     * Sovrascritto
     */
    protected String elaboraBody() {
        String text = CostBio.VUOTO;
        String ref1 = "Attualmente il '''[[Utente:Biobot|<span style=\"color:green;\">bot</span>]]''' usa il tipo '''8''' (estesa con simboli)";
        ref1 = LibWiki.setRef(ref1);
        Bio bio;
        String didascaliaGiornoNato = "";
        String didascaliaGiornoMorto = "";
        String didascaliaAnnoNato = "";
        String didascaliaAnnoMorto = "";
        String tagBR = "<br>";

        bio = Bio.find(234567);
        didascaliaGiornoNato = bio.getDidascaliaGiornoNato();
        didascaliaGiornoMorto = bio.getDidascaliaGiornoMorto();
        didascaliaAnnoNato = bio.getDidascaliaAnnoNato();
        didascaliaAnnoMorto = bio.getDidascaliaAnnoMorto();

        text += A_CAPO;
        text += "==Didascalie==";
        text += A_CAPO;
        text += A_CAPO;
        text += "Pagina di servizio per il '''controllo'''";
//        text += ref1;
        text += " delle didascalie utilizzate nelle pagine delle liste di giorni ed anni.";
        text += " Le didascalie sono di quattro tipi:";
        text += A_CAPO;
        text += "* Nella pagina con la lista dei [[Nati il 18 agosto]]";
        text += tagBR;
        text += LibWiki.setBold(didascaliaGiornoNato);
        text += A_CAPO;
        text += A_CAPO;
        text += "* Nella pagina con la lista dei [[Morti il 20 giugno]]";
        text += tagBR;
        text += LibWiki.setBold(didascaliaGiornoMorto);
        text += A_CAPO;
        text += A_CAPO;
        text += "* Nella pagina con la lista dei [[Nati nel 1943]]";
        text += tagBR;
        text += LibWiki.setBold(didascaliaAnnoNato);
        text += A_CAPO;
        text += A_CAPO;
        text += "* Nella pagina con la lista dei [[Morti nel 2010]]";
        text += tagBR;
        text += LibWiki.setBold(didascaliaAnnoMorto);
        text += A_CAPO;

        return text;
    }// fine del metodo

}// end of class
