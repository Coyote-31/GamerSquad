import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('GameSub e2e test', () => {
  const gameSubPageUrl = '/game-sub';
  const gameSubPageUrlPattern = new RegExp('/game-sub(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const gameSubSample = {};

  let gameSub;
  // let appUser;
  // let game;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/app-users',
      body: {},
    }).then(({ body }) => {
      appUser = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/games',
      body: {"title":"Clothing","description":"Rupiah","imgUrl":"Unbranded Avon seize"},
    }).then(({ body }) => {
      game = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/game-subs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/game-subs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/game-subs/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/app-users', {
      statusCode: 200,
      body: [appUser],
    });

    cy.intercept('GET', '/api/games', {
      statusCode: 200,
      body: [game],
    });

  });
   */

  afterEach(() => {
    if (gameSub) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/game-subs/${gameSub.id}`,
      }).then(() => {
        gameSub = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (appUser) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/app-users/${appUser.id}`,
      }).then(() => {
        appUser = undefined;
      });
    }
    if (game) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/games/${game.id}`,
      }).then(() => {
        game = undefined;
      });
    }
  });
   */

  it('GameSubs menu should load GameSubs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('game-sub');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('GameSub').should('exist');
    cy.url().should('match', gameSubPageUrlPattern);
  });

  describe('GameSub page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(gameSubPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create GameSub page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/game-sub/new$'));
        cy.getEntityCreateUpdateHeading('GameSub');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', gameSubPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/game-subs',
          body: {
            ...gameSubSample,
            appUser: appUser,
            game: game,
          },
        }).then(({ body }) => {
          gameSub = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/game-subs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [gameSub],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(gameSubPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(gameSubPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details GameSub page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('gameSub');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', gameSubPageUrlPattern);
      });

      it('edit button click should load edit GameSub page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('GameSub');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', gameSubPageUrlPattern);
      });

      it('edit button click should load edit GameSub page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('GameSub');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', gameSubPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of GameSub', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('gameSub').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', gameSubPageUrlPattern);

        gameSub = undefined;
      });
    });
  });

  describe('new GameSub page', () => {
    beforeEach(() => {
      cy.visit(`${gameSubPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('GameSub');
    });

    it.skip('should create an instance of GameSub', () => {
      cy.get(`[data-cy="appUser"]`).select(1);
      cy.get(`[data-cy="game"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        gameSub = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', gameSubPageUrlPattern);
    });
  });
});
