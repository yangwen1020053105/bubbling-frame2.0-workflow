const gateway="http://localhost:8083/";
const methods={
    /**
     * 获取当前时间yyyy-MM-dd hh:mm:ss.SSS
     * @returns {string}
     */
    getNowDate(){
        const time = new Date();
        return time.getFullYear()+"-"+time.getMonth()+"-"+time.getDate()+" "+time.getHours()+":"+time.getMinutes()+":"+time.getSeconds()+"."+time.getMilliseconds()
    },
    /**
     * 构建树数据
     * @param data 数据
     * @param id 唯一标识 默认为id
     * @param parentId 上级标识 默认为pid
     * @param children 子数据名称 默认为children
     * @param rootId 根节点id 默认为1
     * @returns {*}
     */
    createTree(data, id, parentId, children, rootId) {
        id = id || 'id'
        parentId = parentId || 'pid'
        children = children || 'children'
        rootId = rootId || '1'
        //对源数据深度克隆
        const cloneData = JSON.parse(JSON.stringify(data))
        //循环所有项
        let treeData =  cloneData.filter(father => {
            let branchArr = cloneData.filter(child => {
                //返回每一项的子级数组
                return father[id] === child[parentId]
            });
            branchArr.length > 0 ? father.children = branchArr : '';
            //返回第一层
            return father[parentId] === rootId;

        });
        return treeData != '' ? treeData : data;
    },
    /**
     * 复制对象
     * @param obj
     * @returns {any}
     */
    copyObject(obj){
        return JSON.parse(JSON.stringify(obj));
    },
    /**
     * 将objs中的某个字段提取成[]
     * @param objs
     * @param name
     * @returns {[]}
     */
    objsToArr(objs,name){
        const arr=[];
        for (const objsKey in objs) {
            arr.push(objs[objsKey][name]);
        }
        return arr;
    }
}
export default {gateway,methods}