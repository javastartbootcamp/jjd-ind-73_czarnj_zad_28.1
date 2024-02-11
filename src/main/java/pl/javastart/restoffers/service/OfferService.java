package pl.javastart.restoffers.service;

import org.springframework.stereotype.Service;
import pl.javastart.restoffers.dto.ReadOfferDto;
import pl.javastart.restoffers.dto.SaveOfferDto;
import pl.javastart.restoffers.entity.Category;
import pl.javastart.restoffers.entity.Offer;
import pl.javastart.restoffers.repository.OfferRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private OfferRepository offerRepository;
    private CategoryService categoryService;

    public OfferService(OfferRepository offerRepository, CategoryService categoryService) {
        this.offerRepository = offerRepository;
        this.categoryService = categoryService;
    }

    public List<ReadOfferDto> getAllOffers() {
        List<Offer> offers = offerRepository.findAll();
        return offers.stream()
                .map(OfferService::mapToReadOfferDto)
                .toList();
    }

    public Long getOffersAmount() {
        return offerRepository.count();
    }

    public List<ReadOfferDto> getAllWithTitle(String title) {
        List<Offer> offers = offerRepository.findByTitleContainingIgnoreCase(title);
        return offers.stream()
                .map(OfferService::mapToReadOfferDto)
                .toList();
    }

    private static ReadOfferDto mapToReadOfferDto(Offer offer) {
        return new ReadOfferDto(offer.getId(), offer.getTitle(), offer.getDescription(), offer.getImgUrl(),
                offer.getPrice(), offer.getCategory().getName());
    }

    public ReadOfferDto saveOffer(SaveOfferDto offer) {
        Category category = categoryService.findByName(offer.category());
        if (category == null) {
            return null;
        }
        Offer offerToSave = mapToOffer(offer, category);
        offerToSave = offerRepository.save(offerToSave);
        return mapToReadOfferDto(offerToSave);
    }

    private static Offer mapToOffer(SaveOfferDto saveOffer, Category category) {
        Offer offer = new Offer();
        offer.setTitle(saveOffer.title());
        offer.setDescription(saveOffer.description());
        offer.setImgUrl(saveOffer.imgUrl());
        offer.setPrice(saveOffer.price());
        offer.setCategory(category);
        return offer;
    }

    public ReadOfferDto getOffer(long id) {
        Optional<Offer> offer = offerRepository.findById(id);
        return offer.map(OfferService::mapToReadOfferDto)
                .orElse(null);
    }

    public void deleteOffer(long id) {
        offerRepository.deleteById(id);
    }
}
