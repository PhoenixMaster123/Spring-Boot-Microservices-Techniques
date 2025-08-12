package org.example.dto;

public class OrderDetails {

    private Order order;

    private Payment payment;

    private Shipping shipping;

    public OrderDetails() {}

    public OrderDetails(Order order, Payment payment, Shipping shipping) {
        this.order = order;
        this.payment = payment;
        this.shipping = shipping;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "order=" + order +
                ", payment=" + payment +
                ", shipping=" + shipping +
                '}';
    }
}
