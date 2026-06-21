<template>
  <div class="login">
      <el-card class="box-card" style="max-width: 550px; margin: 0 auto; ">
          <h1>用户登录</h1>
          <el-form :model="form" label-width="auto" style="max-width: 500px; margin: 0 auto; "
                   :rules="rules"
                   ref="ruleFormRef"
                   :size="formSize"
                   status-icon>
              <el-form-item label="用户名/电话" prop="userInfo">
                  <el-input v-model="form.userInfo" placeholder="请输入用户名或手机号"/>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                  <el-input v-model="form.password" type="password" show-password placeholder="请输入密码"/>
              </el-form-item>
              <div style="display:flex; justify-content: right">
                  <el-form-item>
                      <el-button type="primary" @click="onSubmit" >登录</el-button>
                      <el-button @click="reset">重置</el-button>
                  </el-form-item>
              </div>
          </el-form>
      </el-card>
  </div>
</template>

<style>
h1{
  display: flex;
  justify-content: center;
  margin: 50px auto ;
}

</style>

<script setup>
import {reactive, ref} from 'vue'
import axios from "../axios";
const form = reactive({
  userInfo: '',
  password: ''
})

const ruleFormRef = ref(null);
const formSize = ref('default');

const rules = reactive({
  userInfo: [{ required: true, message: '请输入姓名或手机号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
})
const onSubmit = () => {
  //表单数据校验
  if (!ruleFormRef.value) return
  ruleFormRef.value.validate((valid, fields) => {
    if (valid) {
      axios.get('/wardrobe_back/login',{
        params:{
          userInfo : form.userInfo,
          password : form.password
        }
      }).then(res => {
        if(res.data != null){
          alert("登录成功！")
            localStorage.setItem('token',JSON.stringify(res.data.token))
            if(res.data.token != null){
                localStorage.setItem('user',JSON.stringify(res.data)) //存储用户信息
            }
            /*location.reload()*/
          // 跳转到指定页面
          window.location.href = '/';
        }else{
          alert("用户名或密码错误！")
        }
        reset()
      }).catch(error => {
        console.log(error)
      })
    } else {
      console.log('表单校验未通过!', fields)
    }
  })
}
const reset = () => {
  form.userInfo = ''
  form.password = ''
  form.autoLogin = ''
}
</script>
