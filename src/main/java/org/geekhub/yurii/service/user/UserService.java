package org.geekhub.yurii.service.user;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.model.user.User;
import org.geekhub.yurii.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public int getCount() {
        return userRepository.getCount();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
