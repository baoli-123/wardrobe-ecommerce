<template>
  <div>
    <el-card style="max-width: 1300px; margin: 0 auto;">
      <template #header>
        <div class="card-header">
          <h2>我的订单</h2>
        </div>
      </template>
      <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
      >
        <el-menu-item index="1" @click="isPay(0)">
          <h3>未支付</h3>
        </el-menu-item>
        <el-menu-item index="2" @click="isPay(1)">
          <h3>未发货</h3>
        </el-menu-item>
        <el-menu-item index="3" @click="isPay(2)">
          <h3>已发货</h3>
        </el-menu-item>
        <el-menu-item index="4" @click="isPay(3)">
          <h3>已收货</h3>
        </el-menu-item>
      </el-menu>
      <el-table :data="orderDataList" style="width: 100%">
        <el-table-column prop="id" label="订单编号" width="130" align="center"/>
        <el-table-column prop="clothesDetails" label="商品详情" width="350" align="center"/>
        <el-table-column prop="price" label="订单价格" width="150" align="center">
          <template v-slot:default="scope">
            ￥{{scope.row.price}}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="订单状态" width="140" align="center">
          <template #default="scope">
            <span>{{ getStatusText(scope.row.status) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="time" label="下单时间" width="220" align="center">
          <template v-slot:default="scope">
            {{scope.row.time}}
          </template>
<!--          <template #default="scope">
            <span>{{ formatDateTime(scope.row.time) }}</span>
          </template>-->
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template v-slot:default="scope">
            <el-button type="danger" plain
                       @click="payOrder(scope.row)"
                       v-if="scope.row.status === 0"
                       class="btn"
            >支付
            </el-button>
            <el-button type="info" plain @click="delOrderData(scope.row)" v-if="scope.row.status === 0" class="btn">删除</el-button>
            <el-button type="info" plain @click="delOrderData(scope.row)" v-if="scope.row.status === 1" class="btn">删除</el-button>
            <el-button type="success" plain @click="receive(scope.row)" v-if="scope.row.status === 2" class="btn">收货</el-button>
            <el-button type="info" plain @click="delOrderData(scope.row)" v-if="scope.row.status === 3" class="btn">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import axios from "../axios";

const activeIndex = ref('1');

const userId = ref(JSON.parse(localStorage.getItem('user'))?.id)

const orderDataList = ref([])

//定义getStatusText函数（用于显示状态）
function getStatusText(status) {
  if (status === 0) {
    return '未支付';
  } else if (status === 1) {
    return '未发货';
  }else if (status === 2) {
    return '已发货';
  }else {
    return '已收货';
  }
}

// 定义 formatDateTime 函数(用于显示时间)
/*function formatDateTime(value) {
  // 这里假设 value 是一个时间戳，您需要根据实际情况进行解析和格式化
  const date = new Date(value);
  return date.toDateString();
}*/

const status = ref(0)
const isPay = (value) => {
  status.value = value
  getOrderData()
}

//获取订单数据
const getOrderData = () => {
  axios.get('/wardrobe_back/getOrderByUser',{
    params:{
      userId : userId.value,
      status : status.value
    }
  }).then(res => {
    console.log(res.data)
    orderDataList.value = res.data
  }).catch(error => {
    alert(error.message)
  })
}
onMounted(() => {
  getOrderData()   // 在组件挂载后获取购物车数据
})

//支付订单
const payOrder = (value) => {
  axios.post('/wardrobe_back/payOrder', {
    id : value.id
  }).then(res => {
    alert(res.data)
    getOrderData()
  })
}
//收货
const receive = (value) => {
  axios.post('/wardrobe_back/receiveOrder', {
    id : value.id
  }).then(res => {
    alert(res.data)
    getOrderData()
  })
}
//删除订单数据
const delOrderData = (value) => {
  axios.post('/wardrobe_back/delOrderData', {
    id : value.id
  }).then(res => {
    alert(res.data)
    getOrderData()
  })
}





</script>

<style scoped>
.total-price-container {
  margin-left: 850px;
  margin-top: 20px; /* 根据需要调整样式 */
  font-weight: bold;
}
.price-button {
  margin-left: 75px; /* 或者使用 padding，根据需要调整 */
}
.el-table {
  min-height: 0; /* 设置最小高度 */
  max-height: 700px; /* 设置最大高度，可以根据需要调整 */
  overflow-y: auto; /* 超出最大高度时显示滚动条 */
  font-size: 20px;
}
.btn{
  font-size: 20px;
}

</style>