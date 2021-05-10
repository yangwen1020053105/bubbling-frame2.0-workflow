<template>
<div class="box">
  <div class="title">{{title}}</div>
  <slot></slot>
</div>

</template>

<script>
export default {
  name: "BCard",
  props:{
    title:String,
  },
}
</script>

<style lang="less" scoped>
.box{padding: 5px;background-color: #fff;margin: 0px 10px 10px 0px;}
.title {padding-left: 12px;margin: 0px 0px 5px 5px; position: relative;}
.title:before {position: absolute;content: '';left: 0;top: 0;bottom: 0;margin: auto;height: 15px;width: 3px;background:@primary-color;}

</style>