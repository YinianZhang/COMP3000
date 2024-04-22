<template>
  <div class="container">
    <div style="width: 400px; padding: 30px; background-color: white; border-radius: 5px;">
      <div style="text-align: center; font-size: 20px; margin-bottom: 20px; color: #333">WelcomeRegister</div>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user" placeholder="Please enter your username" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
            <password v-model="form.password"
                      :toggle="true"
                      @score="showScore"
                      @feedback="showFeedback"/>
        </el-form-item>

        <el-form-item prop="confirmPass">
          <el-input prefix-icon="el-icon-lock" placeholder="Please confirm your password" show-password  v-model="form.confirmPass"></el-input>
        </el-form-item>
        <el-form-item prop="name">
          <el-input prefix-icon="el-icon-user" placeholder="Please enter your name" v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="form.role" placeholder="Please select the role" style="width: 100%">
            <el-option label="USER" value="USER"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input prefix-icon="el-icon-user" placeholder="Please enter your phone" v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input prefix-icon="el-icon-user" placeholder="Please enter your email" v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%; background-color: #333; border-color: #333; color: white" @click="register">Register</el-button>
          <el-button style="width: 100%; background-color: #333; border-color: #333; color: white;margin-left: 0px;margin-top: 15px" @click="goToLogin">Login</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import Password from 'vue-password-strength-meter'
export default {
  name: "Register",
  components: { Password },
  data() {
    var validateUsernameLength = (rule, value, callback) => {
      if (value.length < 3 || value.length > 10) {
        callback(new Error('The username length should be between 3-10'));
      } else {
        callback();
      }
    };
    // Verification code verification
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('Please confirm your password'))
      } else if (confirmPass !== this.form.password) {
        callback(new Error('The two passwords entered are not match'))
      } else {
        callback()
      }
    }
    return {
      ifAdoptPassword:null,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        username: [
          { required: true, message: 'Please enter your username', trigger: 'blur' },
          { validator: validateUsernameLength, trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please enter your password', trigger: 'blur' },
        ],
        confirmPass: [
          { validator: validatePassword, trigger: 'blur' }
        ]
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
    register() {
      if (this.ifAdoptPassword <3){
        this.$message.error("Password strength not enough");
        return;
      }
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          // Verification passed
          this.$request.post('/register', this.form).then(res => {
            if (res.code === '200') {
              this.$router.push('/login')  // turn to login
              this.$message.success('RegisterSuccessful')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    goToLogin() {
      this.$router.push('/login');
    }
  }
}
</script>

<style scoped>
.container {
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/imgs/bg1.jpg");
  background-size: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
}
a {
  color: #2a60c9;
}
</style>