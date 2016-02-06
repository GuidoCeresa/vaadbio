package it.algos.vaadbio.cognome;

import it.algos.webbase.web.entity.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Cognome.class)
public class Cognome_ extends BaseEntity_ {
	public static volatile SingularAttribute<Cognome, String> cognome;
	public static volatile SingularAttribute<Cognome, Boolean> principale;
	public static volatile SingularAttribute<Cognome, Cognome> riferimento;
}// end of entity class
