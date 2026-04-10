import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DashboardComponent } from '../app/supplylink/components/dashboard/dashboard.component';
import { SupplyLinkService } from '../app/supplylink/services/supplylink.service';
import { ActivatedRoute, Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { Warehouse } from '../app/supplylink/types/Warehouse';
import { Product } from '../app/supplylink/types/Product';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { SharedModule } from '../app/shared/shared.module';
import { RouterTestingModule } from '@angular/router/testing';
import { ProductComponent } from '../app/supplylink/components/product/product.component';
import { DebugElement } from '@angular/core';

describe('DashboardComponent', () => {
    let component: DashboardComponent;
    let fixture: ComponentFixture<DashboardComponent>;
    let supplyLinkService: jasmine.SpyObj<SupplyLinkService>;

    beforeEach(async () => {
        const spySupplyLinkService = jasmine.createSpyObj('SupplyLinkService', ['getSupplierById',
            'getAllSuppliers',
            'getWarehousesBySupplier',
            'getAllProductByWarehouse',
            'addProduct',
            'editProduct',
            'deleteProduct', 'getSupplierById']);

        // Return observables with mock data
        spySupplyLinkService.getSupplierById.and.returnValue(of({ supplierId: 1, supplierName: 'Supplier 1' }));
        spySupplyLinkService.getAllSuppliers.and.returnValue(of([{ supplierId: 1, supplierName: 'Supplier 1' }]));
        spySupplyLinkService.getWarehousesBySupplier.and.returnValue(of([{ warehouseId: 1, warehouseName: 'Warehouse 1', supplier: { supplierId: 1, supplierName: 'Supplier 1' } }]));
        spySupplyLinkService.getAllProductByWarehouse.and.returnValue(of([{ productId: 1, productName: 'Product 1', quantity: 10, price: 100, warehouse: { warehouseId: 1, warehouseName: 'Warehouse 1', supplier: { supplierId: 1, supplierName: 'Supplier 1' } } }]));
        spySupplyLinkService.addProduct.and.returnValue(of({}));
        spySupplyLinkService.editProduct.and.returnValue(of({}));
        spySupplyLinkService.deleteProduct.and.returnValue(of({}));

        await TestBed.configureTestingModule({
            declarations: [DashboardComponent],
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
            ],
            imports: [ReactiveFormsModule, HttpClientTestingModule, SharedModule, RouterTestingModule.withRoutes([])] // Include necessary imports
        }).compileComponents();

        fixture = TestBed.createComponent(DashboardComponent);
        component = fixture.componentInstance;
        supplyLinkService = TestBed.inject(SupplyLinkService) as jasmine.SpyObj<SupplyLinkService>;

        fixture.detectChanges();
    });

    it('should create the component', () => {
        expect(component).toBeTruthy();
    });

    it('should call loadUserData if role is USER in ngOnInit', () => {
        spyOn(component, 'loadUserData');
        localStorage.setItem('role', 'USER');
        component.ngOnInit();
        expect(component.loadUserData).toHaveBeenCalled();
    });

    it('should open the popup and patch the form with product data', () => {
        const product = { productName: 'Test Product', quantity: 10, price: 100, productDescription: 'Test Description' } as Product;
        spyOn(component.productForm, 'patchValue');

        component.editProduct(product);

        expect(component.showProductPopup).toBeTrue();
        expect(component.currentProduct).toEqual(product);
        expect(component.productForm.patchValue).toHaveBeenCalledWith({
            productName: product.productName,
            productDescription: product.productDescription,
            quantity: product.quantity,
            price: product.price
        });
    });

    it('should update the product when form is valid', () => {
        component.productForm = new FormBuilder().group({
            productName: ['Updated Product'],
            productDescription: ['Updated Description'],
            quantity: [20],
            price: [200]
        });

        const product = { productId: 1, productName: 'Product 1', quantity: 10, price: 100, warehouse: { warehouseId: 1 , supplier: {supplierId: 1}} } as Product;
        component.currentProduct = product;
        component.selectedWarehouseId = 1;

        component.onProductFormSubmit();
        const expectedProduct = {
            productId: 1,
            productName: 'Updated Product',
            productDescription: 'Updated Description',
            quantity: 20,
            price: 200,
            warehouse: { warehouseId: 1 , supplier: {supplierId: 1}}
        };

        expect(supplyLinkService.editProduct).toHaveBeenCalledWith(jasmine.objectContaining(expectedProduct));
    });

});

describe('ProductComponent', () => {
    let component: ProductComponent;
    let fixture: ComponentFixture<ProductComponent>;
    let supplyLinkService: jasmine.SpyObj<SupplyLinkService>;
    let debugElement: DebugElement;

    beforeEach(async () => {
        const spy = jasmine.createSpyObj('SupplyLinkService', ['getWarehousesBySupplier', 'addProduct']);

        await TestBed.configureTestingModule({
            declarations: [ProductComponent],
            imports: [ReactiveFormsModule, FormsModule, HttpClientTestingModule, SharedModule],
            providers: [
                { provide: SupplyLinkService, useValue: spy },
                {
                    provide: ActivatedRoute,
                    useValue: { snapshot: { paramMap: { get: () => '1' } } } // Mock ActivatedRoute
                }
            ]
        }).compileComponents();

        fixture = TestBed.createComponent(ProductComponent);
        component = fixture.componentInstance;
        supplyLinkService = TestBed.inject(SupplyLinkService) as jasmine.SpyObj<SupplyLinkService>;
        debugElement = fixture.debugElement;

        // Mocking the return of getWarehousesBySupplier method
        supplyLinkService.getWarehousesBySupplier.and.returnValue(of([{ warehouseId: 1, warehouseName: 'Warehouse 1' } as Warehouse]));
        fixture.detectChanges(); // Trigger component initialization
    });

    it('should create the component', () => {
        expect(component).toBeTruthy();
    });

    it('should initialize form with required controls', () => {
        expect(component.productForm.contains('warehouse')).toBeTrue();
        expect(component.productForm.contains('productName')).toBeTrue();
        expect(component.productForm.contains('productDescription')).toBeTrue();
        expect(component.productForm.contains('quantity')).toBeTrue();
        expect(component.productForm.contains('price')).toBeTrue();
    });

    it('should fetch warehouses for the supplier on init', () => {
        expect(supplyLinkService.getWarehousesBySupplier).toHaveBeenCalled();
        component.warehouses.subscribe((warehouses) => {
            expect(warehouses.length).toBeGreaterThan(0);
            expect(warehouses[0].warehouseName).toBe('Warehouse 1');
        });
    });

    it('should call addProduct when form is valid and submitted', () => {
        component.productForm.patchValue({
            warehouse: { warehouseId: 1, warehouseName: 'Warehouse 1' } as Warehouse,
            productName: 'Product 1',
            productDescription: 'Product desc',
            quantity: 10,
            price: 100
        });

        const mockProduct = new Product(1, { warehouseId: 1, warehouseName: 'Warehouse 1' } as Warehouse, 'Product 1', 'Product desc', 10, 100);
        supplyLinkService.addProduct.and.returnValue(of(mockProduct));

        component.onSubmit();
        expect(supplyLinkService.addProduct).toHaveBeenCalledWith(jasmine.objectContaining({
            productName: 'Product 1',
            productDescription: 'Product desc',
            quantity: 10,
            price: 100
        }));

        component.productSuccess.subscribe(successMessage => {
            expect(successMessage).toBe('Product created successfully');
        });
    });

    it('should handle error when addProduct fails', () => {
        component.productForm.patchValue({
            warehouse: { warehouseId: 1, warehouseName: 'Warehouse 1' } as Warehouse,
            productName: 'Product 1',
            productDescription: 'Product desc',
            quantity: 10,
            price: 100
        });

        supplyLinkService.addProduct.and.returnValue(throwError('Error occurred'));

        component.onSubmit();

        expect(supplyLinkService.addProduct).toHaveBeenCalled();

        component.productError.subscribe(errorMessage => {
            expect(errorMessage).toBe('Unable to create product');
        });
    });


    it('should not call addProduct if form is invalid', () => {
        component.productForm.patchValue({
            warehouse: { warehouseId: 1, warehouseName: 'Warehouse 1' } as Warehouse,
            productName: '',
            productDescription: '',
            quantity: '',
            price: ''
        });
        component.onSubmit();

        expect(supplyLinkService.addProduct).not.toHaveBeenCalled();
    });
});
