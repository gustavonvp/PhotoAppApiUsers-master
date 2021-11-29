package com.appdeveloper.photoapp.api.users.photoappapiusers.ui.security;

import com.appdeveloper.photoapp.api.users.photoappapiusers.ui.rest.model.requests.LoginRequestModel;
import com.appdeveloper.photoapp.api.users.photoappapiusers.ui.rest.shared.UserDto;
import com.appdeveloper.photoapp.api.users.photoappapiusers.ui.services.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AutheticationFilter extends UsernamePasswordAuthenticationFilter {

    private UsersService usersService;
    private Environment environment;


    public AutheticationFilter(UsersService usersService, Environment environment, AuthenticationManager authenticationManager) {
        this.usersService = usersService;
        this.environment = environment;
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        try {
            LoginRequestModel creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
       String userName = ((User) authResult.getPrincipal()).getUsername();
       UserDto userDetails =  usersService.getUserDetailsByEmail(userName);

       String token = Jwts.builder()
               .setSubject(userDetails.getUserId())
              // .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration"))))
               .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
               .compact();

        response.addHeader("token", token);
        response.addHeader("userId",userDetails.getUserId());
    }
}
