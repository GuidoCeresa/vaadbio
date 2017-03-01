package it.algos.vaadbio.nome;

import it.algos.vaadbio.liste.ListeTablePortal;
import it.algos.webbase.web.module.ModulePop;

/**
 * Created by gac on 26 nov 2016.
 * Portale specifico per i nomi
 */
public class NomeTablePortal extends ListeTablePortal {

    /**
     * Costruttore base
     */
    public NomeTablePortal(ModulePop modulo) {
        super(modulo);
    }// end of constructor


    @Override
    protected void init() {
        wikiProgetto = "Antroponimi";
        cmdPagina = "Nomi";
        cmdLista = "Liste";
        linkPagina = "Nomi";
        linkLista = "Liste nomi";

        super.init();
    }// end of method

}// end of class
