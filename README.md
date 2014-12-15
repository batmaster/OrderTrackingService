OrderTrackingService
======================

Web Service for e-commerce website, used for admin to provide order status like:

* 2014-11-01 08:20 Order received
* 2014-11-01 09:02 Order picked (means they start processing it)
* 2014-11-01 09:18 Ready for shipment
* 2014-11-01 10:02 Scanned at Thai Post Rangsit, tracking 1234556667
* 2014-11-02 12:02 In transit
(example from Aj.Jim)

Client side separate to 2 applications, for admin to add, update and remove status
and for customer to check the status only.


Stakeholder
---
* Customer
* Admin (e-commerce owner or staff)
* Transaction system

Use cases
---
1 Use Case Name : See order status

Actor : Customer , Transaction system

Description : After customer finish payment. Transaction system will return a tracking id. And customer can use the tracking id to check order status in the future.
 

2 Use Case Name : Update Status

Actor : Admin

Description : After admin already authenticated and customer already finished payment. Admin able to show the orders that customer finish payment (made by the transaction system). And then admin can update that orders state to let customers know the status.



API Specification
---
https://docs.google.com/document/d/1iKT14ru_fQzlMcY-z1sd4p9uiCwZ5L1FjiYW55mbXpo/edit

Repository
---
Github: https://github.com/batmaster/OrderTrackingService.git


Developers
---
Poramate Homprakob 5510546077<br>
Rungroj Maipradit 55105466547 <br>
