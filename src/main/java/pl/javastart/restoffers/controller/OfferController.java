package pl.javastart.restoffers.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.javastart.restoffers.dto.ReadOfferDto;
import pl.javastart.restoffers.dto.SaveOfferDto;
import pl.javastart.restoffers.service.OfferService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
    private OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public List<ReadOfferDto> getAllOffers(@RequestParam(required = false) String title) {
        if (title == null) {
            return offerService.getAllOffers();
        }
        return offerService.getAllWithTitle(title);
    }

    @GetMapping("/count")
    public Long getOffersAmount() {
        return offerService.getOffersAmount();
    }

    @PostMapping()
    public ResponseEntity<?> saveOffer(@RequestBody SaveOfferDto offer) {
        Optional<ReadOfferDto> savedOffer = offerService.saveOffer(offer);
        if (savedOffer.isEmpty()) {
            return new ResponseEntity<>(String.format("Nie znaleziono kategorii %s", offer.category()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(savedOffer.get(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadOfferDto> getOffer(@PathVariable long id) {
        Optional<ReadOfferDto> readOffer = offerService.getOffer(id);
        return readOffer.map(offer -> new ResponseEntity<>(offer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable long id) {
        offerService.deleteOffer(id);
    }
}
