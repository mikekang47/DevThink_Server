package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Stack;
import com.devthink.devthink_server.dto.StackData;
import com.devthink.devthink_server.errors.StackNotFoundException;
import com.devthink.devthink_server.infra.StackRepository;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StackService {
    private final StackRepository stackRepository;
    private final Mapper mapper;

    public Stack getStack(Long id) {
        return findStack(id);
    }

    private Stack findStack(Long id) {
        return stackRepository.findById(id).orElseThrow(() -> new StackNotFoundException(id));
    }

    public List<Stack> getStacks() {
        return stackRepository.findAll();
    }

    public Stack register(StackData stackData) {
        Stack stack = mapper.map(stackData, Stack.class);
        return stackRepository.save(stack);
    }
}
