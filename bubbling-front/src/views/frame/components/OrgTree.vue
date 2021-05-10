<template>
  <a-directory-tree
      multiple
      :treeData="orgData"
      @select="selectTree"
      :replaceFields="{children:'children', title:'orgName', key:'id' }">
  </a-directory-tree>
</template>

<script>
export default {
  name: "OrgTree",
  created() {
    //初始化树
    this.getOrgData();
  },
  props:{

  },
  methods:{
    getOrgData(){
      this.axios.post('/proxyService/frameService/getOrgList').then((res)=>{
        if(res.rtnCode=="200"){
          this.orgData=this.global.methods.createTree(res.data)
        }
      })
    },
    selectTree(selectedKeys, e){
      this.$emit('selectNode',e.selectedNodes[0].dataRef);
    }
  },
  data(){
    return{
      orgData:[],
    }
  },
}
</script>

<style scoped>

</style>