<template>
  <div style="">
    <el-row>
      <div style="height: 500px;">
        <template>
          <el-carousel indicator-position="outside" style="width: 80%;margin-top: 20px" type="card" height="400px">
            <el-carousel-item v-for="item in imgs" :key="item">
              <img :src="item" style="width: 100%">
            </el-carousel-item>
          </el-carousel>
        </template>
      </div>
      <div style="display: flex;gap: 30px;">
        <div class="cards">
          <h1>
            Hello，{{ user?.name }}！Welcome!
          </h1>
          <p>
            Introduction:
          </p>
          <p style="text-indent: 2em;">
            As a user, user can register, login, view personal details, change passwords, and check system
            announcements, as well as browse and purchase pet supplies, adopt or return pets, post pet boarding
            requests, and recharge balances for pet-related expenses.
          </p>
        </div>
        <div class="card">
          <div class="content" style="height: 260px;overflow-y: auto; border: 1px solid #000;
  padding: 10px;" >
            <svg fill="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path
                  d="M20 9V5H4V9H20ZM20 11H4V19H20V11ZM3 3H21C21.5523 3 22 3.44772 22 4V20C22 20.5523 21.5523 21 21 21H3C2.44772 21 2 20.5523 2 20V4C2 3.44772 2.44772 3 3 3ZM5 12H8V17H5V12ZM5 6H7V8H5V6ZM9 6H11V8H9V6Z">
              </path>
            </svg>
            <p class="para">
            <div v-for="notice in notices">
              <div>
                <h3>{{ notice.title }}</h3>
                <div>{{ notice.content }}</div>
                <div style="margin-left: 180px;width: 100px;">{{ notice.time }}</div>
                <hr>
              </div>
            </div>
            </p>
          </div>
        </div>
      </div>

    </el-row>
    <div
        style="position: fixed;width: 200px;height: 80vh;right: 60px;top: 100px;z-index: 99;display: flex;flex-direction: column;justify-content: space-around;">
      <div v-for="(item, index) in arr">
        <div style="margin: auto;" class="bt1" :class="{ active: name == item.path }" @click="go(item.path)">
          <i :class="item.icon" style="margin-right: 10px;"></i>{{ item.name }}
        </div>
      </div>

    </div>



  </div>
</template>

<script>

export default {
  name: 'Uhome',
  data() {
    return {
      imgs: [
        "http://localhost:9090/files/adopt.jpg",
        "http://localhost:9090/files/foster.jpg",
        "http://localhost:9090/files/goods.jpg",
        "http://localhost:9090/files/order.jpg"
      ],
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: [],
      arr: [{
        name: 'Adopt',
        path: 'Uadopt',
        icon: 'el-icon-star-off'
      }, {
        name: 'Foster',
        path: 'Ufoster',
        icon: 'el-icon-c-scale-to-original'

      }, {
        name: 'Goods',
        path: 'Ugoods',
        icon: 'el-icon-shopping-cart-full'

      }, {
        name: 'Orders',
        path: 'uorder',
        icon: 'el-icon-pie-chart'

      },],
      name: 'Uadopt'
    }
  },
  created() {
    this.$request.get('/notice/selectAll').then(res => {
      this.notices = res.data || []
    })
  },
  methods: {
    go(url) {
      this.$router.push('/front/' + url)  // Navigate to the homepage
      this.name = url
    }
  }
}
</script>
<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

.bt1 {
  width: 200px;
  height: 80px;
  border-radius: 15px;
  background-color: #8c939d;
  line-height: 80px;
  text-align: center;
  color: white;
  cursor: pointer;
  box-shadow: 0 0 10px #8c939d;


}

.active {
  color: white;
}

.bt1:hover {
  background-color: #99a9bf;
  color: white;
}



.bt1:hover {
  background-color: #99a9bf;
  color: white;
}

.cards {
  width: 900px;
  color: #000;
  background-color: rgba(255, 255, 255, 0.25);
  backdrop-filter: blur(6px);
  -webkit-backdrop-filter: blur(6px);
  border: 1px solid rgba(255, 255, 255, 0.18);
  box-shadow: rgba(142, 142, 142, 0.19) 0px 6px 15px 0px;
  -webkit-box-shadow: rgba(142, 142, 142, 0.19) 0px 6px 15px 0px;
  border-radius: 12px;
  -webkit-border-radius: 12px;
  margin-left: 30px;
  padding: 10px;
  line-height: 30px;
  border: 1px dashed #ccc;
}

.card {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 320px;
  border-radius: 24px;
  line-height: 1.6;
  transition: all 0.48s cubic-bezier(0.23, 1, 0.32, 1);
}

.content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 24px;
  padding: 36px;
  border-radius: 22px;
  color: #ffffff;
  overflow: hidden;
  background: #0a3cff;
  transition: all 0.48s cubic-bezier(0.23, 1, 0.32, 1);
}

.content::before {
  position: absolute;
  content: "";
  top: -4%;
  left: 50%;
  width: 90%;
  height: 90%;
  transform: translate(-50%);
  background: #ced8ff;
  z-index: -1;
  transform-origin: bottom;

  border-radius: inherit;
  transition: all 0.48s cubic-bezier(0.23, 1, 0.32, 1);
}

.content::after {
  position: absolute;
  content: "";
  top: -8%;
  left: 50%;
  width: 80%;
  height: 80%;
  transform: translate(-50%);
  background: #e7ecff;
  z-index: -2;
  transform-origin: bottom;
  border-radius: inherit;
  transition: all 0.48s cubic-bezier(0.23, 1, 0.32, 1);
}

.content svg {
  width: 48px;
  height: 48px;
}

.content .para {
  z-index: 1;
  opacity: 1;
  font-size: 18px;
  transition: all 0.48s cubic-bezier(0.23, 1, 0.32, 1);
}

.content .link {
  z-index: 1;
  color: #fea000;
  text-decoration: none;
  font-family: inherit;
  font-size: 16px;
  transition: all 0.48s cubic-bezier(0.23, 1, 0.32, 1);
}

.content .link:hover {
  text-decoration: underline;
}

.card:hover {
  transform: translate(0px, -16px);
}

.card:hover .content::before {
  rotate: -8deg;
  top: 0;
  width: 100%;
  height: 100%;
}

.card:hover .content::after {
  rotate: 8deg;
  top: 0;
  width: 100%;
  height: 100%;
}
</style>
