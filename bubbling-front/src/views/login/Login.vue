<template>
  <div class="back">
    <div class="loginBox">
      <div class="loginTitle">
        <div class="title1">Bubbling@Frame2.0集成开发平台</div>
        <p>欢迎登录</p>
      </div>
      <a-row >
        <a-col :span="24">
          <div class="components-input-demo-presuffix" style="padding: 0px 0px">
            <a-input size="large" autocomplete="autocomplete" placeholder="用户名" v-model:value="userName">
              <template #prefix>
                <user-outlined type="user" />
              </template>
            </a-input>
            <br />
            <br />
            <a-input size="large" autocomplete="autocomplete" @onkeydown="loginKeyDown" type="password" placeholder="密码" v-model:value="password">
              <template #prefix>
                <KeyOutlined />
              </template>
            </a-input>
            <br />
            <br />
            <br />
            <a-button size="large" type="primary" @click="login" block>登录</a-button>
          </div>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script>
import { UserOutlined } from '@ant-design/icons-vue';
import { KeyOutlined } from '@ant-design/icons-vue';
import Cookies from 'js-cookie';
import JSEncrypt from 'jsencrypt';
export default {
  name: "Login",
  components: {
    UserOutlined,
    KeyOutlined,
  },
  methods:{
    loginKeyDown(e){
      if(e.keyCode==13){
        this.login();
      }
    },
    login(){
      //计算响应时间
      const startDate=new Date();
      this.axios.get('/test.do').then((res)=>{
        if(res.rtnCode==200){
          const endDate=new Date();
          const time=endDate.getTime()-startDate.getTime();//响应时间
          //获取公钥
          const param={}
          param.time=time;
          this.axios.get('/queryRsaPublicKey.do', {params:param}).then((res)=>{
            if(res.rtnCode=="200"){
              const encrypt = new JSEncrypt();
              encrypt.setPublicKey(res.data);
              //登录
              const param={}
              param.publicKey=res.data;
              param.userName=encrypt.encrypt(this.userName);
              param.password=encrypt.encrypt(this.password);
              this.axios.post("/login.do",param).then((res)=>{
                if(res.rtnCode=="200"){
                  Cookies.set('token', res.data.token, { expires: 0.125, path: '/' });
                  Cookies.set('userName', res.data.userName, { expires: 0.125, path: '/' });
                  this.$router.push("/home");
                }else{
                  this.$notification['error']({
                    message: '提示',
                    description:res.rtnMsg,
                  });
                }
              })
            }
          })
        }
      })


    },
  },
  data(){
    return {
      userName:'',
      password:'',
    }
  }
}
</script>

<style scoped>
.back{
  width: 100%;
  height: 100%;
  background-color: #f0f2f5;
  background-image: url("image/back.svg");
  background-size: 100% 100%;
}
.loginBox{
  width: 400px;
  height: 400px;
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
  padding: 15px 30px;

}
.loginTitle {
  margin-bottom: 30px;
  text-align: center;
}
.title1 {
  display: block;
  font-size: 20px;
  margin-block-start: 1em;
  margin-block-end: 1em;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
  font-weight: bold;
}
</style>