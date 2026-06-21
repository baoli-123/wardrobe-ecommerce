<template>
  <div>
    <el-dialog v-model="addDialogSon" @close="handleClose" title="添加用户" width="400">
      <el-form :model="formData"
               ref="ruleFormRef"
               :rules="rules"
               status-icon
               :size="formSize"
               label-width="auto">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="formData.userName"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="formData.password"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="formData.phone"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="formData.address"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="handleClose">取消</el-button>
          <el-button type="primary" @click="submitForm">提交</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {defineProps, reactive, ref, defineEmits, computed} from 'vue';
import axios from "../../axios"
const props = defineProps({
  addDialog: {
    type: Boolean,
    default: false,
  }
})
const formSize = ref('default');
const ruleFormRef = ref(null);
const rules = reactive({
  userName: [
    { required: true, message: '请输入用户姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '用户姓名长度在 2 到 10 个字符', trigger: 'blur' }
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

// 使用 reactive 创建响应式状态，确保可以响应 prop 的变化
const formData = reactive({})
//提交事件
const emits = defineEmits(['ChangeAddDialog']);
const addDialogSon = computed({
  get() {
    return props.addDialog;
  }
})
// 提交表单的方法
const submitForm = () => {
  // 处理表单提交逻辑，发送请求
  const data = {
    id: formData.id,
    userName: formData.userName,
    password: formData.password,
    phone: formData.phone,
    address: formData.address,
    role: 2,
  }
  // 表单数据校验
  if (!ruleFormRef.value) return
  ruleFormRef.value.validate((valid, fields) => {
    if (valid) {
      axios.post('/wardrobe_back/addUser',data).then(res => {
        alert(res.data)
        emits('ChangeAddDialog', false);
      })
    } else {
      console.log('表单校验未通过!', fields)
    }
  })
};
//取消的方法
const handleClose = () => {
  // 处理表单提交逻辑，例如发送请求等
  console.log("取消");
  emits('ChangeAddDialog', false);
};
</script>
<style scoped>

</style>