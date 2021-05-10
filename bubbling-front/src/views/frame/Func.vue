<template>
  <a-row style="height: 100%;">
    <a-col :span="6">
      <b-card title="菜单信息">
        <func-manager-tree ref="func" @selectNode="selectNode"></func-manager-tree>
      </b-card>
    </a-col>
    <a-col :span="18">
      <b-card title="编辑">
        <a-form :model="funcData" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }">
          <a-form-item label="菜单名称">
            <a-input v-model:value="funcData.funcName" />
          </a-form-item>
          <a-form-item label="菜单路径">
            <a-input v-model:value="funcData.viewPath" />
          </a-form-item>
          <a-form-item label="图标代码">
            <a-input v-model:value="funcData.imagePath" />
          </a-form-item>
          <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
            <a-space>
              <a-button type="primary" @click="insert">添加</a-button>
              <a-button type="primary" @click="update">修改</a-button>
              <a-button type="primary" @click="del">删除</a-button>
            </a-space>
          </a-form-item>
        </a-form>
      </b-card>
    </a-col>
  </a-row>
</template>

<script>
import FuncManagerTree from "./components/FuncManagerTree";

export default {
  name: "Func",
  components: {FuncManagerTree},
  component:{
    FuncManagerTree,
  },
  methods:{
    selectNode(node){
      this.funcData=this.global.methods.copyObject(node);
    },
    insert(){
      const param=this.funcData;
      param.id=null;
      this.axios.post("/proxyService/frameService/saveOrUpdateFunc",param).then((res)=>{
        if(res.rtnCode==200){
          this.$refs.func.getFuncData();
        }
      })
    },
    update(){
      this.axios.post("/proxyService/frameService/saveOrUpdateFunc",this.funcData).then((res)=>{
        if(res.rtnCode==200){
          this.$refs.func.getFuncData();
        }
      })
    },
    del(){
      this.axios.post("/proxyService/frameService/delFunc",this.funcData).then((res)=>{
        if(res.rtnCode==200){
          this.$refs.func.getFuncData();
        }
      })
    },
  },
  data(){
    return{
      funcData:{
        id:null,
        pid:null,
        funcName:null,
        viewPath:null,
        imagePath:null,
      },
    }
  },
}
</script>

<style scoped>

</style>