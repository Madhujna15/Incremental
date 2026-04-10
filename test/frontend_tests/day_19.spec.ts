import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SupplierArrayComponent } from '../app/supplylink/components/supplierarray/supplierarray.component';
import { Supplier } from '../app/supplylink/types/Supplier';

describe('SupplierArrayComponent', () => {
  let component: SupplierArrayComponent;
  let fixture: ComponentFixture<SupplierArrayComponent>;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SupplierArrayComponent]
    });

    fixture = TestBed.createComponent(SupplierArrayComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });
  

  it('should display supplier information', () => {
    const supplier: Supplier[] = [
      new Supplier(1, "Jessica Alba", "jessica@gmail.com", "7368289682", "california", "jessica", "July@12221", "USER"),
      new Supplier(1, "John Wane", "johnwane@gmail.com", "9876543210", "texas", "johnwane", "July@101", "USER"),
      new Supplier(1, "Kristan", "kristan@gmail.com", "9364812638", "NYC", "kristan", "Julll@101", "USER")
    ];
  
    component.supplierList = supplier;
    fixture.detectChanges();
  
    const compiled = fixture.nativeElement;

   const supplierParagraphs = compiled.querySelectorAll('p');

   expect(supplierParagraphs[0].textContent).toContain('Jessica Alba');
   expect(supplierParagraphs[0].textContent).toContain('jessica@gmail.com');

   expect(supplierParagraphs[1].textContent).toContain('John Wane');
   expect(supplierParagraphs[1].textContent).toContain('johnwane@gmail.com');

   expect(supplierParagraphs[2].textContent).toContain('Kristan');
   expect(supplierParagraphs[2].textContent).toContain('kristan@gmail.com');
  });

  
});

