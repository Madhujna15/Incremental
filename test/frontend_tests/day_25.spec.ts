import { ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { SupplierEditComponent } from '../app/supplylink/components/supplieredit/supplieredit.component';
import { SupplyLinkService } from '../app/supplylink/services/supplylink.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { Supplier } from '../app/supplylink/types/Supplier';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Component, DebugElement } from '@angular/core';
import { SharedModule } from '../app/shared/shared.module';
import { WarehouseEditComponent } from '../app/supplylink/components/warehouseedit/warehouseedit.component';
import { Warehouse } from '../app/supplylink/types/Warehouse';
import { DashboardComponent } from '../app/supplylink/components/dashboard/dashboard.component';

@Component({ template: 'supplylink' })
class MockDashboardComponent {}

describe('SupplierEditComponent', () => {
  let component: SupplierEditComponent;
  let fixture: ComponentFixture<SupplierEditComponent>;
  let supplyLinkService: jasmine.SpyObj<SupplyLinkService>;

  beforeEach(async () => {
    const spySupplyLinkService = jasmine.createSpyObj('SupplyLinkService', ['getSupplierById', 'editSupplier']);
    spySupplyLinkService.editSupplier.and.returnValue(of({}));
    spySupplyLinkService.getSupplierById.and.returnValue(of({
        supplierId: 1,
        supplierName: 'Supplier 1',
        email: 'supplier1@test.com',
        phone: '1234567890',
        address: 'Supplier Address',
        username: 'supplier1',
        password: 'password123',
        role: 'USER'
      }));

    await TestBed.configureTestingModule({
      declarations: [SupplierEditComponent],
      imports: [
        ReactiveFormsModule, 
        HttpClientTestingModule, 
        SharedModule,
        RouterTestingModule.withRoutes([
            {path: 'supplylink', component: MockDashboardComponent}
        ])
      ],
      providers: [
        FormBuilder,
        { provide: SupplyLinkService, useValue: spySupplyLinkService }, 
        {
          provide: ActivatedRoute, 
          useValue: {
            params: of({ supplierId: 1 }) // Mock params observable
          }
        },
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(SupplierEditComponent);
    component = fixture.componentInstance;
    supplyLinkService = TestBed.inject(SupplyLinkService) as jasmine.SpyObj<SupplyLinkService>;
    fixture.detectChanges(); // Triggers ngOnInit
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the form with required controls', () => {
    expect(component.supplierForm.contains('supplierName')).toBeTrue();
    expect(component.supplierForm.contains('phone')).toBeTrue();
    expect(component.supplierForm.contains('address')).toBeTrue();
    expect(component.supplierForm.contains('username')).toBeTrue();
    expect(component.supplierForm.contains('password')).toBeTrue();
    expect(component.supplierForm.contains('role')).toBeTrue();
  });

  it('should load supplier details on init', () => {
    const mockSupplier: Supplier = {
      supplierId: 1,
      supplierName: 'Test Supplier',
      email: 'test@supplier.com',
      phone: '1234567890',
      address: 'Test Address',
      username: 'testuser',
      password: 'password123',
      role: 'USER'
    };

    supplyLinkService.getSupplierById.and.returnValue(of(mockSupplier));

    component.loadSupplierDetails();
    
    expect(supplyLinkService.getSupplierById).toHaveBeenCalledWith(1);
    expect(component.supplierForm.value.supplierName).toEqual(mockSupplier.supplierName);
  });

  it('should update supplier when form is valid and submitted', () => {

    component.supplierForm.patchValue({
       supplierName: 'Updated Supplier',
       phone: '9876543210',
       address: 'Updated Address',
       username: 'updateduser',
       password: 'password123',
       role: 'ADMIN'
    });
 
    component.onSubmit();

    expect(supplyLinkService.editSupplier).toHaveBeenCalledWith(
        jasmine.objectContaining({
            supplierId: 1,
            supplierName: 'Updated Supplier',
            phone: '9876543210',
            address: 'Updated Address',
            username: 'updateduser',
            password: 'password123',
            role: 'ADMIN'
          })
        );
    });
 

  it('should not update supplier if the form is invalid', () => {
    component.supplierForm.patchValue({
      supplierName: '',
      email: '',
      phone: '',
      address: '',
      username: '',
      role: ''
    });

    component.onSubmit();

    expect(supplyLinkService.editSupplier).not.toHaveBeenCalled();
  });
  
});

describe("WarehouseEditComponent", () => {
  let component: WarehouseEditComponent;
  let fixture: ComponentFixture<WarehouseEditComponent>;
  let supplyLinkService: jasmine.SpyObj<SupplyLinkService>;

  beforeEach(async () => {
    const spySupplyLinkService = jasmine.createSpyObj('SupplyLinkService', ['getWarehouseById', 'editWarehouse']);
    spySupplyLinkService.editWarehouse.and.returnValue(of({}));
    spySupplyLinkService.getWarehouseById.and.returnValue(of({ 
        warehouseId: 1,
        warehouseName: 'Test Warehouse',
        location: 'Test Location',
        capacity: 100,
        supplier: {
          supplierId: 1,
          supplierName: 'Test Supplier'
        } as Supplier
    }));

    await TestBed.configureTestingModule({
      declarations: [WarehouseEditComponent],
      imports: [
        ReactiveFormsModule, 
        HttpClientTestingModule, 
        SharedModule,
        RouterTestingModule.withRoutes([
            {path: 'supplylink', component: MockDashboardComponent}
        ])
      ],
      providers: [
        FormBuilder,
        { provide: SupplyLinkService, useValue: spySupplyLinkService }, 
        {
          provide: ActivatedRoute, 
          useValue: {
            params: of({ warehouseId: 1 })
          }
        },
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(WarehouseEditComponent);
    component = fixture.componentInstance;
    supplyLinkService = TestBed.inject(SupplyLinkService) as jasmine.SpyObj<SupplyLinkService>;
    fixture.detectChanges(); 
    });
  
    it("should create the component", () => {
      expect(component).toBeTruthy();
    });

    it('should load warehouse details on init', () => {
        const mockWarehouse: Warehouse = {
          warehouseId: 1,
          warehouseName: 'Test Warehouse',
          location: 'Test Location',
          capacity: 100,
          supplier: {
            supplierId: 1,
            supplierName: 'Test Supplier'
          } as Supplier
        };

        supplyLinkService.getWarehouseById.and.returnValue(of(mockWarehouse));

        component.loadWarehouseDetails();
    
        expect(supplyLinkService.getWarehouseById).toHaveBeenCalledWith(1);
        expect(component.warehouseForm.value.warehouseName).toEqual(mockWarehouse.warehouseName);
        expect(component.warehouseForm.value.location).toEqual(mockWarehouse.location);
    });

    it('should update warehouse when form is valid and submitted', () => {
        
        component.warehouseForm.patchValue({
          warehouseName: 'Updated Warehouse',
          location: 'Updated Location',
          capacity: 150,
          supplier: {
            supplierId: 1,
            supplierName: 'Test Supplier'
          } as Supplier
        });
    
        component.onSubmit();
    
        expect(supplyLinkService.editWarehouse).toHaveBeenCalledWith(
            jasmine.objectContaining({
                warehouseId: 1,
                warehouseName: 'Updated Warehouse',
                location: 'Updated Location',
                capacity: 150,
                supplier: {
                    supplierId: 1,
                    supplierName: 'Test Supplier'
                } as Supplier
              })
        );
    });
    
});

describe("DeleteFunctionaility", () => {
    let component: DashboardComponent;
    let fixture: ComponentFixture<DashboardComponent>;
    let supplyLinkService: jasmine.SpyObj<SupplyLinkService>;
   
    beforeEach(async () => {
        const spySupplyLinkService = jasmine.createSpyObj('SupplyLinkService', ['deleteWarehouse']);
        spySupplyLinkService.deleteWarehouse.and.returnValue(of({}));
        await TestBed.configureTestingModule({
          declarations: [DashboardComponent],
          providers: [
            { provide: SupplyLinkService, useValue: spySupplyLinkService }
          ],
          imports: [ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule, SharedModule]
        }).compileComponents();
      
        fixture = TestBed.createComponent(DashboardComponent);
        component = fixture.componentInstance;
        supplyLinkService = TestBed.inject(SupplyLinkService) as jasmine.SpyObj<SupplyLinkService>;
    });
  
    it("should create the component", () => {
      expect(component).toBeTruthy();
    });
  
    it('should call deleteWarehouse when Delete button is clicked', () => {
        spyOn(window, 'confirm').and.returnValue(true);
        
        spyOn(component, 'loadAdminData');

        const mockWarehouse = { warehouseId: 1 };

        supplyLinkService.deleteWarehouse.and.returnValue(of({}));

        component.deleteWarehouse(mockWarehouse);

        expect(supplyLinkService.deleteWarehouse).toHaveBeenCalledWith(1);
    });
  
});
