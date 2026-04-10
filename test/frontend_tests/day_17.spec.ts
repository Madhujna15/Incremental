
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Supplier } from '../app/supplylink/types/Supplier';
import { Warehouse } from '../app/supplylink/types/Warehouse';
import { SupplierSampleComponent } from '../app/supplylink/components/suppliersample/suppliersample.component';
import { WarehouseSampleComponent } from '../app/supplylink/components/warehousesample/warehousesample.component';


describe('WarehouseSampleComponent', () => {
    let componentWarehouse: WarehouseSampleComponent;
    let fixtureWarehouse: ComponentFixture<WarehouseSampleComponent>;
  
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WarehouseSampleComponent]
      });
  
      fixtureWarehouse = TestBed.createComponent(WarehouseSampleComponent);
      componentWarehouse = fixtureWarehouse.componentInstance;
    });

    it('should create the component', () => {
    expect(componentWarehouse).toBeTruthy();
    });
  
    it('should display warehouse information', () => {
      const warehouse: Warehouse = new Warehouse(1, "12", "Flamingo", "Nevada", 1000);
  
      componentWarehouse.warehouse = warehouse;
      fixtureWarehouse.detectChanges();
  
      const compiled = fixtureWarehouse.nativeElement;
  
      expect(compiled.textContent).toContain('Warehouse ID: 1');
      expect(compiled.textContent).toContain('Supplier ID: 12');
      expect(compiled.textContent).toContain('Warehouse Name: $Flamingo');
      expect(compiled.textContent).toContain('Location: $Nevada');
      expect(compiled.textContent).toContain('Capacity: $1000');
    });
  });

describe('SupplierSampleComponent', () => {
    let componentSupplier: SupplierSampleComponent;
    let fixtureSupplier: ComponentFixture<SupplierSampleComponent>;
  
    beforeEach(() => {
      TestBed.configureTestingModule({});
      fixtureSupplier = TestBed.createComponent(SupplierSampleComponent);
      componentSupplier = fixtureSupplier.componentInstance;
    });

    it('should create the component', () => {
        expect(componentSupplier).toBeTruthy();
    });

    it('should display supplier details correctly', () => {
      const supplier:Supplier = new Supplier(1, "John Wane", "johnwane@gmail.com", "9876543210", "texas", "johnwane", "July@101", "USER");

      componentSupplier.supplier = supplier;
      fixtureSupplier.detectChanges();
  
      const compiled = fixtureSupplier.nativeElement;
  
      expect(compiled.textContent).toContain('Supplier Details');
      expect(compiled.textContent).toContain('Supplier ID: 1');
      expect(compiled.textContent).toContain('Name: John Wane');
      expect(compiled.textContent).toContain('Email: johnwane@gmail.com');
      expect(compiled.textContent).toContain('Username: johnwane');
      expect(compiled.textContent).toContain('Password: July@101');
      expect(compiled.textContent).toContain('Role: USER');
    });
  });
  