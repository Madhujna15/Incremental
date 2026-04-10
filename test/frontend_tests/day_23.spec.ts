import { ComponentFixture, TestBed } from "@angular/core/testing";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { of, throwError } from "rxjs";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { AuthModule } from "../app/auth/auth.module";
import { ActivatedRoute, Router, RouterModule } from "@angular/router";
import { RouterTestingModule } from '@angular/router/testing';
import { WarehouseComponent } from "../app/supplylink/components/warehouse/warehouse.component";
import { SupplyLinkService } from "../app/supplylink/services/supplylink.service";
import { Supplier } from "../app/supplylink/types/Supplier";
import { Warehouse } from "../app/supplylink/types/Warehouse";
import { DashboardComponent } from "../app/supplylink/components/dashboard/dashboard.component";
import { Product } from "../app/supplylink/types/Product";
import { SharedModule } from "../app/shared/shared.module";

describe("WarehouseComponent", () => {
  let component: WarehouseComponent;
  let fixture: ComponentFixture<WarehouseComponent>;
  let supplyLinkService: SupplyLinkService;
  let formBuilder: FormBuilder;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [WarehouseComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule, AuthModule],
      providers: [
        SupplyLinkService,
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: (key: string) => "john",
              },
            },
          },
        },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WarehouseComponent);
    component = fixture.componentInstance;
    supplyLinkService = TestBed.inject(SupplyLinkService);
    formBuilder = TestBed.inject(FormBuilder);
    fixture.detectChanges();
  });

  it("should create the component", () => {
    expect(component).toBeTruthy();
  });

  describe("ngOnInit", () => {
    it("should initialize the warehouseForm with empty fields and validators", () => {
      component.ngOnInit();
      const form = component.warehouseForm;
      expect(form.get("warehouseName")?.value).toEqual("");
      expect(form.get("capacity")?.value).toEqual("");
    });
  });

  describe("onSubmit", () => {
    it("should call SupplyLinkService.addWarehouse with the warehouse data if the form is valid", () => {
      const supplierData: Supplier = new Supplier(1, "John Wane", "johnwane@gmail.com", "9876543210", "texas", "johnwane", "July@101", "USER");

      const warehouseData: Warehouse = new Warehouse(1, supplierData, "Flamingo", "Nevada", 1000);

      spyOn(supplyLinkService, "addWarehouse").and.returnValue(of(warehouseData));

      component.warehouseForm = formBuilder.group({
        warehouseName: ["Flamingo", Validators.required],
        location: ["Nevada", Validators.required],
        capacity: [1000, Validators.required],
        supplier: [supplierData, Validators.required],
      });

      component.onSubmit();

      expect(supplyLinkService.addWarehouse).toHaveBeenCalledWith(jasmine.objectContaining({
        warehouseName: "Flamingo",
        location: "Nevada",
        capacity: 1000,
        supplier: supplierData
      }));
    });
  });
});


describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let supplyLinkService: jasmine.SpyObj<SupplyLinkService>;

  const mockSuppliers: Supplier[] = [new Supplier(1, "John Wane", "johnwane@gmail.com", "9876543210", "texas", "johnwane", "July@101", "USER")];
  const mockWarehouses: Warehouse[] = [new Warehouse(1, new Supplier(1, "John Wane", "johnwane@gmail.com", "9876543210", "texas", "johnwane", "July@101", "USER"), "Flamingo", "Nevada", 1000)];
  const mockProducts: Product[] = [new Product(1, mockWarehouses[0], "table", "table desc", 100, 1500.0)];

  beforeEach(async () => {
    const spySupplyLinkService = jasmine.createSpyObj('SupplyLinkService', [
      'getAllSuppliers', 
      'getAllWarehouses', 
      'getAllProducts', 
      'getSupplierById', 
      'getWarehousesBySupplier', 
      'getAllProductByWarehouse',
      'deleteSupplier',
      'deleteWarehouse'
    ]);
  

    // Mock return values
    spySupplyLinkService.getAllSuppliers.and.returnValue(of(mockSuppliers));
    spySupplyLinkService.getAllWarehouses.and.returnValue(of(mockWarehouses));
    spySupplyLinkService.getAllProducts.and.returnValue(of(mockProducts));
    spySupplyLinkService.getWarehousesBySupplier.and.returnValue(of(mockWarehouses));
    spySupplyLinkService.getSupplierById.and.returnValue(of(mockSuppliers[0]));
    spySupplyLinkService.getAllProductByWarehouse.and.returnValue(of(mockProducts));
  
  
    await TestBed.configureTestingModule({
      declarations: [DashboardComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule, SharedModule, RouterTestingModule.withRoutes([]) ],
      providers: [
        { provide: SupplyLinkService, useValue: spySupplyLinkService },
        {
          provide: ActivatedRoute, // Provide a mock for ActivatedRoute
          useValue: {
            snapshot: {
              paramMap: {
                get: (key: string) => '1', // Mock getting route parameters
              },
            },
          },
        }
      ]
    }).compileComponents();
  
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    supplyLinkService = TestBed.inject(SupplyLinkService) as jasmine.SpyObj<SupplyLinkService>;
  
    fixture.detectChanges();
  });
  

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call loadAdminData if role is ADMIN in ngOnInit', () => {
    spyOn(component, 'loadAdminData');
    localStorage.setItem('role', 'ADMIN');
    component.ngOnInit();
    expect(component.loadAdminData).toHaveBeenCalled();
  });

  it('should load suppliers, warehouses, and products for admin', () => {
    component.loadAdminData();

    expect(component.suppliers).toEqual(mockSuppliers);
    expect(component.warehouses).toEqual(mockWarehouses);
    expect(component.products).toEqual(mockProducts);
  });

});
