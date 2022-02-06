package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Stack;
import com.devthink.devthink_server.errors.StackNotFoundException;
import com.devthink.devthink_server.infra.StackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StackService {
    private final StackRepository stackRepository;

    public List<String> getStacks(List<Long> stackIds) {
        List<String> stacks = new ArrayList<>();
        for(Long id: stackIds) {
            Stack stack = stackRepository.findById(id).orElseThrow(() -> new StackNotFoundException(id));
            if(stack != null) {
                stacks.add(stack.getName());
            }
        }
        return stacks;
    }


}
