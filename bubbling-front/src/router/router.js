import { createRouter, createWebHistory } from 'vue-router'
import home from "@/views/home/Home";
import login from "@/views/login/Login";
import test from "@/views/test/Test";
import func from "@/views/frame/Func";
import org from "@/views/frame/Org";
import role from "@/views/frame/Role";
import user from "@/views/frame/User";

const routerHistory = createWebHistory()
const router = createRouter({
    history: routerHistory,
    routes: [
        {path: '/', component: home},
        {path: '/login', component: login},
        {path: '/test', component: test},
        {path: '/user', component: user},
        {path: '/home', component: home,
            children: [
                //测试页面
                {path: 'func', component: func},
                {path: 'org', component: org},
                {path: 'role', component: role},
                {path: 'user', component: user},
            ]
        },

    ]
})

export default router