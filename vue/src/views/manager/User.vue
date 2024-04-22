  <template>
  <div>
    <div class="search" style="margin-bottom: 20px;">
      <el-input placeholder="Please enter your account for search" style="width: 240px" v-model="username"></el-input>
      <el-button type="info"   style="margin-left: 10px" icon="el-icon-search" @click="load(1)">Search</el-button>
      <el-button type="warning"   style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>

    <div class="operation" style="margin-bottom: 10px">
      <el-button type="primary" icon="el-icon-plus" @click="handleAdd"></el-button>
      <el-button type="danger" icon="el-icon-minus" @click="delBatch"></el-button>
    </div>

    <div class="table" >
      <el-table :data="tableData"  stripe :header-cell-style="{background:'#D6E4FD',color:'#26304A'}" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="uid" label="uid" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="username" label="username"></el-table-column>
        <el-table-column prop="name" label="name"></el-table-column>
        <el-table-column prop="phone" label="telephone"></el-table-column>
        <el-table-column prop="email" label="email"></el-table-column>
        <el-table-column prop="role" label="role"></el-table-column>
        <el-table-column prop="wallet" label="wallet"></el-table-column>
        <el-table-column label="avatar">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px; border-radius: 25%" v-if="scope.row.avatar"
                        :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="role" label="role"></el-table-column>
        <el-table-column label="operation" align="center" width="150">
          <template v-slot="scope">
            <el-button type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row)"></el-button>
            <el-button type="danger" icon="el-icon-delete" circle  @click="del(scope.row.uid)"></el-button>
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
<!--add-->
    <el-dialog title="User" :visible.sync="fromVisible1" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="avatar">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">UploadAvatar</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="username" prop="username" >
          <el-input v-model="form.username" placeholder="username"></el-input>
        </el-form-item>
        <el-form-item label="name" prop="name">
          <el-input v-model="form.name" placeholder="name"></el-input>
        </el-form-item>
        <el-form-item label="telephone" prop="phone">
          <el-input v-model="form.phone" placeholder="telephone"></el-input>
        </el-form-item>
        <el-form-item label="email" prop="email">
          <el-input v-model="form.email" placeholder="email"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible1 = false">Return</el-button>
        <el-button type="primary" @click="save">Sure</el-button>
      </div>
    </el-dialog>
    <el-dialog title="User" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="avatar">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">UploadAvatar</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="username" prop="username" >
          <el-input v-model="form.username" disabled placeholder="username"></el-input>
        </el-form-item>
        <el-form-item label="name" prop="name">
          <el-input v-model="form.name" placeholder="name"></el-input>
        </el-form-item>
        <el-form-item label="telephone" prop="phone">
          <el-input v-model="form.phone" placeholder="telephone"></el-input>
        </el-form-item>
        <el-form-item label="email" prop="email">
          <el-input v-model="form.email" placeholder="email"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">Return</el-button>
        <el-button type="primary" @click="save">Sure</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "User",
  data() {
    var validateUsernameLength = (rule, value, callback) => {
      if (value.length < 3 || value.length > 10) {
        callback(new Error('The username length should be between 3-10'));
      } else {
        callback();
      }
    };
    return {
      tableData: [],  // All data
      pageNum: 1,   // Current page
      pageSize: 10,  // Number of displays per page
      total: 0,
      username: null,
      fromVisible: false,
      fromVisible1: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        username: [
          {required: true, message: 'Please enter your account number', trigger: 'blur'},
          {validator: validateUsernameLength, trigger: 'blur' }
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
      this.fromVisible1 = true   // open window
    },
    handleEdit(row) {   // edit data
      this.form = JSON.parse(JSON.stringify(row))  // Assign a value to the form object, pay attention to deep copying of data
      this.fromVisible = true   // open window
    },
    save() {   // save button logic, it will happen add or update
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.uid ? '/user/update' : '/user/add',
            method: this.form.uid ? 'POST':'POST',
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
      this.$confirm('Are you sure you want to delete it?', 'Confirm delete',  {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(response => {
        this.$request.delete('/user/delete/' + id).then(res => {
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
      this.$confirm('Are you sure you want to delete these data?', 'Confirm delete',  {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(response => {
        this.$request.delete('/user/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.get('/user/selectPage', {
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
.has-gutter{
  background-color: #2CBE56;
}

</style>