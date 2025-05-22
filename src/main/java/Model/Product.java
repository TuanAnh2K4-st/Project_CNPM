package Model;

public class Product {
    private String id;
    private String name;
    private double price;
    private ProductType productType;

    private int quantity;

    private Producer producer;

    private String img;

    public Product() {
    }

    public Product(String id, String name, double price, ProductType productType, int quantity, Producer producer, String img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.quantity = quantity;
        this.producer = producer;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", productType=" + productType +
                ", quantity=" + quantity +
                ", producer=" + producer +
                ", img='" + img + '\'' +
                '}';
    }

}
