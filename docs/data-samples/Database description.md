# Database description

## Admin

This table is called "admin", it  stores all the users with administrator access.

| Parameter | Type         | Description                             |
| --------- | ------------ | --------------------------------------- |
| id        | serial       | A serial id which is a primary key      |
| username  | Varchar(255) | The username used for login             |
| password  | Varchar(255) | The password used for login             |
| lastname  | Varchar(255) | Records the last name of administrator  |
| firstname | Varchar(255) | Records the first name of administrator |

## Company

This table is called "company", it stores all company selling shares in our system.

| Parameter   | Type          | Description                                                  |
| ----------- | ------------- | ------------------------------------------------------------ |
| id          | serial        | A serial id which is a primary key                           |
| companyname | Varchar(255)  | The name of the company                                      |
| category    | Varchar(255)  | The main areas in which this company operates                |
| balance     | numeric(20,2) | The balance that this company earned by sells stock in the system |

## Company Listing

This table is called "companylisting", it stores all listings created by companies.

| Parameter | Type    | Description                                      |
| --------- | ------- | ------------------------------------------------ |
| id        | serial  | A serial id which is a primary key               |
| shareid   | integer | The id of share this listing included            |
| numshare  | integer | The amount of the share remained in this account |

## CompanyUser

The users that belongs to companies, they can not buy or sell any shares, but they can edit the prices or amount of share their companies want to sell.

| Parameter | Type          | Description                                  |
| --------- | ------------- | -------------------------------------------- |
| id        | serial        | A serial id which is a primary key           |
| username  | Varchar(255)  | The username used for login                  |
| password  | Varchar(255)  | The password used for login                  |
| lastname  | Varchar(255)  | Records the last name of company user        |
| firstname | Varchar(255)  | Records the first name of company user       |
| balance   | numeric(20,2) | The amount of money left in the account      |
| companyid | integer       | The company that this company user works for |

## Customer

This table is called "customer". The users that belongs to a customer, they are able to purchase shares from company and sell them back.

| Parameter | Type          | Description                             |
| --------- | ------------- | --------------------------------------- |
| id        | serial        | A serial id which is a primary key      |
| username  | Varchar(255)  | The username used for login             |
| password  | Varchar(255)  | The password used for login             |
| lastname  | Varchar(255)  | Records the last name of customer       |
| firstname | Varchar(255)  | Records the first name of customer      |
| balance   | numeric(20,2) | The amount of money left in the account |

## InvestListing

This table is called "investlisting", it is the shares and their amount a customer purchased from a company.

| Parameter | Type    | Description                                      |
| --------- | ------- | ------------------------------------------------ |
| id        | serial  | A serial id which is a primary key               |
| shareid   | integer | The id of share this listing included            |
| numshare  | integer | The amount of the share remained in this account |

## Portfolio

This table is called "portfolio", it has all ids related to a invest listing.

| Parameter | Type    | Description                        |
| --------- | ------- | ---------------------------------- |
| id        | serial  | A serial id which is a primary key |
| userid    | integer | The id of the customer             |
| listingid | integer | The id of invest listing           |
| shareid   | integer | The id of share                    |
| companyid | integer | The id of company                  |

## Share

This table is called "share", it has all shares weather belongs to company or customer.

| Parameter | Type          | Description                        |
| --------- | ------------- | ---------------------------------- |
| id        | serial        | A serial id which is a primary key |
| companyid | integer       | The company which sells this share |
| sharetype | character     | The type of share                  |
| price     | numeric(20,2) | The price of each share.           |