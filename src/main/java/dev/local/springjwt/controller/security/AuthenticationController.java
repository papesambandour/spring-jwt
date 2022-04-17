package dev.local.springjwt.controller.security;

import dev.local.springjwt.config.security.JwtTokenUtil;
import dev.local.springjwt.dao.UserDao;
import dev.local.springjwt.model.Users;
import dev.local.springjwt.model.customer.ApiResponse;
import dev.local.springjwt.model.customer.AuthToken;
import dev.local.springjwt.model.customer.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    AuthenticationController(AuthenticationManager authenticationManager,JwtTokenUtil jwtTokenUtil,UserDao userDao){
        this.jwtTokenUtil=jwtTokenUtil;
        this.authenticationManager=authenticationManager;
        this.userDao=userDao;
    }
   final private AuthenticationManager authenticationManager;

   final private JwtTokenUtil jwtTokenUtil;
   final private UserDao userDao;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResponse<Users> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final Users user = userDao.findByUsername(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "success",user,new AuthToken(token, user.getUsername()));
    }

    @GetMapping("/no-enable")
    public ApiResponse<List<Users>> listRole(){
        return new ApiResponse<>(222, "Account disabled.","noenable");
    }

}
