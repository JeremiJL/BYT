# Emptio

Emptio - («to purchase» from latin) - is a 3p e-commerce platform.

### Overview of how platform operates

Emptio offers wide selection of products of almost any kind and operates in whole European Union.

On the platform we can distinguish three main types of users :
- _shopper_
- _merchant_
- _advertiser_

Being 3p platform Emptio doesn't own any products listed on the platform,
rather it's responsibility is limited to creating a space for _merchants_ to put on display
their own products and enable _shoppers_ to buy them. The payment process is outsourced by _Stripe_
- third party vendor with whom Emptio cooperates, while the shipping is considered a responsibility of _merchants_.

Platform's business model relays on the existence of _advertisers_. Emptio gives advertisers
option to purchase more favorable positioning of chosen products on the website.

### How does any user interact with the platform

Any interaction with Emptio starts from having to log in to the platform.
_User_ fills in login and password, if login succeeds, _user_ is redirected to dedicated home page.
If user doesn't have an account yet, he may create one by completing an entry form. First user has to choose what
type of account (_shopper_, _merchant_ or _advertiser_) he would like to create. Then _user_ provides his/her new login 
credentials and all profile details specific to kind of account chosen.

### How does a _shopper_ interact with the platform

After logging to the platform the _shopper_ is faced with a home page.
On home page there are a couple of rows of 'special' offers and a search bar.

Upon querying the search bar _shopper_ is sent to a listing page.
The listing page consists of rows of products that were matched by an internal
algorithm translating user query to products information.

On the listing page products are represented as tiles with a graphic, title and a price.
Upon clicking on a product, _shopper_ is sent to product page with a more detailed 
information of the product :
- graphic
- price
- title
- category
- description

Via product page _shopper_ has the availability to add product to a cart.
At any moment _shopper_ may proceed to cart by clicking it's icon visible in top bar of the platform.

The cart page is a list of products previously added to it. User may change the quantity of any product
in the cart of request the purchase of the whole cart.

Upon clicking the purchase button following actions take place :
- The cost of each product and the bank account number of a _merchant_ that owns this product is sent to Stripe - 3p vendor to whom Emptio outsources payments.
- Shopper is redirected to Stripe webpage in new browser tab to complete the payment. There Shopper pays the total sum
while Stripe splits its and transfer the money to appropriate merchants.
- When Stripe returns to Emptio information that payment has succeeded, then the cart clears empties itself 
and _shopper_ is informed that the purchase was successful
- Then Emptio mails appropriate merchants that their products have been purchased and that they should deliver 
ordered products under shopper's address.

Next to cart icon, there is a profile icon. Upon clicking it, Shopper is redirected to profile page, where
_shopper_ can change his/her information :
- full name
- contact information - email and mobile number
- shipping address

### How does _merchant_ interact with a platform

After logging to the platform the _merchant_ is faced with _merchant_ panel.
Merchant panel is nothing but a welcome screen with a top bar presenting _merchant_ with available options for him/her.
- inventory
- add product
- profile

Every option redirects user to corresponding page, described below.

Inventory page is where all products that _merchant_ put on display on Emptio are listed. Products are shown
in the same manner as in listing page. Upon clicking one of his products, _merchant_ is sent to product edit page,
where he can change any details about his product or remove it from his/her inventory if it's out of his/her stock.

Profile page is where _merchant_ may edit his/her information :
- full name
- contact information - email and mobile number
- address of his warehouse where his products are located
- bank account number

Add product page is where _merchant_ may add new products to his/her inventory. The page is an entry form of all 
product details.

### How does advertiser interact with a platform

After logging to the platform the advertiser is faced with advertiser panel.

The advertiser panel is a simple welcome screen with a top bar presenting advertiser with available options for him/her.
- new campaign
- inventory
- profile

Every option redirects user to corresponding page, described below.

New campaign page plays the role of an entry form for creating a new ad campaign.
Ad campaign is a selected set of products for which favourable display advertiser is priced - such products are
referred to as sponsored ones.
 
In order to create a new campaign advertiser has to provide following information :
- name of the campaign
- choose products available at Emptio that will be the object of campaign - for that advertiser is equipped
with search mechanism similar to that of shopper.
- chose the placement of campaign - shopper's main page xor shopper's listing page.
- chose how money is advertiser willing to pay for each shopper's interaction with advertised product. Interaction with
a product of a _shopper_ is understood as going to the product's page.
- choose the total budget of the campaign

After completing the form advertiser has to pay for the campaign upfront, while the platform promises to
display chosen products in such a way that the set budget will be spent while roas will be maximised.

Next, the payment is outsourced to Stripe and the process resembles that of shopper's.

Inventory page is where advertiser can review and edit his/her active and inactive campaigns.

Every campaigned is described by the parameters with which it was previously configured via a form and the performance
statistics :
- amount of budget spent
- number of interactions generated

Advertiser can take following actions with each of his/her campaign :
- edit any of its parameters (placement, budget, name, price per interaction, offer's list)
- stop xor resume the campaign

Profile page is where advertiser may change information about him/her - functionally it resembles that of shopper.

### How ads work?

On both listing page and main page products are displayed in a grid. One can easily imagine that certain
positioning of a product on such grid could be more attractive from the perspective of the _merchant_ or advertiser,
as certain cells are more prominent than the others.
More specifically - a _shopper_ has a greater chance to notice products displayed in cells in higher rows and in centered
columns. Emptio's ad system utilize this nuance, by offering the display of chosen products in more favourable cells for
a price.

Each cell's on listing page and main page attractiveness is calculated by how high and how close to the center it is.
Then, when the listing page or main page is loaded products and sponsored products (ads) are being displayed in such a
sorted manner that the more _merchant_ is willing to pay for the interaction with his product the better cell from the
perspective of attractiveness will be assigned for his product.

The assumption is that merchants are willing to pay 0 EUR for interactions with regular products - those that aren't
part of any ad campaigns. This assumption allows products to be part of the sorting process with sponsored products.


### Nice to have features :
- XYZ