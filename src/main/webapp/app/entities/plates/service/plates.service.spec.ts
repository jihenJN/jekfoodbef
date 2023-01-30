import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPlates } from '../plates.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../plates.test-samples';

import { PlatesService } from './plates.service';

const requireRestSample: IPlates = {
  ...sampleWithRequiredData,
};

describe('Plates Service', () => {
  let service: PlatesService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlates | IPlates[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlatesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Plates', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const plates = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(plates).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Plates', () => {
      const plates = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(plates).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Plates', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Plates', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Plates', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlatesToCollectionIfMissing', () => {
      it('should add a Plates to an empty array', () => {
        const plates: IPlates = sampleWithRequiredData;
        expectedResult = service.addPlatesToCollectionIfMissing([], plates);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plates);
      });

      it('should not add a Plates to an array that contains it', () => {
        const plates: IPlates = sampleWithRequiredData;
        const platesCollection: IPlates[] = [
          {
            ...plates,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlatesToCollectionIfMissing(platesCollection, plates);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Plates to an array that doesn't contain it", () => {
        const plates: IPlates = sampleWithRequiredData;
        const platesCollection: IPlates[] = [sampleWithPartialData];
        expectedResult = service.addPlatesToCollectionIfMissing(platesCollection, plates);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plates);
      });

      it('should add only unique Plates to an array', () => {
        const platesArray: IPlates[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const platesCollection: IPlates[] = [sampleWithRequiredData];
        expectedResult = service.addPlatesToCollectionIfMissing(platesCollection, ...platesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const plates: IPlates = sampleWithRequiredData;
        const plates2: IPlates = sampleWithPartialData;
        expectedResult = service.addPlatesToCollectionIfMissing([], plates, plates2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plates);
        expect(expectedResult).toContain(plates2);
      });

      it('should accept null and undefined values', () => {
        const plates: IPlates = sampleWithRequiredData;
        expectedResult = service.addPlatesToCollectionIfMissing([], null, plates, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plates);
      });

      it('should return initial array if no Plates is added', () => {
        const platesCollection: IPlates[] = [sampleWithRequiredData];
        expectedResult = service.addPlatesToCollectionIfMissing(platesCollection, undefined, null);
        expect(expectedResult).toEqual(platesCollection);
      });
    });

    describe('comparePlates', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlates(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.comparePlates(entity1, entity2);
        const compareResult2 = service.comparePlates(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.comparePlates(entity1, entity2);
        const compareResult2 = service.comparePlates(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.comparePlates(entity1, entity2);
        const compareResult2 = service.comparePlates(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
