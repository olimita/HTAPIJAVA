package com.olima.ws.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;
import com.olima.ws.rest.model.Credentials;
import com.olima.ws.rest.service.AuthService;

@Path("/autenticar")
public class AuthResource {
	
	private Gson builder= new Gson();
	
	private ResponseBuilder response;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validar(Credentials credenciales) {
		
		boolean status = AuthService.validar(credenciales.getUsuario(), credenciales.getContrasena());
		
		if(status) {
			
			String token = AuthService.createJWT();
			response=Response.status(Response.Status.OK);
			response.entity(token);
			return  response.build();
		}
		
		response=Response.status(Response.Status.NOT_ACCEPTABLE);
		response.entity("CREDENCIALES INCORRECTAS.");
		return  response.build();
	
	}
}