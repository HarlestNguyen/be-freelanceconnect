package vn.edu.likelion.group3.hackathon.generic;

import vn.edu.likelion.group3.hackathon.model.RequestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public abstract class GenericController<T, ID> {

    public abstract IService<T, ID> getService();

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(getService().findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable("id") ID id) {
        return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(getService().findOne(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") ID id) {
        getService().delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse("delete successfully"));
    }
}
