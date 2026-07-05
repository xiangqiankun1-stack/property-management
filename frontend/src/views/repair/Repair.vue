<template>
  <div class="repair-container">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="报修编号">
          <el-input v-model="searchForm.repairNo" placeholder="请输入报修编号" />
        </el-form-item>
        <el-form-item label="报修类型">
          <el-select v-model="searchForm.repairType" placeholder="请选择类型">
            <el-option label="全部" value="" />
            <el-option label="水电" value="水电" />
            <el-option label="门窗" value="门窗" />
            <el-option label="家电" value="家电" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态">
            <el-option label="全部" value="" />
            <el-option label="待处理" :value="0" />
            <el-option label="处理中" :value="1" />
            <el-option label="已完成" :value="2" />
            <el-option label="已取消" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openDialog('add')" class="add-btn">新增报修</el-button>
    </el-card>

    <!-- 数据表格 -->
    <el-card class="table-card">
      <el-table 
        :data="tableData" 
        border
        stripe
        :loading="loading"
        style="width: 100%"
      >
        <el-table-column prop="id" label="ID" width="80" align="center"/>
        <el-table-column prop="repairNo" label="报修编号"/>
        <el-table-column prop="repairType" label="报修类型" width="100"/>
        <el-table-column prop="repairContent" label="报修内容"/>
        <el-table-column prop="contactPhone" label="联系电话" width="130"/>
        <el-table-column prop="houseNumber" label="房号" width="120"/>
        <el-table-column prop="expectedTime" label="期望时间" width="160"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"/>
        <el-table-column label="操作" width="200" align="center">
          <template #default="scope">
            <el-button size="small" @click="openDialog('edit', scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
            <el-button v-if="scope.row.status !== 2 && scope.row.status !== 3" size="small" type="success" @click="handleComplete(scope.row.id)">完成</el-button>
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
        <el-form-item label="报修类型" prop="repairType">
          <el-select v-model="form.repairType" placeholder="请选择报修类型">
            <el-option label="水电" value="水电" />
            <el-option label="门窗" value="门窗" />
            <el-option label="家电" value="家电" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="报修内容" prop="repairContent">
          <el-input v-model="form.repairContent" type="textarea" placeholder="请描述问题详情" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="房号" prop="houseId">
          <el-select v-model="form.houseId" placeholder="请选择房号">
            <el-option v-for="item in houseList" :key="item.id" :label="item.houseNumber" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="期望时间">
          <el-date-picker v-model="form.expectedTime" type="datetime" placeholder="请选择期望维修时间" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="待处理" :value="0" />
            <el-option label="处理中" :value="1" />
            <el-option label="已完成" :value="2" />
            <el-option label="已取消" :value="3" />
          </el-select>
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
import { getRepairList, createRepair, updateRepair, deleteRepair } from '@/api/repair'
import { getHouseList } from '@/api/house'

// 房屋列表
const houseList = ref([])

// 搜索表单
const searchForm = reactive({
  repairNo: '',
  repairType: '',
  status: ''
})

// 表格数据
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
  repairType: '',
  repairContent: '',
  contactPhone: '',
  houseId: '',
  expectedTime: '',
  status: 0
})

// 表单验证规则
const rules = {
  repairType: [{ required: true, message: '请选择报修类型', trigger: 'blur' }],
  repairContent: [{ required: true, message: '请描述问题详情', trigger: 'blur' }],
  contactPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
  houseId: [{ required: true, message: '请选择房号', trigger: 'blur' }]
}

// 弹窗标题
const dialogTitle = computed(() => dialogType.value === 'add' ? '新增报修' : '编辑报修')

// 获取状态类型
const getStatusType = (status) => {
  const types = {
    0: 'warning',
    1: 'primary',
    2: 'success',
    3: 'danger'
  }
  return types[status] || 'info'
}

// 获取状态文本
const getStatusText = (status) => {
  const texts = {
    0: '待处理',
    1: '处理中',
    2: '已完成',
    3: '已取消'
  }
  return texts[status] || '未知'
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

// 加载报修数据
const loadData = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.currentPage,
      size: pagination.pageSize,
      ...searchForm
    }
    const res = await getRepairList(params)
    
    if (Array.isArray(res)) {
      if (res.length > 0) {
        // 补充房号信息
        tableData.value = res.map(item => {
          const house = houseList.value.find(h => h.id === item.houseId)
          return {
            ...item,
            houseNumber: house ? house.houseNumber : '未知房号'
          }
        })
        pagination.total = res.length
      } else {
        // 使用模拟数据
        tableData.value = [
          { id: 1, repairNo: 'RP20260201001', repairType: '水电', repairContent: '厨房水龙头漏水，请尽快维修', contactPhone: '13900000001', houseId: 1, houseNumber: '1号楼101', expectedTime: '2026-02-02T09:00:00', status: 3, createTime: '2026-02-01T08:30:00' },
          { id: 2, repairNo: 'RP20260203001', repairType: '门窗', repairContent: '卧室窗户密封条老化，通风时有异响', contactPhone: '13900000002', houseId: 2, houseNumber: '1号楼102', expectedTime: '2026-02-05T10:00:00', status: 1, createTime: '2026-02-03T14:00:00' }
        ]
        pagination.total = tableData.value.length
      }
    } else {
      // 使用模拟数据
      tableData.value = [
        { id: 1, repairNo: 'RP20260201001', repairType: '水电', repairContent: '厨房水龙头漏水，请尽快维修', contactPhone: '13900000001', houseId: 1, houseNumber: '1号楼101', expectedTime: '2026-02-02T09:00:00', status: 3, createTime: '2026-02-01T08:30:00' },
        { id: 2, repairNo: 'RP20260203001', repairType: '门窗', repairContent: '卧室窗户密封条老化，通风时有异响', contactPhone: '13900000002', houseId: 2, houseNumber: '1号楼102', expectedTime: '2026-02-05T10:00:00', status: 1, createTime: '2026-02-03T14:00:00' }
      ]
      pagination.total = tableData.value.length
    }
  } catch (error) {
    console.error('加载报修列表失败:', error)
    // 使用模拟数据
    tableData.value = [
      { id: 1, repairNo: 'RP20260201001', repairType: '水电', repairContent: '厨房水龙头漏水，请尽快维修', contactPhone: '13900000001', houseId: 1, houseNumber: '1号楼101', expectedTime: '2026-02-02T09:00:00', status: 3, createTime: '2026-02-01T08:30:00' },
      { id: 2, repairNo: 'RP20260203001', repairType: '门窗', repairContent: '卧室窗户密封条老化，通风时有异响', contactPhone: '13900000002', houseId: 2, houseNumber: '1号楼102', expectedTime: '2026-02-05T10:00:00', status: 1, createTime: '2026-02-03T14:00:00' }
    ]
    pagination.total = tableData.value.length
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => { pagination.currentPage = 1; loadData() }

// 重置
const handleReset = () => {
  searchForm.repairNo = ''
  searchForm.repairType = ''
  searchForm.status = ''
  handleSearch()
}

// 打开弹窗
const openDialog = (type, row = null) => {
  dialogType.value = type
  dialogVisible.value = true
  if (formRef.value) formRef.value.resetFields()
  
  if (type === 'add') {
    Object.assign(form, {
      id: '',
      repairType: '',
      repairContent: '',
      contactPhone: '',
      houseId: '',
      expectedTime: '',
      status: 0
    })
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
      await createRepair(form)
      ElMessage.success('新增成功')
    } else {
      await updateRepair(form.id, form)
      ElMessage.success('修改成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error(dialogType.value === 'add' ? '新增失败' : '修改失败')
  }
}

// 完成报修
const handleComplete = async (id) => {
  try {
    await updateRepair(id, { status: 2 })
    ElMessage.success('报修已完成')
    loadData()
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

// 删除
const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该报修记录吗？', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await deleteRepair(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') ElMessage.error('删除失败')
  }
}

// 分页大小改变
const handleSizeChange = (size) => { pagination.pageSize = size; loadData() }

// 当前页改变
const handleCurrentChange = (page) => { pagination.currentPage = page; loadData() }

// 初始化
onMounted(() => {
  loadHouseList()
  loadData()
})
</script>

<style scoped>
.repair-container { padding: 20px; }

.search-card { 
  margin-bottom: 20px; 
  position: relative;
}

.add-btn {
  position: absolute;
  right: 20px;
  top: 15px;
}

.table-card { min-height: 400px; }
</style>