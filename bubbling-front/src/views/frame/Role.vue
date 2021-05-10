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
          <a-button type="primary" @click="showSetFunc">授予权限</a-button>
        </b-table-header>
        <!--用户数据表        -->
        <b-data-table ref="userTable" :columns="[
        {title: 'id',dataIndex: 'id',},
        {title: '角色名称',dataIndex: 'roleName',},
        ]" :type="'radio'" @rowChange="userChange"  :data="roleData"></b-data-table>
      </b-card>
    </a-col>
  </a-row>
  <!--  添加和修改表单-->
  <a-modal v-model:visible="addAndUpdateVisible"  width="700px" title="用户信息" @ok="addAndUpdate">
    <a-form ref="form" :model="roleInfo" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }" :rules="{
              roleName: [{ required: true, message: '请输入角色名', trigger: 'blur' }],
              }">
      <a-form-item name="roleName" label="角色名">
        <a-input v-model:value="roleInfo.roleName" />
      </a-form-item>
    </a-form>
  </a-modal>
  <!--  授予权限-->
  <a-modal v-model:visible="setFuncVisible"  width="700px" title="用户信息" @ok="setFunc">
    <a-tree
        checkable
        :tree-data="funcs"
        :replaceFields="{children:'children', title:'funcName', key:'id' }"
        :checkStrictly="true"
        v-model:checkedKeys="funcSelect"
    ></a-tree>
  </a-modal>
</template>

<script>
import OrgTree from "@/views/frame/components/OrgTree";
import BTableHeader from "@/views/components/BTableHeader";

export default {
  name: "Role",
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
      this.axios.post("/proxyService/frameService/getRoleByOrg",param).then((res)=>{
        if(res.rtnCode==200){
          this.roleData=res.data;
        }
      })
    },
    /**
     * role选择
     * @param selectedRowKeys
     * @param selectedRows
     */
    userChange(selectedRowKeys, selectedRows){
      if(selectedRowKeys.length!=0){
        this.roleInfo=selectedRows[0]
      }

    },
    /**
     * 展示添加窗口
     */
    showAdd(){
      if(this.treeNode){
        this.addOrUpdate=1;
        this.addAndUpdateVisible=true;
        this.roleInfo={
          id:'',
          roleName:'',
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
          param.role=this.roleInfo;
          param.role.id=null;
          param.orgId=this.treeNode.id;
          this.axios.post("/proxyService/frameService/saveOrUpdateRole",param).then((res)=>{
            if(res.rtnCode==200){
              this.userData=res.data;
              this.addAndUpdateVisible=false;
              this.selectNode(this.treeNode);
            }
          })
        }else if(this.addOrUpdate==2){
          const param={};
          param.role=this.roleInfo;
          console.log(param)
          this.axios.post("/proxyService/frameService/saveOrUpdateRole",param).then((res)=>{
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
     * 删除role
     */
    delUser(){
      if(this.roleInfo.id!=null){
        //删除确认窗口
        this.$confirm({
          title: '提示',
          content: '确定删除？', okText: '删除', cancelText: '取消', okType: 'danger',
          onOk:()=> {
            const param={};
            param.id=this.roleInfo.id;
            this.axios.post("/proxyService/frameService/delRole",param).then((res)=>{
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
    showSetFunc(){
      if(this.roleInfo.id){
        const param={};
        param.roleId=this.roleInfo.id;
        param.orgId=this.treeNode.id;
        this.axios.post("/proxyService/frameService/queryFuncByRole",param).then((res)=>{
          if(res.rtnCode==200){
            this.funcs=this.global.methods.createTree(res.data.funcs)
            this.funcSelect=this.global.methods.objsToArr(res.data.funcSelect,"id");
          }
        })
        this.setFuncVisible=true;
      }else{
        this.$message.warning('请选择用户');
      }
    },
    setFunc(){
      const param={};
      param.roleId=this.roleInfo.id;
      param.funcSelect=this.funcSelect.checked;
      console.log(param)
      this.axios.post("/proxyService/frameService/saveRoleFunc",param).then((res)=>{
        if(res.rtnCode==200){
          this.setFuncVisible=false;
        }
      })
    },
  },
  data(){
    return{
      treeNode:null,
      roleData:null,
      roleInfo:{
        id:null,
        roleName:null,
      },
      addOrUpdate:0,
      addAndUpdateVisible:false,
      setFuncVisible:false,
      funcs:[],
      funcSelect:[],
    }
  },
}
</script>

<style scoped>

</style>