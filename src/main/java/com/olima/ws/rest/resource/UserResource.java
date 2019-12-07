package com.olima.ws.rest.resource;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import com.olima.ws.rest.model.GenericUser;
import com.olima.ws.rest.model.User;
import com.olima.ws.rest.service.AuthService;
import com.olima.ws.rest.service.UserService;

@Path("/users")
public class UserResource {
	
	private ResponseBuilder response;
	
	@Context
	private UriInfo uriInfo;
	
	private UserService userService = new UserService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers(@HeaderParam("access-token") String authToken) {
		if(authToken == null) {
			response=Response.status(Response.Status.UNAUTHORIZED);
			response.entity("TOKEN NO PROPORCIONADO");
			return  response.build(); 
		}
		if(!AuthService.verifyToken(authToken)) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("TOKEN INVALIDO");
			return  response.build(); 
		}
		if(userService.getDbCon()==null) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("ERROR BASE DE DATOS");
			return  response.build();
		}
	    List<User> users = new ArrayList<User>();
		try {
			users = userService.getAllUsers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity(e.getMessage());
			return  response.build();
		}
		if(users == null) {
			response=Response.status(Response.Status.NOT_FOUND);
			response.entity("NO HAY USUARIOS.");
			return  response.build();
		}
		GenericEntity<List<User>> list2 = new GenericEntity<List<User>>(users){};
		response = Response.status(Response.Status.OK);
		response.entity(list2);
		return  response.build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") Integer id,@HeaderParam("access-token") String authToken) {
		if(authToken == null) {
			response=Response.status(Response.Status.UNAUTHORIZED);
			response.entity("TOKEN NO PROPORCIONADO");
			return  response.build(); 
		}
		if(!AuthService.verifyToken(authToken)) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("TOKEN INVALIDO");
			return  response.build(); 
		}
		if(userService.getDbCon()==null) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("ERROR BASE DE DATOS");
			return  response.build();
		}
		User user = null;
		try {
			user = userService.getUserById(id);
			System.out.println("hola");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity(e.getMessage());
			return  response.build();			
		}
		if(user == null) {
			response=Response.status(Response.Status.NOT_FOUND);
			response.entity("USUARIO NO REGISTRADO.");
			return  response.build();
		}
		response = Response.status(Response.Status.OK);
		response.entity(user);
		return  response.build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(GenericUser user,@HeaderParam("access-token") String authToken) {
		
		if(authToken == null) {
			response=Response.status(Response.Status.UNAUTHORIZED);
			response.entity("TOKEN NO PROPORCIONADO");
			return  response.build(); 
		}
		if(!AuthService.verifyToken(authToken)) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("TOKEN INVALIDO");
			return  response.build(); 
		}
		if(userService.getDbCon()==null) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("ERROR BASE DE DATOS");
			return  response.build();
		}
		GenericUser createdUser;
		try {
			createdUser = userService.createUser(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity(e.getMessage());
			return  response.build();			
		}		
		response = Response.status(Response.Status.CREATED);
		response.entity(createdUser);
		return  response.build();
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("id") Integer id,@HeaderParam("access-token") String authToken,GenericUser user) {	
		
		if(authToken == null) {
			response=Response.status(Response.Status.UNAUTHORIZED);
			response.entity("TOKEN NO PROPORCIONADO");
			return  response.build(); 
		}
		if(!AuthService.verifyToken(authToken)) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("TOKEN INVALIDO");
			return  response.build(); 
		}
		
		if(userService.getDbCon()==null) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("ERROR BASE DE DATOS");
			return  response.build();
		}
		GenericUser updatedUser;
		try {
			updatedUser = userService.updateUser(id, user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity(e.getMessage());
			return  response.build();			
		}		
		
		if(updatedUser == null) {
			response=Response.status(Response.Status.NOT_FOUND);
			response.entity("USUARIO NO REGISTRADO.");
			return  response.build();
		}
		response = Response.status(Response.Status.OK);
		response.entity(updatedUser);
		return  response.build();
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("id") Integer id,@HeaderParam("access-token") String authToken) {	
		
		if(authToken == null) {
			response=Response.status(Response.Status.UNAUTHORIZED);
			response.entity("TOKEN NO PROPORCIONADO");
			return  response.build(); 
		}
		if(!AuthService.verifyToken(authToken)) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("TOKEN INVALIDO");
			return  response.build(); 
		}
		if(userService.getDbCon()==null) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("ERROR BASE DE DATOS");
			return  response.build();
		}
		Integer deletedUser;
		try {
			deletedUser = userService.deleteUser(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity(e.getMessage());
			return  response.build();			
		}	
		
		if(deletedUser == null) {
			response=Response.status(Response.Status.NOT_FOUND);
			response.entity("USUARIO NO REGISTRADO.");
			return  response.build();
		}
		response = Response.status(Response.Status.OK);
		response.entity(deletedUser);
		return  response.build();
	}

}
