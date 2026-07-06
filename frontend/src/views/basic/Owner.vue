<template>
  <div class="owner-container">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="业主姓名">
          <el-input v-model="searchForm.ownerName" placeholder="请输入业主姓名" clearable />
        </el-form-item>
       <!--  <el-form-item label="联系电话">
          <el-input v-model="searchForm.phoneNumber" placeholder="请输入联系电话" clearable />
        </el-form-item> -->
        <el-form-item label="与房屋关系">
        <el-select 
          v-model="searchForm.relationship" 
          placeholder="请选择关系" 
          clearable
          style="width: 120px"
        >
          <el-option label="全部" value="" />
          <el-option label="业主" value="业主" />
          <el-option label="配偶" value="配偶" />
          <el-option label="子女" value="子女" />
          <el-option label="父母" value="父母" /> 
          <el-option label="租户" value="租户" />
        </el-select>
      </el-form-item>

      <el-form-item label="状态">
        <el-select 
          v-model="searchForm.status" 
          placeholder="请选择状态" 
          clearable
          style="width: 120px"
        >
          <el-option label="全部" value="" />
          <el-option label="正常" :value="1" />
          <el-option label="已迁出" :value="0" />
        </el-select>
      </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
           
            搜索
          </el-button>
          <el-button @click="handleReset">
           
            重置
          </el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openDialog('add')" class="add-btn">
       
        新增业主
      </el-button>
    </el-card>

    <!-- 数据表格 -->
    <el-card class="table-card">
      <div class="table-header">
        <div class="summary-info">
          <span>共 <strong>{{ pagination.total }}</strong> 条记录</span>
          <span class="divider">|</span>
          <span>正常: <strong>{{ normalCount }}</strong> 条</span>
          <span class="divider">|</span>
          <span>已迁出: <strong>{{ movedOutCount }}</strong> 条</span>
        </div>
      </div>
      
      <el-table 
        :data="displayData" 
        border
        stripe
        :loading="loading"
        style="width: 100%"
      >
        <el-table-column prop="id" label="ID" width="80" align="center"/>
        <el-table-column prop="ownerName" label="业主姓名"/>
        <el-table-column prop="gender" label="性别" width="80" align="center">
          <template #default="scope">
            {{ scope.row.gender === 1 ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="phoneNumber" label="联系电话"/>
        <el-table-column prop="idCard" label="身份证号"/>
        <el-table-column prop="houseNumber" label="房号"/>
        <el-table-column prop="relationship" label="与房屋关系" width="120"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '正常' : '已迁出' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"/>
        <el-table-column label="操作" width="165" align="center">
          <template #default="scope">
            <el-button size="small" @click="openDialog('edit', scope.row)">
             
              编辑
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">
             
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="pagination.currentPage"
        v-model:page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 20px; text-align: right"
      />
    </el-card>

    <!-- 弹窗表单 -->
    <el-dialog 
      v-model="dialogVisible" 
      :title="dialogTitle"
      width="600px"
      @close="handleDialogClose"
    >
      <el-form 
        :model="form" 
        ref="formRef"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="业主姓名" prop="ownerName">
          <el-input v-model="form.ownerName" placeholder="请输入业主姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="男" :value="1" />
            <el-option label="女" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="联系电话" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="form.idCard" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="房号" prop="houseId">
          <el-select v-model="form.houseId" placeholder="请选择房号">
            <el-option v-for="item in filteredHouses" :key="item.id" :label="item.houseNumber" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="与房屋关系">
          <el-select v-model="form.relationship" placeholder="请选择关系">
            <el-option label="业主" value="业主" />
            <el-option label="配偶" value="配偶" />
            <el-option label="子女" value="子女" />
            <el-option label="父母" value="父母" />
            <el-option label="租户" value="租户" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, RefreshLeft, Plus, Edit, Delete } from '@element-plus/icons-vue'
import { getOwnerList, createOwner, updateOwner, deleteOwner } from '@/api/owner'
import { getHouseList } from '@/api/house'

// 房屋列表
const houseList = ref([])

// 搜索表单
const searchForm = reactive({
  ownerName: '',
  phoneNumber: '',
  relationship: '',
  status: ''
})

// 表格数据
const allData = ref([])
const tableData = ref([])
const loading = ref(false)

// 分页
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 弹窗
const dialogVisible = ref(false)
const dialogType = ref('add')
const formRef = ref(null)
const form = reactive({
  id: '',
  ownerName: '',
  gender: 1,
  phoneNumber: '',
  idCard: '',
  houseId: '',
  relationship: '业主',
  status: 1,
  remark: ''
})

// 表单验证规则
const rules = {
  ownerName: [{ required: true, message: '请输入业主姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'blur' }],
  phoneNumber: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
  houseId: [{ required: true, message: '请选择房号', trigger: 'blur' }]
}

// 弹窗标题
const dialogTitle = computed(() => dialogType.value === 'add' ? '新增业主' : '编辑业主')

// 根据楼栋过滤房屋
const filteredHouses = computed(() => houseList.value)

// 计算统计信息
const normalCount = computed(() => {
  return tableData.value.filter(item => item.status === 1).length
})

const movedOutCount = computed(() => {
  return tableData.value.filter(item => item.status === 0).length
})

// 当前页显示的数据
const displayData = computed(() => {
  const start = (pagination.currentPage - 1) * pagination.pageSize
  const end = start + pagination.pageSize
  return tableData.value.slice(start, end)
})

// 前端搜索过滤
const filterData = () => {
  let filtered = [...allData.value]
  
  if (searchForm.ownerName) {
    filtered = filtered.filter(item => 
      item.ownerName.toLowerCase().includes(searchForm.ownerName.toLowerCase())
    )
  }
  
  if (searchForm.phoneNumber) {
    filtered = filtered.filter(item => 
      item.phoneNumber.includes(searchForm.phoneNumber)
    )
  }
  
  if (searchForm.relationship) {
    filtered = filtered.filter(item => 
      item.relationship === searchForm.relationship
    )
  }
  
  if (searchForm.status !== '') {
    filtered = filtered.filter(item => 
      item.status === searchForm.status
    )
  }
  
  return filtered
}

// 更新表格数据
const updateTableData = () => {
  const filtered = filterData()
  tableData.value = filtered.map(item => {
    const house = houseList.value.find(h => h.id === item.houseId)
    return {
      ...item,
      houseNumber: house ? house.houseNumber : '未知房号'
    }
  })
  pagination.total = tableData.value.length
  pagination.currentPage = 1
}

// 加载房屋列表
const loadHouseList = async () => {
  try {
    const res = await getHouseList()
    if (Array.isArray(res)) {
      houseList.value = res
    } else {
      houseList.value = [
        { id: 1, houseNumber: '1号楼101' },
        { id: 2, houseNumber: '1号楼102' },
        { id: 3, houseNumber: '2号楼201' },
        { id: 4, houseNumber: 'A栋301' }
      ]
    }
  } catch (error) {
    houseList.value = [
      { id: 1, houseNumber: '1号楼101' },
      { id: 2, houseNumber: '1号楼102' },
      { id: 3, houseNumber: '2号楼201' },
      { id: 4, houseNumber: 'A栋301' }
    ]
  }
}

// 加载业主数据
const loadData = async () => {
  loading.value = true
  try {
    const res = await getOwnerList()
    
    if (Array.isArray(res) && res.length > 0) {
      allData.value = res
    } else {
      allData.value = [
        { id: 1, ownerName: '刘建国', gender: 1, phoneNumber: '13900000001', idCard: '310101198001011234', houseId: 1, relationship: '业主', status: 1, createTime: '2026-01-02T10:00:00' },
        { id: 2, ownerName: '陈美丽', gender: 2, phoneNumber: '13900000002', idCard: '310101198505052345', houseId: 2, relationship: '业主', status: 1, createTime: '2026-01-02T10:10:00' },
        { id: 3, ownerName: '赵小龙', gender: 1, phoneNumber: '13900000003', idCard: '310101199212123456', houseId: 3, relationship: '租户', status: 1, createTime: '2026-01-02T10:20:00' },
        { id: 4, ownerName: '王大爷', gender: 1, phoneNumber: '13900000004', idCard: '310101195003034567', houseId: 4, relationship: '业主', status: 0, createTime: '2026-01-02T10:30:00' }
      ]
    }
    
    updateTableData()
  } catch (error) {
    console.error('加载业主列表失败:', error)
    allData.value = [
      { id: 1, ownerName: '刘建国', gender: 1, phoneNumber: '13900000001', idCard: '310101198001011234', houseId: 1, relationship: '业主', status: 1, createTime: '2026-01-02T10:00:00' },
      { id: 2, ownerName: '陈美丽', gender: 2, phoneNumber: '13900000002', idCard: '310101198505052345', houseId: 2, relationship: '业主', status: 1, createTime: '2026-01-02T10:10:00' },
      { id: 3, ownerName: '赵小龙', gender: 1, phoneNumber: '13900000003', idCard: '310101199212123456', houseId: 3, relationship: '租户', status: 1, createTime: '2026-01-02T10:20:00' },
      { id: 4, ownerName: '王大爷', gender: 1, phoneNumber: '13900000004', idCard: '310101195003034567', houseId: 4, relationship: '业主', status: 0, createTime: '2026-01-02T10:30:00' }
    ]
    updateTableData()
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  updateTableData()
}

// 重置
const handleReset = () => {
  searchForm.ownerName = ''
  searchForm.phoneNumber = ''
  searchForm.relationship = ''
  searchForm.status = ''
  updateTableData()
}

// 打开弹窗
const openDialog = (type, row = null) => {
  dialogType.value = type
  dialogVisible.value = true
  if (formRef.value) formRef.value.resetFields()
  
  if (type === 'add') {
    Object.assign(form, { id: '', ownerName: '', gender: 1, phoneNumber: '', idCard: '', houseId: '', relationship: '业主', status: 1, remark: '' })
  } else if (type === 'edit' && row) {
    Object.assign(form, row)
  }
}

// 关闭弹窗
const handleDialogClose = () => { if (formRef.value) formRef.value.resetFields() }

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return
  const valid = await formRef.value.validate()
  if (!valid) return
  
  try {
    if (dialogType.value === 'add') {
      await createOwner(form)
      ElMessage.success('新增成功')
    } else {
      await updateOwner(form.id, form)
      ElMessage.success('修改成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error(dialogType.value === 'add' ? '新增失败' : '修改失败')
  }
}

// 删除
const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该业主吗？', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await deleteOwner(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') ElMessage.error('删除失败')
  }
}

// 分页大小改变
const handleSizeChange = (size) => {
  pagination.pageSize = size
  if (pagination.currentPage > Math.ceil(pagination.total / size)) {
    pagination.currentPage = 1
  }
}

// 当前页改变
const handleCurrentChange = (page) => {
  pagination.currentPage = page
}

// 初始化
onMounted(() => {
  loadHouseList()
  loadData()
})
</script>

<style scoped>
/* 容器样式 */
.owner-container {
  padding: 24px;
  min-height: calc(100vh - 60px);
  background: linear-gradient(180deg, #f0f4f8 0%, #e8ecef 100%);
}

/* 搜索卡片 */
.search-card {
  margin-bottom: 24px;
  position: relative;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
  overflow: hidden;
  background: white;
}

.search-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4px;
  background: linear-gradient(90deg, #E6A23C 0%, #409EFF 50%, #67C23A 100%);
}

/* 新增按钮 */
.add-btn {
  position: absolute;
  right: 24px;
  top: 20px;
  padding: 10px 24px;
  font-size: 14px;
  font-weight: 500;
  border-radius: 8px;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(64, 158, 255, 0.3);
}

/* 表格卡片 */
.table-card {
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
  overflow: hidden;
  background: white;
}

/* 表格头部信息 */
.table-header {
  margin-bottom: 20px;
  padding: 16px 20px;
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  border-radius: 8px;
  border: 1px solid #e2e8f0;
}

.summary-info {
  font-size: 14px;
  color: #64748b;
  font-weight: 500;
}

.summary-info strong {
  color: #E6A23C;
  margin: 0 4px;
  font-size: 16px;
  font-weight: 600;
}

.divider {
  margin: 0 10px;
  color: #cbd5e1;
}

/* 表格样式优化 */
:deep(.el-table) {
  border-radius: 8px;
  overflow: hidden;
}

:deep(.el-table th) {
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  color: #475569;
  font-weight: 600;
  padding: 16px 12px;
  border-bottom: 2px solid #e2e8f0;
  font-size: 14px;
}

:deep(.el-table td) {
  padding: 14px 12px;
  transition: all 0.2s ease;
  color: #374151;
  font-size: 14px;
}

:deep(.el-table tr:hover td) {
  background: #f8fafc;
}

:deep(.el-table--striped .el-table__body tr.el-table__row--striped) {
  background: #fafbfc;
}

/* 操作按钮 */
:deep(.el-table .el-button) {
  margin-right: 12px;
  border-radius: 6px;
  padding: 6px 16px;
  font-size: 12px;
  transition: all 0.2s ease;
  white-space: nowrap;
}

:deep(.el-table .el-button:hover) {
  transform: translateY(-1px);
}

:deep(.el-table .el-button:last-child) {
  margin-right: 0;
}

/* 操作列容器 */
:deep(.el-table .cell) {
  padding: 8px 0;
}

/* 分页样式 */
:deep(.el-pagination) {
  padding: 20px;
  background: #fafbfc;
  border-top: 1px solid #e2e8f0;
}

:deep(.el-pagination .el-pager li) {
  border-radius: 6px;
  margin: 0 4px;
  min-width: 32px;
  height: 32px;
  line-height: 32px;
}

:deep(.el-pagination .el-pager li.active) {
  background: #E6A23C;
  color: white;
}

/* 弹窗样式 */
:deep(.el-dialog) {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
}

/* 弹窗标题样式优化 */
:deep(.el-dialog__header) {
  background: linear-gradient(135deg, #a2a4a5 0%, #e1d1d1 100%);
  padding: 18px 24px;
  display: flex;
  justify-content: center;  /* ✅ 标题居中 */
  align-items: center;
  position: relative;
}

:deep(.el-dialog__title) {
  color: white;
  font-weight: 700;  /* ✅ 字体加粗 */
  font-size: 18px;  /* ✅ 稍微大一点 */
  letter-spacing: 1px;  /* ✅ 字间距 */
}

/* 关闭按钮（X）位置微调 */
:deep(.el-dialog__headerbtn) {
  position: absolute;
  right: 16px;
  top: 50%;
  transform: translateY(-50%);
  color: #333 !important;
  font-size: 20px;
}

:deep(.el-dialog__headerbtn:hover) {
  color: black;
}

:deep(.el-dialog__headerbtn .el-dialog__close) {
  font-weight: 1000;  /* ✅ X 按钮加粗 */
}

:deep(.el-dialog__body) {
  padding: 24px;
}

/* 表单样式 */
:deep(.el-form-item) {
  margin-bottom: 20px;
}

:deep(.el-form-item__label) {
  font-weight: 500;
  color: #475569;
  font-size: 14px;
}

:deep(.el-input__wrapper) {
  border-radius: 8px;
  transition: all 0.2s ease;
  box-shadow: none;
  border-color: #e2e8f0;
}

:deep(.el-input__wrapper:focus-within) {
  box-shadow: 0 0 0 3px rgba(230, 162, 60, 0.1);
  border-color: #E6A23C;
}

:deep(.el-input__inner) {
  font-size: 14px;
  padding: 10px 14px;
}

/* 弹窗按钮 */
:deep(.el-dialog__footer) {
  padding: 16px 24px;
  border-top: 1px solid #e2e8f0;
}

:deep(.el-dialog__footer .el-button) {
  padding: 10px 24px;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
}

/* 选择器样式 */
:deep(.el-select .el-input__wrapper) {
  border-radius: 8px;
}

/* 开关样式 */
:deep(.el-switch) {
  margin-top: 4px;
}

/* 文本域样式 */
:deep(.el-textarea__inner) {
  border-radius: 8px;
  font-size: 14px;
}
</style>