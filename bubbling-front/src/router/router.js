import { createRouter, createWebHistory } from 'vue-router'
import home from "@/views/home/Home";
import login from "@/views/login/Login";
import test from "@/views/test/Test";
import func from "@/views/frame/Func";
import org from "@/views/frame/Org";
import role from "@/views/frame/Role";
import user from "@/views/frame/User";
import Designer from "@/views/workFlow/designer/Designer";
import FlowDef from "@/views/workFlow/FlowDef";
import FlowInst from "@/views/workFlow/FlowInst";

const routerHistory = createWebHistory()
const router = createRouter({
    history: routerHistory,
    routes: [
        {path: '/', component: home},
        {path: '/login', component: login},
        {path: '/test', component: test},
        {path: '/designer', component: Designer},
        {path: '/home', component: home,
            children: [
                {path: 'func', component: func,meta: {notCache: false}},
                {path: 'org', component: org,meta: {notCache: false}},
                {path: 'role', component: role,meta: {notCache: false}},
                {path: 'user', component: user,meta: {notCache: false}},
                {path: 'flowDef', component: FlowDef,meta: {notCache: false}},
                {path: 'flowInst', component: FlowInst,meta: {notCache: false}},
            ]
        },

    ]
})

export default router