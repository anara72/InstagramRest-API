package peaksoft.instogram.service;
import peaksoft.instogram.entity.User;
import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUser();


    User getUserById(Long id);

    User updateUser(Long id,User user);

    String deleteUserById(Long id);

//    User autenicate(String email, String password);

//    User userProfile(Long id);





}