<template>
  <div class="container">
    <div style="width: 400px; padding: 30px; background-color: white; border-radius: 5px;">
      <div style="text-align: center; font-size: 20px; margin-bottom: 20px; color: #333">WelcomeLogin</div>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user" placeholder="Please enter your username" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" placeholder="Please enter your password" show-password  v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="form.role" placeholder="Please select the role" style="width: 100%">
            <el-option label="ADMIN" value="ADMIN"></el-option>
            <el-option label="USER" value="USER"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%; background-color: #333; border-color: #333; color: white;margin-bottom: 15px" @click="login">Login</el-button>
          <el-button style="width: 100%; background-color: #333; border-color: #333; color: white;margin-left: 0px" @click="goToRegister">Register</el-button>
        </el-form-item>
        </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  components: {},

  data() {
    return {
      form: {role:'ADMIN'},
      rules: {
        username: [
          { required: true, message: 'Please enter your username', trigger: 'blur' },
        ],
        password: [
          { required: true, message: 'Please enter your password', trigger: 'blur' },
        ]
      }
    }
  },
  created() {

  },
  methods: {
    login() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          // Verification passed
          this.$request.post('/login', this.form).then(res => {
            if (res.code === '200') {
              localStorage.setItem("xm-user", JSON.stringify(res.data))  // Store the user data
              if(res.data.role=="USER"){
                this.$router.push('/front/Uhome')  // Navigate to the homepage

              }else{
                // alert(111)
                this.$router.push('/home')  // Navigate to the homepage

              }
              this.$message.success('Login successful')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    goToRegister() {
      this.$router.push('/register');
    }
  }
}



</script>

<style scoped>
.container {
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/imgs/bg.jpg");
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