<template>
  <div class="container">
    <div class="upper-content">
      <div class="card2" style="padding: 10px;">
        Hello, {{ user?.name }}! Welcome
      </div>
    </div>
    <div class="lower-content">
      <div class="card2" style="width: 100%;">
        <div style="margin-bottom: 30px; font-size: 20px; font-weight: bold;">Notice List</div>
        <div>
          <el-timeline reverse slot="reference">
            <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time">
              <div class="card5">
                <a class="card6" href="#">
                  <p> {{item.title}}</p>
                  <p class="small">{{item.content}}</p>
                  <div class="go-corner" href="#">
                    <div class="go-arrow">
                    </div>
                  </div>
                </a>
              </div>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Home',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: []
    }
  },
  created() {
    this.$request.get('/notice/selectAll').then(res => {
      this.notices = res.data || []
    })
  }
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
}

.upper-content {
  flex: 1;
  margin-bottom: 10px;
}

.lower-content {
  flex: 1;
}

.card2 {
    padding: 20px;
    background-color: #fff;
    border-radius: 5px;
    box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.1);
}
/*NoticeCard*/
.card5 p {
  font-size: 17px;
  font-weight: 400;
  line-height: 20px;
  color: #666;
}

.card5 p.small {
  font-size: 14px;
}

.go-corner {
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  width: 32px;
  height: 32px;
  overflow: hidden;
  top: 0;
  right: 0;
  background-color: #00838d;
  border-radius: 0 4px 0 32px;
}

.go-arrow {
  margin-top: -4px;
  margin-right: -4px;
  color: white;
  font-family: courier, sans;
}

.card6 {
  display: block;
  position: relative;
  max-width: 262px;
  background-color: #f2f8f9;
  border-radius: 4px;
  padding: 32px 24px;
  margin: 12px;
  text-decoration: none;
  z-index: 0;
  overflow: hidden;
}

.card6:before {
  content: "";
  position: absolute;
  z-index: -1;
  top: -16px;
  right: -16px;
  background: #00838d;
  height: 32px;
  width: 32px;
  border-radius: 32px;
  transform: scale(1);
  transform-origin: 50% 50%;
  transition: transform 0.25s ease-out;
}

.card6:hover:before {
  transform: scale(21);
}

.card6:hover p {
  transition: all 0.3s ease-out;
  color: rgba(255, 255, 255, 0.8);
}

.card6:hover h3 {
  transition: all 0.3s ease-out;
  color: #fff;
}
</style>