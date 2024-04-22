<template>
  <div class="main-content">
    <el-card style="width: 50%; margin: 30px auto">
      <el-form :model="user" label-width="80px" style="padding-right: 20px">
        <div style="margin: 15px; text-align: center">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="user.avatar" :src="user.avatar" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="username" prop="username">
          <el-input v-model="user.username" placeholder="username" disabled></el-input>
        </el-form-item>
        <el-form-item label="name" prop="name">
          <el-input v-model="user.name" placeholder="name"></el-input>
        </el-form-item>
        <el-form-item label="phone" prop="phone">
          <el-input v-model="user.phone" placeholder="phone"></el-input>
        </el-form-item>
        <el-form-item label="email" prop="email">
          <el-input v-model="user.email" placeholder="email"></el-input>
        </el-form-item>
        <el-form-item label="wallet" prop="wallet">
          <el-input v-model="user.wallet" disabled ></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <div style="text-align: center; margin-bottom: 20px">
            <el-button type="primary" @click="updatePassword">ChangePassword</el-button>
          </div>
          <el-button type="primary" @click="TurnToH">Return</el-button>
          <el-button type="primary" @click="update">Save</el-button>
          <el-button type="primary" @click="recharge">TopUp</el-button>
        </div>
      </el-form>
    </el-card>
    <el-dialog title="ChangePassword" :visible.sync="dialogVisible" width="30%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="user" label-width="200px" style="padding-right: 20px" :rules="rules" ref="formRef">
        <el-form-item label="OriginalPassword" prop="password" >
          <el-input disabled v-model="user.password" placeholder="OriginalPassword"></el-input>
        </el-form-item>
        <el-form-item label="NewPassword" prop="newPassword">
          <password2 v-model="user.newPassword"
                     :toggle="true"
                     @score="showScore"
                     @feedback="showFeedback" style="float: left;width: 310px;"/>
        </el-form-item>
        <el-form-item label="ConfirmNewPassword" prop="confirmPassword">
          <el-input show-password v-model="user.confirmPassword" placeholder="ConfirmNewPassword"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Sure</el-button>
      </div>
    </el-dialog>
<!--  Topup-->
    <el-dialog title="TopupData" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false"  destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item prop="account" label="Please enter the amount" label-width="200px">
          <el-input v-model="account" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">Cancel</el-button>
        <el-button type="primary" @click="pay">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Password2 from "vue-password-strength-meter";

export default {
  components: { Password2 },
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please confirm password'))
      } else if (value !== this.user.newPassword) {
        callback(new Error('Confirm password error'))
      } else {
        callback()
      }
    }
    return {
      ifAdoptPassword:null,
      account: null,
      fromVisible:false,
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      dialogVisible: false,
      rules: {
        password: [
          { required: true, message: 'Please enter the original password', trigger: 'blur' },
        ],
        newPassword: [
          { required: true, message: 'Please enter the new password', trigger: 'blur' },
        ],
        confirmPassword: [
          { validator: validatePassword, required: true, trigger: 'blur' },
        ],
      },
      rules1: {
        account: [
          {required: true, message: 'Please enter the amount', trigger: 'blur'},
        ]
      },
    }
  },
  created() {
    this.getPerson()
  },
  methods: {
    showFeedback ({suggestions, warning}) {
    },
    showScore (score) {
      this.ifAdoptPassword = score;
    },
    getPerson() {
      this.$request.get('/user/selectById/' + this.user.uid).then(res => {
        if (res.code === '200') {
          this.user = res.data
          localStorage.setItem('xm-user', JSON.stringify(this.user))
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    recharge() {
      this.account = 100
      this.fromVisible = true
    },
    pay() {
      this.user.wallet = parseFloat(this.account) + parseFloat(this.user.wallet)
      this.update()
      this.fromVisible = false
    },
    update() {

      // save current user data in database
      this.$request.post('/user/update', this.user).then(res => {
        if (res.code === '200') {
          // Successfully updated
          this.$message.success('SaveSuccessful')
          // update the user data in browser cache
          localStorage.setItem('xm-user', JSON.stringify(this.user))

          // trigger parent level data update
          this.$emit('update:user')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    TurnToH() {
      this.$router.push("/front/Uhome");
    },
    handleAvatarSuccess(response, file, fileList) {
      // replace the user avatar attribute to the link of upload photo
      this.$set(this.user, 'avatar', response.data)
    },
    // edit password
    updatePassword() {
      this.dialogVisible = true
    },
    save() {
      if (this.ifAdoptPassword <3){
        this.$message.error("Password strength not enough");
        return;
      }
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request.put('/updatePassword', this.user).then(res => {
            if (res.code === '200') {
              // update successful
              this.$message.success('ChangePasswordSuccessful')
              this.$router.push('/login')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
/deep/.el-form-item__label {
  font-weight: bold;
}
/deep/.el-upload {
  border-radius: 25%;
}
/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 25%;
}
/deep/.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
  border-radius: 25%;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 25%;
}
</style>