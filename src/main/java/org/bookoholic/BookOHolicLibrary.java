package org.bookoholic;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;


@Path("/library")
public class BookOHolicLibrary {
    List<Book> myBooks = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        return Response.ok(myBooks).build();
    }

    @GET
    @Path("/{_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("_id")ObjectId _id) {
        for(Book book: myBooks){
            if(_id.equals(book.get_id())){
                return Response.ok(book).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Book not found with id: " + _id)
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(Book book){
        myBooks.add(book);
        return Response.ok(myBooks).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") ObjectId _id, Book book){
        for(Book b: myBooks){
            if(_id.equals(b.get_id())){
                b.getFields(book);
                return Response.ok(myBooks).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Book not found with id: " + _id)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") ObjectId _id){
        for(Book b: myBooks){
            if(_id.equals(b.get_id())){
                myBooks.remove(b);
                return Response.ok(myBooks).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Book not found with id: " + _id)
                .build();
    }
}
