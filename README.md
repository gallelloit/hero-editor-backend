# Hero Editor Backend (Spring Data Couchbase Rest API)

## Previous considerations

To understand this project, a previous one has to be set on the table: Angular2 Hero Editor tutorial.

https://angular.io/guide/quickstart

That is the quick start example that Angular2 proposes to understand the fundamentals of the framework. The resulting project is a visual CRUD module of heroes with just an ID and a name as the main Hero object.

As it's explained in the tutorial, HTTP communication is simulated using browser local data storage and leaves for developer imagination how that would be with a real Rest API server.

What I do in this Hero Editor backend project is to implement the Rest API that the Angular2 web client from the tutorial consumes.

## Architecture
 
 1. **Controller:** is the presentation layer where the end points are located
 2. **Service:** is the service layer where the business logic resides
 3. **Repository:** is the persistence layer where the CRUD repository is located
 
## Technologies

1. Spring Boot (spring-boot-starter-web, spring-boot-starter-tomcat, spring-boot-starter-test, spring-boot-starter-data-couchbase)
2. Java 8
3. Tomcat 9.0.1
4. Couchbase 5.0.0
5. Maven 3.5.2

## Unit Tests

 1. **For the Controller:** it uses the Spring Boot Test framework with mockMvc and hamcrest matchers
 2. **For the Service:** it uses the Mockito framework with hamcrest matchers and mock and injectMocks annotations 
 
## Exposed methods

**1. Get hero by id. HTTP Method: GET**
```
http://localhost:8080/heroeditorbackend/heroes/1
```

**2. Create a hero. HTTP Method: POST**
```
http://localhost:8080/heroeditorbackend/heroes
```
```
{
  "name": "Superman"
}
```

**3. Update a hero. HTTP Method: PUT**
```
http://localhost:8080/heroeditorbackend/heroes/1
```
```
{
  "name": "Super-Man"
}
```

**4. Delete a hero. HTTP Method: DELETE**
```
http://localhost:8080/heroeditorbackend/heroes/1
```

**5. Find hero by name. HTTP Method: GET**
```
http://localhost:8080/heroeditorbackend/heroes/findByName/{heroName}
```

**6. Find all heroes. HTTP Method: GET**
```
http://localhost:8080/heroeditorbackend/heroes/findAll
```

## Considerations about couchbase
 
 * To create a primary index on the bucket.
 ```
 CREATE PRIMARY INDEX `hero-primary-index` ON `hero` USING GSI;
 ```
 * To delete the primary index of the bucket.
 ```
 DROP INDEX `hero`.`hero-primary-index` USING GSI;
 ```
 * In the HeroDoc.java (@Document) we can annotate the key (@id) to be part of the json as well using @Field.
 * In the HeroRepository, the CrudRepository provides sophisticated CRUD functionality for the entity class that is being managed.

## Documentation and Examples
 
* [Couchbase CRUD Repository documentation](http://docs.spring.io/spring-data/couchbase/docs/current/reference/html/#repositories.core-concepts): There you will find core concepts.
* [Spring Data and Couchbase](https://blog.couchbase.com/spring-data-couchbase-2-is-out-quick-getting-started-with-spring-initializr/): There you will find more considerations when working with spring data and couchbase.
* [Introduction to spring data and couchbase](http://www.baeldung.com/spring-data-couchbase): There you will find an introduction example.
* [Couchbase CRUD Excample](https://blog.couchbase.com/vaadin-couchbase-crud-sample/): There you will find a CRUD example.

## About me
I am Pedro Gallello Bonino, with main dedication: Software Project Manager at Indra based in Badajoz, Spain.
[Not so] spare time dedication: full stack open source contributor.
24x7 learner.
24x7 children raiser.

"All parts should go together without forcing. You must remember that the parts you are reassembling were disassembled by you. Therefore, if you can't get them together again, there must be a reason. By all means, do not use a hammer." -- IBM maintenance manual, 1975

* [Twitter](https://twitter.com/GallelloIT)
* [Stackoverflow](https://stackoverflow.com/users/882150/elpiter)
* [LinkedIn](https://www.linkedin.com/in/pedrogallello/)

_**Any improvement or comment about the project is always welcome! As well as others shared their code publicly I want to share mine! Thanks!**_

_**Thanks to Carlos Becerra (carlosCharz) whose springdatacouchbase project was the seed for this one**_

## License
```javas
Copyright 2017 Pedro Gallello Bonino

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
