package vn.com.easyjob.model.record;

import jakarta.validation.constraints.NotNull;

public record JobTypeRecord(
        @NotNull
        String name,
        String description,
        @NotNull
        Integer minPrice,
        @NotNull
        Integer maxPrice
) {
}
