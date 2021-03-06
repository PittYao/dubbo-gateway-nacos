## 基础数据

- dubbo-web-base/resource/sql/*.sql
- 导入mysql 即有基础数据 

## 基本结构

- 网关: Spring-gateway 日志 限流 token基本校验 [9900]
- BFF(backend for frontend): 各个dubbo-web开头项目-->用于数据聚合 
    - dubbo-web-*: 各个dubbo服务，服务间尽量不互相调用，通过BFF调用 [8082]
- dubbo-api-base: api基础接口 
- dubbo-web-file: 文件服务 [8083] 文件服务控制台[8088]
- auth: 认证服务 [8889]
- monitor: 监控服务 [8180]
- nacos: 注册中心[8848]

## 测试接口
- auth/resource/http/auth.http 包含接口测试文件，运行可测试
- gateway/resource/http/api-web-base.http 包含接口测试文件，运行可测试

## token颁发 认证流程

- token颁发
    网关-->转发到认证服务-->(校验用户信息-->颁发token)-->请求头中加入 ket=Authorization;value=token
- token鉴权
    网关-->(基础token校验,token是否有效,token是否已过期)-->转发请求到认证服务-->(校验token是否满足访问权限)-->返回网关,满足则放行,不满足则返回权限不足
    
## 文件服务
- 文件分布式系统采用 [go-fastdfs](https://sjqzhang.github.io/go-fastdfs/#vision)提供服务
- 上传接口 POST /file/upload
- token校验接口 POST /file/token/auth
- 具体的token校验流程和go-fastdfs集群配置参考官方文档
- 文件管理平台 [go-fastdfs-web](https://github.com/perfree/go-fastdfs-web/releases)
- 使用参考官方文档