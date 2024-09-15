# SpringBoot Template

This project contains useful feature flagments like logging, etc.

## Features

### Application Lifecycle Logging

Log when Application is starting up, started up, shutdown, failed to start.

### Transaction Logging

Log around(Before and After) BEGIN, COMMIT, ROLLBACK.

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

## memo
### 追加したいもの
- background periodic job(kick, graceful shutdown)
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