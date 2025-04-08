# Currency Conversion and Discount Calculation API

This Spring Boot application calculates the final payable amount for a bill after applying applicable discounts and converting it to a specified currency using real-time exchange rates from a third-party API.

## Features

- Real-time currency conversion using [Open Exchange Rates](https://v6.exchangerate-api.com)
- Smart discount system:
  - 30% off for store employees
  - 10% off for store affiliates
  - 5% off for loyal customers (>2 years)
  - $5 off for every $100 spent (all users)
  - Percentage discounts excluded for groceries
- User authentication for secure access
- REST API endpoint to calculate net payable bill

---

## Technologies Used

- Java 17+
- Spring Boot
- Spring Security
- RestTemplate / WebClient for API integration
- JUnit, Mockito for testing
- Lombok
- Maven or Gradle
- Open Exchange Rates API

---

## API Endpoint

### `POST /api/calculatePayable`

**Request Body:**

```json
{
  "user": {
    "type": "EMPLOYEE",
    "tenureInYears": 3
  },
  "items": [
    { "name": "Shampoo", "category": "NON_GROCERY", "price": 150.0 },
    { "name": "Apple", "category": "GROCERY", "price": 50.0 }
  ],
  "originalCurrency": "USD",
  "targetCurrency": "EUR"
}

Response (JSON):

{
  "netPayableAmount": 170.45,
  "currency": "EUR"
}

🔐 Authentication
All endpoints are secured using Basic Authentication:

Username: user

Password: password

🧩 UML Class High-Level Diagram
lua
Copy
Edit
+-------------------+           +-------------------+
|      User         |           |      Item         |
+-------------------+           +-------------------+
| type              |           | name              |
| tenureInYears     |           | category          |
+-------------------+           | price             |
                                +-------------------+

+----------------------------+
|     DiscountService        |
+----------------------------+
| calculateDiscount(...)     |
+----------------------------+

+----------------------------+
|   ExchangeRateService      |
+----------------------------+
| getExchangeRate(...)       |
+----------------------------+

+----------------------------+
|     BillingController      |
+----------------------------+
| calculateNetPayable(...)   |
+----------------------------+

+----------------------------+
|   AuthenticationConfig     |
+----------------------------+
| security configuration     |
+----------------------------+
