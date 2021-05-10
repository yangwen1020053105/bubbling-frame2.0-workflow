<template>
  <a-row style="height: 100%;">
    <a-col :span="6">
      <b-card title="部门信息">
        <org-tree ref="org" @selectNode="selectNode"></org-tree>
      </b-card>
    </a-col>
    <a-col :span="18">
      <b-card title="编辑">
        <a-form :model="orgData" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }">
          <a-form-item label="部门名称">
            <a-input v-model:value="orgData.orgName" />
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
import OrgTree from "@/views/frame/components/OrgTree";
export default {
  name: "Org",
  created() {

  },
  components:{
    OrgTree,
  },
  methods:{
    selectNode(node){
      this.orgData=this.global.methods.copyObject(node);
    },
    insert(){
      const param={}
      param.orgName=this.orgData.orgName;
      param.pid=this.orgData.id;
      this.axios.post("/proxyService/frameService/saveOrUpdateOrg",param).then((res)=>{
        if(res.rtnCode==200){
          this.$refs.org.getOrgData();
        }
      })
    },
    update(){
      this.axios.post("/proxyService/frameService/saveOrUpdateOrg",this.orgData).then((res)=>{
        if(res.rtnCode==200){
          this.$refs.org.getOrgData();
        }
      })
    },
    del(){
      this.axios.post("/proxyService/frameService/delOrg",this.orgData).then((res)=>{
        if(res.rtnCode==200){
          this.$refs.org.getOrgData();
        }
      })
    }
  },
  data(){
    return{
      orgData:{
        id:null,
        pid:null,
        orgName:null,
      },
    }
  },
}
</script>

<style scoped>

</style>