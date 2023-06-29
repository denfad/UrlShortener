package ru.denfad.UrlShortener.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.denfad.UrlShortener.model.UrlModel;

public interface UrlRepository extends JpaRepository<UrlModel, Integer> {
}
