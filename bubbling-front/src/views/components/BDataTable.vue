
<template>
  <a-table
      class="ant-table-striped"
      size="small"
      :columns="columns"
      :data-source="data"
      :row-selection="{type:type,onChange:rowChange,selectedRowKeys:selectedRowKeys}"
      :customRow="rowClick"
      :rowKey="'id'"
  />
</template>

<script>
export default {
  name: "BDataTable",
  props:{
    selection:Object,
    data:Array,
    columns:Array,
    type:String,
  },
  methods:{
    rowClick(record){
      return {
        onClick: () => {
          const index=this.selectedRowKeys.indexOf(record.id);
          if(index==-1){
            if(this.type=="radio"){
              this.selectedRowKeys=[];
              this.selectedRows=[];
              this.selectedRowKeys.push(record.id);
              this.selectedRows.push(record);
            }else{
              this.selectedRowKeys.push(record.id);
              this.selectedRows.push(record);
            }
          }else{
            this.selectedRowKeys.splice(index,1);
            this.selectedRows.splice(index,1);
          }
          this.$emit('rowChange',this.selectedRowKeys,this.selectedRows);
        }
      };
    },
    rowChange(selectedRowKeys, selectedRows){
      //选中的数据的key
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
      this.$emit('rowChange',selectedRowKeys, selectedRows);
    },
    cleanSlect(){
      this.selectedRowKeys=[];
    },
  },
  data(){
    return{
      selectedRowKeys:[],
      selectedRows:[],
    }
  }
}
</script>

<style scoped>

</style>