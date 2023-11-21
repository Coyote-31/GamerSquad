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

describe('Event e2e test', () => {
  const eventPageUrl = '/event';
  const eventPageUrlPattern = new RegExp('/event(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const eventSample = {"title":"Total hacking","meetingDate":"2023-11-21T00:33:28.952Z","isPrivate":true};

  let event;
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
      body: {"title":"c Clothing systemic","description":"d'Orsel Account Specialiste","imgUrl":"virtual"},
    }).then(({ body }) => {
      game = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/events+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/events').as('postEntityRequest');
    cy.intercept('DELETE', '/api/events/*').as('deleteEntityRequest');
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
    if (event) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/events/${event.id}`,
      }).then(() => {
        event = undefined;
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

  it('Events menu should load Events page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('event');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Event').should('exist');
    cy.url().should('match', eventPageUrlPattern);
  });

  describe('Event page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(eventPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Event page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/event/new$'));
        cy.getEntityCreateUpdateHeading('Event');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/events',
          body: {
            ...eventSample,
            owner: appUser,
            game: game,
          },
        }).then(({ body }) => {
          event = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/events+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [event],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(eventPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(eventPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details Event page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('event');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);
      });

      it('edit button click should load edit Event page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Event');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);
      });

      it('edit button click should load edit Event page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Event');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of Event', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('event').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);

        event = undefined;
      });
    });
  });

  describe('new Event page', () => {
    beforeEach(() => {
      cy.visit(`${eventPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Event');
    });

    it.skip('should create an instance of Event', () => {
      cy.get(`[data-cy="title"]`).type('c withdrawal').should('have.value', 'c withdrawal');

      cy.get(`[data-cy="description"]`).type('Checking Rhône-Alpes').should('have.value', 'Checking Rhône-Alpes');

      cy.get(`[data-cy="meetingDate"]`).type('2023-11-21T10:24').blur().should('have.value', '2023-11-21T10:24');

      cy.get(`[data-cy="isPrivate"]`).should('not.be.checked');
      cy.get(`[data-cy="isPrivate"]`).click().should('be.checked');

      cy.get(`[data-cy="owner"]`).select(1);
      cy.get(`[data-cy="game"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        event = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', eventPageUrlPattern);
    });
  });
});
