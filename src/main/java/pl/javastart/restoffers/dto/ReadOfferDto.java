package pl.javastart.restoffers.dto;

public record ReadOfferDto(long id, String title, String description, String imgUrl, double price, String category) {
}
