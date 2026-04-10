import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
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
    const formControls = ['name', 'email', 'username', 'password'];

    formControls.forEach(control => {
      expect(component.supplierForm.get(control)).toBeTruthy();
    });
  });

  it('should mark form controls as invalid if they are touched and empty', () => {
    const formControls = ['name', 'email', 'username', 'password'];

    formControls.forEach(control => {
      const input = fixture.debugElement.query(By.css(`#${control}`)).nativeElement;

      input.dispatchEvent(new Event('focus'));
      input.dispatchEvent(new Event('blur'));

      fixture.detectChanges();

      expect(component.supplierForm.get(control)?.invalid).toBe(true);
    });
  });

 
});
