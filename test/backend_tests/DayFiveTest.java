package com.edutech.progressive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.edutech.progressive.controller.SupplierController;
import com.edutech.progressive.entity.Supplier;
import com.edutech.progressive.service.impl.SupplierServiceImplArraylist;
import com.edutech.progressive.service.impl.SupplierServiceImplJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class DayFiveTest {
   @Mock
   private SupplierServiceImplJpa supplierServiceImplJpa;

   @Mock
   private SupplierServiceImplArraylist supplierServiceImplArraylist;

   @InjectMocks
   private SupplierController supplierController;

   private ObjectMapper objectMapper;

   private MockMvc mockMvc;

   @BeforeEach
   public void setUp() {
       mockMvc = MockMvcBuilders.standaloneSetup(supplierController).build();
       objectMapper = new ObjectMapper();
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

   // Day - 4
//    @Test
//    public void testMainMethodOutput_Day4() {
//        SpringApplication app = new SpringApplication(SupplyLinkApplication.class);
//        app.setDefaultProperties(Collections.singletonMap("server.port", "9999"));
//        ConfigurableApplicationContext context = app.run();
//        assertNotNull(context);
//        context.close();
//    }

   // Day - 5 : Test cases for ArrayList Service methods
   @Test
   void testAddSupplierToArrayList_Day5() throws Exception {
       Supplier supplier = new Supplier(1, "TI", "ti@gmail.com", "9876543210", "texas", "ti12", "password", "USER");
       given(supplierServiceImplArraylist.addSupplier(supplier)).willReturn(1);

       mockMvc.perform(post("/supplier/toArrayList")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(supplier)))
               .andExpect(status().isCreated());
   }

   @Test
   void testGetAllSuppliersSortedByNameFromArrayList_Day5() throws Exception {
       SupplierServiceImplArraylist supplierServiceImplArray = new SupplierServiceImplArraylist();
       supplierServiceImplArray.emptyArrayList();
       Supplier supplier1 = new Supplier(1, "Texas", "ti@gmail.com", "9876543210", "texas", "ti12", "password", "USER");
       Supplier supplier2 = new Supplier(2, "Luxor", "luxor@gmail.com", "9876543210", "texas", "luxor", "password", "USER");
       Supplier supplier3 = new Supplier(3, "AAAAAA", "flamingo@gmail.com", "9876543210", "texas", "flamingo", "password", "USER");
       supplierServiceImplArray.addSupplier(supplier1);
       supplierServiceImplArray.addSupplier(supplier2);
       supplierServiceImplArray.addSupplier(supplier3);

       List<Supplier> result = supplierServiceImplArray.getAllSuppliersSortedByName();

       given(supplierServiceImplArraylist.getAllSuppliersSortedByName()).willReturn(result);
       mockMvc.perform(get("/supplier/fromArrayList/all"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].supplierName").value(supplier3.getSupplierName()));
   }

   @Test
   void testGetAllSupplier_Day5() throws Exception {
       SupplierServiceImplArraylist supplierServiceImplArray = new SupplierServiceImplArraylist();
       supplierServiceImplArray.emptyArrayList();
       Supplier supplier1 = new Supplier(1, "Texas", "ti@gmail.com", "9876543210", "texas", "ti12", "password", "USER");
       Supplier supplier2 = new Supplier(2, "Luxor", "luxor@gmail.com", "9876543210", "texas", "luxor", "password", "USER");
       Supplier supplier3 = new Supplier(3, "AAAAAA", "flamingo@gmail.com", "9876543210", "texas", "flamingo", "password", "USER");
       supplierServiceImplArray.addSupplier(supplier1);
       supplierServiceImplArray.addSupplier(supplier2);
       supplierServiceImplArray.addSupplier(supplier3);

       List<Supplier> result = supplierServiceImplArray.getAllSuppliers();
       given(supplierServiceImplArraylist.getAllSuppliers()).willReturn(result);

       mockMvc.perform(get("/supplier/fromArrayList"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.size()").value(result.size()));
   }

}



