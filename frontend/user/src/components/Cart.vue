<template>
  <div>
    <el-card style="max-width: 1500px; margin: 0 auto;">
      <template #header>
        <div class="card-header">
          <h2>我的购物车</h2>
        </div>
      </template>
    <el-table :data="cartDataList" style="width: 100%">
      <el-table-column prop="clothes.clothName" label="服装名称" width="150" align="center"/>
      <el-table-column prop="image" label="图片" width="200" align="center">
        <template v-slot:default="scope">
          <el-image style="width: 100px; height: 120px" :src="url + scope.row.clothes.image" />
        </template>
      </el-table-column>
      <el-table-column prop="clothes.style" label="风格" width="150" align="center"/>
      <el-table-column prop="clothSize" label="尺码" width="120" align="center"/>
      <el-table-column prop="clothes.price" label="单价" width="180" align="center">
        <template v-slot:default="scope">
          {{ scope.row.clothes.price }}
        </template>
      </el-table-column>
      <el-table-column label="数量" width="250" align="center">
        <template v-slot:default="scope">
          <el-input-number
              v-model="scope.row.amount"
              :min="1"
              :max="10"
              @change="handleChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column prop="clothes.price" label="小计" width="180" align="center">
        <template v-slot:default="scope">
          {{ (scope.row.clothes.price * scope.row.amount).toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template v-slot:default="scope">
          <el-button type="info" plain @click="delCartData(scope.row)" style="font-size: 20px;">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
      <!-- 显示总价 -->
      <div class="total-price-container">
        总价：{{ totalPrice.toFixed(2) }}
        <el-button type="danger" plain class="price-button" @click="submitOrder" style="font-size: 20px;">结算</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import axios from "../axios";

const userId = ref(JSON.parse(localStorage.getItem('user'))?.id)
const cartDataList = ref([])
const url = "/wardrobe_back/images/"

const totalPrice = computed(() => {
  if (!Array.isArray(cartDataList.value)) return 0
  return cartDataList.value.reduce((sum, item) => {
    return sum + (item.clothes.price * item.amount);
  }, 0);
});

const getCartData = () => {
  axios.get('/wardrobe_back/getCartDataByUser',{
    params:{
      userId : userId.value
    }
  }).then(res => {
    console.log(res.data)
    let data = res.data
    if (typeof data === 'string') {
      try { data = JSON.parse(data) } catch(e) { data = [] }
    }
    cartDataList.value = Array.isArray(data) ? data : []
  }).catch(error => {
    alert(error.message)
  })
}
onMounted(() => {
  getCartData()
})

const handleChange = (value) => {
  axios.post('/wardrobe_back/updateCartData', {
    id : value.id,
    amount : value.amount
  }).then(res => {
    console.log("修改数据" + res.data + "条")
    getCartData()
  })
}

const delCartData = (value) => {
  axios.post('/wardrobe_back/delCartData', {
    id : value.id
  }).then(res => {
    alert(res.data)
    getCartData()
  })
}

const orderList = computed(() => {
  if (!Array.isArray(cartDataList.value)) return []
  return cartDataList.value.map(item => {
    return {
      clothesDetails: `服装编号${item.clothId}，${item.clothes.clothName}${item.clothSize}码（${item.clothes.price.toFixed(2)}）×${item.amount}`,
      price: item.clothes.price.toFixed(2) * item.amount,
      status: '0',
      userId: item.user?.id,
      address: item.user?.address,
    }
  })
})

const submitOrder = () => {
  axios.post('/wardrobe_back/addOrder', orderList.value).then(res => {
    alert(res.data)
    window.location.href = '/#/order';
  })
}
</script>

<style scoped>
.total-price-container {
  margin-left: 850px;
  margin-top: 20px;
  font-weight: bold;
}
.price-button {
  margin-left: 63px;
}
.el-table {
  min-height: 0;
  max-height: 700px;
  overflow-y: auto;
  font-size: 20px;
}
</style>



