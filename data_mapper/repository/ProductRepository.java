package hw.data_mapper.repository;

import hw.data_mapper.entity.Product;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ProductRepository implements Repository<Product> {
    private static final ThreadLocal<Map<String, Object>> identityMap = new ThreadLocal<>();
    private static ProductRepository instance;

    public static void main(String[] args) {
        ProductRepository repository = ProductRepository.getInstance();
        System.out.println(repository.findById(2));
        System.out.println(repository.findById(2));
        Product product = new Product();
        product.setId(16);
        product.setTitle("New33");
        product.setCost(121231);
        System.out.println(repository.delete(product));

    }

    private ProductRepository() {
        identityMap.set(new HashMap<>());
    }

    public static ProductRepository getInstance() {
        if (instance == null)
            instance = new ProductRepository();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.
                getConnection("jdbc:mysql://localhost:3306/learning", "root", "root");
    }

    @Override
    public Product findById(Integer id) {
        String query = "SELECT * FROM products where id = " + id;
        if (identityMap.get().containsKey(query)) {
            System.out.println("fin in map");
            return (Product) identityMap.get().get(query);
        }
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("find in db");
            if (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setTitle(resultSet.getString("title"));
                product.setCost(resultSet.getInt("cost"));
                identityMap.get().put(query, product);
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Product findByTitle(String title) {
        String query = "SELECT * FROM products where title = \"" + title + "\"";
        if (identityMap.get().containsKey(query)) {
            return (Product) identityMap.get().get(query);
        }
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setTitle(resultSet.getString("title"));
                product.setCost(resultSet.getInt("cost"));
                identityMap.get().put(query, product);
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer save(Product entity) {
        String query = "Insert into products (`title`,`cost`) " +
                "values (\"" + entity.getTitle() + "\", " + entity.getCost() + ")";

        try (Statement statement = getConnection().createStatement()) {
            if (statement.executeUpdate(query) != -1)
                return findByTitle(entity.getTitle()).getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Product update(Product entity) {
        String query = "UPDATE products SET title=\"" + entity.getTitle() +
                "\",cost=\"" + entity.getCost() + "\" WHERE id=" + entity.getId();
        try (Statement statement = getConnection().createStatement()) {
            if (statement.executeUpdate(query) != -1)
                return findById(entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product delete(Product entity) {
        String query = "Delete from products where id=" + entity.getId();
        try (Statement statement = getConnection().createStatement()) {
            if (statement.executeUpdate(query) != -1)
                return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
