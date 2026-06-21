<template>
    <div>
        <el-container class="home-container">
            <!-- header -->
            <el-header>
                <el-row>
                    <el-col :span="4">
                        <p class="system-name">网上衣橱-后台管理</p>
                    </el-col>
                    <el-col :offset="12" :span="8" style="min-width: 150px">
                        <el-dropdown style="float: right; margin: 20px 10px">
                            <span class="el-dropdown-link" style="color: #fff; cursor: pointer">
                                    {{userName}}&nbsp;&nbsp;
                              <el-icon class="el-icon--right">
                                  <arrow-down />
                              </el-icon>
                            </span>
                            <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item @click="logout">退出系统</el-dropdown-item>
                                </el-dropdown-menu>
                            </template>
                        </el-dropdown>
                        <el-avatar shape="square" :src="avatar" style="margin: 10px; float: right"></el-avatar>
                    </el-col>
                </el-row>
            </el-header>

            <el-container style="overflow: auto">
                <!-- 菜单 -->
                <el-aside>
                    <div class="toggle-button" @click="isCollapse = !isCollapse">
                        <el-icon :size="20">
                            <Expand v-if="isCollapse" />
                            <Fold v-if="!isCollapse" />
                        </el-icon>
                    </div>
                    <el-menu router :default-active="activePath" class="el-menu-vertical-demo" :collapse="isCollapse">
                        <el-menu-item index="/index" @click="saveActiveNav('/index')">
                            <el-icon>
                                <house />
                            </el-icon>
                            <span>首页</span>
                        </el-menu-item>
                        <el-menu-item index="/clothes" @click="saveActiveNav('/clothes')">
                        <el-icon><Goods /></el-icon>
                        <span>服装管理</span>
                        </el-menu-item>
                        <el-menu-item index="/order" @click="saveActiveNav('/cart')">
                          <el-icon><ShoppingCart /></el-icon>
                        <span>订单管理</span>
                        </el-menu-item>
                        <el-menu-item index="/user" @click="saveActiveNav('/user')">
                            <el-icon>
                                <user/>
                            </el-icon>
                            <span>用户管理</span>
                        </el-menu-item>
                    </el-menu>
                </el-aside>
                <el-container>
                    <el-main>
                        <router-view></router-view>
                    </el-main>
                    <el-footer>联系我们：https://www.itcast.cn/</el-footer>
                </el-container>
            </el-container>
        </el-container>
    </div>
</template>
<script setup>
import { onBeforeMount, ref } from 'vue';
import avatar from "../assets/img/avator.jpg"
import { useRouter } from 'vue-router'
import {ArrowDown, Expand, Fold, Goods, House, ShoppingCart, User} from "@element-plus/icons-vue";
const router = useRouter();
const userName = ref(JSON.parse(localStorage.getItem('user'))?.userName)
// 挂载 DOM 之前
onBeforeMount(() => {
    activePath.value = localStorage.getItem("activePath")
        ? localStorage.getItem("activePath")
        : "/index"
})
let isCollapse = ref(false);
let activePath = ref("/index");
// 保存链接的激活状态
const saveActiveNav = (path) => {
    localStorage.setItem("activePath", path);
    activePath.value = path;
}
const logout = () => {
    // 清除缓存
    localStorage.clear();
    router.push("/login");
}
</script>

<style scoped>
.home-container {
    position: absolute;
    height: 100%;
    top: 0px;
    left: 0px;
    width: 100%;
    background: #f2f3f5;
}

.el-header {
    background: #4682B4;
    padding: 0 10px;
    overflow: hidden;
}

.system-name {
    color: #fff;
    font-size: 18px;
}

.el-aside {
    background: white;
    width: auto !important;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
}

.el-footer {
    color: #cccccc;
    text-align: center;
    line-height: 60px;
}

.el-footer:hover {
    color: #4682B4;
}

.toggle-button {
    background-color: #d9e0e7;
    font-size: 18px;
    line-height: 24px;
    color: #fff;
    text-align: center;
    letter-spacing: 0.2em;
    cursor: pointer;
    color: black;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
}

.el-menu-item.is-active {
    color: #fff !important;
    font-size: 15px;
    font-weight: bold;
    background-color: #4682B4;
    border-radius: 2px;
    height: 50px;
    line-height: 50px;
    box-sizing: border-box;
    margin: 2px 5px 0px 2px;
}
</style>