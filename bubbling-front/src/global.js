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
    },
    datetimeFormat(longTypeDate){
        var dateTypeDate = "";
        var date = new Date();
        date.setTime(longTypeDate);
        dateTypeDate += date.getFullYear(); //年
        dateTypeDate += "-" + getMonth(date); //月
        dateTypeDate += "-" + getDay(date); //日
        dateTypeDate += " " + getHours(date); //时
        dateTypeDate += ":" + getMinutes(date);  //分
        dateTypeDate += ":" + getSeconds(date);  //分
        console.log(dateTypeDate)
        return dateTypeDate;
        //返回 01-12 的月份值
        function getMonth(date){
            var month = "";
            month = date.getMonth() + 1; //getMonth()得到的月份是0-11
            if(month<10){
                month = "0" + month;
            }
            return month;
        }
        //返回01-30的日期
        function getDay(date){
            var day = "";
            day = date.getDate();
            if(day<10){
                day = "0" + day;
            }
            return day;
        }
        //小时
        function getHours(date){
            var hours = "";
            hours = date.getHours();
            if(hours<10){
                hours = "0" + hours;
            }
            return hours;
        }
        //分
        function getMinutes(date){
            var minute = "";
            minute = date.getMinutes();
            if(minute<10){
                minute = "0" + minute;
            }
            return minute;
        }
        //秒
        function getSeconds(date){
            var second = "";
            second = date.getSeconds();
            if(second<10){
                second = "0" + second;
            }
            return second;
        }
    }
}
export default {gateway,methods}