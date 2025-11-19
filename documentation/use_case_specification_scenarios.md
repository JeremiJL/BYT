# Use case specification scenarios

## Add to a cart - use case scenario

### Comments
There is no point in analyzing this use case in isolation; therefore, our focus begins from the moment
when shopper is presented with the _product page_.
### Actors
Shopper.
### Purpose and context
The use case allows shoppers to add products found on a platform to their cart. To fully understand all points of the
flow of events, it's worth noting that every cart on the platform has TTL of 4 hours starting from the first product 
being added to it. When this time elapses, all products from the cart will be cleared out and put on the platform once more.
### Assumptions
Shopper is not blocked.
### Pre-conditions
Shopper is on a _main page_ or _product's listing_.
### Basic flow of events
1. Shopper clicks on product tile.
2. The system presents the _product page_ with information about the product and an option of adding it to cart.
3. Shopper clicks "add to cart" button.
4. The system asserts the availability of chosen product.
5. Transactionally, the system adds the product to the cart and updates the availability of a product on the market.
6. The system presents the updated _cart page_ including the chosen product.
### Alternative flows of events
_**The product is no longer available on the marketplace**_ <br>
5A1. The system displays a pop-up informing that the product is out of stock. <br>
5A2. Shopper clicks "ok" button on a pop-up. <br>
5A3. Return to point 2. <br>
_**The product is the first item put in the cart**_ <br>
6A1. The system starts the timer counting to 4 hours. <br>
6A2. The system displays a pop-up informing that there are 4 hours left to complete the order. <br>
6A3. Shopper clicks "ok" button on a pop-up. <br>
6A4. Return to point 6.
### Post-condition
**Basic flow of events**: product in a quantity of one is added to Shopper's cart. <br>
**The product is no longer available**: the state of Shopper's cart does not change. <br>
**The product is the first item put in the cart**: product in a quantity of one is added to Shopper's cart. Additionally 
cart's timer is started.

## Make an order - use case scenario

### Actors
Shopper.
### Purpose and context
The use case allows shoppers to finalize their order formed from products previously
put to the cart.
### Assumptions
There is a minimum one product in a cart.
### Pre-conditions
Shopper is on a _cart page_.
### Basic flow of events
1. Shopper clicks on "make an order" button.
2. The system displays a pop-up informing that Shopper will be redirected to third-party
payment service.
3. Shopper clicks "ok".
4. The system forwards to Stripe (third-party payment service) information, including : 
- Bank account number of every merchant whose product is included in the order.
- Payment value each merchant should receive.
- Shopper's identification.
5. The system displays a pop-up informing that it's awaiting payment confirmation from Stripe. System starts
timer counting to 30 minutes.
6. The system updates pop-up with a message saying that payment has been confirmed.
7. The system forwards to every merchant involved in the order information, including :
- Shopper's address - necessary for shipment of ordered product.
- Product and quantity ordered by Shopper.
7. Shopper clicks "ok".
8. The system presents an updated—emptied _cart page_.
### Alternative flow of events
_**Time for finalizing the payment elapsed before confirmation from Stripe**_ <br>
6A1. The system updates pop-up with a message saying that payment failed - has not been made in a given time frame. <br>
6A2. Transactionally, the system adds the products back to the market from the cart and updates the availability of a product on the platform, which results
in cart getting emptied. <br>
6A3. Shopper clicks "ok". <br>
6A4. Return to point 8. <br>
### Post-condition
**Basic flow of events**: Order is completed - every merchant whose product was included in the cart
receives appropriate payment and information which product should be shipped and to what address. Shopper's cart is 
cleared out. <br>
**Time for finalizing the payment elapsed**: All products from the Shopper's cart are put back on the market, 
no order is made.

## Login to a platform as chosen user - use case scenario

### Comments
There is no point in analyzing this use case in isolation; therefore, our focus begins from the moment
when the moderator is presented with the _browse entities page_.

### Actors
Moderator.
### Purpose and context
The use case allows moderators to take over control of user to act on their behalf.
### Assumptions
There are at least two users on the platform.
### Pre-conditions
Moderators in on _browse entities page_.
### Basic flow of events
1. Moderator queries for an entity in a search bar.
2. The system lists the entities matching search results.
3. Moderator clicks on a chosen search result.
4. The system presents entities description including a "log in as this user" button.
5. The Moderator clicks on "log in as this user" button.
6. The system presents a _welcoming page_ of chosen user.
### Alternative flow of events
_**The query has not matched any entity**_ <br>
2A1. Instead of lists of matched entities, the system notifies user that no entity matched the query. <br>
2A2. Moderator clicks "ok". <br>
2A3. The system reloads the _browse entities page_.

_**The query has not matched any entities of 'user' type**_ <br>
3A1. Return to point 1.
### Post-condition
**Basic flow of events**: Moderator logs in to the platform a chosen user.<br>
**The query has not matched any entity**: No changes occur.<br>
**The query has not matched any entities of 'user' type**: No changes occur.

## Stop a campaign - use case scenario

### Comments
There is no point in analyzing this use case in isolation; therefore, our focus begins from the moment
when the advertiser is presented with the _inventory page_.

### Actors
Advertiser.
### Purpose and context
The use case allows advertisers to stop their campaigns. Which allows them not to waste their budget
at times when advertisement for products of their interest is unfavourable.
### Assumptions
Advertiser has at least one active campaign.
### Pre-conditions
Advertiser in on _inventory page_.
### Basic flow of events
1. Advertiser chooses one of the campaigns listed in his/her inventory.
2. The system presents the _campaign page_ where among campaign details (like 'state'), parameters and performance summary, a button
"stop campaign" button is included at the bottom of the page.
3. Advertiser clicks "stop campaign" button.
4. The system stops the campaign.
5. The system refreshes the _campaign page_ updating the 'state' information to "stopped".
### Alternative flow of events
_**The campaign is blocked**_ <br>
4A1. The system displays a pop-up saying that moderation currently blocks the campaign and no changes can be made to it. <br>
4A2. The Advertiser clicks "ok". <br>
4A3. The system returns to presenting the _campaign page_

_**The campaign is already stopped**_ <br>
4A1. The system displays a pop-up saying that the campaign is already stopped. <br>
4A2. The Advertiser clicks "ok". <br>
4A3. The system returns to presenting the _campaign page_.

### Post-condition
**Basic flow of events**: The system stops the campaign setting 'orderingCost' of all 'sponsored offers' which it concerns back to the neutral,
default value - 0. <br>
**The campaign is blocked**: No changes occur.<br>
**The campaign is already stopped**: No changes occur.