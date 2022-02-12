package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Stack;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.domain.UserStack;
import com.devthink.devthink_server.dto.UserStackRequestData;
import com.devthink.devthink_server.errors.StackNotFoundException;
import com.devthink.devthink_server.errors.UserNotFoundException;
import com.devthink.devthink_server.errors.UserStackNotFoundException;
import com.devthink.devthink_server.infra.StackRepository;
import com.devthink.devthink_server.infra.UserRepository;
import com.devthink.devthink_server.infra.UserStackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStackService {
    private final UserStackRepository userStackRepository;
    private final UserRepository userRepository;
    private final StackRepository stackRepository;

    public UserStackService(UserStackRepository userStackRepository, UserRepository userRepository, StackRepository stackRepository) {
        this.userStackRepository = userStackRepository;
        this.userRepository = userRepository;
        this.stackRepository = stackRepository;
    }

    public List<UserStack> getUserStacks(Long userId) {
        return userStackRepository.findAllByUserId(userId);
    }

    public UserStack create(Long userId, UserStackRequestData userStackRequestData) {
        User user = findUser(userId);
        Stack stack = findStack(userStackRequestData.getStackId());
        UserStack userStack = UserStack.builder().user(user).stack(stack).build();
        return userStackRepository.save(userStack);
    }

    public void destroy(Long id) {
        UserStack userStack = userStackRepository.findById(id)
                .orElseThrow(() -> new UserStackNotFoundException(id));

        userStackRepository.delete(userStack);
    }

    private User findUser(Long userId) {
        return userRepository.findByIdAndDeletedIsFalse(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

    }

    private Stack findStack(Long stackId) {
        return stackRepository.findById(stackId)
                .orElseThrow(() -> new StackNotFoundException(stackId));
    }

}
