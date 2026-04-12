package com.survey.springboot.pwa.app.springboot_survey_app.security.jwt;

//import edu.uptc.PizonAcevedo.domain.model.userModel.UserEntity;
//import edu.uptc.PizonAcevedo.domain.repository.repositoryUser.CredentialRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.models.user.UserEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtils {

    @Autowired
    UserRepository userRepository;

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    public String generateAccesToken (String email){
        UserEntity userEntity = userRepository.findByEmail(email).get();


        return Jwts.builder()
                .subject(email)
//                .claim("id", userEntity.getId())
//                .claim("name", userEntity.getName())
//                .claim("lastName", userEntity.getLastName())
//                .claim("email", userEntity.getEmail())
//                .claim( "pathImage", userEntity.getPathImage())
//                .claim("userStatus", userEntity.isUserStatus())
//                .claim("roles", userEntity.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()))
                .claim("numero_identificacion", userEntity.getNumberIdentification())
                .claim("role", userEntity.getRole().getName().name())
                .claim("email", userEntity.getEmail())

                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValidateToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        }catch (Exception e){
            log.error("Token invalido". concat(e.getMessage()));
            return false;
        }
    }

    public String getUserNameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public Claims extractAllClaims (String token){
        return Jwts.parser()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key getSignatureKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
