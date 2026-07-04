下面是一份符合你们**Spring Boot + Vue3 智慧物业管理系统**的 API 接口文档，可以直接作为 **docs/03_API文档.md** 使用，同时也符合老师要求，后续使用 Knife4j 生成的文档格式也基本一致。

---

# 智慧物业管理系统 API接口文档

**项目名称：** 智慧物业管理系统

**接口版本：** v1.0

**接口前缀：**

```text
http://localhost:8080/api/v1
```

统一请求格式

```
Content-Type: application/json
```

统一返回格式

```json
{
    "code":200,
    "message":"success",
    "data":{}
}
```

分页返回格式

```json
{
    "code":200,
    "message":"success",
    "data":{
        "total":100,
        "pages":10,
        "current":1,
        "size":10,
        "records":[]
    }
}
```

---

# 一、登录认证模块

---

## 1. 用户登录

### 请求地址

```
POST /auth/login
```

### 请求参数

| 参数       | 类型     | 是否必填 | 说明  |
| -------- | ------ | ---- | --- |
| username | String | 是    | 用户名 |
| password | String | 是    | 密码  |

### 请求示例

```json
{
    "username":"admin",
    "password":"123456"
}
```

### 返回示例

```json
{
    "code":200,
    "message":"登录成功",
    "data":{
        "token":"eyJhbGc..."
    }
}
```

---

## 2. 获取当前登录用户

```
GET /auth/info
```

Header

```
Authorization: Bearer Token
```

返回

```json
{
    "code":200,
    "message":"success",
    "data":{
        "id":1,
        "username":"admin",
        "nickname":"管理员",
        "roles":[
            "管理员"
        ]
    }
}
```

---

# 二、用户管理

---

## 1. 查询用户列表

```
GET /users
```

请求参数

| 参数       | 说明   |
| -------- | ---- |
| pageNum  | 当前页  |
| pageSize | 每页数量 |
| userName | 用户名  |

返回

```json
{
  "code":200,
  "message":"success",
  "data":{
      "total":20,
      "records":[
          {
              "id":1,
              "userName":"admin",
              "realName":"系统管理员",
              "phone":"13800000000",
              "status":1
          }
      ]
  }
}
```

---

## 2. 根据ID查询用户

```
GET /users/{id}
```

---

## 3. 新增用户

```
POST /users
```

请求参数

```json
{
    "userName":"zhangsan",
    "password":"123456",
    "realName":"张三",
    "phone":"13888888888",
    "roleId":2
}
```

---

## 4. 修改用户

```
PUT /users/{id}
```

---

## 5. 删除用户

```
DELETE /users/{id}
```

---

# 三、角色管理

---

## 查询角色列表

```
GET /roles
```

## 新增角色

```
POST /roles
```

请求

```json
{
    "roleName":"物业管理员",
    "remark":"负责物业业务"
}
```

---

## 修改角色

```
PUT /roles/{id}
```

---

## 删除角色

```
DELETE /roles/{id}
```

---

## 用户分配角色

```
PUT /users/{id}/role
```

请求

```json
{
    "roleId":2
}
```

---

# 四、小区管理

---

## 查询小区

```
GET /communities
```

查询参数

| 参数            | 说明   |
| ------------- | ---- |
| pageNum       | 页码   |
| pageSize      | 数量   |
| communityName | 小区名称 |

返回

```json
{
    "code":200,
    "data":{
        "records":[
            {
                "id":1,
                "communityName":"阳光花园",
                "address":"北京市昌平区"
            }
        ]
    }
}
```

---

## 新增小区

```
POST /communities
```

请求

```json
{
    "communityName":"阳光花园",
    "address":"北京市昌平区",
    "remark":"一期"
}
```

---

## 修改小区

```
PUT /communities/{id}
```

---

## 删除小区

```
DELETE /communities/{id}
```

---

# 五、楼栋管理

---

## 查询楼栋

```
GET /buildings
```

查询参数

```
communityId
buildingName
pageNum
pageSize
```

---

## 新增楼栋

```
POST /buildings
```

```json
{
    "communityId":1,
    "buildingName":"1号楼",
    "floorCount":18
}
```

---

## 修改楼栋

```
PUT /buildings/{id}
```

---

## 删除楼栋

```
DELETE /buildings/{id}
```

---

# 六、房屋管理

---

## 查询房屋

```
GET /houses
```

查询参数

```
buildingId
roomNo
status
```

返回

```json
{
    "code":200,
    "data":{
        "records":[
            {
                "id":1,
                "roomNo":"101",
                "area":120,
                "status":"已入住"
            }
        ]
    }
}
```

---

## 新增房屋

```
POST /houses
```

```json
{
    "buildingId":1,
    "roomNo":"101",
    "area":120
}
```

---

## 修改房屋

```
PUT /houses/{id}
```

---

## 删除房屋

```
DELETE /houses/{id}
```

---

# 七、业主管理

---

## 查询业主

```
GET /owners
```

查询参数

```
ownerName
phone
pageNum
pageSize
```

---

## 新增业主

```
POST /owners
```

```json
{
    "ownerName":"李四",
    "phone":"13800000000",
    "idCard":"110xxxxxxxx"
}
```

---

## 修改业主

```
PUT /owners/{id}
```

---

## 删除业主

```
DELETE /owners/{id}
```

---

## 房屋绑定业主

```
PUT /houses/{id}/owner
```

请求

```json
{
    "ownerId":3
}
```

---

# 八、报修管理

---

## 提交报修

```
POST /repairs
```

请求

```json
{
    "ownerId":3,
    "houseId":5,
    "title":"卫生间漏水",
    "content":"卫生间水管漏水"
}
```

---

## 查询报修

```
GET /repairs
```

查询参数

```
status
ownerId
pageNum
pageSize
```

---

## 报修派单

```
PUT /repairs/{id}/assign
```

```json
{
    "workerId":2
}
```

---

## 完成报修

```
PUT /repairs/{id}/finish
```

---

## 报修评价

```
POST /repairs/{id}/comment
```

```json
{
    "score":5,
    "content":"维修及时"
}
```

---

# 九、投诉建议

---

## 提交投诉

```
POST /complaints
```

```json
{
    "title":"电梯故障",
    "content":"电梯停运"
}
```

---

## 查询投诉

```
GET /complaints
```

---

## 回复投诉

```
PUT /complaints/{id}/reply
```

```json
{
    "reply":"已经安排维修"
}
```

---

# 十、费用项目管理

---

## 查询费用项目

```
GET /feeItems
```

---

## 新增费用项目

```
POST /feeItems
```

```json
{
    "feeName":"物业费",
    "price":2.5,
    "unit":"元/㎡"
}
```

---

## 修改费用项目

```
PUT /feeItems/{id}
```

---

## 删除费用项目

```
DELETE /feeItems/{id}
```

---

# 十一、账单管理

---

## 生成账单

```
POST /bills
```

请求

```json
{
    "ownerId":1,
    "feeItemId":2,
    "amount":360
}
```

---

## 查询账单

```
GET /bills
```

查询参数

```
ownerId
status
pageNum
pageSize
```

---

## 查询账单详情

```
GET /bills/{id}
```

---

# 十二、缴费管理

---

## 缴费登记

```
POST /payments
```

```json
{
    "billId":1,
    "payType":"微信",
    "amount":360
}
```

---

## 查询缴费记录

```
GET /payments
```

---

# 十三、数据统计

---

## 首页统计

```
GET /statistics/home
```

返回

```json
{
    "code":200,
    "data":{
        "communityCount":3,
        "buildingCount":20,
        "houseCount":800,
        "ownerCount":650,
        "repairCount":25,
        "complaintCount":8
    }
}
```

---

## 报修统计

```
GET /statistics/repair
```

返回

```json
{
    "code":200,
    "data":[
        {
            "month":"1月",
            "count":30
        },
        {
            "month":"2月",
            "count":18
        }
    ]
}
```

---

## 缴费率统计

```
GET /statistics/payment
```

---

# 十四、错误码说明

| 错误码 | 说明      |
| --- | ------- |
| 200 | 请求成功    |
| 400 | 请求参数错误  |
| 401 | 未登录     |
| 403 | 没有权限    |
| 404 | 资源不存在   |
| 409 | 数据冲突    |
| 500 | 服务器内部错误 |

---

# Token 使用说明

所有除登录接口外，均需在请求头中携带 Token。

```text
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## 接口统计

| 模块     | 接口数量       |
| ------ | ---------- |
| 登录认证   | 2          |
| 用户管理   | 5          |
| 角色管理   | 5          |
| 小区管理   | 4          |
| 楼栋管理   | 4          |
| 房屋管理   | 4          |
| 业主管理   | 5          |
| 报修管理   | 5          |
| 投诉建议   | 3          |
| 费用项目   | 4          |
| 账单管理   | 3          |
| 缴费管理   | 2          |
| 数据统计   | 3          |
| **总计** | **49 个接口** |

这份 API 文档覆盖了你们项目需求中的所有必做模块及数据统计模块，可直接保存为 `docs/03_API文档.md`，也便于后续使用 Swagger/Knife4j 自动生成接口文档进行维护。
