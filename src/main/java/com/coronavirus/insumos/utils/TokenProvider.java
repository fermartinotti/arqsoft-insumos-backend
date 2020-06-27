package com.coronavirus.insumos.utils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.security.Key;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TokenProvider {
	
	//key privada, llevar a propertie o BD.
	private static String SECRET_KEY = "clave";

    
    public static String generarJWT(String subject, String rol) {
    	//tiempo de expiracion.
    	int ttlMillis = 600000;

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //uso de la llave privada para crear jwt
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(rol);

        //builder del JWT
        JwtBuilder builder = Jwts.builder().setId("idToken")
                .setIssuedAt(now)
                .setSubject(subject)
                //.signWith(signatureAlgorithm, signingKey)
                .claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes());
        
                // .claim("role", rol);

        //seteo expiracion de token.
         //   long expMillis = nowMillis + ttlMillis;
          //  Date exp = new Date(expMillis);
           // builder.setExpiration(exp);

        //retorno del bearer.
        return builder.compact();
    }
    
	public static Claims decodeJWT(String jwt) {
		String jwtToken =  jwt.replace("Bearer ", "");
		return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(jwtToken).getBody();
	}
	

}

