# money-transfering
Simple web application based on Javalin framework to transfer money between accounts.

You can set application port in Starter class (`7000` by default).
API contract description provided by Swagger will be available at `http://localhost:7000/swagger-ui`

The datastore was implemented in-memory as required. 

ConcurrentHashMap helps to avoid map destruction while multithread working. AtomicLong is used for id generation.

To avoid race conditions while money transfering I used `ConcurrentHashMap#compute` method which blocks entries.
It's tested in `perform_two_valid_transaction_returns200_onlyForOne` (`Thread.sleep()` was added to the transaction logic to simulate duration)

The code is divided into layers (controllers, services, validators, repositories) which are carefully covered by unit tests.

Also you can find positive and negative integration (functional) tests.
