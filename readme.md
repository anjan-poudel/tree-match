# Getting Started
### Running app
The command line script doesn't work, so please run 
the `main` function in class ` com.hm.treematch.TreeMatchApplication

`sorry, had issue with spring boot loader and decided not to dig in further`

# TODO for production readiness:
  -  Use proper database instead of json file
  - add more logging for tracing 
  - add unique-id per request in logs for observability
  - test/validate step data for circularity
  - use objects instead of Ids in the data models ( like customerId, stepId etc )
  - add controller tests for failure conditions