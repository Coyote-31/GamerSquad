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

describe('FriendshipChat e2e test', () => {
  const friendshipChatPageUrl = '/friendship-chat';
  const friendshipChatPageUrlPattern = new RegExp('/friendship-chat(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const friendshipChatSample = {"message":"integrate Russie Namibie","sendAt":"2023-11-20T23:55:07.846Z"};

  let friendshipChat;
  // let friendship;
  // let appUser;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/friendships',
      body: {"isAccepted":true},
    }).then(({ body }) => {
      friendship = body;
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
    cy.intercept('GET', '/api/friendship-chats+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/friendship-chats').as('postEntityRequest');
    cy.intercept('DELETE', '/api/friendship-chats/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/friendships', {
      statusCode: 200,
      body: [friendship],
    });

    cy.intercept('GET', '/api/app-users', {
      statusCode: 200,
      body: [appUser],
    });

  });
   */

  afterEach(() => {
    if (friendshipChat) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/friendship-chats/${friendshipChat.id}`,
      }).then(() => {
        friendshipChat = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (friendship) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/friendships/${friendship.id}`,
      }).then(() => {
        friendship = undefined;
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

  it('FriendshipChats menu should load FriendshipChats page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('friendship-chat');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FriendshipChat').should('exist');
    cy.url().should('match', friendshipChatPageUrlPattern);
  });

  describe('FriendshipChat page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(friendshipChatPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FriendshipChat page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/friendship-chat/new$'));
        cy.getEntityCreateUpdateHeading('FriendshipChat');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', friendshipChatPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/friendship-chats',
          body: {
            ...friendshipChatSample,
            friendship: friendship,
            sender: appUser,
          },
        }).then(({ body }) => {
          friendshipChat = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/friendship-chats+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [friendshipChat],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(friendshipChatPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(friendshipChatPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details FriendshipChat page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('friendshipChat');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', friendshipChatPageUrlPattern);
      });

      it('edit button click should load edit FriendshipChat page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FriendshipChat');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', friendshipChatPageUrlPattern);
      });

      it('edit button click should load edit FriendshipChat page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FriendshipChat');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', friendshipChatPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of FriendshipChat', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('friendshipChat').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', friendshipChatPageUrlPattern);

        friendshipChat = undefined;
      });
    });
  });

  describe('new FriendshipChat page', () => {
    beforeEach(() => {
      cy.visit(`${friendshipChatPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FriendshipChat');
    });

    it.skip('should create an instance of FriendshipChat', () => {
      cy.get(`[data-cy="message"]`).type('Barbados Saint-Denis invoice').should('have.value', 'Barbados Saint-Denis invoice');

      cy.get(`[data-cy="sendAt"]`).type('2023-11-21T01:35').blur().should('have.value', '2023-11-21T01:35');

      cy.get(`[data-cy="friendship"]`).select(1);
      cy.get(`[data-cy="sender"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        friendshipChat = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', friendshipChatPageUrlPattern);
    });
  });
});
