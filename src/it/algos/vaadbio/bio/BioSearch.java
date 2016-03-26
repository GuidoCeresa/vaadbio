package it.algos.vaadbio.bio;


import com.vaadin.data.Container;
import it.algos.vaad.wiki.entities.wiki.Wiki_;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.search.SearchManager;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class BioSearch extends SearchManager {

    public BioSearch(ModulePop module) {
        super(module);
    }// end of constructor

    @Override
    public ArrayList<Container.Filter> createFilters() {
        ArrayList<Container.Filter> filters = new ArrayList<Container.Filter>();

        filters.add(createStringFilter(Bio_.pageid, SearchManager.SearchType.MATCHES));
        filters.add(createStringFilter(Bio_.nome, SearchType.MATCHES));
        filters.add(createStringFilter(Bio_.cognome, SearchType.MATCHES));
        return filters;
    }// end of method

}// end of class
