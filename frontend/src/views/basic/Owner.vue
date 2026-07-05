<template>
  <div class="owner-container">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :model="searchForm" inline>
        <el-form-item label="业主姓名">
          <el-input v-model="searchForm.ownerName" placeholder="请输入业主姓名" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="searchForm.phoneNumber" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="所属小区">
          <el-select v-model="searchForm.communityId" placeholder="请选择小区">
            <el-option label="全部" value="" />
            <el-option 
              v-for="item in communityList" 
              :key="item.id" 
              :label="item.communityName" 
              :value="item.id" 
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openDialog('add')" class="add-btn">
        <el-icon>新增业主</el-icon>
        
      </el-button>
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
        <el-table-column prop="ownerName" label="业主姓名"/>
        <el-table-column prop="phoneNumber" label="联系电话"/>
        <el-table-column prop="idCardNumber" label="身份证号"/>
        <el-table-column prop="communityName" label="所属小区"/>
        <el-table-column prop="buildingName" label="所属楼栋"/>
        <el-table-column prop="houseNumber" label="房号"/>
        <el-table-column prop="relation" label="与房屋关系" width="120"/>
        <el-table-column prop="moveInDate" label="入住日期" width="120"/>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '正常' : '已迁出' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"/>
        <el-table-column label="操作" width="200" align="center">
          <template #default="scope">
            <el-button 
              size="small" 
              @click="openDialog('edit', scope.row)"
            >
              <el-icon>编辑</el-icon>
              
            </el-button>
            <el-button 
              size="small" 
              type="danger"
              @click="handleDelete(scope.row.id)"
            >
              <el-icon>删除</el-icon>
              
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
        <el-form-item label="联系电话" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="form.idCardNumber" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="所属小区">
          <el-select 
            v-model="form.communityId" 
            placeholder="请选择小区"
            @change="handleCommunityChange"
          >
            <el-option 
              v-for="item in communityList" 
              :key="item.id" 
              :label="item.communityName" 
              :value="item.id" 
            />
          </el-select>
        </el-form-item>
        <el-form-item label="所属楼栋" prop="buildingId">
          <el-select 
            v-model="form.buildingId" 
            placeholder="请选择楼栋"
            @change="handleBuildingChange"
          >
            <el-option 
              v-for="item in filteredBuildings" 
              :key="item.id" 
              :label="item.buildingName" 
              :value="item.id" 
            />
          </el-select>
        </el-form-item>
        <el-form-item label="房号" prop="houseId">
          <el-select v-model="form.houseId" placeholder="请选择房号">
            <el-option 
              v-for="item in filteredHouses" 
              :key="item.id" 
              :label="item.houseNumber" 
              :value="item.id" 
            />
          </el-select>
        </el-form-item>
        <el-form-item label="与房屋关系">
          <el-select v-model="form.relation" placeholder="请选择关系">
            <el-option label="业主" :value="'业主'" />
            <el-option label="配偶" :value="'配偶'" />
            <el-option label="子女" :value="'子女'" />
            <el-option label="父母" :value="'父母'" />
            <el-option label="租户" :value="'租户'" />
            <el-option label="其他" :value="'其他'" />
          </el-select>
        </el-form-item>
        <el-form-item label="入住日期">
          <el-date-picker v-model="form.moveInDate" type="date" placeholder="请选择入住日期" />
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
import {
  getOwnerList,
  createOwner,
  updateOwner,
  deleteOwner
} from '@/api/owner'
import { getCommunityList } from '@/api/community'
import { getBuildingList } from '@/api/building'
import { getHouseList } from '@/api/house'

// 小区列表
const communityList = ref([])
// 楼栋列表
const buildingList = ref([])
// 房屋列表
const houseList = ref([])

// 搜索表单
const searchForm = reactive({
  ownerName: '',
  phoneNumber: '',
  communityId: ''
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
  ownerName: '',
  phoneNumber: '',
  idCardNumber: '',
  communityId: '',
  buildingId: '',
  houseId: '',
  relation: '业主',
  moveInDate: '',
  status: 1,
  remark: ''
})

// 表单验证规则
const rules = {
  ownerName: [
    { required: true, message: '请输入业主姓名', trigger: 'blur' }
  ],
  phoneNumber: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  communityId: [
    { required: true, message: '请选择所属小区', trigger: 'blur' }
  ],
  buildingId: [
    { required: true, message: '请选择所属楼栋', trigger: 'blur' }
  ],
  houseId: [
    { required: true, message: '请选择房号', trigger: 'blur' }
  ]
}

// 弹窗标题
const dialogTitle = computed(() => {
  return dialogType.value === 'add' ? '新增业主' : '编辑业主'
})

// 根据小区过滤楼栋
const filteredBuildings = computed(() => {
  if (!form.communityId) return []
  return buildingList.value.filter(b => b.communityId === form.communityId)
})

// 根据楼栋过滤房屋
const filteredHouses = computed(() => {
  if (!form.buildingId) return []
  return houseList.value.filter(h => h.buildingId === form.buildingId)
})

// 加载小区列表
const loadCommunityList = async () => {
  try {
    const res = await getCommunityList()
    if (Array.isArray(res)) {
      communityList.value = res
    } else {
      communityList.value = [
        { id: 1, communityName: '阳光小区' },
        { id: 2, communityName: '幸福花园' },
        { id: 3, communityName: '和谐家园' }
      ]
    }
  } catch (error) {
    communityList.value = [
      { id: 1, communityName: '阳光小区' },
      { id: 2, communityName: '幸福花园' },
      { id: 3, communityName: '和谐家园' }
    ]
  }
}

// 加载楼栋列表
const loadBuildingList = async () => {
  try {
    const res = await getBuildingList()
    if (Array.isArray(res)) {
      buildingList.value = res
    } else {
      buildingList.value = [
        { id: 1, communityId: 1, buildingName: '1号楼' },
        { id: 2, communityId: 1, buildingName: '2号楼' },
        { id: 3, communityId: 2, buildingName: 'A栋' }
      ]
    }
  } catch (error) {
    buildingList.value = [
      { id: 1, communityId: 1, buildingName: '1号楼' },
      { id: 2, communityId: 1, buildingName: '2号楼' },
      { id: 3, communityId: 2, buildingName: 'A栋' }
    ]
  }
}

// 加载房屋列表
const loadHouseList = async () => {
  try {
    const res = await getHouseList()
    if (Array.isArray(res)) {
      houseList.value = res
    } else {
      houseList.value = [
        { id: 1, communityId: 1, buildingId: 1, houseNumber: '101' },
        { id: 2, communityId: 1, buildingId: 1, houseNumber: '102' },
        { id: 3, communityId: 1, buildingId: 1, houseNumber: '201' },
        { id: 4, communityId: 2, buildingId: 3, houseNumber: '301' }
      ]
    }
  } catch (error) {
    houseList.value = [
      { id: 1, communityId: 1, buildingId: 1, houseNumber: '101' },
      { id: 2, communityId: 1, buildingId: 1, houseNumber: '102' },
      { id: 3, communityId: 1, buildingId: 1, houseNumber: '201' },
      { id: 4, communityId: 2, buildingId: 3, houseNumber: '301' }
    ]
  }
}

// 小区改变时重置楼栋和房屋
const handleCommunityChange = () => {
  form.buildingId = ''
  form.houseId = ''
}

// 楼栋改变时重置房屋
const handleBuildingChange = () => {
  form.houseId = ''
}

// 加载业主数据
const loadData = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.currentPage,
      size: pagination.pageSize,
      ...searchForm
    }
    const res = await getOwnerList(params)
    // 后端返回的是数组，直接使用
    if (Array.isArray(res) && res.length > 0) {
      // 补充小区、楼栋、房屋信息
      tableData.value = res.map(item => {
        const house = houseList.value.find(h => h.id === item.houseId)
        const building = house ? buildingList.value.find(b => b.id === house.buildingId) : null
        const community = building ? communityList.value.find(c => c.id === building.communityId) : null
        return {
          ...item,
          communityName: community ? community.communityName : '未知小区',
          buildingName: building ? building.buildingName : '未知楼栋',
          houseNumber: house ? house.houseNumber : '未知房号'
        }
      })
      pagination.total = res.length
    } else {
      // 使用模拟数据
      tableData.value = [
        { id: 1, ownerName: '张三', phoneNumber: '13800138001', idCardNumber: '110101199001011234', communityId: 1, buildingId: 1, houseId: 1, communityName: '阳光小区', buildingName: '1号楼', houseNumber: '101', relation: '业主', moveInDate: '2026-01-01', status: 1, createTime: '2026-01-01T09:00:00' },
        { id: 2, ownerName: '李四', phoneNumber: '13900139002', idCardNumber: '110101199202022345', communityId: 1, buildingId: 1, houseId: 2, communityName: '阳光小区', buildingName: '1号楼', houseNumber: '102', relation: '业主', moveInDate: '2026-01-05', status: 1, createTime: '2026-01-05T10:00:00' },
        { id: 3, ownerName: '王五', phoneNumber: '13700137003', idCardNumber: '110101198803033456', communityId: 2, buildingId: 3, houseId: 4, communityName: '幸福花园', buildingName: 'A栋', houseNumber: '301', relation: '租户', moveInDate: '2026-02-01', status: 1, createTime: '2026-02-01T11:00:00' }
      ]
      pagination.total = tableData.value.length
    }
  } catch (error) {
    console.error('加载业主列表失败:', error)
    // 使用模拟数据
    tableData.value = [
      { id: 1, ownerName: '张三', phoneNumber: '13800138001', idCardNumber: '110101199001011234', communityId: 1, buildingId: 1, houseId: 1, communityName: '阳光小区', buildingName: '1号楼', houseNumber: '101', relation: '业主', moveInDate: '2026-01-01', status: 1, createTime: '2026-01-01T09:00:00' },
      { id: 2, ownerName: '李四', phoneNumber: '13900139002', idCardNumber: '110101199202022345', communityId: 1, buildingId: 1, houseId: 2, communityName: '阳光小区', buildingName: '1号楼', houseNumber: '102', relation: '业主', moveInDate: '2026-01-05', status: 1, createTime: '2026-01-05T10:00:00' },
      { id: 3, ownerName: '王五', phoneNumber: '13700137003', idCardNumber: '110101198803033456', communityId: 2, buildingId: 3, houseId: 4, communityName: '幸福花园', buildingName: 'A栋', houseNumber: '301', relation: '租户', moveInDate: '2026-02-01', status: 1, createTime: '2026-02-01T11:00:00' }
    ]
    pagination.total = tableData.value.length
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  pagination.currentPage = 1
  loadData()
}

// 重置
const handleReset = () => {
  searchForm.ownerName = ''
  searchForm.phoneNumber = ''
  searchForm.communityId = ''
  handleSearch()
}

// 打开弹窗
const openDialog = (type, row = null) => {
  dialogType.value = type
  dialogVisible.value = true
  
  // 重置表单
  if (formRef.value) {
    formRef.value.resetFields()
  }
  
  if (type === 'add') {
    form.id = ''
    form.ownerName = ''
    form.phoneNumber = ''
    form.idCardNumber = ''
    form.communityId = ''
    form.buildingId = ''
    form.houseId = ''
    form.relation = '业主'
    form.moveInDate = ''
    form.status = 1
    form.remark = ''
  } else if (type === 'edit' && row) {
    form.id = row.id
    form.ownerName = row.ownerName
    form.phoneNumber = row.phoneNumber
    form.idCardNumber = row.idCardNumber
    form.communityId = row.communityId
    form.buildingId = row.buildingId
    form.houseId = row.houseId
    form.relation = row.relation
    form.moveInDate = row.moveInDate
    form.status = row.status
    form.remark = row.remark
  }
}

// 关闭弹窗
const handleDialogClose = () => {
  if (formRef.value) {
    formRef.value.resetFields()
  }
}

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
    await ElMessageBox.confirm(
      '确定要删除该业主吗？',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    await deleteOwner(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 分页大小改变
const handleSizeChange = (size) => {
  pagination.pageSize = size
  loadData()
}

// 当前页改变
const handleCurrentChange = (page) => {
  pagination.currentPage = page
  loadData()
}

// 初始化
onMounted(() => {
  loadCommunityList()
  loadBuildingList()
  loadHouseList()
  loadData()
})
</script>

<style scoped>
.owner-container {
  padding: 20px;
}

.search-card {
  margin-bottom: 20px;
}

.add-btn {
  float: right;
}

.table-card {
  min-height: 400px;
}
</style>