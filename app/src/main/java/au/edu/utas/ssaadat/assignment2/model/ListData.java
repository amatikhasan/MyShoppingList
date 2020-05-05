package au.edu.utas.ssaadat.assignment2.model;

/**
 * Created by User on 3/21/2018.
 */

public class ListData {
    private String category;
    private byte[] image;
    private int id;
    private String name;
    private String quantity;
    private String date;
    private String price;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public ListData(int id,String name, String quantity, String price, byte[] image, String date ) {
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.price = price;
        this.image = image;
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public ListData(String name, String quantity, String category, byte[] image) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.image = image;
    }

    public ListData( int id,String name, String quantity, String category, byte[] image) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.image = image;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
