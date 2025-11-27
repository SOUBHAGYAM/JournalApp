package com.Ilearn.journalApp.Controller;


import com.Ilearn.journalApp.Entity.User;
import com.Ilearn.journalApp.service.UserDetailsServiceImpl;
import com.Ilearn.journalApp.service.UserService;
import com.Ilearn.journalApp.utilis.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {


    @Autowired
    private AuthenticationManager authenticationmanager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtutil;

    @GetMapping("/health")
    public String HealthCheck() {
        return "Healthy";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody User user){
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            authenticationmanager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtutil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch(Exception e){
            log.error("Exception occurred while createAuthenticationToken",e);
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);

        }
    }

}
