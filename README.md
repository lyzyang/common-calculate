# Common Calculate Library

## 简介
基于Aviator表达式的通用计算库。

## 功能特性
- 支持Aviator表达式计算
- 提供常用数学计算工具
- 线程安全

## 快速开始

### Maven依赖
```xml
<dependency>
    <groupId>com.example.common</groupId>
    <artifactId>common-calculate</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 使用说明

#### 发送链接卡片-XH助手
- 方法：XHSendMsgUtil.sendHelpLinkCard
- 参数 xhDomain：chatserver服务地址
- 参数 im_user_names：接收人，多个时用逗号隔开
- 参数 short_content：提醒标题
- 参数 url：访问链接

```bash
使用范例：
CalculateService calculateService = new CalculateService();

```
