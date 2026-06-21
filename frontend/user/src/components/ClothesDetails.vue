<template>
  <div class="clothing-container"> <!-- 注意这里更换了外部容器的类名 -->
    <el-row>
      <el-col :span="12" class="image-col">
        <div class="image-wrapper">
          <img
              :src="url + clothing.image"
              alt="Clothing Image"
              class="clothing-image"
          />
        </div>
      </el-col>
      <el-col :span="12" class="detail-col">
        <div class="detail-wrapper">
          <h1 class="clothing-name">{{ clothing.clothName }}</h1>
          <p class="clothing-style">风格：<strong>{{ clothing.style }}</strong></p>
          <p class="clothing-category">类别：<strong>{{ clothing.typeName }}</strong></p>
          <el-form-item label="尺码:" prop="size" class="clothing-size">
            <el-radio-group v-model="selectedSize">
              <el-radio-button
                  v-for="size in clothing.sizeList"
                  :label="size"
                  :key="size.id"
              >
                {{ size.sizeName }}
              </el-radio-button>
            </el-radio-group>
          </el-form-item>
          <h2 class="clothing-price">￥{{ clothing.price }}</h2>
          <el-button type="primary" @click="addToCart">加入购物车</el-button>
        </div>
      </el-col>
    </el-row>
  </div>
</template>



<script setup>
import {onMounted, ref} from 'vue';
import {useRoute} from "vue-router";
import axios from "../axios";
const route = useRoute()
//图片路径
const url = "/wardrobe_back/images/"
//添加购物车时需要选择的尺码参数
let clothing = ref({})
const selectedSize = ref('');
onMounted(() => {
  getClothesInfo()
})
const getClothesInfo = () =>{
  axios.get('/wardrobe_back/clothDetails',{
    params:{
      clothId : route.query.clothId
    }
  }).then(res => {
    clothing.value = res.data
  }).catch(error =>{
    console.log(error)
  })
}

const userId = ref(JSON.parse(localStorage.getItem('user'))?.id)
//添加购物车
const addToCart = () => {
  if (!selectedSize.value) {
    alert('请选择尺码！');
    return;
  }
  if(userId.value == null){
    alert("请先登录！")
  }else{
    axios.post('/wardrobe_back/addToCart',{
      clothId : clothing.value.id,
      clothSize : selectedSize.value.sizeName,
      userId : userId.value
    }).then(res => {
      alert(res.data)
    }).catch(error => {
      console.log(error)
    })
  }
}
</script>


<style scoped>
.clothing-container { /* 外部容器的样式 */
  width: 100%; /* 根据需要调整宽度 */
  max-width: 800px; /* 可选，设置最大宽度以适应不同屏幕尺寸 */
  margin: 0 auto; /* 水平居中 */
  padding: 50px; /* 可选，增加内边距 */
}

.image-col, .detail-col { /* 图片列和详情列的通用样式 */
  display: flex;
  flex-direction: column; /* 垂直方向排列子元素 */
  justify-content: center; /* 垂直居中 */
}

.image-wrapper { /* 图片容器的样式 */
  text-align: center; /* 水平居中图片 */
}

.clothing-image { /* 图片的样式 */
  max-width: 80%; /* 图片最大宽度为列宽 */
  height: auto; /* 保持图片比例 */
  display: block; /* 消除图片底部的间隙 */
  object-fit: cover; /* 可选，保持图片覆盖整个容器，同时保持比例 */
}
h2{
  color: red;
}
h1,p{
  color: dimgray;
}

</style>