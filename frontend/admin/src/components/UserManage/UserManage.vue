<template>
  <div>
    <el-card>
      <el-input style="width:440px" v-model="nameOrPhone" placeholder="请输入用户名或手机号" class="large">
        <template #append>
          <el-button icon="Search" @click="searchUser" />
        </template>
      </el-input> &nbsp;&nbsp;&nbsp;
      <el-button type="primary" @click="addUser">添加用户</el-button>
      <el-table
          :data="currentData"
          border
          style="width: 100%;margin-top:30px"
          height="600"
          sticky
          :empty-text="emptyText"
          highlight-current-row
      >
        <el-table-column prop="id" label="编号" width="150" align="center"/>
        <el-table-column prop="userName" label="用户名" width="180" align="center"/>
        <el-table-column prop="password" label="密码" width="250" align="center"/>
        <el-table-column prop="phone" label="电话" width="280" align="center"/>
        <el-table-column prop="address" label="地址" width="300" align="center"/>
        <el-table-column label="操作" width="300" align="center">
          <template #default="scope">
              <el-button type="primary" :icon="Edit" circle class="btn" @click="updateUser(scope.row)" />
              <el-button type="danger" :icon="Delete" circle class="btn" @click="deleteUser(scope.row)"/>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          style="margin-top:20px"
      >
      </el-pagination>
      <!--子组件弹窗（编辑用户）-->
      <EditUser v-if="editDialog"
                :editDialog="editDialog"
                :editData="editData"
                @ChangeEditDialog="ChangeEditDialog"/>
      <!--子组件弹窗（增加用户）-->
      <AddUser v-if="addDialog"
                :addDialog="addDialog"
                @ChangeAddDialog="ChangeAddDialog"/>
    </el-card>
  </div>
</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import axios from "../../axios";
import {Delete, Edit} from "@element-plus/icons-vue";
import EditUser from './EditUser.vue'
import AddUser from './AddUser.vue'
import {ElMessage, ElMessageBox} from "element-plus";
const emptyText = ref('无数据')
// Dom 挂载之后
onMounted(() => {
  getUserList();
})
// 用户数据
let tableData = ref([]);

const currentPage = ref(1); // 当前页码
const pageSize = ref(10); // 每页显示的数量

// 总数据条数
const total = ref(0)

// 计算当前页需要展示的数据
const currentData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return tableData.value.slice(start, end);
});

// 处理每页显示数量的变化
const handleSizeChange = (newSize) => {
  pageSize.value = newSize;
  currentPage.value = 1; // 当每页数量变化时，通常回到第一页
};

// 处理页码变化
const handleCurrentChange = (newPage) => {
  currentPage.value = newPage;
};

// 获取用户列表
const getUserList = () => {
  axios.get('/wardrobe_back/getAllUser').then(res => {
    console.log(res);
    tableData.value = res.data;
    total.value = res.data.length;
  }).catch(error => {
    console.log(error)
  })
}

//根据用户名称或手机号查询用户
const nameOrPhone = ref('')
const searchUser = () => {
  if(nameOrPhone.value === ''){
    getUserList()
  }else{
    axios.get('/wardrobe_back/searchUser',{
      params:{
        nameOrPhone : nameOrPhone.value
      }
    }).then(res => {
      tableData.value = res.data
      total.value = res.data.length
    }).catch(error => {
      alert(error.message)
    })
  }
}
//添加用户
const addDialog = ref(false)

const addUser = () => {
  addDialog.value = true; // 打开弹窗
}
const ChangeAddDialog = (value) =>{
  addDialog.value = value;
  getUserList()
}

//编辑用户信息
const editDialog = ref(false)
const editData = ref({});
const updateUser = (value) => {
  editDialog.value = true; // 打开弹窗
  editData.value = value; // 设置弹窗的初始数据
}
const ChangeEditDialog = (value) =>{
  editDialog.value = value;
  getUserList()
}

// 删除用户
const deleteUser = (value) => {
  ElMessageBox.confirm(
      '确定要注销该用户账号吗?',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    axios.post('/wardrobe_back/delUser', {
      id : value.id
    }).then(res => {
      alert(res.data)
      getUserList()
    }).catch(error => {
      console.log(error)
    })
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '取消删除',
    })
  })
}
</script>

<style scoped>
.el-table {
  font-size: 20px;
}
.btn{
  border-radius: 50%;
  /* 增大按钮的大小 */
  width: 40px; /* 你可以根据需要调整这个值 */
  height: 40px; /* 你可以根据需要调整这个值，确保宽高相等以保持圆形 */
  /* 增大内部图标的大小（如果需要的话） */
  font-size: 20px;
}
.large{
  font-size: 18px;
}
</style>