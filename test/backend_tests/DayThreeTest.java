package com.edutech.progressive;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.dao.ProductDAOImpl;
import com.edutech.progressive.dao.SupplierDAOImpl;
import com.edutech.progressive.dao.WarehouseDAOImpl;
import com.edutech.progressive.entity.Product;
import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.entity.Warehouse;
import com.edutech.progressive.service.ProductService;
import com.edutech.progressive.service.SupplierService;
import com.edutech.progressive.service.WarehouseService;
import com.edutech.progressive.service.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DayThreeTest {
  private SupplierService supplierService;
  private SupplierServiceImplArraylist supplierServiceImplArraylist;
  private WarehouseService warehouseService;
  private WarehouseServiceImplArraylist warehouseServiceImplArraylist;

  @BeforeEach
  public void setUp() {
      String url = "jdbc:mysql://localhost:3306/mydb";
      String user = "root";
      String password = "root";
      try (Connection connection = DriverManager.getConnection(url, user, password);
           Statement statement = connection.createStatement()) {
          List<String> tableNames = List.of("supplier", "warehouse", "product");
          for (String tableName : tableNames) {
              String deleteQuery = "DELETE FROM " + tableName;
              statement.executeUpdate(deleteQuery);
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }

  // Helper classes to create Objects
  Supplier getSupplierObject(Integer id, String name, String email, String username) {
      Supplier supplier = new Supplier();
      if (id != null) {
          supplier.setSupplierId(id.intValue());
      }
      supplier.setSupplierName(name);
      supplier.setEmail(email);
      supplier.setUsername(username);
      supplier.setPassword("password");
      return supplier;
  }

  Warehouse getWarehouseObject(Integer id, Integer supplierId, String warehouseName, String location, int capacity) {
      Warehouse warehouse = new Warehouse();
      warehouse.setWarehouseId(id);
      warehouse.setSupplierId(supplierId);
      warehouse.setWarehouseName(warehouseName);
      warehouse.setLocation(location);
      warehouse.setCapacity(capacity);
      return warehouse;
  }

// Day 2 Testcases:-
//   @Test
//   public void testAddSupplierToArrayList_Day2() throws SQLException {
//       supplierServiceImplArraylist = new SupplierServiceImplArraylist();
//       supplierServiceImplArraylist.emptyArrayList();
//       Supplier supplier = getSupplierObject(1, "John Doe", "john@example.com", "john");
//       int result = supplierServiceImplArraylist.addSupplier(supplier);
//       assertNotNull(result);
//       assertEquals(result, 1);
//   }

//   @Test
//   public void testGetAllSuppliersSortedByName_Day2() throws SQLException {
//       supplierServiceImplArraylist = new SupplierServiceImplArraylist();
//       supplierServiceImplArraylist.emptyArrayList();
//       Supplier supplier1 = getSupplierObject(1, "John Doe", "john@example.com", "john");
//       Supplier supplier2 = getSupplierObject(2, "Alice Smith", "alice@example.com", "alice");
//       Supplier supplier3 = getSupplierObject(3, "Bob Johnson", "bob@example.com", "bob");

//       supplierServiceImplArraylist.addSupplier(supplier2);
//       supplierServiceImplArraylist.addSupplier(supplier1);
//       supplierServiceImplArraylist.addSupplier(supplier3);

//       List<Supplier> result = supplierServiceImplArraylist.getAllSuppliersSortedByName();
//       assertNotNull(result);
//       assertFalse(result.isEmpty());

//       assertTrue(result.get(0).getSupplierName().compareTo(result.get(1).getSupplierName()) <= 0);
//       assertTrue(result.get(1).getSupplierName().compareTo(result.get(2).getSupplierName()) <= 0);
//   }

//   @Test
//   public void testGetAllSuppliers_Day2() throws SQLException {
//       supplierServiceImplArraylist = new SupplierServiceImplArraylist();
//       supplierServiceImplArraylist.emptyArrayList();
//       Supplier supplier = getSupplierObject(1, "John Doe", "john@example.com", "john");
//       Supplier supplier2 = getSupplierObject(2, "Alice Smith", "alice@example.com", "alice");
//       Supplier supplier3 = getSupplierObject(3, "Bob Johnson", "bob@example.com", "bob");
//       supplierServiceImplArraylist.addSupplier(supplier);
//       supplierServiceImplArraylist.addSupplier(supplier2);
//       supplierServiceImplArraylist.addSupplier(supplier3);
//       List<Supplier> result = supplierServiceImplArraylist.getAllSuppliers();
//       assertNotNull(result);
//       assertEquals(3, result.size());
//   }

//   @Test
//   public void testGetAllWarehouseSortedByName_Day2() throws SQLException {
//       warehouseServiceImplArraylist = new WarehouseServiceImplArraylist();
//       warehouseServiceImplArraylist.emptyArrayList();
//       Warehouse warehouse1 = getWarehouseObject(1, 1, "Flamingo", "California", 200);
//       Warehouse warehouse2 = getWarehouseObject(2, 2, "Cactus", "California", 300);
//       Warehouse warehouse3 = getWarehouseObject(3, 3, "Lans", "California", 500);

//       warehouseServiceImplArraylist.addWarehouse(warehouse1);
//       warehouseServiceImplArraylist.addWarehouse(warehouse2);
//       warehouseServiceImplArraylist.addWarehouse(warehouse3);
//       List<Warehouse> result = warehouseServiceImplArraylist.getWarehousesSortedByCapacity();

//       assertNotNull(result);
//       assertFalse(result.isEmpty());

//       // Check if the list is sorted by name
//       assertTrue(result.get(0).getCapacity() == warehouse1.getCapacity());
//       assertTrue(result.get(1).getCapacity() <= warehouse2.getCapacity());
//   }

//   @Test
//   public void testWarehouseToArrayList_Day2() throws SQLException {
//       warehouseServiceImplArraylist = new WarehouseServiceImplArraylist();
//       warehouseServiceImplArraylist.emptyArrayList();
//       Warehouse warehouse1 = getWarehouseObject(1, 1, "Flamingo", "California", 200);
//       int result = warehouseServiceImplArraylist.addWarehouse(warehouse1);
//       assertNotNull(result);
//       assertEquals(1, result);
//   }

//   @Test
//   public void testGetWarehouseById_Day2() throws SQLException {
//       warehouseServiceImplArraylist = new WarehouseServiceImplArraylist();
//       warehouseServiceImplArraylist.emptyArrayList();
//       Warehouse warehouse1 = getWarehouseObject(1, 1, "Flamingo", "California", 200);
//       int result = warehouseServiceImplArraylist.addWarehouse(warehouse1);
//       assertNotNull(result);
//       assertEquals(1, result);
//   }

//   @Test
//   public void testGetWarehouseByIdNonExistent_Day2() throws SQLException {
//       warehouseServiceImplArraylist = new WarehouseServiceImplArraylist();
//       Warehouse result = warehouseServiceImplArraylist.getWarehouseById(99);
//       assertNull(result);
//   }

//   @Test
//   public void testGetSupplierByIdNonExistent_Day2() throws SQLException {
//       supplierServiceImplArraylist = new SupplierServiceImplArraylist();
//       Supplier result = supplierServiceImplArraylist.getSupplierById(99);
//       assertNull(result);
//   }

  private void insertSampleSupplier() throws SQLException {
      insertSampleSupplier("John Doe", "john@example.com", "johnDoe", "password");
      insertSampleSupplier("Jane Smith", "jane@example.com", "janemsith", "password");
  }

  private int insertSampleSupplier(String supplierName, String email, String username, String password) throws SQLException {
      try (Connection connection = DatabaseConnectionManager.getConnection();
           PreparedStatement statement = connection.prepareStatement(
                   "INSERT INTO supplier (supplier_name, email, username, password) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
          statement.setString(1, supplierName);
          statement.setString(2, email);
          statement.setString(3, username);
          statement.setString(4, password);
          statement.executeUpdate();

          int generatedID = -1;
          try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
              if (generatedKeys.next()) {
                  generatedID = generatedKeys.getInt(1);
              }
          }
          return generatedID;
      }
  }
// Day 3 Testcases
  @Test
  public void testGetAllSuppliers_Day3() throws SQLException {
      supplierService = new SupplierServiceImplJdbc(new SupplierDAOImpl());
      // Insert sample supplier into the test database
      insertSampleSupplier();

      List<Supplier> supplier = supplierService.getAllSuppliers();

      assertNotNull(supplier);
      assertEquals(2, supplier.size()); // Adjust as per your test data
  }


  @Test
  public void testAddSupplier_Day3() throws SQLException {
      supplierService = new SupplierServiceImplJdbc(new SupplierDAOImpl());

      Supplier newSupplier = getSupplierObject(null, "Jane Smith", "jane@example.com", "jhon");

      int id = supplierService.addSupplier(newSupplier);

      Supplier supplier = supplierService.getSupplierById(id);
      assertNotNull(supplier);
      assertEquals(newSupplier.getSupplierId(), supplier.getSupplierId());
      assertEquals(newSupplier.getSupplierName(), supplier.getSupplierName());
      assertEquals(newSupplier.getEmail(), supplier.getEmail());
  }

  @Test
  public void testUpdateSupplier_Day3() throws SQLException {
      supplierService = new SupplierServiceImplJdbc(new SupplierDAOImpl());

      // Insert a sample supplier into the test database
      int supplierId = insertSampleSupplier("Alice Johnson", "alice@example.com", "alicejohn", "password");

      Supplier updatedSuppliers = getSupplierObject(supplierId, "Updated Name", "updated@example.com", "jhon");

      supplierService.updateSupplier(updatedSuppliers);

      Supplier retrievedCustomers = supplierService.getSupplierById(supplierId);

      assertNotNull(retrievedCustomers);
      assertEquals(updatedSuppliers.getSupplierName(), retrievedCustomers.getSupplierName());
      assertEquals(updatedSuppliers.getEmail(), retrievedCustomers.getEmail());
  }

  @Test
  public void testDeleteSupplier_Day3() throws SQLException {
      supplierService = new SupplierServiceImplJdbc(new SupplierDAOImpl());
      // Insert a sample supplier into the test database
      int supplierId = insertSampleSupplier("Bob Brown", "bob@example.com", "bobbrown", "password");
      Supplier savedSupplier = supplierService.getSupplierById(supplierId);
      assertNotNull(savedSupplier);

      supplierService.deleteSupplier(supplierId);

      Supplier deletedSupplier = supplierService.getSupplierById(supplierId);

      assertNull(deletedSupplier);
  }

  @Test
  public void testGetAllSupplierSortedByName_Day3() throws SQLException {
      supplierService = new SupplierServiceImplJdbc(new SupplierDAOImpl());
      // Insert sample supplier into the test database
      insertSampleSupplier();

      List<Supplier> sortedSupplier = supplierService.getAllSuppliersSortedByName();

      assertNotNull(sortedSupplier);
      assertEquals(2, sortedSupplier.size()); // Adjust as per your test data
      assertTrue(sortedSupplier.get(0).getSupplierName().compareTo(sortedSupplier.get(1).getSupplierName()) < 0);
  }

  @Test
  public void testAddWarehouse_Day3() throws SQLException {
      supplierService = new SupplierServiceImplJdbc(new SupplierDAOImpl());
      warehouseService = new WarehouseServiceImplJdbc(new WarehouseDAOImpl());

      Supplier supplier = getSupplierObject(0, "John Doe", "john.example.com", "jhon");
      int supplierId = supplierService.addSupplier(supplier);
      Warehouse warehousesToAdd = new Warehouse(0, supplierId, "Trust store", "San Francisco", 100);
      int generatedAccountId = warehouseService.addWarehouse(warehousesToAdd);

      Warehouse retrievedWarehouse = warehouseService.getWarehouseById(generatedAccountId);

      assertNotNull(retrievedWarehouse);
      assertEquals(generatedAccountId, retrievedWarehouse.getWarehouseId());
      assertEquals(100, retrievedWarehouse.getCapacity());
  }

  @Test
  public void testUpdateWarehouse_Day3() throws SQLException {
      supplierService = new SupplierServiceImplJdbc(new SupplierDAOImpl());
      warehouseService = new WarehouseServiceImplJdbc(new WarehouseDAOImpl());

      Supplier supplier = getSupplierObject(0, "John Doe", "john.example.com", "jhon");
      int supplierId = supplierService.addSupplier(supplier);
      Warehouse warehousesToAdd = new Warehouse();
      warehousesToAdd.setSupplierId(supplierId);
      warehousesToAdd.setWarehouseName("Flamingo");
      warehousesToAdd.setLocation("California");
      warehousesToAdd.setCapacity(100);

      int generatedWarehouseId = warehouseService.addWarehouse(warehousesToAdd);

      Warehouse retrievedWarehouse = warehouseService.getWarehouseById(generatedWarehouseId);

      retrievedWarehouse.setCapacity(150);
      warehouseService.updateWarehouse(retrievedWarehouse);

      Warehouse updatedAccounts = warehouseService.getWarehouseById(generatedWarehouseId);

      assertNotNull(updatedAccounts);
      assertEquals(generatedWarehouseId, updatedAccounts.getWarehouseId());
      assertEquals(150, updatedAccounts.getCapacity());
  }

  @Test
  public void testDeleteWarehouse_Day3() throws SQLException {
      supplierService = new SupplierServiceImplJdbc(new SupplierDAOImpl());
      warehouseService = new WarehouseServiceImplJdbc(new WarehouseDAOImpl());

      Supplier supplier = getSupplierObject(0, "John Doe", "john.example.com", "jhon");
      int supplierId = supplierService.addSupplier(supplier);
      assertNotEquals(-1, supplierId);

      Warehouse warehousesToAdd = getWarehouseObject(0, supplierId, "Flamingo", "California", 1000);
      int generatedWarehouseId = warehouseService.addWarehouse(warehousesToAdd);

      warehouseService.deleteWarehouse(generatedWarehouseId);

      assertNull(warehouseService.getWarehouseById(generatedWarehouseId));
  }

  @Test
  public void testGetAllWarehouses_Day3() throws SQLException {
      supplierService = new SupplierServiceImplJdbc(new SupplierDAOImpl());
      warehouseService = new WarehouseServiceImplJdbc(new WarehouseDAOImpl());

      Supplier supplier = getSupplierObject(0, "John Doe", "john.example.com", "jhon");
      int supplierId = supplierService.addSupplier(supplier);

      Warehouse warehouses1 = getWarehouseObject(0, supplierId, "Flamingo", "California", 100);
      Warehouse warehouses2 = getWarehouseObject(0, supplierId, "Cactus", "California", 200);
      Warehouse warehouses3 = getWarehouseObject(0, supplierId, "Lans", "California", 300);
      warehouseService.addWarehouse(warehouses1);
      warehouseService.addWarehouse(warehouses2);
      warehouseService.addWarehouse(warehouses3);

      List<Warehouse> allWarehouse = warehouseService.getAllWarehouses();

      assertNotNull(allWarehouse);
      assertTrue(allWarehouse.size() == 3);
  }

  @Test
  public void testGetAllWarehouseSortedByBalance_Day3() throws SQLException {
      supplierService = new SupplierServiceImplJdbc(new SupplierDAOImpl());
      warehouseService = new WarehouseServiceImplJdbc(new WarehouseDAOImpl());

      Supplier supplier = getSupplierObject(0, "John Doe", "john.example.com", "joe");
      int supplierId = supplierService.addSupplier(supplier);
      List<Warehouse> unsortedWarehouse = new ArrayList<>();
      unsortedWarehouse.add(getWarehouseObject(1, supplierId, "cactus", "California", 50));
      unsortedWarehouse.add(getWarehouseObject(2, supplierId, "Flamingo", "California", 20));
      unsortedWarehouse.add(getWarehouseObject(3, supplierId, "lans", "California", 70));

      for (Warehouse warehouse : unsortedWarehouse) {
          warehouseService.addWarehouse(warehouse);
      }

      List<Warehouse> sortedWarehouses = warehouseService.getWarehousesSortedByCapacity();

      assertNotNull(sortedWarehouses);
      assertEquals(sortedWarehouses.get(0).getCapacity(), 20);
      assertEquals(sortedWarehouses.get(1).getCapacity(), 50);
      assertEquals(sortedWarehouses.get(2).getCapacity(), 70);
  }

  @Test
  public void testGetAllProducts_Day3() throws SQLException {
      supplierService = new SupplierServiceImplJdbc(new SupplierDAOImpl());
      warehouseService = new WarehouseServiceImplJdbc(new WarehouseDAOImpl());
      ProductService productService = new ProductServiceImplJdbc(new ProductDAOImpl());

      Supplier supplier = getSupplierObject(0, "John Doe", "john.example.com", "jhon");
      int supplierId = supplierService.addSupplier(supplier);

      Warehouse warehouses1 = getWarehouseObject(0, supplierId, "Flamingo", "California", 100);
      int warehouseId = warehouseService.addWarehouse(warehouses1);

      List<Product> products = new ArrayList<>();
      products.add(new Product(1, warehouseId, "table", "table desc", 10, 1500L));
      products.add(new Product(1, warehouseId, "chair", "chair desc", 50, 500L));
      products.add(new Product(1, warehouseId, "cup", "cup desc", 100, 200L));

      // Insert the Product into the database (or mock)
      for (Product product : products) {
          productService.addProduct(product);
      }

      // Call the method to get all Products
      List<Product> retrievedProducts = productService.getAllProducts();
      assertNotNull(retrievedProducts);
      // Assert that the retrieved Products match the expected Products
      assertEquals(retrievedProducts.size(), 3);
  }

  @Test
  public void testGetProductById_Day3() throws SQLException {
      supplierService = new SupplierServiceImplJdbc(new SupplierDAOImpl());
      warehouseService = new WarehouseServiceImplJdbc(new WarehouseDAOImpl());
      ProductService productService = new ProductServiceImplJdbc(new ProductDAOImpl());

      Supplier supplier = getSupplierObject(0, "John Doe", "john.example.com", "jhon");
      int supplierId = supplierService.addSupplier(supplier);

      Warehouse warehouses1 = getWarehouseObject(0, supplierId, "Flamingo", "California", 100);
      int warehouseId = warehouseService.addWarehouse(warehouses1);

      Product product = new Product(1, warehouseId, "table", "table desc", 10, 1500L);
      int productId = productService.addProduct(product);

      // Call the method to get the Product by ID
      Product retrievedProduct = productService.getProductById(productId);

      assertNotNull(retrievedProduct);
      // Assert that the retrieved Product matches the expected Product
      assertEquals(product.getWarehouseId(), retrievedProduct.getWarehouseId());
      assertEquals(product.getProductName(), retrievedProduct.getProductName());
  }

  @Test
  public void testUpdateProduct_Day3() throws SQLException {
      supplierService = new SupplierServiceImplJdbc(new SupplierDAOImpl());
      warehouseService = new WarehouseServiceImplJdbc(new WarehouseDAOImpl());
      ProductService productService = new ProductServiceImplJdbc(new ProductDAOImpl());

      Supplier supplier = getSupplierObject(0, "John Doe", "john.example.com", "jhon");
      int supplierId = supplierService.addSupplier(supplier);

      Warehouse warehouses1 = getWarehouseObject(0, supplierId, "Flamingo", "California", 100);
      int warehouseId = warehouseService.addWarehouse(warehouses1);

      Product product = new Product(1, warehouseId, "table", "table desc", 10, 1500L);

      // Insert the product into the database (or mock)
      int productId = productService.addProduct(product);

      // Modify the product
      product.setPrice(2000L);

      // Update the transaction
      productService.updateProduct(product);

      // Retrieve the updated product
      Product updatedProduct = productService.getProductById(productId);
      assertNotNull(updatedProduct);
      // Assert that the updated transaction matches the modified transaction
      assertEquals(product.getPrice(), updatedProduct.getPrice());
  }

  @Test
  public void testDeleteProduct_Day3() throws SQLException {
      supplierService = new SupplierServiceImplJdbc(new SupplierDAOImpl());
      warehouseService = new WarehouseServiceImplJdbc(new WarehouseDAOImpl());
      ProductService productService = new ProductServiceImplJdbc(new ProductDAOImpl());

      Supplier supplier = getSupplierObject(0, "John Doe", "john.example.com", "jhon");
      int supplierId = supplierService.addSupplier(supplier);

      Warehouse warehouses1 = getWarehouseObject(0, supplierId, "Flamingo", "California", 100);
      int warehouseId = warehouseService.addWarehouse(warehouses1);

      Product product = new Product(1, warehouseId, "table", "table desc", 10, 1500L);

      // Insert the product into the database (or mock)
      int productId = productService.addProduct(product);
      assertNotNull(productId);
      assertNotEquals(-1, productId);

      productService.deleteProduct(productId);

      assertNull(productService.getProductById(productId));
  }
}
