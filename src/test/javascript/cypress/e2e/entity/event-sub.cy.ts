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

describe('EventSub e2e test', () => {
  const eventSubPageUrl = '/event-sub';
  const eventSubPageUrlPattern = new RegExp('/event-sub(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const eventSubSample = {"isAccepted":false};

  let eventSub;
  // let event;
  // let appUser;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/events',
      body: {"title":"Ameliorated","description":"Berkshire revolutionary Specialiste","meetingDate":"2023-11-20T22:54:51.914Z","isPrivate":false},
    }).then(({ body }) => {
      event = body;
    });
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
    cy.intercept('GET', '/api/event-subs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/event-subs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/event-subs/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/events', {
      statusCode: 200,
      body: [event],
    });

    cy.intercept('GET', '/api/app-users', {
      statusCode: 200,
      body: [appUser],
    });

  });
   */

  afterEach(() => {
    if (eventSub) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/event-subs/${eventSub.id}`,
      }).then(() => {
        eventSub = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (event) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/events/${event.id}`,
      }).then(() => {
        event = undefined;
      });
    }
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

  it('EventSubs menu should load EventSubs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('event-sub');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('EventSub').should('exist');
    cy.url().should('match', eventSubPageUrlPattern);
  });

  describe('EventSub page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(eventSubPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create EventSub page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/event-sub/new$'));
        cy.getEntityCreateUpdateHeading('EventSub');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventSubPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/event-subs',
          body: {
            ...eventSubSample,
            event: event,
            appUser: appUser,
          },
        }).then(({ body }) => {
          eventSub = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/event-subs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [eventSub],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(eventSubPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(eventSubPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details EventSub page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('eventSub');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventSubPageUrlPattern);
      });

      it('edit button click should load edit EventSub page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EventSub');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventSubPageUrlPattern);
      });

      it('edit button click should load edit EventSub page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EventSub');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventSubPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of EventSub', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('eventSub').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventSubPageUrlPattern);

        eventSub = undefined;
      });
    });
  });

  describe('new EventSub page', () => {
    beforeEach(() => {
      cy.visit(`${eventSubPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('EventSub');
    });

    it.skip('should create an instance of EventSub', () => {
      cy.get(`[data-cy="isAccepted"]`).should('not.be.checked');
      cy.get(`[data-cy="isAccepted"]`).click().should('be.checked');

      cy.get(`[data-cy="event"]`).select(1);
      cy.get(`[data-cy="appUser"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        eventSub = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', eventSubPageUrlPattern);
    });
  });
});
