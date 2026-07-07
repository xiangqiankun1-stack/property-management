---
title: 默认模块
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.30"

---

# 默认模块

Base URLs:

# Authentication

# RepairController

## GET 获取报修列表

GET /api/v1/repairs

GET /api/v1/repairs

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "repairNo": "",
      "ownerId": 0,
      "houseId": 0,
      "repairType": "",
      "repairContent": "",
      "repairImages": "",
      "contactPhone": "",
      "expectedTime": "",
      "status": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListRepair](#schemarlistrepair)|

## POST 新增报修记录

POST /api/v1/repairs

POST /api/v1/repairs

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "repairNo": "string",
  "ownerId": 0,
  "houseId": 0,
  "repairType": "string",
  "repairContent": "string",
  "repairImages": "string",
  "contactPhone": "string",
  "expectedTime": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[Repair](#schemarepair)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单条报修记录

GET /api/v1/repairs/{id}

GET /api/v1/repairs/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "repairNo": "",
    "ownerId": 0,
    "houseId": 0,
    "repairType": "",
    "repairContent": "",
    "repairImages": "",
    "contactPhone": "",
    "expectedTime": "",
    "status": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RRepair](#schemarrepair)|

## PUT 更新报修记录

PUT /api/v1/repairs/{id}

PUT /api/v1/repairs/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "repairNo": "string",
  "ownerId": 0,
  "houseId": 0,
  "repairType": "string",
  "repairContent": "string",
  "repairImages": "string",
  "contactPhone": "string",
  "expectedTime": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[Repair](#schemarepair)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除报修记录

DELETE /api/v1/repairs/{id}

DELETE /api/v1/repairs/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# ComplaintController

## GET 获取投诉列表

GET /api/v1/complaint

GET /api/v1/complaint

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "complaintNo": "",
      "ownerId": 0,
      "complaintType": 0,
      "complaintCategory": "",
      "complaintTitle": "",
      "complaintContent": "",
      "complaintImages": "",
      "contactPhone": "",
      "handleUserId": 0,
      "handleTime": "",
      "handleResult": "",
      "status": "0",
      "satisfaction": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListComplaint](#schemarlistcomplaint)|

## POST 新增投诉

POST /api/v1/complaint

POST /api/v1/complaint

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "complaintNo": "string",
  "ownerId": 0,
  "complaintType": 0,
  "complaintCategory": "string",
  "complaintTitle": "string",
  "complaintContent": "string",
  "complaintImages": "string",
  "contactPhone": "string",
  "handleUserId": 0,
  "handleTime": "string",
  "handleResult": "string",
  "status": 0,
  "satisfaction": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[Complaint](#schemacomplaint)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单个投诉

GET /api/v1/complaint/{id}

GET /api/v1/complaint/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "complaintNo": "",
    "ownerId": 0,
    "complaintType": 0,
    "complaintCategory": "",
    "complaintTitle": "",
    "complaintContent": "",
    "complaintImages": "",
    "contactPhone": "",
    "handleUserId": 0,
    "handleTime": "",
    "handleResult": "",
    "status": "0",
    "satisfaction": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RComplaint](#schemarcomplaint)|

## PUT 更新投诉

PUT /api/v1/complaint/{id}

PUT /api/v1/complaint/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "complaintNo": "string",
  "ownerId": 0,
  "complaintType": 0,
  "complaintCategory": "string",
  "complaintTitle": "string",
  "complaintContent": "string",
  "complaintImages": "string",
  "contactPhone": "string",
  "handleUserId": 0,
  "handleTime": "string",
  "handleResult": "string",
  "status": 0,
  "satisfaction": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[Complaint](#schemacomplaint)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除投诉

DELETE /api/v1/complaint/{id}

DELETE /api/v1/complaint/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## PUT 处理投诉

PUT /api/v1/complaint/{id}/handle

PUT /api/v1/complaint/{id}/handle

> Body 请求参数

```json
{
  "handleUserId": 0,
  "handleTime": "string",
  "handleResult": "string",
  "status": 0,
  "satisfaction": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[ComplaintHandleDTO](#schemacomplainthandledto)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# PermissionController

## GET 获取权限列表

GET /api/v1/permissions

GET /api/v1/permissions

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "permissionCode": "",
      "permissionName": "",
      "permissionType": 0,
      "parentId": 0,
      "permissionIcon": "",
      "permissionStr": "",
      "permissionComponent": "",
      "sort": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListSysPermissionInfo](#schemarlistsyspermissioninfo)|

## POST 新增权限

POST /api/v1/permissions

POST /api/v1/permissions

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "permissionCode": "string",
  "permissionName": "string",
  "permissionType": 0,
  "parentId": 0,
  "permissionIcon": "string",
  "permissionStr": "string",
  "permissionComponent": "string",
  "sort": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[SysPermissionInfo](#schemasyspermissioninfo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单个权限

GET /api/v1/permissions/{id}

GET /api/v1/permissions/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "permissionCode": "",
    "permissionName": "",
    "permissionType": 0,
    "parentId": 0,
    "permissionIcon": "",
    "permissionStr": "",
    "permissionComponent": "",
    "sort": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RSysPermissionInfo](#schemarsyspermissioninfo)|

## PUT 更新权限

PUT /api/v1/permissions/{id}

PUT /api/v1/permissions/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "permissionCode": "string",
  "permissionName": "string",
  "permissionType": 0,
  "parentId": 0,
  "permissionIcon": "string",
  "permissionStr": "string",
  "permissionComponent": "string",
  "sort": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[SysPermissionInfo](#schemasyspermissioninfo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除权限

DELETE /api/v1/permissions/{id}

DELETE /api/v1/permissions/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# RepairEvaluateController

## GET 获取维修评价列表

GET /api/v1/repair-evaluate

GET /api/v1/repair-evaluate

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "repairId": 0,
      "ownerId": 0,
      "score": 0,
      "evaluateContent": "",
      "evaluateImages": "",
      "serviceAttitude": 0,
      "repairQuality": 0,
      "responseSpeed": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListRepairEvaluate](#schemarlistrepairevaluate)|

## POST 新增维修评价

POST /api/v1/repair-evaluate

POST /api/v1/repair-evaluate

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "repairId": 0,
  "ownerId": 0,
  "score": 0,
  "evaluateContent": "string",
  "evaluateImages": "string",
  "serviceAttitude": 0,
  "repairQuality": 0,
  "responseSpeed": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[RepairEvaluate](#schemarepairevaluate)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单个维修评价

GET /api/v1/repair-evaluate/{id}

GET /api/v1/repair-evaluate/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "repairId": 0,
    "ownerId": 0,
    "score": 0,
    "evaluateContent": "",
    "evaluateImages": "",
    "serviceAttitude": 0,
    "repairQuality": 0,
    "responseSpeed": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RRepairEvaluate](#schemarrepairevaluate)|

## PUT 更新维修评价

PUT /api/v1/repair-evaluate/{id}

PUT /api/v1/repair-evaluate/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "repairId": 0,
  "ownerId": 0,
  "score": 0,
  "evaluateContent": "string",
  "evaluateImages": "string",
  "serviceAttitude": 0,
  "repairQuality": 0,
  "responseSpeed": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[RepairEvaluate](#schemarepairevaluate)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除维修评价

DELETE /api/v1/repair-evaluate/{id}

DELETE /api/v1/repair-evaluate/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# BuildingController

## GET 获取楼栋列表

GET /api/v1/buildings

GET /api/v1/buildings

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "communityId": 0,
      "buildingName": "",
      "totalFloors": 0,
      "unitsPerFloor": 0,
      "status": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListBuilding](#schemarlistbuilding)|

## POST 新增楼栋

POST /api/v1/buildings

POST /api/v1/buildings

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "communityId": 0,
  "buildingName": "string",
  "totalFloors": 0,
  "unitsPerFloor": 0,
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[Building](#schemabuilding)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单个楼栋

GET /api/v1/buildings/{id}

GET /api/v1/buildings/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "communityId": 0,
    "buildingName": "",
    "totalFloors": 0,
    "unitsPerFloor": 0,
    "status": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RBuilding](#schemarbuilding)|

## PUT 更新楼栋

PUT /api/v1/buildings/{id}

PUT /api/v1/buildings/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "communityId": 0,
  "buildingName": "string",
  "totalFloors": 0,
  "unitsPerFloor": 0,
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[Building](#schemabuilding)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除楼栋

DELETE /api/v1/buildings/{id}

DELETE /api/v1/buildings/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# RoleController

## GET 获取角色列表

GET /api/v1/roles

GET /api/v1/roles

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "roleCode": "",
      "roleName": ""
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListSysRoleInfo](#schemarlistsysroleinfo)|

## POST 新增角色

POST /api/v1/roles

POST /api/v1/roles

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "roleCode": "string",
  "roleName": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[SysRoleInfo](#schemasysroleinfo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单个角色

GET /api/v1/roles/{id}

GET /api/v1/roles/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "roleCode": "",
    "roleName": ""
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RSysRoleInfo](#schemarsysroleinfo)|

## PUT 更新角色

PUT /api/v1/roles/{id}

PUT /api/v1/roles/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "roleCode": "string",
  "roleName": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[SysRoleInfo](#schemasysroleinfo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除角色

DELETE /api/v1/roles/{id}

DELETE /api/v1/roles/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# RepairOrderController

## GET 获取派单列表

GET /api/v1/repair-orders

GET /api/v1/repair-orders

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "repairId": 0,
      "workerId": 0,
      "assignTime": "",
      "acceptTime": "",
      "completeTime": "",
      "resultDesc": "",
      "status": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListRepairOrder](#schemarlistrepairorder)|

## POST 新增派单记录

POST /api/v1/repair-orders

POST /api/v1/repair-orders

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "repairId": 0,
  "workerId": 0,
  "assignTime": "string",
  "acceptTime": "string",
  "completeTime": "string",
  "resultDesc": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[RepairOrder](#schemarepairorder)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单条派单记录

GET /api/v1/repair-orders/{id}

GET /api/v1/repair-orders/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "repairId": 0,
    "workerId": 0,
    "assignTime": "",
    "acceptTime": "",
    "completeTime": "",
    "resultDesc": "",
    "status": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RRepairOrder](#schemarrepairorder)|

## PUT 更新派单记录

PUT /api/v1/repair-orders/{id}

PUT /api/v1/repair-orders/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "repairId": 0,
  "workerId": 0,
  "assignTime": "string",
  "acceptTime": "string",
  "completeTime": "string",
  "resultDesc": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[RepairOrder](#schemarepairorder)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除派单记录

DELETE /api/v1/repair-orders/{id}

DELETE /api/v1/repair-orders/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# InspectionController

## GET 获取巡检任务列表

GET /api/v1/inspections

GET /api/v1/inspections

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "inspectionNo": "",
      "inspectionName": "",
      "communityId": 0,
      "inspectionArea": "",
      "inspectorId": 0,
      "planStartTime": "",
      "planEndTime": "",
      "actualStartTime": "",
      "actualEndTime": "",
      "inspectionItems": "",
      "status": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListInspection](#schemarlistinspection)|

## POST 新增巡检任务

POST /api/v1/inspections

POST /api/v1/inspections

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "inspectionNo": "string",
  "inspectionName": "string",
  "communityId": 0,
  "inspectionArea": "string",
  "inspectorId": 0,
  "planStartTime": "string",
  "planEndTime": "string",
  "actualStartTime": "string",
  "actualEndTime": "string",
  "inspectionItems": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[Inspection](#schemainspection)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单个巡检任务

GET /api/v1/inspections/{id}

GET /api/v1/inspections/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "inspectionNo": "",
    "inspectionName": "",
    "communityId": 0,
    "inspectionArea": "",
    "inspectorId": 0,
    "planStartTime": "",
    "planEndTime": "",
    "actualStartTime": "",
    "actualEndTime": "",
    "inspectionItems": "",
    "status": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RInspection](#schemarinspection)|

## PUT 更新巡检任务

PUT /api/v1/inspections/{id}

PUT /api/v1/inspections/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "inspectionNo": "string",
  "inspectionName": "string",
  "communityId": 0,
  "inspectionArea": "string",
  "inspectorId": 0,
  "planStartTime": "string",
  "planEndTime": "string",
  "actualStartTime": "string",
  "actualEndTime": "string",
  "inspectionItems": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[Inspection](#schemainspection)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除巡检任务

DELETE /api/v1/inspections/{id}

DELETE /api/v1/inspections/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# UserRoleController

## POST 新增用户角色

POST /api/v1/user-roles

POST /api/v1/user-roles

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "userInfoId": 0,
  "roleInfoId": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[SysUserRole](#schemasysuserrole)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## PUT 更新用户角色

PUT /api/v1/user-roles/{id}

PUT /api/v1/user-roles/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "userInfoId": 0,
  "roleInfoId": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[SysUserRole](#schemasysuserrole)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除用户角色

DELETE /api/v1/user-roles/{id}

DELETE /api/v1/user-roles/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取用户角色列表

GET /api/v1/user-roles/{userId}/roles

GET /api/v1/user-roles/{userId}/roles

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    ""
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListString](#schemarliststring)|

# HouseController

## GET 获取房屋列表

GET /api/v1/houses

GET /api/v1/houses

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "buildingId": 0,
      "houseNumber": "",
      "floor": 0,
      "unitNumber": 0,
      "area": 0.0,
      "houseType": "",
      "status": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListHouse](#schemarlisthouse)|

## POST 新增房屋

POST /api/v1/houses

POST /api/v1/houses

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "buildingId": 0,
  "houseNumber": "string",
  "floor": 0,
  "unitNumber": 0,
  "area": 0,
  "houseType": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[House](#schemahouse)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单个房屋

GET /api/v1/houses/{id}

GET /api/v1/houses/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "buildingId": 0,
    "houseNumber": "",
    "floor": 0,
    "unitNumber": 0,
    "area": 0.0,
    "houseType": "",
    "status": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RHouse](#schemarhouse)|

## PUT 更新房屋

PUT /api/v1/houses/{id}

PUT /api/v1/houses/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "buildingId": 0,
  "houseNumber": "string",
  "floor": 0,
  "unitNumber": 0,
  "area": 0,
  "houseType": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[House](#schemahouse)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除房屋

DELETE /api/v1/houses/{id}

DELETE /api/v1/houses/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取房屋详细信息，包括小区和楼栋等

GET /api/v1/houses/{id}/detail

GET /api/v1/houses/{id}/detail

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "buildingId": 0,
    "houseNumber": "",
    "floor": 0,
    "unitNumber": 0,
    "area": 0.0,
    "houseType": "",
    "status": 0,
    "buildingName": "",
    "communityName": "",
    "communityAddress": ""
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RHouseDTO](#schemarhousedto)|

## GET 获取所有房屋详细信息，包括小区和楼栋等

GET /api/v1/houses/detail

GET /api/v1/houses/detail

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "buildingId": 0,
      "houseNumber": "",
      "floor": 0,
      "unitNumber": 0,
      "area": 0.0,
      "houseType": "",
      "status": 0,
      "buildingName": "",
      "communityName": "",
      "communityAddress": ""
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListHouseDTO](#schemarlisthousedto)|

# PaymentRecordController

## GET 获取缴费记录列表

GET /api/v1/payment-records

GET /api/v1/payment-records

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "paymentNo": "",
      "billId": 0,
      "ownerId": 0,
      "payAmount": 0.0,
      "payMethod": 0,
      "payTime": "",
      "operatorId": 0,
      "voucherNo": "",
      "status": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListPaymentRecord](#schemarlistpaymentrecord)|

## POST 新增缴费记录

POST /api/v1/payment-records

POST /api/v1/payment-records

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "paymentNo": "string",
  "billId": 0,
  "ownerId": 0,
  "payAmount": 0,
  "payMethod": 0,
  "payTime": "string",
  "operatorId": 0,
  "voucherNo": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[PaymentRecord](#schemapaymentrecord)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单条缴费记录

GET /api/v1/payment-records/{id}

GET /api/v1/payment-records/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "paymentNo": "",
    "billId": 0,
    "ownerId": 0,
    "payAmount": 0.0,
    "payMethod": 0,
    "payTime": "",
    "operatorId": 0,
    "voucherNo": "",
    "status": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RPaymentRecord](#schemarpaymentrecord)|

## PUT 更新缴费记录

PUT /api/v1/payment-records/{id}

PUT /api/v1/payment-records/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "paymentNo": "string",
  "billId": 0,
  "ownerId": 0,
  "payAmount": 0,
  "payMethod": 0,
  "payTime": "string",
  "operatorId": 0,
  "voucherNo": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[PaymentRecord](#schemapaymentrecord)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除缴费记录

DELETE /api/v1/payment-records/{id}

DELETE /api/v1/payment-records/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# RolePermissionController

## GET 获取角色权限列表

GET /api/v1/role-permission/{roleId}/permissions

GET /api/v1/role-permission/{roleId}/permissions

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|roleId|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "permissionCode": "",
      "permissionName": "",
      "permissionType": 0,
      "parentId": 0,
      "permissionIcon": "",
      "permissionStr": "",
      "permissionComponent": "",
      "sort": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListSysPermissionInfo](#schemarlistsyspermissioninfo)|

## GET 获取所有权限（用于权限树）

GET /api/v1/role-permission/all

GET /api/v1/role-permission/all

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "permissionCode": "",
      "permissionName": "",
      "permissionType": 0,
      "parentId": 0,
      "permissionIcon": "",
      "permissionStr": "",
      "permissionComponent": "",
      "sort": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListSysPermissionInfo](#schemarlistsyspermissioninfo)|

## POST 分配角色权限（完整替换）

POST /api/v1/role-permission/assign

POST /api/v1/role-permission/assign

> Body 请求参数

```json
{
  "roleId": 0,
  "permissionIds": [
    0
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[AssignPermissionRequest](#schemaassignpermissionrequest)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## POST 新增角色权限

POST /api/v1/role-permission

POST /api/v1/role-permission

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "roleInfoId": 0,
  "permissionInfoId": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[SysRolePermission](#schemasysrolepermission)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除角色权限（根据ID）

DELETE /api/v1/role-permission/{id}

DELETE /api/v1/role-permission/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## PUT 修改角色权限

PUT /api/v1/role-permission/{id}

PUT /api/v1/role-permission/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "roleInfoId": 0,
  "permissionInfoId": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[SysRolePermission](#schemasysrolepermission)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除角色的某个权限（根据角色ID和权限ID）

DELETE /api/v1/role-permission/role/{roleId}/permission/{permissionId}

DELETE /api/v1/role-permission/role/{roleId}/permission/{permissionId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|roleId|path|integer| 是 |none|
|permissionId|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# FeeItemController

## GET 获取费用项目列表

GET /api/v1/fee-items

GET /api/v1/fee-items

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "feeCode": "",
      "feeName": "",
      "feeType": 0,
      "chargeMode": 0,
      "unitPrice": 0.0,
      "unit": "",
      "billingCycle": 0,
      "status": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListFeeItem](#schemarlistfeeitem)|

## POST 新增费用项目

POST /api/v1/fee-items

POST /api/v1/fee-items

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "feeCode": "string",
  "feeName": "string",
  "feeType": 0,
  "chargeMode": 0,
  "unitPrice": 0,
  "unit": "string",
  "billingCycle": 0,
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[FeeItem](#schemafeeitem)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单个费用项目

GET /api/v1/fee-items/{id}

GET /api/v1/fee-items/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "feeCode": "",
    "feeName": "",
    "feeType": 0,
    "chargeMode": 0,
    "unitPrice": 0.0,
    "unit": "",
    "billingCycle": 0,
    "status": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RFeeItem](#schemarfeeitem)|

## PUT 更新费用项目

PUT /api/v1/fee-items/{id}

PUT /api/v1/fee-items/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "feeCode": "string",
  "feeName": "string",
  "feeType": 0,
  "chargeMode": 0,
  "unitPrice": 0,
  "unit": "string",
  "billingCycle": 0,
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[FeeItem](#schemafeeitem)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除费用项目

DELETE /api/v1/fee-items/{id}

DELETE /api/v1/fee-items/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# BillController

## GET 获取账单列表

GET /api/v1/bills

GET /api/v1/bills

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "billNo": "",
      "houseId": 0,
      "ownerId": 0,
      "feeItemId": 0,
      "periodStart": "",
      "periodEnd": "",
      "amount": 0.0,
      "paidAmount": 0.0,
      "dueDate": "",
      "generateTime": "",
      "status": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListBill](#schemarlistbill)|

## POST 新增账单

POST /api/v1/bills

POST /api/v1/bills

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "billNo": "string",
  "houseId": 0,
  "ownerId": 0,
  "feeItemId": 0,
  "periodStart": "string",
  "periodEnd": "string",
  "amount": 0,
  "paidAmount": 0,
  "dueDate": "string",
  "generateTime": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[Bill](#schemabill)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单个账单

GET /api/v1/bills/{id}

GET /api/v1/bills/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "billNo": "",
    "houseId": 0,
    "ownerId": 0,
    "feeItemId": 0,
    "periodStart": "",
    "periodEnd": "",
    "amount": 0.0,
    "paidAmount": 0.0,
    "dueDate": "",
    "generateTime": "",
    "status": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RBill](#schemarbill)|

## PUT 更新账单

PUT /api/v1/bills/{id}

PUT /api/v1/bills/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "billNo": "string",
  "houseId": 0,
  "ownerId": 0,
  "feeItemId": 0,
  "periodStart": "string",
  "periodEnd": "string",
  "amount": 0,
  "paidAmount": 0,
  "dueDate": "string",
  "generateTime": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[Bill](#schemabill)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除账单

DELETE /api/v1/bills/{id}

DELETE /api/v1/bills/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# OwnerController

## GET 获取业主列表

GET /api/v1/owners

GET /api/v1/owners

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "ownerName": "",
      "phoneNumber": "",
      "idCard": "",
      "gender": 0,
      "houseId": 0,
      "relationship": "",
      "status": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListOwner](#schemarlistowner)|

## POST 添加业主

POST /api/v1/owners

POST /api/v1/owners

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "ownerName": "string",
  "phoneNumber": "string",
  "idCard": "string",
  "gender": 0,
  "houseId": 0,
  "relationship": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[Owner](#schemaowner)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单个业主

GET /api/v1/owners/{id}

GET /api/v1/owners/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "ownerName": "",
    "phoneNumber": "",
    "idCard": "",
    "gender": 0,
    "houseId": 0,
    "relationship": "",
    "status": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ROwner](#schemarowner)|

## DELETE 删除业主

DELETE /api/v1/owners/{id}

DELETE /api/v1/owners/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## PUT 修改业主信息

PUT /api/v1/owners/{id}

PUT /api/v1/owners/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "ownerName": "string",
  "phoneNumber": "string",
  "idCard": "string",
  "gender": 0,
  "houseId": 0,
  "relationship": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[Owner](#schemaowner)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# CommunityController

## GET 获取小区列表

GET /api/v1/communities

GET /api/v1/communities

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "communityName": "",
      "communityAddress": "",
      "area": 0.0,
      "totalBuildings": 0,
      "developer": "",
      "propertyCompany": "",
      "contactPhone": "",
      "status": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListCommunity](#schemarlistcommunity)|

## POST 新增小区

POST /api/v1/communities

POST /api/v1/communities

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "communityName": "string",
  "communityAddress": "string",
  "area": 0,
  "totalBuildings": 0,
  "developer": "string",
  "propertyCompany": "string",
  "contactPhone": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[Community](#schemacommunity)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单个小区

GET /api/v1/communities/{id}

GET /api/v1/communities/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "communityName": "",
    "communityAddress": "",
    "area": 0.0,
    "totalBuildings": 0,
    "developer": "",
    "propertyCompany": "",
    "contactPhone": "",
    "status": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RCommunity](#schemarcommunity)|

## PUT 更新小区

PUT /api/v1/communities/{id}

PUT /api/v1/communities/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "communityName": "string",
  "communityAddress": "string",
  "area": 0,
  "totalBuildings": 0,
  "developer": "string",
  "propertyCompany": "string",
  "contactPhone": "string",
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[Community](#schemacommunity)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除小区

DELETE /api/v1/communities/{id}

DELETE /api/v1/communities/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# UserController

## POST 用户注册

POST /api/v1/users/register

POST /api/v1/users/register

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "userName": "string",
  "password": "string",
  "phoneNumber": "string",
  "avatarAddress": "string",
  "fullName": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[SysUserInfo](#schemasysuserinfo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## POST 用户登录

POST /api/v1/users/login

POST /api/v1/users/login

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|username|query|string| 是 |none|
|password|query|string| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RString](#schemarstring)|

## GET 获取用户列表

GET /api/v1/users

GET /api/v1/users

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "userName": "",
      "password": "",
      "phoneNumber": "",
      "avatarAddress": "",
      "fullName": ""
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListSysUserInfo](#schemarlistsysuserinfo)|

## POST 新增用户

POST /api/v1/users

POST /api/v1/users

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "userName": "string",
  "password": "string",
  "phoneNumber": "string",
  "avatarAddress": "string",
  "fullName": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[SysUserInfo](#schemasysuserinfo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单个用户

GET /api/v1/users/{id}

GET /api/v1/users/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "userName": "",
    "password": "",
    "phoneNumber": "",
    "avatarAddress": "",
    "fullName": ""
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RSysUserInfo](#schemarsysuserinfo)|

## PUT 更新用户

PUT /api/v1/users/{id}

PUT /api/v1/users/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "userName": "string",
  "password": "string",
  "phoneNumber": "string",
  "avatarAddress": "string",
  "fullName": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[SysUserInfo](#schemasysuserinfo)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除用户

DELETE /api/v1/users/{id}

DELETE /api/v1/users/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# InspectionRecordController

## GET 获取巡检整改记录列表

GET /api/v1/inspection-records

GET /api/v1/inspection-records

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": [
    {
      "id": 0,
      "createTime": "",
      "updateTime": "",
      "createUser": 0,
      "updateUser": 0,
      "description": "",
      "remark": "",
      "deleted": 0,
      "inspectionId": 0,
      "recordNo": "",
      "issueType": "",
      "issueDesc": "",
      "issueImages": "",
      "issueLocation": "",
      "severity": 0,
      "handleUserId": 0,
      "handleDesc": "",
      "handleImages": "",
      "planDeadline": "",
      "actualCompleteTime": "",
      "verifyUserId": 0,
      "verifyTime": "",
      "verifyResult": 0,
      "status": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RListInspectionRecord](#schemarlistinspectionrecord)|

## POST 新增巡检整改记录

POST /api/v1/inspection-records

POST /api/v1/inspection-records

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "inspectionId": 0,
  "recordNo": "string",
  "issueType": "string",
  "issueDesc": "string",
  "issueImages": "string",
  "issueLocation": "string",
  "severity": 0,
  "handleUserId": 0,
  "handleDesc": "string",
  "handleImages": "string",
  "planDeadline": "string",
  "actualCompleteTime": "string",
  "verifyUserId": 0,
  "verifyTime": "string",
  "verifyResult": 0,
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[InspectionRecord](#schemainspectionrecord)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## GET 获取单条巡检整改记录

GET /api/v1/inspection-records/{id}

GET /api/v1/inspection-records/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": {
    "id": 0,
    "createTime": "",
    "updateTime": "",
    "createUser": 0,
    "updateUser": 0,
    "description": "",
    "remark": "",
    "deleted": 0,
    "inspectionId": 0,
    "recordNo": "",
    "issueType": "",
    "issueDesc": "",
    "issueImages": "",
    "issueLocation": "",
    "severity": 0,
    "handleUserId": 0,
    "handleDesc": "",
    "handleImages": "",
    "planDeadline": "",
    "actualCompleteTime": "",
    "verifyUserId": 0,
    "verifyTime": "",
    "verifyResult": 0,
    "status": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RInspectionRecord](#schemarinspectionrecord)|

## PUT 更新巡检整改记录

PUT /api/v1/inspection-records/{id}

PUT /api/v1/inspection-records/{id}

> Body 请求参数

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "inspectionId": 0,
  "recordNo": "string",
  "issueType": "string",
  "issueDesc": "string",
  "issueImages": "string",
  "issueLocation": "string",
  "severity": 0,
  "handleUserId": 0,
  "handleDesc": "string",
  "handleImages": "string",
  "planDeadline": "string",
  "actualCompleteTime": "string",
  "verifyUserId": 0,
  "verifyTime": "string",
  "verifyResult": 0,
  "status": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|body|body|[InspectionRecord](#schemainspectionrecord)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

## DELETE 删除巡检整改记录

DELETE /api/v1/inspection-records/{id}

DELETE /api/v1/inspection-records/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[RVoid](#schemarvoid)|

# 数据模型

<h2 id="tocS_Repair">Repair</h2>

<a id="schemarepair"></a>
<a id="schema_Repair"></a>
<a id="tocSrepair"></a>
<a id="tocsrepair"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "repairNo": "string",
  "ownerId": 0,
  "houseId": 0,
  "repairType": "string",
  "repairContent": "string",
  "repairImages": "string",
  "contactPhone": "string",
  "expectedTime": "string",
  "status": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|repairNo|string|false|none||报修单号(后端自动生成)|
|ownerId|integer(int64)|true|none||报修业主ID|
|houseId|integer(int64)|true|none||房屋ID|
|repairType|string|true|none||报修类型(水电/门窗/管道/公共设施等)|
|repairContent|string|true|none||报修内容|
|repairImages|string|false|none||报修图片(多张逗号分隔)|
|contactPhone|string|false|none||联系电话|
|expectedTime|string|false|none||期望维修时间|
|status|integer|true|none||状态：0.待处理 1.已派单 2.维修中 3.已完成 4.已评价 5.已关闭|

<h2 id="tocS_RListRepair">RListRepair</h2>

<a id="schemarlistrepair"></a>
<a id="schema_RListRepair"></a>
<a id="tocSrlistrepair"></a>
<a id="tocsrlistrepair"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "repairNo": "string",
      "ownerId": 0,
      "houseId": 0,
      "repairType": "string",
      "repairContent": "string",
      "repairImages": "string",
      "contactPhone": "string",
      "expectedTime": "string",
      "status": 0
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[Repair](#schemarepair)]|false|none||none|

<h2 id="tocS_RRepair">RRepair</h2>

<a id="schemarrepair"></a>
<a id="schema_RRepair"></a>
<a id="tocSrrepair"></a>
<a id="tocsrrepair"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "repairNo": "string",
    "ownerId": 0,
    "houseId": 0,
    "repairType": "string",
    "repairContent": "string",
    "repairImages": "string",
    "contactPhone": "string",
    "expectedTime": "string",
    "status": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[Repair](#schemarepair)|false|none||none|

<h2 id="tocS_RVoid">RVoid</h2>

<a id="schemarvoid"></a>
<a id="schema_RVoid"></a>
<a id="tocSrvoid"></a>
<a id="tocsrvoid"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": null
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|null|false|none||none|

<h2 id="tocS_Complaint">Complaint</h2>

<a id="schemacomplaint"></a>
<a id="schema_Complaint"></a>
<a id="tocScomplaint"></a>
<a id="tocscomplaint"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "complaintNo": "string",
  "ownerId": 0,
  "complaintType": 0,
  "complaintCategory": "string",
  "complaintTitle": "string",
  "complaintContent": "string",
  "complaintImages": "string",
  "contactPhone": "string",
  "handleUserId": 0,
  "handleTime": "string",
  "handleResult": "string",
  "status": 0,
  "satisfaction": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|complaintNo|string|false|none||投诉编号(后端自动生成)|
|ownerId|integer(int64)|true|none||投诉业主ID|
|complaintType|integer|true|none||类型：1.投诉 2.建议|
|complaintCategory|string|true|none||分类(物业服务/环境卫生/安全隐患/设施损坏/其他)|
|complaintTitle|string|true|none||标题|
|complaintContent|string|true|none||详细内容|
|complaintImages|string|false|none||图片(多张逗号分隔)|
|contactPhone|string|false|none||联系电话|
|handleUserId|integer(int64)|false|none||处理人ID(关联sys_user_info)|
|handleTime|string|false|none||处理时间|
|handleResult|string|false|none||处理结果|
|status|integer|true|none||状态：0.待处理 1.处理中 2.已处理 3.已关闭|
|satisfaction|integer|false|none||满意度评分(1-5)|

<h2 id="tocS_RListComplaint">RListComplaint</h2>

<a id="schemarlistcomplaint"></a>
<a id="schema_RListComplaint"></a>
<a id="tocSrlistcomplaint"></a>
<a id="tocsrlistcomplaint"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "complaintNo": "string",
      "ownerId": 0,
      "complaintType": 0,
      "complaintCategory": "string",
      "complaintTitle": "string",
      "complaintContent": "string",
      "complaintImages": "string",
      "contactPhone": "string",
      "handleUserId": 0,
      "handleTime": "string",
      "handleResult": "string",
      "status": 0,
      "satisfaction": 0
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[Complaint](#schemacomplaint)]|false|none||none|

<h2 id="tocS_RComplaint">RComplaint</h2>

<a id="schemarcomplaint"></a>
<a id="schema_RComplaint"></a>
<a id="tocSrcomplaint"></a>
<a id="tocsrcomplaint"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "complaintNo": "string",
    "ownerId": 0,
    "complaintType": 0,
    "complaintCategory": "string",
    "complaintTitle": "string",
    "complaintContent": "string",
    "complaintImages": "string",
    "contactPhone": "string",
    "handleUserId": 0,
    "handleTime": "string",
    "handleResult": "string",
    "status": 0,
    "satisfaction": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[Complaint](#schemacomplaint)|false|none||none|

<h2 id="tocS_ComplaintHandleDTO">ComplaintHandleDTO</h2>

<a id="schemacomplainthandledto"></a>
<a id="schema_ComplaintHandleDTO"></a>
<a id="tocScomplainthandledto"></a>
<a id="tocscomplainthandledto"></a>

```json
{
  "handleUserId": 0,
  "handleTime": "string",
  "handleResult": "string",
  "status": 0,
  "satisfaction": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|handleUserId|integer(int64)|false|none||处理人ID(关联sys_user_info)|
|handleTime|string|false|none||处理时间|
|handleResult|string|false|none||处理结果|
|status|integer|true|none||状态：0.待处理 1.处理中 2.已处理 3.已关闭|
|satisfaction|integer|false|none||满意度评分(1-5)|

<h2 id="tocS_SysPermissionInfo">SysPermissionInfo</h2>

<a id="schemasyspermissioninfo"></a>
<a id="schema_SysPermissionInfo"></a>
<a id="tocSsyspermissioninfo"></a>
<a id="tocssyspermissioninfo"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "permissionCode": "string",
  "permissionName": "string",
  "permissionType": 0,
  "parentId": 0,
  "permissionIcon": "string",
  "permissionStr": "string",
  "permissionComponent": "string",
  "sort": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|permissionCode|string|false|none||权限编码|
|permissionName|string|true|none||权限名称|
|permissionType|integer|true|none||权限类型<br />1：目录<br />2：菜单<br />3：按钮|
|parentId|integer(int64)|false|none||父节点ID|
|permissionIcon|string|false|none||图标|
|permissionStr|string|false|none||权限标识|
|permissionComponent|string|false|none||前端组件地址|
|sort|integer|true|none||排序|

<h2 id="tocS_RListSysPermissionInfo">RListSysPermissionInfo</h2>

<a id="schemarlistsyspermissioninfo"></a>
<a id="schema_RListSysPermissionInfo"></a>
<a id="tocSrlistsyspermissioninfo"></a>
<a id="tocsrlistsyspermissioninfo"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "permissionCode": "string",
      "permissionName": "string",
      "permissionType": 0,
      "parentId": 0,
      "permissionIcon": "string",
      "permissionStr": "string",
      "permissionComponent": "string",
      "sort": 0
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[SysPermissionInfo](#schemasyspermissioninfo)]|false|none||none|

<h2 id="tocS_RSysPermissionInfo">RSysPermissionInfo</h2>

<a id="schemarsyspermissioninfo"></a>
<a id="schema_RSysPermissionInfo"></a>
<a id="tocSrsyspermissioninfo"></a>
<a id="tocsrsyspermissioninfo"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "permissionCode": "string",
    "permissionName": "string",
    "permissionType": 0,
    "parentId": 0,
    "permissionIcon": "string",
    "permissionStr": "string",
    "permissionComponent": "string",
    "sort": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[SysPermissionInfo](#schemasyspermissioninfo)|false|none||none|

<h2 id="tocS_RepairEvaluate">RepairEvaluate</h2>

<a id="schemarepairevaluate"></a>
<a id="schema_RepairEvaluate"></a>
<a id="tocSrepairevaluate"></a>
<a id="tocsrepairevaluate"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "repairId": 0,
  "ownerId": 0,
  "score": 0,
  "evaluateContent": "string",
  "evaluateImages": "string",
  "serviceAttitude": 0,
  "repairQuality": 0,
  "responseSpeed": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|repairId|integer(int64)|true|none||报修单ID|
|ownerId|integer(int64)|true|none||评价业主ID|
|score|integer|true|none||评分(1-5分)|
|evaluateContent|string|false|none||评价内容|
|evaluateImages|string|false|none||评价图片(多张逗号分隔)|
|serviceAttitude|integer|false|none||服务态度评分(1-5)|
|repairQuality|integer|false|none||维修质量评分(1-5)|
|responseSpeed|integer|false|none||响应速度评分(1-5)|

<h2 id="tocS_RListRepairEvaluate">RListRepairEvaluate</h2>

<a id="schemarlistrepairevaluate"></a>
<a id="schema_RListRepairEvaluate"></a>
<a id="tocSrlistrepairevaluate"></a>
<a id="tocsrlistrepairevaluate"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "repairId": 0,
      "ownerId": 0,
      "score": 0,
      "evaluateContent": "string",
      "evaluateImages": "string",
      "serviceAttitude": 0,
      "repairQuality": 0,
      "responseSpeed": 0
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[RepairEvaluate](#schemarepairevaluate)]|false|none||none|

<h2 id="tocS_RRepairEvaluate">RRepairEvaluate</h2>

<a id="schemarrepairevaluate"></a>
<a id="schema_RRepairEvaluate"></a>
<a id="tocSrrepairevaluate"></a>
<a id="tocsrrepairevaluate"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "repairId": 0,
    "ownerId": 0,
    "score": 0,
    "evaluateContent": "string",
    "evaluateImages": "string",
    "serviceAttitude": 0,
    "repairQuality": 0,
    "responseSpeed": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[RepairEvaluate](#schemarepairevaluate)|false|none||none|

<h2 id="tocS_Building">Building</h2>

<a id="schemabuilding"></a>
<a id="schema_Building"></a>
<a id="tocSbuilding"></a>
<a id="tocsbuilding"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "communityId": 0,
  "buildingName": "string",
  "totalFloors": 0,
  "unitsPerFloor": 0,
  "status": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|communityId|integer(int64)|true|none||所属小区ID|
|buildingName|string|true|none||楼栋名称|
|totalFloors|integer|false|none||总层数|
|unitsPerFloor|integer|false|none||每层户数|
|status|integer|true|none||状态：1.正常 0.停用|

<h2 id="tocS_RListBuilding">RListBuilding</h2>

<a id="schemarlistbuilding"></a>
<a id="schema_RListBuilding"></a>
<a id="tocSrlistbuilding"></a>
<a id="tocsrlistbuilding"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "communityId": 0,
      "buildingName": "string",
      "totalFloors": 0,
      "unitsPerFloor": 0,
      "status": 0
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[Building](#schemabuilding)]|false|none||none|

<h2 id="tocS_RBuilding">RBuilding</h2>

<a id="schemarbuilding"></a>
<a id="schema_RBuilding"></a>
<a id="tocSrbuilding"></a>
<a id="tocsrbuilding"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "communityId": 0,
    "buildingName": "string",
    "totalFloors": 0,
    "unitsPerFloor": 0,
    "status": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[Building](#schemabuilding)|false|none||none|

<h2 id="tocS_SysRoleInfo">SysRoleInfo</h2>

<a id="schemasysroleinfo"></a>
<a id="schema_SysRoleInfo"></a>
<a id="tocSsysroleinfo"></a>
<a id="tocssysroleinfo"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "roleCode": "string",
  "roleName": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|roleCode|string|false|none||角色编码|
|roleName|string|true|none||角色名称|

<h2 id="tocS_RListSysRoleInfo">RListSysRoleInfo</h2>

<a id="schemarlistsysroleinfo"></a>
<a id="schema_RListSysRoleInfo"></a>
<a id="tocSrlistsysroleinfo"></a>
<a id="tocsrlistsysroleinfo"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "roleCode": "string",
      "roleName": "string"
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[SysRoleInfo](#schemasysroleinfo)]|false|none||none|

<h2 id="tocS_RSysRoleInfo">RSysRoleInfo</h2>

<a id="schemarsysroleinfo"></a>
<a id="schema_RSysRoleInfo"></a>
<a id="tocSrsysroleinfo"></a>
<a id="tocsrsysroleinfo"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "roleCode": "string",
    "roleName": "string"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[SysRoleInfo](#schemasysroleinfo)|false|none||none|

<h2 id="tocS_RepairOrder">RepairOrder</h2>

<a id="schemarepairorder"></a>
<a id="schema_RepairOrder"></a>
<a id="tocSrepairorder"></a>
<a id="tocsrepairorder"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "repairId": 0,
  "workerId": 0,
  "assignTime": "string",
  "acceptTime": "string",
  "completeTime": "string",
  "resultDesc": "string",
  "status": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|repairId|integer(int64)|true|none||报修单ID|
|workerId|integer(int64)|true|none||维修人员ID(关联sys_user_info)|
|assignTime|string|true|none||派单时间|
|acceptTime|string|false|none||接单时间|
|completeTime|string|false|none||完成时间|
|resultDesc|string|false|none||维修结果描述|
|status|integer|true|none||状态：0.待接单 1.已接单 2.维修中 3.已完成 4.已取消|

<h2 id="tocS_RListRepairOrder">RListRepairOrder</h2>

<a id="schemarlistrepairorder"></a>
<a id="schema_RListRepairOrder"></a>
<a id="tocSrlistrepairorder"></a>
<a id="tocsrlistrepairorder"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "repairId": 0,
      "workerId": 0,
      "assignTime": "string",
      "acceptTime": "string",
      "completeTime": "string",
      "resultDesc": "string",
      "status": 0
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[RepairOrder](#schemarepairorder)]|false|none||none|

<h2 id="tocS_RRepairOrder">RRepairOrder</h2>

<a id="schemarrepairorder"></a>
<a id="schema_RRepairOrder"></a>
<a id="tocSrrepairorder"></a>
<a id="tocsrrepairorder"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "repairId": 0,
    "workerId": 0,
    "assignTime": "string",
    "acceptTime": "string",
    "completeTime": "string",
    "resultDesc": "string",
    "status": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[RepairOrder](#schemarepairorder)|false|none||none|

<h2 id="tocS_Inspection">Inspection</h2>

<a id="schemainspection"></a>
<a id="schema_Inspection"></a>
<a id="tocSinspection"></a>
<a id="tocsinspection"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "inspectionNo": "string",
  "inspectionName": "string",
  "communityId": 0,
  "inspectionArea": "string",
  "inspectorId": 0,
  "planStartTime": "string",
  "planEndTime": "string",
  "actualStartTime": "string",
  "actualEndTime": "string",
  "inspectionItems": "string",
  "status": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|inspectionNo|string|true|none||巡检任务编号|
|inspectionName|string|true|none||巡检任务名称|
|communityId|integer(int64)|true|none||所属小区ID|
|inspectionArea|string|false|none||巡检区域|
|inspectorId|integer(int64)|true|none||巡检人员ID(关联sys_user_info)|
|planStartTime|string|true|none||计划开始时间|
|planEndTime|string|true|none||计划结束时间|
|actualStartTime|string|false|none||实际开始时间|
|actualEndTime|string|false|none||实际结束时间|
|inspectionItems|string|false|none||巡检项目(如：消防设施/电梯/楼道/停车场等)|
|status|integer|true|none||状态：0.待执行 1.巡检中 2.已完成 3.已取消|

<h2 id="tocS_RListInspection">RListInspection</h2>

<a id="schemarlistinspection"></a>
<a id="schema_RListInspection"></a>
<a id="tocSrlistinspection"></a>
<a id="tocsrlistinspection"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "inspectionNo": "string",
      "inspectionName": "string",
      "communityId": 0,
      "inspectionArea": "string",
      "inspectorId": 0,
      "planStartTime": "string",
      "planEndTime": "string",
      "actualStartTime": "string",
      "actualEndTime": "string",
      "inspectionItems": "string",
      "status": 0
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[Inspection](#schemainspection)]|false|none||none|

<h2 id="tocS_RInspection">RInspection</h2>

<a id="schemarinspection"></a>
<a id="schema_RInspection"></a>
<a id="tocSrinspection"></a>
<a id="tocsrinspection"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "inspectionNo": "string",
    "inspectionName": "string",
    "communityId": 0,
    "inspectionArea": "string",
    "inspectorId": 0,
    "planStartTime": "string",
    "planEndTime": "string",
    "actualStartTime": "string",
    "actualEndTime": "string",
    "inspectionItems": "string",
    "status": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[Inspection](#schemainspection)|false|none||none|

<h2 id="tocS_SysUserRole">SysUserRole</h2>

<a id="schemasysuserrole"></a>
<a id="schema_SysUserRole"></a>
<a id="tocSsysuserrole"></a>
<a id="tocssysuserrole"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "userInfoId": 0,
  "roleInfoId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|userInfoId|integer(int64)|true|none||用户ID|
|roleInfoId|integer(int64)|true|none||角色ID|

<h2 id="tocS_RListString">RListString</h2>

<a id="schemarliststring"></a>
<a id="schema_RListString"></a>
<a id="tocSrliststring"></a>
<a id="tocsrliststring"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    "string"
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[string]|false|none||none|

<h2 id="tocS_House">House</h2>

<a id="schemahouse"></a>
<a id="schema_House"></a>
<a id="tocShouse"></a>
<a id="tocshouse"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "buildingId": 0,
  "houseNumber": "string",
  "floor": 0,
  "unitNumber": 0,
  "area": 0,
  "houseType": "string",
  "status": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|buildingId|integer(int64)|true|none||所属楼栋ID|
|houseNumber|string|true|none||房屋编号|
|floor|integer|true|none||所在楼层|
|unitNumber|integer|false|none||单元号|
|area|number|false|none||房屋面积(平方米)|
|houseType|string|false|none||户型(如：一室一厅)|
|status|integer|true|none||状态：0.空置 1.已入住 2.已出租|

<h2 id="tocS_RListHouse">RListHouse</h2>

<a id="schemarlisthouse"></a>
<a id="schema_RListHouse"></a>
<a id="tocSrlisthouse"></a>
<a id="tocsrlisthouse"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "buildingId": 0,
      "houseNumber": "string",
      "floor": 0,
      "unitNumber": 0,
      "area": 0,
      "houseType": "string",
      "status": 0
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[House](#schemahouse)]|false|none||none|

<h2 id="tocS_RHouse">RHouse</h2>

<a id="schemarhouse"></a>
<a id="schema_RHouse"></a>
<a id="tocSrhouse"></a>
<a id="tocsrhouse"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "buildingId": 0,
    "houseNumber": "string",
    "floor": 0,
    "unitNumber": 0,
    "area": 0,
    "houseType": "string",
    "status": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[House](#schemahouse)|false|none||none|

<h2 id="tocS_HouseDTO">HouseDTO</h2>

<a id="schemahousedto"></a>
<a id="schema_HouseDTO"></a>
<a id="tocShousedto"></a>
<a id="tocshousedto"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "buildingId": 0,
  "houseNumber": "string",
  "floor": 0,
  "unitNumber": 0,
  "area": 0,
  "houseType": "string",
  "status": 0,
  "buildingName": "string",
  "communityName": "string",
  "communityAddress": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|buildingId|integer(int64)|true|none||所属楼栋ID|
|houseNumber|string|true|none||房屋编号|
|floor|integer|true|none||所在楼层|
|unitNumber|integer|false|none||单元号|
|area|number|false|none||房屋面积(平方米)|
|houseType|string|false|none||户型(如：一室一厅)|
|status|integer|true|none||状态：0.空置 1.已入住 2.已出租|
|buildingName|string|false|none||none|
|communityName|string|false|none||none|
|communityAddress|string|false|none||none|

<h2 id="tocS_RHouseDTO">RHouseDTO</h2>

<a id="schemarhousedto"></a>
<a id="schema_RHouseDTO"></a>
<a id="tocSrhousedto"></a>
<a id="tocsrhousedto"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "buildingId": 0,
    "houseNumber": "string",
    "floor": 0,
    "unitNumber": 0,
    "area": 0,
    "houseType": "string",
    "status": 0,
    "buildingName": "string",
    "communityName": "string",
    "communityAddress": "string"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[HouseDTO](#schemahousedto)|false|none||none|

<h2 id="tocS_RListHouseDTO">RListHouseDTO</h2>

<a id="schemarlisthousedto"></a>
<a id="schema_RListHouseDTO"></a>
<a id="tocSrlisthousedto"></a>
<a id="tocsrlisthousedto"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "buildingId": 0,
      "houseNumber": "string",
      "floor": 0,
      "unitNumber": 0,
      "area": 0,
      "houseType": "string",
      "status": 0,
      "buildingName": "string",
      "communityName": "string",
      "communityAddress": "string"
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[HouseDTO](#schemahousedto)]|false|none||none|

<h2 id="tocS_PaymentRecord">PaymentRecord</h2>

<a id="schemapaymentrecord"></a>
<a id="schema_PaymentRecord"></a>
<a id="tocSpaymentrecord"></a>
<a id="tocspaymentrecord"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "paymentNo": "string",
  "billId": 0,
  "ownerId": 0,
  "payAmount": 0,
  "payMethod": 0,
  "payTime": "string",
  "operatorId": 0,
  "voucherNo": "string",
  "status": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|paymentNo|string|false|none||缴费流水号(后端自动生成)|
|billId|integer(int64)|true|none||账单ID|
|ownerId|integer(int64)|true|none||缴费业主ID|
|payAmount|number|true|none||本次缴费金额(元)|
|payMethod|integer|true|none||缴费方式：1.现金 2.微信 3.支付宝 4.银行卡 5.其他|
|payTime|string|true|none||缴费时间|
|operatorId|integer(int64)|true|none||登记人员ID(关联sys_user_info)|
|voucherNo|string|false|none||第三方支付流水号/凭证号|
|status|integer|true|none||状态：1.有效 0.已作废(如登记有误被撤销)|

<h2 id="tocS_RListPaymentRecord">RListPaymentRecord</h2>

<a id="schemarlistpaymentrecord"></a>
<a id="schema_RListPaymentRecord"></a>
<a id="tocSrlistpaymentrecord"></a>
<a id="tocsrlistpaymentrecord"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "paymentNo": "string",
      "billId": 0,
      "ownerId": 0,
      "payAmount": 0,
      "payMethod": 0,
      "payTime": "string",
      "operatorId": 0,
      "voucherNo": "string",
      "status": 0
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[PaymentRecord](#schemapaymentrecord)]|false|none||none|

<h2 id="tocS_RPaymentRecord">RPaymentRecord</h2>

<a id="schemarpaymentrecord"></a>
<a id="schema_RPaymentRecord"></a>
<a id="tocSrpaymentrecord"></a>
<a id="tocsrpaymentrecord"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "paymentNo": "string",
    "billId": 0,
    "ownerId": 0,
    "payAmount": 0,
    "payMethod": 0,
    "payTime": "string",
    "operatorId": 0,
    "voucherNo": "string",
    "status": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[PaymentRecord](#schemapaymentrecord)|false|none||none|

<h2 id="tocS_AssignPermissionRequest">AssignPermissionRequest</h2>

<a id="schemaassignpermissionrequest"></a>
<a id="schema_AssignPermissionRequest"></a>
<a id="tocSassignpermissionrequest"></a>
<a id="tocsassignpermissionrequest"></a>

```json
{
  "roleId": 0,
  "permissionIds": [
    0
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|roleId|integer(int64)|false|none||none|
|permissionIds|[integer]|false|none||none|

<h2 id="tocS_SysRolePermission">SysRolePermission</h2>

<a id="schemasysrolepermission"></a>
<a id="schema_SysRolePermission"></a>
<a id="tocSsysrolepermission"></a>
<a id="tocssysrolepermission"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "roleInfoId": 0,
  "permissionInfoId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|roleInfoId|integer(int64)|true|none||角色ID|
|permissionInfoId|integer(int64)|true|none||权限ID|

<h2 id="tocS_FeeItem">FeeItem</h2>

<a id="schemafeeitem"></a>
<a id="schema_FeeItem"></a>
<a id="tocSfeeitem"></a>
<a id="tocsfeeitem"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "feeCode": "string",
  "feeName": "string",
  "feeType": 0,
  "chargeMode": 0,
  "unitPrice": 0,
  "unit": "string",
  "billingCycle": 0,
  "status": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|feeCode|string|true|none||费用编码|
|feeName|string|true|none||费用名称(如：物业费/停车费/水费)|
|feeType|integer|true|none||费用类型：1.物业费 2.水费 3.电费 4.停车费 5.其他|
|chargeMode|integer|true|none||计费方式：1.按面积计费 2.固定金额 3.按用量计费|
|unitPrice|number|true|none||单价(元)|
|unit|string|false|none||计价单位(如：元/㎡/月、元/车位/月)|
|billingCycle|integer|true|none||计费周期：1.月度 2.季度 3.年度 4.一次性|
|status|integer|true|none||状态：1.启用 0.停用|

<h2 id="tocS_RListFeeItem">RListFeeItem</h2>

<a id="schemarlistfeeitem"></a>
<a id="schema_RListFeeItem"></a>
<a id="tocSrlistfeeitem"></a>
<a id="tocsrlistfeeitem"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "feeCode": "string",
      "feeName": "string",
      "feeType": 0,
      "chargeMode": 0,
      "unitPrice": 0,
      "unit": "string",
      "billingCycle": 0,
      "status": 0
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[FeeItem](#schemafeeitem)]|false|none||none|

<h2 id="tocS_RFeeItem">RFeeItem</h2>

<a id="schemarfeeitem"></a>
<a id="schema_RFeeItem"></a>
<a id="tocSrfeeitem"></a>
<a id="tocsrfeeitem"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "feeCode": "string",
    "feeName": "string",
    "feeType": 0,
    "chargeMode": 0,
    "unitPrice": 0,
    "unit": "string",
    "billingCycle": 0,
    "status": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[FeeItem](#schemafeeitem)|false|none||none|

<h2 id="tocS_Bill">Bill</h2>

<a id="schemabill"></a>
<a id="schema_Bill"></a>
<a id="tocSbill"></a>
<a id="tocsbill"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "billNo": "string",
  "houseId": 0,
  "ownerId": 0,
  "feeItemId": 0,
  "periodStart": "string",
  "periodEnd": "string",
  "amount": 0,
  "paidAmount": 0,
  "dueDate": "string",
  "generateTime": "string",
  "status": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|billNo|string|false|none||账单编号(后端自动生成)|
|houseId|integer(int64)|true|none||房屋ID|
|ownerId|integer(int64)|true|none||业主ID|
|feeItemId|integer(int64)|true|none||费用项目ID|
|periodStart|string|true|none||账期开始日期|
|periodEnd|string|true|none||账期结束日期|
|amount|number|true|none||应收金额(元)|
|paidAmount|number|true|none||已收金额(元)|
|dueDate|string|false|none||缴费截止日期|
|generateTime|string|true|none||账单生成时间|
|status|integer|true|none||状态：0.未缴纳 1.部分缴纳 2.已缴清 3.已作废|

<h2 id="tocS_RListBill">RListBill</h2>

<a id="schemarlistbill"></a>
<a id="schema_RListBill"></a>
<a id="tocSrlistbill"></a>
<a id="tocsrlistbill"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "billNo": "string",
      "houseId": 0,
      "ownerId": 0,
      "feeItemId": 0,
      "periodStart": "string",
      "periodEnd": "string",
      "amount": 0,
      "paidAmount": 0,
      "dueDate": "string",
      "generateTime": "string",
      "status": 0
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[Bill](#schemabill)]|false|none||none|

<h2 id="tocS_RBill">RBill</h2>

<a id="schemarbill"></a>
<a id="schema_RBill"></a>
<a id="tocSrbill"></a>
<a id="tocsrbill"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "billNo": "string",
    "houseId": 0,
    "ownerId": 0,
    "feeItemId": 0,
    "periodStart": "string",
    "periodEnd": "string",
    "amount": 0,
    "paidAmount": 0,
    "dueDate": "string",
    "generateTime": "string",
    "status": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[Bill](#schemabill)|false|none||none|

<h2 id="tocS_Owner">Owner</h2>

<a id="schemaowner"></a>
<a id="schema_Owner"></a>
<a id="tocSowner"></a>
<a id="tocsowner"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "ownerName": "string",
  "phoneNumber": "string",
  "idCard": "string",
  "gender": 0,
  "houseId": 0,
  "relationship": "string",
  "status": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|ownerName|string|true|none||业主姓名|
|phoneNumber|string|true|none||联系电话|
|idCard|string|false|none||身份证号|
|gender|integer|false|none||性别：1.男 2.女|
|houseId|integer(int64)|false|none||关联房屋ID|
|relationship|string|false|none||与房屋关系(业主/家属/租户)|
|status|integer|true|none||状态：1.正常 0.停用|

<h2 id="tocS_RListOwner">RListOwner</h2>

<a id="schemarlistowner"></a>
<a id="schema_RListOwner"></a>
<a id="tocSrlistowner"></a>
<a id="tocsrlistowner"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "ownerName": "string",
      "phoneNumber": "string",
      "idCard": "string",
      "gender": 0,
      "houseId": 0,
      "relationship": "string",
      "status": 0
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[Owner](#schemaowner)]|false|none||none|

<h2 id="tocS_ROwner">ROwner</h2>

<a id="schemarowner"></a>
<a id="schema_ROwner"></a>
<a id="tocSrowner"></a>
<a id="tocsrowner"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "ownerName": "string",
    "phoneNumber": "string",
    "idCard": "string",
    "gender": 0,
    "houseId": 0,
    "relationship": "string",
    "status": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[Owner](#schemaowner)|false|none||none|

<h2 id="tocS_Community">Community</h2>

<a id="schemacommunity"></a>
<a id="schema_Community"></a>
<a id="tocScommunity"></a>
<a id="tocscommunity"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "communityName": "string",
  "communityAddress": "string",
  "area": 0,
  "totalBuildings": 0,
  "developer": "string",
  "propertyCompany": "string",
  "contactPhone": "string",
  "status": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|communityName|string|true|none||小区名称|
|communityAddress|string|true|none||小区地址|
|area|number|false|none||小区面积(平方米)|
|totalBuildings|integer|false|none||总楼栋数|
|developer|string|false|none||开发商|
|propertyCompany|string|false|none||物业公司|
|contactPhone|string|false|none||联系电话|
|status|integer|true|none||状态：1.正常 0.停用|

<h2 id="tocS_RListCommunity">RListCommunity</h2>

<a id="schemarlistcommunity"></a>
<a id="schema_RListCommunity"></a>
<a id="tocSrlistcommunity"></a>
<a id="tocsrlistcommunity"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "communityName": "string",
      "communityAddress": "string",
      "area": 0,
      "totalBuildings": 0,
      "developer": "string",
      "propertyCompany": "string",
      "contactPhone": "string",
      "status": 0
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[Community](#schemacommunity)]|false|none||none|

<h2 id="tocS_RCommunity">RCommunity</h2>

<a id="schemarcommunity"></a>
<a id="schema_RCommunity"></a>
<a id="tocSrcommunity"></a>
<a id="tocsrcommunity"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "communityName": "string",
    "communityAddress": "string",
    "area": 0,
    "totalBuildings": 0,
    "developer": "string",
    "propertyCompany": "string",
    "contactPhone": "string",
    "status": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[Community](#schemacommunity)|false|none||none|

<h2 id="tocS_SysUserInfo">SysUserInfo</h2>

<a id="schemasysuserinfo"></a>
<a id="schema_SysUserInfo"></a>
<a id="tocSsysuserinfo"></a>
<a id="tocssysuserinfo"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "userName": "string",
  "password": "string",
  "phoneNumber": "string",
  "avatarAddress": "string",
  "fullName": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|userName|string|true|none||用户名（登录账号）|
|password|string|true|none||登录密码|
|phoneNumber|string|true|none||手机号|
|avatarAddress|string|false|none||头像地址|
|fullName|string|true|none||姓名|

<h2 id="tocS_RString">RString</h2>

<a id="schemarstring"></a>
<a id="schema_RString"></a>
<a id="tocSrstring"></a>
<a id="tocsrstring"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|string|false|none||none|

<h2 id="tocS_RListSysUserInfo">RListSysUserInfo</h2>

<a id="schemarlistsysuserinfo"></a>
<a id="schema_RListSysUserInfo"></a>
<a id="tocSrlistsysuserinfo"></a>
<a id="tocsrlistsysuserinfo"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "userName": "string",
      "password": "string",
      "phoneNumber": "string",
      "avatarAddress": "string",
      "fullName": "string"
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[SysUserInfo](#schemasysuserinfo)]|false|none||none|

<h2 id="tocS_RSysUserInfo">RSysUserInfo</h2>

<a id="schemarsysuserinfo"></a>
<a id="schema_RSysUserInfo"></a>
<a id="tocSrsysuserinfo"></a>
<a id="tocsrsysuserinfo"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "userName": "string",
    "password": "string",
    "phoneNumber": "string",
    "avatarAddress": "string",
    "fullName": "string"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[SysUserInfo](#schemasysuserinfo)|false|none||none|

<h2 id="tocS_InspectionRecord">InspectionRecord</h2>

<a id="schemainspectionrecord"></a>
<a id="schema_InspectionRecord"></a>
<a id="tocSinspectionrecord"></a>
<a id="tocsinspectionrecord"></a>

```json
{
  "id": 0,
  "createTime": "string",
  "updateTime": "string",
  "createUser": 0,
  "updateUser": 0,
  "description": "string",
  "remark": "string",
  "deleted": 0,
  "inspectionId": 0,
  "recordNo": "string",
  "issueType": "string",
  "issueDesc": "string",
  "issueImages": "string",
  "issueLocation": "string",
  "severity": 0,
  "handleUserId": 0,
  "handleDesc": "string",
  "handleImages": "string",
  "planDeadline": "string",
  "actualCompleteTime": "string",
  "verifyUserId": 0,
  "verifyTime": "string",
  "verifyResult": 0,
  "status": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|
|createUser|integer(int64)|false|none||none|
|updateUser|integer(int64)|false|none||none|
|description|string|false|none||none|
|remark|string|false|none||none|
|deleted|integer|false|none||逻辑删除标记：0-未删除，1-已删除|
|inspectionId|integer(int64)|true|none||巡检任务ID|
|recordNo|string|true|none||整改单号|
|issueType|string|true|none||问题类型(设施损坏/安全隐患/环境问题/其他)|
|issueDesc|string|true|none||问题描述|
|issueImages|string|false|none||问题图片(多张逗号分隔)|
|issueLocation|string|false|none||问题位置|
|severity|integer|true|none||严重程度：1.轻微 2.一般 3.严重 4.紧急|
|handleUserId|integer(int64)|false|none||整改负责人ID(关联sys_user_info)|
|handleDesc|string|false|none||整改说明|
|handleImages|string|false|none||整改后图片(多张逗号分隔)|
|planDeadline|string|false|none||计划整改期限|
|actualCompleteTime|string|false|none||实际完成时间|
|verifyUserId|integer(int64)|false|none||验收人ID|
|verifyTime|string|false|none||验收时间|
|verifyResult|integer|false|none||验收结果：1.合格 2.不合格|
|status|integer|true|none||状态：0.待整改 1.整改中 2.待验收 3.已完成 4.已驳回|

<h2 id="tocS_RListInspectionRecord">RListInspectionRecord</h2>

<a id="schemarlistinspectionrecord"></a>
<a id="schema_RListInspectionRecord"></a>
<a id="tocSrlistinspectionrecord"></a>
<a id="tocsrlistinspectionrecord"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "createTime": "string",
      "updateTime": "string",
      "createUser": 0,
      "updateUser": 0,
      "description": "string",
      "remark": "string",
      "deleted": 0,
      "inspectionId": 0,
      "recordNo": "string",
      "issueType": "string",
      "issueDesc": "string",
      "issueImages": "string",
      "issueLocation": "string",
      "severity": 0,
      "handleUserId": 0,
      "handleDesc": "string",
      "handleImages": "string",
      "planDeadline": "string",
      "actualCompleteTime": "string",
      "verifyUserId": 0,
      "verifyTime": "string",
      "verifyResult": 0,
      "status": 0
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[[InspectionRecord](#schemainspectionrecord)]|false|none||none|

<h2 id="tocS_RInspectionRecord">RInspectionRecord</h2>

<a id="schemarinspectionrecord"></a>
<a id="schema_RInspectionRecord"></a>
<a id="tocSrinspectionrecord"></a>
<a id="tocsrinspectionrecord"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "createTime": "string",
    "updateTime": "string",
    "createUser": 0,
    "updateUser": 0,
    "description": "string",
    "remark": "string",
    "deleted": 0,
    "inspectionId": 0,
    "recordNo": "string",
    "issueType": "string",
    "issueDesc": "string",
    "issueImages": "string",
    "issueLocation": "string",
    "severity": 0,
    "handleUserId": 0,
    "handleDesc": "string",
    "handleImages": "string",
    "planDeadline": "string",
    "actualCompleteTime": "string",
    "verifyUserId": 0,
    "verifyTime": "string",
    "verifyResult": 0,
    "status": 0
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|[InspectionRecord](#schemainspectionrecord)|false|none||none|

