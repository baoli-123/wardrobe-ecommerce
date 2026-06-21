<template>
  <div class="reg">
      <el-card class="box-card" style="max-width: 550px; margin: 0 auto; ">
          <h1>用户注册</h1>
          <el-form :model="form" label-width="auto" style="max-width: 500px; margin: 0 auto; "
                   :rules="rules"
                   ref="ruleFormRef"
                   :size="formSize"
                   status-icon>
              <el-form-item label="用户名" prop="name">
                  <el-input v-model="form.name" placeholder="请输入用户名"/>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                  <el-input v-model="form.password" type="password" show-password placeholder="请输入密码"/>
              </el-form-item>
              <el-form-item label="手机号" prop="phone">
                  <el-input v-model="form.phone" placeholder="请输入手机号码"/>
              </el-form-item>
              <el-form-item label="地址" prop="address">
                  <el-input v-model="form.address" placeholder="请输入详细地址"/>
              </el-form-item>
              <div style="display:flex; justify-content: center">
                  <el-form-item>
                      <el-button type="primary" @click="onSubmit">注册</el-button>
                      <el-button @click="reset">重置</el-button>
                  </el-form-item>
              </div>
          </el-form>
      </el-card>
  </div>
</template>
<script setup>
import {reactive, ref} from 'vue'
import axios from "../axios";

const form = reactive({
  name: '',
  password: '',
  phone: '',
  address: ''
})

const ruleFormRef = ref(null);
const formSize = ref('default');

const rules = reactive({
  name: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 10, message: '用户名长度在 2 到 10 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 16, message: '密码长度在 6 到 16 个字符', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入电话号码', trigger: 'blur' },
    { pattern: /^1[34578]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  address: [
    { required: true, message: '请输入地址', trigger: 'blur' }
  ]
})
const onSubmit = () => {
  const data = {
    userName: form.name,
    password: form.password,
    phone: form.phone,
    address: form.address
  }
  //表单数据校验
  if (!ruleFormRef.value) return
  ruleFormRef.value.validate((valid, fields) => {
    if (valid) {
      axios.post('/wardrobe_back/register',data).then(res => {
        alert(res.data)
        reset()
      })
    } else {
      console.log('表单校验未通过!', fields)
    }
  })
}
const reset = () => {
  form.name = ''
  form.password = ''
  form.phone = ''
  form.address = ''
}
</script>
<style scoped>
h1{
  display: flex;
  justify-content: center;
  margin: 50px auto ;
}
el-button{
  display: flex;
  justify-content: center; /* 水平居中 */
}
</style>