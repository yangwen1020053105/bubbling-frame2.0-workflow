<template>
  <div class="containers" ref="content">
    <div id="canvas" class="canvas" ref="canvas"></div>
    <div id="js-properties-panel" class="panel"></div>
    <ul class="buttons">
      <li>
        <a ref="saveDiagram" @click="saveDiagram" href="javascript:" title="下载为bpmn">保存为bpmn</a>
      </li>
      <li>
        <a ref="deploymentProcess" @click="deploymentProcess" href="javascript:" title="部署流程">部署流程</a>
      </li>
      <li>
        <a ref="saveSvg" href="javascript:" title="保存为svg">保存为svg</a>
      </li>
    </ul>
  </div>
</template>
<script>
import BpmnJS from 'bpmn-js/lib/Modeler'
import propertiesPanelModule from 'bpmn-js-properties-panel'
import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/camunda'
import activitiModdleDescriptor from 'camunda-bpmn-moddle/resources/camunda'
import customTranslate from './customTranslate';
var viewer=null;
export default {
  data () {
    return {
      // bpmn建模器
      container: null,
      canvas: null,
      key:null,
      category:null,
    }
  },
  mounted() {
    // 获取到属性ref为“content”的dom节点
    this.container = this.$refs.content
    // 获取到属性ref为“canvas”的dom节点
    const canvas = this.$refs.canvas
    var customTranslateModule = {
      translate: [ 'value', customTranslate ]
    };
    viewer = new BpmnJS({
      container: canvas,
      //添加控制板
      propertiesPanel: {
        parent: '#js-properties-panel'
      },
      additionalModules: [
        // 右边的工具栏
        propertiesPanelModule,
        propertiesProviderModule,
        customTranslateModule,
      ],
      moddleExtensions: {
        camunda: activitiModdleDescriptor
      }
    })
    this.createNewDiagram();
    this.addEventBusListener();
  },
  methods:{
    createNewDiagram(){
      var query=this.$route.query;
      this.key=query.key;
      this.category=query.category;
      const param={};
      param.key=this.key;
      this.axios.post("/proxyService/flowDefService/queryFlowDefXml",param).then((res)=>{
        if(res.rtnCode==200){
          let xml=this.createXml(res.data);
          viewer.importXML(xml, (err)=>{
            if (!err) {
              viewer.get('canvas').zoom('fit-viewport')
            } else {
              console.log('something went wrong:', err)
            }
          })
        }
      })

    },
    addEventBusListener() {
      // 监听 element
      const eventBus = viewer.get('eventBus')
      const eventTypes = ['element.click', 'element.changed', 'element.updateLabel']
      eventTypes.forEach(function(eventType) {
        eventBus.on(eventType, function(e) {
          console.log(eventType)
          if (!e || e.element.type == 'bpmn:Process') return
          if (eventType === 'element.changed') {
            // that.elementChanged(e)
          } else if (eventType === 'element.click') {
            console.log('点击了element', e)
            // if (e.element.type === 'bpmn:Task') {
            // }
          } else if (eventType === 'element.updateLabel') {
            console.log('element.updateLabel', e.element)
          }
        })
      })
    },
    deploymentProcess(){
      viewer.saveXML({ format: true }, (err, xml) =>{
        xml=this.formXML(xml);
        const param={}
        param.bpmn=xml;
        param.key=this.key;
        param.category=this.category;
        this.axios.post("/proxyService/flowDefService/updateProcess",param).then((res)=>{
          if(res.rtnCode==200){
            this.$message.success("部署成功")
          }
        })
      });
    },
    formXML(data){
      let temp=data;
      temp = data.replace(/camunda/ig,"activiti");
      temp = temp.replace(/FormField/ig,'formProperty');
      temp=temp.replace(/<definitions.*?>/,
          '<definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:activiti="http://activiti.org/bpmn" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC" xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI" xmlns:tns="http://www.activiti.org/test" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" expressionLanguage="http://www.w3.org/1999/XPath" id="m1621667210359" name="" targetNamespace="http://www.activiti.org/test" typeLanguage="http://www.w3.org/2001/XMLSchema">');
      return temp ;
    },
    createXml(data){
      let temp=data;
      temp = data.replace(/activiti/ig,"camunda");
      temp = temp.replace(/formProperty/ig,'FormField');
      temp=temp.replace(/<definitions.*?>/,
          '<definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI" xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" id="sid-38422fae-e03e-43a3-bef4-bd33b32041b2" targetNamespace="http://bpmn.io/bpmn" exporter="bpmn-js (https://demo.bpmn.io)" exporterVersion="5.1.2">')
      return temp ;
    },
    saveDiagram(){
      const downloadLink = this.$refs.saveDiagram
      viewer.saveXML({ format: true }, (err, xml) =>{
        xml=this.formXML(xml);
        this.download(downloadLink,"Diagram.xml",xml)
      });
    },
    download(link,name,data){
      // 把xml转换为URI，下载要用到的
      const encodedData = encodeURIComponent(data)
      if (data) {
        link.className = 'active'
        link.href = 'data:application/bpmn20-xml;charset=UTF-8,' + encodedData
        link.download = name
      }
    },
  }
}
</script>
<style scoped>
/*左边工具栏以及编辑节点的样式*/
@import '~bpmn-js/dist/assets/diagram-js.css';
@import '~bpmn-js/dist/assets/bpmn-font/css/bpmn.css';
@import '~bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css';
@import '~bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css';
@import '~bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css';
.containers{
  background-color: #ffffff;
  width: 100%;
  height: calc(100vh - 52px);
}
.canvas{
  width: 100%;
  height: 100%;
}
.panel{
  position: absolute;
  right: 0;
  top: 0;
  width: 300px;
}
.buttons {
  position: absolute;
  left: 20px;
  bottom: 20px;
}
.buttons li {
  display: inline-block;
  margin: 5px;
}
.buttons li a {
  color: #333;
  background: #fff;
  cursor: pointer;
  padding: 8px;
  border: 1px solid #52c41a;
  text-decoration: none;
}
</style>