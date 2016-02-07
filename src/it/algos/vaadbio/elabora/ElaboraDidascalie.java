package it.algos.vaadbio.elabora;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.didascalia.*;


/**
 * Created by gac on 23 dic 2015.
 * Elabora tutte le didascalie della voce
 */
public class ElaboraDidascalie {


    /**
     * Costruttore
     *
     * @param bio istanza da cui estrarre il tmplBioServer originale
     */
    public ElaboraDidascalie(Bio bio) {
        if (bio != null) {
            this.doInit(bio);
        }// end of if cycle
    }// end of constructor


    private void doInit(Bio bio) {
        String didascaliaGiornoNato = new DidascaliaNatiGiorno(bio).getTesto();
        bio.setDidascaliaGiornoNato(didascaliaGiornoNato);

        String didascaliaGiornoMorto = new DidascaliaMortiGiorno(bio).getTesto();
        bio.setDidascaliaGiornoMorto(didascaliaGiornoMorto);

        String didascaliaAnnoNato = new DidascaliaNatiAnno(bio).getTesto();
        bio.setDidascaliaAnnoNato(didascaliaAnnoNato);

        String didascaliaAnnoMorto = new DidascaliaMortiAnno(bio).getTesto();
        bio.setDidascaliaAnnoMorto(didascaliaAnnoMorto);

        String didascaliaListe = new DidascaliaListe(bio).getTesto();
        bio.setDidascaliaListe(didascaliaListe);
    }// end of method

}// end of class
