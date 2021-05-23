<template>
  <a-row style="height: 100%;">
    <a-col :span="24">
      <b-card title="流程列表">
        <!--操作按钮       -->
        <b-table-header>
          <a-input v-model:value="queryInfo.category" style="width: 100px;" placeholder="类别"></a-input>
          <a-input v-model:value="queryInfo.key" style="width: 100px;" placeholder="唯一编码"></a-input>
          <a-input v-model:value="queryInfo.name" style="width: 100px;" placeholder="名称"></a-input>
          <a-button type="primary" @click="query">查询</a-button>
          <a-button type="primary" @click="showAdd">新增流程</a-button>
          <a-button type="primary" @click="showUpdate">修改流程</a-button>
          <a-button type="primary" @click="del">删除流程</a-button>
        </b-table-header>
        <!--用户数据表        -->
        <b-data-table ref="flowTable" :columns="[
            {title: '类别',dataIndex: 'category',},
            {title: '唯一编码',dataIndex: 'key',},
            {title: '名称',dataIndex: 'name',},
            ]" :type="'radio'" @rowChange="change"  :data="flowInfoList"></b-data-table>
      </b-card>
    </a-col>
  </a-row>
  <!--  添加表单-->
  <a-modal v-model:visible="addVisible"  width="700px" title="流程定义" @ok="add">
    <a-form ref="form" :model="flowInfo" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }" :rules="{
              key: [{ required: true, message: '请输入唯一编码', trigger: 'blur' }],
              name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
              }">
      <a-form-item name="category" label="类别">
        <a-input v-model:value="flowInfo.category" />
      </a-form-item>
      <a-form-item name="key" label="唯一编码">
        <a-input v-model:value="flowInfo.key" />
      </a-form-item>
      <a-form-item name="name" label="名称">
        <a-auto-complete v-model:value="flowInfo.name"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: "FlowDef",
  created() {
    this.query();
  },
  methods:{
    showAdd(){
      this.addVisible=true;
      this.flowInfo={
        id:null,
        category:null,
        key:null,
        name:null,
      }
      this.$refs.flowTable.cleanSlect();
    },
    add(){
      this.$refs.form.validate().then(() => {
        const param=this.flowInfo;
        this.axios.post("/proxyService/flowDefService/deploymentProcess",param).then((res)=>{
          if(res.rtnCode==200){
            this.addVisible=false;
            this.query();
            this.$message.success("添加成功");
          }
        })
      })
    },
    query(){
      const param=this.queryInfo;
      this.axios.post("/proxyService/flowDefService/queryFlowDef",param).then((res)=>{
        if(res.rtnCode==200){
          this.flowInfoList=res.data;
          console.log(this.flowInfoList)
        }
      })
    },
    showUpdate(){
      if(this.flowInfo.id!=null){
        window.open("/designer?key="+this.flowInfo.key+"&category="+this.flowInfo.category);
      }else{
        this.$message.warning("请选择流程")
      }

    },
    change(selectedRowKeys, selectedRows){
      this.flowInfo=selectedRows[0];
    },
    del(){
      if(this.flowInfo.id!=null){
        //删除确认窗口
        this.$confirm({
          title: '提示',
          content: '确定删除？', okText: '删除', cancelText: '取消', okType: 'danger',
          onOk:()=> {
            const param={};
            param.key=this.flowInfo.key;
            this.axios.post("/proxyService/flowDefService/delFlowDef",param).then((res)=>{
              if(res.rtnCode==200){
                this.$message.success("删除成功");
                this.query();
              }
            })
          },
          onCancel:()=> {

          },
        });
      }else{
        this.$message.error("请选择流程")
      }
    }
  },
  data(){
    return{
      addVisible:false,
      flowInfoList:[],
      flowInfo:{
        id:null,
        category:null,
        key:null,
        name:null,
      },
      queryInfo:{
        category:null,
        key:null,
        name:null,
      },
    }
  }
}
</script>

<style scoped>

</style>