<template>
  <a-menu-item  v-if="item.children==null" :key="item.id">
    <span @click="handleClick(item)">{{item.funcName}}</span>
  </a-menu-item>
  <a-sub-menu v-if="item.children!=null" :key="item.id">
    <template #title>
      <span>
        <AppstoreOutlined />
        <span>{{item.funcName}}</span>
      </span>
    </template>
    <FuncTreeItem @nodeClick="outClick" v-for="node in item.children" :key="node" :item="node"></FuncTreeItem>
  </a-sub-menu>
</template>

<script>
import {
  AppstoreOutlined
} from '@ant-design/icons-vue';
export default {
  name: "FuncTreeItem",
  components:{
    AppstoreOutlined
  },
  props:{
    item:Object,
  },
  methods: {
    handleClick(node){
      this.$emit('nodeClick',node);
    },
    outClick(node){
      this.$emit('nodeClick',node);
    }
  }
}
</script>

<style scoped>

</style>