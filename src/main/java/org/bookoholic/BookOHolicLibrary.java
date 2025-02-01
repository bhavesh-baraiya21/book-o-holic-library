package org.bookoholic;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

@Path("/library")
public class BookOHolicLibrary {

    @Inject
    BookService bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        return Response.ok(bookService.listBooks()).build();
    }

    @GET
    @Path("/{_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("_id") ObjectId _id) {
        Book book = bookService.getBook(_id);

        if(book == null)
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Book not found with id: " + _id)
                .build();

        return Response.ok(book).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(Book book){
        bookService.addBook(book);

        return Response.ok(book).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") ObjectId _id, Book book){
        Book updatedBook = bookService.updateBook(_id, book);

        if(updatedBook == null)
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Book not found with id: " + _id)
                .build();

        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") ObjectId _id){
        int deletedBooks = bookService.deleteBook(_id);

        if(deletedBooks == 0)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Book not found with id: " + _id)
                    .build();

        return Response.ok("Book deleted with id: " + _id).build();
    }
}
