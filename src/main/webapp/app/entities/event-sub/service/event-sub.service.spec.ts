import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEventSub } from '../event-sub.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../event-sub.test-samples';

import { EventSubService } from './event-sub.service';

const requireRestSample: IEventSub = {
  ...sampleWithRequiredData,
};

describe('EventSub Service', () => {
  let service: EventSubService;
  let httpMock: HttpTestingController;
  let expectedResult: IEventSub | IEventSub[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EventSubService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a EventSub', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const eventSub = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(eventSub).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EventSub', () => {
      const eventSub = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(eventSub).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EventSub', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EventSub', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EventSub', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEventSubToCollectionIfMissing', () => {
      it('should add a EventSub to an empty array', () => {
        const eventSub: IEventSub = sampleWithRequiredData;
        expectedResult = service.addEventSubToCollectionIfMissing([], eventSub);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventSub);
      });

      it('should not add a EventSub to an array that contains it', () => {
        const eventSub: IEventSub = sampleWithRequiredData;
        const eventSubCollection: IEventSub[] = [
          {
            ...eventSub,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEventSubToCollectionIfMissing(eventSubCollection, eventSub);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EventSub to an array that doesn't contain it", () => {
        const eventSub: IEventSub = sampleWithRequiredData;
        const eventSubCollection: IEventSub[] = [sampleWithPartialData];
        expectedResult = service.addEventSubToCollectionIfMissing(eventSubCollection, eventSub);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventSub);
      });

      it('should add only unique EventSub to an array', () => {
        const eventSubArray: IEventSub[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const eventSubCollection: IEventSub[] = [sampleWithRequiredData];
        expectedResult = service.addEventSubToCollectionIfMissing(eventSubCollection, ...eventSubArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const eventSub: IEventSub = sampleWithRequiredData;
        const eventSub2: IEventSub = sampleWithPartialData;
        expectedResult = service.addEventSubToCollectionIfMissing([], eventSub, eventSub2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventSub);
        expect(expectedResult).toContain(eventSub2);
      });

      it('should accept null and undefined values', () => {
        const eventSub: IEventSub = sampleWithRequiredData;
        expectedResult = service.addEventSubToCollectionIfMissing([], null, eventSub, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventSub);
      });

      it('should return initial array if no EventSub is added', () => {
        const eventSubCollection: IEventSub[] = [sampleWithRequiredData];
        expectedResult = service.addEventSubToCollectionIfMissing(eventSubCollection, undefined, null);
        expect(expectedResult).toEqual(eventSubCollection);
      });
    });

    describe('compareEventSub', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEventSub(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEventSub(entity1, entity2);
        const compareResult2 = service.compareEventSub(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEventSub(entity1, entity2);
        const compareResult2 = service.compareEventSub(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEventSub(entity1, entity2);
        const compareResult2 = service.compareEventSub(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
