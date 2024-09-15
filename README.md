# SpringBoot Template

This project contains useful feature flagments like logging, etc.

## Features

### Application Lifecycle Logging

Log when Application is starting up, started up, shutdown, failed to start.

### Transaction Logging

Log around(Before and After) BEGIN, COMMIT, ROLLBACK.

## memo
### 追加したいもの
- background periodic job(kick, graceful shutdown)
- log backtrace info
- entity to record(readonly, free from entity management)
- multi module project, with multi format packaging(jar/war)
- fork async job(with handover some data)
- request context data handling
- monitor performance
- validation
- force insert/update(without select)
- API version
- testing
    - test log content