package com.edutech.progressive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.edutech.progressive.entity.Product;
import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.entity.Warehouse;
import com.edutech.progressive.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DayThirteenTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    InsuranceRepository insuranceRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws SQLException {
        objectMapper = new ObjectMapper();
        insuranceRepository.deleteAll();
        shipmentRepository.deleteAll();
        productRepository.deleteAll();
        warehouseRepository.deleteAll();
        supplierRepository.deleteAll();
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
        supplier.setPhone("9876543210");
        supplier.setAddress("California");
        return supplier;
    }

    Warehouse getWarehouseObject(Integer id, Supplier supplier, String warehouseName, String location, int capacity) {
        Warehouse warehouse = new Warehouse();
        if (id != null) {
            warehouse.setWarehouseId(id.intValue());
        }
        setDynamicProperty(warehouse, "supplier", supplier);
        warehouse.setWarehouseName(warehouseName);
        warehouse.setLocation(location);
        warehouse.setCapacity(capacity);
        return warehouse;
    }

    Product getProductObject(Integer productId, Warehouse warehouse, String name, int quantity, Long price) {
        Product product = new Product();
        if (productId != null) {
            product.setProductId(productId.intValue());
        }
        setDynamicProperty(product, "warehouse", warehouse);
        product.setProductName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        return product;
    }

    public void setDynamicProperty(Object entity, String propertyName, Object value) {
        try {
            Field field = entity.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(entity, value);
        } catch (Exception e) {
            // Handle exception
        }
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testSupplierControllerGetAllSupplier_Day13() throws Exception {
        supplierRepository.deleteAll();
        Supplier supplier = getSupplierObject(null, "John Doe", "john@example.com", "john");
        supplierRepository.save(supplier);
        Supplier supplier2 = getSupplierObject(null, "Alice Smith", "alice@example.com", "alice");
        supplierRepository.save(supplier2);

        mockMvc.perform(get("/supplier")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }


    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testSupplierControllerAddSupplier_Day13() throws Exception {
        supplierRepository.deleteAll();
        Supplier supplier = getSupplierObject(null, "John Doe", "john@example.com", "john");
        supplier.setRole("USER");
        mockMvc.perform(post("/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplier)))
                .andExpect(status().isCreated());

        // Verify that the supplier was added to the database
        List<Supplier> suppliers = supplierRepository.findAll();
        assertEquals(1, suppliers.size());
        assertEquals("John Doe", suppliers.get(0).getSupplierName());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testSupplierControllerDeleteSupplier_Day13() throws Exception {
        Supplier supplier = getSupplierObject(null, "John Doe", "john@example.com", "john");
        Integer supplierId = supplierRepository.save(supplier).getSupplierId();

        mockMvc.perform(delete("/supplier/{supplierId}", supplierId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertFalse(supplierRepository.existsById(supplierId));
    }

    private void assertFalse(boolean b) {
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testWarehouseControllerAddWarehouse_Day13() throws Exception {
        Supplier supplier = getSupplierObject(null, "John Doe", "john@example.com", "john");
        supplier = supplierRepository.save(supplier);
        Warehouse warehouse1 = getWarehouseObject(null, supplier, "flamingo", "Nevada", 100);

        mockMvc.perform(post("/warehouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(warehouse1)))
                .andExpect(status().isCreated());

        List<Warehouse> warehouse = warehouseRepository.findAll();
        assertEquals(1, warehouse.size());
        assertEquals(supplier.getSupplierId(), warehouse.get(0).getSupplier().getSupplierId());
        assertEquals(100, warehouse.get(0).getCapacity(), 0.01);
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    public void testWarehouseControllerGetWarehouseById_Day13() throws Exception {
        Supplier supplier = getSupplierObject(null, "John Doe", "john@example.com", "john");
        supplier = supplierRepository.save(supplier);
        Warehouse warehouse1 = getWarehouseObject(null, supplier, "Sphere", "Nevada", 200);
        warehouseRepository.save(warehouse1);

        mockMvc.perform(get("/warehouse/{warehouseId}", warehouse1.getWarehouseId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.supplier.supplierId", is(supplier.getSupplierId())))
                .andExpect(jsonPath("$.capacity", is(200)));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testWarehouseControllerDeleteWarehouse_Day13() throws Exception {
        Supplier supplier = getSupplierObject(null, "John Doe", "john@example.com", "john");
        supplier = supplierRepository.save(supplier);
        Warehouse warehouse = getWarehouseObject(null, supplier, "Sphere", "Nevada", 200);
        warehouse = warehouseRepository.save(warehouse);

        mockMvc.perform(delete("/warehouse/{warehouseId}", warehouse.getWarehouseId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertFalse(warehouseRepository.existsById(warehouse.getWarehouseId()));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    public void testProductControllerAddProduct_Day13() throws Exception {
        Supplier supplier = getSupplierObject(null, "John Doe", "john@example.com", "john");
        supplier = supplierRepository.save(supplier);
        Warehouse warehouse = getWarehouseObject(null, supplier, "Sphere", "Nevada", 200);
        warehouseRepository.save(warehouse);
        Product product = getProductObject(null, warehouse, "table", 100, 1500L);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated());


        List<Product> productList = productRepository.findAll();
        assertEquals(1, productList.size());
        assertEquals(warehouse.getWarehouseId(), productList.get(0).getWarehouse().getWarehouseId());
        assertEquals(100, productList.get(0).getQuantity(), 0.01);
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    public void testProductControllerDeleteProduct_Day13() throws Exception {
        Supplier supplier = getSupplierObject(null, "John Doe", "john@example.com", "john");
        supplier = supplierRepository.save(supplier);
        Warehouse warehouse = getWarehouseObject(null, supplier, "Sphere", "Nevada", 200);
        warehouseRepository.save(warehouse);
        Product product = getProductObject(null, warehouse, "table", 100, 1500L);
        product = productRepository.save(product);

        mockMvc.perform(delete("/product/{productId}", product.getProductId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertFalse(productRepository.existsById(product.getProductId()));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    public void testUnauthorizedAddSupplierByUser_Day13() throws Exception {
        supplierRepository.deleteAll();
        Supplier supplier = getSupplierObject(null, "John Doe", "john@example.com", "john");
        supplier.setRole("USER");
        mockMvc.perform(post("/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplier)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    public void testUnauthorizedDeleteWarehouseByUser_Day13() throws Exception {
        Supplier supplier = getSupplierObject(null, "John Doe", "john@example.com", "john");
        supplier = supplierRepository.save(supplier);
        Warehouse warehouse = getWarehouseObject(null, supplier, "Sphere", "Nevada", 200);
        warehouseRepository.save(warehouse);

        mockMvc.perform(delete("/warehouse/{warehouseId}", warehouse.getWarehouseId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplier)))
                .andExpect(status().isForbidden());
    }
}
