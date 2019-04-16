问答社区，模拟知乎

技术栈：springboot+redis+mysql

+ 用户 注册、登录功能，在数据库中保存用户状态
+ 用户访问控制、用户登陆后页面跳转，拦截器
+ 添加问题、添加评论功能，使用 TrieTee 过滤敏感词
+ 展示问题、评论，对问题关注、对评论点赞点踩
+ 完善异步处理框架
    + 定义 EventModel，不同的 Event 对应不同的 EventType，实现不同的 EventProcuder 和 EventHandler
    +  EventHandler 实现 EventHandler  接口，在启动时自动注册，EventType-EventHandler  的mapping
    + 编写 EventModel 的生产 和 消费 统一入口，利用 redis 中的 list 作为消息队列
    + 一个线程监听 redis list，得到一个 EventModel后，分发给线程池，调用对应 EventHandler 的事件处理函数
+ 添加 feed流 逻辑，通过注册 EventHandler 来实现
+ 使用spring注解型事务，进行事务管理和回滚
+ 添加 redis 缓存 数据库数据，解决数据库、redis数据一致性问题
    + 用户登录ticket缓存
    + 用户信息user缓存