# Use case specification scenarios

## Add to a cart - use case scenario

### Comments
There is no point in analyzing this use case in isolation; therefore, our focus begins from the moment
when Shopper is presented with the _product page_.
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
