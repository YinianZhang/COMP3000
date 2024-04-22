<template>
  <div>
    <div class="search" style="margin-bottom: 20px">
      <el-input placeholder="Please enter a title to query" style="width: 240px" v-model="title"></el-input>
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
        <el-table-column prop="nid" label="nid" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="title" label="title" show-overflow-tooltip></el-table-column>
        <el-table-column prop="content" label="content" show-overflow-tooltip></el-table-column>
        <el-table-column prop="time" label="time"></el-table-column>
        <el-table-column prop="user" label="user"></el-table-column>

        <el-table-column label="operation" width="180" align="center">
          <template v-slot="scope">
            <el-button type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row)"></el-button>
            <el-button type="danger" icon="el-icon-delete" circle  @click="del(scope.row.nid)"></el-button>
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
  name: "Notice",
  data() {
    return {
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
            url: this.form.nid ? '/notice/update' : '/notice/add',
            method: this.form.nid ? 'PUT' : 'POST',
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
    del(id) {   // single delete
      this.$confirm('Are you sure you want to delete it?', 'Confirm delete', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(response => {
        this.$request.delete('/notice/delete/' + id).then(res => {
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
      this.ids = rows.map(v => v.nid)   //  [1,2]
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
        this.$request.delete('/notice/delete/batch', {data: this.ids}).then(res => {
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
    load(pageNum) {  // paged search
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/notice/selectPage', {
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
