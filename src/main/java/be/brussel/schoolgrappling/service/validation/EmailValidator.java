package be.brussel.schoolgrappling.service.validation;

import org.springframework.stereotype.Component;

@Component
public class EmailValidator {

    public boolean emailIsValid(String email){
        return email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }
}
