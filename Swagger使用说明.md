# Swagger API 文档使用说明

## 概述

本项目已集成 Swagger 3 (OpenAPI 3) 用于生成和展示 API 文档。所有主要的 Controller、DTO、VO 和实体类都已添加了相应的 Swagger 注解。

## 访问方式

启动项目后，可以通过以下地址访问 Swagger UI：

```
http://localhost:8080/swagger-ui/index.html
```

## 已添加 Swagger 注解的模块

### 1. 用户管理模块
- **UserController**: 用户注册、登录、登出、信息管理
- **UserAddressController**: 用户地址的增删改查

### 2. 数据传输对象 (DTO)
- **UserRegisterDTO**: 用户注册请求参数
- **UserLoginDTO**: 用户登录请求参数
- **UserUpdateInfoDTO**: 用户信息更新请求参数
- **UserUpdatePwDTO**: 用户密码修改请求参数
- **AdsRegisterDTO**: 地址注册请求参数

### 3. 视图对象 (VO)
- **UserViewVO**: 用户信息响应数据
- **UserLoginVO**: 用户登录响应数据
- **UserRegisterVO**: 用户注册响应数据
- **UserAddressVO**: 用户地址响应数据

### 4. 实体类
- **User**: 用户领域模型
- **Result**: 统一响应结果封装类

## 主要注解说明

### Controller 层注解
- `@Tag`: 为 Controller 添加标签和描述
- `@Operation`: 为接口方法添加操作描述
- `@ApiResponses`: 定义接口响应状态码和描述
- `@Parameter`: 为参数添加描述

### 数据模型注解
- `@Schema`: 为类或字段添加描述、示例值、是否必填等信息
- `hidden = true`: 隐藏敏感字段（如密码）

### 安全认证
- 已配置 JWT Bearer Token 认证
- 在 Swagger UI 中点击 "Authorize" 按钮输入 Token

## 错误码和响应说明

### 统一响应格式
项目使用统一的 `Result<T>` 响应格式：
- **成功响应**: 状态码 `200`，包含具体的数据和成功消息
- **失败响应**: 状态码 `500`，包含具体的错误消息

### 错误信息类型
根据 `BusinessMessage` 类，系统定义了多种业务错误信息：
- 用户相关：用户不存在、用户名已存在、手机号已存在、用户被锁定等
- 认证相关：账号或密码错误、JWT无效等
- 地址相关：地址数量已达上限、添加/删除/修改地址失败等
- 数据相关：数据异常、数据库操作失败等

### 异常处理机制
- `GlobalExceptionHandler` 统一处理各种异常
- `BusinessException` 可以携带自定义错误码（如 `4001`）
- 参数验证异常会返回具体的字段错误信息

## 配置说明

Swagger 配置类位于：`main/src/main/java/com/zhuxi/main/config/SwaggerConfig.java`

主要配置内容：
- API 基本信息（标题、描述、版本等）
- 联系人信息
- 许可证信息
- JWT 安全认证配置

## 使用建议

1. **开发阶段**: 使用 Swagger UI 进行接口测试
2. **文档生成**: 可以导出 OpenAPI 规范文件用于其他工具
3. **团队协作**: 前端开发人员可以通过 Swagger 文档了解接口规范
4. **接口测试**: 直接在 Swagger UI 中测试接口功能

## 注意事项

1. 所有敏感信息（如密码）已设置为 `hidden = true`
2. 示例数据仅用于文档展示，实际使用时请替换为真实数据
3. 接口描述和参数说明已根据业务需求进行了详细配置
4. 响应状态码和错误信息已按照接口设计文档进行了配置

## 扩展说明

如需为其他模块（如商品管理、订单管理等）添加 Swagger 注解，请参考现有的注解模式：

1. Controller 类添加 `@Tag` 注解
2. 接口方法添加 `@Operation` 和 `@ApiResponses` 注解
3. 参数添加 `@Parameter` 注解
4. DTO/VO 类添加 `@Schema` 注解
5. 字段添加 `@Schema` 注解并配置描述和示例值
