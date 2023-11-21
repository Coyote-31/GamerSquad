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

describe('Friendship e2e test', () => {
  const friendshipPageUrl = '/friendship';
  const friendshipPageUrlPattern = new RegExp('/friendship(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const friendshipSample = {"isAccepted":false};

  let friendship;
  // let appUser;

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
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/friendships+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/friendships').as('postEntityRequest');
    cy.intercept('DELETE', '/api/friendships/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/app-users', {
      statusCode: 200,
      body: [appUser],
    });

  });
   */

  afterEach(() => {
    if (friendship) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/friendships/${friendship.id}`,
      }).then(() => {
        friendship = undefined;
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
  });
   */

  it('Friendships menu should load Friendships page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('friendship');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Friendship').should('exist');
    cy.url().should('match', friendshipPageUrlPattern);
  });

  describe('Friendship page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(friendshipPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Friendship page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/friendship/new$'));
        cy.getEntityCreateUpdateHeading('Friendship');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', friendshipPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/friendships',
          body: {
            ...friendshipSample,
            appUserOwner: appUser,
            appUserReceiver: appUser,
          },
        }).then(({ body }) => {
          friendship = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/friendships+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [friendship],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(friendshipPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(friendshipPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details Friendship page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('friendship');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', friendshipPageUrlPattern);
      });

      it('edit button click should load edit Friendship page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Friendship');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', friendshipPageUrlPattern);
      });

      it('edit button click should load edit Friendship page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Friendship');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', friendshipPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of Friendship', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('friendship').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', friendshipPageUrlPattern);

        friendship = undefined;
      });
    });
  });

  describe('new Friendship page', () => {
    beforeEach(() => {
      cy.visit(`${friendshipPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Friendship');
    });

    it.skip('should create an instance of Friendship', () => {
      cy.get(`[data-cy="isAccepted"]`).should('not.be.checked');
      cy.get(`[data-cy="isAccepted"]`).click().should('be.checked');

      cy.get(`[data-cy="appUserOwner"]`).select(1);
      cy.get(`[data-cy="appUserReceiver"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        friendship = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', friendshipPageUrlPattern);
    });
  });
});
