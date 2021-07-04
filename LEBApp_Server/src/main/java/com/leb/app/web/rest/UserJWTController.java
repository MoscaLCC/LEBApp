package com.leb.app.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leb.app.domain.User;
import com.leb.app.repository.UserRepository;
import com.leb.app.security.jwt.JWTFilter;
import com.leb.app.security.jwt.TokenProvider;
import com.leb.app.service.DeliveryManService;
import com.leb.app.service.PointService;
import com.leb.app.service.ProducerService;
import com.leb.app.service.TransporterService;
import com.leb.app.service.dto.LoginDTO;
import com.leb.app.web.rest.vm.LoginVM;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final PointService pointService;

    private final TransporterService transporterService;

    private final ProducerService producerService;

    private final DeliveryManService deliveryManService;

    private final UserRepository userRepository;

    public UserJWTController(TokenProvider tokenProvider, 
    AuthenticationManagerBuilder authenticationManagerBuilder,
    PointService pointService,
    TransporterService transporterService,
    ProducerService produtorService,
    DeliveryManService deliveryManService,
    UserRepository userRepository
    ) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.pointService = pointService;
        this.transporterService = transporterService;
        this.producerService = produtorService;
        this.deliveryManService = deliveryManService;
        this.userRepository = userRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        log.info("<authorize>");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        log.info("</authorize>");
        return new ResponseEntity<JWTToken>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/interface/authenticate")
    public ResponseEntity<LoginDTO> authorize2(@Valid @RequestBody LoginVM loginVM) {
        log.info("<authorize>");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        
        User user = userRepository.findOneByLogin(loginVM.getUsername()).get();
        LoginDTO login = new LoginDTO("Bearer " + jwt, user.getFirstName(), user.getLastName(), getProfils(user));

        log.info("</authorize>");
        return new ResponseEntity<>(login, httpHeaders, HttpStatus.OK);
    }

    private List<String> getProfils(User user){
        log.info("<getProfils>");
        List<String> profils = new ArrayList<>();
    
        Long userId = user.getId();

        if(deliveryManService.isDeliveryMan(userId)) profils.add("DeliveryMan");
        if(producerService.isProducer(userId)) profils.add("Producer");
        if(transporterService.isTransporter(userId)) profils.add("Transporter");
        if(pointService.isPoint(userId)) profils.add("Point");

        log.info("</getProfils>");
        return profils;
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
