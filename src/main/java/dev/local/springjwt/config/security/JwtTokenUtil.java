package dev.local.springjwt.config.security;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import dev.local.springjwt.dao.ProfilDao;
import dev.local.springjwt.dao.UserDao;
import dev.local.springjwt.model.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import static dev.local.springjwt.config.security.JWTConstant.*;
@Component
public class JwtTokenUtil implements Serializable {
    final UserDao userDao;
    final ProfilDao profilDao;
    final UserDetailsService userDetailsService;
    JwtTokenUtil(ProfilDao profil, UserDao userDao, UserDetailsService userDetailsService){
        this.profilDao = profil;
        this.userDao = userDao;
        this.userDetailsService = userDetailsService;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Users getUserAthenticate(HttpServletRequest req) {
        String token = getToken(req);
        String username = getUsernameFromToken(token);
        return userDao.findByUsername(username);
    }
    public String getToken(HttpServletRequest req) {
        String token = req.getHeader(HEADER_STRING) ;
        return token.replace(TOKEN_PREFIX,"");
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Users user) {
        return doGenerateToken(user.getUsername());
    }

    private String doGenerateToken(String subject) {

        Claims claims = Jwts.claims().setSubject(subject);
        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
        claims.put("scopes", userDetails.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://localhost:8080")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
                username.equals(userDetails.getUsername())
                        && !isTokenExpired(token));
    }

}