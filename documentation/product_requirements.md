# Emptio

Emptio - («to purchase» from latin) - is a 3p e-commerce platform.

### Overview of how the platform operates

Emptio offers a wide selection of products of almost any kind and operates across the entire European Union.

On the platform, we can distinguish three main types of users:
- _shopper_
- _merchant_
- _advertiser_
- _moderator_

Being a 3P platform, Emptio doesn’t own any of the products listed on the site.  
Its responsibility is limited to creating a space for _merchants_ to display their products and enabling _shoppers_ to buy them.  
The payment process is outsourced to _Stripe_ — a third-party vendor cooperating with Emptio — while shipping is the responsibility of _merchants_.

The platform’s business model relies on the presence of _advertisers_.  
Emptio allows advertisers to purchase more favorable positioning for selected products on the website.

### How any user interacts with the platform

Any interaction with Emptio begins with logging in to the platform.  
The _user_ enters a login and password. If authentication succeeds, the _user_ is redirected to a dedicated home page.  
If the _user_ doesn’t have an account yet, they can create one by completing a registration form.  
First, the _user_ chooses the type of account they would like to create (_shopper_, _merchant_, or _advertiser_), then provides login credentials and all profile details specific to the chosen account type.

User archival policy :
For economical motivations, all users who have not logged in within the last two years are placed in an archived state.
This state indicates that the user's data may be subject to deletion.

### How a _shopper_ interacts with the platform

After logging in, the _shopper_ is greeted with a home page.  
The home page contains several rows of “special” offers and a search bar.

When using the search bar, the _shopper_ is taken to a listing page.  
This page consists of rows of products matched by an internal algorithm that translates the user’s query into product information .

On the listing page, products are represented as tiles with an image, title, and price.  
Upon clicking a product, the _shopper_ is sent to a product page with more detailed information, including:
- image
- price
- title
- category
- description
- list of links to subproducts

From the product page, the _shopper_ can add a product to their cart.  
At any moment, the _shopper_ may proceed to the cart by clicking its icon visible in the top bar of the platform.

The cart page displays a list of products previously added to it.  
The user may change the quantity of any product in the cart or request the purchase of the entire cart.

Upon clicking the purchase button, the following actions take place:
- The cost of each product and the bank account number of the _merchant_ who owns it are sent to Stripe — the third-party vendor handling payments.
- The _shopper_ is redirected to the Stripe webpage in a new browser tab to complete the payment. Stripe collects the total sum, splits it accordingly, and transfers the money to the respective merchants.
- When Stripe notifies Emptio that the payment succeeded, the cart automatically clears, and the _shopper_ is informed that the purchase was successful.
- Emptio then emails the relevant _merchants_ to inform them that their products have been purchased and should be delivered to the _shopper’s_ address.

Next to the cart icon, there is a profile icon.  
Upon clicking it, the _shopper_ is redirected to their profile page, where they can change the following information:
- full name
- contact information — email and mobile number
- shipping address

### How a _merchant_ interacts with the platform

After logging in, the _merchant_ is greeted with a _merchant panel_.  
The merchant panel is a welcome screen with a top bar presenting the following options:
- inventory
- add product
- profile

Each option redirects the user to its corresponding page, described below.

The inventory page lists all products that the _merchant_ has displayed on Emptio.  
Products are shown in the same way as on the listing page.  
Upon clicking one of their products, the _merchant_ is sent to the product edit page, where they can change any product details or remove it from their inventory if it’s out of stock.

The profile page allows the _merchant_ to edit the following information:
- full name
- contact information — email and mobile number
- address of the warehouse where their products are located
- bank account number

The add product page allows the _merchant_ to add new products to their inventory via an entry form containing all product details.

### How an _advertiser_ interacts with the platform

After logging in, the _advertiser_ is greeted with an _advertiser panel_.  
The advertiser panel is a welcome screen with a top bar showing the following options:
- new campaign
- inventory
- profile

Each option redirects the user to its corresponding page, described below.

The new campaign page serves as a form for creating a new ad campaign.  
An ad campaign is a selected set of products for which the advertiser pays to receive more favorable placement — such products are referred to as sponsored.

To create a new campaign, the advertiser must provide the following information:
- name of the campaign
- products available on Emptio to be included in the campaign (selected via a search mechanism similar to that used by _shoppers_)
- placement of the campaign — shopper’s main page or shopper’s listing page
- amount the advertiser is willing to pay for each shopper’s interaction with an advertised product (interaction means visiting the product page)
- total campaign budget

After completing the form, the advertiser pays for the campaign upfront, while the platform promises to display the chosen products in a way that ensures the budget is spent and ROAS is maximized.

Next, the payment is outsourced to Stripe, and the process resembles that of the _shopper_.

The inventory page allows the _advertiser_ to review and edit their active and inactive campaigns.

Each campaign is described by the parameters configured in the form and performance statistics, including:
- amount of budget spent
- number of interactions generated

The _advertiser_ can perform the following actions for each campaign:
- edit any parameter (placement, budget, name, price per interaction, product list)
- stop or resume the campaign

The profile page allows the _advertiser_ to update their information — functionally, it resembles that of the _shopper_.

### How ads work

On both the listing and main pages, products are displayed in a grid.  
Certain positions on this grid are more attractive from the perspective of _merchants_ or _advertisers_, as some cells are more prominent than others.  
More specifically, _shoppers_ are more likely to notice products displayed in higher rows and centered columns.  
Emptio’s ad system leverages this by offering more favorable display positions for selected products — for a price.

Each cell’s attractiveness on the listing and main pages is determined by how high and how close to the center it is.  
When a page is loaded, products and sponsored products (ads) are sorted such that the more a _merchant_ or _advertiser_ is willing to pay for interactions with their product, the better (more attractive) cell is assigned to it.

It is assumed that _merchants_ pay **0 EUR** for interactions with regular products — those not part of any ad campaign.  
This assumption allows all products to participate in the sorting process alongside sponsored ones.


### How does a _moderator_ interact with the platform

On Emptio we can distinguish two types of moderators:
- _regular moderator_
- _advertising support_

Moderators fulfill a single, purpose on the platform — to block any content or entities that violate the platform's guidelines.

The subject of a block can be any of the following:
- user
- product
- campaign

Additionally, _advertising support_ moderator has rights to edit campaign parameters of any
advertiser on the platform. This gives them the possibility to act as intermediate between
big brands and platform technicalities.

### To do:
- TTL of Shopper's Cart.
- Explicit quantity of products on the platform.
- Moderator description and Class Diagram has to be updated to match Use Case logic of loging in
- Paying for campaigns has to be reflected on use case.