package ed.lab.ed1labo04.model;

public class UpdateProductRequest {

    private Double price;
    private int quantity;

    public UpdateProductRequest() {
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}