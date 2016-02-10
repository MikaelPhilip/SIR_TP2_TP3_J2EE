package fr.istic.sir.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.Heater;


@Path("/site")

public class SampleWebService {
    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello, how are you?";
    }
    
    @GET
    @Path("/heater")
    @Produces({ MediaType.APPLICATION_JSON })
    public Heater getHome() {
    	Heater h = new Heater();
        h.setModelName("SaCrame");
        h.setElecCosume(570);
        return h;
    }
}
