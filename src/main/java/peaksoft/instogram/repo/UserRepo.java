package peaksoft.instogram.repo;

import org.springframework.data.repository.CrudRepository;
import peaksoft.instogram.entity.User;

import java.util.List;

public interface UserRepo extends CrudRepository<User,Long> {

    List<User> id(Long id);

    String deleteById();
}
