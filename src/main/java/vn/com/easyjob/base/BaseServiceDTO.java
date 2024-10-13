package vn.com.easyjob.base;

import org.springframework.http.HttpStatus;
import vn.com.easyjob.exception.ErrorHandler;

import java.lang.reflect.Field;
import java.util.Collection;

public abstract class  BaseServiceDTO<T extends BaseEntity, DTO, ID> extends BaseService {

    // Phương thức để map đối tượng kiểu T (entity) sang DTO
    private DTO toDTO(BaseEntity t, Class<DTO> dtoClass) {
        try {
            // Tạo một thể hiện mới của DTO
            DTO dto = dtoClass.getDeclaredConstructor().newInstance();

            // Lấy tất cả các field của entity
            Field[] entityFields = t.getClass().getDeclaredFields();

            // Lặp qua các field trong entity
            for (Field entityField : entityFields) {
                entityField.setAccessible(true); // Truy cập các private field
                Object value = entityField.get(t); // Lấy giá trị của field từ entity

                try {
                    // Tìm field tương ứng trong DTO theo tên
                    Field dtoField = dtoClass.getDeclaredField(entityField.getName());
                    dtoField.setAccessible(true);

                    // Kiểm tra nếu kiểu dữ liệu của field trong DTO và entity tương thích
                    if (dtoField.getType().isAssignableFrom(entityField.getType())) {
                        dtoField.set(dto, value); // Gán giá trị từ entity sang DTO
                    }

                } catch (NoSuchFieldException e) {
                    // Nếu DTO không có field tương ứng, bỏ qua field này
                    continue;
                }
            }

            return dto; // Trả về đối tượng DTO đã được map
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Error while mapping entity to DTO: " + e.getMessage());
        }
    }

    public DTO save(T t, Class<DTO> dtoClass) {
        return toDTO(save(t), dtoClass);
    }

    public Collection<DTO> findAll(Class<DTO> dtoClass) {
        try {
            return findAll().stream().map(t -> toDTO((BaseEntity) t, dtoClass)).toList();
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public DTO findOne(ID id, Class<DTO> dtoClass) {
        return toDTO(findOne(id), dtoClass);
    }

}
