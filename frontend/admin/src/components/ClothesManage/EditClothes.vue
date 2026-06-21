<template>
  <div>
    <el-dialog v-model="editDialogSon" @close="handleClose" title="修改服装" width="500">
      <el-form :model="formData"
               ref="ruleFormRef"
               :rules="rules"
               status-icon
               style="width: 400px"
               label-width="auto">
        <el-form-item label="服装名称" prop="clothName">
          <el-input v-model="formData.clothName"></el-input>
        </el-form-item>
        <el-form-item label="服装类别" prop="typeId">
          <el-select v-model="formData.typeName" placeholder="请选择服装类别" >
            <el-option
                v-for="type in typesData.list"
                :key="type.id"
                :label="type.typeName"
                :value="type.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="服装风格" prop="style">
          <el-input v-model="formData.style"></el-input>
        </el-form-item>
        <el-form-item label="图片">
          <el-upload
              class="avatar-uploader"
              action="/wardrobe_back/uploadFile"
              :show-file-list="false"
              :on-success="handleImgSuccess"
              :before-upload="beforeImgUpload"
              name="clothesImage"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar"  alt=""/>
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="服装价格" prop="price">
          <el-input v-model="formData.price"></el-input>
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
import {defineProps, reactive, ref, defineEmits, computed, onMounted} from 'vue';
import axios from "../../axios"
import {Plus} from "@element-plus/icons-vue";
const ruleFormRef = ref(null);
const rules = reactive({
  clothName: [
    { required: true, message: '请输入服装名称', trigger: 'blur' }
  ],
  typeId: [
    { required: true, message: '请选择服装类别', trigger: 'blur' }
  ],
  style: [
    { required: true, message: '请输入服装风格', trigger: 'blur' },
  ],
  price: [
    { required: true, message: '请输入服装价格', trigger: 'blur' },
    { pattern: /^\d+(\.\d+)?$/, message: '请输入合法的数字', trigger: 'blur' }
  ]
})

const props = defineProps({
  editData : Object,
  editDialog: {
    type: Boolean,
    default: false,
  }
})


// 使用 reactive 创建响应式状态，确保可以响应 prop 的变化
const formData = reactive({
  ...props.editData
})
//提交事件
const emits = defineEmits(['ChangeEditDialog']);
const editDialogSon = computed({
  get() {
    return props.editDialog;
  }
})
onMounted(() => {
  allTypes()
})

//获取服装类别
const typesData = reactive({
  list: []
})
const allTypes = () => {
  axios.get("/wardrobe_back/allTypes").then(res => {
    typesData.list = res.data
  })
}

//文件名称
const fileName = ref('')
//上传文件之前的检查方法
const beforeImgUpload = (file) => {
  const isJPEG = file.type === 'image/jpeg'
  const isJPG = file.type === 'image/jpeg'
  const isPNG = file.type === 'image/png'
  const isImage = isJPG || isPNG || isJPEG;
  if (!isImage) {
    alert("上传图片只能是 JPG/PNG 格式!")
    return false;
  }
  // 如果一切正常，则返回 true，允许文件上传
  return true;
}

//图片路径
const url = "/wardrobe_back/images/"
//上传图片成功的方法
const imageUrl = ref(url + formData.image)
const handleImgSuccess = (response,uploadFile) => {
  if(uploadFile.raw){
    imageUrl.value = URL.createObjectURL(uploadFile.raw)
    fileName.value = response
    alert("上传成功！")
  }else{
    console.error('uploadFile.raw is not defined');
  }
}

//图片名称(当修改图片时图片名称为新返回的，否则为原来传递过来的数据)
const imageName = computed(() => {
  return fileName.value || formData.image
})

// 提交表单的方法
const submitForm = () => {
  // 处理表单提交逻辑，发送请求
  const data = {
    id: formData.id,
    clothName: formData.clothName,
    style: formData.style,
    typeId : formData.typeId,
    image: imageName.value,
    price: formData.price,
  }
  // 表单数据校验
  if (!ruleFormRef.value) return
  ruleFormRef.value.validate((valid, fields) => {
    if (valid) {
      axios.post('/wardrobe_back/editClothes',data).then(res => {
        alert(res.data)
        emits('ChangeEditDialog', false);
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
  emits('ChangeEditDialog', false);
};
</script>
<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>