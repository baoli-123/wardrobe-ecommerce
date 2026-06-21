<template>
  <div class="common-layout">
    <el-container>
      <el-header ><Header/></el-header>
      <!--主要组件-->
      <el-main>
        <router-view></router-view>
      </el-main>
      <el-footer><Footer/></el-footer>
    </el-container>
  </div>
</template>
<script setup>
import Header from "./components/Header.vue"
import Footer from "./components/Footer.vue"
</script>
<style scoped lang="less">
.el-container {
  display: flex; // 启用 flexbox 布局
  flex-direction: column; // 设置为垂直方向
  height: 100vh; // 使容器占据整个视口高度
  position: relative; // 如果需要保持相对定位
  margin: 0; // 确保没有外部边距影响布局
  padding: 0; // 确保没有内部填充影响布局
  box-sizing: border-box; // 包括边距和填充在内的总高度和总宽度
}

.el-header {
  padding: 0; // 移除内边距
  // 可以设置具体的高度值
  height: 60px; // 示例高度值
}

.el-footer {
  padding: 0; // 移除内边距
  height: 90px; // 保持固定高度
}

.el-main {
  // 使用 flex 属性来使 main 占据剩余空间
  flex: 1;
  overflow-y: auto; // 如果内容超出可视区域，添加滚动条

}
</style>


