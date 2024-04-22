<template>
  <div>
    <div class="search" style="margin-bottom: 20px">
      <el-input placeholder="Please enter goods name query" style="width: 240px" v-model="name"></el-input>
      <el-button type="info"   style="margin-left: 10px" icon="el-icon-search" @click="load(1)">Search</el-button>
      <el-button type="warning"   style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>

    <div class="operation" style="margin-bottom: 10px">
      <el-button type="primary" icon="el-icon-plus" @click="handleAdd"></el-button>
      <el-button type="danger" icon="el-icon-minus" @click="delBatch"></el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe :header-cell-style="{background:'#D6E4FD',color:'#26304A'}"  @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="gid" label="gid" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="name" show-overflow-tooltip></el-table-column>
        <el-table-column prop="gimg" label="image" show-overflow-tooltip>
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px" v-if="scope.row.gimg"
                        :src="scope.row.gimg" :preview-src-list="[scope.row.gimg]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="price"></el-table-column>
        <el-table-column prop="numb" label="number"></el-table-column>

        <el-table-column label="operation" width="180" align="center">
          <template v-slot="scope">
            <el-button type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row)"></el-button>
            <el-button type="danger" icon="el-icon-delete" circle  @click="del(scope.row.gid)"></el-button>
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


    <el-dialog title="GoodsData" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="150px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="img" label="GoodsImage">
          <el-upload
              class="image-uploader"
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              list-type="picture"
              :on-success="handleimgSuccess"
          >
            <el-button type="primary">Uploadimage</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="name" label="GoodsName">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="price" label="GoodsPrice">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="numb" label="RemainderNumber">
          <el-input v-model="form.numb" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Confirm</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "Goods",
  data() {
    return {
      name:null,
      tableData: [],  // all data
      pageNum: 1,   //  current page
      pageSize: 10,  //  the number of per page
      total: 0,
      title: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          {required: true, message: 'Please enter the goods name', trigger: 'blur'},
        ],
        price: [
          {required: true, message: 'Please enter the goods price', trigger: 'blur'},
        ],
        numb: [
          {required: true, message: 'Please enter the goods number', trigger: 'blur'},
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
    save() {   // The logic triggered by the save button will trigger adding or updating
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.gid ? '/goods/update' : '/goods/add',
            method: this.form.gid ? 'PUT' : 'POST',
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
    del(gid) {   // single delete
      this.$confirm('Are you sure you want to delete it?', 'Confirm delete', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(response => {
        this.$request.delete('/goods/delete/' + gid).then(res => {
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
      this.ids = rows.map(v => v.gid)   //  [1,2]
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
        this.$request.delete('/goods/delete/batch', {data: this.ids}).then(res => {
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
    handleimgSuccess(response, file, fileList) {
      this.form.gimg = response.data
    },
  }
}
</script>

<style scoped>

</style>
