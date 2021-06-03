package pl.bartekde.loelix.auth.request;


import pl.bartekde.loelix.auth.annotation.ValidEmail;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Valid
public class UserRegisterDto {

    @NotNull
    @NotEmpty
    public String name;

    @NotNull
    @NotEmpty
    public String password;

    @ValidEmail
    @NotNull
    @NotEmpty
    public String email;
}
