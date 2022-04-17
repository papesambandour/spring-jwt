package dev.local.springjwt.model.customer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class LoginUser {

    private String username;
    private String password;


}
