import { createApp,createVNode } from 'vue';
import App from './App.vue';
import router from "@/router/router";
import Antd from 'ant-design-vue';
import { message } from 'ant-design-vue';
import { notification } from 'ant-design-vue';
import 'ant-design-vue/dist/antd.less';
import global from "@/global";
import axiosF from 'axios';
import VueAxios from 'vue-axios';
import Cookies from 'js-cookie';
import BCard from "@/views/components/BCard"
import BDataTable from "@/views/components/BDataTable"
import BTableHeader from "@/views/components/BTableHeader"
const app = createApp(App)
app.use(createVNode)
app.component('BCard', BCard)
app.component('BDataTable', BDataTable)
app.component('BTableHeader', BTableHeader)
//引入全局变量
app.config.globalProperties.global=global
//引入antd
app.use(Antd)
// app.use(notification)
//引入axios
const axios = axiosF.create({
    baseURL:global.gateway,//全局变量
    timeout: 10000,
    headers: {'X-Custom-Header': 'foobar'},
    withCredentials: true,
});
// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    return config
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error)
})
// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    console.log(response)
    // 对响应数据做点什么
    if(response.data==1100||response.data.rtnCode==1100){
        //未登录跳转登录界面
        window.location.href="/login";
    }else if(response.data.rtnCode!=200&&response.data.rtnCode!=1100){
        message.error(response.rtnMsg);
    }
    return response.data;
}, function (error) {
    console.log(error.toString())
    // 对响应错误做点什么
    notification["error"]({
        message: 'error',
        description:error.toString(),
    });
    return Promise.reject(error);
});
app.use(VueAxios, axios)
//引入路由
app.use(router)
// 全局路由守卫
router.beforeEach((to, from, next)=>{
    if(to.fullPath.indexOf("/home")!=-1||to.fullPath=='/'){
        //验证token是否有效
        const token = Cookies.get("token");
        if(token!=null&&token!=''){
            const param={}
            param.token=token;
            axios.get('/checkToken.do', {params:param}).then((res)=>{
                if(res.rtnCode==200){
                    //有效放行
                    next()
                }else if(res.rtnCode==1100){
                    //token无效 跳转登录
                    next("/login")
                }
            })
        }else{
            //token为空 跳转登录
            next("/login")
        }
    }else{
        next()
    }
})
app.mount('#app')