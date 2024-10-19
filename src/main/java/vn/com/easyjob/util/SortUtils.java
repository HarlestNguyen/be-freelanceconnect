package vn.com.easyjob.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortUtils {
    // Phương thức sử dụng Reflection để lấy danh sách các trường hợp lệ từ một class
    public static List<String> getValidSortFields(Class<?> clazz) {
        // Lấy danh sách tất cả các field từ class và chuyển thành List<String> các tên trường
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    // Phương thức kiểm tra xem field có hợp lệ không (dựa trên danh sách các field từ class)
    public static boolean isValidSortField(Class<?> clazz, String field) {
        List<String> validFields = getValidSortFields(clazz);
        return validFields.contains(field);
    }
}
