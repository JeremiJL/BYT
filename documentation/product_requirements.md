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

### How does any user interact with the platform

Any interaction with Emptio starts from having to log in to the platform.
User fills in login and password, if login succeeds, user is redirected to dedicated home page.
If user doesn't have an account yet, he may create one by completing an entry form. First user has to choose what
type of account (shopper, merchant or advertiser) he would like to create. Then user provides his/her new login credentials
and all profile details specific to kind of account chosen.

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
- The cost of each product and the bank account number of a merchant that owns this product is sent to Stripe - 3p vendor to whom Emptio outsources payments.
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

### How does merchant interact with a platform

After logging to the platform the merchant is faced with merchant panel.
Merchant panel is nothing but a welcome screen with a top bar presenting merchant which available options for him.
- inventory
- profile
- add product

Inventory page is where all products that merchant put on display on Emptio are listed. Products are shown
in the same manner as in listing page. Upon clicking one of his products, merchant is sent to product edit page,
where he can change any details about his product or remove it from his/her inventory if it's out of his/her stock.

Profile page is where merchant may edit his/her information :
- full name
- contact information - email and mobile number
- address of his warehouse where his products are located
- bank account number

Add product page is where merchant may add new products to his/her inventory. The page is an entry form of all 
product details.

### How does advertiser interact with a platform

After logging to the platform the merchant is faced with advertiser panel.

