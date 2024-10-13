package vn.com.easyjob.model.record;

public record JobtypeRecord(
        String name,
        String description,
        Integer minPrice,
        Integer maxPrice
) {
}
