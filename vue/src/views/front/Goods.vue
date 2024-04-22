<template>
  <div>
    <img src="http://localhost:9090/files/goods-b.jpg" style="width: 100%; height: 400px">
    <br>
    <div class="search" style="margin-left: 750px;margin-right: 20px">
      <el-input placeholder="Please enter the goods name" style="width: 300px" v-model="name"></el-input>
      <el-button type="info"    style="margin-left: 10px" icon="el-icon-search" @click="load(1)">Search</el-button>
      <el-button type="warning"    style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>
    <br>
    <div class="table" style="margin-left: 50px;margin-right: 20px">
      <div style="padding: 10px 15px">
        <el-row>
          <el-col :span="6" v-for="item in tableData" style="margin-bottom: 20px">
            <el-card shadow="hover" style="margin-right: 20px;cursor: pointer;" :body-style="{ padding: '20px' }">
              <div style="display: flex">
                <div style="flex: 1; padding-right: 5px">
                  <img :src="item.gimg" alt=""
                    style="width: 100%; height: 155px; border-radius: 10px; border: 1px solid #cccccc">
                </div>
                <div style="flex: 1; padding-left: 5px">
                  <div style="font-size: 15px; font-weight: 550; color: #817a70">{{ item.name }}</div>
                  <div style="margin-top: 5px; font-weight: 600; font-size: 20px; color: red">
                    <el-tag type="danger" size="mini" effect="dark">
                      £ {{ item.price }}
                    </el-tag>

                  </div>
                  <div style="margin-top: 5px; font-size: 13px; color: #4b4949">RemainingNumber：{{ item.numb }}</div>
                  <div style="margin-top: 10px">
                    <el-input-number v-model="item.tmpNum" :precision="0" :min="1" :max="item.numb"
                      @change="changeVal($event)"></el-input-number>
                  </div>
                  <div style="margin-top: 15px">
                    <el-button type="warning" size="mini" :disabled="item.numb === 0" @click="handleEdit(item)">
                      <i class="el-icon-goods"></i>Buy</el-button>
                  </div>
                </div>
              </div>
            </el-card>

          </el-col>
        </el-row>
      </div>
      <div class="pagination" style="text-align: center;margin-top: 40px;">
        <el-pagination background @current-change="handleCurrentChange" :current-page="pageNum"
          :page-sizes="[5, 10, 20]" :page-size="pageSize" layout="total, prev, pager, next" :total="total">
        </el-pagination>
      </div>
    </div>
    <!--Order page-->
    <el-dialog title="ReceivingData" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false"
      destroy-on-close>
      <el-form label-width="150px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username" label="Recipient">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="ContactPhone">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="address" label="DeliveryAddress">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">Cancel</el-button>
        <el-button type="primary" @click="buy">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Goods",
  data() {
    return {
      tableData: [],  // All data
      pageNum: 1,   // Current page number
      pageSize: 12,  // Number of items per page
      total: 0,
      name: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        username:[
          { required: true, message: 'Please enter the name', trigger: 'blur' },
        ],
        phone:[
          { required: true, message: 'Please enter the phone', trigger: 'blur' },
        ],
        address:[
          { required: true, message: 'Please enter the address', trigger: 'blur' },
        ],
      },
      ids: []
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    changeVal() {
      this.$forceUpdate();
    },
    buy() {
      let data = {
        userId: this.user.uid,
        goodsId: this.form.gid,
        username: this.form.username,
        phone: this.form.phone,
        address: this.form.address,
        num: this.form.tmpNum,
        status: 'WaitingDelivery',
        price: parseFloat(this.form.tmpNum) * parseFloat(this.form.price)
      }
      // console.log(data);
      this.$request.post('/orders/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success('BuySuccessful')
          this.fromVisible = false
          this.load(1)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAdd() {   // Add data
      this.form = {}  // Clear data when adding new data
      this.fromVisible = true   // Open window
    },
    handleEdit(row) {   // Edit data
      if (row.tmpNum > row.numb) {
        this.$message.error("StockInsufficient ")
        return;
      }
      this.form = JSON.parse(JSON.stringify(row))  // Assign value to form object, remember to deep clone the data
      this.fromVisible = true   // Open window
    },
    save() {   // Logic triggered by the save button, it will trigger add or update
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.gid ? '/goods/update' : '/goods/add',
            method: this.form.gid ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // Success in saving
              this.$message.success('SaveSuccessful')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // Display error message
            }
          })
        }
      })
    },
    handleSelectionChange(rows) {   // All currently selected row data
      this.ids = rows.map(v => v.gid)   //  [1,2]
    },
    load(pageNum) {  // Page query
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/goods/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
        // Set initial quantity to 1
        this.tableData.forEach(res => { res.tmpNum = 1 })
      })
    },
    reset() {
      this.name = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    handleAvatarSuccess(response, file, fileList) {
      this.form.gimg = response.data
    },
  }
}

</script>

<style scoped></style>
