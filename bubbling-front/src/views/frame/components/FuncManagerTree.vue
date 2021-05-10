<template>
  <a-directory-tree multiple  :treeData="funcData" @select="selectTree"  :replaceFields="{children:'children', title:'funcName', key:'id' }"></a-directory-tree>
</template>

<script>
export default {
  name: "FuncManagerTree",
  created() {
    //初始化树
    console.log(123)
    this.getFuncData();
  },
  props:{

  },
  methods:{
    getFuncData(){
      this.axios.post('/proxyService/frameService/getFuncList').then((res)=>{
        if(res.rtnCode=="200"){
          this.funcData=this.global.methods.createTree(res.data)
        }
      })
    },
    selectTree(selectedKeys, e){
      this.$emit('selectNode',e.selectedNodes[0].dataRef);
    }
  },
  data(){
    return{
      funcData:[],
    }
  },
}
</script>

<style scoped>

</style>