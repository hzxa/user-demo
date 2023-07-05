<template>
  <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="80px" class="demo-ruleForm register-container">
    <h3 class="title">欢迎注册此系统</h3>
    <el-form-item label="E-mail" prop="email" required>
      <el-input type="text" v-model="ruleForm2.email" auto-complete="off" placeholder="邮箱地址"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password" required>
      <el-input type="password" v-model="ruleForm2.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item label="姓">
      <el-input type="text" v-model="ruleForm2.last_name" auto-complete="off" placeholder="姓"></el-input>
    </el-form-item>
    <el-form-item label="名">
      <el-input type="text" v-model="ruleForm2.first_name" auto-complete="off" placeholder="名"></el-input>
    </el-form-item>
    <el-form-item label="出生日期">
      <el-input type="text" v-model="ruleForm2.birthdate" auto-complete="off" placeholder="20230101"></el-input>
    </el-form-item>
    <el-form-item label="性别">
      <el-select v-model="ruleForm2.gender" placeholder="请选择">
        <el-option v-for="item in genders" :key="item" :label="item" :value="item"></el-option>
      </el-select>
      <!-- <el-input type="text" v-model="ruleForm2.gender" auto-complete="off" placeholder="male"></el-input> -->
    </el-form-item>
    <el-form-item label="邮政编码">
      <el-input type="text" v-model="ruleForm2.zipcode" auto-complete="off" placeholder="1000000"></el-input>
    </el-form-item>
    <el-form-item style="width:100%;">
      <el-button type="primary" @click.native.prevent="handleSubmit2" :loading="logining">注册</el-button>
      <el-button @click.native.prevent="handleReset2">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import { requestRegister, checkEmail } from '../api/api';
  //import NProgress from 'nprogress'
  export default {
    data() {
      var validateEmailFormat = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入Email'))
      } else {
        var emailRegExp=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;//验证邮箱的正则表达式
				var ok=emailRegExp.test(value);//验证是否符合要求
        if(!ok){
          callback(new Error("format invalid"))
        }else{
          checkEmail({ email: value }).then((response) => {
            console.log(response)
          const data = response.data
          if (data.code !== 0) {
            callback(new Error(data.msg))
          }else{
            callback()
          }
        }, () => {
          callback()
        })
        }
      }
    }
      return {
        genders:[
          "male", "female", "other"
        ],
        logining: false,
        ruleForm2: {
          email: '',
          password: '',
          first_name: '',
          last_name: '',
          birthdate: '',
          gender: '',
          zipcode: ''
        },
        rules2: {
          email: [
            { required: true, message: '请输入Email地址', trigger: 'blur' },
            { validator: validateEmailFormat }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            //{ validator: validaePass2 }
          ]
        },
        checked: true
      };
    },
    methods: {
      handleReset2() {
        this.$refs.ruleForm2.resetFields();
      },
      handleSubmit2(ev) {
        var _this = this;
        this.$refs.ruleForm2.validate((valid) => {
          if (valid) {
            //_this.$router.replace('/table');
            this.logining = true;
            //NProgress.start();
            var loginParams = { username: this.ruleForm2.account, password: this.ruleForm2.checkPass };
            requestRegister(this.ruleForm2).then(rsp => {
              this.logining = false;
              if (rsp.status !== 200) {
                this.$message({
                  message: rsp.error,
                  type: 'error'
                });
              } else {
                let user = rsp.data
                sessionStorage.setItem('user', JSON.stringify(user));
                this.$router.push({ path: '/usermodel' });
              }
            }).catch(err =>{
              this.logining = false
              this.$message({
                  message: '登录失败',
                  type: 'error'
                });
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }

</script>

<style lang="scss" scoped>
  .register-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 400px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
</style>
