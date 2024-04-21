<template>
  <div>
    <img src="http://localhost:9090/files/adopt-b.jpg" style="width: 100%; height: 400px">
    <br>
    <div class="radio-inputs" style="margin-left: 750px ">
      <label class="radio">
        <input type="radio" name="radio" checked="" @click="changeType('all')">
        <span class="name">All</span>
      </label>
      <label class="radio">
        <input type="radio" name="radio"   @click="changeType('Adopted')">
        <span class="name">Adopted</span>
      </label>
      <label class="radio">
        <input type="radio" name="radio"  @click="changeType('WaitingAdoption')">
        <span class="name">WaitingAdoption</span>
      </label>
    </div>
    <br>
    <div style="margin-bottom: 10px; font-size: 20px; font-weight: bold; color: #817a70;margin-left: 760px">If you like me, adopt me~</div>
    <div style="margin-left: 50px">
      <br>
      <el-row>
        <el-col :span="5" v-for="item in petData">
          <div class="card1">
            <div class="card1-inner">
              <div class="card1-front">
                          <div class="card10" style="background-color: #f8f4e8; text-align: center;margin-left: 10px;margin-right: 10px" v-if="getView(item.status)">
                            <img :src="item.img" alt="" style="width: 80px; height: 80px; border-radius: 50%">
                            <div style="font-weight: bold; color: #817a70; margin-top: 5px">{{ item.name }}（{{ item.type }}）</div>
                            <div style="color: #575353; font-size: 13px; margin-top: 5px">Sex：{{ item.sex }}， Age：{{ item.age }}， <span style="color: #7d3d0c">{{ item.status }}</span></div>

                          </div>
              </div>
              <div class="card1-back">
                <div class="card10" style="background-color: #f8f4e8; text-align: center;margin-left: 10px;margin-right: 10px" v-if="getView(item.status)">
                  <div style="margin-top: 15px; color: #4b4949; text-align: center;font-size: 13px;width: 100%;overflow-wrap: break-word;">Introduction:<br>{{ item.intro}}</div>
                              <div>
                                <el-button type="success" size="mini" style="margin-top: 5px;height: 35px;position: absolute;bottom: 10px;left: 50px" :disabled="item.status === 'Adopted'" :abled="item.status === 'WaitingAdoption'"@click="adopt(item)">
                                  <span style="position: relative;top: -20px">Adopt</span>
                                </el-button>
                              </div>
                </div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>


  </div>
</template>

<script>

export default {
  name: 'Uadopt',
  data() {
    return {
      type: "all",
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      petData: [],
    }
  },
  created() {
    this.selectAll()
  },
  methods:{
    getView(type){
      console.log(type)
      console.log(this.type)
      if (this.type === "all"){
        return true;
      }
      if (this.type === "Adopted"){
        return type === "Adopted";
      }
      if (this.type === "WaitingAdoption"){
        return type === "WaitingAdoption";
      }
    },
    changeType(msg){
      this.type = msg;
      console.log(this.type)
    },
    selectAll(){
      this.$request.get('/pet/selectAll').then(res => {
        if (res.code === '200') {
          this.petData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    adopt(pet){
      if (this.user.role === 'ADMIN') {
        this.$message.warning('Your role does not support this operation')
        return
      }
      let data = JSON.parse(JSON.stringify(pet))
      data.status = "Adopted"
      this.$request.put("/pet/update",data).then(res => {
        if(res.code === '200'){
          this.selectAll();
          let adoptData={
            userId:this.user.uid,
            petId:pet.pid
          }
          this.$request.post('/adopt/add',adoptData).then(res =>{
            if(res.code === '200') {
              this.$message.success('Adopt Successful')
            }else{
              this.$message.success(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style>
.card10 {
  width: 190px;
  height: 254px;
  background-image: linear-gradient(144deg, #F5DEB3, #ADD8E6 60%, #bd6fda);
  border: none;
  border-radius: 10px;
  padding-top: 10px;
  position: relative;
  margin: auto;
  font-family: inherit;
}

.card10 span {
  font-weight: 600;
  color: white;
  text-align: center;
  display: block;
  padding-top: 10px;
  font-size: 1.3em;
}

.card10 .job {
  font-weight: 400;
  color: white;
  display: block;
  text-align: center;
  padding-top: 5px;
  font-size: 1em;
}

.card10 .img {
  width: 70px;
  height: 70px;
  background: #e8e8e8;
  border-radius: 100%;
  margin: auto;
  margin-top: 20px;
}


.card10 button:hover {
  background: #212121;
  color: #ffffff;
}
/*r*/
.radio-inputs {
  position: relative;
  display: flex;
  flex-wrap: wrap;
  border-radius: 0.5rem;
  background-color: #EEE;
  box-sizing: border-box;
  box-shadow: 0 0 0px 1px rgba(0, 0, 0, 0.06);
  padding: 0.25rem;
  width: 300px;
  font-size: 14px;
  margin-left: 50px;
  margin-top: 10px;
}

.radio-inputs .radio {
  flex: 1 1 auto;
  text-align: center;
}

.radio-inputs .radio input {
  display: none;
}

.radio-inputs .radio .name {
  display: flex;
  cursor: pointer;
  align-items: center;
  justify-content: center;
  border-radius: 0.5rem;
  border: none;
  padding: .5rem 0;
  color: rgba(51, 65, 85, 1);
  transition: all .15s ease-in-out;
}

.radio-inputs .radio input:checked + .name {
  background-color: #fff;
  font-weight: 600;
}
/*card1*/
.card1 {
  width: 200px;
  height: 400px;
  perspective: 1000px;
}

.card1-inner {
  width: 100%;
  height: 100%;
  position: relative;
  transform-style: preserve-3d;
  transition: transform 0.999s;
}

.card1:hover .card1-inner {
  transform: rotateY(180deg);
}

.card1-front,
.card1-back {
  position: absolute;
  width: 100%;
  height: 100%;
  backface-visibility: hidden;
}

.card1-front {
  /*background-color: #6A2C70;*/
  /*color: #fff;*/
  display: flex;
  align-items: center;
  /*border: 10px solid #6A2C70;*/
  border-radius: 10px;
  justify-content: center;
  font-size: 24px;
  transform: rotateY(0deg);
}

.card1-back {
  /*background-color: #F08A5D;*/
  /*color: #fff;*/
  display: flex;
  align-items: center;
  /*border: 10px solid #F08A5D;*/
  border-radius: 10px;
  justify-content: center;
  font-size: 24px;
  transform: rotateY(180deg);
}

</style>
