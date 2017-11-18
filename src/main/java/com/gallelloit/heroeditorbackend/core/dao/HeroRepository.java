package com.gallelloit.heroeditorbackend.core.dao;

import java.util.List;

import com.gallelloit.heroeditorbackend.core.dao.doc.HeroDoc;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Standard CRUD repository for user doc + query methods
 * 
 * Note: To use N1QL we should at least create a primary NQ1L index or, to be more specific, a N1QL secondary indexes tailored for queries for better performance
 *
 * @author Charz++
 */

public interface HeroRepository extends CrudRepository<HeroDoc, String> {
	// This method uses an annotation to provide all of the users
	@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter}")
	List<HeroDoc> findAllHeroes();

	@Query("SELECT COUNT(u.id) as c FROM users u WHERE #{#n1ql.filter}")
	Long countHeroes();

	// This method uses the Query annotation to provide a N1QL statement inline. A few N1QL-specific values are provided through SpEL.
	@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND CONTAINS(name, $1)")
	List<HeroDoc> findHeroesByName(String heroName);


	// This method is a query method defined in the interface. In addition to query methods, query derivation for both count and delete queries, is available.
	HeroDoc findByName (String heroName);
}
