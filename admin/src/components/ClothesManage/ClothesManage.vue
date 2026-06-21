<template>
  <div>
    <el-card>
      <el-input style="width:200px" v-model="searchClothesName" placeholder="请输入服装名称"  >
      </el-input> &nbsp;
      <el-select v-model="searchType" placeholder="请选择服装类别" class="large">
        <el-option
            v-for="type in typesData.list"
            :key="type.id"
            :label="type.typeName"
            :value="type.typeName">
        </el-option>
      </el-select>  &nbsp;
      <el-select v-model="searchStyle" placeholder="请选择服装风格" class="large">
        <el-option
            v-for="(style,index) in styles"
            :key="index"
            :label="style"
            :value="style">
        </el-option>
      </el-select>  &nbsp;&nbsp;&nbsp;
      <el-button type="primary" :icon="Search" @click="getClothesByParams" class="large">搜索</el-button>
      <el-button type="info" plain @click="reset" class="large">重置</el-button>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <el-button type="success" plain @click="addClothes" class="large">
        上架服装
        <el-icon class="el-icon--right"><Upload /></el-icon>
      </el-button>
      <el-table
          :data="currentData"
          border
          style="width: 100%;margin-top:30px"
          height="630"
          sticky
          :empty-text="emptyText"
          highlight-current-row
      >
        <el-table-column prop="id" label="编号" width="100" align="center"/>
        <el-table-column prop="clothName" label="服装名称" width="180" align="center"/>
        <el-table-column prop="image" label="图片" width="200" align="center">
          <template v-slot:default="scope">
            <el-image style="width: 80px; height: 100px" :src="url + scope.row.image" />
          </template>
        </el-table-column>
        <el-table-column prop="sizeList" label="尺码信息" width="280" align="center">
          <template v-slot:default="scope" >
            <div style="display: flex; justify-content: center; ">
              <div v-for="size in scope.row.sizeList" :key="size.id" style="display: inline-block; margin-right: 10px;">
                {{ size.sizeName }}
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="typeName" label="类别" width="150" align="center"/>
        <el-table-column prop="style" label="风格" width="150" align="center"/>
        <el-table-column prop="price" label="价格" width="150" align="center">
          <template v-slot:default="scope">
            ￥{{scope.row.price}}
          </template>
        </el-table-column>

        <el-table-column label="操作" width="300" align="center">
          <template #default="scope">
            <el-button type="primary" :icon="Edit" circle class="btn" @click="editClothes(scope.row)" />
            <el-button type="danger" :icon="Delete" circle class="btn" @click="deleteClothes(scope.row)"/>
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
      <!--子组件弹窗（编辑服装）-->
      <EditClothes v-if="editDialog"
                :editDialog="editDialog"
                :editData="editData"
                @ChangeEditDialog="ChangeEditDialog"/>
      <!--子组件弹窗（添加服装）-->
      <AddClothes v-if="addDialog"
               :addDialog="addDialog"
               @ChangeAddDialog="ChangeAddDialog"/>
    </el-card>
  </div>
</template>

<script setup>
import {computed, onMounted, reactive, ref} from "vue";
import axios from "../../axios";
import {Delete, Edit, Search, Upload} from "@element-plus/icons-vue";
import EditClothes from './EditClothes.vue'
import AddClothes from './AddClothes.vue'
import {ElMessage, ElMessageBox} from "element-plus";

const emptyText = ref('无数据')
// Dom 挂载之后
onMounted(() => {
  getClothesList();
  allTypes()
  allStyles()
})
// 服装数据
let tableData = ref([]);
//图片访问路径
const url = "/wardrobe_back/images/"

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

// 获取服装列表
const getClothesList = () => {
  axios.get('/wardrobe_back/getAllClothesData').then(res => {
    console.log(res);
    tableData.value = res.data;
    total.value = res.data.length;
  }).catch(error => {
    console.log(error)
  })
}

//多条件查询
const typesData = reactive({
  list: []
})
const styles = ref([])
const searchType = ref('')
const searchStyle = ref('')
const searchClothesName = ref('')
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
//根据名称、类别、风格进行多条件查询服装
const getClothesByParams = () => {
  axios.get('/wardrobe_back/searchClothes',{
    params:{
      clothName : searchClothesName.value,
      style : searchStyle.value,
      type : searchType.value
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
  searchClothesName.value = ''
  searchStyle.value = ''
  searchType.value = ''
}

//添加服装
const addDialog = ref(false)

const addClothes = () => {
  addDialog.value = true; // 打开弹窗
}
const ChangeAddDialog = (value) =>{
  addDialog.value = value;
  getClothesList()
}

//编辑服装信息
const editDialog = ref(false)
const editData = ref({});
const editClothes = (value) => {
  editDialog.value = true; // 打开弹窗
  editData.value = value; // 设置弹窗的初始数据
}
const ChangeEditDialog = (value) =>{
  editDialog.value = value;
  getClothesList()
}

//删除服装
const deleteClothes = (value) => {
  ElMessageBox.confirm(
      '确定要下架该服装吗?',
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
      getClothesList()
    }).catch(error => {
      console.log(error)
    })
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '取消删除',
    })
  })
}
</script>

<style scoped>
.el-table {
  font-size: 20px;
}
.btn{
  border-radius: 50%;
  /* 增大按钮的大小 */
  width: 40px; /* 你可以根据需要调整这个值 */
  height: 40px; /* 你可以根据需要调整这个值，确保宽高相等以保持圆形 */
  /* 增大内部图标的大小（如果需要的话） */
  font-size: 20px;
}
.large{
  font-size: 18px;
}
</style>