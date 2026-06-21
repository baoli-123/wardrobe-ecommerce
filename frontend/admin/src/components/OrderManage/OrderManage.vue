<template>
  <div>
    <el-card>
      <el-input style="width:200px" v-model="searchUserName" placeholder="请输入用户姓名" class="input-with-select">
      </el-input> &nbsp;
      <el-select v-model="orderStatus" placeholder="请选择订单状态" >
        <el-option
            v-for="(status,index) in statusList" :key="index"
            :label="status.name"
            :value="status.id">
        </el-option>
      </el-select>  &nbsp;
      <el-button type="primary" :icon="Search" @click="getOrdersByParams">搜索</el-button>
      <el-button type="info" plain @click="reset">重置</el-button>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <el-table
          :data="currentData"
          border
          style="width: 100%;margin-top:30px"
          height="500"
          sticky
          :empty-text="emptyText"
          highlight-current-row
      >
        <el-table-column prop="id" label="编号" width="100" align="center"/>
        <el-table-column prop="clothesDetails" label="商品详情" width="380" align="center"/>
        <el-table-column prop="address" label="收货信息" width="250" align="center">
          <template v-slot:default="scope">
            {{scope.row.user.userName}} <br> {{scope.row.user.phone}} <br> {{scope.row.user.address}}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="订单状态" width="150" align="center">
          <template #default="scope">
            <span>{{ getStatusText(scope.row.status) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="user.userName" label="下单用户" width="150" align="center"/>
        <el-table-column prop="time" label="下单时间" width="200" align="center">
          <template v-slot:default="scope">
            {{ scope.row.time }}
          </template>
        </el-table-column>

        <el-table-column label="操作" width="200" align="center">
          <template #default="scope">
            <el-button type="success" @click="delivery(scope.row)" v-if="scope.row.status === 1">发货</el-button>
<!--            <el-button type="danger"   v-if="scope.row.status === 0">未支付</el-button>
            <el-button type="danger"   v-if="scope.row.status === 3">已收货</el-button>-->
<!--            <el-button type="danger"  @click="delOrderData(scope.row)" v-if="scope.row.status === 0">删除</el-button>
            <el-button type="danger"  @click="delOrderData(scope.row)" v-if="scope.row.status === 3">删除</el-button>-->
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
    </el-card>
  </div>
</template>

<script setup>
import {computed, onMounted, reactive, ref} from "vue";
import axios from "../../axios";

/*import {ElMessage, ElMessageBox} from "element-plus";*/
import {Search} from "@element-plus/icons-vue";

const emptyText = ref('无数据')
// Dom 挂载之后
onMounted(() => {
  getOrderDataList();
})
// 订单数据
let tableData = ref([]);

const currentPage = ref(1); // 当前页码
const pageSize = ref(5); // 每页显示的数量

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

//定义getStatusText函数（用于显示状态）
function getStatusText(status) {
  if (status === 0) {
    return '未支付';
  } else if (status === 1) {
    return '未发货';
  } else if (status === 2) {
    return '已发货';
  }else {
    return '已收货'
  }
}

// 定义 formatDateTime 函数(用于显示时间)
/*function formatDateTime(value) {
  // 这里假设 value 是一个时间戳，您需要根据实际情况进行解析和格式化
  const date = new Date(value);
  return date.toDateString();
}*/

// 获取服装列表
const getOrderDataList = () => {
  axios.get('/wardrobe_back/allOrderData').then(res => {
    console.log(res);
    tableData.value = res.data;
    total.value = res.data.length;
  }).catch(error => {
    console.log(error)
  })
}

//多条件查询
const orderStatus = ref('')
const statusList = reactive([
  { id: 0, name: '未支付' },
  { id: 1, name: '未发货' },
  { id: 2, name: '已发货' },
  { id: 3, name: '已收货' },
  // 可以继续添加更多对象
]);
const searchUserName = ref('')

//多条件查询
//根据名称、类别、风格进行多条件查询服装
const getOrdersByParams = () => {
  axios.get('/wardrobe_back/searchOrder',{
    params:{
      userName : searchUserName.value,
      status : orderStatus.value
    }
  }).then(res => {
    console.log(res.data);
    tableData.value = res.data;
    total.value = res.data.length;
  }).catch(error => {
    console.log(error)
  })
}

const reset = () => {
  orderStatus.value = ''
  searchUserName.value = ''
}
//发货
const delivery = (value) => {
  axios.post('/wardrobe_back/deliveryOrder', {
    id : value.id
  }).then(res => {
    alert(res.data)
    getOrderDataList()
  })
}

//删除订单
/*const delOrderData = (value) => {
  ElMessageBox.confirm(
      '确定要删除该订单吗?',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    axios.post('/wardrobe_back/delClothes', {
      id : value.id
    }).then(res => {
      alert(res.data)
      getOrderDataList()
    }).catch(error => {
      console.log(error)
    })
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '取消删除',
    })
  })
}*/
</script>

<style scoped>
.el-table {
  font-size: 20px;
}
</style>