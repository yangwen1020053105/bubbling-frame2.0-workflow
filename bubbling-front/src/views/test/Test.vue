<template>
  <div>
    <a-table :rowSelection="{
        selectedRowKeys: selectedRowKeys,
        onChange: onSelectChange,
        getCheckboxProps:getCheckboxProps
      }"

             :columns="columns"
             :dataSource="data"
             :customRow="rowClick">
    </a-table>
  </div>
</template>
<script>
const columns = [
  {
    title: "Name",
    dataIndex: "name",
    scopedSlots: { customRender: "name" }
  },
  {
    title: "Age",
    dataIndex: "age"
  },
  {
    title: "Address",
    dataIndex: "address"
  }
];
const data = [
  {
    key: "1",
    name: "John Brown",
    age: 32,
    address: "New York No. 1 Lake Park"
  },
  {
    key: "2",
    name: "Jim Green",
    age: 42,
    address: "London No. 1 Lake Park"
  },
  {
    key: "3",
    name: "Joe Black",
    age: 32,
    address: "Sidney No. 1 Lake Park"
  },
  {
    key: "4",
    name: "Disabled User",
    age: 99,
    address: "Sidney No. 1 Lake Park"
  }
];
export default {
  data () {
    return {
      data,
      columns,
      selectedRowKeys: [],
      selectedRows: []
    };
  },
  computed: {
    // checkBox属性设置
    // eslint-disable-next-line no-unused-vars
    getCheckboxProps (record) {
      return record => ({
        props: {
          defaultChecked: record.name === this.data[0].name
        }
      })
    }
  },
  methods: {
    rowClick (record) {
      return {
        onClick: () => {
          console.log(record)
          if(this.selectedRowKeys.indexOf(record.id)==-1){
            this.selectedRowKeys.push(record.id);
          }
        }
      };
    },
    onSelectChange (selectedRowKeys) {
      console.log("selectedRowKeys changed: ", selectedRowKeys);
      this.selectedRowKeys = selectedRowKeys;
    },

  },
  mounted () {
  }
};
</script>