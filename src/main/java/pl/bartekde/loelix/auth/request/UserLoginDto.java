package pl.bartekde.loelix.auth.request;

import pl.bartekde.loelix.auth.annotation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserLoginDto {

    @ValidEmail
    @NotNull
    @NotEmpty
    public String email;

    @NotNull
    @NotEmpty
    public String password;

}
