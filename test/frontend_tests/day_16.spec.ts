import { Product } from '../app/supplylink/types/Product';
import { Supplier } from '../app/supplylink/types/Supplier';
import { Warehouse } from '../app/supplylink/types/Warehouse';


// Using Jasmine for testing
describe('Classes', () => {
  
  describe('Supplier Class', () => {
    let supplier: Supplier;
    let spy: jasmine.Spy;

    beforeEach(() => {
      supplier = new Supplier(1, "John Wane", "johnwane@gmail.com", "9876543210", "texas", "johnwane", "July@101", "USER");
      spy = spyOn(console, 'log');
    });

    it('displayInfo method should log correct information', () => {
      supplier.displayInfo();
      expect(spy).toHaveBeenCalledWith(jasmine.stringMatching(/Supplier\s*ID\s*:\s*1/));
      expect(spy).toHaveBeenCalledWith(jasmine.stringMatching(/Supplier name\s*:\s*John Wane/));
      expect(spy).toHaveBeenCalledWith(jasmine.stringMatching(/email\s*:\s*johnwane@gmail.com/));
    });
  });

  describe('Warehouse Class', () => {
    let warehouse: Warehouse;
    let spy: jasmine.Spy;

    beforeEach(() => {
      warehouse = new Warehouse(1, "12", "Flamingo", "Nevada", 1000);
      spy = spyOn(console, 'log');
    });

    it('displayInfo method should log correct information', () => {
      warehouse.displayInfo();
      expect(spy).toHaveBeenCalledWith(jasmine.stringMatching(/Warehouse\s*ID\s*:\s*1/));
      expect(spy).toHaveBeenCalledWith(jasmine.stringMatching(/Supplier\s*ID\s*:\s*12/));
      expect(spy).toHaveBeenCalledWith(jasmine.stringMatching(/Capacity\s*:\s*1000/));
    });
  });

  describe('Product Class', () => {
    let product: Product;
    let spy: jasmine.Spy;

    beforeEach(() => {
      product = new Product(1, "12", "table", "table desc", 100, 1500.0);
      spy = spyOn(console, 'log');
    });

    it('displayInfo method should log correct information', () => {
      product.displayInfo();
      expect(spy).toHaveBeenCalledWith(jasmine.stringMatching(/Product\s*ID\s*:\s*1/));
      expect(spy).toHaveBeenCalledWith(jasmine.stringMatching(/Warehouse\s*ID\s*:\s*12/));
      expect(spy).toHaveBeenCalledWith(jasmine.stringMatching(/quantity\s*:\s*100/));
    });
  });

});
