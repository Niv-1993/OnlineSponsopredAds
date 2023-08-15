# Online Sponsored Ads Module
An ad tech solution developed using Spring Boot for online sellers to efficiently promote their products through targeted campaigns. The application runs on the default port: 8080.

## Overview
This module facilitates the promotion of products through campaigns. Sellers can initiate a campaign specifying attributes such as a start-date, bid amount, and the choice of products for promotion. Upon querying for promoted products, the module fetches the relevant product with the highest bid from active campaigns based on the specified category. If a category-specific product is unavailable, it returns a product with the highest bid irrespective of the category.

## Configuration
The module utilizes JPA (Java Persistence API) and Hibernate for ORM (Object-Relational Mapping) capabilities. It's currently configured for PostgreSQL, and configurations can be adjusted in the application.properties file.

## Endpoints
The base URL for the API is /api/v1/campaign.

### Create Campaign:

- Endpoint: /create
- HTTP Method: POST
- Description: Creates a new campaign based on the provided parameters.
- Request Body:
  - name: Name of the campaign.
  - startDate: Start date for the campaign.
  - productIds: List of product identifiers intended for promotion.
  - bid: The bid amount for the campaign.
### Serve Ad:

- Endpoint: /serveAd
- HTTP Method: GET
- Description: Retrieves the ad based on the highest bid for a given product category.
- Parameters:
  - category: Represents the category of the products.
