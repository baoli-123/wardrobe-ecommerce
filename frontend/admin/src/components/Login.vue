<template>
  <div>
    <el-container>
      <el-header>
        <div style="width:400px">
          <p style="float: left;">
            <el-icon color="#409EFF" :size="50">
              <ElementPlus />
            </el-icon>
          </p>
          <p style="float: left;font-size: 25px; font-weight: bold">
            网上衣橱-后台管理
          </p>
        </div>
      </el-header>
      <el-main>
        <el-card class="login_card">
          <el-form :model="form" :rules="rules" ref="ruleFormRef" label-width="80px">
            <el-form-item label="账号：" prop="userInfo">
              <el-input v-model="form.userInfo" placeholder="请输入用户名或手机号" />
            </el-form-item>
            <el-form-item label="密码：" prop="password">
              <el-input type="password" placeholder="请输入密码" v-model="form.password" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit()">登录</el-button>
              <el-button type="primary" @click="resetForm()">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-main>
      <el-footer>
        <p>联系我们：https://www.itcast.cn/</p>
      </el-footer>
    </el-container>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import {useRouter} from "vue-router";
import axios from "../axios";
import {ElementPlus} from "@element-plus/icons-vue";
const form = reactive({
  userInfo: "",
  password: "",
});
const ruleFormRef = ref();
const rules = reactive({
  userInfo: [{ required: true, message: "账号不能为空", trigger: "blur" }],
  password: [{ required: true, message: "密码不能为空", trigger: "blur" }]
});
const router = useRouter()
const onSubmit = () => {
  console
  if (!ruleFormRef.value) return;
  ruleFormRef.value.validate(async (valid,fields) => {
    if (valid) {
      axios.get('/wardrobe_back/login',{
        params:{
          userInfo : form.userInfo,
          password : form.password,
          isAdminLogin : true
        }
      }).then(res => {
        const type = typeof res.data
        if(res.data != null){
          if(type === 'object'){
            alert(res.data.userName + "登录成功！")
            localStorage.setItem('token',JSON.stringify(res.data))
            if(res.data.token != null){
              localStorage.setItem('user',JSON.stringify(res.data)) //存储用户信息
            }
            router.push({path:'/home'})
          }else {
            alert(res.data)
          }
        }else {
          alert("用户名或密码错误！")
        }
      }).catch(error => {
        console.log(error)
      })
    } else {
      console.log('表单校验未通过!', fields)
    }
  });
};
const resetForm = () => {
  if (!ruleFormRef.value) return;
  ruleFormRef.value.resetFields();
};
</script>

<style scoped>
.el-container {
  height: 800px;
}

.el-header {
  height: 10%;
}

.el-main {
  height: 80%;
  background-image: url("../assets/img/bg.jpg");
  background-repeat: no-repeat;
  background-size: 100% 120%;
  background-position-y: bottom;
}

.login_card {
  margin: 100px 200px;
  width: 20%;
  min-width: 300px;
  height: 200px;
  border-radius: 10px;
}

.el-footer {
  height: 10%;
  text-align: center;
}
</style>
