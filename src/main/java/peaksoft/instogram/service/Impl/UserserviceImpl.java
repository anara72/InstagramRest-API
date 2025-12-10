package peaksoft.instogram.service.Impl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.instogram.entity.User;
import peaksoft.instogram.repo.UserRepo;
import peaksoft.instogram.service.UserService;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class UserserviceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return (List<User>) userRepo.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not fount"));
    }

    @Override
    public User updateUser(Long id, User user) {
        User user1 = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not fount"));
        user1.setUserName(user.getUserName());
        user1.setPassword(user.getPassword());
        user1.setEmail(user.getEmail());
        user1.setPhoneNumber(String.valueOf(user.getPhoneNumber()));
        return userRepo.save(user1);
    }

    @Override
    public String deleteUserById(Long id) {
        return userRepo.deleteById();
    }

}
