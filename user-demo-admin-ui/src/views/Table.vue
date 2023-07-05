<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchForm">
				<el-form-item label="Email">
					<el-input v-model="searchForm.email" placeholder="email"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getUsers">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;"
		 border>
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column prop="user_id" label="用户ID" width="100">
			</el-table-column>
			<el-table-column prop="email" label="email" width="200">
			</el-table-column>
			<el-table-column prop="first_name" label="名字" width="100">
			</el-table-column>
			<el-table-column prop="last_name" label="姓" width="100">
			</el-table-column>
			<el-table-column prop="birthdate" label="生日" width="120">
			</el-table-column>
			<el-table-column prop="gender" label="性别" min-width="80">
			</el-table-column>
			<el-table-column prop="zipcode" label="邮编" min-width="100">
			</el-table-column>
			<el-table-column prop="gmt_create" label="创建日期" show-overflow-tooltip>
			</el-table-column>
			<el-table-column prop="gmt_modified" label="修改日期" show-overflow-tooltip>
			</el-table-column>
			<el-table-column label="操作" width="200">
				<template scope="scope">
					
					<el-button type="danger" size="small" @click="handleDel(scope.row)">删除</el-button>
					<el-button size="small" @click="handleMail(scope.row)">查看邮件</el-button>
				</template>
			</el-table-column>
		</el-table>
	
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
			<el-pagination style="float:right;"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="searchForm.pn"
      :page-sizes="[10, 20, 100]"
      :page-size="searchForm.ps"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
		</el-col>

		<el-dialog title="Welcome Mail" :visible.sync="welcomeDiagVisible" 
      :close-on-click-modal="false" :close-on-press-escape="false"  @close="closeMailDiag" size=small>
      <el-form ref="passwordform" :model="mailContent" label-position="left" label-width="80px">
			<el-row>
				<div v-html="mailContent"></div>
			</el-row>
      <el-row>
        <el-col :span="8" :offset="16">
          <el-button type="primary" @click="welcomeDiagVisible=false">关闭</el-button>
        </el-col>
      </el-row>
      </el-form>
    </el-dialog>

	</section>
</template>

<script>
	// import util from '../common/js/util'
	//import NProgress from 'nprogress'
	import { listUsers, removeUser, getUserEmail } from '../api/api';

	export default {
		data() {
			return {
				searchForm: {
					email: '',
					deleted: false,
					pn: 1,
					ps: 10
				},
				users: [],
				total: 0,
				page: 1,
				listLoading: false,
				sels: [],//列表选中列,
				welcomeDiagVisible: false,
				mailContent:''
			}
		},
		methods: {

			handleMail(row){
				this.mailContent = ''
				this.welcomeDiagVisible = true
				getUserEmail({user_id: row.user_id}).then( rsp => {
					console.log(rsp)
					this.mailContent = rsp.data.content
				}).catch()

			},
			closeMailDiag(){
				this.welcomeDiagVisible = false
			},
			handleSizeChange(val) {
        this.searchForm.ps = val
        this.getUsers()
      },
      handleCurrentChange(val) {
        this.searchForm.pn = val
        this.getUsers()
      },
		
			//获取用户列表
			getUsers() {
				this.listLoading = true;
				//NProgress.start();
				listUsers(this.searchForm).then((res) => {
					this.total = res.data.total;
					this.users = res.data.users;
					this.listLoading = false;
					//NProgress.done();
				}).catch(err =>{
					this.listLoading = false;
					console.log(err)
					this.$message({
						message: '用户列表获取失败',
						type: 'error'
					})
				})
			},
			//删除
			handleDel: function (row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					removeUser(row.user_id).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getUsers();
					}).catch( err =>{
						this.$message({
							message: '删除失败',
							type: 'error'
						})
					})
				}).catch(() => {

				});
			},
			
			selsChange: function (sels) {
				this.sels = sels;
			},
			//批量删除
			batchRemove: function () {
				var ids = this.sels.map(item => item.user_id).toString();
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { ids: ids };
					console.log(ids);
					removeUser(ids).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getUsers();
					});
				}).catch(() => {

				});
			}
		},
		mounted() {
			this.getUsers()
		}
	}

</script>

<style scoped>

</style>
