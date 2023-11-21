import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEventChat } from '../event-chat.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../event-chat.test-samples';

import { EventChatService, RestEventChat } from './event-chat.service';

const requireRestSample: RestEventChat = {
  ...sampleWithRequiredData,
  sendAt: sampleWithRequiredData.sendAt?.toJSON(),
};

describe('EventChat Service', () => {
  let service: EventChatService;
  let httpMock: HttpTestingController;
  let expectedResult: IEventChat | IEventChat[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EventChatService);
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

    it('should create a EventChat', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const eventChat = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(eventChat).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EventChat', () => {
      const eventChat = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(eventChat).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EventChat', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EventChat', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EventChat', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEventChatToCollectionIfMissing', () => {
      it('should add a EventChat to an empty array', () => {
        const eventChat: IEventChat = sampleWithRequiredData;
        expectedResult = service.addEventChatToCollectionIfMissing([], eventChat);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventChat);
      });

      it('should not add a EventChat to an array that contains it', () => {
        const eventChat: IEventChat = sampleWithRequiredData;
        const eventChatCollection: IEventChat[] = [
          {
            ...eventChat,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEventChatToCollectionIfMissing(eventChatCollection, eventChat);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EventChat to an array that doesn't contain it", () => {
        const eventChat: IEventChat = sampleWithRequiredData;
        const eventChatCollection: IEventChat[] = [sampleWithPartialData];
        expectedResult = service.addEventChatToCollectionIfMissing(eventChatCollection, eventChat);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventChat);
      });

      it('should add only unique EventChat to an array', () => {
        const eventChatArray: IEventChat[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const eventChatCollection: IEventChat[] = [sampleWithRequiredData];
        expectedResult = service.addEventChatToCollectionIfMissing(eventChatCollection, ...eventChatArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const eventChat: IEventChat = sampleWithRequiredData;
        const eventChat2: IEventChat = sampleWithPartialData;
        expectedResult = service.addEventChatToCollectionIfMissing([], eventChat, eventChat2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventChat);
        expect(expectedResult).toContain(eventChat2);
      });

      it('should accept null and undefined values', () => {
        const eventChat: IEventChat = sampleWithRequiredData;
        expectedResult = service.addEventChatToCollectionIfMissing([], null, eventChat, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventChat);
      });

      it('should return initial array if no EventChat is added', () => {
        const eventChatCollection: IEventChat[] = [sampleWithRequiredData];
        expectedResult = service.addEventChatToCollectionIfMissing(eventChatCollection, undefined, null);
        expect(expectedResult).toEqual(eventChatCollection);
      });
    });

    describe('compareEventChat', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEventChat(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEventChat(entity1, entity2);
        const compareResult2 = service.compareEventChat(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEventChat(entity1, entity2);
        const compareResult2 = service.compareEventChat(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEventChat(entity1, entity2);
        const compareResult2 = service.compareEventChat(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
