# README (Ekaitz)

## Some decissions made

- To mock the http requests to payments sandbox in `StripeServiceTest` I used **mockserver**
- I added the verification of the minimum amount of money that can be charged using the payments platform because I
  think it should be a domain validation, not an infrastructure one. I assumed that it is 5 specified in the
  `Wallet.MINIMUM_AMOUNT_TO_TOP_UP` so it is easily changeable. If the user introduces less than 5 the response will be
  a `400 BAD REQUEST`.

## Tests

I included in my opinion 2 kind of tests:

- **Unit tests** to test at low level some domain concepts like `TopUpWalletServiceTest` or `WalletTest`. There would be
  much more (for the value objects, domain classes...) but for simplicity for this exercise I decided to code only a few
- **Acceptance test**, only `WalletIntegrationTest`. The boundaries for this case are the HTTP layer and the payments
  platform. If we'd like to test a bit more, it would be interesting to instead of calling directly to the controller
  method, call it directly through HTTP using RestAssured or similar tool. Injected classes are used to prepare or
  assert the test cases.
- **Integration test**, only `StripeServiceTest`. This tests the integration with the payments platform mocking the HTTP
  responses from it.

In my opinion, the naming of the tests is debatable 😛

## Architecture

The used architecture is the classic DDD/ports & adapters architecture following the 3 usual layers:

- **Application**: Use cases like `TopUpWallet` and `ObtainWallet`
- **Domain**: Domain/core classes (`Wallet`), value objects (`Balance`, `Card`...) and domain services (
  `TopUpWalletService`). I decided also to code two
  interfaces, one for the repository (`WalletRepository`) and the other one for the payment platform (
  `PaymentPlatform`). This allows using the Dependency Inversion principle to decouple from the infra details (which
  repository or db to use, which 3rd party payment platform to use, etc).
- **Infrastructure**: Implementation of DB layer (like JPA), implementation of the payments 3rd party (like Stripe) and
  the controllers of the application