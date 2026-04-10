package com.edutech.progressive;

import com.edutech.progressive.SupplyLinkApplication;
import com.edutech.progressive.entity.*;
import com.edutech.progressive.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SupplyLinkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DayElevenTest {

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

    public Shipment getShipmentObject(Integer id, Product product, Warehouse warehouse, String source, String destination, String status) {
        Shipment shipment = new Shipment();
        if (id != null) {
            shipment.setShipmentId(id.intValue());
        }  // Optional if using auto-generated ID
        setDynamicProperty(shipment, "product", product);
        setDynamicProperty(shipment, "warehouse", warehouse);
        shipment.setShipmentDate(new Date(System.currentTimeMillis()));  // Current date
        shipment.setExpectedDeliveryDate(new Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000L));  // 5 days later
        shipment.setSourceLocation(source);
        shipment.setDestinationLocation(destination);
        shipment.setStatus(status);
        return  shipment;
    }

    public Insurance getInsuranceObject(Integer id, Shipment shipment, String insuranceProvider, Long amt) {
        Insurance insurance = new Insurance();
        if (id != null) {
            insurance.setInsuranceId(id);
        }
        setDynamicProperty(insurance, "shipment", shipment);
        insurance.setInsuranceProvider(insuranceProvider);
        insurance.setInsuranceCoverageAmount(amt);
        return insurance;
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
    public void testGetAllInsurance_Day11() throws Exception {
        Supplier supplier = getSupplierObject(null, "Flamingo", "flamingo@gmail.com", "flamingo");
        supplier = supplierRepository.save(supplier);
        Warehouse warehouse = getWarehouseObject(null, supplier, "texas", "texas", 100);
        warehouse = warehouseRepository.save(warehouse);
        Product product1 = getProductObject(null, warehouse, "table", 100, 1500L);
        product1 = productRepository.save(product1);
        Shipment shipment1 = getShipmentObject(null, product1, warehouse, "Los Angeles", "NYC", "Booked");
        Shipment shipment2 = getShipmentObject(null, product1, warehouse, "Los Angeles", "LAs Vegas", "Booked");
        shipment1 = shipmentRepository.save(shipment1);
        shipment2 = shipmentRepository.save(shipment2);
        Insurance insurance1 = getInsuranceObject(null, shipment1, "Jio", 10000L);
        Insurance insurance2 = getInsuranceObject(null, shipment2, "Tata", 150000L);
        insurance1 = insuranceRepository.save(insurance1);
        insurance2 = insuranceRepository.save(insurance2);

        mockMvc.perform(get("/insurance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].insuranceId").value(insurance1.getInsuranceId()))
                .andExpect(jsonPath("$[1].insuranceId").value(insurance2.getInsuranceId()))
                .andDo(print());
    }

    @Test
    public void testGetInsuranceById_Success_Day11() throws Exception {
        // Mock Service Layer
        Supplier supplier = getSupplierObject(null, "Flamingo", "flamingo@gmail.com", "flamingo");
        supplier = supplierRepository.save(supplier);
        Warehouse warehouse = getWarehouseObject(null, supplier, "texas", "texas", 100);
        warehouse = warehouseRepository.save(warehouse);
        Product product1 = getProductObject(null, warehouse, "table", 100, 1500L);
        product1 = productRepository.save(product1);
        Shipment shipment1 = getShipmentObject(null, product1, warehouse, "Los Angeles", "NYC", "Booked");
        shipment1 = shipmentRepository.save(shipment1);

        Insurance insurance1 = getInsuranceObject(null, shipment1, "Jio", 10000L);
        insurance1 = insuranceRepository.save(insurance1);

        // Perform GET Request and Validate Response
        mockMvc.perform(get("/insurance/" + insurance1.getInsuranceId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.insuranceId").value(insurance1.getInsuranceId()))
                .andExpect(jsonPath("$.insuranceProvider").value(insurance1.getInsuranceProvider()))
                .andDo(print());
    }

    @Test
    public void testGetInsuranceById_NotFound_Day11() throws Exception {
        mockMvc.perform(get("/insurance/999"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testAddInsurance_Day11() throws Exception {
        Supplier supplier = getSupplierObject(null, "Flamingo", "flamingo@gmail.com", "flamingo");
        supplier = supplierRepository.save(supplier);
        Warehouse warehouse = getWarehouseObject(null, supplier, "texas", "texas", 100);
        warehouse = warehouseRepository.save(warehouse);
        Product product1 = getProductObject(null, warehouse, "table", 100, 1500L);
        product1 = productRepository.save(product1);
        Shipment shipment1 = getShipmentObject(null, product1, warehouse, "Los Angeles", "NYC", "Booked");
        shipment1 = shipmentRepository.save(shipment1);
        Insurance insurance1 = getInsuranceObject(null, shipment1, "Jio", 10000L);

        mockMvc.perform(post("/insurance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(insurance1)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

}
