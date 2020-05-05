package au.edu.utas.ssaadat.assignment2.model;

public class PurchasedData {
    private byte[] image;
    private int id;
    private String name;
    private String quantity;
    private String date;
    private String price;

    public PurchasedData(int id, String name, String quantity,String price,byte[] image,  String date ) {
        this.image = image;
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.price = price;
    }

    public PurchasedData(int id, String name, String quantity,String price,byte[] image ) {
        this.image = image;
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
