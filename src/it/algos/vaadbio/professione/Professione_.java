package it.algos.vaadbio.professione;

import it.algos.webbase.web.entity.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Professione.class)
public class Professione_ extends BaseEntity_ {
	public static volatile SingularAttribute<Professione, String> singolare;
	public static volatile SingularAttribute<Professione, String> pagina;
}// end of entity class
