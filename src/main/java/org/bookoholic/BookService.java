package org.bookoholic;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.BsonDocument;
import org.bson.Document;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BookService {
    @Inject
    MongoClient mongoClient;

    public List<Book> listBooks(){
        List<Book> books = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Book book = new Book();
                book.setId(document.getObjectId("_id"));
                book.setName(document.getString("name"));
                book.setIfsc(document.getString("ifsc"));
                book.setAuthors((List<String>) document.getList("authors", String.class));
                book.setSellingPrice(document.getInteger("sellingPrice"));
                book.setType(document.getString("type"));
                books.add(book);
            }
        } finally {
            cursor.close();
        }
        return books;
    }

    public Book getBook(ObjectId id){
        Bson filter = Filters.eq("_id", id);
        if(getCollection().countDocuments() == 0) return null;

        FindIterable<Document> iter = getCollection().find(filter);
        Document document = iter.first();

        Book book = new Book();
        assert document != null;
        book.setId(document.getObjectId("_id"));
        book.setName(document.getString("name"));
        book.setIfsc(document.getString("ifsc"));
        book.setAuthors((List<String>) document.getList("authors", String.class));
        book.setSellingPrice(document.getInteger("sellingPrice"));
        book.setType(document.getString("type"));

        return book;
    }

    public int deleteBook(ObjectId id){
        Bson filter = Filters.eq("_id", id);

        if(getCollection().countDocuments(filter) == 0)
            return 0;

        getCollection().deleteOne(filter);
        return 1;
    }

    public Book updateBook(ObjectId id, Book book){
        if(getCollection().countDocuments() == 0) return null;

        Bson filter = Filters.eq("_id", id);
        Bson update = new BsonDocument();

        if(book.getName() != null){
            update = Updates.combine(update, Updates.set("name", book.getName()));
        }

        if(book.getIfsc() != null){
            update = Updates.combine(update, Updates.set("ifsc", book.getIfsc()));
        }

        if(book.getSellingPrice() != null){
            update = Updates.combine(update, Updates.set("sellingPrice", book.getSellingPrice()));
        }

        if(book.getType() != null){
            update = Updates.combine(update, Updates.set("type", book.getType()));
        }

        if(!book.getAuthors().isEmpty()){
            update = Updates.combine(update, Updates.set("authors", book.getAuthors()));
        }

        getCollection().updateOne(filter, update);

        return getBook(id);
    }

    public void addBook(Book book){
        Document document = new Document()
                .append("_id", book.getId())
                .append("name", book.getName())
                .append("ifsc", book.getIfsc())
                .append("authors", book.getAuthors())
                .append("sellingPrice", book.getSellingPrice())
                .append("type", book.getType());

        getCollection().insertOne(document);
    }

    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("library").getCollection("books");
    }
}
