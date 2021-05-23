<template>
  <a-row style="height: 100%;">
    <a-col :span="6">
      <b-card title="流程定义信息">
        <a-directory-tree
            multiple
            :treeData="flowDefData"
            @select="selectTree"
            :replaceFields="{children:'children', title:'name', key:'id' }">
        </a-directory-tree>
      </b-card>
    </a-col>
    <a-col :span="18">
      <b-card title="流程实例信息">
        <!--操作按钮       -->
        <b-table-header>
          <a-button type="primary" @click="showAdd">启动流程</a-button>
        </b-table-header>
        <!--用户数据表        -->
        <b-data-table ref="flowInstTable" :columns="[
        {title: '编码',dataIndex: 'key',},
        {title: '名称',dataIndex: 'name',},
        {title: '业务编码',dataIndex: 'businessKey',},
        ]" :type="'radio'" @rowChange="flowInstChange"  :data="flowInstData"></b-data-table>
      </b-card>
      <b-card title="环节实例信息">
        <!--操作按钮       -->
        <b-table-header>
          <a-button type="primary" @click="showCommit">提交结论</a-button>
        </b-table-header>
        <!--用户数据表        -->
        <b-data-table ref="activeInstTable" :columns="[
        {title: 'id',dataIndex: 'id',},
        {title: '环节名称',dataIndex: 'name',},
        {title: '执行人',dataIndex: 'assignee',},
        {title: '开始时间',dataIndex: 'startTime',},
        {title: '环节编码',dataIndex: 'taskDefinitionKey',},
        {title: '环节状态',dataIndex: 'activeStatus',},
        ]" :type="'radio'" @rowChange="activeInstChange"  :data="activeInstData"></b-data-table>
      </b-card>
    </a-col>
  </a-row>
  <!--  添加和修改表单-->
  <a-modal v-model:visible="addVisible"  width="700px" title="用户信息" @ok="add">
    <a-form ref="form" :model="flowInstInfo" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }" :rules="{
              businessKey: [{ required: true, message: '请输入业务id', trigger: 'blur' }],
              }">
      <a-form-item name="businessKey" label="业务id">
        <a-input v-model:value="flowInstInfo.businessKey" />
      </a-form-item>
      <a-form-item name="assigneeMap" label="参数">
        <a-textarea
            v-model:value="flowInstInfo.assigneeMap"
            placeholder='org:000102&user:00001'
            :auto-size="{ minRows: 5, maxRows: 8 }"
        />
      </a-form-item>
    </a-form>
  </a-modal>
  <!--  添加和修改表单-->
  <a-modal v-model:visible="commitVisible"  width="700px" title="用户信息" @ok="commit">
    <a-form ref="form"  :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }" >
      <a-form-item name="assigneeMap" label="参数">
        <a-textarea
            v-model:value="assigneeMap"
            placeholder='org:000102&user:00001'
            :auto-size="{ minRows: 5, maxRows: 8 }"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: "FlowInst",
  created() {
    this.queryAllFlowDef();
  },
  methods:{
    queryAllFlowDef(){
      const param={};
      this.axios.post("/proxyService/flowDefService/queryAllFlowDef",param).then((res)=>{
        if(res.rtnCode==200){
          this.flowDefData=this.global.methods.createTree(res.data,"id","category")
        }
      })
    },
    selectTree(selectedKeys, e){
      if (e.selectedNodes[0].dataRef.key!=null) {
        this.flowKey=e.selectedNodes[0].dataRef.key;
        this.queryFlowInst();
      }
    },
    queryFlowInst(){
      const param={};
      param.key=this.flowKey;
      this.axios.post("/proxyService/flowInstService/queryFlowInstByKey",param).then((res)=>{
        if(res.rtnCode==200){
          this.flowInstData=res.data;
        }
      })
    },
    showAdd(){
      if(this.flowKey){
        this.addVisible=true;
      }else{
        this.$message.warning("请选择要启动的流程");
      }

    },
    /**
     * 启动流程
     */
    add(){
      //表单验证
      this.$refs.form.validate().then(() => {
        const param={};
        param.key=this.flowKey;
        param.businessKey=this.flowInstInfo.businessKey;
        param.assigneeMap=this.createAssigneeMap(this.flowInstInfo.assigneeMap);
        this.axios.post("/proxyService/flowInstService/startFlowInst",param).then((res)=>{
          if(res.rtnCode==200){
            this.addVisible=false;
            this.$message.success("启动成功");
            this.queryFlowInst();
          }
        })
      })
    },
    createAssigneeMap(str){
      const jsonObj={};
      const arr = str.split("&");
      for (let arrKey in arr) {
        var split = arr[arrKey].split(":");
        jsonObj[split[0]]=split[1];
      }
      return jsonObj;
    },
    flowInstChange(selectedRowKeys, selectedRows){
      this.flowInstInfo=selectedRows[0];
      if(this.flowInstInfo){
        this.queryActiveInst();
      }
    },
    activeInstChange(selectedRowKeys, selectedRows){
      this.activeInstInfo=selectedRows[0];
    },
    queryActiveInst(){
      const param={};
      param.flowInstId=this.flowInstInfo.id;
      this.axios.post("/proxyService/flowInstService/queryActiveInstByKey",param).then((res)=>{
        if(res.rtnCode==200){
          this.activeInstData=res.data;
        }
      })
    },
    showCommit(){
      this.commitVisible=true;
    },
    commit(){
      const param={};
      param.activeId=this.activeInstInfo.id;
      param.assigneeMap=this.createAssigneeMap(this.assigneeMap);
      this.axios.post("/proxyService/flowInstService/commitActive",param).then((res)=>{
        if(res.rtnCode==200){
          this.$message.success("提交成功");
          this.queryActiveInst();
        }
      })
    },
  },
  data(){
    return{
      flowKey:null,
      flowDefData:null,
      addVisible:false,
      flowInstInfo:{
        key:null,
        name:null,
        businessKey:null,
        assigneeMap:null,
      },
      flowInstData:null,
      activeInstData:null,
      activeInstInfo:null,
      commitVisible:false,
      assigneeMap:null,
    }
  }
}
</script>

<style scoped>

</style>