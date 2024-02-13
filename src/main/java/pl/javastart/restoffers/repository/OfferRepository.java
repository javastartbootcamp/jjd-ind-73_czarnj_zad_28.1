package pl.javastart.restoffers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.javastart.restoffers.entity.Offer;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByTitleContainingIgnoreCase(String title);
}
