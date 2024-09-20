# SpringBoot Template

This project contains useful feature flagments like logging, etc.

## Features

### Application Lifecycle Logging

Log when Application is starting up, started up, shutdown, failed to start.

### Transaction Logging

Log around(Before and After) BEGIN, COMMIT, ROLLBACK.

### Bean IN/OUT Logging

Log bean IN/OUT.

### Storied Debug Log

Like following.

```
2024-09-16 07:56:20.558 [http-nio-8080-exec-1] DEBUG ┐ IN OshiruController.test() oshiru.springboot_template.BeanAspect
2024-09-16 07:56:20.559 [http-nio-8080-exec-1] DEBUG │ at Controller oshiru.springboot_template.OshiruController
2024-09-16 07:56:20.560 [http-nio-8080-exec-1] DEBUG │ [Aspect] Before BEGIN TRANSACTION oshiru.springboot_template.logging.TransactionLogging
2024-09-16 07:56:20.569 [http-nio-8080-exec-1] DEBUG │ [Aspect] After BEGIN TRANSACTION oshiru.springboot_template.logging.TransactionLogging
2024-09-16 07:56:20.570 [http-nio-8080-exec-1] DEBUG │┐ IN OshiruService.test() oshiru.springboot_template.BeanAspect
2024-09-16 07:56:20.570 [http-nio-8080-exec-1] DEBUG ││ at Service oshiru.springboot_template.OshiruService
2024-09-16 07:56:20.571 [http-nio-8080-exec-1] DEBUG │┘ OUT OshiruService.test() oshiru.springboot_template.BeanAspect
2024-09-16 07:56:20.571 [http-nio-8080-exec-1] DEBUG │ [Aspect] Before COMMIT oshiru.springboot_template.logging.TransactionLogging
2024-09-16 07:56:20.572 [http-nio-8080-exec-1] DEBUG │ [Aspect] After COMMIT oshiru.springboot_template.logging.TransactionLogging
2024-09-16 07:56:20.572 [http-nio-8080-exec-1] DEBUG ┘ OUT OshiruController.test() oshiru.springboot_template.BeanAspect
```

### Bean Name Stacktrace

Bean name stacktrace.

### Request Context

Request context data(e.g. Request received date) handling using Filter.

#### Discussion: ThreadLocal vs RequestScope bean?

基本的にはThreadLocalがおすすめ。アプリケーションの規模が大きくなってくると、非Beanにしておきたいレイヤーが出てくるため（経験則）。

ThreadLocal
- 利点
  - 非Bean含めアプリケーション全域からアクセス可能
- 欠点
  - リークのないよう注意が必要(ただしFilterやHandlerInterceptorを使えば難しくはない)
RequestScope bean
- 利点
  - リークの心配なく気軽に使える
- 欠点
  - 非Beanでは参照できない
  - コンストラクタインジェクションを採用している場合、コンストラクタの引数が増えて若干煩雑になる

### Background Job

Background job with dynamic delay decision and graceful shutdown.

## memo
### 追加したいもの
- log backtrace info
- entity to record(readonly, free from entity management)
- multi module project, with multi format packaging(jar/war)
- fork async job(with handover some data)
- monitor performance
- validation
- force insert/update(without select)
- API version
- testing
    - test log content