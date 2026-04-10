package com.edutech.progressive;

import com.edutech.progressive.entity.Product;
import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.entity.Warehouse;
import com.edutech.progressive.exception.NoWarehouseFoundForSupplierException;
import com.edutech.progressive.exception.SupplierAlreadyExistsException;
import com.edutech.progressive.repository.ProductRepository;
import com.edutech.progressive.repository.SupplierRepository;
import com.edutech.progressive.repository.WarehouseRepository;
import com.edutech.progressive.service.SupplierService;
import com.edutech.progressive.service.WarehouseService;
import com.edutech.progressive.service.impl.SupplierServiceImplJpa;
import com.edutech.progressive.service.impl.WarehouseServiceImplJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SupplyLinkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DayNineTest {


    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
    public void testSupplierAlreadyExistsException_Day9() throws SQLException {
        SupplierService supplierService = new SupplierServiceImplJpa(supplierRepository);
        Supplier supplier = getSupplierObject(null, "John", "john@gmail.com", "john123");
        supplierRepository.save(supplier);

        assertThrows(SupplierAlreadyExistsException.class, () -> {
            supplierService.addSupplier(supplier);
        });
    }

    @Test
    public void testNoWarehouseFoundForSupplierException_Day9() throws SQLException {
        SupplierService supplierService = new SupplierServiceImplJpa(supplierRepository);
        WarehouseService warehouseService = new WarehouseServiceImplJpa(warehouseRepository);
        Supplier supplier = getSupplierObject(null, "John", "john@gmail.com", "john123");
        int suppId = supplierService.addSupplier(supplier);

        assertThrows(NoWarehouseFoundForSupplierException.class, () -> {
            warehouseService.getWarehouseBySupplier(suppId);
        });
    }

}
