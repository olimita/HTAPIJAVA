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
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.olima.ws.rest.model.CCard;
import com.olima.ws.rest.model.GenericCCard;
import com.olima.ws.rest.service.AuthService;
import com.olima.ws.rest.service.CardService;


@Path("/ccards")
public class CardResource {
	
	private ResponseBuilder response;
	
	@Context
	private UriInfo uriInfo;
	
	private CardService cardService = new CardService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCards(@HeaderParam("access-token") String authToken) {
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
		if(cardService.getDbCon()==null) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("ERROR BASE DE DATOS");
			return  response.build();
		}
	    List<CCard> cards = new ArrayList<CCard>();
		try {
			cards = cardService.getAllCards();
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
	
	@GET
	@Path("{ccard_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("ccard_id") Integer ccard_id,@HeaderParam("access-token") String authToken) {
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
		if(cardService.getDbCon()==null) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("ERROR BASE DE DATOS");
			return  response.build();
		}
		CCard card = null;
		try {
			card = cardService.getCardById(ccard_id);
			System.out.println("hola");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity(e.getMessage());
			return  response.build();			
		}
		if(card == null) {
			response=Response.status(Response.Status.NOT_FOUND);
			response.entity("USUARIO NO REGISTRADO.");
			return  response.build();
		}
		response = Response.status(Response.Status.OK);
		response.entity(card);
		return  response.build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(GenericCCard card,@HeaderParam("access-token") String authToken) {
		
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
		if(cardService.getDbCon()==null) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("ERROR BASE DE DATOS");
			return  response.build();
		}
		GenericCCard createdCard;
		try {
			createdCard = cardService.createCard(card);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity(e.getMessage());
			return  response.build();			
		}		
		response = Response.status(Response.Status.CREATED);
		response.entity(createdCard);
		return  response.build();
	}
	
	@PUT
	@Path("{ccard_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("ccard_id") Integer ccard_id,@HeaderParam("access-token") String authToken,GenericCCard card) {	
		
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
		
		if(cardService.getDbCon()==null) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("ERROR BASE DE DATOS");
			return  response.build();
		}
		GenericCCard updatedCard;
		try {
			updatedCard = cardService.updateCard(ccard_id, card);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity(e.getMessage());
			return  response.build();			
		}		
		
		if(updatedCard == null) {
			response=Response.status(Response.Status.NOT_FOUND);
			response.entity("USUARIO NO REGISTRADO.");
			return  response.build();
		}
		response = Response.status(Response.Status.OK);
		response.entity(updatedCard);
		return  response.build();
	}
	
	@DELETE
	@Path("{ccard_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("ccard_id") Integer ccard_id,@HeaderParam("access-token") String authToken) {	
		
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
		if(cardService.getDbCon()==null) {
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity("ERROR BASE DE DATOS");
			return  response.build();
		}
		Integer deletedCard;
		try {
			deletedCard = cardService.deleteCard(ccard_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response=Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			response.entity(e.getMessage());
			return  response.build();			
		}	
		
		if(deletedCard == null) {
			response=Response.status(Response.Status.NOT_FOUND);
			response.entity("USUARIO NO REGISTRADO.");
			return  response.build();
		}
		response = Response.status(Response.Status.OK);
		response.entity(deletedCard);
		return  response.build();
	}

}
