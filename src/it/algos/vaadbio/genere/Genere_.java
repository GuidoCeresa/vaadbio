package it.algos.vaadbio.genere;

import it.algos.webbase.web.entity.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Genere.class)
public class Genere_ extends BaseEntity_ {
	public static volatile SingularAttribute<Genere, String> singolare;
	public static volatile SingularAttribute<Genere, String> plurale;
	public static volatile SingularAttribute<Genere, String> sesso;
}// end of entity class
