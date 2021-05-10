<template>
  <a-layout style="height: 100%">
    <!--左侧菜单-->
    <a-layout-sider v-model:collapsed="collapsed" :trigger="null" collapsible>
      <div class="logo" style="height: 50px;color: white;line-height: 50px;text-align: center;font-size: 18px;" >{{collapsed?'B':'Bubbling@Frame2.0'}}</div>
      <FuncTree :treeDate="treeDate" @nodeClick="showContent"></FuncTree>
    </a-layout-sider>
    <!--右侧-->
    <a-layout>
      <!--右侧头部-->
      <a-layout-header style="background: #fff; height: 45px;padding: 0px 10px;">
        <!--收缩菜单-->
        <menu-unfold-outlined  v-if="collapsed" class="trigger" @click="() => (collapsed = !collapsed)"/>
        <menu-fold-outlined  v-else class="trigger" @click="() => (collapsed = !collapsed)" />
        <!--登录人-->
        <a-dropdown>
          <div class="userBox" >
            <div class="rightTrigger">{{userName==null?'未登录':userName}}</div>
            <UserOutlined class="rightTrigger" />
          </div>
          <template #overlay>
            <a-menu >
              <a-menu-item key="1" @click="updatePassword"><KeyOutlined />修改密码</a-menu-item>
              <a-menu-item key="2" @click="loginOut"><PoweroffOutlined />退出登录</a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
      </a-layout-header>
      <!--右侧内容-->
      <a-tabs tabBarGutter="0" type="editable-card" style="margin-left: 2px;" size="small"  hideAdd="true" v-model:activeKey="activeKey" @change="changePage"  @edit="removeTba">
        <a-tab-pane forceRender="true"  v-for="pane in panes" :key="pane.key" :tab="pane.title" :closable="pane.closable">
        </a-tab-pane>
      </a-tabs>
      <a-divider style="height: 0.5px;margin:-16px 0px 0px 0px;background-color: #fff" />
      <a-layout-content :style="{ padding: '10px 0px 0px 10px', background: '#f0f2f5', minHeight: '280px' }">
        <router-view ></router-view>
      </a-layout-content>
    </a-layout>
  </a-layout>
  <a-modal v-model:visible="updatePassVisible"  width="700px" title="用户信息" @ok="updatePass">
    <a-form ref="form" :model="passWord" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }" :rules="{
              oldPassword: [{ required: true, message: 'Please input Activity name', trigger: 'blur' }],
              newPassword: [{ required: true, message: 'Please input Activity name', trigger: 'blur' }],
              sNewPassword: [{ required: true, message: 'Please input Activity name', trigger: 'blur' }],
              }">
      <a-form-item name="oldPassword" label="旧密码">
        <a-input autocomplete="autocomplete" type="password" placeholder="密码" v-model:value="passWord.oldPassword"></a-input>
      </a-form-item>
      <a-form-item name="newPassword" label="新密码">
        <a-input autocomplete="autocomplete" type="password" placeholder="密码" v-model:value="passWord.newPassword"></a-input>
      </a-form-item>
      <a-form-item name="sNewPassword" label="确认密码">
        <a-input autocomplete="autocomplete" type="password" placeholder="密码" v-model:value="passWord.sNewPassword"></a-input>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import {UserOutlined,KeyOutlined,PoweroffOutlined, MenuUnfoldOutlined, MenuFoldOutlined,} from '@ant-design/icons-vue';
import { defineComponent, ref } from 'vue';
import Cookies from 'js-cookie';
import FuncTree from "@/views/home/components/FuncTree";
export default defineComponent({
  components: {
    UserOutlined,
    MenuUnfoldOutlined,
    MenuFoldOutlined,
    FuncTree,
    KeyOutlined,
    PoweroffOutlined,
  },
  setup() {
    return {
      selectedKeys: ref(['1']),
      collapsed: ref(false),
    };
  },
  created() {
    //登录人
    this.userName=Cookies.get('userName');
    //菜单
    this.axios.get('/queryFuncTree.do').then((res)=>{
      if(res.rtnCode==200){
        this.treeDate=this.global.methods.createTree(res.data);
      }
    })
  },
  methods:{
    /**
     * 显示修改密码窗口
     */
    updatePassword(){
      this.updatePassVisible=true;
    },
    /**
     * 修改密码
     */
    updatePass(){
      //新密码相等
      if(this.passWord.newPassword==this.passWord.sNewPassword){
        const param={}
        param.token=Cookies.get("token");
        param.oldPassword=this.passWord.oldPassword;
        param.newPassword=this.passWord.newPassword;
        this.axios.post("/updatePassword.do",param).then((res)=>{
          if(res.rtnCode=="200"){
            this.$message.success("密码修改成功请重新登录！正在跳转...");
            setTimeout(()=>{
              window.location.href="/login";
            },1000);
          }else{
            this.$message.error(res.rtnMsg);
          }
        })
      }else{
        this.$message.error("两次密码不相等");
      }
    },
    /**
     * 登出
     */
    loginOut(){
      const token = Cookies.get("token");
      this.axios.get('/loginOut.do',{params:{"token":token}}).then((res)=>{
        if(res.rtnCode==200){
          Cookies.remove('token');
          Cookies.remove('userName');
          this.$router.push("/login");
        }
      })
    },
    showContent(node){
      if(this.getIndeInPanes(node.viewPath)==-1){
        this.panes.push({
          title: node.funcName,
          content: node.funcName,
          key: node.viewPath,
        });
        this.activeKey=node.viewPath;
        this.$router.push({ path: node.viewPath });
      }
    },
    changePage(activeKey){
      this.$router.push(activeKey);
    },
    removeTba(targetKey, action){
      if (action != 'add') {
        const num=this.getIndeInPanes(targetKey);
        //切换前一个tab
        if(this.panes[num*1-1]!=null){
          this.activeKey=this.panes[num*1-1].key;
          this.changePage(this.activeKey);
        }else if(this.panes[num*1+1]!=null){
          this.activeKey=this.panes[num*1+1].key;
          this.changePage(this.activeKey);
        }else{
          //最后一个tab不允许删除
          this.changePage("/home");
        }
        //删除tab
        this.panes.splice(num,1);
      }
    },
    getIndeInPanes(targetKey){
      var num=-1;
      for (const index in this.panes) {
        if(this.panes[index].key==targetKey){
          num=index;
        }
      }
      return num;
    }
  },
  data(){
    return {
      userName:null,
      treeDate:null,
      activeKey:null,
      panes:[],
      display:false,
      updatePassVisible:false,
      passWord:{
        oldPassword:null,
        newPassword:null,
        sNewPassword:null,
      },
    }
  },
});
</script>

<style lang="less" scoped>
#components-layout-demo-custom-trigger .trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

#components-layout-demo-custom-trigger .trigger:hover {
  color: #1890ff;
}

#components-layout-demo-custom-trigger .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.3);
  margin: 16px;
}

.site-layout .site-layout-background {
  background: #fff;
}
.trigger{
  float: left;height: 100%;line-height: 50px;margin-left: 10px;
}
.rightTrigger{
  float: right;height: 100%;line-height: 50px;margin-right: 10px;
}
.userBox{
  cursor: pointer;
  float: right;
}
.userBox:hover{
  background-color: #f0f0f0;
}
.updatePassword{
  background-color: rgb(255, 255, 255);
  width: 120px;
  height: 80px;
  position: absolute;
  z-index: 9999;
  right: 0px;
  top: 45px;
  border-radius: 5px;
  display: none;
}
.divBut{
  width: 100%;height: 30px;margin: 5px 0px;line-height: 30px;text-align: center;
}
.divBut:hover{
  background-color:@primary-color;
}
</style>