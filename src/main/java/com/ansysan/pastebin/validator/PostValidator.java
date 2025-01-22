package com.ansysan.pastebin.validator;

import com.ansysan.pastebin.dto.UserDto;
import com.ansysan.pastebin.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidator {
    private final PostRepository postRepository;

    public void validateCreate(UserDto userDto) {
        validateSomething(userDto);
    }

    public void validateUpdate(UserDto userDto) {
        validateSomething(userDto);
        validateAnotherThing(userDto);
    }

    private void validateSomething(UserDto userDto) {}

    private void validateAnotherThing(UserDto userDto) {}
}