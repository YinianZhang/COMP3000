<template>
  <div>
    <div class="search"style="margin-bottom: 20px">
      <el-input placeholder="Please enter a title to query" style="width: 200px" v-model="petName"></el-input>
      <el-button type="info"   style="margin-left: 10px" icon="el-icon-search" @click="findByPetName()">Search</el-button>
      <el-button type="warning"   style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>

    <div class="operation" style="margin-bottom: 10px">
      <el-button type="danger" icon="el-icon-minus" @click="delBatch"></el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe :header-cell-style="{background:'#D6E4FD',color:'#26304A'}" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="fid" label="fid" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="userName" label="owner" show-overflow-tooltip></el-table-column>
        <el-table-column prop="pet_name" label="petname" show-overflow-tooltip></el-table-column>
        <el-table-column label="photo">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px; border-radius: 25%" v-if="scope.row.photo"
                        :src="scope.row.photo" :preview-src-list="[scope.row.photo]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="ftime" label="time"></el-table-column>
        <el-table-column prop="days" label="days"></el-table-column>
        <el-table-column prop="service" label="service"></el-table-column>
        <el-table-column prop="status" label="status"></el-table-column>
        <el-table-column prop="room_id" label="room"></el-table-column>
        <el-table-column prop="spend" label="spend"></el-table-column>

        <el-table-column label="operation" width="180" align="center">
          <template v-slot="scope">
            <el-button   type="primary" size="mini" v-if="scope.row.status === 'WaitingFoster'" @click=fosteringSave(scope.row)>Confirm</el-button>
            <el-button   type="primary" size="mini" v-if="scope.row.status === 'Fostering'" @click=finishfosterSave(scope.row)>Finish</el-button>
            <el-button type="danger" icon="el-icon-delete" circle  @click="del(scope.row.fid)"></el-button>
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
    </div>
</template>

<script>
export default {
  name: "Foster",
  data() {
    return {
      petName:null,
      tableData: [],  // all data
      pageNum: 1,   //  current page
      pageSize: 10,  //  the number of per page
      total: 0,
      title: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        title: [
          {required: true, message: 'Please enter the title', trigger: 'blur'},
        ],
        content: [
          {required: true, message: 'Please enter the content', trigger: 'blur'},
        ]
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
    save() {   // The logic triggered by the save button will trigger adding or updating
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.fid ? '/foster/update' : '/foster/add',
            method: this.form.fid ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // indicates save successful
              this.$message.success('SaveSuccessful')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // indicates incorrect messages
            }
          })
        }
      })
    },
    fosteringSave(row){
      row.status = "Fostering"
      this.$request.put('/foster/update', row).then(res => {
        if (res.code === '200') {
          // update successful
          this.$message.success('FosteringSuccessful')
          this.load(1)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    finishfosterSave(row){
      row.status = "FinishFoster"
      this.$request.put('/foster/update', row).then(res => {
        if (res.code === '200') {
          // update successful
          this.$message.success('FinishFosterSuccessful')
          this.load(1)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    del(fid) {   // single delete
      this.$confirm('Are you sure you want to delete it?', 'Confirm delete', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(response => {
        this.$request.delete('/foster/delete/' + fid).then(res => {
          if (res.code === '200') {   // indicates successful operation
            this.$message.success('OperationSuccessful')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // indicates incorrect messages
          }
        })
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {   // All currently selected row data
      this.ids = rows.map(v => v.fid)   //  [1,2]
    },
    delBatch() {   // batch delete
      if (!this.ids.length) {
        this.$message.warning('Please select data')
        return
      }
      this.$confirm('Are you sure you want to delete these data?', 'Confirm delete', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(response => {
        this.$request.delete('/foster/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {   // indicates successful operation
            this.$message.success('OperationSuccessful')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // indicates incorrect messages
          }
        })
      }).catch(() => {
      })
    },
    findByPetName(){
      this.$request.get('/foster/selectByUserName?petName='+this.petName).then(res => {
        if (res.code === '200') {   // indicates successful operation
          this.tableData = res.data
        } else {
          this.$message.error(res.msg)  // indicates incorrect messages
        }
      })
    },
    load(pageNum) {  // paged search
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/foster/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          title: this.title,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.title = null
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
