<template>
  <a-row style="height: 100%;">
    <a-col :span="6">
      <b-card title="部门信息">
        <org-tree ref="org" @selectNode="selectNode"></org-tree>
      </b-card>
    </a-col>
    <a-col :span="18">
      <b-card title="用户信息">
        <!--操作按钮       -->
        <b-table-header>
          <a-button type="primary" @click="showAdd">新增</a-button>
          <a-button type="primary" @click="showUpdate">修改</a-button>
          <a-button type="primary" @click="delUser">删除</a-button>
          <a-button type="primary" @click="showSetRole">授予角色</a-button>
          <a-button type="primary" @click="setPassword">重置密码</a-button>
        </b-table-header>
        <!--用户数据表        -->
        <b-data-table ref="userTable" :columns="[
        {title: '登录名',dataIndex: 'loginName',},
        {title: '姓名',dataIndex: 'userName',},
        {title: '性别',dataIndex: 'sex',customRender:function({text}){if(text=='1'){return '男';}else{return '女';}}},
        {title: '邮箱',dataIndex: 'email',},
        {title: '电话',dataIndex: 'phone',},
        ]" :type="'radio'" @rowChange="userChange"  :data="userData"></b-data-table>
      </b-card>
    </a-col>
  </a-row>
  <!--  添加和修改表单-->
  <a-modal v-model:visible="addAndUpdateVisible"  width="700px" title="用户信息" @ok="addAndUpdate">
    <a-form ref="form" :model="userInfo" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }" :rules="{
              loginName: [{ required: true, message: '请输入登录名', trigger: 'blur' }],
              userName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
              }">
      <a-form-item name="loginName" label="账号">
        <a-input v-model:value="userInfo.loginName" />
      </a-form-item>
      <a-form-item name="userName" label="姓名">
        <a-input v-model:value="userInfo.userName" />
      </a-form-item>
      <a-form-item name="sex" label="性别">
        <a-radio-group v-model:value="userInfo.sex">
          <a-radio :value="1">男</a-radio>
          <a-radio :value="2">女</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item name="email" label="邮箱">
        <a-auto-complete v-model:value="userInfo.email"/>
      </a-form-item>
      <a-form-item name="phone" label="手机">
        <a-input v-model:value="userInfo.phone" />
      </a-form-item>
    </a-form>
  </a-modal>
  <!--  授予角色-->
  <a-modal v-model:visible="setRoleVisible"  width="700px" title="用户信息" @ok="setRole">
    <a-transfer show-search :render="item => item.title" style="text-align: center;"
                :data-source="roles"
                :target-keys="roleSelect"
                @change="roleChange"></a-transfer>
  </a-modal>
</template>

<script>
import OrgTree from "@/views/frame/components/OrgTree";
import BTableHeader from "../components/BTableHeader";
export default {
  name: "User",
  components:{
    BTableHeader,
    OrgTree
  },
  methods:{
    /**
     * 树节点选择
     * @param node
     */
    selectNode(node){
      this.treeNode=node;
      const param={}
      param.orgId=node.id;
      this.axios.post("/proxyService/frameService/getUserByOrg",param).then((res)=>{
        if(res.rtnCode==200){
          this.userData=res.data;
        }
      })
    },
    /**
     * user选择
     * @param selectedRowKeys
     * @param selectedRows
     */
    userChange(selectedRowKeys, selectedRows){
      this.userInfo=selectedRows[0]
    },
    /**
     * 展示添加窗口
     */
    showAdd(){
      if(this.treeNode){
        this.addOrUpdate=1;
        this.addAndUpdateVisible=true;
        this.userInfo={
          id:'',
          loginName:'',
          userName:'',
          sex:'1',
          email:'',
          phone:'',
        }
        this.$refs.userTable.cleanSlect();
      }else{
        this.$message.warning('请选择部门');
      }
    },
    /**
     * 展示修改窗口
     */
    showUpdate(){
      this.addOrUpdate=2;
      this.addAndUpdateVisible=true;
    },
    /**
     * 添加或修改
     */
    addAndUpdate(){
      //表单验证
      this.$refs.form.validate().then(() => {
        if(this.addOrUpdate==1){
          const param={};
          param.user=this.userInfo;
          param.orgId=this.treeNode.id;
          this.axios.post("/proxyService/frameService/saveOrUpdateUser",param).then((res)=>{
            if(res.rtnCode==200){
              this.userData=res.data;
              this.addAndUpdateVisible=false;
              this.selectNode(this.treeNode);
            }
          })
        }else if(this.addOrUpdate==2){
          const param={};
          param.user=this.userInfo;
          console.log(param)
          this.axios.post("/proxyService/frameService/saveOrUpdateUser",param).then((res)=>{
            if(res.rtnCode==200){
              this.userData=res.data;
              this.addAndUpdateVisible=false;
              this.selectNode(this.treeNode);
            }
          })
        }
      })
    },
    /**
     * 删除user
     */
    delUser(){
      if(this.userInfo.id!=null){
        //删除确认窗口
        this.$confirm({
          title: '提示',
          content: '确定删除？', okText: '删除', cancelText: '取消', okType: 'danger',
          onOk:()=> {
            const param={};
            param.id=this.userInfo.id;
            this.axios.post("/proxyService/frameService/delUser",param).then((res)=>{
              if(res.rtnCode==200){
                this.selectNode(this.treeNode)
              }
            })
          },
          onCancel:()=> {

          },
        });
      }else{
        this.$message.error("请选择用户")
      }
    },
    showSetRole(){
      if(this.userInfo.id){
        const param={};
        param.userId=this.userInfo.id;
        param.orgId=this.treeNode.id;
        this.axios.post("/proxyService/frameService/queryRoleByOrg",param).then((res)=>{
          if(res.rtnCode==200){
            console.log(res.data.roles,res.data.roleSelect)
            this.roles=res.data.roles;
            this.roleSelect=this.global.methods.objsToArr(res.data.roleSelect,"id");
          }
        })
        this.setRoleVisible=true;
      }else{
        this.$message.warning('请选择用户');
      }
    },
    setRole(){
      const param={};
      param.userId=this.userInfo.id;
      param.roleSelect=this.roleSelect;
      this.axios.post("/proxyService/frameService/saveUserRole",param).then((res)=>{
        if(res.rtnCode==200){
          this.setRoleVisible=false;
        }
      })
    },
    roleChange(keys){
      this.roleSelect=keys;
    },
    setPassword(){
      if(this.userInfo.id!=null){
        //删除确认窗口
        this.$confirm({
          title: '提示',
          content: '确定重置密码？', okText: '确认', cancelText: '取消', okType: 'danger',
          onOk:()=> {
            const param={};
            param.userId=this.userInfo.id;
            this.axios.post("/proxyService/frameService/updatePassword",param).then((res)=>{
              if(res.rtnCode==200){
                this.$message.success("重置成功");
              }
            })
          },
          onCancel:()=> {

          },
        });
      }else{
        this.$message.error("请选择用户")
      }

    }
  },
  data(){
    return{
      treeNode:null,
      userData:null,
      userInfo:{
        id:null,
        loginName:null,
        userName:null,
        sex:'1',
        email:null,
        phone:null,
      },
      addOrUpdate:0,
      addAndUpdateVisible:false,
      setRoleVisible:false,
      roles:[],
      roleSelect:[],
    }
  },
}
</script>

<style scoped>

</style>