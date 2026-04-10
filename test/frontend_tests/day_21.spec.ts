import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { WarehouseComponent } from '../app/supplylink/components/warehouse/warehouse.component';
import { SupplierComponent } from '../app/supplylink/components/supplier/supplier.component';

describe('SupplierComponent', () => {
  let fixture: ComponentFixture<SupplierComponent>;
  let component: SupplierComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SupplierComponent],
      imports: [FormsModule, ReactiveFormsModule],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SupplierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the form', () => {
    expect(component.supplierForm).toBeDefined();
  });

  it('should have form controls for name, email, username, and password', () => {
    const formControls = ['supplierName', 'email', 'username', 'password', 'role'];

    formControls.forEach(control => {
      expect(component.supplierForm.get(control)).toBeTruthy();
    });
  });

  it('should call onSubmit method on form submission', () => {
    spyOn(component, 'onSubmit');
    const form = fixture.debugElement.query(By.css('form')).nativeElement;
    form.dispatchEvent(new Event('submit'));

    expect(component.onSubmit).toHaveBeenCalled();
  });
 
});


describe('WarehouseComponent', () => {
  let component: WarehouseComponent;
  let fixture: ComponentFixture<WarehouseComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WarehouseComponent],
      imports: [ReactiveFormsModule]
    });

    fixture = TestBed.createComponent(WarehouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });
  it('should validate supplierId as required', () => {
    const supplierIdControl = component.warehouseForm.get('supplierId');
    supplierIdControl!.setValue(null);

    expect(supplierIdControl!.hasError('required')).toBeTruthy();
  });

  it('should validate capacity as non-negative', () => {
    const capacityControl = component.warehouseForm.get('capacity')!;
    capacityControl.setValue(-1);

    expect(capacityControl.hasError('min')).toBeTruthy();
  });

  it('should pass validation when all fields are valid', () => {
    component.warehouseForm.setValue({
        supplierId: 1,
        warehouseName: 'Flamingo',
        location: 'Texas',
        capacity: 1000, 
      });
    
      expect(component.warehouseForm.valid).toBeTruthy();
  });

});
