package com.blogapi.blog.controller;

import com.blogapi.blog.entity.Role;
import com.blogapi.blog.entity.User;
import com.blogapi.blog.payload.JWTAuthResponse;
import com.blogapi.blog.payload.LoginDto;
import com.blogapi.blog.payload.SignUpDto;
import com.blogapi.blog.repository.RoleRepository;
import com.blogapi.blog.repository.UserRepository;
import com.blogapi.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private UserRepository userRepo;
  private PasswordEncoder passwordEncoder;
  private RoleRepository roleRepo;

  public AuthController(UserRepository userRepo, PasswordEncoder passwordEncoder, RoleRepository roleRepo) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
    this.roleRepo = roleRepo;
  }
  @Autowired
  private JwtTokenProvider tokenProvider;



  //localhost:8080/api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?>registerUser(@RequestBody SignUpDto signUpDto){

    if(userRepo.existsByUsername(signUpDto.getUsername())){
      return  new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
    }
      if(userRepo.existsByEmail(signUpDto.getEmail())){
        return  new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
      }
        User user=new User();
      user.setName(signUpDto.getName());
      user.setUsername(signUpDto.getUsername());
      user.setEmail(signUpDto.getEmail());
      user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));


      Role roles=roleRepo.findByName("ROLE_ADMIN").get();
      user.setRoles(Collections.singleton(roles));

      userRepo.save(user);
      return new ResponseEntity<>("User registered successfully",HttpStatus.OK);
    }

    //http://localhost:8080/api/auth/signin
    @Autowired
    private AuthenticationManager authenticationManager;
  @PostMapping("/signin")
  public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginDto.getUsernameOrEmail(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    // get token form tokenProvider
    String token = tokenProvider.generateToken(authentication);

    return ResponseEntity.ok(new JWTAuthResponse(token));
  }


}
