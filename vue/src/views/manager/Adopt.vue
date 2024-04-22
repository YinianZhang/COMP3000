<template>
  <div>
    <div class="search" style="margin-bottom: 20px">
      <el-form ref="form" :model="choseStatus" label-width="80px" style="float: left">
        <el-form-item label="Status">
          <el-select v-model="choseStatus" placeholder="Please select status" @change="getChoseStatus">
            <el-option label="Adopted" value="Adopted"></el-option>
            <el-option label="WaitingAdoption" value="WaitingAdoption"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <el-button type="warning"   style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe :header-cell-style="{background:'#D6E4FD',color:'#26304A'}"  @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="aid" label="aid" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="petName" label="PetName" show-overflow-tooltip></el-table-column>
        <el-table-column prop="userName" label="Owner" show-overflow-tooltip></el-table-column>
        <el-table-column label="Image">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px; border-radius: 25%" v-if="scope.row.petImg"
                        :src="scope.row.petImg" :preview-src-list="[scope.row.petImg]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="atime" label="time"></el-table-column>
        <el-table-column prop="astatus" label="status"></el-table-column>

        <el-table-column label="operation" width="180" align="center">
          <template v-slot="scope">
            <el-button   type="danger" size="mini" :disabled="scope.row.astatus === 'WaitingAdoption'" @click=cancel(scope.row)>GiveUp</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>


    <el-dialog title="Data" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="title" label="title">
          <el-input v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="content" label="content">
          <el-input type="textarea" :rows="5" v-model="form.content" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Sure</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "Adopt",
  data() {
    return {
      choseStatus:null,
      tableData: [],  // all data
      pageNum: 1,   //  current page
      pageSize: 10,  //  the number of per page
      total: 0,
      title: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        astatus: [
          {required: true, message: 'Please enter the status', trigger: 'blur'},
        ],
      },
      ids: []
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    handleAdd() {   // add data
      this.form = {}  // clear data when add new data
      this.fromVisible = true   // open window
    },
    handleEdit(row) {   // edit data
      this.form = JSON.parse(JSON.stringify(row))  // Assigning values to the form object requires deep copying of data
      this.fromVisible = true   // open window
    },
    getChoseStatus(){
      this.form.astatus = this.choseStatus;
      this.$request.get('/adopt/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          astatus: this.choseStatus
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    save() {   // The logic triggered by the save button will trigger adding or updating
      console.log(this.form)
      this.form.astatus=this.form.status;
      this.$request.put(
          // url: this.form.aid ? '/adopt/update',
          // method: this.form.aid ? 'POST',
          // data: this.form
          "/adopt/update",this.form
      ).then(res => {
        if (res.code === '200') {  // indicates save successful
          this.$message.success('SaveSuccessful')
          this.load(1)
          this.fromVisible = false
        } else {
          this.$message.error(res.msg)  // indicates incorrect messages
        }
      })
    },
    cancel(row) {
      this.$confirm('Are you sure you want to give up it?', 'Confirm Warning', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(response => {
        // alert('111')
        this.form = JSON.parse(JSON.stringify(row))
        this.form.status = "WaitingAdoption"
        this.save();
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {   // All currently selected row data
      this.ids = rows.map(v => v.aid)   //  [1,2]
    },
    load(pageNum) {  // paged search
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/adopt/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.choseStatus = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
  }
}
</script>

<style scoped>

</style>
