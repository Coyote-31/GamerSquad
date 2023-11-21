import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGameSub } from '../game-sub.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../game-sub.test-samples';

import { GameSubService } from './game-sub.service';

const requireRestSample: IGameSub = {
  ...sampleWithRequiredData,
};

describe('GameSub Service', () => {
  let service: GameSubService;
  let httpMock: HttpTestingController;
  let expectedResult: IGameSub | IGameSub[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GameSubService);
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

    it('should create a GameSub', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const gameSub = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(gameSub).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GameSub', () => {
      const gameSub = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(gameSub).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GameSub', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GameSub', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GameSub', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGameSubToCollectionIfMissing', () => {
      it('should add a GameSub to an empty array', () => {
        const gameSub: IGameSub = sampleWithRequiredData;
        expectedResult = service.addGameSubToCollectionIfMissing([], gameSub);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(gameSub);
      });

      it('should not add a GameSub to an array that contains it', () => {
        const gameSub: IGameSub = sampleWithRequiredData;
        const gameSubCollection: IGameSub[] = [
          {
            ...gameSub,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGameSubToCollectionIfMissing(gameSubCollection, gameSub);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GameSub to an array that doesn't contain it", () => {
        const gameSub: IGameSub = sampleWithRequiredData;
        const gameSubCollection: IGameSub[] = [sampleWithPartialData];
        expectedResult = service.addGameSubToCollectionIfMissing(gameSubCollection, gameSub);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(gameSub);
      });

      it('should add only unique GameSub to an array', () => {
        const gameSubArray: IGameSub[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const gameSubCollection: IGameSub[] = [sampleWithRequiredData];
        expectedResult = service.addGameSubToCollectionIfMissing(gameSubCollection, ...gameSubArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const gameSub: IGameSub = sampleWithRequiredData;
        const gameSub2: IGameSub = sampleWithPartialData;
        expectedResult = service.addGameSubToCollectionIfMissing([], gameSub, gameSub2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(gameSub);
        expect(expectedResult).toContain(gameSub2);
      });

      it('should accept null and undefined values', () => {
        const gameSub: IGameSub = sampleWithRequiredData;
        expectedResult = service.addGameSubToCollectionIfMissing([], null, gameSub, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(gameSub);
      });

      it('should return initial array if no GameSub is added', () => {
        const gameSubCollection: IGameSub[] = [sampleWithRequiredData];
        expectedResult = service.addGameSubToCollectionIfMissing(gameSubCollection, undefined, null);
        expect(expectedResult).toEqual(gameSubCollection);
      });
    });

    describe('compareGameSub', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGameSub(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGameSub(entity1, entity2);
        const compareResult2 = service.compareGameSub(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGameSub(entity1, entity2);
        const compareResult2 = service.compareGameSub(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGameSub(entity1, entity2);
        const compareResult2 = service.compareGameSub(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
