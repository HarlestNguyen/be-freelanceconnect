package vn.com.easyjob.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class PaginationUtils {

    public <T> List<T> paginate(Collection<T> collection, int page, int size) {
        List<T> list = new ArrayList<>(collection);
        int fromIndex = Math.min(page * size, list.size());
        int toIndex = Math.min((page + 1) * size, list.size());

        if (fromIndex > toIndex) {
            return Collections.emptyList();
        }

        return list.subList(fromIndex, toIndex);
    }

    public <T> List<T> paginate(Collection<T> collection, int page) {
        return paginate(collection, page, 10); // Default page size is 10
    }

    public <T> List<T> paginate(Collection<T> collection) {
        return paginate(collection, 0, 10); // Default page is 0 and page size is 10
    }
}
