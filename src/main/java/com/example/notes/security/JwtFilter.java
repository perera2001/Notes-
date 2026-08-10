package com.example.notes.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    )
            throws ServletException, IOException {
        String authHeader =
                request.getHeader("Authorization");


        String token = null;
        String email = null;



        // Check Bearer Token
        if(authHeader != null &&
                authHeader.startsWith("Bearer ")) {


            token = authHeader.substring(7);


            email = jwtService.extractEmail(token);

        }



        // If user exists and not already authenticated
        if(email != null &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {


            // loading the user
            UserDetails userDetails =
                    customUserDetailsService
                            .loadUserByUsername(email);



            if(jwtService.validateToken(
                    token,
                    userDetails
            )) {


                // creating an authentication object
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,        //the authenticated user
                                null,                //the password is unnecessary because the JWT was already validated
                                userDetails.getAuthorities()  //the user’s roles, such as ROLE_USER or ROLE_ADMIN
                        );


                 // saving the authenticated user - The JWT is valid, and this request belongs to this authenticated user.
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authenticationToken);

            }

        }


         //This sends the request to the next security filter.
        //
        //After all filters finish, the request can reach the controller:
        filterChain.doFilter(
                request,
                response
        );

    }
}
