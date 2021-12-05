<script async src="//busuanzi.ibruce.info/busuanzi/2.3/busuanzi.pure.mini.js"></script>
## RabbitMQ基础概念
> - 作者：ZC
> - 日期：2021/12/5
> - 主页：https://blog.csdn.net/qq_41595149?spm=1010.2135.3001.5421&type=blog

## 官网:https://www.rabbitmq.com/



## RabbitMQ是什么？

  RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件（亦称面向消息的中间件）。RabbitMQ服务器是用Erlang语言编写的，而集群和故障转移是构建在开放电信平台框架上的。所有主要的编程语言均有与代理接口通讯的客户端。(百度百科)

AMQP ：Advanced Message Queue，高级消息队列协议。它是应用层协议的一个开放标准，为面向消息的中间件设计，基于此协议的客户端与消息中间件可传递消息，并不受产品、开发语言等条件的限制。

![](https://gitee.com/zhangchuan11/pic-go/raw/master/img/202112051416779.jpg)



### RabbitMQ的使用场景

1. 接口之间，应用之间，耦合严重（应用解耦）
2. 面对大流量，容易冲垮（流量削峰）
3. 存在性能问题（异步处理）



## RabbitMQ亮点

1. 可靠性：提供持久性机制，投递确认，发布者证实，高可用机制等多种技术来权衡可靠性和性能

2. 灵活的路由：在消息进入路由之前，通过交换器来路由消息,提供内置的交换器实现，针对复杂路由功

   能，可以将多个交换器绑定到一起，或通过插件来实现自己的交换器

3. 扩展性:多个 RabbitMQ 可以组成一个集群，也可以根据实际业务动态的扩展集群中的节点

4. 高可用性：队列可以在集群中的机器上设置镜像，使得部分节点在出现问题的情况下队列依然可用

5. 多种协议：除了支持原生 AMQP 协议，还支持 STOMP,MQTT 等多种消息中间件协议

6. 多语言客户端:几乎支持所有常用语言:JAVA,Python,Ruby,PHP,C#,JavaScript

7. 管理界面：RabbitMQ 提供一个易用的用户界面，使用户可以监控和管理消息，集群中的节点

8. 多种插件机制:RabbitMQ 提供许多插件，从多方面进行扩展，也可以自己编写插件





### RabbitMQ中的组件

- Server(broker):一个 erlang node 节点,接受客户端连接，实现AMQP消息队列和路由功能的进程。
- Virtual Host:其实是一个虚拟概念，类似于权限控制组，一个Virtual Host里面可以有若干个Exchange和Queue，但是权限控制的最小粒度是Virtual Host

- 交换器（Exchange）：接受生产者发送的消息，并根据Binding规则将消息路由给服务器中的队列。ExchangeType决定了Exchange路由消息的行为，在RabbitMQ中，ExchangeType有direct，topic，headers,fanout四种，不同类型的Exchange路由的行为是不一样的。

  

  direct(路由模型)：直接匹配,通过Exchange名称+RountingKey来发送与接收消息. 

  topic(订阅模型): 主题匹配订阅,这里的主题指的是RoutingKey,RoutingKey可以采用通配符,如:*或#，RoutingKey命名采用.来分隔多个词,只有消息这将队列绑定到该路由器且指定RoutingKey符合匹配规则时才能收到消息; 

  headers(头模型）:消息头订阅,消息发布前,为消息定义一个或多个键值对的消息头,然后消费者接收消息同时需要定义类似的键值对请              求头:(如:x-mactch=all或者x_match=any)，只有请求头与消息头匹配,才能接收消息,忽略RoutingKey. 
   默认的exchange:如果用空字符串去声明一个exchange，那么系统就会使用”amq.direct”这个exchange，我们创建一个queue时,默认的都会有一个和新建queue同名的routingKey绑定到这个默认的exchange上去

  fanout(广播模型)：广播订阅,向所有的消费者发布消息,但是只有消费者将队列绑定到该路由器才能收到消息,忽略Routing Key.

  

- 队列(Queue)：一个命名实体，用来保存消息直到发送给消费者，位于磁盘或者内存中
- Message: 由Header和Body组成，Header是由生产者添加的各种属性的集合，包括Message是否被持久化、由哪个Message Queue接受、优先级是多少等。而Body是真正需要传输的数据。

- 绑定（Binding）: 一套规则，告知交换器消息应该将消息投递给那个队列,消息队列和交换器之间的关联,Binding联系了Exchange与Message Queue。Exchange在与多个Message Queue发生Binding后会生成一张路由表，路由表中存储着Message Queue所需消息的限制条件即Binding Key。当Exchange收到Message时会解析其Header得到Routing Key，Exchange根据Routing Key与Exchange Type将Message路由到Message Queue。Binding Key由Consumer在Binding Exchange与Message Queue时指定，而Routing Key由Producer发送Message时指定，两者的匹配方式由Exchange Type决定。
- 绑定器关键字（Binding Key）：绑定的名称。一些交换器类型可能使用这个名称作为定义绑定器路由行为的模式。

- 路由键（RoutingKey）:指定消息的路由规则，需要和交换器类型和Binding联合使用才能生效,交换器可以用RoutingKey决定如何路由某条消息。

- 生产者（Producer）：一个向交换器发布消息的客户端应用程序

- 消费者（Consumer）: 个从消息队列中请求消息的客户端应用程序
- 连接(Connection):一个网络连接，比如TCP/IP套接字连接，在RabbitMQ中就是一个位于客户端和Broker之间的TCP连接

- 信道(channel): 多路复用连接中的一条独立的双向数据流通道。为会话提供物理传输介质,实际进行路由工作的实体，负责按照 routing_key 将 message 投递给 queue

- 死信队列：DLX，死信交换器，当一个消息被拒，或 TTL 过期或者队列满了，无法添加时，他能被重新

  发送到另一个交换器中，这个交换器就是死信交换器，绑定到这个交换器上面的队列叫做死信队列





### 生产者生产消息过程

1. Producer 连接到 Broker,建立 Conection,开启一个信道

2. Producer 声明一个交换器并设置相关属性

3. Producer 声明一个队列并设置相关属性

4. Producer 通过路由键将交换器和队列绑定起来

5. Producer 发送消息到 Broker,其中包含路由键，交换器等信息

6. 当Broker中的交换器收到消息时会解析其Header得到路由键，交换器根据路由键和交换器类型将消息路由到消息队列
7. 如果没有找到消息队列,根据生产者的配置丢弃或者退回给生产者

8. 关闭信道

9. 关闭连接





### 消费者消费消息过程

1. Consumer连接到 Broker,建立 Conection,开启一个信道（channel） 
2. 向 Broker 请求消费响应的队列中消息，可能会设置响应的回调函数

3. 等待 Broker 回应并投递相应相应队列中的消息，接收消息

4. 消费者确认收到消息，ack

5. RabbitMQ 从队列中删除已经确定的消息

6. 关闭信道

7. 关闭连接



<span id="busuanzi_container_page_pv">
本文总阅读量<span id="busuanzi_value_page_pv"></span>次
</span>

