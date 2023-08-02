import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../plates.test-samples';

import { PlatesFormService } from './plates-form.service';

describe('Plates Form Service', () => {
  let service: PlatesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlatesFormService);
  });

  describe('Service methods', () => {
    describe('createPlatesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlatesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            price: expect.any(Object),
            photo: expect.any(Object),
            origin: expect.any(Object),
            stars: expect.any(Object),
            favorite: expect.any(Object),
            cookTime: expect.any(Object),
            idrestaurant: expect.any(Object),
            photos: expect.any(Object),
            restaurant: expect.any(Object),
          })
        );
      });

      it('passing IPlates should create a new form with FormGroup', () => {
        const formGroup = service.createPlatesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            price: expect.any(Object),
            photo: expect.any(Object),
            origin: expect.any(Object),
            stars: expect.any(Object),
            favorite: expect.any(Object),
            cookTime: expect.any(Object),
            idrestaurant: expect.any(Object),
            photos: expect.any(Object),
            restaurant: expect.any(Object),
          })
        );
      });
    });

    describe('getPlates', () => {
      it('should return NewPlates for default Plates initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPlatesFormGroup(sampleWithNewData);

        const plates = service.getPlates(formGroup) as any;

        expect(plates).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlates for empty Plates initial value', () => {
        const formGroup = service.createPlatesFormGroup();

        const plates = service.getPlates(formGroup) as any;

        expect(plates).toMatchObject({});
      });

      it('should return IPlates', () => {
        const formGroup = service.createPlatesFormGroup(sampleWithRequiredData);

        const plates = service.getPlates(formGroup) as any;

        expect(plates).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlates should not enable id FormControl', () => {
        const formGroup = service.createPlatesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlates should disable id FormControl', () => {
        const formGroup = service.createPlatesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
