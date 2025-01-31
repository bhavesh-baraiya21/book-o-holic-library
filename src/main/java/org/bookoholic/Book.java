package org.bookoholic;

import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class Book {

    private ObjectId _id;
    private String name;
    private String ifsc;
    private List<String> authors = new ArrayList<>();
    private Integer sellingPrice;
    private String type;

    // Default constructor required for deserialization
    public Book() {
        this._id = new ObjectId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIfsc() {
        return ifsc;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void getFields(Book book){
        if(book.getName() != null){
            this.setName(book.getName());
        }

        if(!book.getAuthors().isEmpty()){
            this.setAuthors(book.getAuthors());
        }

        if(book.getType() != null){
            this.setType(book.getType());
        }

        if(book.getIfsc() != null){
            this.setIfsc(book.getIfsc());
        }

        if(book.getSellingPrice() != null){
            this.setSellingPrice(book.getSellingPrice());
        }
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}