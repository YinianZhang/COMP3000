<template>
  <div>
    <!--header-->
    <div class="front-header">
      <div class="front-header-left">
        <img src="@/assets/imgs/logo.png" alt="">
        <div class="title">PetManagementCenter</div>
      </div>
      <div class="front-header-center">
        <div class="front-header-nav">
          <el-menu :default-active="$route.path" mode="horizontal" router>
						<el-menu-item index="/front/Uhome">Home</el-menu-item>
            <el-menu-item index="/front/Uadopt">Adopt</el-menu-item>
            <el-menu-item index="/front/Ufoster">Foster</el-menu-item>
            <el-menu-item index="/front/Ugoods">Goods</el-menu-item>
            <el-menu-item index="/front/uorder">Orders</el-menu-item>
          </el-menu>
        </div>
      </div>
      <div class="front-header-right">
        <div v-if="!user.username">
          <el-button @click="$router.push('/login')">Login</el-button>
          <el-button @click="$router.push('/register')">Register</el-button>
        </div>
        <div v-else>
          <el-dropdown>
            <div class="front-header-dropdown" >
              <img :src="user.avatar" style="border-radius: 25%">
              <div style="margin-left: 10px;">
                <span >
                  {{ user.name }}
                  </span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
              </div>
            </div>
            <el-dropdown-menu slot="dropdown" >
              <el-dropdown-item>
                <div @click="TurnPC">PersonalCenter</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div @click="logout">Exit</div>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>
    <!--body-->
    <div class="main-body">
      <router-view ref="child" @update:user="updateUser" />
    </div>
  </div>

</template>

<script>

export default {
  name: "FrontLayout",

  data () {
    return {
      top: '',
      notice: [],
      user: JSON.parse(localStorage.getItem("xm-user") || '{}'),
    }
  },

  mounted() {
    this.loadNotice()
  },
  methods: {
    loadNotice() {
      this.$request.get('/notice/selectAll').then(res => {
        this.notice = res.data
        let i = 0
        if (this.notice && this.notice.length) {
          this.top = this.notice[0].content
          setInterval(() => {
            this.top = this.notice[i].content
            i++
            if (i === this.notice.length) {
              i = 0
            }
          }, 2500)
        }
      })
    },
    updateUser() {
      this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // Retrieve the latest information for the user
    },
    // exit login
    logout() {
      localStorage.removeItem("xm-user");
      this.$router.push("/login");
    },
    TurnPC() {
      this.$router.push("/front/person");
    },
  }

}
</script>

<style scoped>
  .front-notice {
    padding: 5px 20px;
    color: #666;
    font-size: 12px
  }
  .front-header {
    display: flex;
    height: 60px;
    line-height: 60px;
    border-bottom: 1px solid #eee;
    background: white;
  }
  .front-header-left {
    width: 350px;
    display: flex;
    align-items: center;
    padding-left: 30px;
  }
  .front-header-left img, .front-header-dropdown img {
    width: 40px;
    height: 40px;
    border-radius: 50%
  }
  .front-header-left .title {
    flex: 1;
    color: #355476;
    cursor: pointer;
    margin-left: 10px;
    font-size: 20px;
    font-weight: bold;
  }
  .front-header-center {
    flex: 1;
  }
  .front-header-right {
    width: 200px;
    padding-right: 20px;
    text-align: right;
  }
  .front-header-dropdown {
    display: flex;
    align-items: center;
    justify-content: right;
  }
  .el-dropdown-menu {
    width: 200px !important;
    text-align: center !important;
  }


  .main-content {
    width: 100%;
    margin: 5px auto;
  }

  .el-menu.el-menu--horizontal {
    border: none !important;
    height: 59px;
  }
</style>