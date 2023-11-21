import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFriendshipChat } from '../friendship-chat.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../friendship-chat.test-samples';

import { FriendshipChatService, RestFriendshipChat } from './friendship-chat.service';

const requireRestSample: RestFriendshipChat = {
  ...sampleWithRequiredData,
  sendAt: sampleWithRequiredData.sendAt?.toJSON(),
};

describe('FriendshipChat Service', () => {
  let service: FriendshipChatService;
  let httpMock: HttpTestingController;
  let expectedResult: IFriendshipChat | IFriendshipChat[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FriendshipChatService);
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

    it('should create a FriendshipChat', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const friendshipChat = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(friendshipChat).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FriendshipChat', () => {
      const friendshipChat = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(friendshipChat).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FriendshipChat', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FriendshipChat', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FriendshipChat', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFriendshipChatToCollectionIfMissing', () => {
      it('should add a FriendshipChat to an empty array', () => {
        const friendshipChat: IFriendshipChat = sampleWithRequiredData;
        expectedResult = service.addFriendshipChatToCollectionIfMissing([], friendshipChat);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(friendshipChat);
      });

      it('should not add a FriendshipChat to an array that contains it', () => {
        const friendshipChat: IFriendshipChat = sampleWithRequiredData;
        const friendshipChatCollection: IFriendshipChat[] = [
          {
            ...friendshipChat,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFriendshipChatToCollectionIfMissing(friendshipChatCollection, friendshipChat);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FriendshipChat to an array that doesn't contain it", () => {
        const friendshipChat: IFriendshipChat = sampleWithRequiredData;
        const friendshipChatCollection: IFriendshipChat[] = [sampleWithPartialData];
        expectedResult = service.addFriendshipChatToCollectionIfMissing(friendshipChatCollection, friendshipChat);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(friendshipChat);
      });

      it('should add only unique FriendshipChat to an array', () => {
        const friendshipChatArray: IFriendshipChat[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const friendshipChatCollection: IFriendshipChat[] = [sampleWithRequiredData];
        expectedResult = service.addFriendshipChatToCollectionIfMissing(friendshipChatCollection, ...friendshipChatArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const friendshipChat: IFriendshipChat = sampleWithRequiredData;
        const friendshipChat2: IFriendshipChat = sampleWithPartialData;
        expectedResult = service.addFriendshipChatToCollectionIfMissing([], friendshipChat, friendshipChat2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(friendshipChat);
        expect(expectedResult).toContain(friendshipChat2);
      });

      it('should accept null and undefined values', () => {
        const friendshipChat: IFriendshipChat = sampleWithRequiredData;
        expectedResult = service.addFriendshipChatToCollectionIfMissing([], null, friendshipChat, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(friendshipChat);
      });

      it('should return initial array if no FriendshipChat is added', () => {
        const friendshipChatCollection: IFriendshipChat[] = [sampleWithRequiredData];
        expectedResult = service.addFriendshipChatToCollectionIfMissing(friendshipChatCollection, undefined, null);
        expect(expectedResult).toEqual(friendshipChatCollection);
      });
    });

    describe('compareFriendshipChat', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFriendshipChat(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFriendshipChat(entity1, entity2);
        const compareResult2 = service.compareFriendshipChat(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFriendshipChat(entity1, entity2);
        const compareResult2 = service.compareFriendshipChat(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFriendshipChat(entity1, entity2);
        const compareResult2 = service.compareFriendshipChat(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
