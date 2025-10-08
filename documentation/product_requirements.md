# Emptio

Emptio - '«to purchase» from latin' - is a 3p e-commerce platform.

### Overview of how platform operates

Emptio offers wide selection of products of any kind and operates in whole European Union.

On the platform we can distinguish three main types of users :
- shopper
- merchant
- advertiser

Being 3p platform Emptio doesn't own any products listed on the platform,
rather it's responsibility is limited to creating a space for _merchants_ to put on display
their own products and enable _shoppers_ to buy them. The payment process is outsourced by Stripe
- third party vendor with whom Emptio cooperates, while the shipping is considered a responsibility of merchants.

Platform's business model relays on the existence of _advertisers_. Emptio gives advertisers
option to purchase more favorable positioning of chosen products on the website.

### How does a shopper interact with the platform

After logging to the platform the shopper is faced with a home page.
On home page there are a couple of rows of 'special' offers and a search bar.

Upon querying the search bar shopper is sent to a listing page.
The listing page consists of rows of products that were matched by an internal
algorithm translating user query to products information.

On the listing page products are represented as tiles with a graphic, title and a price.
Upon clicking on a product, shopper is sent to product page with a more detailed 
information of the product :
- graphic
- price
- title
- category
- description

Via product page shopper has the availability to add product to a cart.
At any moment shopper may proceed to cart by clicking it's icon visible in top bar of the platform.

The cart page is a list of products previously added to it. User may change the quantity of any product
in the cart of request the purchase of the whole cart.

Upon clicking the purchase button following actions take place :
- The cost of each product and the bank account of a merchant that owns this product is sent to Stripe - 3p vendor to whom Emptio outsources payments.
- Shopper is redirected to Stripe webpage in new browser tab to complete the payment. There Shopper pays the total sum
while Stripe splits its and transfer the money to appropriate merchants.
- When Stripe returns to Emptio information that payment has succeeded, then the cart clears empties itself 
and shopper is informed that the purchase was successful
- Then Emptio mails appropriate merchants that their products have been purchased and that they should deliver 
ordered products under shopper's address.

Next to cart icon, there is a profile icon. Upon clicking it, Shopper is redirected to profile page, where
shopper can change his/her information :
- full name
- contact information - email and mobile number
- shipping address


