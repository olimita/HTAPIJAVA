package com.olima.ws.rest.resource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.olima.ws.rest.model.CCard;
import com.olima.ws.rest.service.AuthService;
import com.olima.ws.rest.service.UserService;

@Path("/usercards")
public class UserCardsResource {
	
	private ResponseBuilder response;
	
	@Context
	private UriInfo uriInfo;
	
	private UserService userService = new UserService();
	
	@GET
	@Path("{user_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCards(@PathParam("user_id") Integer user_id,@HeaderParam("access-token") String authToken) {
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
	    List<CCard> cards = new ArrayList<CCard>();
		try {
			cards = userService.getUserCards(user_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity(e.getMessage());
			return  response.build();
		}
		if(cards == null) {
			response=Response.status(Response.Status.NOT_FOUND);
			response.entity("NO HAY USUARIOS.");
			return  response.build();
		}
		GenericEntity<List<CCard>> list2 = new GenericEntity<List<CCard>>(cards){};
		response = Response.status(Response.Status.OK);
		response.entity(list2);
		return  response.build();
	}

}
