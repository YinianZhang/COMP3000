<template>
  <div>
    <div class="table">
      <el-table :data="tableData" stripe :header-cell-style="{background:'#D6E4FD',color:'#26304A'}" @selection-change="handleSelectionChange">
        <el-table-column prop="id" label="id" width="70" align="center"></el-table-column>
        <el-table-column prop="username" label="username"></el-table-column>
        <el-table-column prop="name" label="name"></el-table-column>
        <el-table-column prop="phone" label="telephone"></el-table-column>
        <el-table-column prop="email" label="email"></el-table-column>
        <el-table-column label="avatar">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px; border-radius: 25%" v-if="scope.row.avatar"
                        :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="role" label="role"></el-table-column>
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
  name: "Admin",
  data() {
    return {
      tableData: [],  // All data
      pageNum: 1,   // Current page
      pageSize: 10,  // Number of displays per page
      total: 0,
      username: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        username: [
          {required: true, message: 'Please enter your account number', trigger: 'blur'},
        ]
      },
      ids: []
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    handleAdd() {   // add new data
      this.form = {}  // clear data when adding new data
      this.fromVisible = true   // open window
    },
    handleEdit(row) {   // edit data
      this.form = JSON.parse(JSON.stringify(row))  // Assign a value to the form object, pay attention to deep copying of data
      this.fromVisible = true   // open window
    },
    save() {   // save button logic, it will happen add or update
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/admin/update' : '/admin/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // indicates successful save
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
      this.$confirm('Are you sure you want to delete it?', 'confirm delete', {type: "warning"}).then(response => {
        this.$request.delete('/admin/delete/' + id).then(res => {
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
      this.ids = rows.map(v => v.id)
    },
    delBatch() {   // Batch delete
      if (!this.ids.length) {
        this.$message.warning('Please select data')
        return
      }
      this.$confirm('Are you sure you want to delete these data?', 'confirm delete', {type: "warning"}).then(response => {
        this.$request.delete('/admin/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.get('/admin/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.username = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    handleAvatarSuccess(response, file, fileList) {
      //Replace the avatar attribute with the link to the uploaded image
      this.form.avatar = response.data
    },
  }
}
</script>

<style scoped>

</style>