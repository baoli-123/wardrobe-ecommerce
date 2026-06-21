<template>
  <el-menu
      :default-active="activeIndex2"
      class="el-menu-demo"
      mode="horizontal"
      background-color="#4682B4"
      text-color="#fff"
      active-text-color="#ffd04b"
      @select="handleSelect"
      :menu-align-right="true"
  >
    <el-menu-item index="0">
      <img style="width: 180px" src="../assets/logo.png" alt="Element logo"
      />
    </el-menu-item>
    <el-menu-item index="1">
      <router-link to="/" style="text-decoration: none">首页</router-link>
    </el-menu-item>
    <el-sub-menu index="2">
      <template #title>服装类别</template>
      <div v-for="type in typesData.list" :key="type.id">
        <el-menu-item @click="getClothByType(type.typeName)">{{ type.typeName }}</el-menu-item>
      </div>
    </el-sub-menu>
    <el-sub-menu index="3">
      <template #title>服装风格</template>
      <div v-for="(style,index) in styles" :key="index">
        <el-menu-item @click="getClothByStyle(style)">{{ style }}</el-menu-item>
      </div>
    </el-sub-menu>

    <el-menu-item index="7" class="cart" @click="toCart()">购物车</el-menu-item>
    <el-menu-item index="8" class="cart" @click="toOrder()">我的订单</el-menu-item>
    <el-menu-item index="6" class="personal" @click="toPersonal()">个人中心</el-menu-item>
    <el-menu-item index="4" class="reg" v-if="!token">
      <router-link to="/register" style="text-decoration: none" >注册</router-link>
    </el-menu-item>
    <el-menu-item index="5" class="login" v-if="!token">
      <router-link to="/login" style="text-decoration: none">登录</router-link>
    </el-menu-item>
    <el-sub-menu index="5" class="login" v-if="token">
      <template #title>{{ userName }}，欢迎你</template>
      <el-menu-item @click="logout">退出登录</el-menu-item>
    </el-sub-menu>

  </el-menu>
</template>

<script setup>
import { onMounted, reactive, ref} from 'vue'
import axios from "../axios"
import {useRouter} from "vue-router"

//默认高亮显示
const activeIndex2 = ref('1')
const typesData = reactive({
  list: []
})
const styles = ref([])
const router = useRouter()
const userName = ref(JSON.parse(localStorage.getItem('user'))?.userName)
const userId = ref(JSON.parse(localStorage.getItem('user'))?.id)
const token = ref(localStorage.getItem('token'))
const handleSelect = (index) => {
  console.log(index)
}
onMounted(() => {
  allTypes()
  allStyles()
})
const allTypes = () => {
  axios.get("/wardrobe_back/allTypes").then(res => {
    typesData.list = res.data
  })
}
const allStyles = () => {
  axios.get("/wardrobe_back/allStyles").then(res => {
    console.log(styles.value)
    styles.value = res.data
  })
}
const getClothByType = (typeName) =>{
  router.push({
    path:'/',
    query:{
      type : typeName
    }
  })
}
const getClothByStyle = (style) =>{
  router.push({
    path:'/',
    query:{
      style : style
    }
  })
}
//购物车
const toCart = () =>{
  if(userName.value == null){
    alert("请先登录！")
  }else {
    router.push({
      path:'/cart'
    })
  }
}
//订单
const toOrder = () =>{
  if(userName.value == null){
    alert("请先登录！")
  }else {
    router.push({
      path:'/order'
    })
  }
}
//退出登录
const logout = () =>{
    // 清除状态保持
    location.reload()
    window.localStorage.clear()
    router.push({
        path:'/'
    })
}
//个人中心
const toPersonal = () =>{
  router.push({
    path:'/personal',
    query:{
      userId : userId.value
    }
  })
}
</script>

<style scoped>

.el-menu-demo::after {
  content: "";
  display: table;
  clear: both;
}
*{
  margin: 0;
  padding: 0;
}
.menu-wrapper {
  display: flex;
  justify-content: flex-end;
}
</style>
