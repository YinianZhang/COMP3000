<template>
  <div>
    <img src="http://localhost:9090/files/foster-b.jpg" style="width: 100%; height: 400px">
    <br>
    <div class="search" style="margin-left: 750px;margin-right: 20px">
      <el-input placeholder="Please enter the foster name" style="width: 300px" v-model="name"></el-input>
      <el-button type="info"    style="margin-left: 10px" icon="el-icon-search" @click="findByName()">Search</el-button>
      <el-button type="warning"    style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>
    <el-button type="primary"   icon="el-icon-plus" @click="handleAdd" style="margin-top: 20px;margin-left: 850px"></el-button>
    <el-dialog title="fosterdata" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false"
      destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="img" label="GoodsImage">
          <el-upload class="image-uploader" :action="$baseUrl + '/files/upload'" :headers="{ token: user.token }"
            list-type="picture" :on-success="handleimgSuccess">
            <el-button type="primary">Uploadimage</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="pet_name" label="PetName">
          <el-input v-model="form.pet_name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="time" label="fosterTime">
          <el-date-picker style="width: 100%" v-model="form.ftime" type="date" value-format="yyyy-MM-dd"
            placeholder="Select start date" :picker-options="expireTimeOPtion">
          </el-date-picker>
        </el-form-item>
        <el-form-item prop="days" label="fosterDays">
          <el-input v-model="form.days" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="service" label="fosterservice">
          <el-select v-model="form.service" placeholder="Please select service" style="width: 100%" @change="setPrice">
            <el-option label="BasicFoster--￡30/day" value="BasicFoster"></el-option>
            <el-option label="IntermediateFoster--￡50/day" value="IntermediateFoster"></el-option>
            <el-option label="AdvancedFoster--￡80/day" value="AdvancedFoster"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="status" label="fosterstatus">
          <el-select v-model="form.status" placeholder="Please select status" style="width: 100%">
            <el-option label="WaitingFoster" value="WaitingFoster"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="spend" label="fosterspend">
          <el-input v-model="form.spend" autocomplete="off" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Confirm</el-button>
      </div>
    </el-dialog>
    <div style="width: 100%">
      <div class="card" style="float: left;height: 120%" v-for="fos in fosData">
        <div class="card-img">
          <img :src="fos.photo" alt=""
            style="width: 100%;height: 90%; height: 200px; border-radius: 50%; border: 1px solid #cccccc">
        </div>
        <div class="card-info">
          <p class="text-title">
            <el-tag type="primary" size="normal" effect="dark">
              {{ fos.pet_name }}
            </el-tag>
          </p>
          <p class="text-body">Service: {{ fos.service }}</p>
          <p class="text-body">Room:{{ fos.room_id }}</p>
          <p class="text-body">Time:{{ fos.ftime
            }}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ fos.days }}Days</p>
        </div>
        <div class="card-footer">
          <div :style="getColor(fos.status)">{{ fos.status }}</div>
          <div class="text-title" style="margin: 10px 0;">
            <el-tag type="danger" size="normal" effect="dark">
              £{{ fos.spend }}
            </el-tag>

          </div>
        </div>
        <div class="card-footer">
          <template style="width: 100%;">
            <el-popconfirm confirm-button-text='Yes' cancel-button-text='No'
              title="Confirm that the foster pet has been retrieved?" @confirm="retrieveSave(fos)">
              <el-button slot="reference" type="primary"    style="margin-left: 50px;margin-top: 7px"
                v-if="fos.status === 'FinishFoster'">Retrieve</el-button>
            </el-popconfirm>
          </template>
        </div>
      </div>
    </div>

  </div>
</template>

<script>

export default {
  name: 'Ufoster',
  data() {
    return {
      name: null,
      fosData: [],
      expireTimeOPtion: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;  //If there is no '-8.64e7' after the date, it means today is not selectable.
        }
      },
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      fosterData: [],
      fromVisible: false,
      form: {},
      rules: {
        title: [
          { required: true, message: 'Please enter the title', trigger: 'blur' },
        ],
        content: [
          { required: true, message: 'Please enter the content', trigger: 'blur' },
        ]
      },
    }
  },
  created() {
    this.selectAll()
  },
  methods: {
    findByName() {
      this.$request.get('/foster/selectByIdAndUser?pet_name=' + this.name + "&userId=" + this.user.uid).then(res => {
        if (res.code === '200') {
          // update successful
          this.fosData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    reset() {
      this.name = null;
      this.selectAll();

    },
    //submit
    retrieveSave(row) {
      row.status = "Retrieved"
      this.$request.put('/foster/update', row).then(res => {
        if (res.code === '200') {
          // update successful
          this.$message.success('RetrievedSuccessful')
          this.selectAll()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    //get color
    getColor(msg) {
      if (msg === "WaitingFoster") {
        return { color: '#4CC08A' };
      }
    },
    //set price
    setPrice() {
      if (this.form.service === "BasicFoster") { this.form.spend = 30 * this.form.days }
      if (this.form.service === "IntermediateFoster") { this.form.spend = 50 * this.form.days }
      if (this.form.service === "AdvancedFoster") { this.form.spend = 80 * this.form.days }
    },
    handleimgSuccess(response, file, fileList) {
      this.form.photo = response.data
    },
    handleAdd() {   // add data
      this.form = {}  // clear data when add new data
      this.fromVisible = true   // open window
    },
    save() {
      this.form.userId = this.user.uid;
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request.post('/foster/add', this.form).then(res => {
            if (res.code === '200') {
              // update successful
              this.$message.success('AddSuccessful')
              this.selectAll()
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    selectAll() {
      this.$request.get('/foster/selectAllByUserId?userId=' + this.user.uid).then(res => {
        if (res.code === '200') {
          this.fosData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
  }
}

</script>
<style>
/*card*/
.card {
  width: 230px;
  height: 424px;
  padding: .8em;
  background: #f5f5f5;
  position: relative;
  overflow: visible;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
  margin-left: 75px;
  margin-bottom: 30px;
  margin-top: 20px;
  border-radius: 10px;
}

.card:hover {
  cursor: pointer;
  background-color: #74b9ff;
  color: #fff;
}

.card:hover .card-img {
  transform: translateY(-25%);
  box-shadow: rgba(226, 196, 63, 0.25) 0px 13px 47px -5px, rgba(180, 71, 71, 0.3) 0px 8px 16px -8px;
}

.card-img {
  /*background-color: #ffcaa6;*/
  height: 50%;
  width: 100%;
  border-radius: .5rem;
  transition: .3s ease;
}

.card-info {
  padding-top: 10%;
}

svg {
  width: 20px;
  height: 20px;
}

.card-footer {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-top: 1px solid #ddd;
}

/*Text*/
.text-title {
  font-weight: 900;
  font-size: 1.2em;
  line-height: 1.5;
}

.text-body {
  font-size: .9em;
  padding-bottom: 10px;
}

/*Button*/
.card-button {
  border: 1px solid #252525;
  display: flex;
  padding: .3em;
  cursor: pointer;
  border-radius: 50px;
  transition: .3s ease-in-out;
}

/*Hover*/
.card-img:hover {
  transform: translateY(-25%);
  box-shadow: rgba(226, 196, 63, 0.25) 0px 13px 47px -5px, rgba(180, 71, 71, 0.3) 0px 8px 16px -8px;
}

.card-button:hover {
  border: 1px solid #ffcaa6;
  background-color: #ffcaa6;
}

.item1 {
  flex: 0 0 calc(16.6666666667% - 10px);
  margin-right: 10px;
  background-color: #f0f0f0;
  padding: 10px;
  box-sizing: border-box;
}

.container {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
}
</style>
