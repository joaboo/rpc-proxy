# rpc-proxy

近期有老项目要从Spring Boot + RPC迁移到Spring Cloud，即RPC接口调用部分需要改成Feign + RESTful方式，去除RPC框架的使用，为了避免接口修改带来的大量工作，考虑通过实现统一代理的方式来维持原有代码的不变动，即保持RPC调用的假象，但实际已经是通过Feign来实现。
那么此项目就是实现了这么一个代理的功能。
