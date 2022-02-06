package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Stack;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.domain.UserStack;
import com.devthink.devthink_server.dto.UserStackData;
import com.devthink.devthink_server.errors.StackNotFoundException;
import com.devthink.devthink_server.errors.UserNotFoundException;
import com.devthink.devthink_server.errors.UserStackBadRequestException;
import com.devthink.devthink_server.errors.UserStackNotFoundException;
import com.devthink.devthink_server.infra.StackRepository;
import com.devthink.devthink_server.infra.UserRepository;
import com.devthink.devthink_server.repository.UserStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStackService {
    private final UserStackRepository userStackRepository;
    private final UserRepository userRepository;
    private final StackRepository stackRepository;

    public List<UserStack> getUserStack(Long userId) {
        return userStackRepository.findAllByUserId(userId);

    }

    public UserStack create(UserStackData userStackData) {
        User user = findUser(userStackData.getUserId());
        Stack stack = findStack(userStackData.getStackId());
        try {
            UserStack userStack = UserStack.builder().user(user).stack(stack).build();
            return userStackRepository.save(userStack);
        } catch(Exception e){
            throw new UserStackBadRequestException();
        }
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
