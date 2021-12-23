package ie.app.models;

public class donation {
    public int id;
    public int amount;
    public String method;

    public donation(int amount, String method) {
        this.amount = amount;
        this.method = method;
    }

    public donation() {
        this.amount = 0;
        this.method = "";
    }

    public String toString() {
        return id + ", " + amount + ", " + method;
    }
}
