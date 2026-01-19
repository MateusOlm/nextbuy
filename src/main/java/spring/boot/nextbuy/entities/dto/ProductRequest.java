package spring.boot.nextbuy.entities.dto;

public record ProductRequest(String name, String description, String category,
        String brand, String imgPath, double price, int quantity) {
}
