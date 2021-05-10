<template>
  <a-menu mode="inline" theme="dark" :inline-collapsed="collapsed" v-model:openKeys="openKeys" v-model:selectedKeys="selectedKeys">
    <FuncTreeItem @nodeClick="nClick" v-for="node in treeDate" :key="node" :item="node" >

    </FuncTreeItem>
  </a-menu>
</template>

<script>
import FuncTreeItem from './FuncTreeItem';
export default {
  name: "FuncTree",
  components:{
    FuncTreeItem,
  },
  props:{
    treeDate:Object,
  },
  methods: {
    nClick(node){
      this.$emit('nodeClick',node);
    }
  }
}
</script>

<style scoped>

</style>