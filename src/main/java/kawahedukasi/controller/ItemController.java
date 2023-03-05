package kawahedukasi.controller;

import kawahedukasi.model.Item;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.Map;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

    @GET
    public Response getAllItems(@PathParam("name") String name){
        return Response.status(Response.Status.OK).entity(Item.findAll().list()).build();
    }

    @GET
    @Path("/{id}")
    public Response getByIdItems(@PathParam("id") Long id) {
        Item item = Item.findById(id);
        if (item == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).entity(Item.findById(id)).build();
    }

    @POST
    @Transactional
    public Response createItems(Map<String, Object> request){
        Item item = new Item();
        item.name = request.get("name").toString();
        item.count = Integer.valueOf(request.get("count").toString());
        item.price = Double.valueOf(request.get("price").toString());
        item.description = request.get("description").toString();

        item.persist();

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateItems(@PathParam("id") Long id, Map<String, Object> request) {
        Item item = Item.findById(id);
        if (item == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        item.name = request.get("name").toString();
        item.count = Integer.valueOf(request.get("count").toString());
        item.price = Double.valueOf(request.get("price").toString());
        item.description = request.get("description").toString();

        item.persist();

        return Response.status(Response.Status.OK).entity(new HashMap<>()).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteItems(@PathParam("id") Long id) {
        Item item = Item.findById(id);
        if (item == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        item.delete();

        return Response.status(Response.Status.OK).entity(new HashMap<>()).build();
    }
}
