package com.olima.ws.rest.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthService {
	
	public static boolean validar(String usuario, String contrasena) {
		return (usuario.equals("lozano") && contrasena.equals("12345"));
	}
	
	private static String SECRET_KEY = "QuieroPasarAHighTech";

    //Sample method to construct a JWT
    public static String createJWT() {

    	String token = Jwts.builder().setSubject("admin").signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
		return token;
    }
    
    public static boolean verifyToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (JwtException e) {

			return false;
		}
	}

}
