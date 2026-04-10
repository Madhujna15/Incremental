import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { ProductComponent } from '../app/supplylink/components/product/product.component';
import { WarehouseComponent } from '../app/supplylink/components/warehouse/warehouse.component';


describe('ProductComponent', () => {
    let componentProduct: ProductComponent;
    let fixtureProduct: ComponentFixture<ProductComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({

            imports: [ReactiveFormsModule],
        }).compileComponents();
    });

    beforeEach(() => {
        fixtureProduct = TestBed.createComponent(ProductComponent);
        componentProduct = fixtureProduct.componentInstance;
        fixtureProduct.detectChanges();
    });

    it('should create the component', () => {
        expect(componentProduct).toBeTruthy();
    });

    it('should initialize the form with controls', () => {
        expect(componentProduct.productForm).toBeDefined();
        expect(componentProduct.productForm.get('warehouseId')).toBeDefined();
        expect(componentProduct.productForm.get('productName')).toBeDefined();
        expect(componentProduct.productForm.get('quantity')).toBeDefined();
        expect(componentProduct.productForm.get('price')).toBeDefined();
    });

    it('should create a product object on form submission', () => {
        componentProduct.productForm.setValue({
            productId: 1,
            warehouseId: "12",
            productName: "table",
            productDescription: "",
            quantity: 100,
            price: 1200.0,
        });

        componentProduct.onSubmit();
    });
});

describe('WarehouseComponent', () => {
    let componentWarehouse: WarehouseComponent;
    let fixtureWarehouse: ComponentFixture<WarehouseComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({

            imports: [ReactiveFormsModule],
        }).compileComponents();
    });

    beforeEach(() => {
        fixtureWarehouse = TestBed.createComponent(WarehouseComponent);
        componentWarehouse = fixtureWarehouse.componentInstance;
        fixtureWarehouse.detectChanges();
    });

    it('should set form controls as required', () => {
        expect(componentWarehouse.warehouseForm).toBeDefined();
        expect(componentWarehouse.warehouseForm.get('supplierId')).toBeDefined();
        expect(componentWarehouse.warehouseForm.get('warehouseName')).toBeDefined();
        expect(componentWarehouse.warehouseForm.get('location')).toBeDefined();
        expect(componentWarehouse.warehouseForm.get('capacity')).toBeDefined();
    });

    it('should pass validation when all fields are valid', () => {
        componentWarehouse.warehouseForm.setValue({
            warehouseId: 1,
            supplierId: 1,
            warehouseName: 'Flaimngo',
            location: 'Texas',
            capacity: 100
        });

        expect(componentWarehouse.warehouseForm.valid).toBeTruthy();
    });

    it('should set capacity control as invalid if value is less than 0', () => {
        const capacityControl = componentWarehouse.warehouseForm.get('capacity');

        capacityControl?.setValue(-1);
        expect(capacityControl?.hasError('min')).toBe(true);

        capacityControl?.setValue(0);
        expect(capacityControl?.hasError('min')).toBe(false);
    });


});
