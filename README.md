Our goal is to build a small retail system that will allow our customer to purchase products via digital channels. We will build it following
microservice architecture. Your assignment will be to build a component called Shopping Cart. This component should be able to:
	Provide current content of a cart for a particular customer	Y
	Add a particular item to a cart					Y
	Remove a particular item from a cart				Y
	Evict a cart							Y
	Provide statistics on how many offers of a particular id and action were sold in a particular period

With an item we should store:
	An identifier of a particular offer representing that item (i.e. if an item represents a Phillips TV, we need to store an identifier
		representing that TV. Metadata about the product is handled by another system called Product Catalog)
	An action that we are doing for this item. If we are purchasing an item, it will be ADD. If we are upgrading an item (i.e. buying a bigger
		subscription for something), it will be MODIFY. If we are removing an item (i.e. cancelling a subscription), it will be DELETE
	Prices which are a complex object

A price can be one of the two types:
	Recurring price a price with which we are storing a number of recurrences (i.e. 12 months, 24 months, 7 days) and a price value
	One time price a one time price with which we are storing only a price value

Rules are the following:
	Each item should have at least one price
	All the attributes of both items and prices are mandatory and should have their respective proper values

Your task will be to:
	Implement a desired application based on the described requirements (backend side only)
	Present a solution and provide us with the code

Our recommended technologies to use are:
	Java/Kotlin with Spring Boot for the application
	MongoDB for the database
But any technology you would like is acceptable.
