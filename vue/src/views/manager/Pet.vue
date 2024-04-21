<template>
  <div>
    <div class="search" style="margin-bottom: 20px">
      <el-input placeholder="Please enter your pet name" style="width: 200px" v-model="name"></el-input>
      <el-button type="info"   style="margin-left: 10px"  icon="el-icon-search" @click="load(1)">Search</el-button>
      <el-button type="warning"   style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>

    <div class="operation" style="margin-bottom: 10px">
      <el-button type="primary" icon="el-icon-plus" @click="handleAdd"></el-button>
      <el-button type="danger" icon="el-icon-minus" @click="delBatch"></el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe :header-cell-style="{background:'#D6E4FD',color:'#26304A'}" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="pid" label="pid" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="img" label="img" show-overflow-tooltip>
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px; border-radius: 25%" v-if="scope.row.img"
                        :src="scope.row.img" :preview-src-list="[scope.row.img]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="name" show-overflow-tooltip></el-table-column>
        <el-table-column prop="type" label="type"></el-table-column>
        <el-table-column prop="sex" label="sex"></el-table-column>
        <el-table-column prop="age" label="age"></el-table-column>
        <el-table-column prop="status" label="status"></el-table-column>
        <el-table-column prop="intro" label="introduction"></el-table-column>

        <el-table-column label="operation" width="180" align="center">
          <template v-slot="scope">
            <el-button type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row)"></el-button>
            <el-button type="danger" icon="el-icon-delete" circle  @click="del(scope.row.pid)"></el-button>
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


    <el-dialog title="Pet" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item label="img">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: pet.token }"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">Uploadimage</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="name" label="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="sex">
          <el-select v-model="form.sex" placeholder="Please select sex" style="width: 100%">
            <el-option label="male" value="male"></el-option>
            <el-option label="female" value="female"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="age" label="age">
          <el-input v-model="form.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="type" label="type">
          <el-select v-model="form.type" placeholder="Please select type" style="width: 100%">
            <el-option label="CAT" value="CAT"></el-option>
            <el-option label="DOG" value="DOG"></el-option>
            <el-option label="OTHERS" value="OTHERS"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="status" label="status">
          <el-select v-model="form.status" placeholder="Please select status" style="width: 100%">
            <el-option label="WaitingAdoption" value="WaitingAdoption"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="intro" label="introduction">
          <el-input type="textarea" :rows="4" v-model="form.intro" autocomplete="off"></el-input>
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
  name: "Pet",
  data() {
    return {
      tableData: [],  // All data
      pageNum: 1,   // Current page
      pageSize: 10,  // Number of displays per page
      total: 0,
      name: null,
      fromVisible: false,
      form: {},
      pet: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          {required: true, message: 'Please enter your pet name', trigger: 'blur'},
        ]
      },
      ids: [],
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
            url: this.form.pid ? '/pet/update' : '/pet/add',
            method: this.form.pid ? 'PUT' : 'POST',
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
    del(pid) {   // single delete
      this.$confirm('Are you sure you want to delete it?', 'Confirm delete',{
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(response => {
        this.$request.delete('/pet/delete/' + pid).then(res => {
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
      this.ids = rows.map(v => v.pid)
    },
    delBatch() {   // Batch delete
      if (!this.ids.length) {
        this.$message.warning('Please select data')
        return
      }
      this.$confirm('Are you sure you want to delete these data?', 'Confirm delete', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(response => {
        this.$request.delete('/pet/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.get('/pet/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data?.list
        this.total = res.data?.total
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
      //Replace the avatar attribute with the link to the uploaded image
      this.form.img = response.data
    },
  }
}
</script>

<style scoped>

</style>