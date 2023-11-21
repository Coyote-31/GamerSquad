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

describe('EventChat e2e test', () => {
  const eventChatPageUrl = '/event-chat';
  const eventChatPageUrlPattern = new RegExp('/event-chat(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const eventChatSample = {"message":"unleash Garden","sendAt":"2023-11-20T22:33:26.682Z"};

  let eventChat;
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
      body: {"title":"experiences b Adaptive","description":"Movies copying Assistant","meetingDate":"2023-11-21T15:25:23.146Z","isPrivate":true},
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
    cy.intercept('GET', '/api/event-chats+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/event-chats').as('postEntityRequest');
    cy.intercept('DELETE', '/api/event-chats/*').as('deleteEntityRequest');
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
    if (eventChat) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/event-chats/${eventChat.id}`,
      }).then(() => {
        eventChat = undefined;
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

  it('EventChats menu should load EventChats page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('event-chat');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('EventChat').should('exist');
    cy.url().should('match', eventChatPageUrlPattern);
  });

  describe('EventChat page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(eventChatPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create EventChat page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/event-chat/new$'));
        cy.getEntityCreateUpdateHeading('EventChat');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventChatPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/event-chats',
          body: {
            ...eventChatSample,
            event: event,
            appUser: appUser,
          },
        }).then(({ body }) => {
          eventChat = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/event-chats+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [eventChat],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(eventChatPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(eventChatPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details EventChat page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('eventChat');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventChatPageUrlPattern);
      });

      it('edit button click should load edit EventChat page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EventChat');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventChatPageUrlPattern);
      });

      it('edit button click should load edit EventChat page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EventChat');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventChatPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of EventChat', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('eventChat').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', eventChatPageUrlPattern);

        eventChat = undefined;
      });
    });
  });

  describe('new EventChat page', () => {
    beforeEach(() => {
      cy.visit(`${eventChatPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('EventChat');
    });

    it.skip('should create an instance of EventChat', () => {
      cy.get(`[data-cy="message"]`).type('Analyste').should('have.value', 'Analyste');

      cy.get(`[data-cy="sendAt"]`).type('2023-11-21T05:20').blur().should('have.value', '2023-11-21T05:20');

      cy.get(`[data-cy="event"]`).select(1);
      cy.get(`[data-cy="appUser"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        eventChat = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', eventChatPageUrlPattern);
    });
  });
});
