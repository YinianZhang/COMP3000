<template>
  <div>
    <el-card style="width: 50%">
      <el-form label-position="left" ref="formRef" :model="user" :rules="rules" label-width="150px" style="padding-right: 50px">
        <el-form-item label="OriginalPassword" prop="password">
          <el-input disabled v-model="user.password" placeholder="OriginalPassword" style=";width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="NewPassword" prop="newPassword">
<!--          <el-input show-password v-model="user.newPassword" placeholder="N      -->
              <password1 v-model="user.newPassword"
                :toggle="true"
                @score="showScore"
                @feedback="showFeedback" style="float: left;width: 600px;"/>
        </el-form-item>
        <el-form-item label="ConfirmPassword" prop="confirmPassword" >
          <el-input show-password v-model="user.confirmPassword" placeholder="ConfirmPassword" style=";width: 400px;"></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">ConfirmChang</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import Password1 from "vue-password-strength-meter";

export default {
  name: "Password",
  components: { Password1 },
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
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
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
      }
    }
  },
  created() {

  },
  methods: {
    showFeedback ({suggestions, warning}) {
    },
    showScore (score) {
      this.ifAdoptPassword = score;
    },
    update() {
      if (this.ifAdoptPassword <3){
        this.$message.error("Password strength not enough");
        return;
      }
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request.put('/updatePassword', this.user).then(res => {
            if (res.code === '200') {
              // update successful
              localStorage.removeItem('xm-user')   // Clear cached user data
              this.$message.success('ChangePasswordSuccessful')
              this.$router.push('/login')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
  }
}
</script>

<style scoped>
/deep/.el-form-item__label {
  font-weight: bold;
}
</style>