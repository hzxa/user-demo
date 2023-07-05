<template>
  <section>
  <el-form :model="usermodelform" label-position="left" label-width="80px" class="demo-ruleForm register-container">
    <h3 class="title">欢迎 {{usermodelform.first_name}}</h3>
    <el-form-item label="用户ID">
      <el-input type="text" v-model="usermodelform.user_id" :disabled="true"></el-input>
    </el-form-item>
    <el-form-item label="E-mail">
      <el-input type="text" v-model="usermodelform.email" :disabled="true"></el-input>
    </el-form-item>
    <el-form-item label="姓">
      <el-input type="text" v-model="usermodelform.last_name"></el-input>
    </el-form-item>
    <el-form-item label="名">
      <el-input type="text" v-model="usermodelform.first_name"></el-input>
    </el-form-item>
    <el-form-item label="出生日期">
      <el-input type="text" v-model="usermodelform.birthdate"></el-input>
    </el-form-item>
    <el-form-item label="性别">
      <el-select v-model="usermodelform.gender" placeholder="请选择">
        <el-option v-for="item in genders" :key="item" :label="item" :value="item"></el-option>
      </el-select>
      <!-- <el-input type="text" v-model="usermodelform.gender" auto-complete="off"></el-input> -->
    </el-form-item>
    <el-form-item label="邮政编码">
      <el-input type="text" v-model="usermodelform.zipcode" auto-complete="off"></el-input>
    </el-form-item>
    <el-form-item style="width:100%;">
      <el-button type="primary" @click.native.prevent="handleUpdateModel" :loading="logining">更新个人信息</el-button>
      <el-button @click.native.prevent="handleUpdatePassword">更新密码</el-button>
      <el-button @click.native.prevent="handleLogout">登出</el-button>
    </el-form-item>
  </el-form>
  <el-dialog title="修改密码" :visible.sync="passwordDiagVisible" 
      :close-on-click-modal="false" :close-on-press-escape="false"  @close="closeUpdatePassword" size=tiny>
      <el-form ref="passwordform" :model="passwordform" label-position="left" label-width="80px">
      <el-form-item label="旧密码">
        <el-input type="password" v-model="passwordform.old_password" required></el-input>
      </el-form-item>
      <el-form-item label="新密码">
        <el-input type="password" v-model="passwordform.new_password" required></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="8" :offset="16">
          <el-button type="primary" @click="submitUpdatePassword">确定</el-button>
          <el-button type="primary" @click="passwordDiagVisible=false">取消</el-button>
        </el-col>
      </el-row>
      </el-form>
    </el-dialog>
  </section>
</template>

<script>
  import { updateUserModel , updatePassword} from '../api/api';
  //import NProgress from 'nprogress'
  export default {
    data() {
      return {
        genders:[
          "male", "female", "other"
        ],
        logining: false,
        passwordDiagVisible: false,
        passwordform: {
          old_password: '',
          new_password: ''
        },
        usermodelform: {
          user_id: '',
          email: '',
          first_name: '',
          last_name: '',
          birthdate: '',
          gender: '',
          zipcode: ''
        },
        checked: true
      };
    },
    methods: {
      closeUpdatePassword(){
        this.passwordform.old_password = ''
        this.passwordform.new_password = ''
        this.passwordDiagVisible = false
      },
      handleUpdatePassword() {
        // this.$refs.ruleForm2.resetFields();
        this.passwordDiagVisible = true
      },
      handleLogout(){
        this.$router.push({ path: '/login' });
      },
      submitUpdatePassword(){
        var param = {
          user_id: this.usermodelform.user_id,
          old_password: this.passwordform.old_password,
          new_password: this.passwordform.new_password
        }
        updatePassword(param).then(rsp => {
          if(rsp.status == 200){
            if(rsp.data.code == 0){
              this.$message({
                message: '密码更新成功',
                type: 'info'
              })
              this.passwordDiagVisible = false
            }else{
              this.$message({
                message: rsp.data.msg,
                type: 'error'
              })
            }
            
          }else{
            this.$message({
                message: '更新失败',
                type: 'error'
              })

          }
        }).catch(err => {
          console.log(err)
           this.$message({
                message: '更新失败,请重新登录',
                type: 'error'
              })
           this.$router.push({ path: '/login' })
        })


      },
      handleUpdateModel(ev) {
        var _this = this;
        var param = {
          user_id: this.usermodelform.user_id,
          first_name: this.usermodelform.first_name,
          last_name: this.usermodelform.last_name,
          birthdate: this.usermodelform.birthdate,
          gender: this.usermodelform.gender,
          zipcode: this.usermodelform.zipcode
        }

        this.$confirm('确认更新个人信息吗?', '提示', {
					type: 'warning'
				}).then(() => {
          updateUserModel(param).then(rsp => {
            if(rsp.status !== 200){
              this.$message({
                    message: rsp.error,
                    type: 'error'
                  })
            }else{
              this.$message({
                message: '更新成功',
                type: 'info'
              });
              sessionStorage.setItem('user', JSON.stringify(this.usermodelform));
            }
          }
          ).catch(err => {
            this.$message({
                    message: '更新失败，请重新登录',
                    type: 'error'
                  })
            this.$router.push({ path: '/login' })
          })
				}).catch(() => {

				});

        
      }
    },
    mounted() {
			var user = sessionStorage.getItem('user');
			if (user) {
				user = JSON.parse(user);
        console.log(user)
				this.usermodelform = user
				// this.sysUserAvatar = user.avatar || '';
			}else{
        console.log("pls login")
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
