import java.io.*;
import java.math.BigDecimal;
import java.util.*;

class Product implements Serializable {
    private String name;
    private String unit;
    private int quantity;
    private BigDecimal price;
    private Date arrivalDate;
    private String description;

    public Product(String name, String unit, int quantity, BigDecimal price, Date arrivalDate, String description) {
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
        this.arrivalDate = arrivalDate;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public String getDescription() {
        return description;
    }
}

class ProductContainer implements Serializable, Iterable<Product> {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void updateProduct(Product oldProduct, Product newProduct) {
        int index = products.indexOf(oldProduct);
        if (index >= 0) {
            products.set(index, newProduct);
        }
    }

    public void serialize(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ProductContainer deserialize(String fileName) {
        ProductContainer container = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            container = (ProductContainer) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return container;
    }

    @Override
    public Iterator<Product> iterator() {
        return products.iterator();
    }
}

public class Main {
    public static void main(String[] args) {
        // Create a product container
        ProductContainer container = new ProductContainer();

        // Add products to the container
        container.addProduct(new Product("Товар1", "шт.", 10, new BigDecimal(100), new Date(), "Опис товару 1"));
        container.addProduct(new Product("Товар2", "кг", 5, new BigDecimal(50), new Date(), "Опис товару 2"));

        // Serialize the container
        container.serialize("products.dat");

        // Deserialize the container
        ProductContainer deserializedContainer = ProductContainer.deserialize("products.dat");

        // Check the deserialized products
        for (Product product : deserializedContainer) {
            System.out.println("Найменування: " + product.getName() +
                    ", Кількість: " + product.getQuantity() +
                    ", Ціна: " + product.getPrice());
        }
    }
}
