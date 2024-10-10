package vn.com.easyjob.base;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomPageResponse<T> {
    long totalElements;
    int totalPages;
    int size;
    int number;
    List<T> content;
    boolean first;
    boolean last;
    int numberOfElements;
    boolean empty;

    // Phương thức map để chuyển đổi các phần tử trong content
    public <U> CustomPageResponse<U> map(Function<? super T, ? extends U> converter) {
        // Chuyển đổi content từ List<T> sang List<U>
        List<U> convertedContent = content.stream()
                .map(converter)
                .collect(Collectors.toList());

        // Trả về một đối tượng CustomPageResponse<U> mới
        return new CustomPageResponse<>(
                totalElements,
                totalPages,
                size,
                number,
                convertedContent,
                first,
                last,
                numberOfElements,
                empty
        );
    }
}
