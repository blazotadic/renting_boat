package com.renting_boat.demo.security.validator;

import com.renting_boat.demo.repository.UserRepository;
import com.renting_boat.demo.security.dto.UserCreateDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserCreateValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UserCreateDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        UserCreateDTO userCreateDTO = (UserCreateDTO) target;
        validateUsername(userCreateDTO.getUsername(), errors);
        validatePassword(userCreateDTO.getPassword(), errors);
        validateFirstName(userCreateDTO.getFirstName(), errors);
        validateLastName(userCreateDTO.getLastName(), errors);
    }

    /**
     * Validate username
     *
     * @param username given username
     * @param errors reference to Errors
     */
    private void validateUsername(String username, Errors errors)
    {
        if (StringUtils.isBlank(username))
        {
            errors.rejectValue(
                "username",
                "username.invalid",
                "Username is not valid!"
            );
        }

        if (StringUtils.isNotBlank(username) && userRepository.existsByUsername(username))
        {
            errors.rejectValue(
                "username",
                "username.invalid",
                "Username is not valid!"
            );
        }
    }

    private void validatePassword(String password, Errors errors)
    {
        if (StringUtils.isBlank(password))
        {
            errors.rejectValue(
                "password",
                "password.invalid",
                "Password is not valid!"
            );
        }

    }

    private void validateFirstName(String firstName, Errors errors)
    {
        if (StringUtils.isBlank(firstName))
        {
            errors.rejectValue(
                "firstName",
                "firstName.required",
                "First name is required!"
            );
        }
    }

    /**
     * Validate last name
     *
     * @param lastName given last name
     * @param errors reference to Errors
     */
    private void validateLastName(String lastName, Errors errors)
    {
        if (StringUtils.isBlank(lastName))
        {
            errors.rejectValue(
                "lastName",
                "lastName.required",
                "Last name is required!"
            );
        }
    }
}
