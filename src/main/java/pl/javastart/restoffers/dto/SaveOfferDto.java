package pl.javastart.restoffers.dto;

public record SaveOfferDto(String title, String description, String imgUrl, double price, String category) {
}
