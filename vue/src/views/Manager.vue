<template>
  <div class="manager-container">
    <!--  header  -->
    <div class="manager-header">
      <div class="manager-header-left">
        <img src="@/assets/imgs/logo.png" style="border-radius: 50%"/>
        <div class="title">BackstageManagement</div>
      </div>
      <div style="margin-left: 120px;margin-top: 15px">
        <label class="switch" @click="changeWidth">
          <input type="checkbox" checked="" v-model="isCollapse">
        </label>
      </div>
      <div class="manager-header-center">
        <el-breadcrumb style="margin-left: 30px" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }" ><span style="color: white">Home</span></el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: $route.path }"><span style="color: white">{{ $route.meta.name }}</span>></el-breadcrumb-item>
        </el-breadcrumb>
      </div>

      <div class="manager-header-right">
        <el-dropdown placement="bottom">
          <div class="avatar" >
            <img :src="user.avatar" style="border-radius: 25%"/>
            <div>{{ user.name}}</div>
          </div>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="goToPerson">PersonalData</el-dropdown-item>
            <el-dropdown-item @click.native="$router.push('/password')">ChangePassword</el-dropdown-item>
            <el-dropdown-item @click.native="logout">ExitLogin</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!--  body  -->
    <div class="manager-main">
      <!--  sidebar   -->
      <div class="manager-main-left animated-div" :style="{width: divWidth+'px'}">
        <el-menu class="animated-div" :default-openeds="['info', 'user']" router style="border: none;" :style="{width: divWidth+'px'}" :default-active="$route.path" @open="handleOpen" @close="handleClose" :collapse="isCollapse">

          <el-menu-item index="/home">
            <i class="el-icon-house"></i>
            <span slot="title">SystemHomepage</span>
          </el-menu-item>
          <el-submenu index="info">
            <template slot="title">
              <i class="el-icon-collection"></i><span style="color: white">DataManagement</span>
            </template>
            <el-menu-item index="/notice">NoticeData</el-menu-item>
            <el-menu-item index="/pet">StrayPetData</el-menu-item>
            <el-menu-item index="/adopt">AdoptData</el-menu-item>
            <el-menu-item index="/foster">FosterData</el-menu-item>
            <el-menu-item index="/goods">GoodsData</el-menu-item>
            <el-menu-item index="/orders">OrdersData</el-menu-item>
          </el-submenu>

          <el-submenu index="user">
            <template slot="title">
              <i class="el-icon-user"></i><span style="color: white">AccountManagement</span>
            </template>
            <el-menu-item index="/admin">AdminData</el-menu-item>
            <el-menu-item index="/user">UserData</el-menu-item>
          </el-submenu>
        </el-menu>
      </div>

      <!--  data table  -->
      <div class="manager-main-right animated-div" style="overflow: hidden">
        <router-view @update:user="updateUser" />
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "Manager",
  data() {
    return {
      divWidth:235,
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      isCollapse: false,
    }
  },
  created() {
    if (this.user.role!=="ADMIN") {
      this.$router.push('/login')
    }
  },
  methods: {

    changeWidth(){
      if (this.isCollapse===true){
        this.divWidth = 235
      }
      if (this.isCollapse === false){
        this.divWidth = 60
      }
    },
    updateUser() {
      this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // Retrieve the latest information for the user
    },
    goToPerson() {
      if (this.user.role === 'ADMIN') {
        this.$router.push('/adminPerson')
      }
    },
    logout() {
      localStorage.removeItem('xm-user')
      this.$router.push('/login')
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>
.animated-div {
  transition: width 0.02s ease-in;

}

.manager-container {
  background-color: #f8f8ff;
  min-height: 100vh;
}

.manager-header {
  display: flex;
  background-color: #2c334c;
}

.manager-header-left {
  width: 200px;
  height: 60px;
  padding-left: 10px;
  display: flex;
  align-items: center
}

.manager-header-left img {
  width: 40px;
  height: 40px;
}

.manager-header-left .title {
  flex: 1;
  margin-left: 5px;
  font-size: 20px;
  font-weight: bold;
  color: #ddd
}

.manager-header-center {
  flex: 1;
  width: 0;
  display: flex;
  align-items: center
}

.manager-header-right {
  width: 200px;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.manager-header-right .avatar {
  display: flex;
  align-items: center;
  padding-right: 10px;
  color: #ccc;
  cursor: default
}

.manager-header-right .avatar img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 5px
}

.manager-main {
  display: flex;
}

.manager-main-left {
  width: 200px;
  min-height: calc(100vh - 60px);
  box-shadow: 0 0 6px rgba(0, 21, 41, .35);
}

.manager-main-right {
  flex: 1;
  width: 0;
  padding: 10px;
}

.manager-main-right .search {
  padding: 10px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.1);
}

.manager-main-right .operation {
  margin-top: 5px;
  padding: 10px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.1)
}

.manager-main-right .table {
  margin-top: 5px;
  padding: 15px 10px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}

.manager-main-right .table .pagination {
  margin-top: 10px;
}

.card {
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.1);
}

/* ElementUI */
.el-menu {
  width: 200px;
  height: 100%;
  background-color: #222b40;
}

.el-submenu__title {
  color: #ccc;
  height: 50px;
  line-height: 50px;
}

.el-menu-item {
  color: #ccc;
  height: 50px;
  line-height: 50px;
  background-color: #222b40 !important;
}

.el-menu-item.is-active {

}

.el-menu--inline .el-menu-item {
  padding-left: 50px !important;
}

.el-menu .is-active {
  color: #3399ff !important;
}

.el-submenu__title:hover {
  background-color: #222 !important;
}

.el-menu-item:not(.is-active):hover {
}

.el-menu-item:hover {
  background-color: #222 !important;
}

.el-submenu__icon-arrow {
  margin-top: -5px;
}

.el-breadcrumb__inner {
  color: #ccc !important;
}

.el-dropdown-menu {
  margin-top: 20px !important;
}

.el-breadcrumb {
  margin-left: 10px;
}

th.el-table__cell {
  background-color: #f8f8f8 !important;
  color: #666;
}
/*bt1*/
.switch {
  --false: #E81B1B;
  --true: #009068;
}

input[type=checkbox] {
  appearance: none;
  height: 2rem;
  width: 3.5rem;
  background-color: #fff;
  position: relative;
  border-radius: .2em;
  cursor: pointer;
}

input[type=checkbox]::before {
  content: '';
  display: block;
  height: 1.9em;
  width: 1.9em;
  transform: translate(-50%, -50%);
  position: absolute;
  top: 50%;
  left: calc(1.9em/2 + .3em);
  background-color: var(--false);
  border-radius: .2em;
  transition: .3s ease;
}

input[type=checkbox]:checked::before {
  background-color: var(--true);
  left: calc(100% - (1.9em/2 + .3em));
}
</style>