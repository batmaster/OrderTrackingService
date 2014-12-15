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
Use Cases

1. Get current status

After customer finish payment, the service will return the tracking id. Customer can use the id to check current status of the order.

Primary Actor: Customer


Scope: Transaction system

Level: Very High

Story: Customer use the id to check current order status.

Main Success Scenario and steps

1 Customer choose the order id in e-commerce page

2 E-commerce site send request to OrderTracking Service.

3 E-commerce show the detail.



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
