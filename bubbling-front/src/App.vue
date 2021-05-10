<template>
  <a-config-provider :locale="locale">
    <router-view />
  </a-config-provider>
</template>

<script>
import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN';

export default {
  name: 'App',
  data() {
    return {
      locale: zhCN,
    };
  },
}
</script>

<style>
html body #app{
  height: 100%;
  width: 100%;
  overflow: hidden;
}
</style>
