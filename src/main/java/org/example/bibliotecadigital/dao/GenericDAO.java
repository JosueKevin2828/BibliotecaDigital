package org.example.bibliotecadigital.dao;

import java.util.List;
import java.util.Optional;


// Interfaz generica,  T es el tipo de entidad, ID es el tipo del identificador
public interface GenericDAO<T,  ID>{
    T save(T entity); //guarda
    Optional<T> findById(ID id); //busca por id
    List<T> findAll(); //lista todos
    T update(T entity); // actualiza
    void delete(ID id); // elimina
    boolean existsById(ID id);
    long count(); //cuenta total
}